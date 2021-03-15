package com.tung.categoryservice.model.repository.impl;

import com.tung.categoryservice.config.constant.ErrorMessages;
import com.tung.categoryservice.exception.DatabaseException;
import com.tung.categoryservice.model.entity.Category;
import com.tung.categoryservice.model.repository.CategoryRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CategoryRepositoryImpl implements CategoryRepository {

    Logger logger = LoggerFactory.getLogger(CategoryRepositoryImpl.class);

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<Category> findAll() {
        String sql ="SELECT id, name FROM category ORDER BY id DESC;";
        List<Category> result;
        try {
            result = jdbcTemplate.query(sql,
                    new BeanPropertyRowMapper<>(Category.class));
        }
        // Has any problem executing the query
        catch (DataAccessException exception) {
            logger.error(exception.getMessage());
            throw new DatabaseException(ErrorMessages.DATABASE_HAS_A_PROBLEM.getMessage());
        }
        return result;

    }

    @Override
    public Category findById(String id) {
        String sql = String.format("SELECT id, name FROM demo_microservice.category WHERE id LIKE '%s';", id);
        Category category;
        try {
            category = jdbcTemplate.queryForObject(sql,
                    new BeanPropertyRowMapper<>(Category.class)
            );
        }
        // Don't found any Category with id
        catch (IncorrectResultSizeDataAccessException exception) {
            return null;
        }
        // Has any problem executing the query
        catch (DataAccessException exception) {
            logger.error(exception.getMessage());
            throw new DatabaseException(ErrorMessages.DATABASE_HAS_A_PROBLEM.getMessage());
        }
        return category;
    }

}
