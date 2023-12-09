package com.example.foxstudent105614.dao;

import com.example.foxstudent105614.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.List;
import java.util.Optional;

public interface StudentRepository extends JpaRepository<Student, Integer> {}
