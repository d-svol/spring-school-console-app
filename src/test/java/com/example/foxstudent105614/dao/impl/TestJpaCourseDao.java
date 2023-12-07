package com.example.foxstudent105614.dao.impl;

import com.example.foxstudent105614.dao.CourseDao;
import com.example.foxstudent105614.model.Course;
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
class TestJpaCourseDao {
    @Autowired
    private CourseDao courseDao;

    @TestConfiguration
    public static class Configuration {
        @Bean
        JpaCourseDao jpaCourseDao() {
            return new JpaCourseDao();
        }
    }

    @Test
    void findById() {
        int firstID = 1;
        Optional<Course> foundCourse = courseDao.findById(firstID);
        assertNotNull(foundCourse.orElse(null));
        assertEquals(1, foundCourse.get().getCourseId());
        assertEquals("Math", foundCourse.get().getCourseName());
        assertEquals("Basic math skills", foundCourse.get().getCourseDescription());
    }

    @Test
    void findAll() {
        List<Course> courses = courseDao.findAll();
        assertEquals(3, courses.size());
    }

    @Test
    void findByName() {
        Optional<Course> course = courseDao.findByName("Math");
        assertNotNull(course.orElse(null));
        assertEquals(1, course.get().getCourseId());
        assertEquals("Math", course.get().getCourseName());
        assertEquals("Basic math skills", course.get().getCourseDescription());
    }

    @Test
    void save() {
        int courseID = 4;
        Course newCourse = new Course("NewName", "NewDescription");
        courseDao.save(newCourse);

        Optional<Course> foundCourse = courseDao.findById(courseID);
        assertNotNull(foundCourse.orElse(null));
        assertEquals(courseID, foundCourse.get().getCourseId());
        assertEquals("NewName", foundCourse.get().getCourseName());
        assertEquals("NewDescription", foundCourse.get().getCourseDescription());
    }

    @Test
    void saveStudentInCourse() {
        int studentID = 1;
        int courseID = 1;

        courseDao.saveStudentInCourse(studentID, courseID);

        Course course = courseDao.findById(courseID).orElse(null);
        assertNotNull(course);
        List<Student> students = course.getStudents();
        assertNotNull(students);

        assertEquals(1, students.size());
        Student savedStudent = students.getFirst();
        assertEquals(studentID, savedStudent.getStudentId());
        assertEquals("NameA", savedStudent.getFirstName());
        assertEquals("LastnameA", savedStudent.getLastName());
    }

    @Test
    void updateCourse() {
        Course initialCourse = new Course();
        initialCourse.setCourseName("Initial Course");
        initialCourse.setCourseDescription("Initial Description");
        courseDao.save(initialCourse);

        Course updatedCourse = new Course();
        updatedCourse.setCourseId(initialCourse.getCourseId());
        updatedCourse.setCourseName("Updated Course");
        updatedCourse.setCourseDescription("Updated Description");
        courseDao.update(updatedCourse);

        Course foundCourse = courseDao.findById(initialCourse.getCourseId()).orElse(null);
        assertNotNull(foundCourse);
        assertEquals(updatedCourse.getCourseName(), foundCourse.getCourseName());
        assertEquals(updatedCourse.getCourseDescription(), foundCourse.getCourseDescription());
    }

    @Test
    void delete() {
        int courseID = 1;
        courseDao.delete(courseID);

        Optional<Course> foundCourse = courseDao.findById(courseID);
        assertFalse(foundCourse.isPresent());
    }

    @Test
    void deleteStudentFromCourse() {
        int studentId = 1;
        int courseId = 1;

        courseDao.saveStudentInCourse(1, 1);
        courseDao.deleteStudentFromCourse(studentId, courseId);

        Course course = courseDao.findById(courseId).orElse(null);
        List<Student> students = course != null ? course.getStudents() : null;

        assertNotNull(students);
        assertEquals(0, students.size());
    }
}