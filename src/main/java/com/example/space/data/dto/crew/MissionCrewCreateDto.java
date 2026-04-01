package com.example.space.data.dto.crew;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class MissionCrewCreateDto {
    @NotNull(message = "Mission ID is mandatory")
    private Integer missionId;

    @NotNull(message = "Member ID is mandatory")
    private Integer memberId;

    private String roleInMission;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime joinDate;
}
