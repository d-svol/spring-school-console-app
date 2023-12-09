package com.example.foxstudent105614.dao.impl;

import com.example.foxstudent105614.dao.StudentDao;
import com.example.foxstudent105614.dao.StudentRepository;
import com.example.foxstudent105614.model.Student;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public class JpaStudentDao implements StudentDao {
    private static final Logger logger = LoggerFactory.getLogger(JpaStudentDao.class);
    private final StudentRepository studentRepository;


    @Autowired
    public JpaStudentDao(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @Override
    public Optional<Student> findById(int studentId) {
        return studentRepository.findById(studentId);
    }

    @Override
    public List<Student> findAll() {
        return studentRepository.findAll();
    }

    @Override
    public void save(Student student) {
        studentRepository.save(student);
    }

    @Override
    public void update(Student student) {
        studentRepository.save(student);
    }

    @Override
    public void delete(Student student) {
        studentRepository.delete(student);
    }

    @Override
    public void deleteById(int studentId) {
        studentRepository.deleteById(studentId);
    }
}