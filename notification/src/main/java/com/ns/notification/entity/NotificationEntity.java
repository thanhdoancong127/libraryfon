package com.ns.notification.entity;

import com.ns.common.entity.AbstractAuditEntity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "notification")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class NotificationEntity extends AbstractAuditEntity {

    private Long userId;
    private Long bookId;
    private String message;
    private boolean isRead;
}
