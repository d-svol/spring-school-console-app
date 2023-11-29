package com.example.foxstudent105614;

import com.example.foxstudent105614.controller.SchoolManager;
import com.example.foxstudent105614.service.DbLoadingService;
import com.example.foxstudent105614.runner.Repl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Student105614Application  implements ApplicationRunner {
	private static final Logger logger = LoggerFactory.getLogger(Student105614Application.class);

	private final DbLoadingService dbLoader;
	private final SchoolManager schoolManager;

	public Student105614Application(DbLoadingService dbLoadingService, SchoolManager schoolManager) {
		this.dbLoader = dbLoadingService;
		this.schoolManager = schoolManager;
	}

	@Override
	public void run(ApplicationArguments args) {
		logger.info("Starting the application.");
		dbLoader.load();
		logger.info("Data loaded successfully.");
		new Repl(schoolManager).run();
	}

	public static void main(String[] args) {
		SpringApplication.run(Student105614Application.class, args);
	}
}