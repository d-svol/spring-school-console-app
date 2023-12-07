package com.example.foxstudent105614.dao.impl;


import com.example.foxstudent105614.dao.GroupDao;
import com.example.foxstudent105614.model.Group;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;


@DataJpaTest(properties = {"isProductionMode  = false"})
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Sql(
        scripts = {"classpath:create_table.sql", "classpath:sample_data.sql"},
        executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD
)
public class TestJdbcGroupDao {
    @Autowired
    private GroupDao groupDao;

    @TestConfiguration
    public static class Configuration {
        @Bean
        JpaGroupDao jpaGroupDao() {
            return new JpaGroupDao();
        }
    }

    @Test
    void findGroupsWithLessOrEqualStudents() {
        int groupSize = 3;
        int maxStudentCount = 10;
        List<Group> groups = groupDao.findGroupsWithLessOrEqualStudents(maxStudentCount);
        assertEquals(groupSize, groups.size());
    }

    @Test
    void findById() {
        int firstID = 1;
        Optional<Group> group = groupDao.findById(firstID);
        assertNotNull(group.orElse(null));
        assertEquals(firstID, group.get().getGroupId());
        assertEquals("AA-01", group.get().getGroupName());
    }

    @Test
    void findAll() {
        List<Group> groups = groupDao.findAll();
        assertEquals(3, groups.size());
    }

    @Test
    void save() {
        int groupID = 4;
        Group newGroup = new Group("Test-Group");
        groupDao.save(newGroup);

        Optional<Group> foundGroup = groupDao.findById(groupID);
        assertNotNull(foundGroup.orElse(null));
        assertEquals(groupID, foundGroup.get().getGroupId());
        assertEquals("Test-Group", foundGroup.get().getGroupName());
    }

    @Test
    void update() {
        int groupID = 1;
        Group updatedGroup = new Group(groupID, "Updated-Group");
        groupDao.update(updatedGroup);

        Optional<Group> foundGroup = groupDao.findById(groupID);
        assertNotNull(foundGroup.orElse(null));
        assertEquals(groupID, foundGroup.get().getGroupId());
        assertEquals("Updated-Group", foundGroup.get().getGroupName());
    }

    @Test
    void delete() {
        int groupID = 1;
        groupDao.delete(groupID);
        Optional<Group> foundGroup = groupDao.findById(groupID);
        assertFalse(foundGroup.isPresent());
    }
}