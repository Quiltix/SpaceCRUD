package com.example.space.dao;


import com.example.space.data.model.ResourceLog;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.*;

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
    public List<ResourceLog> findAll(Integer spacecraftId, Integer resourceId, LocalDate startDate, LocalDate endDate) {
        StringBuilder sql = new StringBuilder("SELECT * FROM resource_logs");
        List<Object> params = new ArrayList<>();
        boolean hasWhere = false;

        // 1. Фильтр по кораблю
        if (spacecraftId != null) {
            sql.append(" WHERE spacecraft_id = ?");
            params.add(spacecraftId);
            hasWhere = true;
        }

        // 2. Фильтр по ресурсу
        if (resourceId != null) {
            sql.append(hasWhere ? " AND " : " WHERE ");
            sql.append("resource_id = ?");
            params.add(resourceId);
            hasWhere = true;
        }

        if (startDate != null) {
            sql.append(hasWhere ? " AND " : " WHERE ");
            sql.append("timestamp >= ?");
            params.add(startDate.atStartOfDay());
            hasWhere = true;
        }

        if (endDate != null) {
            sql.append(hasWhere ? " AND " : " WHERE ");
            sql.append("timestamp <= ?");
            params.add(endDate.atTime(23, 59, 59));
        }

        sql.append(" ORDER BY timestamp DESC");

        return jdbcTemplate.query(sql.toString(), rowMapper, params.toArray());
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