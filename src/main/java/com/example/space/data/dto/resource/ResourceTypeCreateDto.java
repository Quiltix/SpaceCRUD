package com.example.space.data.dto.resource;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class ResourceTypeCreateDto {

    @NotBlank(message = "Name is mandatory")
    @Size(max =100)
    private String name;
}
