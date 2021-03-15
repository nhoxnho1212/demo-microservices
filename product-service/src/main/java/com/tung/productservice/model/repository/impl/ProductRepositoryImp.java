package com.tung.productservice.model.repository.impl;

import com.tung.productservice.config.constant.ErrorMessages;
import com.tung.productservice.exception.DatabaseException;
import com.tung.productservice.model.entity.Product;
import com.tung.productservice.model.repository.ProductRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;

@Repository
public class ProductRepositoryImp implements ProductRepository {

    Logger logger = LoggerFactory.getLogger(ProductRepositoryImp.class);

    @Autowired
    public JdbcTemplate jdbcTemplate;

    @Override
    public List<Product> findAll() {

        String sql = "SELECT id, name, price, category FROM demo_microservice.product ORDER BY id DESC;";

        List<Product> result;

        try {
            result =  jdbcTemplate.query(sql,
                    new BeanPropertyRowMapper<>(Product.class));
        }
        // Has any problem executing the query
        catch (DataAccessException exception) {
            logger.error(exception.getMessage());
            throw new DatabaseException(ErrorMessages.DATABASE_HAS_A_PROBLEM.getMessage());
        }

        return result;
    }

    @Override
    public List<Product> findByName(String name) {
        String searchPattern = "%" + name + "%";
        String sql = String.format("SELECT id, name, price, category " +
                "FROM demo_microservice.product " +
                "WHERE name like '%s';", searchPattern);

        List<Product> result;

        try {
            result =  jdbcTemplate.query(sql,
                    new BeanPropertyRowMapper<>(Product.class));
        }
        // Has any problem executing the query
        catch (DataAccessException exception) {
            logger.error(exception.getMessage());
            throw new DatabaseException(ErrorMessages.DATABASE_HAS_A_PROBLEM.getMessage());
        }

        return result;
    }
}
