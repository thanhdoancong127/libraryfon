package com.ns.book.entity;

import com.ns.common.entity.AbstractAuditEntity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Table(name = "categories")
@Entity
@Setter
@Getter
@NoArgsConstructor
public class CategoryEntity extends AbstractAuditEntity {
    private String name;
    private String description;

    public CategoryEntity(String name, String description) {
        this.name = name;
        this.description = description;
    }
}
