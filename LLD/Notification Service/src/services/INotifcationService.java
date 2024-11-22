package services;

import models.Notification;

public interface INotifcationService {
    public void handle(Notification notification);
}
