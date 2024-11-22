package services;

import models.EmailNotification;
import models.NotificationType;

public class NotificationServiceFactory {
    private final EmailNotificationService emailNotificationService;
    private final SMSNotificationService smsNotificationService;

    public NotificationServiceFactory() {
        emailNotificationService = new EmailNotificationService();
        smsNotificationService = new SMSNotificationService();
    }
    public INotifcationService getNotificationService(NotificationType type) {
        switch (type) {
            case EMAIL:
                return emailNotificationService;
            case SMS:
                return smsNotificationService;
            default:
                return null;
        }
    }
}
