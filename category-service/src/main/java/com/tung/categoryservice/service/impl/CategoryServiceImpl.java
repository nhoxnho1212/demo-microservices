package com.tung.categoryservice.service.impl;

import com.tung.categoryservice.config.constant.ErrorMessages;
import com.tung.categoryservice.exception.CategoryServiceException;
import com.tung.categoryservice.model.entity.Category;
import com.tung.categoryservice.model.repository.CategoryRepository;
import com.tung.categoryservice.service.CategoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    Logger logger = LoggerFactory.getLogger(CategoryServiceImpl.class);

    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public List<Category> findAll() {
        List<Category> result = categoryRepository.findAll();
        if (null == result) {
            result = new ArrayList<>();
        }
        return result;
    }

    @Override
    public Category findById(final long id) {
        Category returnValue = categoryRepository.findById(id);

        if (null == returnValue) {
            logger.warn(String.format("Category id: %d not found", id));
            throw new CategoryServiceException(ErrorMessages.CATEGORY_NOT_FOUND.getMessage());
        }

        return returnValue;
    }
}
