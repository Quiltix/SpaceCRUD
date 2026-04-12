package com.example.space.data.dto.crewmember;

import com.example.space.config.ApiDateFormat;
import com.example.space.data.enums.HealthStatus;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDate;

@Data
public class CrewMemberResponseDto {
 private Integer id;

 private String firstName;

 private String lastName;

 private String specialization;

 private HealthStatus healthStatus;

 @Schema(type = "string", pattern = ApiDateFormat.REGEX, example = ApiDateFormat.EXAMPLE)
 @JsonFormat(pattern = ApiDateFormat.PATTERN)
 private LocalDate birthDate;
}
