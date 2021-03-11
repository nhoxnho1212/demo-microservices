package com.tung.categoryservice.controller;

import com.tung.categoryservice.model.entity.Category;
import com.tung.categoryservice.payload.response.ApiResponse;
import com.tung.categoryservice.payload.response.CategoryResponse;
import com.tung.categoryservice.service.CategoryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CategoryControllerTest {

    @InjectMocks
    CategoryController categoryController;

    @Mock
    CategoryService categoryService;

    private long categoryId = 1L;
    private String name = "catTEST 1";
    private Category category;
    List<Category> listCategories;

    @BeforeEach
    void setUp() {
        category = new Category(categoryId, name);
        listCategories = new ArrayList<>();
        listCategories.add(new Category(1L, "catTEST 1"));
        listCategories.add(new Category(2L, "catTEST 3"));
        listCategories.add(new Category(3L, "catTEST 4"));
    }

    @Test
    void testGetCategoryById() {
        when(categoryService.findById(anyLong())).thenReturn(category);

        ApiResponse apiResponse = new ApiResponse(Boolean.TRUE, category);
        ResponseEntity responseEntity = new ResponseEntity<>(apiResponse, HttpStatus.OK);

        ResponseEntity result = categoryController.getCategoryById(categoryId);
        ApiResponse resultApiResponse = (ApiResponse) result.getBody();

        assertNotNull(resultApiResponse.getMessage());
        assertEquals(result.getStatusCode(), responseEntity.getStatusCode());
        assertEquals(resultApiResponse.isSuccess(), apiResponse.isSuccess());
        assertEquals(resultApiResponse.getMessage(), apiResponse.getMessage());
        verify(categoryService, times(1)).findById(anyLong());

    }

    @Test
    void testGetAllCategories() {
        when(categoryService.findAll()).thenReturn(listCategories);

        ApiResponse apiResponse = new ApiResponse(Boolean.TRUE, category);
        ResponseEntity responseEntity = new ResponseEntity<>(apiResponse, HttpStatus.OK);

        ResponseEntity result = categoryController.getAllCategories();
        ApiResponse resultApiResponse = (ApiResponse) result.getBody();
        List<CategoryResponse> resultListCategoriesResponse = (List<CategoryResponse>) resultApiResponse.getMessage();

        assertNotNull(resultApiResponse.getMessage());
        assertEquals(result.getStatusCode(), responseEntity.getStatusCode());
        assertEquals(resultApiResponse.isSuccess(), apiResponse.isSuccess());
        assertEquals(resultListCategoriesResponse.size(), listCategories.size());

        for (int idx = 0; idx < listCategories.size(); idx ++) {
            CategoryResponse resultCategory = resultListCategoriesResponse.get(idx);

            assertEquals(resultCategory.getId(), listCategories.get(idx).getId());
            assertEquals(resultCategory.getName(), listCategories.get(idx).getName());
        }
        verify(categoryService, times(1)).findAll();

    }
}