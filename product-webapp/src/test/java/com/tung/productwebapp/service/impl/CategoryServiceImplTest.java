package com.tung.productwebapp.service.impl;

import com.tung.productwebapp.gateway.CategoryClient;
import com.tung.productwebapp.model.Category;
import com.tung.productwebapp.service.CategoryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import java.util.*;

@ExtendWith(MockitoExtension.class)
class CategoryServiceImplTest {

    @InjectMocks
    private CategoryService categoryService = new CategoryServiceImpl();

    @Mock
    private CategoryClient categoryClient;

    List<Category> categoryList;
    Category category;

    @BeforeEach
    void setUp() {
        categoryList = new ArrayList<>();
        categoryList.add(new Category("CATETEST1", "catTEST 1"));
        categoryList.add(new Category("CATETEST2", "catTEST 2"));
        categoryList.add(new Category("CATETEST3", "catTEST 3"));

        category = new Category("CATETEST4", "catTEST 4");
    }

    @Test
    void testGetAll() {
        when(categoryClient.getAll()).thenReturn(categoryList);

        List<Category> result = categoryService.getAll();

        assertNotNull(result);
        assertEquals(categoryList.size(), result.size());
        final int cateSize = categoryList.size();
        for (int idx = 0; idx < cateSize; idx ++) {
            assertEquals(result.get(idx).getId(), categoryList.get(idx).getId());
            assertEquals(result.get(idx).getName(), categoryList.get(idx).getName());
        }
        verify(categoryClient, times(1)).getAll();
    }

    @Test
    void testGetAll_NotAnyCategory() {
        List<Category> listEmptyCategory = new ArrayList<>();
        when(categoryClient.getAll()).thenReturn(listEmptyCategory);

        List<Category> result = categoryService.getAll();

        assertNotNull(result);
        assertEquals(result.size(), listEmptyCategory.size());
        verify(categoryClient, times(1)).getAll();
    }

}