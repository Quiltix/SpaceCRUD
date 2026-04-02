package com.example.space.controller;


import com.example.space.data.dto.crew.MissionCrewCreateDto;
import com.example.space.data.dto.crew.MissionCrewMemberResponseDto;
import com.example.space.data.dto.crew.MissionCrewUpdateDto;
import com.example.space.service.MissionCrewService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/missions/{missionId}/crew")
@RequiredArgsConstructor
public class MissionCrewController {

    private final MissionCrewService missionCrewService;

    // Добавить члена экипажа в миссию
    @PostMapping
    public ResponseEntity<Void> addMemberToMission(
            @PathVariable Integer missionId,
            @Valid @RequestBody MissionCrewCreateDto createDto) {
        missionCrewService.addMemberToMission(missionId, createDto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping
    public ResponseEntity<List<MissionCrewMemberResponseDto>> getCrewForMission(
            @PathVariable Integer missionId) {
        List<MissionCrewMemberResponseDto> crew = missionCrewService.getCrewByMissionId(missionId);
        return ResponseEntity.ok(crew);
    }

    @PutMapping("/{memberId}")
    public ResponseEntity<Void> updateMemberRole(
            @PathVariable Integer missionId,
            @PathVariable Integer memberId,
            @Valid @RequestBody MissionCrewUpdateDto updateDto) {
        missionCrewService.updateMemberRoleInMission(missionId, memberId, updateDto);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{memberId}")
    public ResponseEntity<Void> removeMemberFromMission(
            @PathVariable Integer missionId,
            @PathVariable Integer memberId) {
        missionCrewService.removeMemberFromMission(missionId, memberId);
        return ResponseEntity.noContent().build();
    }
}