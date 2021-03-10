package com.tung.categoryservice.model.repository;

import com.tung.categoryservice.model.entity.Category;

import java.util.List;

public interface CategoryRepository {
    List<Category> findAll();
    Category findById(final long id);
}
