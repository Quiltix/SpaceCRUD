package com.example.space.data.model;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class ResourceLog {
    private Long id;
    private Integer spacecraftId;
    private Integer resourceId;
    private BigDecimal quantityChange;
    private LocalDateTime timestamp;
}