package services;

import models.Group;
import models.User;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GroupService {
    Map<String, Group> groupsMap;
    public GroupService() {
        groupsMap = new HashMap<>();
    }

    public void createGroup(String groupName, User createdBy) {
        Group group = new Group(groupName, createdBy);
        groupsMap.put(groupName, group);
    }

    public void addUserInGroup(String groupId, User user) {
        Group group = groupsMap.get(groupId);
        group.addUser(user);
    }

    public List<User> getUsersByGroupId(String groupId) {
        Group group = groupsMap.get(groupId);
        return group.getUsers();
    }

    public Group getGroupById(String groupId) {
        return groupsMap.get(groupId);
    }
}
