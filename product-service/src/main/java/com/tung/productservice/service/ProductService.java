package com.tung.productservice.service;

import com.tung.productservice.dto.ProductDto;
import com.tung.productservice.dto.paging.Page;
import com.tung.productservice.payload.request.ProductPagingRequest;

import java.util.List;

public interface ProductService {
    List<ProductDto> findAll();
    List<ProductDto> findByName(final String name);
    Page<ProductDto> findAndPaging(ProductPagingRequest productPagingRequest);
}
