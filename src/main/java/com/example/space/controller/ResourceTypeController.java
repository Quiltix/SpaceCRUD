package com.example.space.controller;

import com.example.space.data.dto.resource.ResourceTypeCreateDto;
import com.example.space.data.dto.resource.ResourceTypeResponseDto;
import com.example.space.data.dto.resource.ResourceTypeUpdateDto;
import com.example.space.service.ResourceTypeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/resources/type")
@RequiredArgsConstructor
public class ResourceTypeController {

    private final ResourceTypeService resourceTypeService;

    @PostMapping
    public ResponseEntity<ResourceTypeResponseDto> createResourceType(
            @Valid @RequestBody ResourceTypeCreateDto createDto) {
        ResourceTypeResponseDto createdType = resourceTypeService.createResourceType(createDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdType);
    }

    @GetMapping
    public ResponseEntity<List<ResourceTypeResponseDto>> getAllResourceTypes(@RequestParam(required = false) String search) {
        List<ResourceTypeResponseDto> types = resourceTypeService.getAllResourceTypes(search);
        return ResponseEntity.ok(types);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResourceTypeResponseDto> getResourceTypeById(@PathVariable Integer id) {
        ResourceTypeResponseDto type = resourceTypeService.getResourceTypeById(id);
        return ResponseEntity.ok(type);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResourceTypeResponseDto> updateResourceType(
            @PathVariable Integer id,
            @Valid @RequestBody ResourceTypeUpdateDto updateDto) {
        ResourceTypeResponseDto updatedType = resourceTypeService.updateResourceType(id, updateDto);
        return ResponseEntity.ok(updatedType);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteResourceType(@PathVariable Integer id) {
        resourceTypeService.deleteResourceType(id);
        return ResponseEntity.noContent().build();
    }
}