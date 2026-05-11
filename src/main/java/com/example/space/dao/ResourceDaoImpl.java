package com.example.space.dao;

import com.example.space.data.dto.resource.SpacecraftConsumptionDto;
import com.example.space.data.dto.resource.SpacecraftResourceStatusDto;
import com.example.space.data.model.Resource;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

@Repository
@RequiredArgsConstructor
public class ResourceDaoImpl implements ResourceDao {

    private final JdbcTemplate jdbcTemplate;

    private final RowMapper<Resource> rowMapper = (rs, rowNum) -> {
        Resource resource = new Resource();
        resource.setId(rs.getInt("id"));
        resource.setSpacecraftId(rs.getInt("spacecraft_id"));
        resource.setResourceTypeId(rs.getInt("resource_type_id"));
        resource.setName(rs.getString("name"));
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
        parameters.put("name", resource.getName());
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
    public List<Resource> findAll(BigDecimal maxCurrentQuantity, Integer resourceTypeId, Integer spacecraftId, LocalDate lastUpdated) {
        StringBuilder sql = new StringBuilder("SELECT * FROM resources");
        List<Object> params = new ArrayList<>();
        boolean hasWhere = false;

        if (maxCurrentQuantity != null) {
            sql.append(" WHERE current_quantity <= ?");
            params.add(maxCurrentQuantity);
            hasWhere = true;
        }

        if (resourceTypeId != null) {
            sql.append(hasWhere ? " AND " : " WHERE ");
            sql.append("resource_type_id = ?");
            params.add(resourceTypeId);
            hasWhere = true;
        }

        if (spacecraftId != null) {
            sql.append(hasWhere ? " AND " : " WHERE ");
            sql.append("spacecraft_id = ?");
            params.add(spacecraftId);
            hasWhere = true;
        }

        if (lastUpdated != null) {
            sql.append(hasWhere ? " AND " : " WHERE ");
            sql.append("cast(last_updated as date) = ?");
            params.add(lastUpdated);
        }

        return jdbcTemplate.query(sql.toString(), rowMapper, params.toArray());
    }

    @Override
    public void update(Resource resource) {
        String sql = "UPDATE resources SET spacecraft_id = ?, resource_type_id = ?, name = ?, " +
                "max_capacity = ?, unit = ?, last_updated = ? WHERE id = ?";

        jdbcTemplate.update(sql,
                resource.getSpacecraftId(),
                resource.getResourceTypeId(),
                resource.getName(),
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

    @Override
    public List<SpacecraftResourceStatusDto> findSpacecraftResourceStatus() {
        String sql = "SELECT * FROM v_spacecraft_resource_status";
        RowMapper<SpacecraftResourceStatusDto> rowMapper = (rs, rowNum) -> {
            SpacecraftResourceStatusDto dto = new SpacecraftResourceStatusDto();
            dto.setSpacecraftName(rs.getString("spacecraft_name"));
            dto.setSpacecraftStatus(rs.getString("spacecraft_status"));
            dto.setResourceType(rs.getString("resource_type"));
            dto.setCurrentQuantity(rs.getBigDecimal("current_quantity"));
            dto.setMaxCapacity(rs.getBigDecimal("max_capacity"));
            dto.setFillPercentage(rs.getBigDecimal("fill_percentage"));
            dto.setUnit(rs.getString("unit"));
            if (rs.getTimestamp("last_updated") != null) {
                dto.setLastUpdated(rs.getTimestamp("last_updated").toLocalDateTime());
            }
            return dto;
        };
        return jdbcTemplate.query(sql, rowMapper);
    }

    @Override
    public List<SpacecraftConsumptionDto> findConsumptionLast24h() {
        String sql = """
            WITH recent_logs AS (
                SELECT spacecraft_id, quantity_change, timestamp
                FROM resource_logs
                WHERE timestamp >= NOW() - INTERVAL '24 hours'
            ),
            consumption_rate AS (
                SELECT spacecraft_id, SUM(quantity_change) AS total_consumed
                FROM recent_logs
                WHERE quantity_change < 0
                GROUP BY spacecraft_id
            )
            SELECT
                s.name AS spacecraft_name,
                s.current_location,
                COALESCE(cr.total_consumed, 0) AS consumed_last_24h,
                CASE
                    WHEN cr.total_consumed < -100 THEN 'КРИТИЧЕСКИЙ'
                    WHEN cr.total_consumed < -50 THEN 'ВЫСОКИЙ'
                    ELSE 'НОРМА'
                END AS consumption_status
            FROM spacecraft s
            LEFT JOIN consumption_rate cr ON s.id = cr.spacecraft_id
            """;
        RowMapper<SpacecraftConsumptionDto> rowMapper = (rs, rowNum) -> {
            SpacecraftConsumptionDto dto = new SpacecraftConsumptionDto();
            dto.setSpacecraftName(rs.getString("spacecraft_name"));
            dto.setCurrentLocation(rs.getString("current_location"));
            dto.setConsumedLast24h(rs.getBigDecimal("consumed_last_24h"));
            dto.setConsumptionStatus(rs.getString("consumption_status"));
            return dto;
        };
        return jdbcTemplate.query(sql, rowMapper);
    }

    @Override
    public void consumeResourceById(Integer resourceId, BigDecimal amount) {
        String sql = """
            SELECT r.spacecraft_id, rt.name as resource_type_name
            FROM resources r
            JOIN resource_types rt ON r.resource_type_id = rt.id
            WHERE r.id = ?
            """;
        try {
            Map<String, Object> result = jdbcTemplate.queryForMap(sql, resourceId);
            Integer spacecraftId = (Integer) result.get("spacecraft_id");
            String resourceTypeName = (String) result.get("resource_type_name");
            jdbcTemplate.update("CALL consume_resource(?, ?, ?)", spacecraftId, resourceTypeName, amount);
        } catch (org.springframework.dao.EmptyResultDataAccessException e) {
            throw new IllegalArgumentException("Resource not found with id: " + resourceId);
        }
    }
}