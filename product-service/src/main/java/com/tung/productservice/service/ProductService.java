package com.tung.productservice.service;

import com.tung.productservice.dto.ProductDto;

import java.util.List;

public interface ProductService {
    List<ProductDto> findAll();
    List<ProductDto> findByName(final String name);
}
