package models;

import java.util.UUID;

public class Subscriber {
    private final String name;
    private final String id;
    public Subscriber(String name) {
        this.id = UUID.randomUUID().toString();
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }
}
