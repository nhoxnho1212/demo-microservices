package com.tung.productwebapp.controller;

import com.tung.productwebapp.model.Product;
import com.tung.productwebapp.payload.request.ApiRequest;
import com.tung.productwebapp.service.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@Controller
public class ProductController {

    RestTemplate restTemplate;

    ProductService productService;

    Logger logger = LoggerFactory.getLogger(ProductController.class);

    @Autowired
    public ProductController(RestTemplate restTemplate, ProductService productService) {
        this.restTemplate = restTemplate;
        this.productService = productService;
    }

    @RequestMapping(value = "/product-api", method = RequestMethod.GET)
    public ResponseEntity<ApiRequest> getAllProductsApi(@RequestParam(name = "name") String name) {

        List<Product> productList = productService.getByName(name);

        ApiRequest apiRequest = new ApiRequest(Boolean.TRUE, productList);
        return new ResponseEntity<>(apiRequest, HttpStatus.OK);
    }
}
