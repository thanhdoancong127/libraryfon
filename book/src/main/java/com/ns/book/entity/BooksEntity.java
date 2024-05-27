package com.ns.book.entity;

import com.ns.common.entity.AbstractAuditEntity;
import com.ns.common.enumeration.Language;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

/**
 * BooksEntity
 * <p>
 * Date: 27/9/2021
 * Time: 10:47 AM
 * <p>
 * Author: namsang
 * URL:
 * Description:
 * <p>
 */
@Entity
@Table(name = "books")
@Getter
@Setter
public class BooksEntity extends AbstractAuditEntity {

    private String title;
    private Double price;

    @ManyToOne
    @JoinColumn(name = "author_id")
    private AuthorEntity author;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private CategoryEntity category;

    @Enumerated(EnumType.ORDINAL)
    private Language language;

    private String description;

    private String imageUrl;
}
