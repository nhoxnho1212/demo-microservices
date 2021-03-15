package com.tung.productservice.model.repository;

import com.tung.productservice.model.entity.Product;
import java.util.List;
public interface ProductRepository {
    List<Product> findAll();
    List<Product> findByName(String name);
}
