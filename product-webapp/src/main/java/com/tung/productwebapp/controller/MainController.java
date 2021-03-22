package com.tung.productwebapp.controller;

import com.tung.productwebapp.model.Category;
import com.tung.productwebapp.service.CategoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.*;

@Controller
public class MainController {

    @Autowired
    CategoryService categoryService;

    @Autowired
    Environment environment;

    Logger logger = LoggerFactory.getLogger(MainController.class);

    @RequestMapping("/")
    public ModelAndView getIndexPage() {

        List<Category> categoryList = categoryService.getAll();

        ModelAndView modelAndView = new ModelAndView("index");
        modelAndView.addObject(categoryList);
        return modelAndView;

    }
}
