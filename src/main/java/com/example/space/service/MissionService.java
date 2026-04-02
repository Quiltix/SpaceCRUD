package com.example.space.service;

import com.example.space.dao.MissionDao;
import com.example.space.dao.SpacecraftDao;
import com.example.space.data.dto.mission.MissionCreateDto;
import com.example.space.data.dto.mission.MissionResponseDto;
import com.example.space.data.dto.mission.MissionUpdateDto;
import com.example.space.data.model.Mission;
import com.example.space.exception.error.EntityNotFoundException;
import com.example.space.mapper.MissionMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MissionService {

    private final MissionDao missionDao;
    private final MissionMapper missionMapper;
    // Добавляем DAO для Spacecraft, чтобы проверить его существование
    private final SpacecraftDao spacecraftDao;

    @Transactional
    public MissionResponseDto createMission(MissionCreateDto createDto) {
        // Бизнес-логика 1: Проверка существования корабля
        spacecraftDao.findById(createDto.getSpacecraftId())
                .orElseThrow(() -> new EntityNotFoundException("Spacecraft with id " + createDto.getSpacecraftId() + " not found"));

        // Бизнес-логика 2: Проверка на уникальность названия миссии для данного корабля
        if (missionDao.existsByNameAndSpacecraftId(createDto.getName(), createDto.getSpacecraftId())) {
            throw new IllegalArgumentException("Mission with name '" + createDto.getName() + "' already exists for this spacecraft");
        }

        Mission entity = missionMapper.toEntity(createDto);
        Mission savedEntity = missionDao.save(entity);
        return missionMapper.toDto(savedEntity);
    }

    public MissionResponseDto getMissionById(Integer id) {
        Mission entity = missionDao.findById(id)
                .orElseThrow(() -> new RuntimeException("Mission not found with id: " + id));
        return missionMapper.toDto(entity);
    }

    public List<MissionResponseDto> getAllMissions() {
        return missionDao.findAll().stream()
                .map(missionMapper::toDto)
                .collect(Collectors.toList());
    }

    @Transactional
    public MissionResponseDto updateMission(Integer id, MissionUpdateDto updateDto) {
        Mission existingEntity = missionDao.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Mission not found with id: " + id));

        missionMapper.updateEntityFromDto(updateDto, existingEntity);
        missionDao.update(existingEntity);

        return missionMapper.toDto(existingEntity);
    }

    @Transactional
    public void deleteMission(Integer id) {
        // Проверяем, существует ли миссия перед удалением
        if (missionDao.findById(id).isEmpty()) {
            throw new EntityNotFoundException("Mission not found with id: " + id);
        }
        missionDao.deleteById(id);
    }
}