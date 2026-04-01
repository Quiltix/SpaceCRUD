package com.example.space.data.dto.crewmember;

import com.example.space.data.enums.HealthStatus;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDate;

@Data
public class CrewMemberResponseDto {
 private Integer id;

 private String firstName;

 private String lastName;

 private String specialization;

 private HealthStatus healthStatus;

 @JsonFormat(pattern = "yyyy-MM-dd")
 private LocalDate birthDate;
}
