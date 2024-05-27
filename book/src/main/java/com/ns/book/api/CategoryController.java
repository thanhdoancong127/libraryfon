package com.ns.book.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;

import com.ns.book.service.CategoryService;
import com.ns.book.viewmodel.CategoriesGetVm;
import com.ns.book.viewmodel.CategoryGetVm;
import com.ns.book.viewmodel.CategoryPostVm;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequiredArgsConstructor
@RequestMapping("/category")
@Validated
public class CategoryController {
    private final CategoryService categoryService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public CategoriesGetVm index() {
        return categoryService.getCategories();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CategoryGetVm createCategory(@Valid @RequestBody CategoryPostVm categoryPostVm) {
        return categoryService.createCategory(categoryPostVm);
    }
}
