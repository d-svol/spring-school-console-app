package com.example.foxstudent105614.runner;






import com.example.foxstudent105614.controller.SchoolManager;

import java.util.Arrays;
import java.util.Scanner;

public enum Command {
	FIND_ALL_GROUPS("a") {
		@Override
		public void run(SchoolManager schoolManager, Scanner in) {
			schoolManager.findGroupsByStudentCount(in);
		}
	},

	FIND_ALL_STUDENTS("b") {
		@Override
		public void run(SchoolManager schoolManager, Scanner in) {
			schoolManager.findStudentsByCourseName(in);
		}
	},

	ADD_NEW_STUDENT("c") {
		@Override
		public void run(SchoolManager schoolManager, Scanner in) {
			schoolManager.addStudent(in);
		}
	},

	DELETE_STUDENT("d") {
		@Override
		public void run(SchoolManager schoolManager, Scanner in) {
			schoolManager.deleteStudentById(in);
		}
	},

	ADD_COURSES_FOR_STUDENT("e") {
		@Override
		public void run(SchoolManager schoolManager, Scanner in) {
			schoolManager.addCoursesForStudent(in);
		}
	},

	REMOVE_STUDENT_FROM_COURSE("f") {
		@Override
		public void run(SchoolManager schoolManager, Scanner in) {
			schoolManager.removeStudentToCourse(in);
		}
	},

	QUIT("q") {
		@Override
		public void run(SchoolManager schoolManager, Scanner in) {
			System.out.println("Exit");
		}
	},

	UNKNOWN("") {
		@Override
		public void run(SchoolManager schoolManager, Scanner in) {
			System.out.println("Unknown command requested");
		}
	};

	private final String code;

	Command(String code) {
		this.code = code;
	}

	public static Command parse(String code) {
		return Arrays.stream(values())
				.filter(cmdType -> cmdType.code.equals(code))
				.findFirst()
				.orElse(UNKNOWN);
	}

	public abstract void run(SchoolManager schoolManager, Scanner in);
}