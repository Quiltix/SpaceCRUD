package com.example.space.data.model;

import com.example.space.data.enums.MissionStatus;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Mission {
    private Integer id;
    private Integer spacecraftId;
    private String name;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private MissionStatus missionStatus;
    private String objectives;
}