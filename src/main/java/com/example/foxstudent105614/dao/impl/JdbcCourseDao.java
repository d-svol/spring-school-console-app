package com.example.foxstudent105614.dao.impl;

import com.example.foxstudent105614.dao.CourseDao;
import com.example.foxstudent105614.model.Course;
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
public class JdbcCourseDao implements CourseDao {
	public static final String FIND_BY_NAME = "SELECT * FROM courses WHERE course_name = :course_name";
	private static final String FIND_BY_ID = "SELECT * FROM courses WHERE course_id = :course_id";
	private static final String FIND_ALL = "SELECT * FROM courses";
	private static final String SAVE = "INSERT INTO courses (course_name, course_description) VALUES (:course_name, :course_description)";
	private static final String SAVE_STUDENT_COURSE = "INSERT INTO student_course (student_id, course_id) VALUES (:student_id, :course_id)";
	private static final String UPDATE = "UPDATE courses SET course_name = :course_name, course_description = :course_description WHERE course_id = :course_id";
	private static final String DELETE = "DELETE FROM courses WHERE course_id = :course_id";
	private static final String DELETE_STUDENT_COURSE = "DELETE FROM student_course WHERE student_id = :student_id AND course_id = :course_id";

	private final NamedParameterJdbcTemplate jdbcTemplate;
	private final RowMapper<Course> courseRowMapper = DataClassRowMapper.newInstance(Course.class);

	public JdbcCourseDao(NamedParameterJdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	public Optional<Course> findById(int id) {
		MapSqlParameterSource parameter = new MapSqlParameterSource();
		parameter.addValue("course_id", id);
		try {
			return Optional.ofNullable(jdbcTemplate.queryForObject(FIND_BY_ID, parameter, courseRowMapper));
		} catch (EmptyResultDataAccessException e) {
			return Optional.empty();
		}
	}

	@Override
	public List<Course> findAll() {
		try {
			return jdbcTemplate.query(FIND_ALL, courseRowMapper);
		} catch (DataAccessException ex) {
			return Collections.emptyList();
		}
	}

	@Override
	public Optional<Course> findByName(String name) {
		try {
			MapSqlParameterSource parameter = new MapSqlParameterSource();
			parameter.addValue("course_name", name);
			return Optional.ofNullable(jdbcTemplate.queryForObject(FIND_BY_NAME, parameter, courseRowMapper));
		} catch (DataAccessException ex) {
			return Optional.empty();
		}
	}

	@Override
	public void save(Course entity) {
		MapSqlParameterSource parameters = new MapSqlParameterSource();
		parameters.addValue("course_name", entity.courseName());
		parameters.addValue("course_description", entity.courseDescription());
		jdbcTemplate.update(SAVE, parameters);
	}

	@Override
	public void saveStudentInCourse(int studentId, int courseId) {
		MapSqlParameterSource parameters = new MapSqlParameterSource();
		parameters.addValue("student_id", studentId);
		parameters.addValue("course_id", courseId);
		jdbcTemplate.update(SAVE_STUDENT_COURSE, parameters);
	}

	@Override
	public void update(Course entity) {
		MapSqlParameterSource parameters = new MapSqlParameterSource();
		parameters.addValue("course_id", entity.courseId());
		parameters.addValue("course_name", entity.courseName());
		parameters.addValue("course_description", entity.courseDescription());
		jdbcTemplate.update(UPDATE, parameters);
	}

	@Override
	public void delete(int id) {
		MapSqlParameterSource parameter = new MapSqlParameterSource();
		parameter.addValue("course_id", id);
		jdbcTemplate.update(DELETE, parameter);
	}

	@Override
	public void deleteStudentFromCourse(int studentId, int courseId) {
		MapSqlParameterSource parameter = new MapSqlParameterSource();
		parameter.addValue("course_id", courseId);
		parameter.addValue("student_id", studentId);
		jdbcTemplate.update(DELETE_STUDENT_COURSE, parameter);
	}
}