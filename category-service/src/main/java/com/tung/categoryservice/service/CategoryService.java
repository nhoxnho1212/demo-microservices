package com.tung.categoryservice.service;

import com.tung.categoryservice.dto.CategoryDto;

import java.util.List;

public interface CategoryService {
    List<CategoryDto> findAll();
    CategoryDto findById(final String id);
}
