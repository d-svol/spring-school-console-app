package com.example.foxstudent105614;

import com.example.foxstudent105614.controller.SchoolManager;
import com.example.foxstudent105614.runner.Repl;
import com.example.foxstudent105614.service.DbLoadingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Student105614Application  implements ApplicationRunner {
	private static final Logger logger = LoggerFactory.getLogger(Student105614Application.class);

	@Autowired(required = false)
	private DbLoadingService dbLoader;
	@Autowired(required = false)
	private SchoolManager schoolManager;
	@Value("${isProductionMode:true}")
	private boolean isProductionMode;



	@Override
	public void run(ApplicationArguments args) {
		if(isProductionMode){
			logger.info("Starting the application.");
			dbLoader.load();
			logger.info("Data loaded successfully.");
			new Repl(schoolManager).run();
		}
	}

	public static void main(String[] args) {
		SpringApplication.run(Student105614Application.class, args);
	}
}