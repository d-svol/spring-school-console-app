package com.example.foxstudent105614.dao.impl;

import com.example.foxstudent105614.dao.GroupDao;
import com.example.foxstudent105614.model.Group;
import jakarta.persistence.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Repository
public class JpaGroupDao implements GroupDao {
    private static final String FIND_ALL_BY_COUNT =
            "SELECT g FROM Group g " +
                    "LEFT JOIN g.students s " +
                    "GROUP BY g.groupId, g.groupName " +
                    "HAVING COUNT(s) <= :maxStudentCount";

    private static final String FIND_ALL = "SELECT * FROM groups";

    @PersistenceContext
    private EntityManager entityManager;
    private static final Logger logger = LoggerFactory.getLogger(JpaCourseDao.class);

    @Override
    public List<Group> findGroupsWithLessOrEqualStudents(int maxStudentCount) {
        TypedQuery<Group> query = entityManager.createQuery(FIND_ALL_BY_COUNT, Group.class);
        query.setParameter("maxStudentCount", maxStudentCount);
        try {
            return query.getResultList();
        } catch (NoResultException e) {
            return Collections.emptyList();
        }
    }

    @Override
    public Optional<Group> findById(int id) {
        Group group = entityManager.find(Group.class, id);
        if (group != null) {
            return Optional.of(group);
        } else {
            logger.info("Group with ID '{}' not found", id);
            return Optional.empty();
        }
    }

    @Override
    public List<Group> findAll() {
        try {
            return entityManager.createQuery(FIND_ALL, Group.class).getResultList();
        } catch (Exception e) {
            logger.error("Error executing 'findAll' database query: ", e);
            return Collections.emptyList();
        }
    }

    @Override
    public void save(Group entity) {
        try {
            entityManager.persist(entity);
        } catch (EntityExistsException e) {
            logger.error("Error save group with ID '{}'", entity.getGroupId(), e);
        }
    }

    @Override
    public void update(Group entity) {
        entityManager.merge(entity);
    }

    @Override
    public void delete(int id) {
        Group group = entityManager.find(Group.class, id);
        if (group != null) {
            entityManager.remove(group);
        } else {
            logger.error("Group with ID '{}' not found. Unable to delete.", id);
        }
    }
}