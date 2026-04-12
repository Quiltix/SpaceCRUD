package com.example.space.data.dto.spacecraft;

import com.example.space.config.ApiDateFormat;
import com.example.space.data.enums.SpacecraftStatus;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalDate;

@Data
public class SpacecraftCreateDto {
    @NotBlank(message = "Name is mandatory")
    @Size(max =100)
    private String name;

    @NotNull
    @Size(max =100)
    private String model;

    @NotNull
    @Schema(type = "string", pattern = ApiDateFormat.REGEX, example = ApiDateFormat.EXAMPLE)
    @JsonFormat(pattern = ApiDateFormat.PATTERN)
    private LocalDate launchDate;

    @NotNull
    private SpacecraftStatus spacecraftStatus; // Если null, сервис поставит дефолт "construction"

    @NotNull
    private String specifications;

    @NotNull
    private String currentLocation;
}
