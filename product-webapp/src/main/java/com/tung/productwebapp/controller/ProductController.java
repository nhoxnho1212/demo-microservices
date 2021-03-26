package com.tung.productwebapp.controller;

import com.tung.productwebapp.model.Product;
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

import java.util.*;

@Controller
public class ProductController {

    ProductService productService;

    Logger logger = LoggerFactory.getLogger(ProductController.class);

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @RequestMapping(value = "/product-api", method = RequestMethod.GET)
    public ResponseEntity<List<Product>> getAllProductsApi(@RequestParam(name = "name") String name) {
        return new ResponseEntity<>(productService.getByName(name), HttpStatus.OK);
    }
}
