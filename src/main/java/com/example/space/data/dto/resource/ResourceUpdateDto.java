package com.example.space.data.dto.resource;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Positive;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class ResourceUpdateDto {
 private Integer spacecraftId;

 private Integer resourceTypeId;

 @Positive(message = "Current quantity must be positive")
 private BigDecimal currentQuantity;

 @Positive(message = "Max capacity must be positive")
 private BigDecimal maxCapacity;

 private String unit;

 @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
 private LocalDateTime lastUpdated;
}
