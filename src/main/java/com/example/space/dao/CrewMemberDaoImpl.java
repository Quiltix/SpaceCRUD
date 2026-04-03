package com.example.space.dao;

import com.example.space.data.enums.HealthStatus;
import com.example.space.data.model.CrewMember;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.*;

@Repository
@RequiredArgsConstructor
public class CrewMemberDaoImpl implements CrewMemberDao {

    private final JdbcTemplate jdbcTemplate;

    private final RowMapper<CrewMember> rowMapper = (rs, rowNum) -> {
        CrewMember member = new CrewMember();
        member.setId(rs.getInt("id"));
        member.setFirstName(rs.getString("first_name"));
        member.setLastName(rs.getString("last_name"));
        member.setSpecialization(rs.getString("specialization"));

        // Преобразование String в Enum
        String statusStr = rs.getString("health_status");
        if (statusStr != null) {
            member.setHealthStatus(HealthStatus.valueOf(statusStr));
        }

        // Обработка Date (может быть null)
        if (rs.getDate("birth_date") != null) {
            member.setBirthDate(rs.getDate("birth_date").toLocalDate());
        }

        return member;
    };

    @Override
    public CrewMember save(CrewMember crewMember) {
        SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("crew_members")
                .usingGeneratedKeyColumns("id");

        Map<String, Object> parameters = new HashMap<>();
        parameters.put("first_name", crewMember.getFirstName());
        parameters.put("last_name", crewMember.getLastName());
        parameters.put("specialization", crewMember.getSpecialization());
        parameters.put("health_status", crewMember.getHealthStatus() != null ? crewMember.getHealthStatus().name() : null);
        parameters.put("birth_date", crewMember.getBirthDate());

        Number generatedId = simpleJdbcInsert.executeAndReturnKey(parameters);
        crewMember.setId(generatedId.intValue());

        return crewMember;
    }

    @Override
    public Optional<CrewMember> findById(Integer id) {
        String sql = "SELECT * FROM crew_members WHERE id = ?";
        try {
            CrewMember member = jdbcTemplate.queryForObject(sql, rowMapper, id);
            return Optional.ofNullable(member);
        } catch (org.springframework.dao.EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public List<CrewMember> findAll(String search, HealthStatus status, LocalDate startDate, LocalDate endDate) {
        StringBuilder sql = new StringBuilder("SELECT * FROM crew_members");
        List<Object> params = new ArrayList<>();
        boolean hasWhere = false;

        if (status != null) {
            sql.append(" WHERE health_status = ?");
            params.add(status.name());
            hasWhere = true;
        }

        if (search != null && !search.trim().isEmpty()) {
            String searchPattern = "%" + search.trim() + "%";
            sql.append(hasWhere ? " AND " : " WHERE ");
            sql.append("(first_name ILIKE ? OR last_name ILIKE ? OR specialization ILIKE ?)");
            params.add(searchPattern);
            params.add(searchPattern);
            params.add(searchPattern);
            hasWhere = true;
        }

        if (startDate != null) {
            sql.append(hasWhere ? " AND " : " WHERE ");
            sql.append("birth_date >= ?");
            params.add(startDate);
            hasWhere = true;
        }

        if (endDate != null) {
            sql.append(hasWhere ? " AND " : " WHERE ");
            sql.append("birth_date <= ?");
            params.add(endDate);
        }

        return jdbcTemplate.query(sql.toString(), rowMapper, params.toArray());
    }

    @Override
    public boolean existsByFirstNameAndLastName(String firstName, String lastName) {
        String sql = "SELECT COUNT(*) FROM crew_members WHERE first_name = ? AND last_name = ?";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, firstName, lastName);
        return count != null && count > 0;
    }

    @Override
    public void update(CrewMember crewMember) {
        String sql = "UPDATE crew_members SET first_name = ?, last_name = ?, specialization = ?, health_status = ?, birth_date = ? WHERE id = ?";

        jdbcTemplate.update(sql,
                crewMember.getFirstName(),
                crewMember.getLastName(),
                crewMember.getSpecialization(),
                crewMember.getHealthStatus() != null ? crewMember.getHealthStatus().name() : null,
                crewMember.getBirthDate(),
                crewMember.getId()
        );
    }

    @Override
    public void deleteById(Integer id) {
        String sql = "DELETE FROM crew_members WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }
}