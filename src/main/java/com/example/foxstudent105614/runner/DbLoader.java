package com.example.foxstudent105614.runner;

import com.example.foxstudent105614.exception.DbException;
import com.example.foxstudent105614.model.Course;
import com.example.foxstudent105614.model.Group;
import com.example.foxstudent105614.model.Student;
import com.example.foxstudent105614.service.DataGenerator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;


@Component
public class DbLoader {
    public static final String INSERT_GROUPS =
            "INSERT INTO groups (group_name) VALUES (?)";
    private static final String INSERT_STUDENTS =
            "INSERT INTO students (first_name, last_name, group_id) VALUES (?, ?, ?)";
    private static final String INSERT_COURSES =
            "INSERT INTO courses (course_name, course_description) VALUES (?, ?)";
    private static final String INSERT_STUDENT_COURSE =
            "INSERT INTO student_course (student_id, course_id) VALUES (?, ?)";

    private static final String SCRIPT_FILE_NAME = "create_table.sql";

    private static final Logger log = LogManager.getLogger(DbLoader.class);
    private final JdbcTemplate jdbcTemplate;

    public DbLoader(JdbcTemplate jdbcTemplate) {
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
                        Objects.requireNonNull(DbLoader.class.getResourceAsStream("/" + DbLoader.SCRIPT_FILE_NAME))))) {
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
                    ps.setString(1, course.courseName());
                    ps.setString(2, course.courseDescription());
                });
    }


    private void insertGroupsIntoDatabase(List<Group> groups) {
        jdbcTemplate.batchUpdate(INSERT_GROUPS, groups, 10,
                (ps, group) -> ps.setString(1, group.groupName()));
    }

    private void insertStudentsIntoDatabase(List<Student> students) {
        jdbcTemplate.batchUpdate(INSERT_STUDENTS, students, 10,
                (ps, student) -> {
                    ps.setString(1, student.firstName());
                    ps.setString(2, student.lastName());
                    ps.setInt(3, student.groupId());
                });
    }

    private void insertStudentCoursesRelationsIntoDatabase(Map<Student, List<Course>> studentCourseMap) {
        List<Object[]> batchArgs = new ArrayList<>();

        studentCourseMap.forEach((student, courses) -> {
            int studentId = student.studentId();
            for (Course course : courses) {
                int courseId = course.courseId();
                batchArgs.add(new Object[]{studentId, courseId});
            }
        });
        jdbcTemplate.batchUpdate(INSERT_STUDENT_COURSE, batchArgs);
    }
}