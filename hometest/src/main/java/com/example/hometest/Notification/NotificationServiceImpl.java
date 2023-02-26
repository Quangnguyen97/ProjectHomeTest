package com.example.hometest.Notification;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.hometest.Module.*;

@Service
public class NotificationServiceImpl implements NotificationService {

    @Autowired
    private NotificationRepository notificationRepository;

    public NotificationServiceImpl(NotificationRepository notificationRepository) {
        super();
        this.notificationRepository = notificationRepository;
    }

    @Override
    public List<String> pushPromotion() {
        try {
            return notificationRepository.pushPromotionNotification();
        } catch (Exception e) {
            throw new ResourceException(e.getMessage());
        }
    }

    @Override
    public List<Notification> pushAll() {
        try {
            return notificationRepository.pushAllNotification();
        } catch (Exception e) {
            throw new ResourceException(e.getMessage());
        }
    }
}
