package com.example.space.dao;

import com.example.space.data.dto.resource.SpacecraftConsumptionDto;
import com.example.space.data.dto.resource.SpacecraftResourceStatusDto;
import com.example.space.data.model.Resource;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface ResourceDao {
    Resource save(Resource resource);

    Optional<Resource> findById(Integer id);

    List<Resource> findAll(BigDecimal maxCurrentQuantity, Integer resourceTypeId, Integer spacecraftId, LocalDate lastUpdated);

    List<SpacecraftResourceStatusDto> findSpacecraftResourceStatus();

    List<SpacecraftConsumptionDto> findConsumptionLast24h();

    void consumeResourceById(Integer resourceId, java.math.BigDecimal amount);

    void update(Resource resource);

    void updateQuantity(Integer id, BigDecimal newQuantity, LocalDateTime lastUpdated);

    void deleteById(Integer id);
}