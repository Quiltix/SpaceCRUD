package com.example.space.service;


import com.example.space.dao.SpacecraftDao;
import com.example.space.data.dto.spacecraft.SpacecraftCreateDto;
import com.example.space.data.dto.spacecraft.SpacecraftResponseDto;
import com.example.space.data.dto.spacecraft.SpacecraftUpdateDto;
import com.example.space.data.model.Spacecraft;
import com.example.space.exception.error.EntityNotFoundException;
import com.example.space.mapper.SpacecraftMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SpacecraftService {

    private final SpacecraftDao spacecraftDao;
    private final SpacecraftMapper spacecraftMapper;

    @Transactional
    public SpacecraftResponseDto createSpacecraft(SpacecraftCreateDto createDto) {
        // 1. Бизнес-логика: Проверка уникальности имени
        if (spacecraftDao.existsByName(createDto.getName())) {
            throw new IllegalArgumentException("Spacecraft with name '" + createDto.getName() + "' already exists");
        }

        Spacecraft entity = spacecraftMapper.toEntity(createDto);

        Spacecraft savedEntity = spacecraftDao.save(entity);

        return spacecraftMapper.toDto(savedEntity);
    }

    public SpacecraftResponseDto getSpacecraftById(Integer id) {
        Spacecraft entity = spacecraftDao.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Spacecraft not found with id: " + id));
        return spacecraftMapper.toDto(entity);
    }

    public List<SpacecraftResponseDto> getAllSpacecrafts() {
        return spacecraftDao.findAll().stream()
                .map(spacecraftMapper::toDto)
                .collect(Collectors.toList());
    }

    @Transactional
    public SpacecraftResponseDto updateSpacecraft(Integer id, SpacecraftUpdateDto updateDto) {
        Spacecraft existingEntity = spacecraftDao.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Spacecraft not found with id: " + id));

        spacecraftMapper.updateEntityFromDto(updateDto, existingEntity);

        spacecraftDao.update(existingEntity);

        return spacecraftMapper.toDto(existingEntity);
    }

    @Transactional
    public void deleteSpacecraft(Integer id) {
        spacecraftDao.deleteById(id);
    }

    public Spacecraft getSpacecraftEntityById(Integer id) {
        return spacecraftDao.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Spacecraft not found with id: " + id));
    }
}