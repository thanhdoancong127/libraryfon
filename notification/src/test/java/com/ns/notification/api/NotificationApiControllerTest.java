package com.ns.notification.api;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

import com.ns.common.viewmodel.BorrowingMessage;
import com.ns.notification.base.BaseSpecificMTest;
import com.ns.notification.viewmodel.NotificationsGetVm;

public class NotificationApiControllerTest extends BaseSpecificMTest {
    
    @Test
    void shouldTestGetAllNotificationsAfterReceiveMessage() {

        String exchange = "borrowing.exchange";
        String routingKey = "borrowing.#";

        BorrowingMessage borrowingMessage = BorrowingMessage.builder()
            .bookId(1L)
            .userId(1L)
            .borrowDate(LocalDate.now())
            .returnDate(LocalDate.now().plusDays(1)).build();

        // Gửi tin nhắn
        rabbitTemplate.convertAndSend(exchange, routingKey, borrowingMessage);

        // Kiem tra tin nhap
        NotificationsGetVm notificationsGetVm = testRestTemplate.getForObject("/notification", NotificationsGetVm.class);
        assert notificationsGetVm != null;
        assert notificationsGetVm.notificationGetVm().size() > 0;
        assert notificationsGetVm.notificationGetVm().get(0).bookId() == 1L;
        assert notificationsGetVm.notificationGetVm().get(0).userId()  == 1L;
        assert notificationsGetVm.notificationGetVm().get(0).isRead() == false;
        assert "You're request already for borrowing".equals(notificationsGetVm.notificationGetVm().get(0).message());
    }
}
