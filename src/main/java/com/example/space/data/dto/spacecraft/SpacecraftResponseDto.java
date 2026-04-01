package com.example.space.data.dto.spacecraft;

import com.example.space.data.enums.SpacecraftStatus;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDate;

@Data
public class SpacecraftResponseDto {
 private Integer id;

 private String name;

 private String model;

 @JsonFormat(pattern = "yyyy-MM-dd")
 private LocalDate launchDate;

 private SpacecraftStatus spacecraftStatus;

 private String specifications;

 private String currentLocation;
}
