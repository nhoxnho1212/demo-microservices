package com.tung.productservice.service.impl;

import com.tung.productservice.model.entity.Category;
import com.tung.productservice.model.entity.Product;
import com.tung.productservice.model.repository.ProductRepository;
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
class ProductServiceImplTest {

    @InjectMocks
    private ProductService productService = new ProductServiceImpl();

    @Mock
    private ProductRepository productRepository;

    List<Product> productList;
    Product productSofm = new Product(1, "name Sofm TEST ONE TWO THREE", 100000.0, new Category("CATETEST1", "cat name TEST1"));
    Product productLevi = new Product(2, "name Levi TEST TWO THREE", 200000.0, new Category("CATETEST1", "cat name TEST1"));
    Product productCanyon = new Product(3, "name Cayon TEST THREE", 300000.0, new Category("CATETEST2", "cat name TEST2"));

    @BeforeEach
    void setUp() {
        productList = new ArrayList<>();
        productList.add(productSofm);
        productList.add(productLevi);
        productList.add(productCanyon);
    }

    @Test
    void testFindAll() {
        Mockito.when(productRepository.findAll()).thenReturn(productList);

        List<Product> result = productService.findAll();

        assertNotNull(result);
        assertEquals(result.size(), productList.size());
        for (int idx = 0; idx < productList.size(); idx++ ) {
            assertEquals(result.get(idx).getId(), productList.get(idx).getId());
            assertEquals(result.get(idx).getName(), productList.get(idx).getName());
            assertEquals(result.get(idx).getPrice(), productList.get(idx).getPrice());
            assertEquals(result.get(idx).getCategory().getId(), productList.get(idx).getCategory().getId());
            assertEquals(result.get(idx).getCategory().getName(), productList.get(idx).getCategory().getName());
        }

        Mockito.verify(productRepository, Mockito.times(1)).findAll();
    }
}