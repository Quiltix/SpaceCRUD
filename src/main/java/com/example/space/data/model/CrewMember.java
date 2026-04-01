package com.example.space.data.model;

import lombok.Data;

import java.time.LocalDate;

@Data
public class CrewMember {
    private Integer id;
    private String firstName;
    private String lastName;
    private String specialization;
    private String healthStatus;
    private LocalDate birthDate;
}