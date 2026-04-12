package com.example.space.data.dto.crew;

import com.example.space.config.ApiDateTimeFormat;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class MissionCrewMemberResponseDto {
    private Integer memberId;
    private String firstName;
    private String lastName;
    private String specialization;
    private String roleInMission;
    @Schema(type = "string", pattern = ApiDateTimeFormat.REGEX, example = ApiDateTimeFormat.EXAMPLE)
    @JsonFormat(pattern = ApiDateTimeFormat.PATTERN)
    private LocalDateTime joinDate;
}
