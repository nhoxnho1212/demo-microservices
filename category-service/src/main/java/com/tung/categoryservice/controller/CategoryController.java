package com.tung.categoryservice.controller;

import com.tung.categoryservice.model.entity.Category;
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
@RequestMapping(value = "/category")
public class CategoryController {

    @Autowired
    CategoryService categoryService;

    @GetMapping(
            path = "/{categoryId}"
    )
    public ResponseEntity<ApiResponse> getCategoryById(@PathVariable final long categoryId) {
        CategoryResponse categoryResponse = new CategoryResponse();

        Category category = categoryService.findById(categoryId);

        BeanUtils.copyProperties(category, categoryResponse);

        ApiResponse response = new ApiResponse(Boolean.TRUE, category);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<ApiResponse> getAllCategories() {
        List<CategoryResponse> listCategoriesResponse = new ArrayList<>();

        List<Category> listCategories = categoryService.findAll();

        for (Category category : listCategories) {
            CategoryResponse categoryResponse = new CategoryResponse();
            BeanUtils.copyProperties(category, categoryResponse);
            listCategoriesResponse.add(categoryResponse);
        }

        ApiResponse response = new ApiResponse(Boolean.TRUE, listCategoriesResponse);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
