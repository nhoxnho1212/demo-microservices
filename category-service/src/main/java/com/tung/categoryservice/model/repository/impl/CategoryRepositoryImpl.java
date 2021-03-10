package com.tung.categoryservice.model.repository.impl;

import com.tung.categoryservice.model.entity.Category;
import com.tung.categoryservice.model.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
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
        String sql = String.format("SELECT id, name FROM demo_microservice.category WHERE id=%d;", id);
        Category category;
        try {
            category = jdbcTemplate.queryForObject(sql,
                    new BeanPropertyRowMapper<>(Category.class)
            );
        } catch (EmptyResultDataAccessException exception) {
           return null;
        }
        return category;
    }

}
