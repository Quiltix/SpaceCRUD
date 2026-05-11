package com.example.space.data.dto.experiment;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ExperimentDurationDto {
    private Integer missionId;
    private String name;
    private String experimentStatus;
    private BigDecimal durationHours;
    private Integer expRank;
}