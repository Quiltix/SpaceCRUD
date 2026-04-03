package com.example.space.dao;

import com.example.space.data.model.ResourceLog;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface ResourceLogDao {
    ResourceLog save(ResourceLog log);

    Optional<ResourceLog> findById(Long id);

    List<ResourceLog> findAll(Integer spacecraftId, Integer resourceId, LocalDate startDate, LocalDate endDate);

    // Дополнительные методы для фильтрации (очень полезно для логов)
    List<ResourceLog> findByResourceId(Integer resourceId);

    List<ResourceLog> findBySpacecraftId(Integer spacecraftId);
}