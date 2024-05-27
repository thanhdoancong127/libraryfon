package com.ns.book.viewmodel;

import com.ns.book.entity.CategoryEntity;

public record CategoryGetVm(
    Long id,
    String name,
    String description
) {
    public static CategoryGetVm fromEntity(CategoryEntity entity) {
        return new CategoryGetVm(
            entity.getId(),
            entity.getName(),
            entity.getDescription()
        );
    }
}