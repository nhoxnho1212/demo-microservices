package com.tung.productservice.dao.impl;

import com.tung.productservice.config.constant.ErrorMessages;
import com.tung.productservice.dto.ProductDto;
import com.tung.productservice.exception.DatabaseException;
import com.tung.productservice.dao.ProductDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ProductDaoImp implements ProductDao {

    Logger logger = LoggerFactory.getLogger(ProductDaoImp.class);

    @Autowired
    public JdbcTemplate jdbcTemplate;

    @Override
    public List<ProductDto> findAll() {

        String sql = "SELECT id, name, price, category FROM demo_microservice.product ORDER BY id DESC;";

        List<ProductDto> result;

        try {
            result =  jdbcTemplate.query(sql,
                    new BeanPropertyRowMapper<>(ProductDto.class));
        }
        // Has any problem executing the query
        catch (DataAccessException exception) {
            logger.error(exception.getMessage());
            throw new DatabaseException(ErrorMessages.DATABASE_HAS_A_PROBLEM.getMessage());
        }

        return result;
    }

    @Override
    public List<ProductDto> findByName(String name) {
        String searchPattern = "%" + name + "%";
        String sql = String.format("SELECT id, name, price, category " +
                "FROM demo_microservice.product " +
                "WHERE name like '%s';", searchPattern);

        List<ProductDto> result;

        try {
            result =  jdbcTemplate.query(sql,
                    new BeanPropertyRowMapper<>(ProductDto.class));
        }
        // Has any problem executing the query
        catch (DataAccessException exception) {
            logger.error(exception.getMessage());
            throw new DatabaseException(ErrorMessages.DATABASE_HAS_A_PROBLEM.getMessage());
        }

        return result;
    }
}
