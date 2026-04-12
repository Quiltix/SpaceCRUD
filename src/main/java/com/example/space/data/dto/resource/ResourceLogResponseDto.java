package com.example.space.data.dto.resource;

import com.example.space.config.ApiDateTimeFormat;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class ResourceLogResponseDto {
    private Long id;
    private Integer spacecraftId;
    private Integer resourceId;
    private BigDecimal quantityChange;

    @Schema(type = "string", pattern = ApiDateTimeFormat.REGEX, example = ApiDateTimeFormat.EXAMPLE)
    @JsonFormat(pattern = ApiDateTimeFormat.PATTERN)
    private LocalDateTime timestamp;
} 
