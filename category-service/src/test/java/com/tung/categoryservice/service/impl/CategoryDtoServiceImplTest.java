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

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class CategoryDtoServiceImplTest {

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

        assertNotNull(result);
        assertEquals(categoryDtoList.size(), result.size());
        final int cateSize = categoryDtoList.size();
        for (int idx = 0; idx < cateSize; idx ++) {
            assertEquals(result.get(idx), categoryDtoList.get(idx));
        }
        verify(categoryDao, times(1)).findAll();
    }

    @Test
    void testFindAll_NotAnyCategories() {
        when(categoryDao.findAll()).thenReturn(null);

        List<CategoryDto> result = categoryService.findAll();

        assertNotNull(result);
        assertEquals(result.size(), 0);
        verify(categoryDao, times(1)).findAll();
    }

    @Test
    void testFindById() {
        when(categoryDao.findById(anyString())).thenReturn(categoryDto);

        CategoryDto result = categoryService.findById(categoryDto.getId());

        assertNotNull(result);
        assertEquals(categoryDto.getId(), result.getId());
        assertEquals(categoryDto.getName(), result.getName());
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