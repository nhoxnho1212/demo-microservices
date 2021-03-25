package com.tung.productwebapp.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tung.productwebapp.model.Category;
import com.tung.productwebapp.model.Product;
import com.tung.productwebapp.payload.ProductRequest;
import com.tung.productwebapp.payload.request.ApiRequest;
import com.tung.productwebapp.service.CategoryService;
import com.tung.productwebapp.service.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ProductServiceImplTest {

    @InjectMocks
    private ProductService productService = new ProductServiceImpl();

    @Mock
    CategoryService categoryService;

    @Mock
    @Autowired
    RestTemplate restTemplate;

    List<Category> categoryList;
    Category category;

    List<Product> productList;
    List<Map<String, Object >> productRequestList;
    ProductRequest productRequestSofm = new ProductRequest(1, "name Sofm TEST ONE TWO THREE", 100000.0, "CATETEST1");
    ProductRequest productRequestLevi = new ProductRequest(2, "TWO THREE name Levi TEST ", 200000.0,"CATETEST1");
    ProductRequest productRequestCanyon = new ProductRequest(3, "name Canyon THREE TEST", 300000.0, "CATETEST2");
    Product productSofm;
    Product productLevi;
    Product productCanyon;
    final java.lang.String NAME_OF_NONE_PRODUCT = "NONE";

    @BeforeEach
    void setUp() {
        categoryList = new ArrayList<>();
        categoryList.add(new Category("CATETEST1", "catTEST 1"));
        categoryList.add(new Category("CATETEST2", "catTEST 2"));
        categoryList.add(new Category("CATETEST3", "catTEST 3"));

        category = new Category("CATETEST4", "catTEST 4");

        Product productSofm = new Product(1, "name Sofm TEST ONE TWO THREE", 100000.0, categoryList.get(0));
        Product productLevi = new Product(2, "TWO THREE name Levi TEST ", 200000.0,categoryList.get(0));
        Product productCanyon = new Product(3, "name Canyon THREE TEST", 300000.0, categoryList.get(1));

        productRequestList = new ArrayList<>();
        ObjectMapper objectMapper = new ObjectMapper();
        productRequestList.add(objectMapper.convertValue(productRequestSofm, Map.class));
        productRequestList.add(objectMapper.convertValue(productRequestLevi, Map.class));
        productRequestList.add(objectMapper.convertValue(productRequestCanyon, Map.class));


        productList = new ArrayList<>();
        productList.add(productSofm);
        productList.add(productLevi);
        productList.add(productCanyon);
    }

    @Test
    void testGetByName_getAllProducts() {
        when(categoryService.getAll()).thenReturn(categoryList);
        ApiRequest apiResponse = new ApiRequest(Boolean.TRUE, productRequestList);
        ResponseEntity responseEntity = new ResponseEntity<>(apiResponse, HttpStatus.OK);
        when(restTemplate.getForEntity(anyString(), any())).thenReturn(responseEntity);

        List<Product> result = productService.getByName(null);

        assertNotNull(result);
        assertEquals(result.size(), productList.size());
        for (int idx = 0; idx < productList.size(); idx++ ) {
            assertEquals(result.get(idx).getId(), productList.get(idx).getId());
            assertEquals(result.get(idx).getName(), productList.get(idx).getName());
            assertEquals(result.get(idx).getPrice(), productList.get(idx).getPrice());
            assertEquals(result.get(idx).getCategory().getId(), productList.get(idx).getCategory().getId());
            assertEquals(result.get(idx).getCategory().getName(), productList.get(idx).getCategory().getName());
        }
        verify(categoryService, times(1)).getAll();
        verify(restTemplate, times(1)).getForEntity(anyString(), any());
    }

    @Test
    void testFindByName_shouldYieldEmptyList_forAnyProductsNotBeMatch() {
        List<Map<String, Object>> emptyProductListRequest = new ArrayList<>();
        when(categoryService.getAll()).thenReturn(categoryList);
        ApiRequest apiResponse = new ApiRequest(Boolean.TRUE, emptyProductListRequest);
        ResponseEntity responseEntity = new ResponseEntity<>(apiResponse, HttpStatus.OK);
        when(restTemplate.getForEntity(anyString(), any())).thenReturn(responseEntity);

        List<Product> result = productService.getByName(NAME_OF_NONE_PRODUCT);

        assertNotNull(result);
        assertEquals(result.size(), 0);
        verify(categoryService, times(1)).getAll();
        verify(restTemplate, times(1)).getForEntity(anyString(), any());
    }

    @Test
    void testGetAll_RestClientException() {
        when(categoryService.getAll()).thenReturn(categoryList);
        when(restTemplate.getForEntity(anyString(), any())).thenThrow(new RestClientException("") {});

        List<Product> result = productService.getByName("");

        assertNotNull(result);
        assertEquals(result.size(), 0);
    }
}