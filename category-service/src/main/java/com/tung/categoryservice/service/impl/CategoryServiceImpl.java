package com.tung.categoryservice.service.impl;

import com.tung.categoryservice.config.constant.ErrorMessages;
import com.tung.categoryservice.exception.CategoryServiceException;
import com.tung.categoryservice.dto.CategoryDto;
import com.tung.categoryservice.dao.CategoryDao;
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
    private CategoryDao categoryDao;

    @Override
    public List<CategoryDto> findAll() {
        List<CategoryDto> result = categoryDao.findAll();
        if (null == result) {
            result = new ArrayList<>();
        }
        return result;
    }

    @Override
    public CategoryDto findById(final String id) {
        CategoryDto returnValue = categoryDao.findById(id);

        if (null == returnValue) {
            logger.warn(String.format("Category id: %s not found", id));
            throw new CategoryServiceException(ErrorMessages.CATEGORY_NOT_FOUND.getMessage());
        }

        return returnValue;
    }
}
