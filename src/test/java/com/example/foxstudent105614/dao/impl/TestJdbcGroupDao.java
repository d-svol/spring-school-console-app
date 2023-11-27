package com.example.foxstudent105614.dao.impl;

import com.example.foxstudent105614.dao.GroupDao;
import com.example.foxstudent105614.model.Group;
import com.example.foxstudent105614.service.GroupService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@SpringBootTest(classes = {GroupService.class})
public class TestJdbcGroupDao {
	@Autowired
	private GroupService groupService;

	@MockBean
	private GroupDao groupDao;

	@Test
	void findGroupsWithLessOrEqualStudents() {
		int studentCount = 2;
		List<Group> expectedGroups = Arrays.asList(
				new Group(1, "AA-01"),
				new Group(2, "BB-02")
		);
		when(groupDao.findGroupsWithLessOrEqualStudents(studentCount)).thenReturn(expectedGroups);

		List<Group> actualGroups = groupService.findGroupsWithLessOrEqualStudents(studentCount);
		assertEquals(expectedGroups.size(), actualGroups.size());
	}

	@Test
	void findById() {
		int firstID = 1;
		Group expectedGroup = new Group(1, "AA-01");
		when(groupDao.findById(firstID)).thenReturn(Optional.of(expectedGroup));
		Optional<Group> actualGroup = groupService.findById(firstID);

		assertTrue(actualGroup.isPresent());
		assertEquals(expectedGroup.groupId(), actualGroup.get().groupId());
		assertEquals(expectedGroup.groupName(), actualGroup.get().groupName());
	}

	@Test
	void findAll() {
		int expectedIndex = 1;
		List<Group> expectedGroups = Arrays.asList(
				new Group(1, "AA-01"),
				new Group(2, "BB-02")
		);

		when(groupDao.findAll()).thenReturn(expectedGroups);
		List<Group> actualGroups = groupService.findAll();
		assertEquals(expectedGroups.size(), actualGroups.size());
		assertEquals(expectedGroups.get(expectedIndex).groupId(), actualGroups.get(expectedIndex).groupId());
		assertEquals(expectedGroups.get(expectedIndex).groupName(), actualGroups.get(expectedIndex).groupName());
	}

	@Test
	void save() {
		int groupID = 4;
		Group groupToSave = new Group(groupID, "NewGroup");
		groupService.save(groupToSave);
		verify(groupDao, times(1)).save(groupToSave);
	}

	@Test
	void update() {
		int groupID = 1;
		Group updatedGroup = new Group(groupID, "UpdatedGroup");
		groupService.update(updatedGroup);
		verify(groupDao, times(1)).update(updatedGroup);
	}

	@Test
	void delete() {
		int groupID = 1;
		groupService.delete(groupID);
		verify(groupDao, times(1)).delete(groupID);
	}
}