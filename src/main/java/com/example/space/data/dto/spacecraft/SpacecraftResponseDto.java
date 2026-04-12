package com.example.space.data.dto.spacecraft;

import com.example.space.config.ApiDateFormat;
import com.example.space.data.enums.SpacecraftStatus;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDate;

@Data
public class SpacecraftResponseDto {
    private Integer id;

    private String name;

    private String model;

    @Schema(type = "string", pattern = ApiDateFormat.REGEX, example = ApiDateFormat.EXAMPLE)
    @JsonFormat(pattern = ApiDateFormat.PATTERN)
    private LocalDate launchDate;

    private SpacecraftStatus spacecraftStatus;

    private String specifications;

    private String currentLocation;
}
