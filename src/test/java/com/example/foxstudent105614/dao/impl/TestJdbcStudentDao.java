package com.example.foxstudent105614.dao.impl;


import com.example.foxstudent105614.dao.StudentDao;
import com.example.foxstudent105614.model.Group;
import com.example.foxstudent105614.model.Student;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest(properties = {"isProductionMode  = false"})
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Sql(
        scripts = {"classpath:create_table.sql", "classpath:sample_data.sql"},
        executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD
)
public class TestJdbcStudentDao {
    @Autowired
    private StudentDao studentDao;

    @TestConfiguration
    public static class Configuration {
        @Bean
        JpaStudentDao jpaStudentDao() {
            return new JpaStudentDao();
        }
    }

    @Test
    void findById() {
        int firstID = 1;
        Optional<Student> foundStudent = studentDao.findById(firstID);
        assertNotNull(foundStudent.orElse(null));
        assertEquals(1, foundStudent.get().getStudentId());
        assertEquals("NameA", foundStudent.get().getFirstName());
        assertEquals("LastnameA", foundStudent.get().getLastName());
    }

    @Test
    void findStudentsByCourseName() {
        String courseName = "English";
        List<Student> foundStudents = studentDao.findStudentsByCourseName(courseName);
        assertEquals(1, foundStudents.size());
    }

    @Test
    void findAll() {
        int studentSize = 5;
        List<Student> students = studentDao.findAll();
        assertEquals(studentSize, students.size());
    }

    @Test
    void save() {
        Student student = new Student("FirstName", "LastName");
        studentDao.save(student);

        Student foundStudent = studentDao.findById(student.getStudentId()).orElse(null);
        assertNotNull(foundStudent);
        assertEquals(6, foundStudent.getStudentId());
        assertEquals("FirstName", foundStudent.getFirstName());
        assertEquals("LastName", foundStudent.getLastName());
    }

	@Test
	void update() {
        int studentID = 1;
        Student foundStudent = studentDao.findById(studentID).orElse(null);
        assertNotNull(foundStudent);
        assertEquals(studentID, foundStudent.getStudentId());
        assertEquals("NameA", foundStudent.getFirstName());
        assertEquals("LastnameA", foundStudent.getLastName());

        Student updatedStudent = new Student(studentID, new Group(), "UpdatedFirstName", "UpdatedLastName");
        studentDao.update(updatedStudent);

        foundStudent = studentDao.findById(updatedStudent.getStudentId()).orElse(null);
        assertNotNull(foundStudent);
        assertEquals(studentID, foundStudent.getStudentId());
        assertEquals("UpdatedFirstName", foundStudent.getFirstName());
        assertEquals("UpdatedLastName", foundStudent.getLastName());
	}

	@Test
	void delete() {
		int studentID = 1;
		studentDao.delete(studentID);
        Optional<Student> foundStudent = studentDao.findById(studentID);
        assertFalse(foundStudent.isPresent());
	}
}