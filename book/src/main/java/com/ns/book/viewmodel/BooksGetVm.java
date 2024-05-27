package com.ns.book.viewmodel;

import java.util.List;

public record BooksGetVm(
    List<BookGetVm> books
) {

}
