package com.tung.productwebapp.service.impl;

import com.tung.productwebapp.gateway.CategoryClient;
import com.tung.productwebapp.model.Category;
import com.tung.productwebapp.service.CategoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    private CategoryClient categoryClient;

    Logger logger = LoggerFactory.getLogger(CategoryServiceImpl.class);

    @Autowired
    public CategoryServiceImpl(CategoryClient categoryClient) {
        this.categoryClient = categoryClient;
    }

    public CategoryServiceImpl() {
    }

    @Override
    public List<Category> getAll() {
        return categoryClient.getAll();
    }
}
