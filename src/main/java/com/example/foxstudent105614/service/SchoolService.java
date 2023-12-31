package com.example.foxstudent105614.service;

import com.example.foxstudent105614.model.Course;
import com.example.foxstudent105614.model.Group;
import com.example.foxstudent105614.model.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SchoolService {
    private final GroupService groupService;
    private final StudentService studentService;
    private final CourseService courseService;

    @Autowired
    public SchoolService(GroupService groupService, StudentService studentService, CourseService courseService) {
        this.groupService = groupService;
        this.studentService = studentService;
        this.courseService = courseService;
    }

    public List<Group> findGroupsByStudentCount(int maxStudentCount) {
        return groupService.findGroupsWithLessOrEqualStudents(maxStudentCount);
    }

    public List<Student> findStudentsByCourseName(String courseName) {
        return courseService.findStudentsByCourseName(courseName);
    }

    public Optional<Group> findGroupById(int groupID) {
        return groupService.findById(groupID);
    }

    public Optional<Student> findStudentById(int id) {
        return studentService.findById(id);
    }

    public Optional<Course> findCourseById(int id) {
        return courseService.findById(id);
    }

    public void addStudent(Student student) {
        studentService.save(student);
    }

    public void deleteStudentById(int studentId) {
        studentService.deleteById(studentId);
    }

    public void saveStudentInCourse(int studentId, int courseId) {
        courseService.saveStudentInCourse(studentId, courseId);
    }

    public void deleteStudentFromCourse(int studentId, int courseId) {
        courseService.deleteStudentFromCourse(studentId, courseId);
    }
}
