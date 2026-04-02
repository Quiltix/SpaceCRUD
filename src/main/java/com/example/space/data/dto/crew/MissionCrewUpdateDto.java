package com.example.space.data.dto.crew;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class MissionCrewUpdateDto {

    @NotBlank(message = "Role in mission cannot be blank")
    private String roleInMission;
}