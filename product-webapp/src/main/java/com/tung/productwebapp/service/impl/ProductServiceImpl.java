package com.tung.productwebapp.service.impl;

import com.tung.productwebapp.gateway.CategoryClient;
import com.tung.productwebapp.gateway.ProductClient;
import com.tung.productwebapp.model.Category;
import com.tung.productwebapp.model.Product;
import com.tung.productwebapp.model.ProductRequest;
import com.tung.productwebapp.model.SearchAndPagingProductRequest;
import com.tung.productwebapp.model.paging.Page;
import com.tung.productwebapp.service.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@CacheConfig(cacheNames = {"PRODUCT"}, cacheManager = "productCacheManager")
public class ProductServiceImpl implements ProductService {

    private ProductClient productClient;

    private CategoryClient categoryClient;

    private final static Logger LOGGER = LoggerFactory.getLogger(ProductServiceImpl.class);

    @Autowired
    public ProductServiceImpl(ProductClient productClient, CategoryClient categoryClient) {
        this.productClient = productClient;
        this.categoryClient = categoryClient;
    }

    public ProductServiceImpl() {
    }

    private List<Product> convertProductsRequestToProducts(List<ProductRequest> productsRequest) {
        List<Category> categoryList = categoryClient.getAll();
        List<Product> productList = new ArrayList<>();

        Map<String, Category> categories = categoryList.stream().collect(Collectors.toMap(Category::getId, Function.identity()));

        for (ProductRequest productRequest : productsRequest) {
            Product product = new Product();
            product.setId(productRequest.getId());
            product.setCategory(categories.get(productRequest.getCategoryId()));
            product.setPrice(productRequest.getPrice());
            product.setName(productRequest.getName());

            productList.add(product);
        }
        return productList;
    }

    @Override
    public List<Product> getByName(String name) {
        List<ProductRequest> productListResponse =  productClient.findByName(name);

        List<Product> productList = convertProductsRequestToProducts(productListResponse);

        return productList;
    }

    @Override
    @Cacheable(key = "{#request.toString()}")
    public Page<Product> searchAndPaging(SearchAndPagingProductRequest request) {
        Page<ProductRequest> response = productClient.searchAndPaging(request);

        List<Product> productList = convertProductsRequestToProducts(response.getData());

        return new Page<>(productList, response.getTotal());
    }
}
