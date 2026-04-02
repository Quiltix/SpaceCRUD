package com.example.space.mapper;

import com.example.space.data.dto.mission.MissionCreateDto;
import com.example.space.data.dto.mission.MissionResponseDto;
import com.example.space.data.dto.mission.MissionUpdateDto;
import com.example.space.data.model.Mission;
import org.springframework.stereotype.Component;

@Component
public class MissionMapper {

    // Из CreateDto в Entity
    public Mission toEntity(MissionCreateDto dto) {
        Mission entity = new Mission();
        entity.setSpacecraftId(dto.getSpacecraftId());
        entity.setName(dto.getName());
        entity.setStartDate(dto.getStartDate());
        entity.setEndDate(dto.getEndDate());
        entity.setMissionStatus(dto.getMissionStatus());
        entity.setObjectives(dto.getObjectives());
        return entity;
    }

    // Обновление Entity из UpdateDto
    public void updateEntityFromDto(MissionUpdateDto dto, Mission entity) {
        if (dto.getName() != null) entity.setName(dto.getName());
        if (dto.getStartDate() != null) entity.setStartDate(dto.getStartDate());
        if (dto.getEndDate() != null) entity.setEndDate(dto.getEndDate());
        if (dto.getMissionStatus() != null) entity.setMissionStatus(dto.getMissionStatus());
        if (dto.getObjectives() != null) entity.setObjectives(dto.getObjectives());
    }

    // Из Entity в ResponseDto
    public MissionResponseDto toDto(Mission entity) {
        MissionResponseDto dto = new MissionResponseDto();
        dto.setId(entity.getId());
        dto.setSpacecraftId(entity.getSpacecraftId());
        dto.setName(entity.getName());
        dto.setStartDate(entity.getStartDate());
        dto.setEndDate(entity.getEndDate());
        dto.setMissionStatus(entity.getMissionStatus());
        dto.setObjectives(entity.getObjectives());
        return dto;
    }
}