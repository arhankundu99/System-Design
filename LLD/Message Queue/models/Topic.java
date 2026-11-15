package models;

import java.util.UUID;

public class Topic {
    private final String name;
    private final String id;
    public Topic(String name) {
        this.name = name;
        this.id = UUID.randomUUID().toString();
    }

    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }
}
