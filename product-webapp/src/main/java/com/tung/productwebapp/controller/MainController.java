package com.tung.productwebapp.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tung.productwebapp.model.Category;
import com.tung.productwebapp.model.Product;
import com.tung.productwebapp.payload.request.ApiRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

import java.util.*;

@Controller
public class MainController {

    @Autowired
    RestTemplate restTemplate;

    @RequestMapping("/")
    public ModelAndView getIndexPage() {

        ResponseEntity<ApiRequest> response = restTemplate.getForEntity(
                "http://localhost:8001/category",
                ApiRequest.class
        );
        ApiRequest apiRequestCategory = response.getBody();
        List<Category> categoryList = new ArrayList<>();

        if (apiRequestCategory.getSuccess()) {
            ObjectMapper mapper = new ObjectMapper();
            categoryList = mapper.convertValue(apiRequestCategory.getMessage(), new TypeReference<List<Category>>() {});
        }

//        ResponseEntity<ApiRequest> responseProductList= restTemplate.getForEntity(
//                "http://localhost:8081/product",
//                ApiRequest.class
//        );
//        ApiRequest apiRequestProduct = responseProductList.getBody();
//        List<Product> productList = new ArrayList<>();
//
//        if (apiRequestProduct != null && apiRequestProduct.getSuccess()) {
//            ObjectMapper mapper = new ObjectMapper();
//            productList = mapper.convertValue(apiRequestProduct.getMessage(), new TypeReference<List<Product>>() {});
//        }
//
        ModelAndView modelAndView = new ModelAndView("index");
        modelAndView.addObject(categoryList);
//        modelAndView.addObject(productList);

        return modelAndView;

    }
}
