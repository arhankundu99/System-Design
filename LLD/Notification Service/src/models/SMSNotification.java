package models;

public class SMSNotification extends Notification {
    private final String to;
    public SMSNotification(String from, String to, String message) {
        super(NotificationType.SMS, from, message);
        this.to = to;
    }

    public String getTo() {
        return to;
    }
}
