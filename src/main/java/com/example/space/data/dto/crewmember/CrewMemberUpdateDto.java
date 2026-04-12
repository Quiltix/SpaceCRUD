package com.example.space.data.dto.crewmember;

import com.example.space.config.ApiDateFormat;
import com.example.space.data.enums.HealthStatus;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
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
 @Schema(type = "string", pattern = ApiDateFormat.REGEX, example = ApiDateFormat.EXAMPLE)
 @JsonFormat(pattern = ApiDateFormat.PATTERN)
 private LocalDate birthDate;
}
