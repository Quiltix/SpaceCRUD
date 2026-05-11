package com.example.space.data.dto.resource;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class SpacecraftConsumptionDto {
    private String spacecraftName;
    private String currentLocation;
    private BigDecimal consumedLast24h;
    private String consumptionStatus;
}