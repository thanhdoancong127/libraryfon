package com.ns.book.viewmodel;

import org.springframework.web.multipart.MultipartFile;

public record BookPutVm(
    String title,
    Double price,
    Long authorId,
    Long categoryId,
    String language,
    String description,
    String imagePath,
    MultipartFile image
) {

}
