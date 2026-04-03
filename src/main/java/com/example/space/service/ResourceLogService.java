package com.example.space.service;


import com.example.space.dao.ResourceLogDao;
import com.example.space.data.dto.resource.ResourceLogResponseDto;
import com.example.space.data.model.ResourceLog;
import com.example.space.mapper.ResourceLogMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ResourceLogService {

    private final ResourceLogDao resourceLogDao;
    private final ResourceLogMapper resourceLogMapper;

    public ResourceLogResponseDto getLogById(Long id) {
        ResourceLog entity = resourceLogDao.findById(id)
                .orElseThrow(() -> new RuntimeException("Resource log not found with id: " + id));
        return resourceLogMapper.toDto(entity);
    }

    public List<ResourceLogResponseDto> getAllLogs(Integer spacecraftId, Integer resourceId, LocalDate startDate, LocalDate endDate) {
        return resourceLogDao.findAll(spacecraftId, resourceId, startDate, endDate).stream()
                .map(resourceLogMapper::toDto)
                .collect(Collectors.toList());
    }

    // Получить историю конкретного ресурса (например, топливо конкретного корабля)
    public List<ResourceLogResponseDto> getLogsByResourceId(Integer resourceId) {
        return resourceLogDao.findByResourceId(resourceId).stream()
                .map(resourceLogMapper::toDto)
                .collect(Collectors.toList());
    }

    // Получить всю историю расхода/пополнения на конкретном корабле
    public List<ResourceLogResponseDto> getLogsBySpacecraftId(Integer spacecraftId) {
        return resourceLogDao.findBySpacecraftId(spacecraftId).stream()
                .map(resourceLogMapper::toDto)
                .collect(Collectors.toList());
    }
}