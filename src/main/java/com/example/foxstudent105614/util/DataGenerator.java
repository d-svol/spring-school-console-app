package com.example.foxstudent105614.util;

import com.example.foxstudent105614.model.Course;
import com.example.foxstudent105614.model.Group;
import com.example.foxstudent105614.model.Student;
import com.github.javafaker.Faker;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.IntSupplier;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class DataGenerator {
	private static final int MAX_SUM_GROUPS = 10;
	private static final int MAX_SUM_COURSES = 10;
	private static final int MAX_SUM_STUDENTS_IN_GROUP = 30;
	private static final int MAX_SUM_COURSES_FOR_STUDENT = 3;
	private static final int MIN_SUM_COURSES_FOR_STUDENT = 1;

	private static final Random RANDOM = new Random();
	private static final Faker FAKER = new Faker();

	private final List<Group> groups;
	private final List<Course> courses;
	private final List<Student> students;
	private final Map<Student, List<Course>> studentCourseMap;

	public DataGenerator() {
		this.groups = generateGroups();
		this.courses = generateCourses();
		this.students = generateStudents(groups);
		this.studentCourseMap = generateStudentCourseMap(students, courses);
	}

	private static Map<Student, List<Course>> generateStudentCourseMap(List<Student> students, List<Course> courses){
		Map<Student, List<Course>> studentCourseMap = new HashMap<>();

		Collections.shuffle(students);

		for (Student student : students) {
			List<Course> randomCourses = generateRandomCoursesWithoutDuplicates(courses);
			studentCourseMap.put(student, randomCourses);
		}
		return studentCourseMap;
	}


	private static List<Group> generateGroups() {
		IntSupplier nextGroupId = idGenerator();
		return Stream.generate(() -> new Group(
						nextGroupId.getAsInt(),
						String.format("%s-%02d",
								FAKER.letterify("??"),
								FAKER.number().numberBetween(1, 99))
				)).limit(MAX_SUM_GROUPS)
				.collect(Collectors.toList());
	}

	private static List<Course> generateCourses() {
		IntSupplier nextCourseId = idGenerator();
		return Stream.generate(() -> new Course(
						nextCourseId.getAsInt(),
						FAKER.bothify("Course ????"),
						FAKER.bothify("Description ?????????")
				)).limit(MAX_SUM_COURSES)
				.collect(Collectors.toList());
	}

	private static List<Student> generateStudents(List<Group> groups) {
		IntSupplier nextStudentId = idGenerator();


		Set<String> uniqueFirstNames = IntStream.range(0, 100_000)
				.mapToObj(i -> FAKER.name().firstName())
				.collect(Collectors.toSet());

		Iterator<String> iterator = uniqueFirstNames.iterator();

		return groups.stream()
				.flatMap(group ->
						IntStream.range(0, MAX_SUM_STUDENTS_IN_GROUP)
								.mapToObj(i -> {
									String firstName = iterator.next();
									iterator.remove();
									String lastName = FAKER.name().lastName();
									return new Student(nextStudentId.getAsInt(), group.groupId(), firstName, lastName);
								})
				)
				.collect(Collectors.toList());
	}

	public static List<Course> generateRandomCoursesWithoutDuplicates(List<Course> courses) {
		int numCoursesPerStudent = RANDOM.nextInt(MAX_SUM_COURSES_FOR_STUDENT) + MIN_SUM_COURSES_FOR_STUDENT;
		return IntStream.range(0, numCoursesPerStudent)
				.mapToObj(i -> {
					Collections.shuffle(courses);
					return courses.get(0);
				})
				.collect(Collectors.toList());
	}

	private static IntSupplier idGenerator() {
		return new AtomicInteger(1)::getAndIncrement;
	}

	public List<Group> getGroups() {
		return groups;
	}

	public List<Course> getCourses() {
		return courses;
	}

	public List<Student> getStudents() {
		return students;
	}

	public Map<Student, List<Course>> getStudentCourseMap() {
		return studentCourseMap;
	}
}
