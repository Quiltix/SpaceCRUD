package com.example.space.dao;

import com.example.space.data.enums.ExperimentStatus;
import com.example.space.data.model.Experiment;

import java.util.List;
import java.util.Optional;

public interface ExperimentDao {
    Experiment save(Experiment experiment);

    Optional<Experiment> findById(Integer id);

    List<Experiment> findAll(String search, Integer missionId, ExperimentStatus status, Integer responsibleMemberId);

    void update(Experiment experiment);

    void deleteById(Integer id);
}