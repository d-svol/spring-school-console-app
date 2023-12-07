package com.example.foxstudent105614.dao;

import com.example.foxstudent105614.model.Student;

import java.util.List;

public interface StudentDao extends Dao<Student>{
	List<Student> findStudentsByCourseName(String courseName);
}