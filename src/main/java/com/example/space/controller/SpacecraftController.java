package com.example.space.controller;


import com.example.space.data.dto.spacecraft.SpacecraftCreateDto;
import com.example.space.data.dto.spacecraft.SpacecraftResponseDto;
import com.example.space.data.dto.spacecraft.SpacecraftUpdateDto;
import com.example.space.data.model.Spacecraft;
import com.example.space.mapper.SpacecraftMapper;
import com.example.space.service.SpacecraftService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@SuppressWarnings("ALL")
@RestController
@RequestMapping("/api/spacecraft")
@RequiredArgsConstructor // Lombok генерирует конструктор для final полей
public class SpacecraftController {

    private final SpacecraftService spacecraftService;
    private final SpacecraftMapper spacecraftMapper;

    // 1. CREATE (POST)
    @PostMapping
    public ResponseEntity<SpacecraftResponseDto> createSpacecraft(
            @Valid @RequestBody SpacecraftCreateDto createDto) {

        // Конвертируем DTO в Entity
        Spacecraft spacecraftToSave = spacecraftMapper.toEntity(createDto);

        // Сохраняем через сервис
        Spacecraft savedSpacecraft = spacecraftService.createSpacecraft(spacecraftToSave);

        // Конвертируем результат обратно в DTO
        SpacecraftResponseDto responseDto = spacecraftMapper.toDto(savedSpacecraft);

        // Возвращаем 201 Created
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
    }

    // 2. READ ALL (GET)
    @GetMapping
    public ResponseEntity<List<SpacecraftResponseDto>> getAllSpacecrafts() {
        List<SpacecraftResponseDto> list = spacecraftService.getAllSpacecrafts()
                .stream()
                .map(spacecraftMapper::toDto) // Преобразуем каждый элемент списка
                .collect(Collectors.toList());

        return ResponseEntity.ok(list);
    }

    // 3. READ ONE (GET by ID)
    @GetMapping("/{id}")
    public ResponseEntity<SpacecraftResponseDto> getSpacecraftById(@PathVariable Integer id) {
        Spacecraft spacecraft = spacecraftService.getSpacecraftDetails(id);
        return ResponseEntity.ok(spacecraftMapper.toDto(spacecraft));
    }

    // 4. UPDATE (PUT)
    @PutMapping("/{id}")
    public ResponseEntity<SpacecraftResponseDto> updateSpacecraft(
            @PathVariable Integer id,
            @Valid @RequestBody SpacecraftUpdateDto updateDto) {

        // 1. Получаем текущую сущность
        Spacecraft existingSpacecraft = spacecraftService.getSpacecraftDetails(id);

        // 2. Обновляем поля из DTO
        spacecraftMapper.updateEntityFromDto(updateDto, existingSpacecraft);

        // 3. Сохраняем изменения
        Spacecraft updated = spacecraftService.updateSpacecraft(existingSpacecraft);

        return ResponseEntity.ok(spacecraftMapper.toDto(updated));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSpacecraft(@PathVariable Integer id) {
        spacecraftService.deleteSpacecraft(id);
        return ResponseEntity.noContent().build();
    }
}