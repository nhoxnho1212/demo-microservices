package com.tung.productservice.controller;

import com.tung.productservice.dto.ProductDto;
import com.tung.productservice.dto.paging.Page;
import com.tung.productservice.payload.request.ProductPagingRequest;
import com.tung.productservice.payload.response.ApiResponse;
import com.tung.productservice.payload.response.ProductResponse;
import com.tung.productservice.service.ProductService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.*;

@RestController
@RequestMapping(value = "${product-service.api.url.main:/product}")
public class ProductController {

    private ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public List<ProductResponse> getAllProducts() {
        List<ProductDto> result = productService.findAll();

        List<ProductResponse> productResponseList = new ArrayList<>();

        for (ProductDto product : result) {
            ProductResponse productResponse = new ProductResponse();
            BeanUtils.copyProperties(product, productResponse);
            productResponseList.add(productResponse);
        }

        return productResponseList;
    }

    @GetMapping(
            path = "/search"
    )
    public List<ProductResponse> getProductsByName(@RequestParam(name = "name") String name) {
        Set<ProductDto> result = new HashSet<>();
        if ( name == null) {
            result = new HashSet<>(productService.findAll());
        } else {
            name = name.trim();
            Set<String> nameSearch = new HashSet<>(Arrays.asList(name.split("\\s+")));
            for (String n : nameSearch) {
                result.addAll(productService.findByName(n));
            }
        }

        List<ProductResponse> productResponseList = new ArrayList<>();

        for (ProductDto product : result) {
            ProductResponse productResponse = new ProductResponse();
            BeanUtils.copyProperties(product, productResponse);
            productResponseList.add(productResponse);
        }

        return productResponseList;
    }

    @PostMapping(path = "/searchAndPaging")
    public Page<ProductResponse> getProductPaging(@Valid @RequestBody ProductPagingRequest request) {

        Page<ProductDto> result = productService.findAndPaging(request);

        List<ProductResponse> productResponseList = new ArrayList<>();

        for (ProductDto product : result.getData()) {
            ProductResponse productResponse = new ProductResponse();
            BeanUtils.copyProperties(product, productResponse);
            productResponseList.add(productResponse);
        }

        return new Page<>(productResponseList, result.getTotal());
    }

}
