package com.example.ecommerce.domain.orders.controller;


import com.example.ecommerce.domain.orders.OrdersCreateForm;
import com.example.ecommerce.domain.orders.entity.Orders;
import com.example.ecommerce.domain.orders.service.OrdersService;
import com.example.ecommerce.domain.product.entity.Product;
import com.example.ecommerce.domain.product.service.ProductService;
import com.example.ecommerce.domain.user.entity.SiteUser;
import com.example.ecommerce.domain.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

@Controller
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrdersController {

    private final ProductService productService;

    private final UserService userService;

    private final OrdersService ordersService;

    @PostMapping("/option/{id}")
    public String selectOptionPost(Model model, @PathVariable(value = "id") Long id, Principal principal, @Valid OrdersCreateForm ordersCreateForm, BindingResult bindingResult) {
        SiteUser user = this.userService.findByUsername(principal.getName());
        Product product = this.productService.findById(id);
        if(ordersCreateForm.getOption1().equals("[필수] 옵션 선택")) {
            bindingResult.rejectValue("option1","unChoicOption1","옵션 1을 선택해주세요.");
            model.addAttribute("product",product);
            return "/product/detail";
        }
        if(ordersCreateForm.getOption2().equals("[필수] 옵션 선택")) {
            bindingResult.rejectValue("option2","unChoicOption2","옵션 2를 선택해주세요.");
            model.addAttribute("product",product);
            return "/product/detail";
        }
        Orders orders = this.ordersService.create(user,product,ordersCreateForm);
        model.addAttribute("product",product);
        model.addAttribute("orders",orders);
        return String.format("redirect:/product/detail/%d",id);
    }

}
