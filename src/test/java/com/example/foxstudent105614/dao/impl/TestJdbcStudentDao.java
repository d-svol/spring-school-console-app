package com.example.foxstudent105614.dao.impl;

import com.example.foxstudent105614.dao.StudentDao;
import com.example.foxstudent105614.model.Student;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@JdbcTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Sql(
		scripts = {"classpath:create_table.sql", "classpath:sample_data.sql"},
		executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD
)
@Testcontainers
@ContextConfiguration(classes = TestJdbcStudentDao.class)
@TestPropertySource(locations = "classpath:/application.yml")
public class TestJdbcStudentDao {
	@Autowired
	private NamedParameterJdbcTemplate jdbcTemplate;
	private StudentDao studentDao;

	@Container
	private static final PostgreSQLContainer<?> postgresContainer = new PostgreSQLContainer<>("postgres:14.6")
			.withDatabaseName("test")
			.withUsername("root")
			.withPassword("test");

	@BeforeAll
	static void beforeAll() {
		postgresContainer.start();
	}

	@AfterAll
	static void afterAll() {
		postgresContainer.stop();
	}

	@BeforeEach
	void setUp() {
		this.studentDao = new JdbcStudentDao(jdbcTemplate);
	}

	@Test
	void findStudentsByCourseName() {
		String courseName = "Math";
		List<Student> students = studentDao.findStudentsByCourseName(courseName);
		Student firstStudent = students.get(0);
		assertEquals(1, firstStudent.studentId());
		assertEquals("NameA", firstStudent.firstName());
		assertEquals("LastnameA", firstStudent.lastName());
	}

	@Test
	void findById() {
		int firstID = 1;
		Optional<Student> student = studentDao.findById(firstID);
		assertTrue(student.isPresent());
		assertEquals(1, student.get().studentId());
		assertEquals("NameA", student.get().firstName());
		assertEquals("LastnameA", student.get().lastName());
	}

	@Test
	void findAll() {
		List<Student> students = studentDao.findAll();
		assertEquals(5, students.size());
	}

	@Test
	void saveWithoutId() {
		int studentID = 6;
		studentDao.save(1, "NewName", "NewLastName");

		Optional<Student> student = studentDao.findById(studentID);
		assertTrue(student.isPresent());
		assertEquals(studentID, student.get().studentId());
		assertEquals(1, student.get().groupId());
		assertEquals("NewName", student.get().firstName());
		assertEquals("NewLastName", student.get().lastName());
	}

	@Test
	void testSave() {
		int studentID = 6;
		int groupID = 1;
		Student newStudent = new Student(studentID, groupID, "NewName", "NewLastName");
		studentDao.save(newStudent);

		Optional<Student> student = studentDao.findById(studentID);
		assertTrue(student.isPresent());
		assertEquals(studentID, student.get().studentId());
		assertEquals(groupID, student.get().groupId());
		assertEquals("NewName", student.get().firstName());
		assertEquals("NewLastName", student.get().lastName());
	}

	@Test
	void update() {
		int studentID = 1;
		int groupID = 1;
		Student student = new Student(studentID, groupID, "NewName", "NewLastName");
		studentDao.update(student);

		Optional<Student> foundStudent = studentDao.findById(studentID);
		assertTrue(foundStudent.isPresent());
		assertEquals(studentID, foundStudent.get().studentId());
		assertEquals(groupID, foundStudent.get().groupId());
		assertEquals("NewName", foundStudent.get().firstName());
		assertEquals("NewLastName", foundStudent.get().lastName());
	}

	@Test
	void delete() {
		int studentID = 1;
		studentDao.delete(studentID);

		Optional<Student> deletedStudent = studentDao.findById(studentID);
		assertFalse(deletedStudent.isPresent());
	}
}