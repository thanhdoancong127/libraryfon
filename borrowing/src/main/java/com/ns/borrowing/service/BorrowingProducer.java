package com.ns.borrowing.service;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.ns.common.viewmodel.BorrowingMessage;

@Service
public class BorrowingProducer {
    private final RabbitTemplate rabbitTemplate;

    @Value("${rabbitmq.exchange}")
    private String exchange;

    @Value("${rabbitmq.routingkey}")
    private String routingKey;

    public BorrowingProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void sendBorrowingMessage(BorrowingMessage message) {
        rabbitTemplate.convertAndSend(exchange, routingKey, message);
    }
}
