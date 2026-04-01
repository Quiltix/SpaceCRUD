package com.example.space.data.dto.resource;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class ResourceCreateDto {
 @NotNull(message = "Spacecraft ID is mandatory")
 private Integer spacecraftId;

 @NotNull(message = "Resource Type ID is mandatory")
 private Integer resourceTypeId;

 @Positive(message = "Current quantity must be positive")
 private BigDecimal currentQuantity;

 @Positive(message = "Max capacity must be positive")
 private BigDecimal maxCapacity;

 @NotNull(message = "Unit is mandatory")
 private String unit;
}
