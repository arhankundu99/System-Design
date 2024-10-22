package com.lld.splitwise.models;

import java.util.UUID;
import com.lld.splitwise.models.User;

class Group {
    int id;
    String name;
    String imageURL;
    String desc;

    List<User> users;

    Group(String name, String imageURL, String desc, List<User> users) {
        this.name = name;
        this.imageURL = imageURL;
        this.desc = desc;
        if (users != null) {
            this.users = users;
        }
    }

    int addUser(User user) {

    }

    int removeUser(int userId) {

    }

    // Other getter and setters
}