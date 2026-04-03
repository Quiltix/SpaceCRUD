package com.example.space.mapper;

import com.example.space.data.dto.experiment.ExperimentCreateDto;
import com.example.space.data.dto.experiment.ExperimentResponseDto;
import com.example.space.data.dto.experiment.ExperimentUpdateDto;
import com.example.space.data.enums.ExperimentStatus;
import com.example.space.data.model.Experiment;
import org.springframework.stereotype.Component;

@Component
public class ExperimentMapper {

    public Experiment toEntity(ExperimentCreateDto dto) {
        Experiment entity = new Experiment();
        entity.setMissionId(dto.getMissionId());
        entity.setName(dto.getName());
        entity.setDescription(dto.getDescription());

        // Дефолтный статус, если не указан
        entity.setExperimentStatus(dto.getExperimentStatus() != null
                ? dto.getExperimentStatus()
                : ExperimentStatus.PLANNED);

        entity.setResponsibleMemberId(dto.getResponsibleMemberId());
        entity.setStartTime(dto.getStartTime());
        entity.setEndTime(dto.getEndTime());
        return entity;
    }

    public void updateEntityFromDto(ExperimentUpdateDto dto, Experiment entity) {
        if (dto.getName() != null) entity.setName(dto.getName());
        if (dto.getMissionId() != null) entity.setMissionId(dto.getMissionId());
        if (dto.getDescription() != null) entity.setDescription(dto.getDescription());
        if (dto.getExperimentStatus() != null) entity.setExperimentStatus(dto.getExperimentStatus());
        if (dto.getResponsibleMemberId() != null) entity.setResponsibleMemberId(dto.getResponsibleMemberId());
        if (dto.getStartTime() != null) entity.setStartTime(dto.getStartTime());
        if (dto.getEndTime() != null) entity.setEndTime(dto.getEndTime());
        if (dto.getResults() != null) entity.setResults(dto.getResults());
    }

    public ExperimentResponseDto toDto(Experiment entity) {
        ExperimentResponseDto dto = new ExperimentResponseDto();
        dto.setId(entity.getId());
        dto.setMissionId(entity.getMissionId());
        dto.setName(entity.getName());
        dto.setDescription(entity.getDescription());
        dto.setExperimentStatus(entity.getExperimentStatus());
        dto.setResponsibleMemberId(entity.getResponsibleMemberId());
        dto.setStartTime(entity.getStartTime());
        dto.setEndTime(entity.getEndTime());
        dto.setResults(entity.getResults());
        return dto;
    }
}