package com.example.hometest.Notification;

import java.util.List;

public interface NotificationService {

    // Account have balance >= 200
    List<String> pushPromotion();

    List<Notification> pushAll();
}
