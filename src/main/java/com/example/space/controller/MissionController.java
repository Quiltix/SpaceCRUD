package com.example.space.controller;

import com.example.space.data.dto.mission.MissionCreateDto;
import com.example.space.data.dto.mission.MissionResponseDto;
import com.example.space.data.dto.mission.MissionUpdateDto;
import com.example.space.data.enums.MissionStatus;
import com.example.space.service.MissionService;
import io.swagger.v3.oas.models.security.SecurityScheme;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/missions")
@RequiredArgsConstructor
public class MissionController {

    private final MissionService missionService;
    // Mapper здесь не нужен, так как сервис уже возвращает DTO

    // 1. CREATE (POST)
    @PostMapping
    public ResponseEntity<MissionResponseDto> createMission(
            @Valid @RequestBody MissionCreateDto createDto) {
        MissionResponseDto mission = missionService.createMission(createDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(mission);
    }

    // 2. READ ALL (GET)
    @GetMapping
    public ResponseEntity<List<MissionResponseDto>> getAllMissions(
            @RequestParam(required = false) String search,
            @RequestParam(required = false) Integer spacecraftId,
            @RequestParam(required = false) MissionStatus missionStatus

    ) {
        List<MissionResponseDto> list = missionService.getAllMissions(search, spacecraftId, missionStatus);
        return ResponseEntity.ok(list);
    }

    // 3. READ ONE (GET by ID)
    @GetMapping("/{id}")
    public ResponseEntity<MissionResponseDto> getMissionById(@PathVariable Integer id) {
        MissionResponseDto mission = missionService.getMissionById(id);
        return ResponseEntity.ok(mission);
    }

    // 4. UPDATE (PUT)
    @PutMapping("/{id}")
    public ResponseEntity<MissionResponseDto> updateMission(
            @PathVariable Integer id,
            @Valid @RequestBody MissionUpdateDto updateDto) {
        MissionResponseDto updated = missionService.updateMission(id, updateDto);
        return ResponseEntity.ok(updated);
    }

    // 5. DELETE (DELETE)
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMission(@PathVariable Integer id) {
        missionService.deleteMission(id);
        return ResponseEntity.noContent().build();
    }
}