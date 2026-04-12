package com.example.space.controller;

import com.example.space.config.ApiDateFormat;
import com.example.space.data.dto.resource.ResourceChangeDto;
import com.example.space.data.dto.resource.ResourceCreateDto;
import com.example.space.data.dto.resource.ResourceResponseDto;
import com.example.space.data.dto.resource.ResourceUpdateDto;
import com.example.space.service.ResourceService;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/resources")
@RequiredArgsConstructor
public class ResourceController {

    private final ResourceService resourceService;

    // 1. CREATE (POST)
    @PostMapping
    public ResponseEntity<ResourceResponseDto> createResource(
            @Valid @RequestBody ResourceCreateDto createDto) {
        ResourceResponseDto resource = resourceService.createResource(createDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(resource);
    }

    // 2. READ ALL (GET)
    @GetMapping
    public ResponseEntity<List<ResourceResponseDto>> getAllResources(
            @RequestParam(required = false)BigDecimal maxCurrentQuantity,
            @RequestParam(required = false) Integer resourceTypeId,
            @RequestParam(required = false) Integer spacecraftId,
            @RequestParam(required = false)
            @DateTimeFormat(pattern = ApiDateFormat.PATTERN)
            @Schema(type = "string", pattern = ApiDateFormat.REGEX, example = ApiDateFormat.EXAMPLE)
            LocalDate lastUpdated

            ) {
        List<ResourceResponseDto> list = resourceService.getAllResources(maxCurrentQuantity, resourceTypeId, spacecraftId, lastUpdated);
        return ResponseEntity.ok(list);
    }

    // 3. READ ONE (GET by ID)
    @GetMapping("/{id}")
    public ResponseEntity<ResourceResponseDto> getResourceById(@PathVariable Integer id) {
        ResourceResponseDto resource = resourceService.getResourceById(id);
        return ResponseEntity.ok(resource);
    }

    // 4. UPDATE (PUT) - Обновление базовых характеристик (вместимость, тип и тд)
    @PutMapping("/{id}")
    public ResponseEntity<ResourceResponseDto> updateResource(
            @PathVariable Integer id,
            @Valid @RequestBody ResourceUpdateDto updateDto) {
        ResourceResponseDto updated = resourceService.updateResource(id, updateDto);
        return ResponseEntity.ok(updated);
    }

    // 5. CHANGE QUANTITY (PATCH) - Трата или пополнение ресурса (с записью лога)
    @PatchMapping("/{id}/quantity")
    public ResponseEntity<ResourceResponseDto> changeQuantity(
            @PathVariable Integer id,
            @Valid @RequestBody ResourceChangeDto changeDto) {

        ResourceResponseDto updated = resourceService.changeQuantity(id, changeDto);
        return ResponseEntity.ok(updated);
    }

    // 6. DELETE (DELETE)
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteResource(@PathVariable Integer id) {
        resourceService.deleteResource(id);
        return ResponseEntity.noContent().build();
    }
}
