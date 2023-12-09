package com.example.foxstudent105614.dao.impl;

import com.example.foxstudent105614.dao.GroupDao;
import com.example.foxstudent105614.dao.GroupRepository;
import com.example.foxstudent105614.dao.StudentRepository;
import com.example.foxstudent105614.model.Course;
import com.example.foxstudent105614.model.Group;
import jakarta.persistence.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Repository
public class JpaGroupDao implements GroupDao {
    private static final Logger logger = LoggerFactory.getLogger(JpaCourseDao.class);
    private final GroupRepository groupRepository;

    @Autowired
    public JpaGroupDao(GroupRepository groupRepository) {
        this.groupRepository = groupRepository;
    }

    @Override
    public List<Group> findGroupsWithLessOrEqualStudents(int maxStudentCount) {
        try {
            return groupRepository.findGroupsWithLessOrEqualStudents(maxStudentCount);
        } catch (Exception e) {
            logger.error("Error find groups with less or equal students '{}'", maxStudentCount, e);
            return Collections.emptyList();
        }
    }

    @Override
    public Optional<Group> findById(int id) {
        return groupRepository.findById((long) id);
    }

    @Override
    public List<Group> findAll() {
        try {
            return groupRepository.findAll();
        } catch (Exception e) {
            logger.error("Error executing 'findAll' database query: ", e);
            return Collections.emptyList();
        }
    }

    @Override
    public void save(Group entity) {
        try {
            groupRepository.save(entity);
        } catch (EntityExistsException e) {
            logger.error("Error save group with ID '{}'", entity.getGroupId(), e);
        }
    }

    @Override
    public void update(Group entity) {
        try {
            groupRepository.save(entity);
        } catch (EntityExistsException e) {
            logger.error("Error save group with ID '{}'", entity.getGroupId(), e);
        }
    }

    @Override
    public void deleteById(int id) {
        try {
            groupRepository.deleteById((long) id);
        } catch (EntityExistsException e) {
            logger.error("Error delete group with ID '{}'", id, e);
        }
    }

    @Override
    public void delete(Group entity) {
        try {
            groupRepository.delete(entity);
        } catch (EntityExistsException e) {
            logger.error("Error delete group '{}'", entity.getGroupName(), e);
        }
    }
}