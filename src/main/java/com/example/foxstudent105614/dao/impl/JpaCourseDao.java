package com.example.foxstudent105614.dao.impl;

import com.example.foxstudent105614.dao.CourseDao;
import com.example.foxstudent105614.model.Course;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Repository;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public class JpaCourseDao implements CourseDao {
    public static final String FIND_BY_NAME = "SELECT * FROM Course c WHERE c.course_name = :course_name";
    private static final String FIND_ALL = "SELECT * FROM Course";
    private static final String SAVE_STUDENT_COURSE = "INSERT INTO student_course (student_id, course_id) VALUES (:student_id, :course_id)";
    private static final String DELETE_STUDENT_COURSE = "DELETE FROM student_course WHERE student_id = :student_id AND course_id = :course_id";

    @PersistenceContext
    private EntityManager entityManager;
    private static final Logger logger = LoggerFactory.getLogger(JpaCourseDao.class);

    @Override
    public Optional<Course> findById(int id) {
        try {
            return Optional.ofNullable(entityManager.find(Course.class, id));
        } catch (EmptyResultDataAccessException e) {
            logger.error("Error find student by ID '{}'", id, e);
            return Optional.empty();
        }
    }

    @Override
    public List<Course> findAll() {
        try {
            return entityManager. createQuery(FIND_ALL, Course.class).getResultList();
        } catch (Exception e) {
            logger.error("Error executing 'findAll' database query: ", e);
            return Collections.emptyList();
        }
    }

    @Override
    public Optional<Course> findByName(String name) {
        try {
            return Optional.ofNullable(entityManager.createQuery(FIND_BY_NAME, Course.class)
                    .setParameter("course_name", name)
                    .getSingleResult());
        } catch (Exception e) {
            logger.error("No result found for course with name '{}'", name);
            return Optional.empty();
        }
    }

    @Override
    public void save(Course entity) {
        try {
            entityManager.persist(entity);
        } catch (EntityExistsException e) {
            logger.error("Error save course with ID '{}'", entity.getCourseId(), e);
        }
    }

    @Override
    public void saveStudentInCourse(int studentId, int courseId) {
        try {
            entityManager.createNativeQuery(SAVE_STUDENT_COURSE)
                    .setParameter("student_id", studentId)
                    .setParameter("course_id", courseId)
                    .executeUpdate();
        } catch (Exception e) {
            logger.error("Error saving student with ID '{}' in course with ID '{}'", studentId, courseId, e);
        }
    }

    @Override
    public void update(Course entity) {
        entityManager.merge(entity);
    }

    @Override
    public void delete(int id) {
        Course course = entityManager.find(Course.class, id);
        if (course != null) {
            entityManager.remove(course);
        } else {
            logger.error("Course with ID '{}' not found. Unable to delete.", id);
        }
    }

    @Override
    public void deleteStudentFromCourse(int studentId, int courseId) {
        try {
            entityManager.createNativeQuery(DELETE_STUDENT_COURSE)
                    .setParameter("student_id", studentId)
                    .setParameter("course_id", courseId)
                    .executeUpdate();
        } catch (Exception e) {
            logger.error("Error deleting student with ID '{}' from course with ID '{}'", studentId, courseId, e);
        }
    }
}