package com.example.foxstudent105614.dao;

import com.example.foxstudent105614.model.Group;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

public interface GroupRepository extends JpaRepository<Group, Long>{
    @Query("SELECT g FROM Group g WHERE SIZE(g.students) <= :maxStudentCount")
    List<Group> findGroupsWithLessOrEqualStudents(@Param("maxStudentCount") int maxStudentCount);
}
