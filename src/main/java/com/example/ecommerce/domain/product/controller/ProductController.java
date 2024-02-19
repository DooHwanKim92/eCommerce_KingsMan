package com.example.ecommerce.domain.product.controller;


import com.example.ecommerce.domain.category.entity.Category;
import com.example.ecommerce.domain.category.service.CategoryService;
import com.example.ecommerce.domain.product.ProductCreateForm;
import com.example.ecommerce.domain.product.entity.Product;
import com.example.ecommerce.domain.product.service.ProductService;
import com.example.ecommerce.domain.user.entity.SiteUser;
import com.example.ecommerce.domain.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/product")
@RequiredArgsConstructor
public class ProductController {


    private final UserService userService;

    private final ProductService productService;

    private final CategoryService categoryService;

    @GetMapping("/list")
    public String productList(Model model) {
        List<Product> productList = this.productService.getList();
        model.addAttribute("productList",productList);
        return "/product/list";
    }

    @GetMapping("/put")
    public String productPut(Model model) {
        List<Category> categoryList = this.categoryService.findAll();
        model.addAttribute("categoryList",categoryList);
        return "/product/put";
    }

    @PostMapping("/put")
    public String productPutPost(@Valid ProductCreateForm productCreateForm, BindingResult bindingResult, Principal principal) {
        SiteUser user = this.userService.findByUsername(principal.getName());
        Category category = this.categoryService.findByname(productCreateForm.getCategory());
        this.productService.createProduct(productCreateForm,user,category);
        return "redirect:/product/list";
    }

    @GetMapping("/detail/{id}")
    public String productDetail(Model model, @PathVariable(value = "id") Long id) {
        Product product = this.productService.findById(id);
        model.addAttribute("product",product);
        return "/product/detail";
    }

    @GetMapping("/remove/{id}")
    public String productRemove(@PathVariable(value = "id") Long id) {
        this.productService.removeProduct(id);
        return "redirect:/product/list";
    }
}
