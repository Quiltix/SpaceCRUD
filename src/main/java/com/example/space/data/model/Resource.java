package com.example.space.data.model;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class Resource {
    private Integer id;
    private Integer spacecraftId;
    private Integer resourceTypeId;
    private String name;
    private BigDecimal currentQuantity;
    private BigDecimal maxCapacity;
    private String unit;
    private LocalDateTime lastUpdated;
}