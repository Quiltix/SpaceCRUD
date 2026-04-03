package com.example.space.controller;


import com.example.space.data.dto.spacecraft.SpacecraftCreateDto;
import com.example.space.data.dto.spacecraft.SpacecraftResponseDto;
import com.example.space.data.dto.spacecraft.SpacecraftUpdateDto;
import com.example.space.data.enums.SpacecraftStatus;
import com.example.space.mapper.SpacecraftMapper;
import com.example.space.service.SpacecraftService;
import jakarta.annotation.Nullable;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/spacecrafts")
@RequiredArgsConstructor
public class SpacecraftController {

    private final SpacecraftService spacecraftService;

    // 1. CREATE (POST)
    @PostMapping
    public ResponseEntity<SpacecraftResponseDto> createSpacecraft(
            @Valid @RequestBody SpacecraftCreateDto createDto) {

        // Сохраняем через сервис
        SpacecraftResponseDto spacecraft = spacecraftService.createSpacecraft(createDto);

        // Возвращаем 201 Created
        return ResponseEntity.status(HttpStatus.CREATED).body(spacecraft);
    }

    // 2. READ ALL (GET)
    @GetMapping
    public ResponseEntity<List<SpacecraftResponseDto>> getAllSpacecrafts(@RequestParam(required = false) SpacecraftStatus status,
                                                                         @RequestParam(required = false) String search) {
        List<SpacecraftResponseDto> list = spacecraftService.getAllSpacecrafts(status, search);

        return ResponseEntity.ok(list);
    }

    // 3. READ ONE (GET by ID)
    @GetMapping("/{id}")
    public ResponseEntity<SpacecraftResponseDto> getSpacecraftById(@PathVariable Integer id) {
        SpacecraftResponseDto spacecraft = spacecraftService.getSpacecraftById(id);
        return ResponseEntity.ok(spacecraft);
    }

    @PutMapping("/{id}")
    public ResponseEntity<SpacecraftResponseDto> updateSpacecraft(
            @PathVariable Integer id,
            @Valid @RequestBody SpacecraftUpdateDto updateDto) {

        SpacecraftResponseDto updated = spacecraftService.updateSpacecraft(id, updateDto);

        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSpacecraft(@PathVariable Integer id) {
        spacecraftService.deleteSpacecraft(id);
        return ResponseEntity.noContent().build();
    }
}