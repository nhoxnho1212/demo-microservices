package com.tung.productservice.service;

import com.tung.productservice.model.entity.Category;
import com.tung.productservice.model.entity.Product;
import java.util.List;

public interface ProductService {
    List<Product> findAll();
    List<Product> findByName(final String name);
}
