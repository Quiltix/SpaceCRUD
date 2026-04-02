package com.example.space.mapper;

import com.example.space.data.dto.crew.MissionCrewCreateDto;
import com.example.space.data.model.MissionCrew;
import org.springframework.stereotype.Component;

@Component
public class MissionCrewMapper {

    public MissionCrew toEntity(Integer missionId, MissionCrewCreateDto dto) {
        MissionCrew entity = new MissionCrew();
        entity.setMissionId(missionId);
        entity.setMemberId(dto.getMemberId());
        entity.setRoleInMission(dto.getRoleInMission());
        return entity;
    }
}