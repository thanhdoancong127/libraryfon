package com.ns.borrowing.viewmodel;

import java.time.LocalDate;

public record BorrowingGetVm(
    Long id,
    Long bookId,
    Long userId,
    LocalDate borrowDate,
    LocalDate returnDate
) {

}
