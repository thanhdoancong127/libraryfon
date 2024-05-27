package com.ns.book.viewmodel;

import com.ns.book.entity.CategoryEntity;

import jakarta.validation.constraints.NotEmpty;

public record CategoryPostVm(
    @NotEmpty(message = "is required")
    String name,
    String description
) {
    public CategoryEntity toEntity() {
        return new CategoryEntity(name, description);
    }
}
