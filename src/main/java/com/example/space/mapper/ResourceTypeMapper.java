package com.example.space.mapper;

import com.example.space.data.dto.resource.ResourceTypeCreateDto;
import com.example.space.data.dto.resource.ResourceTypeResponseDto;
import com.example.space.data.dto.resource.ResourceTypeUpdateDto;
import com.example.space.data.model.ResourceType;
import org.springframework.stereotype.Component;

@Component
public class ResourceTypeMapper {

    // Из CreateDto в Entity
    public ResourceType toEntity(ResourceTypeCreateDto dto) {
        ResourceType entity = new ResourceType();
        entity.setName(dto.getName());
        return entity;
    }

    // Обновление Entity из UpdateDto
    public void updateEntityFromDto(ResourceTypeUpdateDto dto, ResourceType entity) {
        if (dto.getName() != null) {
            entity.setName(dto.getName());
        }
    }

    // Из Entity в ResponseDto
    public ResourceTypeResponseDto toDto(ResourceType entity) {
        ResourceTypeResponseDto dto = new ResourceTypeResponseDto();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        return dto;
    }
}