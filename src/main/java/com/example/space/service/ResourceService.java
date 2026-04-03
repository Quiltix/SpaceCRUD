package com.example.space.service;

import com.example.space.dao.ResourceDao;
import com.example.space.dao.ResourceLogDao;
import com.example.space.data.dto.resource.ResourceChangeDto;
import com.example.space.data.dto.resource.ResourceCreateDto;
import com.example.space.data.dto.resource.ResourceResponseDto;
import com.example.space.data.dto.resource.ResourceUpdateDto;
import com.example.space.data.model.Resource;
import com.example.space.data.model.ResourceLog;
import com.example.space.exception.error.EntityNotFoundException;
import com.example.space.mapper.ResourceMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ResourceService {

    private final ResourceDao resourceDao;
    private final ResourceLogDao resourceLogDao;
    private final ResourceMapper resourceMapper;

    public ResourceResponseDto createResource(ResourceCreateDto createDto) {
        Resource entity = resourceMapper.toEntity(createDto);
        Resource savedEntity = resourceDao.save(entity);
        return resourceMapper.toDto(savedEntity);
    }

    public ResourceResponseDto getResourceById(Integer id) {
        Resource entity = resourceDao.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Resource not found with id: " + id));
        return resourceMapper.toDto(entity);
    }

    public List<ResourceResponseDto> getAllResources(BigDecimal maxCurrentQuantity, Integer resourceTypeId, Integer spacecraftId, LocalDate lastUpdated) {
        return resourceDao.findAll(maxCurrentQuantity, resourceTypeId, spacecraftId, lastUpdated).stream()
                .map(resourceMapper::toDto)
                .collect(Collectors.toList());
    }

    @Transactional
    public ResourceResponseDto updateResource(Integer id, ResourceUpdateDto updateDto) {
        Resource existingEntity = resourceDao.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Resource not found with id: " + id));

        resourceMapper.updateEntityFromDto(updateDto, existingEntity);
        resourceDao.update(existingEntity);

        return resourceMapper.toDto(existingEntity);
    }

    public void deleteResource(Integer id) {
        resourceDao.deleteById(id);
    }

    /**
     * Метод для траты или пополнения ресурса с записью в лог.
     */
    @Transactional
    public ResourceResponseDto changeQuantity(Integer id, ResourceChangeDto changeDto) {
        Resource resource = resourceDao.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Resource not found with id: " + id));

        BigDecimal currentQty = resource.getCurrentQuantity();
        BigDecimal changeQty = changeDto.getQuantityChange();
        BigDecimal newQty = currentQty.add(changeQty);

        // Бизнес-проверка 1: Нельзя потратить больше, чем есть (уход в минус)
        if (newQty.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException(
                    "Cannot spend resources. Current: " + currentQty + ", Attempt to change: " + changeQty);
        }

        // Бизнес-проверка 2: Нельзя пополнить больше максимальной вместимости
        if (newQty.compareTo(resource.getMaxCapacity()) > 0) {
            throw new IllegalArgumentException(
                    "Cannot replenish resources beyond max capacity. Max: " + resource.getMaxCapacity() + ", Result would be: " + newQty);
        }

        LocalDateTime now = LocalDateTime.now();

        // 1. Обновляем количество в таблице resources
        resourceDao.updateQuantity(id, newQty, now);

        // 2. Создаем запись в таблице resource_logs
        ResourceLog log = new ResourceLog();
        log.setSpacecraftId(resource.getSpacecraftId());
        log.setResourceId(resource.getId());
        log.setQuantityChange(changeQty);
        log.setTimestamp(now);

        resourceLogDao.save(log);

        // 3. Возвращаем обновленный объект (для ответа формируем его вручную, чтобы не делать лишний SELECT)
        resource.setCurrentQuantity(newQty);
        resource.setLastUpdated(now);
        return resourceMapper.toDto(resource);
    }
}