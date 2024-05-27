package com.ns.notification.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ns.notification.service.NotificationService;
import com.ns.notification.viewmodel.NotificationsGetVm;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/notification")
@RequiredArgsConstructor
public class NotificationController {

    private final NotificationService notificationService;

    @GetMapping
    public NotificationsGetVm getNotifications() {
        return notificationService.getAllNotifications();
    }
}
