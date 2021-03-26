package com.tung.productwebapp.gateway;

import com.tung.productwebapp.model.Category;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.*;

@FeignClient(value = "category-client", url = "${category-service.api.uri}")
public interface CategoryClient {
    @GetMapping
    List<Category> getAll();

    @GetMapping(path = "/{categoryId}")
    Category findById(@PathVariable String categoryId);
}
