package com.tung.productservice.service.impl;

import com.tung.productservice.dto.ProductDto;
import com.tung.productservice.dao.ProductDao;
import com.tung.productservice.dto.paging.Page;
import com.tung.productservice.payload.request.ProductPagingRequest;
import com.tung.productservice.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    private ProductDao productDao;

    @Autowired
    public ProductServiceImpl(ProductDao productDao) {
        this.productDao = productDao;
    }

    public ProductServiceImpl() {
    }

    @Override
    public List<ProductDto> findAll() {
        List<ProductDto> result = productDao.findAll();

        if (null == result) {
            result = new ArrayList<>();
        }

        return result;
    }

    @Override
    public List<ProductDto> findByName(final String name) {
        List<ProductDto> result = productDao.findByName(name);

        if (null == result) {
            result = new ArrayList<>();
        }
        return result;
    }

    @Override
    public Page<ProductDto> findAndPaging(ProductPagingRequest productPagingRequest) {
        String name = productPagingRequest.getName();
        name = name.trim();
        // Remove special characters
        name = name.replaceAll("[^\\w+]", "");
        productPagingRequest.setName(name);

        Page<ProductDto> result = productDao.findAndPaging(productPagingRequest);

        return result;
    }
}
