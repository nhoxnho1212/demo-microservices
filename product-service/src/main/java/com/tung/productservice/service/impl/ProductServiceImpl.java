package com.tung.productservice.service.impl;

import com.tung.productservice.model.entity.Product;
import com.tung.productservice.model.repository.ProductRepository;
import com.tung.productservice.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Override
    public List<Product> findAll() {
        List<Product> result = productRepository.findAll();

        if (null == result) {
            result = new ArrayList<>();
        }

        return result;
    }

    @Override
    public List<Product> findByName(final String name) {
        List<Product> result = productRepository.findByName(name);

        if (null == result) {
            result = new ArrayList<>();
        }
        return result;
    }
}
