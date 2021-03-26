package com.tung.productwebapp.service.impl;

import com.tung.productwebapp.gateway.CategoryClient;
import com.tung.productwebapp.gateway.ProductClient;
import com.tung.productwebapp.model.Category;
import com.tung.productwebapp.model.Product;
import com.tung.productwebapp.model.ProductRequest;
import com.tung.productwebapp.service.CategoryService;
import com.tung.productwebapp.service.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {

    private ProductClient productClient;

    private CategoryClient categoryClient;

    Logger logger = LoggerFactory.getLogger(ProductServiceImpl.class);

    @Autowired
    public ProductServiceImpl(ProductClient productClient, CategoryClient categoryClient) {
        this.productClient = productClient;
        this.categoryClient = categoryClient;
    }

    public ProductServiceImpl() {
    }

    @Override
    public List<Product> getByName(String name) {

        List<Category> categoryList = categoryClient.getAll();
        List<Product> productList = new ArrayList<>();

        List<ProductRequest> productListResponse =  productClient.findByName(name);

                for (ProductRequest productRequest : productListResponse) {
                    Product product = new Product();

                    product.setId(productRequest.getId());
                    Category category = categoryList.stream()
                            .filter(c -> c.getId().contains(productRequest.getCategory()))
                            .limit(1)
                            .collect(Collectors.toList())
                            .get(0);
                    product.setCategory(category);
                    product.setPrice(productRequest.getPrice());
                    product.setName(productRequest.getName());

                    productList.add(product);
                }

        return productList;
    }
}
