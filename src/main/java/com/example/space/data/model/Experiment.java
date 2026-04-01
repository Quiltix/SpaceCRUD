package com.example.space.data.model;

import com.example.space.data.enums.ExperimentStatus;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Experiment {
    private Integer id;
    private Integer missionId;
    private String name;
    private String description;
    private ExperimentStatus experimentStatus;
    private Integer responsibleMemberId;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private String results;
}