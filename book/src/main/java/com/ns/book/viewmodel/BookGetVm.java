package com.ns.book.viewmodel;

import com.ns.book.entity.BooksEntity;

import lombok.Builder;

@Builder
public record BookGetVm(
    Long id,
    String title,
    Double price,
    String author,
    String category,
    String language,
    String description,
    String imageUrl
) {
    public static BookGetVm fromEntity(BooksEntity entity, String bookImagePublicPath) {
        return new BookGetVm(
            entity.getId(),
            entity.getTitle(),
            entity.getPrice(),
            entity.getAuthor().getName(),
            entity.getCategory().getName(),
            entity.getLanguage().getAbbreviation(),
            entity.getDescription(),
            bookImagePublicPath + "/" + entity.getImageUrl()
        );
    }

    public static BookGetVm fromEntity(BooksEntity entity) {
        return new BookGetVm(
            entity.getId(),
            entity.getTitle(),
            entity.getPrice(),
            entity.getAuthor().getName(),
            entity.getCategory().getName(),
            entity.getLanguage().name(),
            entity.getDescription(),
            entity.getImageUrl()
        );
    }
}
