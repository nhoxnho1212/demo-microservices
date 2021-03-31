package com.tung.productwebapp.service.impl;

import com.tung.productwebapp.gateway.CategoryClient;
import com.tung.productwebapp.model.Category;
import com.tung.productwebapp.service.CategoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@CacheConfig(cacheNames = {"CATEGORY"})
public class CategoryServiceImpl implements CategoryService {

    private CategoryClient categoryClient;

    private final static Logger LOGGER = LoggerFactory.getLogger(CategoryServiceImpl.class);

    @Autowired
    public CategoryServiceImpl(CategoryClient categoryClient) {
        this.categoryClient = categoryClient;
    }

    public CategoryServiceImpl() {
    }

    @Override
    @Cacheable
    public List<Category> getAll() {
        return categoryClient.getAll();
    }
}
