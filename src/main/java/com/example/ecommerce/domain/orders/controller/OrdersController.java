package com.example.ecommerce.domain.orders.controller;


import com.example.ecommerce.domain.option.entity.Option;
import com.example.ecommerce.domain.orderdetail.OrderDetailCreateForm;
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
import org.springframework.web.bind.annotation.GetMapping;
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
        if(principal==null) {
            return "redirect:/user/login";
        }
        SiteUser user = this.userService.findByUsername(principal.getName());
        Product product = this.productService.findById(id);

        model.addAttribute("product",product);

        if(bindingResult.hasErrors()) {
            return "/product/detail";
        }
        if(ordersCreateForm.getOption().contains("[필수] 옵션 선택")) {
            bindingResult.rejectValue("option","unChoicOption","옵션을 선택해주세요.");
            return "/product/detail";
        }

        Orders orders = this.ordersService.create(user,product,ordersCreateForm);
        model.addAttribute("orders",orders);

        return "/product/detail";
    }

    @GetMapping("/remove/{id}")
    public String removeOrders(Model model, @PathVariable(value = "id") Long id, OrdersCreateForm ordersCreateForm) {
        Product product = this.ordersService.findById(id).getProduct();
        this.ordersService.removeById(id);

        model.addAttribute("product",product);

        return "/product/detail";
    }

    @GetMapping("/buy/{id}")
    public String buyOrders(Model model, @PathVariable(value = "id") Long id, Principal principal, OrderDetailCreateForm orderDetailCreateForm) {

        Product product = this.productService.findById(id);
        SiteUser user = this.userService.findByUsername(principal.getName());

        int totalPrice = 0;

        totalPrice = this.ordersService.getTotalPrice(product, user);

        if(totalPrice==0) {
            return String.format("redirect:/product/detail/%d",id);
        }

        model.addAttribute("product", product);
        model.addAttribute("user", user);
        model.addAttribute("totalPrice", totalPrice);

        return "/orders/detail";
    }

}
