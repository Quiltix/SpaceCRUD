package com.example.space.dao;


import com.example.space.data.model.ResourceLog;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public class ResourceLogDaoImpl implements ResourceLogDao {

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert simpleJdbcInsert;

    // Внедряем SimpleJdbcInsert сразу с указанием таблицы и ключа
    public ResourceLogDaoImpl(JdbcTemplate jdbcTemplate1, JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate1;
        this.simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("resource_logs")
                .usingGeneratedKeyColumns("id");
    }

    private final RowMapper<ResourceLog> rowMapper = (rs, rowNum) -> {
        ResourceLog log = new ResourceLog();
        log.setId(rs.getLong("id"));
        log.setSpacecraftId(rs.getInt("spacecraft_id"));
        log.setResourceId(rs.getInt("resource_id"));
        log.setQuantityChange(rs.getBigDecimal("quantity_change"));

        if (rs.getTimestamp("timestamp") != null) {
            log.setTimestamp(rs.getTimestamp("timestamp").toLocalDateTime());
        }
        return log;
    };

    @Override
    public ResourceLog save(ResourceLog log) {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("spacecraft_id", log.getSpacecraftId());
        parameters.put("resource_id", log.getResourceId());
        parameters.put("quantity_change", log.getQuantityChange());
        parameters.put("timestamp", log.getTimestamp());

        Number generatedId = simpleJdbcInsert.executeAndReturnKey(parameters);
        log.setId(generatedId.longValue());
        return log;
    }

    @Override
    public Optional<ResourceLog> findById(Long id) {
        String sql = "SELECT * FROM resource_logs WHERE id = ?";
        try {
            ResourceLog log = jdbcTemplate.queryForObject(sql, rowMapper, id);
            return Optional.ofNullable(log);
        } catch (org.springframework.dao.EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public List<ResourceLog> findAll() {
        String sql = "SELECT * FROM resource_logs ORDER BY timestamp DESC"; // Сортируем от новых к старым
        return jdbcTemplate.query(sql, rowMapper);
    }

    @Override
    public List<ResourceLog> findByResourceId(Integer resourceId) {
        String sql = "SELECT * FROM resource_logs WHERE resource_id = ? ORDER BY timestamp DESC";
        return jdbcTemplate.query(sql, rowMapper, resourceId);
    }

    @Override
    public List<ResourceLog> findBySpacecraftId(Integer spacecraftId) {
        String sql = "SELECT * FROM resource_logs WHERE spacecraft_id = ? ORDER BY timestamp DESC";
        return jdbcTemplate.query(sql, rowMapper, spacecraftId);
    }
}