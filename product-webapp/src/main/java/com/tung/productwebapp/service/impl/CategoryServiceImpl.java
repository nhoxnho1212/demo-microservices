package com.tung.productwebapp.service.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tung.productwebapp.model.Category;
import com.tung.productwebapp.payload.request.ApiRequest;
import com.tung.productwebapp.service.CategoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    Environment environment;

    @Value("${category-service.api.uri:}")
    private String categoryUri;

    @Autowired
    RestTemplate restTemplate;

    Logger logger = LoggerFactory.getLogger(CategoryServiceImpl.class);

    @Override
    public List<Category> getAll() {
        List<Category> categoryList = new ArrayList<>();
        try {
            ResponseEntity<ApiRequest> response = restTemplate.getForEntity(
                    categoryUri,
                    ApiRequest.class
            );
            ApiRequest apiRequestCategory = response.getBody();

            // ApiRequest.getSuccess() always return True if it don't throw RestClientException.
            if (apiRequestCategory.getSuccess()) {
                ObjectMapper mapper = new ObjectMapper();
                categoryList = mapper.convertValue(apiRequestCategory.getMessage(), new TypeReference<List<Category>>() {
                });
            }
        } catch (RestClientException exception) {
            logger.error(exception.getMessage());
        }

        return categoryList;
    }
}
