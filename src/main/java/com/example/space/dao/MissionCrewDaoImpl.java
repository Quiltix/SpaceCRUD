package com.example.space.dao;


import com.example.space.data.dto.crew.MissionCrewMemberResponseDto;
import com.example.space.data.model.MissionCrew;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class MissionCrewDaoImpl implements MissionCrewDao {

    private final JdbcTemplate jdbcTemplate;

    @Override
    public void addMemberToMission(MissionCrew missionCrew) {
        String sql = "INSERT INTO mission_crew (mission_id, member_id, role_in_mission, join_date) VALUES (?, ?, ?, ?)";
        jdbcTemplate.update(sql,
                missionCrew.getMissionId(),
                missionCrew.getMemberId(),
                missionCrew.getRoleInMission(),
                missionCrew.getJoinDate()
        );
    }

    @Override
    public void removeMemberFromMission(Integer missionId, Integer memberId) {
        String sql = "DELETE FROM mission_crew WHERE mission_id = ? AND member_id = ?";
        jdbcTemplate.update(sql, missionId, memberId);
    }

    @Override
    public void updateMemberInMission(MissionCrew missionCrew) {
        String sql = "UPDATE mission_crew SET role_in_mission = ? WHERE mission_id = ? AND member_id = ?";
        jdbcTemplate.update(sql,
                missionCrew.getRoleInMission(),
                missionCrew.getMissionId(),
                missionCrew.getMemberId()
        );
    }

    @Override
    public Optional<MissionCrew> find(Integer missionId, Integer memberId) {
        String sql = "SELECT * FROM mission_crew WHERE mission_id = ? AND member_id = ?";
        try {
            MissionCrew missionCrew = jdbcTemplate.queryForObject(sql, (rs, rowNum) -> {
                MissionCrew mc = new MissionCrew();
                mc.setMissionId(rs.getInt("mission_id"));
                mc.setMemberId(rs.getInt("member_id"));
                mc.setRoleInMission(rs.getString("role_in_mission"));
                Timestamp joinDateTs = rs.getTimestamp("join_date");
                if (joinDateTs != null) {
                    mc.setJoinDate(joinDateTs.toLocalDateTime());
                }
                return mc;
            }, missionId, memberId);
            return Optional.ofNullable(missionCrew);
        } catch (org.springframework.dao.EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public List<MissionCrewMemberResponseDto> findCrewDetailsByMissionId(Integer missionId) {
        String sql = "SELECT " +
                "    cm.id, " +
                "    cm.first_name, " +
                "    cm.last_name, " +
                "    cm.specialization, " +
                "    mc.role_in_mission, " +
                "    mc.join_date " +
                "FROM " +
                "    crew_members cm " +
                "JOIN " +
                "    mission_crew mc ON cm.id = mc.member_id " +
                "WHERE " +
                "    mc.mission_id = ?";

        RowMapper<MissionCrewMemberResponseDto> rowMapper = (rs, rowNum) -> {
            MissionCrewMemberResponseDto dto = new MissionCrewMemberResponseDto();
            dto.setMemberId(rs.getInt("id"));
            dto.setFirstName(rs.getString("first_name"));
            dto.setLastName(rs.getString("last_name"));
            dto.setSpecialization(rs.getString("specialization"));
            dto.setRoleInMission(rs.getString("role_in_mission"));
            Timestamp joinDateTs = rs.getTimestamp("join_date");
            if (joinDateTs != null) {
                dto.setJoinDate(joinDateTs.toLocalDateTime());
            }
            return dto;
        };

        return jdbcTemplate.query(sql, rowMapper, missionId);
    }
}