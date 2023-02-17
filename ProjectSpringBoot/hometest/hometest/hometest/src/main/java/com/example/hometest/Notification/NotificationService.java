package com.example.hometest.Notification;

import java.util.List;

public interface NotificationService {

    List<Notification> getByUserId(long userId);

    Notification getByUserIdAndAccountNumber(long userId, long accountNumber);

    Notification saveNotification(long userId, Notification notification);

    Notification updateNotification(long userId, Notification notification, long accountNumber);

    boolean deleteNotification(long userId, long accountNumber);
}
