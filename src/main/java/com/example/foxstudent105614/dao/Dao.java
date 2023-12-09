package com.example.foxstudent105614.dao;

import com.example.foxstudent105614.model.Course;

import java.util.List;
import java.util.Optional;

public interface Dao<T> {
	Optional<T> findById(int id);

	List<T> findAll();

	void save(T entity);

	void update(T entity);

	void delete(T entity);

	void deleteById(int id);
}
