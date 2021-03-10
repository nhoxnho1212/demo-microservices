package com.tung.categoryservice.controller;

import com.tung.categoryservice.model.entity.Category;
import com.tung.categoryservice.payload.response.ApiResponse;
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

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CategoryControllerTest {

    @InjectMocks
    CategoryController categoryController;

    @Mock
    CategoryService categoryService;

    private long categoryId = 1L;
    private String name = "catTEST 1";
    private Category category;

    @BeforeEach
    void setUp() {
        category = new Category(categoryId, name);
    }

    @Test
    void testGetCategoryById() {
        when(categoryService.findById(anyLong())).thenReturn(category);

        ApiResponse apiResponse = new ApiResponse(Boolean.TRUE, category);
        ResponseEntity responseEntity = new ResponseEntity<>(apiResponse, HttpStatus.OK);

        ResponseEntity result = categoryController.getCategoryById(categoryId);
        ApiResponse resultApiResponse = (ApiResponse) result.getBody();

        assertNotNull(category);
        assertEquals(result.getStatusCode(), responseEntity.getStatusCode());
        assertEquals(resultApiResponse.isSuccess(), apiResponse.isSuccess());
        assertEquals(resultApiResponse.getMessage(), apiResponse.getMessage());

    }
}