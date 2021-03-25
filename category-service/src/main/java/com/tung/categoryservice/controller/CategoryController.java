package com.tung.categoryservice.controller;

import com.tung.categoryservice.dto.CategoryDto;
import com.tung.categoryservice.payload.response.ApiResponse;
import com.tung.categoryservice.payload.response.CategoryResponse;
import com.tung.categoryservice.service.CategoryService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "${category-service.api.url.main:/category}")
public class CategoryController {

    @Autowired
    CategoryService categoryService;

    @GetMapping(
            path = "/{categoryId}"
    )
    public ResponseEntity<ApiResponse> getCategoryById(@PathVariable final String categoryId) {
        CategoryResponse categoryResponse = new CategoryResponse();

        CategoryDto categoryDto = categoryService.findById(categoryId);

        BeanUtils.copyProperties(categoryDto, categoryResponse);

        ApiResponse response = new ApiResponse(Boolean.TRUE, categoryDto);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<ApiResponse> getAllCategories() {
        List<CategoryResponse> listCategoriesResponse = new ArrayList<>();

        List<CategoryDto> listCategories = categoryService.findAll();

        for (CategoryDto categoryDto : listCategories) {
            CategoryResponse categoryResponse = new CategoryResponse();
            BeanUtils.copyProperties(categoryDto, categoryResponse);
            listCategoriesResponse.add(categoryResponse);
        }

        ApiResponse response = new ApiResponse(Boolean.TRUE, listCategoriesResponse);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
