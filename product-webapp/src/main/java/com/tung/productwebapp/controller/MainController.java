package com.tung.productwebapp.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tung.productwebapp.model.Category;
import com.tung.productwebapp.model.Product;
import com.tung.productwebapp.payload.request.ApiRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

import java.util.*;

@Controller
public class MainController {

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    Environment environment;

    Logger logger = LoggerFactory.getLogger(MainController.class);

    @RequestMapping("/")
    public ModelAndView getIndexPage() {

        List<Category> categoryList = new ArrayList<>();
        try {
            ResponseEntity<ApiRequest> response = restTemplate.getForEntity(
                    environment.getProperty("category-service.api.uri"),
                    ApiRequest.class
            );
            ApiRequest apiRequestCategory = response.getBody();

            if (apiRequestCategory.getSuccess()) {
                ObjectMapper mapper = new ObjectMapper();
                categoryList = mapper.convertValue(apiRequestCategory.getMessage(), new TypeReference<List<Category>>() {
                });
            }
        } catch (RestClientException exception) {
            logger.error(exception.getMessage());
        }

        ModelAndView modelAndView = new ModelAndView("index");
        modelAndView.addObject(categoryList);
        modelAndView.addObject("categoryServiceUri", environment.getProperty("category-service.api.uri"));
        modelAndView.addObject("productServiceUri", environment.getProperty("product-service.api.uri"));
        return modelAndView;

    }
}
