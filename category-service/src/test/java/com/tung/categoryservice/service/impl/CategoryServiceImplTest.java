package com.tung.categoryservice.service.impl;

import com.tung.categoryservice.exception.CategoryServiceException;
import com.tung.categoryservice.dto.CategoryDto;
import com.tung.categoryservice.dao.CategoryDao;
import com.tung.categoryservice.service.CategoryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class CategoryServiceImplTest {

    @InjectMocks
    CategoryService categoryService = new CategoryServiceImpl();

    @Mock
    CategoryDao categoryDao;

    List<CategoryDto> categoryDtoList;
    CategoryDto categoryDto;

    @BeforeEach
    void setUp() {
        categoryDtoList = new ArrayList<>();
        categoryDtoList.add(new CategoryDto("CATETEST1", "catTEST 1"));
        categoryDtoList.add(new CategoryDto("CATETEST2", "catTEST 2"));
        categoryDtoList.add(new CategoryDto("CATETEST3", "catTEST 3"));

        categoryDto = new CategoryDto("CATETEST4", "catTEST 4");
    }

    @Test
    void testFindAll() {
        when(categoryDao.findAll()).thenReturn(categoryDtoList);

        List<CategoryDto> result = categoryService.findAll();

        assertThat(result, notNullValue());
        assertThat(result, hasSize(categoryDtoList.size()));
        categoryDtoList.forEach(category -> {
            assertThat(result, hasItem(category));
        });
        verify(categoryDao, times(1)).findAll();
    }

    @Test
    void testFindAll_NotAnyCategories() {
        when(categoryDao.findAll()).thenReturn(null);

        List<CategoryDto> result = categoryService.findAll();

        assertThat(result, notNullValue());
        assertThat(result, empty());
        verify(categoryDao, times(1)).findAll();
    }

    @Test
    void testFindById() {
        when(categoryDao.findById(anyString())).thenReturn(categoryDto);

        CategoryDto result = categoryService.findById(categoryDto.getId());

        assertThat(result, notNullValue());
        assertThat(result, equalTo(categoryDto));
        verify(categoryDao, times(1)).findById(anyString());
    }

    @Test
    void  testFindById_CategoryIdException() {
        when(categoryDao.findById(anyString())).thenReturn(null);

        assertThrows(CategoryServiceException.class,
                () -> {
                    categoryService.findById(categoryDto.getId());
                }
        );
        verify(categoryDao, times(1)).findById(anyString());
    }
}