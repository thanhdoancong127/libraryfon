package com.ns.book.viewmodel;

import com.ns.book.entity.AuthorEntity;

public record AuthorityGetVm (
    Long id,    
    String name,
    String description,
    String bio,
    String imageUrl
) {
    public static AuthorityGetVm fromEntity(AuthorEntity entity) {
        return new AuthorityGetVm(
            entity.getId(),
            entity.getName(),
            entity.getDescription(),
            entity.getBio(),
            entity.getImageUrl()
        );
    }

    public static AuthorityGetVm fromEntity(AuthorEntity entity, String imageUrl) {
        return new AuthorityGetVm(
            entity.getId(),
            entity.getName(),
            entity.getDescription(),
            entity.getBio(),
            imageUrl
        );
    }
}
