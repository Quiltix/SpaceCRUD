package com.example.space.data.dto.mission;

import com.example.space.data.enums.MissionStatus;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class MissionResponseDto {
 private Integer id;

 private String name;

 private Integer spacecraftId;

 @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
 private LocalDateTime startDate;

 @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
 private LocalDateTime endDate;

 private MissionStatus missionStatus;

 private String objectives;
}
