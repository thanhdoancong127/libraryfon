package com.ns.notification.service;

import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ns.common.viewmodel.BorrowingMessage;
import com.ns.notification.entity.NotificationEntity;
import com.ns.notification.repository.NotificationRepository;
import com.ns.notification.viewmodel.NotificationsGetVm;
import com.ns.notification.viewmodel.NotificationsGetVm.NotificationGetVm;

@Service
public class NotificationService {

    @Autowired
    private NotificationRepository notificationRepository;

    public void sendNotification(BorrowingMessage borrowingMessage) {
        // Logic to send notification (email, SMS, push notification, etc.)
        System.out.println("Sending notification: " + borrowingMessage);

        // Create Notification Entity converter
        NotificationEntity notification = new NotificationEntity(
            borrowingMessage.userId(),
            borrowingMessage.bookId(),
            "You're request already for borrowing",
            false);

        // Lưu thông báo vào database
        notificationRepository.save(notification);
    }

    public NotificationsGetVm getAllNotifications() {
        return new NotificationsGetVm(notificationRepository.findAll().stream()
            .map(NotificationGetVm::fromEntity).collect(Collectors.toList()));
    }
}
