package com.tung.productwebapp.controller;

import com.tung.productwebapp.model.Category;
import com.tung.productwebapp.service.CategoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.*;

@Controller
public class MainController {

    Logger logger = LoggerFactory.getLogger(MainController.class);

    CategoryService categoryService;

    @Autowired
    public MainController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }


    @RequestMapping("/")
    public ModelAndView getIndexPage() {

        List<Category> categoryList = categoryService.getAll();

        ModelAndView modelAndView = new ModelAndView("page/index");
        modelAndView.addObject(categoryList);
        return modelAndView;

    }
}
