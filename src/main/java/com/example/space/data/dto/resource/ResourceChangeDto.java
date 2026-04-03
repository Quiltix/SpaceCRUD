package com.example.space.data.dto.resource;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class ResourceChangeDto {
    @NotNull(message = "Quantity change is mandatory")
    private BigDecimal quantityChange;
}