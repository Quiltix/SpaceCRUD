package com.example.space.data.dto.mission;

import com.example.space.data.enums.MissionStatus;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class MissionCreateDto {
 @NotBlank(message = "Name is mandatory")
 @Size(max =100)
 private String name;

 private Integer spacecraftId;

 @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
 private LocalDateTime startDate;

 @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
 private LocalDateTime endDate;

 private MissionStatus missionStatus; // Если null, сервис поставит дефолт

 private String objectives;
}
