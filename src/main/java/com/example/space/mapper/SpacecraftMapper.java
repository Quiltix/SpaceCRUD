package com.example.space.mapper;

import com.example.space.data.dto.spacecraft.SpacecraftCreateDto;
import com.example.space.data.dto.spacecraft.SpacecraftResponseDto;
import com.example.space.data.dto.spacecraft.SpacecraftUpdateDto;
import com.example.space.data.enums.SpacecraftStatus;
import com.example.space.data.model.Spacecraft;
import org.springframework.stereotype.Component;

@Component
public class SpacecraftMapper {

    public Spacecraft toEntity(SpacecraftCreateDto dto) {
        Spacecraft entity = new Spacecraft();
        entity.setName(dto.getName());
        entity.setModel(dto.getModel());
        entity.setLaunchDate(dto.getLaunchDate());
        entity.setSpacecraftStatus(dto.getSpacecraftStatus() != null ? dto.getSpacecraftStatus() : SpacecraftStatus.STANDBY);
        entity.setSpecifications(dto.getSpecifications());
        entity.setCurrentLocation(dto.getCurrentLocation());
        return entity;
    }

    // Обновление Entity из UpdateDto
    public void updateEntityFromDto(SpacecraftUpdateDto dto, Spacecraft entity) {
        if (dto.getModel() != null) entity.setModel(dto.getModel());
        if (dto.getSpacecraftStatus() != null) entity.setSpacecraftStatus(dto.getSpacecraftStatus());
        if (dto.getSpecifications() != null) entity.setSpecifications(dto.getSpecifications());
        if (dto.getCurrentLocation() != null) entity.setCurrentLocation(dto.getCurrentLocation());
        if (dto.getLaunchDate() != null) entity.setLaunchDate(dto.getLaunchDate());
    }

    // Из Entity в ResponseDto
    public SpacecraftResponseDto toDto(Spacecraft entity) {
        SpacecraftResponseDto dto = new SpacecraftResponseDto();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setModel(entity.getModel());
        dto.setLaunchDate(entity.getLaunchDate());
        dto.setSpacecraftStatus(entity.getSpacecraftStatus());
        dto.setSpecifications(entity.getSpecifications());
        dto.setCurrentLocation(entity.getCurrentLocation());
        return dto;
    }
}