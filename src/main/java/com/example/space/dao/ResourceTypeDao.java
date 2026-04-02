package com.example.space.dao;

import com.example.space.data.model.ResourceType;

import java.util.List;
import java.util.Optional;

public interface ResourceTypeDao {
    ResourceType save(ResourceType resourceType);

    Optional<ResourceType> findById(Integer id);

    List<ResourceType> findAll();

    boolean existsByName(String name);

    Optional<ResourceType> findByName(String name); // Полезен для проверки уникальности

    void update(ResourceType resourceType);

    void deleteById(Integer id);
}