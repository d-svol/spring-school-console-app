package com.example.foxstudent105614.service;

import com.example.foxstudent105614.model.Course;
import com.example.foxstudent105614.model.Group;
import com.example.foxstudent105614.model.Student;

import java.util.List;
import java.util.Optional;

public class ReportSchoolService {
    private final SchoolService schoolService;

    public ReportSchoolService(SchoolService schoolService) {
        this.schoolService = schoolService;
    }

    public void printGroupsByStudentCount(int maxStudentCount) {
        List<Group> groups = schoolService.findGroupsByStudentCount(maxStudentCount);
        if (groups.isEmpty()) {
            System.out.println("No groups found with " + maxStudentCount + " or fewer students.");
        } else {
            System.out.println("Groups of " + maxStudentCount + " or fewer students:");
            for (Group group : groups) {
                System.out.println(group.toString());
            }
        }
    }

    public void printStudentsByCourseName(String courseName) {
        List<Student> students = schoolService.findStudentsByCourseName(courseName);
        if (students.isEmpty()) {
            System.out.println("No students found for the course with name: " + courseName);
        } else {
            System.out.println("Students related to the course '" + courseName + "':");
            for (Student student : students) {
                System.out.println(student.firstName() + " " + student.lastName() + " (ID: " + student.studentId() + ")");
            }
        }
    }

    public void printAddStudent(int groupId, String firstName, String lastName) {
        Optional<Group> groupOptional = schoolService.findGroupById(groupId);

        if (groupOptional.isEmpty()) {
            System.out.println("Error: Group not found for id: " + groupId);
        } else {
            schoolService.addStudent(groupId, firstName, lastName);
        }
    }

    public void printDeleteStudentById(int studentId) {
        Optional<Student> studentOptional = schoolService.findStudentById(studentId);

        studentOptional.ifPresent(student -> {
            schoolService.deleteStudentById(studentId);
            System.out.println("Deleted student with ID: " + studentId);
        });

        if (studentOptional.isEmpty()) {
            System.out.println("Student not found with ID: " + studentId);
        }
    }

    public void printCoursesForStudent(int studentId, List<Integer> courseListId) {
        Optional<Student> studentOptional = schoolService.findStudentById(studentId);

        if (studentOptional.isPresent()) {
            Student student = studentOptional.get();
            courseListId.forEach(courseId -> {
                Optional<Course> courseOptional = schoolService.findCourseById(courseId);

                if (courseOptional.isPresent()) {
                    schoolService.saveStudentInCourse(studentId, courseId);
                    System.out.println("Added student with ID " + student.studentId() + " to course with ID: " + courseId);
                } else {
                    System.out.println("Error: Course not found for ID - CourseID: " + courseId);
                }
            });
        } else {
            System.out.println("Error: Student not found for ID - StudentID: " + studentId);
        }
    }

    public void printRemoveStudentFromCourse(int studentId, int courseId) {
        schoolService.deleteStudentFromCourse(studentId, courseId);
        System.out.println("Removed student with ID " + studentId + " from course with ID " + courseId);
    }

}
