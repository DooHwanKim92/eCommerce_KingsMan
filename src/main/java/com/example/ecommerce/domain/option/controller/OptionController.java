package com.example.ecommerce.domain.option.controller;


import com.example.ecommerce.domain.option.OptionCreateForm;
import com.example.ecommerce.domain.option.entity.Option;
import com.example.ecommerce.domain.option.service.OptionService;
import com.example.ecommerce.domain.product.controller.ProductController;
import com.example.ecommerce.domain.product.entity.Product;
import com.example.ecommerce.domain.product.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.Banner;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/option")
public class OptionController {

    private final ProductService productService;

    private final OptionService optionService;

    @GetMapping("/create/{id}")
    public String createOption(Model model, @PathVariable(value = "id") Long id, OptionCreateForm optionCreateForm) {
        Product product = this.productService.findById(id);
        model.addAttribute("product",product);
        return "/product/create_option";
    }

    @PostMapping("/create/{id}")
    public String createOptionPost(Model model, @PathVariable(value = "id") Long id, @Valid OptionCreateForm optionCreateForm, BindingResult bindingResult) {
        Product product = this.productService.findById(id);
        model.addAttribute("product",product);
        if(bindingResult.hasErrors()) {
            return "/product/create_option";
        }
        Option option = this.optionService.create(optionCreateForm,product);
        this.productService.addOption(product,option);
        return String.format("redirect:/option/create/%d",id);
    }
}
