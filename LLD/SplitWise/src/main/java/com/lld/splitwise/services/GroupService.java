package com.lld.splitwise.services;

class GroupService {
    GroupRepository groupRepository;
    public GroupService() {
        this.groupRepository = new GroupRepository();
    }

    int addGroup(String name, String imageURL, String desc, List<Integer> userIds) {
        Group group = new Group(name, imageURL, desc, userIds);
        return groupRepository.addGroup(group);
    }

    int addUserToGrop(int userId, int groupId) {
        return groupRepository.addUserToGrop(userId, groupId);
    }
}