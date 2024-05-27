package com.ns.book.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.ns.book.repository.CategoryRepository;
import com.ns.book.viewmodel.CategoriesGetVm;
import com.ns.book.viewmodel.CategoryGetVm;
import com.ns.book.viewmodel.CategoryPostVm;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoriesGetVm getCategories() {
        List<CategoryGetVm> categoryGetVms = categoryRepository.findAll().stream()
            .map(CategoryGetVm::fromEntity)
            .collect(Collectors.toList());

        return CategoriesGetVm.builder()
            .categories(categoryGetVms)
            .build();
    }

    public CategoryGetVm createCategory(CategoryPostVm categoryPostVm) {
        return CategoryGetVm.fromEntity(categoryRepository.save(categoryPostVm.toEntity()));
    }

}
