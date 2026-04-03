package com.example.space.dao;

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

    void update(Resource resource);

    // Специфичный метод для обновления только количества и даты (оптимизация)
    void updateQuantity(Integer id, BigDecimal newQuantity, LocalDateTime lastUpdated);

    void deleteById(Integer id);
}