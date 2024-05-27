package com.ns.borrowing.viewmodel;

import java.time.LocalDate;

import com.ns.borrowing.entity.BorrowingEntity;
import com.ns.common.viewmodel.BorrowingMessage;

import lombok.Builder;

@Builder
public record BorrowingPostVm(
    Long bookId,
    Long userId
) {

    public static BorrowingPostVm fromMessage(BorrowingMessage borrowingMessage) {
        return new BorrowingPostVm(borrowingMessage.bookId(), borrowingMessage.userId());
    }

    // To entity
    public BorrowingEntity toBorrowingEntity() {
        return new BorrowingEntity(bookId, userId, LocalDate.now(), null);
    }
}
