package com.example.ecommerce.domain.wishlist.controller;


import com.example.ecommerce.domain.orders.service.OrdersService;
import com.example.ecommerce.domain.product.entity.Product;
import com.example.ecommerce.domain.product.service.ProductService;
import com.example.ecommerce.domain.user.entity.SiteUser;
import com.example.ecommerce.domain.user.service.UserService;
import com.example.ecommerce.domain.wishlist.service.WishlistService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

@Controller
@RequestMapping("/wish")
@RequiredArgsConstructor
public class WishlistController {


    private final WishlistService wishlistService;

    private final UserService userService;

    private final ProductService productService;

    private final OrdersService ordersService;

    @GetMapping("/add/{id}")
    public String addWish(Model model, @PathVariable(value = "id") Long id, Principal principal) {
        SiteUser user = this.userService.findByUsername(principal.getName());
        Product product = this.productService.findById(id);

        if(this.ordersService.findByUserandProduct(user,product).isEmpty()) {
            return String.format("redirect:/product/detail/%d",id);
        }

        this.userService.addWishList(user, this.wishlistService.addProduct(user, product));

        String isWish = "";
        isWish = this.wishlistService.isWish(user, product);

        model.addAttribute("isWish",isWish);

        return String.format("redirect:/product/detail/%d",id);
    }

}
