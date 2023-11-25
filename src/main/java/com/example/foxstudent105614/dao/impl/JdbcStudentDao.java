package com.example.foxstudent105614.dao.impl;

import com.example.foxstudent105614.dao.StudentDao;
import com.example.foxstudent105614.model.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.DataClassRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Repository
public class JdbcStudentDao implements StudentDao {
	private static final String FIND_BY_ID = "SELECT * FROM students WHERE student_id = :student_id";
	private static final String FIND_ALL = "SELECT * FROM students";
	private static final String SAVE = "INSERT INTO students (student_id, group_id, first_name, last_name) VALUES (:student_id, :group_id, :first_name, :last_name)";
	private static final String SAVE_STUDENT_WITHOUT_ID = "INSERT INTO students (group_id, first_name, last_name) VALUES (:group_id, :first_name, :last_name)";
	private static final String UPDATE = "UPDATE students SET group_id = :group_id, first_name = :first_name, last_name = :last_name WHERE student_id = :student_id";
	private static final String DELETE = "DELETE FROM students WHERE student_id = :student_id";
	private static final String FIND_ALL_BY_COURSE_NAME = "SELECT * FROM students  " +
			"INNER JOIN student_course ON students.student_id = student_course.student_id " +
			"INNER JOIN courses ON student_course.course_id = courses.course_id " +
			"WHERE courses.course_name = :course_name";

	private final NamedParameterJdbcTemplate jdbcTemplate;

	private final RowMapper<Student> studentRowMapper = DataClassRowMapper.newInstance(Student.class);

	@Autowired
	public JdbcStudentDao(NamedParameterJdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	public List<Student> findStudentsByCourseName(String courseName) {
		try {
			SqlParameterSource parameter = new MapSqlParameterSource().addValue("course_name", courseName);
			return jdbcTemplate.query(FIND_ALL_BY_COURSE_NAME, parameter, studentRowMapper);
		} catch (EmptyResultDataAccessException e) {
			return Collections.emptyList();
		}
	}

	@Override
	public Optional<Student> findById(int studentId) {
		SqlParameterSource parameter = new MapSqlParameterSource("student_id", studentId);
		try {
			return Optional.ofNullable(jdbcTemplate.queryForObject(FIND_BY_ID, parameter, studentRowMapper));
		} catch (EmptyResultDataAccessException e) {
			return Optional.empty();
		}

	}

	@Override
	public List<Student> findAll() {
		try {
			return jdbcTemplate.query(FIND_ALL, studentRowMapper);
		} catch (DataAccessException ex) {
			return Collections.emptyList();
		}
	}

	@Override
	public void save(Student student) {
		MapSqlParameterSource parameters = new MapSqlParameterSource();
		parameters.addValue("student_id", student.studentId());
		parameters.addValue("group_id", student.groupId());
		parameters.addValue("first_name", student.firstName());
		parameters.addValue("last_name", student.lastName());
		jdbcTemplate.update(SAVE, parameters);
	}

	@Override
	public void save(int groupId, String firstName, String lastName) {
		MapSqlParameterSource parameters = new MapSqlParameterSource();
		parameters.addValue("group_id", groupId);
		parameters.addValue("first_name", firstName);
		parameters.addValue("last_name", lastName);
		jdbcTemplate.update(SAVE_STUDENT_WITHOUT_ID, parameters);
	}

	@Override
	public void update(Student student) {
		MapSqlParameterSource parameters = new MapSqlParameterSource();
		parameters.addValue("group_id", student.groupId());
		parameters.addValue("first_name", student.firstName());
		parameters.addValue("last_name", student.lastName());
		parameters.addValue("student_id", student.studentId());

		jdbcTemplate.update(UPDATE, parameters);
	}

	@Override
	public void delete(int studentId) {
		MapSqlParameterSource parameters = new MapSqlParameterSource();
		parameters.addValue("student_id", studentId);
		jdbcTemplate.update(DELETE, parameters);
	}
}