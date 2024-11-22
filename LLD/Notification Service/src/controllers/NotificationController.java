package controllers;

import models.Notification;
import services.INotifcationService;
import services.NotificationServiceFactory;

public class NotificationController {
    NotificationServiceFactory notificationServiceFactory;
    public NotificationController() {
        this.notificationServiceFactory = new NotificationServiceFactory();
    }

    public void handleNotification(Notification notification) {
        INotifcationService notifcationService = notificationServiceFactory.getNotificationService(notification.getType());
        notifcationService.handle(notification);
    }
}
