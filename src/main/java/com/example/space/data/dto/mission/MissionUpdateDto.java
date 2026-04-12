package com.example.space.data.dto.mission;

import com.example.space.config.ApiDateTimeFormat;
import com.example.space.data.enums.MissionStatus;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class MissionUpdateDto {
    // При обновлении обычно разрешают менять статус, даты, цели, но не ID

    private String name;

    private Integer spacecraftId;

    @Schema(type = "string", pattern = ApiDateTimeFormat.REGEX, example = ApiDateTimeFormat.EXAMPLE)
    @JsonFormat(pattern = ApiDateTimeFormat.PATTERN)
    private LocalDateTime startDate;

    @Schema(type = "string", pattern = ApiDateTimeFormat.REGEX, example = ApiDateTimeFormat.EXAMPLE)
    @JsonFormat(pattern = ApiDateTimeFormat.PATTERN)
    private LocalDateTime endDate;

    private MissionStatus missionStatus;

    private String objectives;
}
