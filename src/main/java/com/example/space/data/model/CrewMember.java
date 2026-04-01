package com.example.space.data.model;

import com.example.space.data.enums.HealthStatus;
import lombok.Data;

import java.time.LocalDate;

@Data
public class CrewMember {
    private Integer id;
    private String firstName;
    private String lastName;
    private String specialization;
    private HealthStatus healthStatus;
    private LocalDate birthDate;
}