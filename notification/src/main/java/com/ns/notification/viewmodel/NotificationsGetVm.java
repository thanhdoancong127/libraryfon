package com.ns.notification.viewmodel;

import java.util.List;

import com.ns.notification.entity.NotificationEntity;

import lombok.Builder;

public record NotificationsGetVm(
    List<NotificationGetVm> NotificationGetVm
) {
    @Builder
    public record NotificationGetVm(
        Long id,
        Long userId,
        Long bookId,
        String message,
        boolean isRead
    ) {
        public static NotificationGetVm fromEntity(NotificationEntity entity) {
            return builder()
                .id(entity.getId())
                .bookId(entity.getBookId())
                .userId(entity.getUserId())
                .message(entity.getMessage())
                .isRead(entity.isRead())
                .build();
        }
    }
}
