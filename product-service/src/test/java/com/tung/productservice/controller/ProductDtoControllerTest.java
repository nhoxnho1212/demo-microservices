package com.tung.productservice.controller;

import com.tung.productservice.dto.ProductDto;
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
class ProductDtoControllerTest {

    @InjectMocks
    ProductController productController;

    @Mock
    ProductService productService = new ProductServiceImpl();

    List<ProductDto> productList;
    ProductDto productSofm = new ProductDto(1, "ONCE TWICE 1", 100000.0, "CATETEST1");
    ProductDto productLevi = new ProductDto(2, "TWICE 2", 200000.0,"CATETEST1");
    ProductDto productCanyon = new ProductDto(3, "test case", 300000.0, "CATETEST2");
    final String NAME_OF_TWO_PRODUCTS = "TWICE";
    final String NAME_OF_ONE_PRODUCT = "ONCE";
    final String NAME_OF_NONE_PRODUCT = "NONE";

    @BeforeEach
    void setUp() {
        productList = new ArrayList<>();
        productList.add(productSofm);
        productList.add(productLevi);
        productList.add(productCanyon);
    }

    @Test
    void testGetAllProducts_shouldYieldListOfProducts_forNoneEmptyProduct() {
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

    @Test
    void testGetAllProducts_shouldYieldEmptyList_forEmptyProduct() {
        List<ProductDto> listEmptyProduct = new ArrayList<>();
        when(productService.findAll()).thenReturn(listEmptyProduct);

        ApiResponse apiResponse = new ApiResponse(Boolean.TRUE, listEmptyProduct);
        ResponseEntity responseEntity = new ResponseEntity<>(apiResponse, HttpStatus.OK);

        ResponseEntity result = productController.getAllProducts();
        ApiResponse resultApiResponse = (ApiResponse) result.getBody();
        List<ProductResponse> resultListProductsResponse = (List<ProductResponse>) resultApiResponse.getMessage();

        assertNotNull(resultApiResponse.getMessage());
        assertEquals(result.getStatusCode(), responseEntity.getStatusCode());
        assertEquals(resultApiResponse.getSuccess(), apiResponse.getSuccess());
        assertEquals(resultListProductsResponse.size(), 0);
    }

    @Test
    void testGetProductsByName_shouldYieldListOfAllProducts_whenNameIsNull() {
        when(productService.findAll()).thenReturn(productList);

        ApiResponse apiResponse = new ApiResponse(Boolean.TRUE, productList);
        ResponseEntity responseEntity = new ResponseEntity<>(apiResponse, HttpStatus.OK);

        ResponseEntity result = productController.getProductsByName(null);
        ApiResponse resultApiResponse = (ApiResponse) result.getBody();
        List<ProductResponse> resultListProductsResponse = (List<ProductResponse>) resultApiResponse.getMessage();

        assertNotNull(resultApiResponse.getMessage());
        assertEquals(result.getStatusCode(), responseEntity.getStatusCode());
        assertEquals(resultApiResponse.getSuccess(), apiResponse.getSuccess());
        assertEquals(resultListProductsResponse.size(), productList.size());

        for (int idx = 0; idx < productList.size(); idx ++) {
            ProductResponse resultProductResponse = resultListProductsResponse.get(idx);
            ProductDto resultProduct = productList.stream()
                    .filter(product -> resultProductResponse.getId() == product.getId())
                    .findAny()
                    .orElse(null);
            assertEquals(resultProduct.getId(), resultProductResponse.getId());
            assertEquals(resultProduct.getName(), resultProductResponse.getName());
            assertEquals(resultProduct.getPrice(), resultProductResponse.getPrice());
            assertEquals(resultProduct.getCategory(), resultProductResponse.getCategory());
        }

        verify(productService,times(1)).findAll();
    }

    @Test
    void testGetProductsByName_shouldYieldListOfAllProducts_whenNameIsBlank() {
        when(productService.findByName(anyString())).thenReturn(productList);

        ApiResponse apiResponse = new ApiResponse(Boolean.TRUE, productList);
        ResponseEntity responseEntity = new ResponseEntity<>(apiResponse, HttpStatus.OK);

        ResponseEntity result = productController.getProductsByName("  ");
        ApiResponse resultApiResponse = (ApiResponse) result.getBody();
        List<ProductResponse> resultListProductsResponse = (List<ProductResponse>) resultApiResponse.getMessage();

        assertNotNull(resultApiResponse.getMessage());
        assertEquals(result.getStatusCode(), responseEntity.getStatusCode());
        assertEquals(resultApiResponse.getSuccess(), apiResponse.getSuccess());
        assertEquals(resultListProductsResponse.size(), productList.size());

        for (int idx = 0; idx < productList.size(); idx ++) {
            ProductResponse resultProductResponse = resultListProductsResponse.get(idx);
            ProductDto resultProduct = productList.stream()
                    .filter(product -> resultProductResponse.getId() == product.getId())
                    .findAny()
                    .orElse(null);
            assertEquals(resultProduct.getId(), resultProductResponse.getId());
            assertEquals(resultProduct.getName(), resultProductResponse.getName());
            assertEquals(resultProduct.getPrice(), resultProductResponse.getPrice());
            assertEquals(resultProduct.getCategory(), resultProductResponse.getCategory());
        }

        verify(productService,times(1)).findByName(anyString());
    }

    @Test
    void testGetProductsByName_shouldYieldListOfOneProduct_whenSizeOfListNameAfterSplitIsOne() {
        productList = new ArrayList<>();
        productList.add(productLevi);
        when(productService.findByName(anyString())).thenReturn(productList);

        ApiResponse apiResponse = new ApiResponse(Boolean.TRUE, productList);
        ResponseEntity responseEntity = new ResponseEntity<>(apiResponse, HttpStatus.OK);

        ResponseEntity result = productController.getProductsByName(NAME_OF_ONE_PRODUCT);
        ApiResponse resultApiResponse = (ApiResponse) result.getBody();
        List<ProductResponse> resultListProductsResponse = (List<ProductResponse>) resultApiResponse.getMessage();

        assertNotNull(resultApiResponse.getMessage());
        assertEquals(result.getStatusCode(), responseEntity.getStatusCode());
        assertEquals(resultApiResponse.getSuccess(), apiResponse.getSuccess());
        assertEquals(resultListProductsResponse.size(), productList.size());

        ProductResponse resultProduct = resultListProductsResponse.get(0);

        assertEquals(resultProduct.getId(), productList.get(0).getId());
        assertEquals(resultProduct.getName(), productList.get(0).getName());
        assertEquals(resultProduct.getPrice(), productList.get(0).getPrice());
        assertEquals(resultProduct.getCategory(), productList.get(0).getCategory());

        verify(productService,times(1)).findByName(anyString());
    }

    @Test
    void testGetProductsByName_shouldYieldListOfTwoProduct_whenSizeOfListNameAfterSplitIsTwo() {
        productList = new ArrayList<>();
        productList.add(productLevi);
        productList.add(productSofm);
        when(productService.findByName(anyString())).thenReturn(productList);

        ApiResponse apiResponse = new ApiResponse(Boolean.TRUE, productList);
        ResponseEntity responseEntity = new ResponseEntity<>(apiResponse, HttpStatus.OK);

        ResponseEntity result = productController.getProductsByName(NAME_OF_TWO_PRODUCTS + " " + NAME_OF_ONE_PRODUCT);
        ApiResponse resultApiResponse = (ApiResponse) result.getBody();
        List<ProductResponse> resultListProductsResponse = (List<ProductResponse>) resultApiResponse.getMessage();

        assertNotNull(resultApiResponse.getMessage());
        assertEquals(result.getStatusCode(), responseEntity.getStatusCode());
        assertEquals(resultApiResponse.getSuccess(), apiResponse.getSuccess());
        assertEquals(resultListProductsResponse.size(), productList.size());

        for (int idx = 0; idx < productList.size(); idx ++) {
            ProductResponse resultProductResponse = resultListProductsResponse.get(idx);
            ProductDto resultProduct = productList.stream()
                    .filter(product -> resultProductResponse.getId() == product.getId())
                    .findAny()
                    .orElse(null);
            assertEquals(resultProduct.getId(), resultProductResponse.getId());
            assertEquals(resultProduct.getName(), resultProductResponse.getName());
            assertEquals(resultProduct.getPrice(), resultProductResponse.getPrice());
            assertEquals(resultProduct.getCategory(), resultProductResponse.getCategory());
        }

        verify(productService,times(2)).findByName(anyString());
    }

    @Test
    void testGetProductsByName_shouldYieldListOfTwoProduct_whenSizeOfListNameAfterSplitIsTwoAndItDuplicate() {
        productList = new ArrayList<>();
        productList.add(productLevi);
        productList.add(productSofm);
        when(productService.findByName(anyString())).thenReturn(productList);

        ApiResponse apiResponse = new ApiResponse(Boolean.TRUE, productList);
        ResponseEntity responseEntity = new ResponseEntity<>(apiResponse, HttpStatus.OK);

        String nameRequest = NAME_OF_TWO_PRODUCTS + " " + NAME_OF_ONE_PRODUCT + "  " + NAME_OF_TWO_PRODUCTS;

        ResponseEntity result = productController.getProductsByName(nameRequest);
        ApiResponse resultApiResponse = (ApiResponse) result.getBody();
        List<ProductResponse> resultListProductsResponse = (List<ProductResponse>) resultApiResponse.getMessage();

        assertNotNull(resultApiResponse.getMessage());
        assertEquals(result.getStatusCode(), responseEntity.getStatusCode());
        assertEquals(resultApiResponse.getSuccess(), apiResponse.getSuccess());
        assertEquals(resultListProductsResponse.size(), productList.size());

        for (int idx = 0; idx < productList.size(); idx ++) {
            ProductResponse resultProductResponse = resultListProductsResponse.get(idx);
            ProductDto resultProduct = productList.stream()
                    .filter(product -> resultProductResponse.getId() == product.getId())
                    .findAny()
                    .orElse(null);
            assertEquals(resultProduct.getId(), resultProductResponse.getId());
            assertEquals(resultProduct.getName(), resultProductResponse.getName());
            assertEquals(resultProduct.getPrice(), resultProductResponse.getPrice());
            assertEquals(resultProduct.getCategory(), resultProductResponse.getCategory());
        }

        verify(productService,times(2)).findByName(anyString());
    }

    @Test
    void testGetProductsByName_shouldYieldEmptyList_WhenCannotCaseNameMatch() {
        when(productService.findByName(anyString())).thenReturn(new ArrayList<>());

        ApiResponse apiResponse = new ApiResponse(Boolean.TRUE, productList);
        ResponseEntity responseEntity = new ResponseEntity<>(apiResponse, HttpStatus.OK);


        ResponseEntity result = productController.getProductsByName(NAME_OF_NONE_PRODUCT);
        ApiResponse resultApiResponse = (ApiResponse) result.getBody();
        List<ProductResponse> resultListProductsResponse = (List<ProductResponse>) resultApiResponse.getMessage();

        assertNotNull(resultApiResponse.getMessage());
        assertEquals(result.getStatusCode(), responseEntity.getStatusCode());
        assertEquals(resultApiResponse.getSuccess(), apiResponse.getSuccess());
        assertEquals(resultListProductsResponse.size(), 0);

    }
}