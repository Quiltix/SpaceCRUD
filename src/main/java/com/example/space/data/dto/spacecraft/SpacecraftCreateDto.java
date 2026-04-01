package com.example.space.data.dto.spacecraft;

import com.example.space.data.enums.SpacecraftStatus;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalDate;

@Data
public class SpacecraftCreateDto {
 @NotBlank(message = "Name is mandatory")
 @Size(max =100)
 private String name;

 @Size(max =100)
 private String model;

 @JsonFormat(pattern = "yyyy-MM-dd")
 private LocalDate launchDate;

 private SpacecraftStatus spacecraftStatus; // Если null, сервис поставит дефолт "construction"

 private String specifications;

 private String currentLocation;
}
