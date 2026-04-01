package com.example.space.mapper;

import com.example.space.data.dto.crewmember.CrewMemberCreateDto;
import com.example.space.data.dto.crewmember.CrewMemberResponseDto;
import com.example.space.data.dto.crewmember.CrewMemberUpdateDto;
import com.example.space.data.enums.HealthStatus;
import com.example.space.data.model.CrewMember;
import org.springframework.stereotype.Component;

@Component
public class CrewMemberMapper {

 public CrewMember toEntity(CrewMemberCreateDto dto) {
 CrewMember entity = new CrewMember();
 entity.setFirstName(dto.getFirstName());
 entity.setLastName(dto.getLastName());
 entity.setSpecialization(dto.getSpecialization());
 entity.setHealthStatus(dto.getHealthStatus() != null ? dto.getHealthStatus() : HealthStatus.HEALTHY);
 entity.setBirthDate(dto.getBirthDate());
 return entity;
 }

 // Обновление Entity из UpdateDto
 public void updateEntityFromDto(CrewMemberUpdateDto dto, CrewMember entity) {
 if (dto.getFirstName() != null) entity.setFirstName(dto.getFirstName());
 if (dto.getLastName() != null) entity.setLastName(dto.getLastName());
 if (dto.getSpecialization() != null) entity.setSpecialization(dto.getSpecialization());
 if (dto.getHealthStatus() != null) entity.setHealthStatus(dto.getHealthStatus());
 if (dto.getBirthDate() != null) entity.setBirthDate(dto.getBirthDate());
 }

 // Из Entity в ResponseDto
 public CrewMemberResponseDto toDto(CrewMember entity) {
 CrewMemberResponseDto dto = new CrewMemberResponseDto();
 dto.setId(entity.getId());
 dto.setFirstName(entity.getFirstName());
 dto.setLastName(entity.getLastName());
 dto.setSpecialization(entity.getSpecialization());
 dto.setHealthStatus(entity.getHealthStatus() != null ? entity.getHealthStatus() : null);
 dto.setBirthDate(entity.getBirthDate());
 return dto;
 }
}
