package com.example.space.data.dto.experiment;

import com.example.space.config.ApiDateTimeFormat;
import com.example.space.data.enums.ExperimentStatus;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ExperimentUpdateDto {
    // При обновлении обычно разрешают менять статус, описание, результаты, даты, ответственного

    private String name;

    private Integer missionId;

    private String description;

    private ExperimentStatus experimentStatus;

    private Integer responsibleMemberId;

    @Schema(type = "string", pattern = ApiDateTimeFormat.REGEX, example = ApiDateTimeFormat.EXAMPLE)
    @JsonFormat(pattern = ApiDateTimeFormat.PATTERN)
    private LocalDateTime startTime;

    @Schema(type = "string", pattern = ApiDateTimeFormat.REGEX, example = ApiDateTimeFormat.EXAMPLE)
    @JsonFormat(pattern = ApiDateTimeFormat.PATTERN)
    private LocalDateTime endTime;

    private String results;
}
