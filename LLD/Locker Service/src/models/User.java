package models;

import java.util.UUID;

public class User {
    private final String id;
    private final String name;
    private final String email;

    public User(String name, String email) {
        if (name == null || email == null || name.isEmpty() || email.isEmpty()) {
            throw new IllegalArgumentException("User details cannot be empty");
        }

        this.name = name;
        this.email = email;
        this.id = UUID.randomUUID().toString();
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }
}
