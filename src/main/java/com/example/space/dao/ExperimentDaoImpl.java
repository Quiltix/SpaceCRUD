package com.example.space.dao;

import com.example.space.data.enums.ExperimentStatus;
import com.example.space.data.model.Experiment;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

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
    public List<Experiment> findAll() {
        String sql = "SELECT * FROM experiments";
        return jdbcTemplate.query(sql, rowMapper);
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
}