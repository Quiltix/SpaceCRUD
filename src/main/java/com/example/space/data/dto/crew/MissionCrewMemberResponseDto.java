package com.example.space.data.dto.crew;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class MissionCrewMemberResponseDto {
    private Integer memberId;
    private String firstName;
    private String lastName;
    private String specialization;
    private String roleInMission;
    private LocalDateTime joinDate;
}