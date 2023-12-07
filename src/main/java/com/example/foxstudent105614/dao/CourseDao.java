package com.example.foxstudent105614.dao;

import com.example.foxstudent105614.model.Course;

import java.util.Optional;

public interface CourseDao extends Dao<Course>{
	Optional<Course> findByName(String name);
	void saveStudentInCourse(int studentId, int courseId);
	void deleteStudentFromCourse(int studentId, int courseId);
}