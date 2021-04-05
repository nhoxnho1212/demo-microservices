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
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CategoryDaoImpl implements CategoryDao {

    private static final Logger LOGGER = LoggerFactory.getLogger(CategoryDaoImpl.class);

    private static final String FIND_ALL_CATEGORIES_SQL = "SELECT id, name FROM category";
    private static final String FIND_BY_ID_SQL = "SELECT id, name FROM category WHERE id = :id";

    private static final String ID_PARAMETER = "id";

    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Autowired
    public CategoryDaoImpl(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    public List<CategoryDto> findAll() {
        List<CategoryDto> result;
        try {
            result = namedParameterJdbcTemplate.query(FIND_ALL_CATEGORIES_SQL,
                    new BeanPropertyRowMapper<>(CategoryDto.class));
        }
        // Has any problem executing the query
        catch (DataAccessException exception) {
            LOGGER.error(exception.getMessage());
            throw new DatabaseException(ErrorMessages.DATABASE_HAS_A_PROBLEM.getMessage());
        }
        return result;
    }

    @Override
    public CategoryDto findById(String id) {
        CategoryDto categoryDto;
        try {
            categoryDto = namedParameterJdbcTemplate.queryForObject(FIND_BY_ID_SQL,
                    new MapSqlParameterSource(ID_PARAMETER, id),
                    new BeanPropertyRowMapper<>(CategoryDto.class)
            );
        }
        catch (IncorrectResultSizeDataAccessException exception) {
            LOGGER.debug("No category found for request id: {}", id);
            LOGGER.trace(exception.getMessage());
            return null;
        }
        catch (DataAccessException exception) {
            LOGGER.error(exception.getMessage());
            throw new DatabaseException(ErrorMessages.DATABASE_HAS_A_PROBLEM.getMessage());
        }
        return categoryDto;
    }

}
