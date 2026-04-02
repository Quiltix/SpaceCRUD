package com.example.space.dao;

import com.example.space.data.model.ResourceType;
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
public class ResourceTypeDaoImpl implements ResourceTypeDao {

    private final JdbcTemplate jdbcTemplate;

    private final RowMapper<ResourceType> rowMapper = (rs, rowNum) -> {
        ResourceType resourceType = new ResourceType();
        resourceType.setId(rs.getInt("id"));
        resourceType.setName(rs.getString("name"));
        return resourceType;
    };

    @Override
    public ResourceType save(ResourceType resourceType) {
        SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("resource_types")
                .usingGeneratedKeyColumns("id");

        Map<String, Object> parameters = new HashMap<>();
        parameters.put("name", resourceType.getName());

        Number generatedId = simpleJdbcInsert.executeAndReturnKey(parameters);
        resourceType.setId(generatedId.intValue());
        return resourceType;
    }

    @Override
    public Optional<ResourceType> findById(Integer id) {
        String sql = "SELECT * FROM resource_types WHERE id = ?";
        try {
            ResourceType type = jdbcTemplate.queryForObject(sql, rowMapper, id);
            return Optional.ofNullable(type);
        } catch (org.springframework.dao.EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public List<ResourceType> findAll() {
        String sql = "SELECT * FROM resource_types ORDER BY name";
        return jdbcTemplate.query(sql, rowMapper);
    }

    @Override
    public boolean existsByName(String name) {
        String sql = "SELECT COUNT(*) FROM resource_types WHERE name = ?";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, name);
        return count != null && count > 0;
    }

    @Override
    public Optional<ResourceType> findByName(String name) {
        String sql = "SELECT * FROM resource_types WHERE name = ?";
        try {
            ResourceType type = jdbcTemplate.queryForObject(sql, rowMapper, name);
            return Optional.ofNullable(type);
        } catch (org.springframework.dao.EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public void update(ResourceType resourceType) {
        String sql = "UPDATE resource_types SET name = ? WHERE id = ?";
        jdbcTemplate.update(sql, resourceType.getName(), resourceType.getId());
    }

    @Override
    public void deleteById(Integer id) {
        String sql = "DELETE FROM resource_types WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }
}