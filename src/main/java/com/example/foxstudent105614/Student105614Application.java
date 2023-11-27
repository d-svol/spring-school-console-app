package com.example.foxstudent105614;

import com.example.foxstudent105614.controller.SchoolManager;
import com.example.foxstudent105614.service.DbLoadingService;
import com.example.foxstudent105614.runner.Repl;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Student105614Application  implements ApplicationRunner {
	private final DbLoadingService dbLoader;
	private final SchoolManager schoolManager;

	public Student105614Application(DbLoadingService dbLoadingService, SchoolManager schoolManager) {
		this.dbLoader = dbLoadingService;
		this.schoolManager = schoolManager;
	}

	@Override
	public void run(ApplicationArguments args) {
		dbLoader.load();
		new Repl(schoolManager).run();
	}

	public static void main(String[] args) {
		SpringApplication.run(Student105614Application.class, args);
	}
}