package com.example.space.dao;

import com.example.space.data.enums.MissionStatus;
import com.example.space.data.enums.SpacecraftStatus;
import com.example.space.data.model.Mission;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class MissionDaoImpl implements MissionDao {

    private final JdbcTemplate jdbcTemplate;

    private final RowMapper<Mission> rowMapper = new RowMapper<>() {
        @Override
        public Mission mapRow(ResultSet rs, int rowNum) throws SQLException {
            Mission mission = new Mission();
            mission.setId(rs.getInt("id"));
            mission.setSpacecraftId(rs.getInt("spacecraft_id"));
            mission.setName(rs.getString("name"));

            // Преобразование TIMESTAMPTZ в LocalDateTime
            mission.setStartDate(rs.getTimestamp("start_date").toLocalDateTime());

            // Обработка endDate (может быть null)
            Timestamp endDateTs = rs.getTimestamp("end_date");
            if (endDateTs != null) {
                mission.setEndDate(endDateTs.toLocalDateTime());
            }

            // Преобразование String в Enum
            String statusStr = rs.getString("mission_status");
            if (statusStr != null) {
                mission.setMissionStatus(MissionStatus.valueOf(statusStr));
            }

            mission.setObjectives(rs.getString("objectives"));
            return mission;
        }
    };

    @Override
    public Mission save(Mission mission) {
        SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("missions")
                .usingGeneratedKeyColumns("id");

        Map<String, Object> parameters = new HashMap<>();
        parameters.put("spacecraft_id", mission.getSpacecraftId());
        parameters.put("name", mission.getName());
        parameters.put("start_date", mission.getStartDate());
        parameters.put("end_date", mission.getEndDate());
        parameters.put("mission_status", mission.getMissionStatus() != null
                ? mission.getMissionStatus().name()
                : MissionStatus.PLANNING.name());
        parameters.put("objectives", mission.getObjectives());

        Number generatedId = simpleJdbcInsert.executeAndReturnKey(parameters);
        mission.setId(generatedId.intValue());

        return mission;
    }

    @Override
    public Optional<Mission> findById(Integer id) {
        String sql = "SELECT * FROM missions WHERE id = ?";
        try {
            Mission mission = jdbcTemplate.queryForObject(sql, rowMapper, id);
            return Optional.ofNullable(mission);
        } catch (org.springframework.dao.EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public List<Mission> findAll() {
        String sql = "SELECT * FROM missions";
        return jdbcTemplate.query(sql, rowMapper);
    }

    @Override
    public boolean existsByNameAndSpacecraftId(String name, Integer spacecraftId) {
        String sql = "SELECT COUNT(*) FROM missions WHERE name = ? AND spacecraft_id = ?";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, name, spacecraftId);
        return count != null && count > 0;
    }

    @Override
    public void update(Mission mission) {
        String sql = "UPDATE missions SET name = ?, start_date = ?, end_date = ?, mission_status = ?, objectives = ? WHERE id = ?";

        jdbcTemplate.update(sql,
                mission.getName(),
                mission.getStartDate(),
                mission.getEndDate(),
                mission.getMissionStatus().name(),
                mission.getObjectives(),
                mission.getId()
        );
    }

    @Override
    public void deleteById(Integer id) {
        String sql = "DELETE FROM missions WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }
}