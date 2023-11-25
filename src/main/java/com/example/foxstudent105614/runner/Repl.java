package com.example.foxstudent105614.runner;


import com.example.foxstudent105614.service.SchoolManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Scanner;

@Component
public class Repl {
	private final SchoolManager schoolManager;

	@Autowired
	public Repl(SchoolManager schoolManager) {
		this.schoolManager = schoolManager;
	}

	private static final String HEADER = """
			Available functions:
				a. Find all groups with less or equals student count;
				b. Find all students related to course with given name;
				c. Add new student;
				d. Delete student by STUDENT_ID;
				e. Add a student to the course (from a list);
				f. Remove the student from one of his or her courses;
				q. Quit the program.
			""";

	private static final String TITLE = "Select function (a, b, c, d, e, f or q) and press Enter: ";

	public void run() {
		System.out.println(HEADER);
		try (Scanner in = new Scanner(System.in)) {
			Command command;
			do {
				System.out.println(TITLE);
				String code = in.nextLine();
				command = Command.parse(code);
				command.run(schoolManager, in);
			} while (command != Command.QUIT);
		}
	}
}