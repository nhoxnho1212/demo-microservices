package com.tung.productservice.controller;

import com.tung.productservice.model.entity.Product;
import com.tung.productservice.payload.response.ApiResponse;
import com.tung.productservice.payload.response.ProductResponse;
import com.tung.productservice.service.ProductService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/product")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping
    public ResponseEntity<ApiResponse> getAllProducts() {
        List<Product> result = productService.findAll();

        List<ProductResponse> productResponseList = new ArrayList<>();

        for (Product product : result) {
            ProductResponse productResponse = new ProductResponse();
            BeanUtils.copyProperties(product, productResponse);
            productResponseList.add(productResponse);
        }

        ApiResponse response = new ApiResponse(Boolean.TRUE, productResponseList);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
