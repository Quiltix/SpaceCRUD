package com.example.space.data.model;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class MissionCrew {
    private Integer missionId;
    private Integer memberId;
    private String roleInMission;
    private LocalDateTime joinDate;
}