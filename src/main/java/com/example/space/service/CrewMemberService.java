package com.example.space.service;


import com.example.space.dao.CrewMemberDao;
import com.example.space.data.dto.crewmember.CrewMemberCreateDto;
import com.example.space.data.dto.crewmember.CrewMemberResponseDto;
import com.example.space.data.dto.crewmember.CrewMemberUpdateDto;
import com.example.space.data.model.CrewMember;
import com.example.space.exception.error.EntityNotFoundException;
import com.example.space.mapper.CrewMemberMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CrewMemberService {

    private final CrewMemberDao crewMemberDao;
    private final CrewMemberMapper crewMemberMapper;

    @Transactional
    public CrewMemberResponseDto createCrewMember(CrewMemberCreateDto createDto) {
        if (crewMemberDao.existsByFirstNameAndLastName(createDto.getFirstName(), createDto.getLastName())) {
            throw new IllegalArgumentException("Crew member with name '" + createDto.getFirstName() + " " + createDto.getLastName() + "' already exists");
        }

        CrewMember entity = crewMemberMapper.toEntity(createDto);
        CrewMember savedEntity = crewMemberDao.save(entity);
        return crewMemberMapper.toDto(savedEntity);
    }

    public CrewMemberResponseDto getCrewMemberById(Integer id) {
        CrewMember entity = crewMemberDao.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Crew member not found with id: " + id));
        return crewMemberMapper.toDto(entity);
    }

    public List<CrewMemberResponseDto> getAllCrewMembers() {
        return crewMemberDao.findAll().stream()
                .map(crewMemberMapper::toDto)
                .collect(Collectors.toList());
    }

    @Transactional
    public CrewMemberResponseDto updateCrewMember(Integer id, CrewMemberUpdateDto updateDto) {
        CrewMember existingEntity = crewMemberDao.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Crew member not found with id: " + id));

        crewMemberMapper.updateEntityFromDto(updateDto, existingEntity);
        crewMemberDao.update(existingEntity);

        return crewMemberMapper.toDto(existingEntity);
    }

    @Transactional
    public void deleteCrewMember(Integer id) {
        if (crewMemberDao.findById(id).isEmpty()) {
            throw new EntityNotFoundException("Crew member not found with id: " + id);
        }
        crewMemberDao.deleteById(id);
    }
}