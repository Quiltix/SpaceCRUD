package com.example.space.service;


import com.example.space.dao.CrewMemberDao;
import com.example.space.dao.MissionCrewDao;
import com.example.space.dao.MissionDao;
import com.example.space.data.dto.crew.MissionCrewCreateDto;
import com.example.space.data.dto.crew.MissionCrewMemberResponseDto;
import com.example.space.data.dto.crew.MissionCrewUpdateDto;
import com.example.space.data.model.MissionCrew;
import com.example.space.exception.error.EntityNotFoundException;
import com.example.space.mapper.MissionCrewMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MissionCrewService {

    private final MissionCrewDao missionCrewDao;
    private final MissionDao missionDao;
    private final CrewMemberDao crewMemberDao;
    private final MissionCrewMapper missionCrewMapper;

    @Transactional
    public void addMemberToMission(Integer missionId, MissionCrewCreateDto createDto) {
        missionDao.findById(missionId)
                .orElseThrow(() -> new EntityNotFoundException("Mission not found with id: " + missionId));
        crewMemberDao.findById(createDto.getMemberId())
                .orElseThrow(() -> new EntityNotFoundException("Crew member not found with id: " + createDto.getMemberId()));
        if (missionCrewDao.find(missionId, createDto.getMemberId()).isPresent()) {
            throw new IllegalArgumentException("Crew member " + createDto.getMemberId() + " is already in mission " + missionId);
        }

        MissionCrew missionCrew = missionCrewMapper.toEntity(missionId, createDto);
        missionCrew.setJoinDate(LocalDateTime.now()); // Устанавливаем текущую дату как дату добавления

        missionCrewDao.addMemberToMission(missionCrew);
    }

    public List<MissionCrewMemberResponseDto> getCrewByMissionId(Integer missionId) {
        missionDao.findById(missionId)
                .orElseThrow(() -> new EntityNotFoundException("Mission not found with id: " + missionId));

        return missionCrewDao.findCrewDetailsByMissionId(missionId);
    }

    @Transactional
    public void updateMemberRoleInMission(Integer missionId, Integer memberId, MissionCrewUpdateDto updateDto) {
        MissionCrew existingAssignment = missionCrewDao.find(missionId, memberId)
                .orElseThrow(() -> new EntityNotFoundException("Crew member " + memberId + " not found in mission " + missionId));

        existingAssignment.setRoleInMission(updateDto.getRoleInMission());
        missionCrewDao.updateMemberInMission(existingAssignment);
    }

    @Transactional
    public void removeMemberFromMission(Integer missionId, Integer memberId) {
        missionCrewDao.find(missionId, memberId)
                .orElseThrow(() -> new EntityNotFoundException("Crew member " + memberId + " not found in mission " + missionId));

        missionCrewDao.removeMemberFromMission(missionId, memberId);
    }
}