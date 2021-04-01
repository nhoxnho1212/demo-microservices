package com.tung.productservice.service.impl;

import com.tung.productservice.dto.ProductDto;
import com.tung.productservice.dao.ProductDao;
import com.tung.productservice.service.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;

@ExtendWith(MockitoExtension.class)
class ProductServiceImplTest {

    @InjectMocks
    private ProductService productService = new ProductServiceImpl();

    @Mock
    private ProductDao productDao;

    List<ProductDto> productList;
    ProductDto productSofm = new ProductDto(1, "name Sofm TEST ONE TWO THREE", 100000.0, "CATETEST1");
    ProductDto productLevi = new ProductDto(2, "TWO THREE name Levi TEST ", 200000.0,"CATETEST1");
    ProductDto productCanyon = new ProductDto(3, "name Canyon THREE TEST", 300000.0, "CATETEST2");
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
    void testFindAll() {
        when(productDao.findAll()).thenReturn(productList);

        List<ProductDto> result = productService.findAll();

        assertThat(result, notNullValue());
        assertThat(result, hasSize(productList.size()));
        productList.forEach(productDto -> {
            assertThat(result, hasItem(productDto));
        });
        verify(productDao, times(1)).findAll();
    }

    @Test
    void testFindAll_NotAnyProducts() {
        when(productDao.findAll()).thenReturn(null);

        List<ProductDto> result = productService.findAll();

        assertThat(result, notNullValue());
        assertThat(result, hasSize(0));
        verify(productDao, times(1)).findAll();
    }

    @Test
    void testFindByName_shouldReturnListOfThreeProducts_forExistingProducts() {
        List<ProductDto> listOfThreeProducts = new ArrayList<>();
        listOfThreeProducts.add(productCanyon);
        listOfThreeProducts.add(productLevi);
        listOfThreeProducts.add(productSofm);
        when(productDao.findByName(anyString())).thenReturn(listOfThreeProducts);

        List<ProductDto> result = productService.findByName(NAME_OF_THREE_PRODUCTS);

        assertThat(result, notNullValue());
        assertThat(result, hasSize(3));
        assertThat(result, hasItem(productCanyon));
        assertThat(result, hasItem(productLevi));
        assertThat(result, hasItem(productSofm));
        verify(productDao, times(1)).findByName(anyString());
    }

    @Test
    void testFindByName_shouldReturnListOfOneProduct_forExistingProduct() {
        List<ProductDto> listOfOneProduct = new ArrayList<>();
        listOfOneProduct.add(productSofm);

        when(productDao.findByName(anyString())).thenReturn(listOfOneProduct);

        List<ProductDto> result = productService.findByName(NAME_OF_ONE_PRODUCT);

        assertThat(result, notNullValue());
        assertThat(result, hasSize(1));
        assertThat(listOfOneProduct, hasItem(productSofm));
        verify(productDao, times(1)).findByName(anyString());

    }

    @Test
    void testFindByName_shouldYieldEmptyList_forAnyProductsNotBeMatch() {
        when(productDao.findByName(anyString())).thenReturn(null);

        List<ProductDto> result = productService.findByName(NAME_OF_NONE_PRODUCT);

        assertThat(result, notNullValue());
        assertThat(result, hasSize(0));
        verify(productDao, times(1)).findByName(anyString());
    }
}