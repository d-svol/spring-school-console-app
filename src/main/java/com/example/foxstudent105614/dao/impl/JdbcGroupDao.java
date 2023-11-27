package com.example.foxstudent105614.dao.impl;

import com.example.foxstudent105614.dao.GroupDao;
import com.example.foxstudent105614.model.Group;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.DataClassRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Repository
public class JdbcGroupDao implements GroupDao {
    private static final String FIND_ALL_BY_COUNT =
            "SELECT groups.group_id, groups.group_name, COUNT(students.student_id) AS student_count " +
                    "FROM groups " +
                    "LEFT JOIN students ON groups.group_id = students.group_id " +
                    "GROUP BY groups.group_id, groups.group_name " +
                    "HAVING COUNT(students.student_id) <= :student_count";
    private static final String FIND_BY_ID = "SELECT * FROM groups WHERE group_id = :group_id";
    private static final String FIND_ALL = "SELECT * FROM groups";
    private static final String SAVE = "INSERT INTO groups (group_id, group_name) VALUES (:group_id, :group_name)";
    private static final String UPDATE = "UPDATE groups SET group_name = :group_name WHERE group_id = :group_id";
    private static final String DELETE = "DELETE FROM groups WHERE group_id = :group_id";


    private final NamedParameterJdbcTemplate jdbcTemplate;
    private final RowMapper<Group> groupRowMapper = DataClassRowMapper.newInstance(Group.class);

    public JdbcGroupDao(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Group> findGroupsWithLessOrEqualStudents(int maxStudentCount) {
        try {
            MapSqlParameterSource parameter = new MapSqlParameterSource();
            parameter.addValue("student_count", maxStudentCount);
            return jdbcTemplate.query(FIND_ALL_BY_COUNT, parameter, groupRowMapper);
        } catch (EmptyResultDataAccessException e) {
            return Collections.emptyList();
        }
    }

    @Override
    public Optional<Group> findById(int id) {
        MapSqlParameterSource parameter = new MapSqlParameterSource();
        parameter.addValue("group_id", id);
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(FIND_BY_ID, parameter, groupRowMapper));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public List<Group> findAll() {
        try {
            return jdbcTemplate.query(FIND_ALL, groupRowMapper);
        } catch (DataAccessException ex) {
            return Collections.emptyList();
        }
    }

    @Override
    public void save(Group entity) {
        MapSqlParameterSource parameter = new MapSqlParameterSource();
        parameter.addValue("group_id", entity.groupId());
        parameter.addValue("group_name", entity.groupName());
        jdbcTemplate.update(SAVE, parameter);
    }

    @Override
    public void update(Group entity) {
        MapSqlParameterSource parameter = new MapSqlParameterSource();
        parameter.addValue("group_id", entity.groupId());
        parameter.addValue("group_name", entity.groupName());
        jdbcTemplate.update(UPDATE, parameter);
    }

    @Override
    public void delete(int id) {
        MapSqlParameterSource parameter = new MapSqlParameterSource();
        parameter.addValue("group_id", id);
        jdbcTemplate.update(DELETE, parameter);
    }
}