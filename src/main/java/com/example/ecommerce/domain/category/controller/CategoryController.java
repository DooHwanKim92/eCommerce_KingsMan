package com.example.ecommerce.domain.category.controller;


import com.example.ecommerce.domain.category.entity.Category;
import com.example.ecommerce.domain.category.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;


    public List<Category> findAll() {
        return this.categoryService.findAll();
    }

    public Category findByname(String category) {
        return this.categoryService.findByname(category);
    }

    public void create() {
        this.categoryService.create();
    }
}
