package com.ns.notification.listener;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import com.ns.common.viewmodel.BorrowingMessage;
import com.ns.notification.service.NotificationService;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class RabbitMQListener {

    private final NotificationService notificationService; 

    @RabbitListener(queues = "${rabbitmq.queue}", containerFactory = "rabbitListenerContainerFactory")
    public void receiveMessage(BorrowingMessage message) {
        // file json to object
        System.out.println("Received message: " + message);
        notificationService.sendNotification(message);

    }
}
