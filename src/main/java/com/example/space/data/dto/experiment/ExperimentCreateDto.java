package com.example.space.data.dto.experiment;

import com.example.space.data.enums.ExperimentStatus;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ExperimentCreateDto {
 @NotBlank(message = "Name is mandatory")
 @Size(max =100)
 private String name;

 private Integer missionId;

 @Size(max =500)
 private String description;

 private ExperimentStatus experimentStatus; // Если null, сервис поставит дефолт

 private Integer responsibleMemberId;

 @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
 private LocalDateTime startTime;

 @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
 private LocalDateTime endTime;
}
