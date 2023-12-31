package com.example.foxstudent105614.service;

import com.example.foxstudent105614.model.Course;
import com.example.foxstudent105614.model.Group;
import com.example.foxstudent105614.model.Student;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ReportSchoolService {
    private final Logger logger = LoggerFactory.getLogger(ReportSchoolService.class);

    private final SchoolService schoolService;

    public ReportSchoolService(SchoolService schoolService) {
        this.schoolService = schoolService;

    }

    public void printGroupsByStudentCount(int maxStudentCount) {
        List<Group> groups = schoolService.findGroupsByStudentCount(maxStudentCount);
        if (groups.isEmpty()) {
            logger.info("No groups found with {} or fewer students.", maxStudentCount);
        } else {
            logger.info("Groups of {} or fewer students:", maxStudentCount);
            for (Group group : groups) {
                System.out.println(group.toString());
            }
        }
    }

    public void printStudentsByCourseName(String courseName) {
        List<Student> students = schoolService.findStudentsByCourseName(courseName);
        if (students.isEmpty()) {
            logger.info("No students found for the course with name: {}", courseName);
        } else {
            logger.info("Students related to the course '{}':", courseName);
            for (Student student : students) {
                logger.info("{} {} (ID: {})", student.getFirstName(), student.getLastName(), student.getStudentId());
            }
        }
    }

    public void printAddStudent(String firstName, String lastName) {
        Student student = new Student(firstName, lastName);
        schoolService.addStudent(student);
        logger.info("Added new student. First name: {}, Last name: {}", firstName, lastName);
    }

    public void printDeleteStudentById(int studentId) {
        Optional<Student> studentOptional = schoolService.findStudentById(studentId);

        studentOptional.ifPresent(student -> {
            schoolService.deleteStudentById(studentId);
            logger.info("Deleted student: {}", student);
        });

        if (studentOptional.isEmpty()) {
            logger.error("Student not found with ID: {}", studentId);
        }
    }

    public void printSaveStudentInCourse(int studentId, int courseId) {
        Optional<Student> studentOptional = schoolService.findStudentById(studentId);
        Optional<Course> courseOptional = schoolService.findCourseById(courseId);

        if (studentOptional.isPresent()) {
            Student student = studentOptional.get();

            if (courseOptional.isPresent()) {
                schoolService.saveStudentInCourse(studentOptional.get().getStudentId(), courseOptional.get().getCourseId());
                logger.info("Added student {} {} to course with ID: {}", student.getFirstName(), student.getLastName(), courseId);
            } else {
                logger.error("Error: Course not found for ID - CourseID: {}", courseId);
            }
        } else {
            logger.error("Error: Student not found for ID - StudentID: {}", studentId);
        }
    }

    public void printDeleteStudentFromCourse(int studentId, int courseId) {
        Optional<Student> studentOptional = schoolService.findStudentById(studentId);
        Optional<Course> courseOptional = schoolService.findCourseById(courseId);

        if (courseOptional.isPresent() && studentOptional.isPresent()) {
            schoolService.deleteStudentFromCourse(studentId, courseId);
            logger.info("Removed student with ID {} from course with ID {}", studentId, courseId);
        } else {
            logger.info("Course with ID {} or Student with ID {} not found", courseId, studentId);
        }
    }
}
