package com.example.space.mapper;

import com.example.space.data.dto.resource.ResourceLogResponseDto;
import com.example.space.data.model.ResourceLog;
import org.springframework.stereotype.Component;

@Component
public class ResourceLogMapper {

    public ResourceLogResponseDto toDto(ResourceLog entity) {
        ResourceLogResponseDto dto = new ResourceLogResponseDto();
        dto.setId(entity.getId());
        dto.setSpacecraftId(entity.getSpacecraftId());
        dto.setResourceId(entity.getResourceId());
        dto.setQuantityChange(entity.getQuantityChange());
        dto.setTimestamp(entity.getTimestamp());
        return dto;
    }
}