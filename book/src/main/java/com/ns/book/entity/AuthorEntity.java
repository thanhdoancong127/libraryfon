package com.ns.book.entity;

import java.util.List;

import com.ns.common.entity.AbstractAuditEntity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "authors")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AuthorEntity extends AbstractAuditEntity {
    private String name;
    private String email;
    private String description;
    private String bio;
    private String imageUrl;

    @Transient
    private List<BooksEntity> bookList;

    public AuthorEntity(String name, String email, String description, String bio, String imageUrl) {
        this.name = name;
        this.email = email;
        this.description = description;
        this.bio = bio;
        this.imageUrl = imageUrl;
    }
}
