package com.example.foxstudent105614.service;

import com.example.foxstudent105614.dao.GroupDao;
import com.example.foxstudent105614.model.Group;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class GroupService {
    private final GroupDao groupDao;

    @Autowired
    public GroupService(GroupDao groupDao) {
        this.groupDao = groupDao;
    }

    public List<Group> findGroupsWithLessOrEqualStudents(int maxStudentCount) {
        return groupDao.findGroupsWithLessOrEqualStudents(maxStudentCount);
    }

    public Optional<Group> findById(int groupId) {
        return groupDao.findById(groupId);
    }

    public List<Group> findAll() {
        return groupDao.findAll();
    }

    public void save(Group group) {
        groupDao.save(group);
    }

    public void update(Group group) {
        groupDao.update(group);
    }

    public void deleteById(int groupId) {
        groupDao.deleteById(groupId);
    }
}
