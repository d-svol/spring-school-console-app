package com.example.foxstudent105614.service;

import com.example.foxstudent105614.dao.StudentDao;
import com.example.foxstudent105614.model.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StudentService {
    private final StudentDao studentDao;

    @Autowired
    public StudentService(StudentDao studentDao) {
        this.studentDao = studentDao;
    }

    public List<Student> findStudentsByCourseName(String courseName) {
        return studentDao.findStudentsByCourseName(courseName);
    }

    public Optional<Student> findById(int studentId) {
        return studentDao.findById(studentId);
    }

    public List<Student> findAll() {return studentDao.findAll();}

    public void save(Student student) {
        studentDao.save(student);
    }

    public void save(int groupId, String firstName, String lastName) {
        studentDao.save(groupId, firstName, lastName);
    }

    public void update(Student student) {
        studentDao.update(student);
    }

    public void delete(int studentId) {
        studentDao.delete(studentId);
    }
}

