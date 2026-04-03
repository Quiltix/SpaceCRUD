package com.example.space.data.dto.resource;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class ResourceLogResponseDto {
    private Long id;
    private Integer spacecraftId;
    private Integer resourceId;
    private BigDecimal quantityChange;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime timestamp;
}