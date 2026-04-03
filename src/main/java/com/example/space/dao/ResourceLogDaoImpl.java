package com.example.space.dao;


import com.example.space.data.model.ResourceLog;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

@Repository
@RequiredArgsConstructor
public class ResourceLogDaoImpl implements ResourceLogDao {

    private final SimpleJdbcInsert simpleJdbcInsert;

    // Внедряем SimpleJdbcInsert сразу с указанием таблицы и ключа
    public ResourceLogDaoImpl(JdbcTemplate jdbcTemplate) {
        this.simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("resource_logs")
                .usingGeneratedKeyColumns("id");
    }

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
}