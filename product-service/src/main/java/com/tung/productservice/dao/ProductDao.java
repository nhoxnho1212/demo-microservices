package com.tung.productservice.dao;

import com.tung.productservice.dto.ProductDto;

import java.util.List;
public interface ProductDao {
    List<ProductDto> findAll();
    List<ProductDto> findByName(String name);
}
