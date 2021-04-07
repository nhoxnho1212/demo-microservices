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
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ProductDaoImp implements ProductDao {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProductDaoImp.class);

    private static final String FIND_ALL_PRODUCTS_SQL = "SELECT id, name, price, category FROM product ORDER BY id DESC";
    private static final String FIND_BY_NAME_SQL = "SELECT * FROM product WHERE name like :name";
    private static final String FIND_AND_PAGING_PRODUCT_SQL = "SELECT p.id, p.name, p.price, p.category " +
            "FROM product p " +
            "left join category c on p.category = c.id " +
            "WHERE %s p.name LIKE :name " +
            "ORDER BY %s %s " +
            "LIMIT :limit " +
            "OFFSET :startedAt";
    private static final String NUMBER_OF_PRODUCT_SEARCHING_SQL ="SELECT COUNT(*) FROM product WHERE %s name LIKE :name";

    private static final String NAME_PARAMETER = "name";
    private static final String LIMIT_PARAMETER = "limit";
    private static final String STARTED_AT_PARAMETER = "startedAt";

    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Autowired
    public ProductDaoImp(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    @Override
    public List<ProductDto> findAll() {

        List<ProductDto> result;

        try {
            result =  namedParameterJdbcTemplate.query(FIND_ALL_PRODUCTS_SQL,
                    new BeanPropertyRowMapper<>(ProductDto.class));
        }
        // Has any problem executing the query
        catch (DataAccessException exception) {
            LOGGER.error(exception.getMessage());
            throw new DatabaseException(ServiceError.DATABASE_HAS_A_PROBLEM);
        }

        return result;
    }

    @Override
    public List<ProductDto> findByName(String name) {
        String searchPattern = "%" + name + "%";

        List<ProductDto> result;

        try {
            result =  namedParameterJdbcTemplate.query(FIND_BY_NAME_SQL,
                    new MapSqlParameterSource(NAME_PARAMETER, searchPattern),
                    new BeanPropertyRowMapper<>(ProductDto.class));
        }
        // Has any problem executing the query
        catch (DataAccessException exception) {
            LOGGER.error(exception.getMessage());
            throw new DatabaseException(ServiceError.DATABASE_HAS_A_PROBLEM);
        }

        return result;
    }

    @Override
    public Page<ProductDto> findAndPaging(ProductPagingRequest productPagingRequest) {
        final String searchPattern = "%" + productPagingRequest.getName() + "%";
        String columnName = "p.id";
        Direction direction = Direction.asc;
        Integer total = 0;
        String conditionCategory =  "";

        if (productPagingRequest.getOrder().size() > 0) {
            if (productPagingRequest.getOrder().get(0).getColumnName().equals("category")) {
                columnName = "c.name";
            }
            else  {
                columnName = "p." + productPagingRequest.getOrder().get(0).getColumnName();
            }

            direction = productPagingRequest.getOrder().get(0).getDir();
        }

        if (productPagingRequest.getCategory().size() > 0) {
            conditionCategory = String.format("LOCATE(p.category, '%s') > 0 AND", productPagingRequest.getCategory().toString());
        }

        MapSqlParameterSource param = new MapSqlParameterSource();
        param.addValue(NAME_PARAMETER, searchPattern);
        param.addValue(LIMIT_PARAMETER, productPagingRequest.getLength());
        param.addValue(STARTED_AT_PARAMETER, productPagingRequest.getStart());

        List<ProductDto> resultQuery;
        String query = String.format(FIND_AND_PAGING_PRODUCT_SQL, conditionCategory, columnName, direction.name());
        String queryTotal = String.format(NUMBER_OF_PRODUCT_SEARCHING_SQL, conditionCategory, columnName, direction.name());

        try {
            resultQuery =  namedParameterJdbcTemplate.query(query, param,
                    new BeanPropertyRowMapper<>(ProductDto.class));
            total =  namedParameterJdbcTemplate.queryForObject(queryTotal, param, Integer.class);
        }
        // Has any problem executing the query
        catch (DataAccessException exception) {
            LOGGER.error(exception.getMessage());
            throw new DatabaseException(ServiceError.DATABASE_HAS_A_PROBLEM);
        }

        return new Page<>(resultQuery, total);
    }
}
