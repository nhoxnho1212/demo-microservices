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

@ExtendWith(MockitoExtension.class)
class ProductDtoServiceImplTest {

    @InjectMocks
    private ProductService productService;

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
        Mockito.when(productDao.findAll()).thenReturn(productList);

        List<ProductDto> result = productService.findAll();

        assertNotNull(result);
        assertEquals(result.size(), productList.size());
        for (int idx = 0; idx < productList.size(); idx++ ) {
            assertEquals(result.get(idx).getId(), productList.get(idx).getId());
            assertEquals(result.get(idx).getName(), productList.get(idx).getName());
            assertEquals(result.get(idx).getPrice(), productList.get(idx).getPrice());
            assertEquals(result.get(idx).getCategory(), productList.get(idx).getCategory());
        }

        Mockito.verify(productDao, Mockito.times(1)).findAll();
    }

    @Test
    void testFindAll_NotAnyProducts() {
        Mockito.when(productDao.findAll()).thenReturn(null);

        List<ProductDto> result = productService.findAll();

        assertNotNull(result);
        assertEquals(result.size(), 0);
        Mockito.verify(productDao, Mockito.times(1)).findAll();
    }

    @Test
    void testFindByName_shouldReturnListOfThreeProducts_forExistingProducts() {
        List<ProductDto> listOfThreeProducts = new ArrayList<>();
        listOfThreeProducts.add(productCanyon);
        listOfThreeProducts.add(productLevi);
        listOfThreeProducts.add(productSofm);
        Mockito.when(productDao.findByName(Mockito.anyString())).thenReturn(listOfThreeProducts);

        List<ProductDto> result = productService.findByName(NAME_OF_THREE_PRODUCTS);

        assertNotNull(result);
        assertEquals(result.size(), 3);
        assertTrue(listOfThreeProducts.contains(productCanyon));
        assertTrue(listOfThreeProducts.contains(productSofm));
        assertTrue(listOfThreeProducts.contains(productLevi));
        Mockito.verify(productDao, Mockito.times(1)).findByName(Mockito.anyString());
    }

    @Test
    void testFindByName_shouldReturnListOfOneProduct_forExistingProduct() {
        List<ProductDto> listOfOneProduct = new ArrayList<>();
        listOfOneProduct.add(productSofm);

        Mockito.when(productDao.findByName(Mockito.anyString())).thenReturn(listOfOneProduct);

        List<ProductDto> result = productService.findByName(NAME_OF_ONE_PRODUCT);

        assertNotNull(result);
        assertEquals(result.size(), 1);
        assertTrue(listOfOneProduct.contains(productSofm));
        Mockito.verify(productDao, Mockito.times(1)).findByName(Mockito.anyString());

    }

    @Test
    void testFindByName_shouldYieldEmptyList_forAnyProductsNotBeMatch() {
        Mockito.when(productDao.findByName(Mockito.anyString())).thenReturn(null);

        List<ProductDto> result = productService.findByName(NAME_OF_NONE_PRODUCT);

        assertNotNull(result);
        assertEquals(result.size(), 0);
        Mockito.verify(productDao, Mockito.times(1)).findByName(Mockito.anyString());
    }
}