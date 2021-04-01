package com.tung.productservice.controller;

import com.tung.productservice.dto.ProductDto;
import com.tung.productservice.payload.response.ProductResponse;
import com.tung.productservice.service.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

import static org.mockito.Mockito.*;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;

@ExtendWith(MockitoExtension.class)
class ProductControllerTest {

    @InjectMocks
    ProductController productController;

    @Mock
    ProductService productService;

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

        List<ProductResponse> resultListProductsResponse = productController.getAllProducts();

        assertThat(resultListProductsResponse, hasSize(productList.size()));
        IntStream.range(0, resultListProductsResponse.size())
                .forEach( idx -> {
                    ProductDto productDto = productList.get(idx);
                    ProductResponse result = resultListProductsResponse.get(idx);

                    assertThat(result.getId(), equalTo(productDto.getId()));
                    assertThat(result.getName(), equalTo(productDto.getName()));
                    assertThat(result.getPrice(), equalTo(productDto.getPrice()));
                    assertThat(result.getCategory(), equalTo(productDto.getCategory()));
                });

        verify(productService,times(1)).findAll();
    }

    @Test
    void testGetAllProducts_shouldYieldEmptyList_forEmptyProduct() {
        List<ProductDto> listEmptyProduct = new ArrayList<>();
        when(productService.findAll()).thenReturn(listEmptyProduct);

        List<ProductResponse> resultListProductsResponse = productController.getAllProducts();

        assertThat(resultListProductsResponse, notNullValue());
        assertThat(resultListProductsResponse, hasSize(0));
        verify(productService,times(1)).findAll();
    }

    @Test
    void testGetProductsByName_shouldYieldListOfAllProducts_whenNameIsNull() {
        when(productService.findAll()).thenReturn(productList);

        List<ProductResponse> resultListProductsResponse = productController.getProductsByName(null);

        assertThat(resultListProductsResponse, notNullValue());
        assertThat(resultListProductsResponse, hasSize(productList.size()));
        IntStream.range(0, resultListProductsResponse.size())
                .forEach( idx -> {
                    ProductResponse result = resultListProductsResponse.get(idx);
                    ProductDto productDto = productList.stream()
                            .filter(product -> result.getId() == product.getId())
                            .findAny()
                            .orElse(null);

                    assertThat(result.getId(), equalTo(productDto.getId()));
                    assertThat(result.getName(), equalTo(productDto.getName()));
                    assertThat(result.getPrice(), equalTo(productDto.getPrice()));
                    assertThat(result.getCategory(), equalTo(productDto.getCategory()));
                });
        verify(productService,times(1)).findAll();
    }

    @Test
    void testGetProductsByName_shouldYieldListOfAllProducts_whenNameIsBlank() {
        when(productService.findByName(anyString())).thenReturn(productList);

        List<ProductResponse> resultListProductsResponse = productController.getProductsByName("  ");

        assertThat(resultListProductsResponse, notNullValue());
        assertThat(resultListProductsResponse, hasSize(productList.size()));
        IntStream.range(0, resultListProductsResponse.size())
                .forEach( idx -> {
                    ProductResponse result = resultListProductsResponse.get(idx);
                    ProductDto productDto = productList.stream()
                            .filter(product -> result.getId() == product.getId())
                            .findAny()
                            .orElse(null);

                    assertThat(result.getId(), equalTo(productDto.getId()));
                    assertThat(result.getName(), equalTo(productDto.getName()));
                    assertThat(result.getPrice(), equalTo(productDto.getPrice()));
                    assertThat(result.getCategory(), equalTo(productDto.getCategory()));
                });
        verify(productService,times(1)).findByName(anyString());
    }

    @Test
    void testGetProductsByName_shouldYieldListOfOneProduct_whenSizeOfListNameAfterSplitIsOne() {
        productList = new ArrayList<>();
        productList.add(productLevi);
        when(productService.findByName(anyString())).thenReturn(productList);

        List<ProductResponse> resultListProductsResponse = productController.getProductsByName(NAME_OF_ONE_PRODUCT);

        assertThat(resultListProductsResponse, notNullValue());
        assertThat(resultListProductsResponse, hasSize(1));
        ProductResponse resultProduct = resultListProductsResponse.get(0);
        assertThat(resultProduct.getName(), equalTo(productLevi.getName()));
        assertThat(resultProduct.getPrice(), equalTo(productLevi.getPrice()));
        assertThat(resultProduct.getCategory(), equalTo(productLevi.getCategory()));
        verify(productService,times(1)).findByName(anyString());
    }

    @Test
    void testGetProductsByName_shouldYieldListOfTwoProduct_whenSizeOfListNameAfterSplitIsTwo() {
        productList = new ArrayList<>();
        productList.add(productLevi);
        productList.add(productSofm);
        when(productService.findByName(anyString())).thenReturn(productList);

        List<ProductResponse> resultListProductsResponse = productController.getProductsByName(NAME_OF_TWO_PRODUCTS + " " + NAME_OF_ONE_PRODUCT);

        assertThat(resultListProductsResponse, notNullValue());
        assertThat(resultListProductsResponse, hasSize(2));
        IntStream.range(0, resultListProductsResponse.size())
                .forEach( idx -> {
                    ProductResponse result = resultListProductsResponse.get(idx);
                    ProductDto productDto = productList.stream()
                            .filter(product -> result.getId() == product.getId())
                            .findAny()
                            .orElse(null);

                    assertThat(result.getId(), equalTo(productDto.getId()));
                    assertThat(result.getName(), equalTo(productDto.getName()));
                    assertThat(result.getPrice(), equalTo(productDto.getPrice()));
                    assertThat(result.getCategory(), equalTo(productDto.getCategory()));
                });
        verify(productService,times(2)).findByName(anyString());
    }

    @Test
    void testGetProductsByName_shouldYieldListOfTwoProduct_whenSizeOfListNameAfterSplitIsTwoAndItDuplicate() {
        productList = new ArrayList<>();
        productList.add(productLevi);
        productList.add(productSofm);
        when(productService.findByName(anyString())).thenReturn(productList);
        String nameRequest = NAME_OF_TWO_PRODUCTS + " " + NAME_OF_ONE_PRODUCT + "  " + NAME_OF_TWO_PRODUCTS;

        List<ProductResponse> resultListProductsResponse = productController.getProductsByName(nameRequest);

        assertThat(resultListProductsResponse, notNullValue());
        assertThat(resultListProductsResponse, hasSize(2));
        IntStream.range(0, resultListProductsResponse.size())
                .forEach( idx -> {
                    ProductResponse result = resultListProductsResponse.get(idx);
                    ProductDto productDto = productList.stream()
                        .filter(product -> result.getId() == product.getId())
                        .findAny()
                        .orElse(null);

                    assertThat(result.getId(), equalTo(productDto.getId()));
                    assertThat(result.getName(), equalTo(productDto.getName()));
                    assertThat(result.getPrice(), equalTo(productDto.getPrice()));
                    assertThat(result.getCategory(), equalTo(productDto.getCategory()));
                });
        verify(productService,times(2)).findByName(anyString());
    }

    @Test
    void testGetProductsByName_shouldYieldEmptyList_WhenCannotCaseNameMatch() {
        when(productService.findByName(anyString())).thenReturn(new ArrayList<>());

        List<ProductResponse> resultListProductsResponse = productController.getProductsByName(NAME_OF_NONE_PRODUCT);

        assertThat(resultListProductsResponse, notNullValue());
        assertThat(resultListProductsResponse, hasSize(0));
        verify(productService,times(1)).findByName(anyString());
    }
}