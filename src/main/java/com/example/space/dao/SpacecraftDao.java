package com.example.space.dao;

import com.example.space.data.enums.SpacecraftStatus;
import com.example.space.data.model.Spacecraft;

import java.util.List;
import java.util.Optional;

public interface SpacecraftDao {
    // Создание
    Spacecraft save(Spacecraft spacecraft);

    // Чтение
    Optional<Spacecraft> findById(Integer id);

    List<Spacecraft> findAll(SpacecraftStatus status);

    boolean existsByName(String name);

    void update(Spacecraft spacecraft);

    void deleteById(Integer id);
}