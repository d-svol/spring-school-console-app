package com.example.foxstudent105614.service;

import com.example.foxstudent105614.dao.CourseDao;
import com.example.foxstudent105614.model.Course;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CourseService {
    private final CourseDao courseDao;

    @Autowired
    public CourseService(CourseDao courseDao) {
        this.courseDao = courseDao;
    }

    public Optional<Course> findById(int courseId) {
        return courseDao.findById(courseId);
    }

    public List<Course> findAll() {
        return courseDao.findAll();
    }

    public Optional<Course> findByName(String courseName) {
        return courseDao.findByName(courseName);
    }

    public void save(Course course) {
        courseDao.save(course);
    }

    public void update(Course course) {
        courseDao.update(course);
    }

    public void delete(int courseId) {
        courseDao.delete(courseId);
    }

    public void saveStudentInCourse(int studentId, int courseId) {
        courseDao.saveStudentInCourse(studentId, courseId);
    }

    public void deleteStudentFromCourse(int studentId, int courseId) {
        courseDao.deleteStudentFromCourse(studentId, courseId);
    }
}
