package com.ns.borrowing.viewmodel;

import lombok.Builder;

@Builder
public record BookDetailVm(
    Long id,
    String title,
    Double price,
    String author,
    String category,
    String language,
    String description,
    String imageUrl
) {

}
