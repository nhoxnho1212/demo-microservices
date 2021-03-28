package com.tung.productwebapp.gateway;

import com.tung.productwebapp.model.Product;
import com.tung.productwebapp.model.ProductRequest;
import com.tung.productwebapp.model.SearchAndPagingProductRequest;
import com.tung.productwebapp.model.paging.Page;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.*;

@FeignClient(value = "product-client", url = "${product-service.api.uri}")
public interface ProductClient {
    @GetMapping
    List<ProductRequest> findAll();

    @GetMapping(path = "/search")
    List<ProductRequest> findByName(@RequestParam(name = "name") String name);

    @PostMapping(path = "/searchAndPaging")
    Page<ProductRequest> searchAndPaging(@RequestBody SearchAndPagingProductRequest request);
}
