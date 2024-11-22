package services;

import models.User;

import java.util.HashMap;
import java.util.Map;

public class UserService {
    Map<String, User> usersMap;
    public UserService() {
        usersMap = new HashMap<String, User>();
    }

    public void addUser(String name, String email) {
        User user = new User(name, email);
        usersMap.put(user.getId(), user);
    }

    public User getUserById(String id) {
        User user = usersMap.get(id);
        return user;
    }
}
