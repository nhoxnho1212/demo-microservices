package com.tung.productwebapp.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tung.productwebapp.model.Category;
import com.tung.productwebapp.model.Product;
import com.tung.productwebapp.payload.request.ApiRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

import java.lang.reflect.Type;
import java.util.*;
import java.util.stream.Collectors;

@Controller
public class ProductController {

    @Autowired
    RestTemplate restTemplate;

    @RequestMapping(value = "/product", method = RequestMethod.GET)
    public ModelAndView getAllProduct(@RequestParam(name = "name") String name, @RequestParam(name = "categoryId") List<String> listCategories) {
        ResponseEntity<ApiRequest> response= restTemplate.getForEntity(
                "http://localhost:8081/product/search?name=" + name + " &",
                ApiRequest.class
        );
        ApiRequest apiRequestProduct = response.getBody();
        List<Product> productList = new ArrayList<>();

        if (apiRequestProduct != null && apiRequestProduct.getSuccess()) {
            ObjectMapper mapper = new ObjectMapper();
            productList = mapper.convertValue(apiRequestProduct.getMessage(), new TypeReference<List<Product>>() {});
        }
        ModelAndView modelAndView = new ModelAndView("index::tableProducts");
        modelAndView.addObject(productList);
        return modelAndView;
    }

    @RequestMapping(value = "/product1", method = RequestMethod.GET)
    public ResponseEntity<ApiRequest> getAllProduct1(@RequestParam(name = "name") String name) {
        ResponseEntity<ApiRequest> response= restTemplate.getForEntity(
                "http://localhost:8081/product/search?name=" + name,
                ApiRequest.class
        );
        ApiRequest apiRequestProduct = response.getBody();
        List<Product> productList = new ArrayList<>();
        ResponseEntity<ApiRequest> responseCategory = restTemplate.getForEntity(
                "http://localhost:8001/category",
                ApiRequest.class
        );
        ApiRequest apiRequestCategory = responseCategory.getBody();
        List<Category> categoryList = new ArrayList<>();

        if (apiRequestCategory.getSuccess()) {
            ObjectMapper mapper = new ObjectMapper();
            categoryList = mapper.convertValue(apiRequestCategory.getMessage(), new TypeReference<List<Category>>() {});
        }
        List<LinkedHashMap> productListResponse = (List<LinkedHashMap>) apiRequestProduct.getMessage();
        if (apiRequestProduct != null && apiRequestProduct.getSuccess()) {
            for (LinkedHashMap productRequest : productListResponse) {
                Product product = new Product();
                product.setId(new Long(productRequest.get("id").toString()));

                Category category = categoryList.stream()
                        .filter(c -> c.getId().contains(productRequest.get("category").toString()) )
                        .limit(1)
                        .collect(Collectors.toList())
                        .get(0);
                product.setCategory(category);

                product.setPrice((Double.valueOf(productRequest.get("price").toString())));
                product.setName(productRequest.get("name").toString());
                productList.add(product);
            }
        }
        ApiRequest apiRequest = new ApiRequest(Boolean.TRUE, productList);
        return new ResponseEntity<>(apiRequest, HttpStatus.OK);
    }
}
