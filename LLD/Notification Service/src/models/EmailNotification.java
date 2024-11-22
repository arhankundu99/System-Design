package models;

import java.util.List;

public class EmailNotification extends Notification {
    private final List<String> to;
    private final List<String> cc;
    public EmailNotification(String from, List<String> to, String message, List<String> cc) {
        super(NotificationType.SMS, from, message);
        this.to = to;
        this.cc = cc;
    }

    public List<String> getTo() {
        return to;
    }

    public List<String> getCc() {
        return cc;
    }
}
