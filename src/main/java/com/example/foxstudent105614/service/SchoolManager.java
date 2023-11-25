package com.example.foxstudent105614.service;

import com.example.foxstudent105614.exception.UserExitException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.Scanner;

@Controller
public class SchoolManager {
    private static final String EMPTY_FIELD_ERROR_MESSAGE = "This field cannot be empty. Please enter a valid value";
    private static final String INVALID_FORMAT = "Invalid format. Please enter a valid format";
    private final SchoolService schoolService;

    @Autowired
    public SchoolManager(SchoolService schoolService) {
        this.schoolService = schoolService;
    }


    public void findGroupsByStudentCount(Scanner scanner) {
        boolean validInput = false;
        System.out.print("Enter the maximum number of students for the group (or 'q' to exit): ");
        while (!validInput) {
            String input = scanner.nextLine().trim();
            if (input.equalsIgnoreCase("q")) {
                break;
            }

            try {
                int maxStudentCount = Integer.parseInt(input);
                schoolService.findGroupsByStudentCount(maxStudentCount);
                validInput = true;
            } catch (NumberFormatException e) {
                System.out.println(INVALID_FORMAT);
            }
        }
    }

    public void findStudentsByCourseName(Scanner scanner) {
        boolean validInput = false;
        while (!validInput) {
            System.out.print("Enter the name of the course (or 'q' to exit): ");
            String courseName = scanner.nextLine().trim();
            if (courseName.equalsIgnoreCase("q")) {
                return;
            } else if (courseName.isEmpty()) {
                System.out.println(EMPTY_FIELD_ERROR_MESSAGE);
            } else {
                schoolService.findStudentsByCourseName(courseName);
                validInput = true;
            }
        }
    }

    public void addStudent(Scanner scanner) {
        try {
            String firstName = getFirstNameInput(scanner);
            String lastName = getLastNameInput(scanner);
            int groupId = getIdInput(scanner);

            schoolService.addStudent(groupId, firstName, lastName);
        } catch (UserExitException e) {
            System.out.println(e.getMessage());
        }
    }


    public void deleteStudentById(Scanner scanner) {
        while (true) {
            System.out.print("Enter STUDENT_ID to delete (or 'q' to exit): ");
            String input = scanner.nextLine().trim();

            if (input.equalsIgnoreCase("q")) {
                break;
            }

            try {
                int studentId = Integer.parseInt(input);
                schoolService.deleteStudentById(studentId);
                break;
            } catch (NumberFormatException e) {
                System.out.println(EMPTY_FIELD_ERROR_MESSAGE);
            }
        }
    }

    public void addCoursesForStudent(Scanner scanner) {
        try {
            int studentId = getIdInput(scanner);
            int courseListId = getIdInput(scanner);

            schoolService.saveStudentInCourse(studentId, courseListId);
        } catch (UserExitException e) {
            System.out.println(e.getMessage());
        }
    }

    public void removeStudentToCourse(Scanner scanner) {
        try {
            System.out.println("Select a student by ID");
            int studentId = getIdInput(scanner);
            System.out.println("Select a course by ID");
            int courseId = getIdInput(scanner);
            schoolService.deleteStudentFromCourse(studentId, courseId);
        } catch (UserExitException e) {
            System.out.println(e.getMessage());
        }
    }

    private String getFirstNameInput(Scanner scanner) throws UserExitException {
        while (true) {
            System.out.println("Enter firstname (or 'q' to exit):");
            String input = scanner.nextLine().trim();
            if (input.equals("q")) {
                throw new UserExitException("User exited input.");
            } else if (input.isEmpty()) {
                System.out.println(EMPTY_FIELD_ERROR_MESSAGE);
            } else {
                return input;
            }
        }
    }

    private String getLastNameInput(Scanner scanner) throws UserExitException {
        while (true) {
            System.out.println("Enter lastname (or 'q' to exit):");
            String input = scanner.nextLine().trim();
            if (input.equals("q")) {
                throw new UserExitException("User exited input.");
            } else if (input.isEmpty()) {
                System.out.println(EMPTY_FIELD_ERROR_MESSAGE);
            } else {
                return input;
            }
        }
    }

    private int getIdInput(Scanner scanner) throws UserExitException {
        while (true) {
            System.out.println("Enter id (or 'q' to exit):");
            String input = scanner.nextLine().trim();
            if (input.equals("q")) {
                throw new UserExitException("User exited input.");
            } else if (input.isEmpty()) {
                System.out.println(EMPTY_FIELD_ERROR_MESSAGE);
            } else {
                try {
                    return Integer.parseInt(input);
                } catch (NumberFormatException e) {
                    System.out.println(INVALID_FORMAT);
                }
            }
        }
    }
}
