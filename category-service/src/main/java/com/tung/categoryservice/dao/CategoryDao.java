package com.tung.categoryservice.dao;

import com.tung.categoryservice.dto.CategoryDto;

import java.util.List;

public interface CategoryDao {
    List<CategoryDto> findAll();
    CategoryDto findById(final String id);
}
