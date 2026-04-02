package com.example.space.dao;

import com.example.space.data.model.Mission;

import java.util.List;
import java.util.Optional;

public interface MissionDao {
    Mission save(Mission mission);

    Optional<Mission> findById(Integer id);

    List<Mission> findAll();

    boolean existsByNameAndSpacecraftId(String name, Integer spacecraftId);

    void update(Mission mission);

    void deleteById(Integer id);
}