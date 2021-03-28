package com.tung.productwebapp.service;

import com.tung.productwebapp.model.Product;
import com.tung.productwebapp.model.SearchAndPagingProductRequest;
import com.tung.productwebapp.model.paging.Page;

import java.util.*;
public interface ProductService {
    List<Product> getByName(String name);
    Page<Product> searchAndPaging(SearchAndPagingProductRequest request);
}
