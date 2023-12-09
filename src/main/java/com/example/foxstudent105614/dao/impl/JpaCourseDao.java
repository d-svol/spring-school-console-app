package com.example.foxstudent105614.dao.impl;

import com.example.foxstudent105614.dao.CourseDao;
import com.example.foxstudent105614.dao.CourseRepository;
import com.example.foxstudent105614.model.Course;
import com.example.foxstudent105614.model.Student;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class JpaCourseDao implements CourseDao {
    private static final Logger logger = LoggerFactory.getLogger(JpaCourseDao.class);
    private final CourseRepository courseRepository;

    @Autowired
    public JpaCourseDao(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    @Override
    public Optional<Course> findById(int id) {
        return courseRepository.findById(id);
    }

    @Override
    public List<Student> findStudentsByCourseName(String courseName) {
        return courseRepository.findStudentsByCourseName(courseName);
    }

    @Override
    public List<Course> findAll() {
        return courseRepository.findAll();
    }

    @Override
    public Optional<Course> findByName(String name) {
        return courseRepository.findByCourseName(name);
    }

    @Override
    public void save(Course entity) {
        try {
            courseRepository.save(entity);
        } catch (Exception e) {
            logger.error("Error saving course", e);
        }
    }

    @Override
    public void saveStudentInCourse(int studentId, int courseId) {
        try {
            courseRepository.saveStudentInCourse(studentId, courseId);
        } catch (Exception e) {
            logger.error("Error saving student in course", e);
        }
    }

    @Override
    public void update(Course entity) {
        courseRepository.save(entity);
    }

    @Override
    public void deleteById(int id) {
        courseRepository.deleteById(id);
    }

    @Override
    public void delete(Course entity) {
        courseRepository.delete(entity);
    }

    @Override
    public void deleteStudentFromCourse(int studentId, int courseId) {
        try {
            courseRepository.deleteStudentFromCourse(studentId, courseId);
        } catch (Exception e) {
            logger.error("Error delete student in course", e);
        }
    }
}