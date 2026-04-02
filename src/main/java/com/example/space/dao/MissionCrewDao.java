package com.example.space.dao;

import com.example.space.data.dto.crew.MissionCrewMemberResponseDto;
import com.example.space.data.model.MissionCrew;

import java.util.List;
import java.util.Optional;

public interface MissionCrewDao {
    void addMemberToMission(MissionCrew missionCrew);

    void removeMemberFromMission(Integer missionId, Integer memberId);

    void updateMemberInMission(MissionCrew missionCrew);

    Optional<MissionCrew> find(Integer missionId, Integer memberId);

    List<MissionCrewMemberResponseDto> findCrewDetailsByMissionId(Integer missionId);
}