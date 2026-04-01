package com.example.space.data.dto.resource;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class ResourceResponseDto {
 private Integer id;

 private Integer spacecraftId;

 private Integer resourceTypeId;

 private BigDecimal currentQuantity;

 private BigDecimal maxCapacity;

 private String unit;

 @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
 private LocalDateTime lastUpdated;
}
