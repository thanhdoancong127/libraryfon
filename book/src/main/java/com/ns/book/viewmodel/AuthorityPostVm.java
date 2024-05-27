package com.ns.book.viewmodel;

import org.springframework.web.multipart.MultipartFile;

import com.ns.book.entity.AuthorEntity;

public record AuthorityPostVm(
    String name,
    String email,
    String description,
    String bio,
    MultipartFile image    
) {
    public AuthorityGetVm toGetVm(Long id, String imageUrl) {
        return new AuthorityGetVm(id, name, description, bio, imageUrl);
    }

    public AuthorEntity toEntity(String imageUrl) {
        return new AuthorEntity(name, email, description, bio, imageUrl);
    }
}
