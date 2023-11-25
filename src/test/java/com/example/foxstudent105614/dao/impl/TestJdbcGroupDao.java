package com.example.foxstudent105614.dao.impl;

import com.example.foxstudent105614.dao.GroupDao;
import com.example.foxstudent105614.model.Group;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;


@JdbcTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Sql(
		scripts = {"classpath:create_table.sql", "classpath:sample_data.sql"},
		executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD
)
@Testcontainers
@ContextConfiguration(classes = TestJdbcGroupDao.class)
@TestPropertySource(locations = "classpath:/application.yml")
public class TestJdbcGroupDao {
	@Autowired
	private NamedParameterJdbcTemplate jdbcTemplate;
	private GroupDao groupDao;

	@Container
	private static final PostgreSQLContainer<?> postgresContainer = new PostgreSQLContainer<>("postgres:14.6")
			.withDatabaseName("test")
			.withUsername("root")
			.withPassword("test");

	@BeforeAll
	static void beforeAll() {
		postgresContainer.start();
	}

	@AfterAll
	static void afterAll() {
		postgresContainer.stop();
	}

	@BeforeEach
	void setUp() {
		groupDao = new JdbcGroupDao(jdbcTemplate);
	}

	@Test
	void findGroupsWithLessOrEqualStudents() {
		int studentCount = 1;
		List<Group> groupList = groupDao.findGroupsWithLessOrEqualStudents(studentCount);

		Group firstGroup = groupList.get(0);
		assertEquals("CC-03", firstGroup.groupName());
	}

	@Test
	void findById() {
		int firstID = 1;
		Optional<Group> group = groupDao.findById(firstID);
		assertTrue(group.isPresent());
		assertEquals(1, group.get().groupId());
		assertEquals("AA-01", group.get().groupName());
	}

	@Test
	void findAll() {
		int groupSize = 3;
		List<Group> groups = groupDao.findAll();
		assertEquals(groupSize, groups.size());
	}

	@Test
	void save() {
		int groupID = 4;
		Group group = new Group(groupID, "NewGroup");
		groupDao.save(group);

		Optional<Group> findGroup = groupDao.findById(groupID);
		assertTrue(findGroup.isPresent());
		assertEquals(groupID, findGroup.get().groupId());
		assertEquals("NewGroup", findGroup.get().groupName());
	}

	@Test
	void update() {
		int groupID = 1;
		Group newGroup = new Group(groupID, "NewGroup");
		groupDao.update(newGroup);

		Optional<Group> findGroup = groupDao.findById(groupID);
		assertTrue(findGroup.isPresent());
		assertEquals(groupID, findGroup.get().groupId());
		assertEquals("NewGroup", findGroup.get().groupName());
	}

	@Test
	void delete() {
		int groupID = 1;
		groupDao.delete(groupID);

		Optional<Group> findGroup = groupDao.findById(groupID);
		assertFalse(findGroup.isPresent());
	}
}