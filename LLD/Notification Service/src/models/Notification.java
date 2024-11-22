package models;

import java.util.UUID;

public class Notification {
    private String id;
    private NotificationType type;
    private String from;
    private String message;

    public Notification(NotificationType type, String from, String message) {
        this.id = UUID.randomUUID().toString();
        this.type = type;
        this.from = from;
        this.message = message;
    }

    public String getId() {
        return id;
    }

    public NotificationType getType() {
        return type;
    }

    public String getFrom() {
        return from;
    }

    public String getMessage() {
        return message;
    }
}
