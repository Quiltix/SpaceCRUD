package com.example.space.mapper;

import com.example.space.data.dto.resource.ResourceCreateDto;
import com.example.space.data.dto.resource.ResourceResponseDto;
import com.example.space.data.dto.resource.ResourceUpdateDto;
import com.example.space.data.model.Resource;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class ResourceMapper {

 public Resource toEntity(ResourceCreateDto dto) {
  Resource entity = new Resource();
  entity.setSpacecraftId(dto.getSpacecraftId());
  entity.setResourceTypeId(dto.getResourceTypeId());
  entity.setCurrentQuantity(dto.getCurrentQuantity());
  entity.setMaxCapacity(dto.getMaxCapacity());
  entity.setUnit(dto.getUnit());
  entity.setLastUpdated(LocalDateTime.now()); // Ставим время создания
  return entity;
 }

 public void updateEntityFromDto(ResourceUpdateDto dto, Resource entity) {
  if (dto.getSpacecraftId() != null) entity.setSpacecraftId(dto.getSpacecraftId());
  if (dto.getResourceTypeId() != null) entity.setResourceTypeId(dto.getResourceTypeId());
  if (dto.getCurrentQuantity() != null) entity.setCurrentQuantity(dto.getCurrentQuantity());
  if (dto.getMaxCapacity() != null) entity.setMaxCapacity(dto.getMaxCapacity());
  if (dto.getUnit() != null) entity.setUnit(dto.getUnit());
  // lastUpdated обычно обновляется только при изменении количества,
  // но раз он есть в DTO, оставим логику:
  if (dto.getLastUpdated() != null) entity.setLastUpdated(dto.getLastUpdated());
 }

 public ResourceResponseDto toDto(Resource entity) {
  ResourceResponseDto dto = new ResourceResponseDto();
  dto.setId(entity.getId());
  dto.setSpacecraftId(entity.getSpacecraftId());
  dto.setResourceTypeId(entity.getResourceTypeId());
  dto.setCurrentQuantity(entity.getCurrentQuantity());
  dto.setMaxCapacity(entity.getMaxCapacity());
  dto.setUnit(entity.getUnit());
  dto.setLastUpdated(entity.getLastUpdated());
  return dto;
 }
}