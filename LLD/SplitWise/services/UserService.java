package services;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import models.User;

public class UserService {
    private final Map<String, User> users;
    private final Set<String> emails;

    public UserService() {
        this.users = new HashMap<>();
        this.emails = new HashSet<>();
    }

    public boolean registerUser(String name, String email) {
        if (emails.contains(email)) {
            return false;
        }
        User user = new User(name, email);
        users.put(user.getId(), user);
        emails.add(email);
        return true;
    }

    public User getUserById(String userId) {
        return users.get(userId);
    }
}
