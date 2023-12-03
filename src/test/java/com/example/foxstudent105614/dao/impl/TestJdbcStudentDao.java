//package com.example.foxstudent105614.dao.impl;
//
//import com.example.foxstudent105614.dao.StudentDao;
//import com.example.foxstudent105614.model.Student;
//import com.example.foxstudent105614.service.StudentService;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//
//import java.util.Arrays;
//import java.util.List;
//import java.util.Optional;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertTrue;
//import static org.mockito.Mockito.*;
//
//@SpringBootTest(classes = {StudentService.class})
//public class TestJdbcStudentDao {
//	@Autowired
//	private StudentService studentService;
//
//	@MockBean
//	private StudentDao studentDao;
//
//	@Test
//	void findStudentsByCourseName() {
//		String courseName = "Math";
//		List<Student> expectedStudents = Arrays.asList(
//				new Student(1, 1, "NameA", "LastnameA"),
//				new Student(2, 1, "NameA", "LastnameA")
//		);
//		when(studentDao.findStudentsByCourseName(courseName)).thenReturn(expectedStudents);
//		List<Student> actualStudents = studentService.findStudentsByCourseName(courseName);
//		assertEquals(expectedStudents.size(), actualStudents.size());
//	}
//
//	@Test
//	void findById() {
//		int studentId = 1;
//		int groupId = 1;
//		Student expectedStudent = new Student(studentId, groupId, "NameA", "LastnameA");
//		when(studentDao.findById(studentId)).thenReturn(Optional.of(expectedStudent));
//		Optional<Student> actualStudent = studentService.findById(studentId);
//		assertTrue(actualStudent.isPresent());
//		assertEquals(expectedStudent, actualStudent.get());
//	}
//
//	@Test
//	void findAll() {
//		List<Student> expectedStudents = List.of(
//                new Student(1, 1, "NameA", "LastnameA"),
//				new Student(2, 1, "NameB", "LastnameB"),
//				new Student(3, 1, "NameC", "LastnameC")
//        );
//		when(studentDao.findAll()).thenReturn(expectedStudents);
//		List<Student> actualStudents = studentService.findAll();
//		assertEquals(expectedStudents.size(), actualStudents.size());
//	}
//
//	@Test
//	void saveWithoutId() {
//		int groupId = 1;
//		String newName = "NewName";
//		String newLastName = "NewLastName";
//		studentService.save(groupId, newName, newLastName);
//		verify(studentDao, times(1)).save(eq(groupId), eq(newName), eq(newLastName));
//	}
//
//	@Test
//	void testSave() {
//		int studentId = 6;
//		int groupId = 1;
//		Student newStudent = new Student(studentId, groupId, "NewName", "NewLastName");
//		studentService.save(newStudent);
//		verify(studentDao, times(1)).save(newStudent);
//	}
//
//	@Test
//	void update() {
//		int studentId = 1;
//		int groupId = 1;
//		Student student = new Student(studentId, groupId, "NewName", "NewLastName");
//		studentService.update(student);
//		verify(studentDao, times(1)).update(student);
//	}
//
//	@Test
//	void delete() {
//		int studentId = 1;
//		studentService.delete(studentId);
//		verify(studentDao, times(1)).delete(studentId);
//	}
//}