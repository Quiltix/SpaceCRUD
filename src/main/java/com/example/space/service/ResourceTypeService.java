package com.example.space.service;


import com.example.space.dao.ResourceTypeDao;
import com.example.space.data.dto.resource.ResourceTypeCreateDto;
import com.example.space.data.dto.resource.ResourceTypeResponseDto;
import com.example.space.data.dto.resource.ResourceTypeUpdateDto;
import com.example.space.data.model.ResourceType;
import com.example.space.exception.error.EntityNotFoundException;
import com.example.space.mapper.ResourceTypeMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ResourceTypeService {

    private final ResourceTypeDao resourceTypeDao;
    private final ResourceTypeMapper resourceTypeMapper;

    @Transactional
    public ResourceTypeResponseDto createResourceType(ResourceTypeCreateDto createDto) {
        if (resourceTypeDao.existsByName(createDto.getName())) {
            throw new IllegalArgumentException("Resource type with name '" + createDto.getName() + "' already exists");
        }
        ResourceType entity = resourceTypeMapper.toEntity(createDto);
        ResourceType savedEntity = resourceTypeDao.save(entity);
        return resourceTypeMapper.toDto(savedEntity);
    }

    public ResourceTypeResponseDto getResourceTypeById(Integer id) {
        ResourceType entity = resourceTypeDao.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Resource type not found with id: " + id));
        return resourceTypeMapper.toDto(entity);
    }

    public List<ResourceTypeResponseDto> getAllResourceTypes(String search) {
        return resourceTypeDao.findAll(search).stream()
                .map(resourceTypeMapper::toDto)
                .collect(Collectors.toList());
    }

    @Transactional
    public ResourceTypeResponseDto updateResourceType(Integer id, ResourceTypeUpdateDto updateDto) {
        ResourceType existingEntity = resourceTypeDao.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Resource type not found with id: " + id));

        // Проверяем, что новое имя не занято другим типом
        if (updateDto.getName() != null && !updateDto.getName().equals(existingEntity.getName())) {
            if (resourceTypeDao.existsByName(updateDto.getName())) {
                throw new IllegalArgumentException("Resource type with name '" + updateDto.getName() + "' already exists");
            }
        }

        resourceTypeMapper.updateEntityFromDto(updateDto, existingEntity);
        resourceTypeDao.update(existingEntity);
        return resourceTypeMapper.toDto(existingEntity);
    }

    @Transactional
    public void deleteResourceType(Integer id) {
        if (resourceTypeDao.findById(id).isEmpty()) {
            throw new EntityNotFoundException("Resource type not found with id: " + id);
        }
        // TODO: Добавить логику проверки, не используется ли этот тип в таблице resources, прежде чем удалять
        resourceTypeDao.deleteById(id);
    }
}