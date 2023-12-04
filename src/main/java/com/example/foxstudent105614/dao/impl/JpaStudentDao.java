package com.example.foxstudent105614.dao.impl;

import com.example.foxstudent105614.dao.StudentDao;
import com.example.foxstudent105614.model.Group;
import com.example.foxstudent105614.model.Student;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityManager;
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
public class JpaStudentDao implements StudentDao {
	private static final String FIND_ALL = "SELECT * FROM students";
	private static final String FIND_ALL_BY_COURSE_NAME =
			"SELECT s FROM Student s JOIN s.courses c WHERE c.courseName = :course_name";


	@PersistenceContext
	private EntityManager entityManager;
	private static final Logger logger = LoggerFactory.getLogger(JpaStudentDao.class);

	@Override
	public List<Student> findStudentsByCourseName(String courseName) {
		try {
			return entityManager.createQuery(FIND_ALL_BY_COURSE_NAME, Student.class)
					.setParameter("course_name", courseName)
					.getResultList();
		} catch (Exception e) {
			logger.error("Error finding students by course name '{}'", courseName, e);
			return Collections.emptyList();
		}
	}

	@Override
	public Optional<Student> findById(int studentId) {
		try {
			return Optional.ofNullable(entityManager.find(Student.class, studentId));
		} catch (EmptyResultDataAccessException e) {
			logger.error("Error find student by ID '{}'", studentId, e);
			return Optional.empty();
		}
	}

	@Override
	public List<Student> findAll() {
		try {
			return entityManager.createQuery(FIND_ALL, Student.class).getResultList();
		} catch (Exception e) {
			logger.error("Error executing 'findAll' database query: ", e);
			return Collections.emptyList();
		}
	}

	@Override
	public void save(Student student) {
		try {
			entityManager.persist(student);
		} catch (EntityExistsException e) {
			logger.error("Error updating student with ID '{}'", student.getStudentId(), e);
		}
	}

	@Override
	public void save(int groupId, String firstName, String lastName) {
		Group group = entityManager.find(Group.class, groupId);

		if (group != null) {
			Student student = new Student(0, group, firstName, lastName);
			entityManager.persist(student);
		} else {
			logger.error("Student with groupId '{}' not found. Unable to save.", groupId);
		}
	}

	@Override
	public void update(Student student) {
		entityManager.merge(student);
	}

	@Override
	public void delete(int studentId) {
		Student student = entityManager.find(Student.class, studentId);
		if (student != null) {
			entityManager.remove(student);
		} else {
			logger.error("Student with ID '{}' not found. Unable to delete.", studentId);
		}
	}
}