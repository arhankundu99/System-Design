package models;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Group {
    private final String id;
    private String name;
    List<User> users;
    User createdBy;

    public Group(String name, User createdBy) {
        this.id = UUID.randomUUID().toString();
        this.name = name;
        this.createdBy = createdBy;
        this.users = new ArrayList<>();
        this.users.add(createdBy);
    }

    public void addUser(User user) {
        this.users.add(user);
    }

    public List<User> getUsers() {
        return users;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
