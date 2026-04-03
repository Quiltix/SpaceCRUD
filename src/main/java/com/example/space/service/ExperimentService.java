package com.example.space.service;

import com.example.space.dao.ExperimentDao;
import com.example.space.data.dto.experiment.ExperimentCreateDto;
import com.example.space.data.dto.experiment.ExperimentResponseDto;
import com.example.space.data.dto.experiment.ExperimentUpdateDto;
import com.example.space.data.model.Experiment;
import com.example.space.exception.error.EntityNotFoundException;
import com.example.space.mapper.ExperimentMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ExperimentService {

    private final ExperimentDao experimentDao;
    private final ExperimentMapper experimentMapper;

    @Transactional
    public ExperimentResponseDto createExperiment(ExperimentCreateDto createDto) {
        Experiment entity = experimentMapper.toEntity(createDto);
        Experiment savedEntity = experimentDao.save(entity);
        return experimentMapper.toDto(savedEntity);
    }

    public ExperimentResponseDto getExperimentById(Integer id) {
        Experiment entity = experimentDao.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Experiment not found with id: " + id));
        return experimentMapper.toDto(entity);
    }

    public List<ExperimentResponseDto> getAllExperiments() {
        return experimentDao.findAll().stream()
                .map(experimentMapper::toDto)
                .collect(Collectors.toList());
    }

    @Transactional
    public ExperimentResponseDto updateExperiment(Integer id, ExperimentUpdateDto updateDto) {
        Experiment existingEntity = experimentDao.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Experiment not found with id: " + id));

        experimentMapper.updateEntityFromDto(updateDto, existingEntity);

        experimentDao.update(existingEntity);

        return experimentMapper.toDto(existingEntity);
    }

    @Transactional
    public void deleteExperiment(Integer id) {
        experimentDao.deleteById(id);
    }

    public Experiment getExperimentEntityById(Integer id) {
        return experimentDao.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Experiment not found with id: " + id));
    }
}