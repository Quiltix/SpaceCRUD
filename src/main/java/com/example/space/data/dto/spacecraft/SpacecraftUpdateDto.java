package com.example.space.data.dto.spacecraft;

import com.example.space.data.enums.SpacecraftStatus;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDate;

@Data
public class SpacecraftUpdateDto {
 // При обновлении обычно разрешают менять статус, локацию и характеристики, но не ID

 private String model;

 private SpacecraftStatus spacecraftStatus;

 private String specifications;

 private String currentLocation;

 @JsonFormat(pattern = "yyyy-MM-dd")
 private LocalDate launchDate;
}
