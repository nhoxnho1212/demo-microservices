package com.tung.categoryservice.service;

import com.tung.categoryservice.model.entity.Category;

import java.util.List;

public interface CategoryService {
    List<Category> findAll();
    Category findById(final String id);
}
