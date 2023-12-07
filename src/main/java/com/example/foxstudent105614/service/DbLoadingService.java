package com.example.foxstudent105614.service;

import com.example.foxstudent105614.exception.DbException;
import com.example.foxstudent105614.model.Course;
import com.example.foxstudent105614.model.Group;
import com.example.foxstudent105614.model.Student;
import com.example.foxstudent105614.util.DataGenerator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;


@Service
public class DbLoadingService {
    public static final String INSERT_GROUPS =
            "INSERT INTO groups (group_name) VALUES (?)";
    private static final String INSERT_STUDENTS =
            "INSERT INTO students (first_name, last_name, group_id) VALUES (?, ?, ?)";
    private static final String INSERT_COURSES =
            "INSERT INTO courses (course_name, course_description) VALUES (?, ?)";
    private static final String INSERT_STUDENT_COURSE =
            "INSERT INTO student_course (student_id, course_id) VALUES (?, ?)";

    private static final String SCRIPT_FILE_NAME = "create_table.sql";

    private static final Logger log = LogManager.getLogger(DbLoadingService.class);

    private final JdbcTemplate jdbcTemplate;

    public DbLoadingService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    public void load() {
        try {
            loadDb();
        } catch (IOException e) {
            log.error("Error loading database: " + e.getMessage(), e);
            throw new DbException("Error loading database: " + e);
        }
    }

    private void loadDb() throws IOException {
        executeSQLScript();
        populateDB();
    }

    private void executeSQLScript() throws IOException {
        try (BufferedReader bufferedReader = new BufferedReader(
                new InputStreamReader(
                        Objects.requireNonNull(DbLoadingService.class.getResourceAsStream("/" + DbLoadingService.SCRIPT_FILE_NAME))))) {
            StringBuilder script = new StringBuilder();
            String line;

            while ((line = bufferedReader.readLine()) != null) {
                script.append(line).append(" ");
                if (line.endsWith(";")) {
                    jdbcTemplate.execute(script.toString());
                    script.setLength(0);
                }
            }
        }
    }

    private void populateDB() {
        DataGenerator generatorDB = new DataGenerator();

        insertCoursesIntoDatabase(generatorDB.getCourses());
        insertGroupsIntoDatabase(generatorDB.getGroups());
        insertStudentsIntoDatabase(generatorDB.getStudents());
        insertStudentCoursesRelationsIntoDatabase(generatorDB.getStudentCourseMap());
    }

    private void insertCoursesIntoDatabase(List<Course> courses) {
        jdbcTemplate.batchUpdate(INSERT_COURSES, courses, 10,
                (ps, course) -> {
                    ps.setString(1, course.getCourseName());
                    ps.setString(2, course.getCourseDescription());
                });
    }


    private void insertGroupsIntoDatabase(List<Group> groups) {
        jdbcTemplate.batchUpdate(INSERT_GROUPS, groups, 10,
                (ps, group) -> ps.setString(1, group.getGroupName()));
    }

    private void insertStudentsIntoDatabase(List<Student> students) {
        jdbcTemplate.batchUpdate(INSERT_STUDENTS, students, 10,
                (ps, student) -> {
                    ps.setString(1, student.getFirstName());
                    ps.setString(2, student.getLastName());
                    ps.setInt(3, student.getGroup().getGroupId());
                });
    }

    private void insertStudentCoursesRelationsIntoDatabase(Map<Student, List<Course>> studentCourseMap) {
        List<Object[]> batchArgs = new ArrayList<>();

        studentCourseMap.forEach((student, courses) -> {
            int studentId = student.getStudentId();
            for (Course course : courses) {
                int courseId = course.getCourseId();
                batchArgs.add(new Object[]{studentId, courseId});
            }
        });
        jdbcTemplate.batchUpdate(INSERT_STUDENT_COURSE, batchArgs);
    }
}