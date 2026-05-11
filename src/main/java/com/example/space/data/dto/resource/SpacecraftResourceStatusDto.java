package com.example.space.data.dto.resource;

import com.example.space.config.ApiDateTimeFormat;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class SpacecraftResourceStatusDto {
    private String spacecraftName;
    private String spacecraftStatus;
    private String resourceType;
    private BigDecimal currentQuantity;
    private BigDecimal maxCapacity;
    private BigDecimal fillPercentage;
    private String unit;

    @Schema(type = "string", pattern = ApiDateTimeFormat.REGEX, example = ApiDateTimeFormat.EXAMPLE)
    @JsonFormat(pattern = ApiDateTimeFormat.PATTERN)
    private LocalDateTime lastUpdated;
}