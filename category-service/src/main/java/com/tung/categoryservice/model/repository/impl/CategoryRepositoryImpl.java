package com.tung.categoryservice.model.repository.impl;

import com.tung.categoryservice.model.entity.Category;
import com.tung.categoryservice.model.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CategoryRepositoryImpl implements CategoryRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<Category> findAll() {
        String sql ="SELECT id, name FROM category ORDER BY id DESC;";
        return jdbcTemplate.query(sql,
                new BeanPropertyRowMapper<>(Category.class));

    }

    @Override
    public Category findById(long id) {
        return null;
    }

}
