package com.example.foxstudent105614.runner;


import com.example.foxstudent105614.controller.SchoolManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Scanner;


public class Repl {
	private static final Logger logger = LoggerFactory.getLogger(Repl.class);

	private final SchoolManager schoolManager;

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
		logger.info("Program started.");
		logger.info(HEADER);
		try (Scanner in = new Scanner(System.in)) {
			Command command;
			do {
				System.out.println(TITLE);
				String code = in.nextLine();
				command = Command.parse(code);
				command.run(schoolManager, in);
			} while (command != Command.QUIT);
		} catch (Exception e) {
			logger.error("An unexpected error occurred: {}", e.getMessage(), e);
		} finally {
			logger.info("Program stopped.");
		}
	}
}