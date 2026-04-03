package com.example.space.dao;

import com.example.space.data.enums.HealthStatus;
import com.example.space.data.model.CrewMember;

import java.util.List;
import java.util.Optional;

public interface CrewMemberDao {
    CrewMember save(CrewMember crewMember);

    Optional<CrewMember> findById(Integer id);

    List<CrewMember> findAll(String search, HealthStatus status);

    boolean existsByFirstNameAndLastName(String firstName, String lastName);

    void update(CrewMember crewMember);

    void deleteById(Integer id);
}