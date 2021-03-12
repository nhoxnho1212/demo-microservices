package com.tung.productservice.controller;

import com.tung.productservice.model.entity.Product;
import com.tung.productservice.payload.response.ApiResponse;
import com.tung.productservice.payload.response.ProductResponse;
import com.tung.productservice.service.ProductService;
import com.tung.productservice.service.impl.ProductServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ProductControllerTest {

    @InjectMocks
    ProductController productController;

    @Mock
    ProductService productService = new ProductServiceImpl();

    List<Product> productList;
    Product productSofm = new Product(1, "name Sofm TEST ONE TWO THREE", 100000.0, "CATETEST1");
    Product productLevi = new Product(2, "TWO THREE name Levi TEST ", 200000.0,"CATETEST1");
    Product productCanyon = new Product(3, "name Canyon THREE TEST", 300000.0, "CATETEST2");
    final java.lang.String NAME_OF_THREE_PRODUCTS = "THREE";
    final java.lang.String NAME_OF_TWO_PRODUCTS = "TWO";
    final java.lang.String NAME_OF_ONE_PRODUCT = "ONE";
    final java.lang.String NAME_OF_NONE_PRODUCT = "NONE";

    @BeforeEach
    void setUp() {
        productList = new ArrayList<>();
        productList.add(productSofm);
        productList.add(productLevi);
        productList.add(productCanyon);
    }

    @Test
    void getAllProducts() {
        when(productService.findAll()).thenReturn(productList);

        ApiResponse apiResponse = new ApiResponse(Boolean.TRUE, productList);
        ResponseEntity responseEntity = new ResponseEntity<>(apiResponse, HttpStatus.OK);

        ResponseEntity result = productController.getAllProducts();
        ApiResponse resultApiResponse = (ApiResponse) result.getBody();
        List<ProductResponse> resultListProductsResponse = (List<ProductResponse>) resultApiResponse.getMessage();

        assertNotNull(resultApiResponse.getMessage());
        assertEquals(result.getStatusCode(), responseEntity.getStatusCode());
        assertEquals(resultApiResponse.getSuccess(), apiResponse.getSuccess());
        assertEquals(resultListProductsResponse.size(), productList.size());

        for (int idx = 0; idx < productList.size(); idx ++) {
            ProductResponse resultProduct = resultListProductsResponse.get(idx);

            assertEquals(resultProduct.getId(), productList.get(idx).getId());
            assertEquals(resultProduct.getName(), productList.get(idx).getName());
            assertEquals(resultProduct.getPrice(), productList.get(idx).getPrice());
            assertEquals(resultProduct.getCategory(), productList.get(idx).getCategory());
        }

        verify(productService,times(1)).findAll();
    }
}