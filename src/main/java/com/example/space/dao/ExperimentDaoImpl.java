package com.example.space.dao;

import com.example.space.data.dto.experiment.ExperimentDurationDto;
import com.example.space.data.enums.ExperimentStatus;
import com.example.space.data.model.Experiment;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

@Repository
@RequiredArgsConstructor
public class ExperimentDaoImpl implements ExperimentDao {

    private final JdbcTemplate jdbcTemplate;

    private final RowMapper<Experiment> rowMapper = new RowMapper<>() {
        @Override
        public Experiment mapRow(ResultSet rs, int rowNum) throws SQLException {
            Experiment experiment = new Experiment();
            experiment.setId(rs.getInt("id"));
            experiment.setMissionId(rs.getInt("mission_id"));
            experiment.setName(rs.getString("name"));
            experiment.setDescription(rs.getString("description"));

            String statusStr = rs.getString("experiment_status");
            if (statusStr != null) {
                experiment.setExperimentStatus(ExperimentStatus.valueOf(statusStr));
            }

            // Обработка nullable внешнего ключа
            int memberId = rs.getInt("responsible_member_id");
            if (!rs.wasNull()) {
                experiment.setResponsibleMemberId(memberId);
            }

            if (rs.getTimestamp("start_time") != null) {
                experiment.setStartTime(rs.getTimestamp("start_time").toLocalDateTime());
            }
            if (rs.getTimestamp("end_time") != null) {
                experiment.setEndTime(rs.getTimestamp("end_time").toLocalDateTime());
            }

            experiment.setResults(rs.getString("results"));
            return experiment;
        }
    };

    @Override
    public Experiment save(Experiment experiment) {
        SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("experiments")
                .usingGeneratedKeyColumns("id");

        Map<String, Object> parameters = new HashMap<>();
        parameters.put("mission_id", experiment.getMissionId());
        parameters.put("name", experiment.getName());
        parameters.put("description", experiment.getDescription());
        parameters.put("experiment_status", experiment.getExperimentStatus() != null
                ? experiment.getExperimentStatus().name() : null);
        parameters.put("responsible_member_id", experiment.getResponsibleMemberId());
        parameters.put("start_time", experiment.getStartTime());
        parameters.put("end_time", experiment.getEndTime());
        parameters.put("results", experiment.getResults());

        Number generatedId = simpleJdbcInsert.executeAndReturnKey(parameters);
        experiment.setId(generatedId.intValue());

        return experiment;
    }

    @Override
    public Optional<Experiment> findById(Integer id) {
        String sql = "SELECT * FROM experiments WHERE id = ?";
        try {
            Experiment experiment = jdbcTemplate.queryForObject(sql, rowMapper, id);
            return Optional.ofNullable(experiment);
        } catch (org.springframework.dao.EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public List<Experiment> findAll(String search, Integer missionId, ExperimentStatus status, Integer responsibleMemberId) {
        StringBuilder sql = new StringBuilder("SELECT * FROM experiments");
        List<Object> params = new ArrayList<>();
        boolean hasWhere = false;

        if (status != null) {
            sql.append(" WHERE experiment_status = ?");
            params.add(status.name());
            hasWhere = true;
        }

        if (missionId != null) {
            sql.append(hasWhere ? " AND " : " WHERE ");
            sql.append("mission_id = ?");
            params.add(missionId);
            hasWhere = true;
        }

        if (responsibleMemberId != null) {
            sql.append(hasWhere ? " AND " : " WHERE ");
            sql.append("responsible_member_id = ?");
            params.add(responsibleMemberId);
            hasWhere = true;
        }

        if (search != null && !search.trim().isEmpty()) {
            String searchPattern = "%" + search.trim() + "%";

            sql.append(hasWhere ? " AND " : " WHERE ");
            sql.append("(name ILIKE ? OR description ILIKE ? OR results ILIKE ?)");

            params.add(searchPattern);
            params.add(searchPattern);
            params.add(searchPattern);
        }

        return jdbcTemplate.query(sql.toString(), rowMapper, params.toArray());
    }

    @Override
    public void update(Experiment experiment) {
        String sql = "UPDATE experiments SET mission_id = ?, name = ?, description = ?, " +
                "experiment_status = ?, responsible_member_id = ?, start_time = ?, " +
                "end_time = ?, results = ? WHERE id = ?";

        jdbcTemplate.update(sql,
                experiment.getMissionId(),
                experiment.getName(),
                experiment.getDescription(),
                experiment.getExperimentStatus() != null ? experiment.getExperimentStatus().name() : null,
                experiment.getResponsibleMemberId(),
                experiment.getStartTime(),
                experiment.getEndTime(),
                experiment.getResults(),
                experiment.getId()
        );
    }

    @Override
    public void deleteById(Integer id) {
        String sql = "DELETE FROM experiments WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }

    @Override
    public List<ExperimentDurationDto> findTop3ByDuration() {
        String sql = """
            WITH experiment_duration AS (
                SELECT
                    mission_id,
                    name,
                    experiment_status,
                    EXTRACT(EPOCH FROM (end_time - start_time)) / 3600 AS duration_hours,
                    RANK() OVER (PARTITION BY mission_id ORDER BY (end_time - start_time) DESC) as exp_rank
                FROM experiments
                WHERE end_time IS NOT NULL AND start_time IS NOT NULL
            )
            SELECT mission_id, name, experiment_status, duration_hours, exp_rank
            FROM experiment_duration
            WHERE exp_rank <= 3
            ORDER BY mission_id, exp_rank
            """;
        RowMapper<ExperimentDurationDto> rowMapper = (rs, rowNum) -> {
            ExperimentDurationDto dto = new ExperimentDurationDto();
            dto.setMissionId(rs.getInt("mission_id"));
            dto.setName(rs.getString("name"));
            dto.setExperimentStatus(rs.getString("experiment_status"));
            dto.setDurationHours(rs.getBigDecimal("duration_hours"));
            dto.setExpRank(rs.getInt("exp_rank"));
            return dto;
        };
        return jdbcTemplate.query(sql, rowMapper);
    }
}