package com.example.space.controller;

import com.example.space.data.dto.crewmember.CrewMemberCreateDto;
import com.example.space.data.dto.crewmember.CrewMemberResponseDto;
import com.example.space.data.dto.crewmember.CrewMemberUpdateDto;
import com.example.space.data.enums.HealthStatus;
import com.example.space.service.CrewMemberService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/crew-members")
@RequiredArgsConstructor
public class CrewMemberController {

    private final CrewMemberService crewMemberService;

    // 1. CREATE (POST)
    @PostMapping
    public ResponseEntity<CrewMemberResponseDto> createCrewMember(
            @Valid @RequestBody CrewMemberCreateDto createDto) {
        CrewMemberResponseDto createdMember = crewMemberService.createCrewMember(createDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdMember);
    }

    // 2. READ ALL (GET)
    @GetMapping
    public ResponseEntity<List<CrewMemberResponseDto>> getAllCrewMembers(
            @RequestParam(required = false) HealthStatus status,
            @RequestParam(required = false) String search
            ) {
        List<CrewMemberResponseDto> list = crewMemberService.getAllCrewMembers(search, status);
        return ResponseEntity.ok(list);
    }

    // 3. READ ONE (GET by ID)
    @GetMapping("/{id}")
    public ResponseEntity<CrewMemberResponseDto> getCrewMemberById(@PathVariable Integer id) {
        CrewMemberResponseDto member = crewMemberService.getCrewMemberById(id);
        return ResponseEntity.ok(member);
    }

    // 4. UPDATE (PUT)
    @PutMapping("/{id}")
    public ResponseEntity<CrewMemberResponseDto> updateCrewMember(
            @PathVariable Integer id,
            @Valid @RequestBody CrewMemberUpdateDto updateDto) {
        CrewMemberResponseDto updatedMember = crewMemberService.updateCrewMember(id, updateDto);
        return ResponseEntity.ok(updatedMember);
    }

    // 5. DELETE (DELETE)
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCrewMember(@PathVariable Integer id) {
        crewMemberService.deleteCrewMember(id);
        return ResponseEntity.noContent().build();
    }
}