package com.example.space.dao;

import com.example.space.data.model.ResourceLog;

import java.util.List;
import java.util.Optional;

public interface ResourceLogDao {
    ResourceLog save(ResourceLog log);

    Optional<ResourceLog> findById(Long id);

    List<ResourceLog> findAll();

    // Дополнительные методы для фильтрации (очень полезно для логов)
    List<ResourceLog> findByResourceId(Integer resourceId);

    List<ResourceLog> findBySpacecraftId(Integer spacecraftId);
}