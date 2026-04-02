package com.example.space.data.dto.crew;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class MissionCrewCreateDto {

    @NotNull(message = "Member ID cannot be null")
    private Integer memberId;

    private String roleInMission;
}