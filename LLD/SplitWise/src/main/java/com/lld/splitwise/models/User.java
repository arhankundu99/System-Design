package com.lld.splitwise.models;
import java.util.UUID;
class User {
    int userId;
    String name;
    String email;

    User(int userId, String name, String email) {
        userId = UUID.randomUUID().toString();
        this.emailId = emailId;
        this.name = name;
    }

    // Add getter / setter
}