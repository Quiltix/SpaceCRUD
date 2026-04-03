package com.example.space.controller;

import com.example.space.data.dto.experiment.ExperimentCreateDto;
import com.example.space.data.dto.experiment.ExperimentResponseDto;
import com.example.space.data.dto.experiment.ExperimentUpdateDto;
import com.example.space.mapper.ExperimentMapper;
import com.example.space.service.ExperimentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/experiments")
@RequiredArgsConstructor
public class ExperimentController {

    private final ExperimentService experimentService;

    // 1. CREATE (POST)
    @PostMapping
    public ResponseEntity<ExperimentResponseDto> createExperiment(
            @Valid @RequestBody ExperimentCreateDto createDto) {

        ExperimentResponseDto experiment = experimentService.createExperiment(createDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(experiment);
    }

    // 2. READ ALL (GET)
    @GetMapping
    public ResponseEntity<List<ExperimentResponseDto>> getAllExperiments() {
        List<ExperimentResponseDto> list = experimentService.getAllExperiments();
        return ResponseEntity.ok(list);
    }

    // 3. READ ONE (GET by ID)
    @GetMapping("/{id}")
    public ResponseEntity<ExperimentResponseDto> getExperimentById(@PathVariable Integer id) {
        ExperimentResponseDto experiment = experimentService.getExperimentById(id);
        return ResponseEntity.ok(experiment);
    }

    // 4. UPDATE (PUT)
    @PutMapping("/{id}")
    public ResponseEntity<ExperimentResponseDto> updateExperiment(
            @PathVariable Integer id,
            @Valid @RequestBody ExperimentUpdateDto updateDto) {

        ExperimentResponseDto updated = experimentService.updateExperiment(id, updateDto);
        return ResponseEntity.ok(updated);
    }

    // 5. DELETE (DELETE)
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteExperiment(@PathVariable Integer id) {
        experimentService.deleteExperiment(id);
        return ResponseEntity.noContent().build();
    }
}