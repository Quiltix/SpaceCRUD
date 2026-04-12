package com.example.space.data.dto.spacecraft;

import com.example.space.config.ApiDateFormat;
import com.example.space.data.enums.SpacecraftStatus;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDate;

@Data
public class SpacecraftUpdateDto {
    // При обновлении обычно разрешают менять статус, локацию и характеристики, но не ID

    private String model;

    private SpacecraftStatus spacecraftStatus;

    private String specifications;

    private String currentLocation;

    @Schema(type = "string", pattern = ApiDateFormat.REGEX, example = ApiDateFormat.EXAMPLE)
    @JsonFormat(pattern = ApiDateFormat.PATTERN)
    private LocalDate launchDate;
}
