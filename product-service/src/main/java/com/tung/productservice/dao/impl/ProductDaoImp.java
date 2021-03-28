package com.tung.productservice.dao.impl;

import com.tung.productservice.dto.paging.Page;
import com.tung.productservice.exception.ServiceError;
import com.tung.productservice.dto.ProductDto;
import com.tung.productservice.dto.paging.Direction;
import com.tung.productservice.exception.DatabaseException;
import com.tung.productservice.dao.ProductDao;
import com.tung.productservice.payload.request.ProductPagingRequest;
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

    public JdbcTemplate jdbcTemplate;

    @Autowired
    public ProductDaoImp(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<ProductDto> findAll() {

        String sql = "SELECT id, name, price, category FROM product ORDER BY id DESC;";

        List<ProductDto> result;

        try {
            result =  jdbcTemplate.query(sql,
                    new BeanPropertyRowMapper<>(ProductDto.class));
        }
        // Has any problem executing the query
        catch (DataAccessException exception) {
            logger.error(exception.getMessage());
            throw new DatabaseException(ServiceError.DATABASE_HAS_A_PROBLEM);
        }

        return result;
    }

    @Override
    public List<ProductDto> findByName(String name) {
        String searchPattern = "%" + name + "%";
        String sql = String.format("SELECT id, name, price, category " +
                "FROM product " +
                "WHERE name like '%s';", searchPattern);

        List<ProductDto> result;

        try {
            result =  jdbcTemplate.query(sql,
                    new BeanPropertyRowMapper<>(ProductDto.class));
        }
        // Has any problem executing the query
        catch (DataAccessException exception) {
            logger.error(exception.getMessage());
            throw new DatabaseException(ServiceError.DATABASE_HAS_A_PROBLEM);
        }

        return result;
    }

    @Override
    public Page<ProductDto> findAndPaging(ProductPagingRequest productPagingRequest) {
        String columnName;
        Direction direction;
        Integer total = 0;

        if (productPagingRequest.getOrder().size() > 0) {
            columnName = productPagingRequest.getOrder().get(0).getColumnName();
            direction = productPagingRequest.getOrder().get(0).getDir();
        }
        else {
            columnName = "id";
            direction = Direction.asc;
        }

        String conditionCategory =  "";

        if (productPagingRequest.getCategory().size() > 0) {
            conditionCategory = String.format("LOCATE(p.category, '%s') > 0 AND", productPagingRequest.getCategory().toString());
        }

        String searchPattern = "%" + productPagingRequest.getName() + "%";
        String sql = String.format("SELECT * " +
                        "FROM product p " +
                        "WHERE %s p.name LIKE '%s' " +
                        "order by %s %s " +
                        "LIMIT %d " +
                        "OFFSET %d;",
                conditionCategory,
                searchPattern,
                columnName,
                direction.name(),
                productPagingRequest.getLength(),
                productPagingRequest.getStart()
                );

        String sqlTotal = String.format("SELECT COUNT(*) " +
                        "FROM product p " +
                        "WHERE %s p.name LIKE '%s' " +
                        "order by %s %s;",
                conditionCategory,
                searchPattern,
                columnName,
                direction.name()
        );

        List<ProductDto> resultQuery;

        try {
            resultQuery =  jdbcTemplate.query(sql,
                    new BeanPropertyRowMapper<>(ProductDto.class));
            total =  jdbcTemplate.queryForObject(sqlTotal, Integer.class);

        }
        // Has any problem executing the query
        catch (DataAccessException exception) {
            logger.error(exception.getMessage());
            throw new DatabaseException(ServiceError.DATABASE_HAS_A_PROBLEM);
        }

        return new Page<ProductDto>(resultQuery, total);

    }
}
