package com.example.space.controller;


import com.example.space.config.ApiDateFormat;
import com.example.space.data.dto.resource.ResourceLogResponseDto;
import com.example.space.service.ResourceLogService;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/resource-logs") // Четко разделяем роуты: ресурсы - /resources, логи ресурсов - /resource-logs
@RequiredArgsConstructor
public class ResourceLogController {

    private final ResourceLogService resourceLogService;

    @GetMapping
    public ResponseEntity<List<ResourceLogResponseDto>> getAllLogs(
            @RequestParam(required = false) Integer spacecraftId,
            @RequestParam(required = false) Integer resourceId,
            @RequestParam(required = false)
            @DateTimeFormat(pattern = ApiDateFormat.PATTERN)
            @Schema(type = "string", pattern = ApiDateFormat.REGEX, example = ApiDateFormat.EXAMPLE)
            LocalDate startDate,
            @RequestParam(required = false)
            @DateTimeFormat(pattern = ApiDateFormat.PATTERN)
            @Schema(type = "string", pattern = ApiDateFormat.REGEX, example = ApiDateFormat.EXAMPLE)
            LocalDate endDate
            ) {
        List<ResourceLogResponseDto> list = resourceLogService.getAllLogs(spacecraftId, resourceId, startDate, endDate);
        return ResponseEntity.ok(list);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResourceLogResponseDto> getLogById(@PathVariable Long id) {
        ResourceLogResponseDto log = resourceLogService.getLogById(id);
        return ResponseEntity.ok(log);
    }

    @GetMapping("/resource/{resourceId}")
    public ResponseEntity<List<ResourceLogResponseDto>> getLogsByResourceId(@PathVariable Integer resourceId) {
        List<ResourceLogResponseDto> list = resourceLogService.getLogsByResourceId(resourceId);
        return ResponseEntity.ok(list);
    }

    @GetMapping("/spacecraft/{spacecraftId}")
    public ResponseEntity<List<ResourceLogResponseDto>> getLogsBySpacecraftId(@PathVariable Integer spacecraftId) {
        List<ResourceLogResponseDto> list = resourceLogService.getLogsBySpacecraftId(spacecraftId);
        return ResponseEntity.ok(list);
    }
}
