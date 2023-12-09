package com.example.foxstudent105614.dao;

import com.example.foxstudent105614.model.Course;
import com.example.foxstudent105614.model.Student;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface CourseRepository extends JpaRepository<Course, Integer> {
    Optional<Course> findByCourseName(String courseName);

    @Query(value = "INSERT INTO student_course (student_id, course_id) VALUES (:studentId, :courseId)", nativeQuery = true)
    void saveStudentInCourse(@Param("studentId") int student_id, @Param("courseId") int course_id);

    @Query(value = "DELETE FROM student_course WHERE student_id = :studentId AND course_id = :courseId", nativeQuery = true)
    void deleteStudentFromCourse(@Param("studentId") int studentId, @Param("courseId") int courseId);

    @Query("SELECT c.students FROM Course c WHERE c.courseName = :courseName")
    List<Student> findStudentsByCourseName(@Param("courseName") String courseName);
}
