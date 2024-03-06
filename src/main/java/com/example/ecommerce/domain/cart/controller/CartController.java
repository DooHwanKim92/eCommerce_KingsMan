package com.example.ecommerce.domain.cart.controller;


import com.example.ecommerce.domain.cart.entity.Cart;
import com.example.ecommerce.domain.cart.service.CartService;
import com.example.ecommerce.domain.product.entity.Product;
import com.example.ecommerce.domain.product.service.ProductService;
import com.example.ecommerce.domain.user.entity.SiteUser;
import com.example.ecommerce.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/cart")
@RequiredArgsConstructor
public class CartController {

    private final UserService userService;

    private final ProductService productService;

    private final CartService cartService;

    @GetMapping("/add/{id}")
    public String addProduct(@PathVariable(value = "id") Long id, Principal principal) {
        SiteUser user = this.userService.findByUsername(principal.getName());
        Product product = this.productService.findById(id);
        this.userService.addCart(user, this.cartService.addProduct(user, product));
        return String.format("redirect:/product/detail/%d",id);
    }

    @GetMapping("/list")
    public String cartList(Model model, Principal principal) {
        if(principal==null) {
            return "redirect:/user/login";
        }
        SiteUser user = this.userService.findByUsername(principal.getName());
        List<Cart> cartList = user.getCartList();
        model.addAttribute("cartList",cartList);
        return "/cart/list";
    }

    @PostMapping("/remove")
    public String removeProduct(@RequestParam(value = "cartId") List<Long> id) {
        this.cartService.remove(id);
        return "redirect:/cart/list";
    }

}
