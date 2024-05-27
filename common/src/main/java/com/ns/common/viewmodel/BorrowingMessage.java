package com.ns.common.viewmodel;

import java.time.LocalDate;

import lombok.Builder;

@Builder
public record BorrowingMessage (
    Long id,
    Long bookId,
    Long userId,
    LocalDate borrowDate,
    LocalDate returnDate
) {

}
