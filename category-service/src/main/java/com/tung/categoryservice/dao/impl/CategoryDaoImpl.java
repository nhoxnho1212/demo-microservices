package com.tung.categoryservice.dao.impl;

import com.tung.categoryservice.config.constant.ErrorMessages;
import com.tung.categoryservice.dao.CategoryDao;
import com.tung.categoryservice.exception.DatabaseException;
import com.tung.categoryservice.dto.CategoryDto;
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
public class CategoryDaoImpl implements CategoryDao {

    Logger logger = LoggerFactory.getLogger(CategoryDaoImpl.class);

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<CategoryDto> findAll() {
        String sql ="SELECT id, name FROM category ORDER BY id DESC;";
        List<CategoryDto> result;
        try {
            result = jdbcTemplate.query(sql,
                    new BeanPropertyRowMapper<>(CategoryDto.class));
        }
        // Has any problem executing the query
        catch (DataAccessException exception) {
            logger.error(exception.getMessage());
            throw new DatabaseException(ErrorMessages.DATABASE_HAS_A_PROBLEM.getMessage());
        }
        return result;

    }

    @Override
    public CategoryDto findById(String id) {
        String sql = String.format("SELECT id, name FROM demo_microservice.category WHERE id = '%s';", id);
        CategoryDto categoryDto;
        try {
            categoryDto = jdbcTemplate.queryForObject(sql,
                    new BeanPropertyRowMapper<>(CategoryDto.class)
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
        return categoryDto;
    }

}
