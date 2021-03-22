package com.tung.productwebapp.service;

import com.tung.productwebapp.model.Product;
import java.util.*;
public interface ProductService {
    List<Product> getByName(String name);
}
