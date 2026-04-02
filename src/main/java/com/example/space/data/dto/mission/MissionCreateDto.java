package com.example.space.data.dto.mission;


import com.example.space.data.enums.MissionStatus;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class MissionCreateDto {

    @NotNull(message = "Spacecraft ID cannot be null")
    private Integer spacecraftId;

    @NotBlank(message = "Mission name cannot be blank")
    private String name;

    @NotNull(message = "Start date cannot be null")
    @FutureOrPresent(message = "Start date must be in the present or future")
    private LocalDateTime startDate;

    private LocalDateTime endDate;

    @NotNull(message = "Mission status cannot be null")
    private MissionStatus missionStatus;

    private String objectives;
}