package com.example.space.data.dto.crew;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class MissionCrewResponseDto {
    private Integer missionId;

    private Integer memberId;

    private String roleInMission;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime joinDate;
}
