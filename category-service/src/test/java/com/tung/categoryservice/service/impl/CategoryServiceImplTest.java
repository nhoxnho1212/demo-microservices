package com.tung.categoryservice.service.impl;

import com.tung.categoryservice.exception.CategoryServiceException;
import com.tung.categoryservice.model.entity.Category;
import com.tung.categoryservice.model.repository.CategoryRepository;
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
class CategoryServiceImplTest {

    @InjectMocks
    CategoryService categoryService = new CategoryServiceImpl();

    @Mock
    CategoryRepository categoryRepository;

    List<Category> categoryList;
    Category category;

    @BeforeEach
    void setUp() {
        categoryList = new ArrayList<>();
        categoryList.add(new Category(1L, "catTEST 1"));
        categoryList.add(new Category(2L, "catTEST 2"));
        categoryList.add(new Category(3L, "catTEST 3"));

        category = new Category(4L, "catTEST 4");
    }

    @Test
    void testFindAll() {
        when(categoryRepository.findAll()).thenReturn(categoryList);

        List<Category> result = categoryService.findAll();

        assertNotNull(result);
        assertEquals(categoryList.size(), result.size());
        final int cateSize = categoryList.size();
        for (int idx = 0; idx < cateSize; idx ++) {
            assertEquals(result.get(idx), categoryList.get(idx));
        }
        verify(categoryRepository, times(1)).findAll();
    }

    @Test
    void testFindAll_NotAnyCategories() {
        when(categoryRepository.findAll()).thenReturn(null);

        List<Category> result = categoryService.findAll();

        assertNotNull(result);
        assertEquals(result.size(), 0);
        verify(categoryRepository, times(1)).findAll();
    }

    @Test
    void testFindById() {
        when(categoryRepository.findById(anyLong())).thenReturn(category);

        Category result = categoryService.findById(category.getId());

        assertNotNull(result);
        assertEquals(category.getId(), result.getId());
        assertEquals(category.getName(), result.getName());
        verify(categoryRepository, times(1)).findById(anyLong());
    }

    @Test
    void  testFindById_CategoryIdException() {
        when(categoryRepository.findById(anyLong())).thenReturn(null);

        assertThrows(CategoryServiceException.class,
                () -> {
                    categoryService.findById(category.getId());
                }
        );

        verify(categoryRepository, times(1)).findById(anyLong());
    }
}