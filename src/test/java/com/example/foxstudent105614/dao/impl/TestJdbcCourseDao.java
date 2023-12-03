package com.example.foxstudent105614.dao.impl;

import com.example.foxstudent105614.Student105614Application;
import com.example.foxstudent105614.controller.SchoolManager;
import com.example.foxstudent105614.dao.CourseDao;
import com.example.foxstudent105614.dao.StudentDao;
import com.example.foxstudent105614.model.Course;
import com.example.foxstudent105614.service.DbLoadingService;
import com.example.foxstudent105614.service.SchoolService;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
//@Sql(
//        scripts = {"classpath:create_table.sql", "classpath:sample_data.sql"},
//        executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD
//)
//@RunWith(SpringRunner.class)
public class TestJdbcCourseDao {
    @Autowired
    private CourseDao courseDao;

    @Test
    void findAll() {
        List<Course> courses = courseDao.findAll();
        assertEquals(3, courses.size());
    }

    //
//    @Test
//    void findById() {
//        int firstID = 1;
//        Optional<Course> course = courseDao.findById(firstID);
//        assertNotNull(course.orElse(null));
//        assertEquals(1, course.get().courseId());
//        assertEquals("Math", course.get().courseName());
//        assertEquals("Basic math skills", course.get().courseDescription());
//    }
//
//    @Test
//    void findByName() {
//        Optional<Course> course = courseDao.findByName("Math");
//        assertNotNull(course.orElse(null));
//        assertEquals(1, course.get().courseId());
//        assertEquals("Math", course.get().courseName());
//        assertEquals("Basic math skills", course.get().courseDescription());
//    }
//
//    @Test
//    void save() {
//        int courseID = 4;
//        Course newCourse = new Course(courseID, "NewName", "NewDescription");
//        courseDao.save(newCourse);
//
//        Optional<Course> course = courseDao.findById(courseID);
//        assertNotNull(course.orElse(null));
//        assertEquals(courseID, course.get().courseId());
//        assertEquals("NewName", course.get().courseName());
//        assertEquals("NewDescription", course.get().courseDescription());
//    }
//
//    @Test
//    void saveStudentInCourse() {
//        int studentID = 5;
//        int courseID = 1;
//        courseDao.saveStudentInCourse(studentID, courseID);
//
//        List<Student> students = studentDao.findStudentsByCourseName("Math");
//        assertFalse(students.isEmpty());
//        Student student = students.get(0);
//        assertEquals("NameA", student.getFirstName());
//        assertEquals("LastnameA", student.getLastName());
//    }
//
//    @Test
//    void update() {
//        int courseID = 1;
//        Course updatedCourse = new Course(courseID, "UpdatedName", "UpdatedDescription");
//        courseDao.update(updatedCourse);
//
//        Optional<Course> course = courseDao.findById(courseID);
//        assertNotNull(course.orElse(null));
//        assertEquals(courseID, course.get().courseId());
//        assertEquals("UpdatedName", course.get().courseName());
//        assertEquals("UpdatedDescription", course.get().courseDescription());
//    }
//
//    @Test
//    void delete() {
//        int courseID = 1;
//        courseDao.delete(courseID);
//
//        Optional<Course> deletedCourse = courseDao.findById(courseID);
//        assertFalse(deletedCourse.isPresent());
//    }
//
//    @Test
//    void deleteStudentFromCourse() {
//        int studentID = 1;
//        int courseID = 1;
//        List<Student> studentsBeforeDeletion = studentDao.findStudentsByCourseName("Math");
//        assertFalse(studentsBeforeDeletion.isEmpty());
//
//        courseDao.deleteStudentFromCourse(studentID, courseID);
//
//        List<Student> studentsAfterDeletion = studentDao.findStudentsByCourseName("Math");
//        assertTrue(studentsAfterDeletion.isEmpty());
//    }
}