package com.tung.productservice.dao;

import com.tung.productservice.dto.ProductDto;
import com.tung.productservice.dto.paging.Page;
import com.tung.productservice.payload.request.ProductPagingRequest;

import java.util.List;
public interface ProductDao {
    List<ProductDto> findAll();
    List<ProductDto> findByName(String name);
    Page<ProductDto> findAndPaging(ProductPagingRequest productPagingRequest);
}
