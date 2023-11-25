package com.example.foxstudent105614.dao;

import com.example.foxstudent105614.model.Group;

import java.util.List;


public interface GroupDao extends Dao<Group>{
	List<Group> findGroupsWithLessOrEqualStudents(int maxStudentCount);
}