package com.ns.book.viewmodel;

import java.util.List;

import lombok.Builder;

@Builder
public record CategoriesGetVm(List<CategoryGetVm> categories) {
    
}
