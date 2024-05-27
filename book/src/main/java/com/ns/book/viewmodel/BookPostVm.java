package com.ns.book.viewmodel;

import org.springframework.web.multipart.MultipartFile;

import lombok.Builder;

@Builder
public record BookPostVm(
    String title,
    Double price,
    Long authorId,
    Long categoryId,
    String language,
    String description,
    MultipartFile image
) {

}
