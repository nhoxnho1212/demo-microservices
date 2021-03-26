package com.tung.categoryservice.controller;

import com.tung.categoryservice.dto.CategoryDto;
import com.tung.categoryservice.payload.response.CategoryResponse;
import com.tung.categoryservice.service.CategoryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CategoryControllerTest {

    @InjectMocks
    CategoryController categoryController;

    @Mock
    CategoryService categoryService;

    private String categoryId = "CATETEST1";
    private String name = "catTEST 1";
    private CategoryDto category;
    List<CategoryDto> listCategories;

    @BeforeEach
    void setUp() {
        category = new CategoryDto(categoryId, name);
        listCategories = new ArrayList<>();
        listCategories.add(new CategoryDto("CATETEST1", "catTEST 1"));
        listCategories.add(new CategoryDto("CATETEST2", "catTEST 3"));
        listCategories.add(new CategoryDto("CATETEST3", "catTEST 4"));
    }

    @Test
    void testGetCategoryById() {
        when(categoryService.findById(anyString())).thenReturn(category);

        CategoryResponse result = categoryController.getCategoryById(categoryId);

        assertNotNull(result);
        assertEquals(result.getName(), category.getName());
        assertEquals(result.getId(), category.getId());
        verify(categoryService, times(1)).findById(anyString());

    }

    @Test
    void testGetAllCategories() {
        when(categoryService.findAll()).thenReturn(listCategories);

        List<CategoryResponse> resultListCategoriesResponse = categoryController.getAllCategories();

        assertEquals(resultListCategoriesResponse.size(), listCategories.size());
        for (int idx = 0; idx < listCategories.size(); idx ++) {
            CategoryResponse resultCategory = resultListCategoriesResponse.get(idx);

            assertEquals(resultCategory.getId(), listCategories.get(idx).getId());
            assertEquals(resultCategory.getName(), listCategories.get(idx).getName());
        }
        verify(categoryService, times(1)).findAll();

    }
}