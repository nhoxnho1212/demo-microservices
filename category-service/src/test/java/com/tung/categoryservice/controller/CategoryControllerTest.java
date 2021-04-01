package com.tung.categoryservice.controller;

import com.tung.categoryservice.dto.CategoryDto;
import com.tung.categoryservice.payload.response.CategoryResponse;
import com.tung.categoryservice.service.CategoryService;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
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

        assertThat(result, notNullValue());
        assertThat(result.getId(), equalTo(category.getId()));
        assertThat(result.getName(), equalTo(category.getName()));
        verify(categoryService, times(1)).findById(anyString());

    }

    @Test
    void testGetAllCategories() {
        when(categoryService.findAll()).thenReturn(listCategories);

        List<CategoryResponse> resultListCategoriesResponse = categoryController.getAllCategories();

        assertThat(resultListCategoriesResponse, hasSize(listCategories.size()));
        IntStream.range(0, listCategories.size())
                .forEach(idx -> {
                    CategoryDto categoryDto = listCategories.get(idx);
                    CategoryResponse result = resultListCategoriesResponse.get(idx);

                    assertThat(result.getId(), equalTo(categoryDto.getId()));
                    assertThat(result.getName(), equalTo(categoryDto.getName()));
                });
        verify(categoryService, times(1)).findAll();

    }
}