package com.example.space.dao;

import com.example.space.data.model.Resource;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class ResourceDaoImpl implements ResourceDao {

    private final JdbcTemplate jdbcTemplate;

    private final RowMapper<Resource> rowMapper = (rs, rowNum) -> {
        Resource resource = new Resource();
        resource.setId(rs.getInt("id"));
        resource.setSpacecraftId(rs.getInt("spacecraft_id"));
        resource.setResourceTypeId(rs.getInt("resource_type_id"));
        resource.setCurrentQuantity(rs.getBigDecimal("current_quantity"));
        resource.setMaxCapacity(rs.getBigDecimal("max_capacity"));
        resource.setUnit(rs.getString("unit"));

        if (rs.getTimestamp("last_updated") != null) {
            resource.setLastUpdated(rs.getTimestamp("last_updated").toLocalDateTime());
        }
        return resource;
    };

    @Override
    public Resource save(Resource resource) {
        SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("resources")
                .usingGeneratedKeyColumns("id");

        Map<String, Object> parameters = new HashMap<>();
        parameters.put("spacecraft_id", resource.getSpacecraftId());
        parameters.put("resource_type_id", resource.getResourceTypeId());
        parameters.put("current_quantity", resource.getCurrentQuantity());
        parameters.put("max_capacity", resource.getMaxCapacity());
        parameters.put("unit", resource.getUnit());
        parameters.put("last_updated", resource.getLastUpdated());

        Number generatedId = simpleJdbcInsert.executeAndReturnKey(parameters);
        resource.setId(generatedId.intValue());
        return resource;
    }

    @Override
    public Optional<Resource> findById(Integer id) {
        String sql = "SELECT * FROM resources WHERE id = ?";
        try {
            Resource resource = jdbcTemplate.queryForObject(sql, rowMapper, id);
            return Optional.ofNullable(resource);
        } catch (org.springframework.dao.EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public List<Resource> findAll() {
        String sql = "SELECT * FROM resources";
        return jdbcTemplate.query(sql, rowMapper);
    }

    @Override
    public void update(Resource resource) {
        String sql = "UPDATE resources SET spacecraft_id = ?, resource_type_id = ?, current_quantity = ?, " +
                "max_capacity = ?, unit = ?, last_updated = ? WHERE id = ?";

        jdbcTemplate.update(sql,
                resource.getSpacecraftId(),
                resource.getResourceTypeId(),
                resource.getCurrentQuantity(),
                resource.getMaxCapacity(),
                resource.getUnit(),
                resource.getLastUpdated(),
                resource.getId()
        );
    }

    @Override
    public void updateQuantity(Integer id, BigDecimal newQuantity, LocalDateTime lastUpdated) {
        String sql = "UPDATE resources SET current_quantity = ?, last_updated = ? WHERE id = ?";
        jdbcTemplate.update(sql, newQuantity, lastUpdated, id);
    }

    @Override
    public void deleteById(Integer id) {
        String sql = "DELETE FROM resources WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }
}