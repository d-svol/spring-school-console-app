package com.example.foxstudent105614.service;

import com.example.foxstudent105614.dao.StudentDao;
import com.example.foxstudent105614.model.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StudentService implements StudentDao{
    private final StudentDao studentDao;

    @Autowired
    public StudentService(StudentDao studentDao) {
        this.studentDao = studentDao;
    }

    @Override
    public void delete(Student entity) {
        studentDao.delete(entity);
    }

    public Optional<Student> findById(int studentId) {
        return studentDao.findById(studentId);
    }

    public List<Student> findAll() {
        return studentDao.findAll();
    }

    public void save(Student student) {
        studentDao.save(student);
    }

    public void update(Student student) {
        studentDao.update(student);
    }

    public void deleteById(int studentId) {
        studentDao.deleteById(studentId);
    }
}

