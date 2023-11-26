package com.example.foxstudent105614.dao.impl;

import com.example.foxstudent105614.dao.CourseDao;
import com.example.foxstudent105614.dao.StudentDao;
import com.example.foxstudent105614.model.Course;
import com.example.foxstudent105614.model.Student;
import com.example.foxstudent105614.service.CourseService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


@SpringBootTest(classes = {CourseService.class})
public class TestJdbcCourseDao {
    @MockBean
    private CourseDao courseDao;

    @MockBean
    private StudentDao studentDao;

    @Test
    void findAll() {
        List<Course> mockCourses = List.of(new Course(1, "Math", "DescriptionMatch"));
        when(courseDao.findAll()).thenReturn(mockCourses);
        List<Course> result = courseDao.findAll();
        assertEquals(mockCourses, result);
    }

    @Test
    void findById() {
        int firstID = 1;
        Course mockCourse = new Course(1, "Math", "DescriptionMatch");
        when(courseDao.findById(firstID)).thenReturn(Optional.of(mockCourse));

        Optional<Course> course = courseDao.findById(firstID);
        assertTrue(course.isPresent());
        assertEquals(1, course.get().courseId());
        assertEquals("Math", course.get().courseName());
        assertEquals("DescriptionMatch", course.get().courseDescription());
    }

    @Test
    void findByName() {
        String courseName = "Math";
        Course mockCourse = new Course(1, courseName, "DescriptionMatch");
        when(courseDao.findByName(courseName)).thenReturn(Optional.of(mockCourse));

        Optional<Course> course = courseDao.findByName("Math");
        assertTrue(course.isPresent());
        assertEquals(1, course.get().courseId());
        assertEquals("Math", course.get().courseName());
        assertEquals("DescriptionMatch", course.get().courseDescription());
    }

    @Test
    void save() {
        int courseID = 4;
        Course mockCourse = new Course(courseID, "NewName", "NewDescription");
        doAnswer(invocation -> {
            Course savedCourse = invocation.getArgument(0);
            assertEquals(mockCourse.courseName(), savedCourse.courseName());
            assertEquals(mockCourse.courseDescription(), savedCourse.courseDescription());
            return null;
        }).when(courseDao).save(any());
        courseDao.save(mockCourse);
        verify(courseDao).save(any());
    }

    @Test
    void saveCourseForStudent() {
        int studentId = 1;
        int courseId = 2;
        when(courseDao.findById(courseId))
                .thenReturn(Optional.of(new Course(courseId, "NewName", "NewDescription")));

        Course foundCourse = courseDao.findById(courseId).orElse(null);
        courseDao.saveStudentInCourse(studentId, courseId);

        assertNotNull(foundCourse);
        assertEquals(courseId, foundCourse.courseId());

        verify(courseDao, times(1)).findById(courseId);
        verify(courseDao, times(1)).saveStudentInCourse(studentId, courseId);
    }

    @Test
    void update() {
        int courseID = 1;
        Course updatedCourse = new Course(courseID, "UpdatedName", "UpdatedDescription");
        doNothing().when(courseDao).update(any(Course.class));

        courseDao.update(updatedCourse);

        verify(courseDao).update(argThat(argument -> argument.courseId() == courseID
                && argument.courseName().equals("UpdatedName")
                && argument.courseDescription().equals("UpdatedDescription")));
    }

    @Test
    void delete() {
        int courseIdToDelete = 1;
        when(courseDao.findById(courseIdToDelete))
                .thenReturn(Optional.of(new Course(courseIdToDelete, "Name", "Description")));

        verify(courseDao, times(1)).findById(courseIdToDelete);

        courseDao.delete(courseIdToDelete);
        verify(courseDao, times(1)).delete(courseIdToDelete);
        when(courseDao.findById(courseIdToDelete)).thenReturn(Optional.empty());
        Optional<Course> updatedFoundCourse = courseDao.findById(courseIdToDelete);
        assertFalse(updatedFoundCourse.isPresent());
    }

    @Test
    void deleteStudentFromCourse() {
        int studentId = 1;
        int groupId = 1;
        int courseIdToDelete = 2;

        when(courseDao.findById(courseIdToDelete))
                .thenReturn(Optional.of(new Course(courseIdToDelete, "Name", "Description")));

        when(studentDao.findById(studentId))
                .thenReturn(Optional.of(new Student(studentId, groupId, "FirstName", "LastName")));

        courseDao.deleteStudentFromCourse(studentId, courseIdToDelete);

        verify(courseDao, times(1)).deleteStudentFromCourse(studentId, courseIdToDelete);
    }
}