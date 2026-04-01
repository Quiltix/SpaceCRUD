package com.example.space.data.dto.experiment;

import com.example.space.data.enums.ExperimentStatus;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ExperimentResponseDto {
 private Integer id;

 private String name;

 private Integer missionId;

 private String description;

 private ExperimentStatus experimentStatus;

 private Integer responsibleMemberId;

 @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
 private LocalDateTime startTime;

 @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
 private LocalDateTime endTime;

 private String results;
}
