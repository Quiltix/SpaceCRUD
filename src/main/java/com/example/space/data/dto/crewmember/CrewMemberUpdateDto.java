package com.example.space.data.dto.crewmember;

import com.example.space.data.enums.HealthStatus;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalDate;

@Data
public class CrewMemberUpdateDto {
 @Size(max =50)
 private String firstName;

 @Size(max =50)
 private String lastName;

 @Size(max =100)
 private String specialization;

 private HealthStatus healthStatus;

 @Past(message = "Birth date must be in the past")
 @JsonFormat(pattern = "yyyy-MM-dd")
 private LocalDate birthDate;
}
