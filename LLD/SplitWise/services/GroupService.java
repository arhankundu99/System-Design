package services;

import java.util.*;
import models.Group;
import models.User;

public class GroupService {
    Map<String, Group> groupsMap;

    public GroupService() {
        this.groupsMap = new HashMap<>();
    }

    public Group createGroup(String name, User createdBy) {
        Group group = new Group(name, createdBy);
        groupsMap.put(group.getId(), group);
        return group;
    }

    public Group getGroupById(String groupId) {
        return groupsMap.get(groupId);
    }

    public boolean addMemberToGroup(String groupId, User user) {
        Group group = groupsMap.get(groupId);
        if (group != null) {
            group.addMember(user);
            return true;
        }
        return false;
    }

    public boolean removeMemberFromGroup(String groupId, User user) {
        Group group = groupsMap.get(groupId);
        if (group != null) {
            return group.removeMember(user);
        }
        return false;
    }

    public boolean isUserInGroup(String groupId, User user) {
        Group group = groupsMap.get(groupId);
        if (group != null) {
            return group.isMember(user);
        }
        return false;
    }

    public Set<User> getGroupMembers(String groupId) {
        Group group = groupsMap.get(groupId);
        if (group != null) {
            return group.getMembers();
        }
        return Collections.emptySet();
    }

    public List<Group> getAllUserGroups(User user) {
        List<Group> userGroups = new ArrayList<>();
        for (Group group : groupsMap.values()) {
            if (group.isMember(user)) {
                userGroups.add(group);
            }
        }
        return userGroups;
    }
}
