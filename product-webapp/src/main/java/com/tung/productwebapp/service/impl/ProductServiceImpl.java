package com.tung.productwebapp.service.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tung.productwebapp.model.Category;
import com.tung.productwebapp.model.Product;
import com.tung.productwebapp.payload.request.ApiRequest;
import com.tung.productwebapp.service.CategoryService;
import com.tung.productwebapp.service.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    Environment environment;

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    CategoryService categoryService;

    Logger logger = LoggerFactory.getLogger(ProductServiceImpl.class);

    @Override
    public List<Product> getByName(String name) {

        String productServiceUri = environment.getProperty("product-service.api.uri");
        List<Category> categoryList = categoryService.getAll();
        List<Product> productList = new ArrayList<>();

        try {
            ResponseEntity<ApiRequest> response = restTemplate.getForEntity(
                    productServiceUri + "/search?name=" + name,
                    ApiRequest.class
            );
            ApiRequest apiRequestProduct = response.getBody();

            List<LinkedHashMap> productListResponse = (List<LinkedHashMap>) apiRequestProduct.getMessage();

            if (apiRequestProduct != null && apiRequestProduct.getSuccess()) {
                for (LinkedHashMap productRequest : productListResponse) {
                    Product product = new Product();
                    product.setId(new Long(productRequest.get("id").toString()));

                    Category category = categoryList.stream()
                            .filter(c -> c.getId().contains(productRequest.get("category").toString()))
                            .limit(1)
                            .collect(Collectors.toList())
                            .get(0);
                    product.setCategory(category);

                    product.setPrice((Double.valueOf(productRequest.get("price").toString())));
                    product.setName(productRequest.get("name").toString());
                    productList.add(product);
                }
            }
        }
        catch (RestClientException exception) {
            logger.error(exception.getMessage());
        }

        return productList;
    }
}
