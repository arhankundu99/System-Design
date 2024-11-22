package controllers;
import models.User;
import java.util.List;
import java.util.ArrayList;
import exceptions.*;
import services.UserService;

public class UserController {
    
    private UserService userService;
    public UserController() {
        userService = new UserService();
    }

    public User getUserById(String id) {
        if (id == null || id.length() == 0) {
            throw new InvalidArgumentException("Id of an user must not be null");
        }

        return userService.getUserById(id);
    }

    public User addUser(String name, String email) {
        if (name == null || email == null || name.length() == 0 || email.length() == 0) {
            throw new InvalidUserDetailsException("Name or email should be non empty.");
        }

        User user = userService.addUser(name, email);
        return user;
    }
}