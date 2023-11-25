package com.example.foxstudent105614;

import com.example.foxstudent105614.runner.DbLoader;
import com.example.foxstudent105614.runner.Repl;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Student105614Application  implements ApplicationRunner {
	private final DbLoader dbLoader;
	private final Repl repl;

	public Student105614Application(DbLoader dbLoader, Repl repl) {
		this.dbLoader = dbLoader;
		this.repl = repl;
	}

	@Override
	public void run(ApplicationArguments args) {
		dbLoader.load();
		repl.run();
	}

	public static void main(String[] args) {
		SpringApplication.run(Student105614Application.class, args);
	}
}