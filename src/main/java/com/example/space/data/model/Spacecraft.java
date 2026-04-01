package com.example.space.data.model;

import com.example.space.data.enums.SpacecraftStatus;
import lombok.Data;

import java.time.LocalDate;

@Data
public class Spacecraft {
    private Integer id;
    private String name;
    private String model;
    private LocalDate launchDate;
    private SpacecraftStatus spacecraftStatus;
    private String specifications;
    private String currentLocation;
}
