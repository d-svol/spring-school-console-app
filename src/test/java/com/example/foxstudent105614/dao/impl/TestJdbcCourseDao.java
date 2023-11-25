package com.example.foxstudent105614.dao.impl;

import com.example.foxstudent105614.dao.CourseDao;
import com.example.foxstudent105614.dao.StudentDao;
import com.example.foxstudent105614.model.Course;
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
public class TestJdbcCourseDao {
	@Autowired
	private NamedParameterJdbcTemplate jdbcTemplate;
	private CourseDao courseDao;
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
		this.courseDao = new JdbcCourseDao(jdbcTemplate);
		this.studentDao = new JdbcStudentDao(jdbcTemplate);
	}

	@Test
	void findAll() {
		List<Course> students = courseDao.findAll();
		assertEquals(3, students.size());
	}

	@Test
	void findById() {
		int firstID = 1;
		Optional<Course> course = courseDao.findById(firstID);
		assertTrue(course.isPresent());
		assertEquals(1, course.get().courseId());
		assertEquals("Math", course.get().courseName());
		assertEquals("Basic math skills", course.get().courseDescription());
	}

	@Test
	void findByName() {
		Optional<Course> course = courseDao.findByName("Math");
		assertTrue(course.isPresent());
		assertEquals(1, course.get().courseId());
		assertEquals("Math", course.get().courseName());
		assertEquals("Basic math skills", course.get().courseDescription());
	}

	@Test
	void save() {
		int courseID = 4;
		Course newStudent = new Course(courseID, "NewName", "NewDescription");
		courseDao.save(newStudent);

		Optional<Course> course = courseDao.findById(courseID);
		assertTrue(course.isPresent());
		assertEquals(courseID, course.get().courseId());
		assertEquals("NewName", course.get().courseName());
		assertEquals("NewDescription", course.get().courseDescription());
	}

	@Test
	void saveCourseForStudent() {
		int studentID = 5;
		int courseID = 1;
		courseDao.saveStudentInCourse(studentID, courseID);

		List<Student> students = studentDao.findStudentsByCourseName("Math");
		Student student = students.get(0);
		assertEquals("NameA", student.firstName());
		assertEquals("LastnameA", student.lastName());
	}

	@Test
	void update() {
		int courseID = 1;
		Course newStudent = new Course(courseID, "NewName", "NewDescription");
		courseDao.update(newStudent);

		Optional<Course> course = courseDao.findById(courseID);
		assertTrue(course.isPresent());
		assertEquals(courseID, course.get().courseId());
		assertEquals("NewName", course.get().courseName());
		assertEquals("NewDescription", course.get().courseDescription());
	}

	@Test
	void delete() {
		int courseID = 1;
		courseDao.delete(courseID);

		Optional<Course> deletedCourse = courseDao.findById(courseID);
		assertFalse(deletedCourse.isPresent());
	}

	@Test
	void deleteCourseForStudent() {
		int studentID = 1;
		int courseID = 1;
		List<Student> students = studentDao.findStudentsByCourseName("Math");
		Student student = students.get(0);
		assertEquals("NameA", student.firstName());
		assertEquals("LastnameA", student.lastName());

		courseDao.deleteStudentFromCourse(studentID, courseID);
		List<Student> updateStudents = studentDao.findStudentsByCourseName("Math");
		Student updateStudent = updateStudents.get(0);
		assertNotEquals("NameA", updateStudent.firstName());
		assertNotEquals("LastnameA", updateStudent.lastName());
	}
}