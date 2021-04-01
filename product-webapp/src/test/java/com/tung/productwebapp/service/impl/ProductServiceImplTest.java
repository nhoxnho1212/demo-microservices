package com.tung.productwebapp.service.impl;

import com.tung.productwebapp.gateway.CategoryClient;
import com.tung.productwebapp.gateway.ProductClient;
import com.tung.productwebapp.model.Category;
import com.tung.productwebapp.model.Product;
import com.tung.productwebapp.model.ProductRequest;
import com.tung.productwebapp.service.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.hamcrest.beans.SamePropertyValuesAs.samePropertyValuesAs;

@ExtendWith(MockitoExtension.class)
class ProductServiceImplTest {

    @InjectMocks
    private ProductService productService = new ProductServiceImpl();

    @Mock
    CategoryClient categoryClient;

    @Mock
    ProductClient productClient;

    List<Category> categoryList;
    Category category;

    List<Product> productList;
    List<ProductRequest> productRequestList;
    ProductRequest productRequestSofm = new ProductRequest(1, "name Sofm TEST ONE TWO THREE", 100000.0, "CATETEST1");
    ProductRequest productRequestLevi = new ProductRequest(2, "TWO THREE name Levi TEST ", 200000.0,"CATETEST1");
    ProductRequest productRequestCanyon = new ProductRequest(3, "name Canyon THREE TEST", 300000.0, "CATETEST2");
    Product productSofm;
    Product productLevi;
    Product productCanyon;
    final String NAME_OF_NONE_PRODUCT = "NONE";
    final String NAME_OF_ALL_PRODUCTS = "ALL";

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
        productRequestList.add(productRequestSofm);
        productRequestList.add(productRequestLevi);
        productRequestList.add(productRequestCanyon);

        productList = new ArrayList<>();
        productList.add(productSofm);
        productList.add(productLevi);
        productList.add(productCanyon);
    }

    @Test
    void testGetByName_getAllProducts() {
        when(categoryClient.getAll()).thenReturn(categoryList);
        when(productClient.findByName(anyString())).thenReturn(productRequestList);
        List<Product> resultProductsList = productService.getByName(NAME_OF_ALL_PRODUCTS);

        assertThat(resultProductsList, notNullValue());
        assertThat(resultProductsList, hasSize(productList.size()));
        for (int idx = 0; idx < productList.size(); idx++ ) {
            Product result = resultProductsList.get(idx);
            Product product = productList.get(idx);
            assertThat(result, samePropertyValuesAs(product));
        }
        verify(categoryClient, times(1)).getAll();
        verify(productClient, times(1)).findByName(anyString());
    }

    @Test
    void testFindByName_shouldYieldEmptyList_forAnyProductsNotBeMatch() {
        List<ProductRequest> emptyProductListRequest = new ArrayList<>();
        when(categoryClient.getAll()).thenReturn(categoryList);
        when(productClient.findByName(anyString())).thenReturn(emptyProductListRequest);

        List<Product> result = productService.getByName(NAME_OF_NONE_PRODUCT);

        assertThat(result, notNullValue());
        assertThat(result, hasSize(0));
        verify(categoryClient, times(1)).getAll();
        verify(productClient, times(1)).findByName(anyString());
    }

}