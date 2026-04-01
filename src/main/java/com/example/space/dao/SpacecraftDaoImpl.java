package com.example.space.dao;


import com.example.space.data.enums.SpacecraftStatus;
import com.example.space.data.model.Spacecraft;
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
public class SpacecraftDaoImpl implements SpacecraftDao {

    private final JdbcTemplate jdbcTemplate;

    private final RowMapper<Spacecraft> rowMapper = new RowMapper<>() {
        @Override
        public Spacecraft mapRow(ResultSet rs, int rowNum) throws SQLException {
            Spacecraft spacecraft = new Spacecraft();
            spacecraft.setId(rs.getInt("id"));
            spacecraft.setName(rs.getString("name"));
            spacecraft.setModel(rs.getString("model"));

            // Обработка Date (может быть null)
            if (rs.getDate("launch_date") != null) {
                spacecraft.setLaunchDate(rs.getDate("launch_date").toLocalDate());
            }

            // Преобразование String в Enum
            String statusStr = rs.getString("spacecraft_status");
            if (statusStr != null) {
                spacecraft.setSpacecraftStatus(SpacecraftStatus.valueOf(statusStr));
            }

            spacecraft.setSpecifications(rs.getString("specifications"));
            spacecraft.setCurrentLocation(rs.getString("current_location"));
            return spacecraft;
        }
    };

    @Override
    public Spacecraft save(Spacecraft spacecraft) {
        // Используем SimpleJdbcInsert для авто-получения ID
        SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("spacecraft")
                .usingGeneratedKeyColumns("id");

        Map<String, Object> parameters = new HashMap<>();
        parameters.put("name", spacecraft.getName());
        parameters.put("model", spacecraft.getModel());
        parameters.put("launch_date", spacecraft.getLaunchDate());
        parameters.put("spacecraft_status", spacecraft.getSpacecraftStatus() != null
                ? spacecraft.getSpacecraftStatus().name()
                : SpacecraftStatus.STANDBY.name());
        parameters.put("specifications", spacecraft.getSpecifications());
        parameters.put("current_location", spacecraft.getCurrentLocation());

        Number generatedId = simpleJdbcInsert.executeAndReturnKey(parameters);
        spacecraft.setId(generatedId.intValue());

        return spacecraft;
    }

    @Override
    public Optional<Spacecraft> findById(Integer id) {
        String sql = "SELECT * FROM spacecraft WHERE id = ?";
        try {
            Spacecraft spacecraft = jdbcTemplate.queryForObject(sql, rowMapper, id);
            return Optional.ofNullable(spacecraft);
        } catch (org.springframework.dao.EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public List<Spacecraft> findAll() {
        String sql = "SELECT * FROM spacecraft";
        return jdbcTemplate.query(sql, rowMapper);
    }

    @Override
    public boolean existsByName(String name) {
        String sql = "SELECT COUNT(*) FROM spacecraft WHERE name = ?";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, name);
        return count != null && count > 0;
    }

    @Override
    public void update(Spacecraft spacecraft) {
        String sql = "UPDATE spacecraft SET model = ?, launch_date = ?, spacecraft_status = ?, " +
                "specifications = ?, current_location = ? WHERE id = ?";

        jdbcTemplate.update(sql,
                spacecraft.getModel(),
                spacecraft.getLaunchDate(),
                spacecraft.getSpacecraftStatus() != null ? spacecraft.getSpacecraftStatus().name() : null,
                spacecraft.getSpecifications(),
                spacecraft.getCurrentLocation(),
                spacecraft.getId()
        );
    }

    @Override
    public void deleteById(Integer id) {
        String sql = "DELETE FROM spacecraft WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }
}