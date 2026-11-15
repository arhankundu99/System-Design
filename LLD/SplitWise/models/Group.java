package models;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class Group {
    private String id;
    private String name;
    private Set<User> members;

    public Group(String name, User createdBy) {
        this.id = UUID.randomUUID().toString();
        this.name = name;
        this.members = new HashSet<>();
        this.members.add(createdBy);
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<User> getMembers() {
        return members;
    }

    public void addMember(User user) {
        members.add(user);
    }

    public boolean removeMember(User user) {
        return members.remove(user);
    }

    public boolean isMember(User user) {
        return members.contains(user);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Group)) {
            return false;
        }
        Group other = (Group) obj;
        return id != null && id.equals(other.id);
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
