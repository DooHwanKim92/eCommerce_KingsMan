package com.example.ecommerce.domain.cart.controller;


import com.example.ecommerce.domain.cart.CartSelectForm;
import com.example.ecommerce.domain.cart.entity.Cart;
import com.example.ecommerce.domain.cart.service.CartService;
import com.example.ecommerce.domain.orderdetail.OrderDetailCreateForm;
import com.example.ecommerce.domain.orders.OrdersCreateForm;
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

    private final OrdersService ordersService;

    @GetMapping("/add/{id}")
    public String addProduct(Model model, @PathVariable(value = "id") Long id, Principal principal) {
        SiteUser user = this.userService.findByUsername(principal.getName());
        Product product = this.productService.findById(id);

        if(this.ordersService.findByUserandProduct(user,product).isEmpty()) {
            return String.format("redirect:/product/detail/%d",id);
        }

        this.userService.addCart(user, this.cartService.addProduct(user, product));

        return String.format("redirect:/product/detail/%d",id);
    }

    @GetMapping("/list")
    public String cartList(Model model, Principal principal, CartSelectForm cartSelectForm) {
        if(principal==null) {
            return "redirect:/user/login";
        }
        SiteUser user = this.userService.findByUsername(principal.getName());
        List<Cart> cartList = user.getCartList();
        model.addAttribute("cartList",cartList);
        return "/cart/list";
    }


    @PostMapping("/remove")
    public String removeProduct(Model model, Principal principal, @Valid CartSelectForm cartSelectForm, BindingResult bindingResult) {
        if (cartSelectForm.getCartId() == null) {
            bindingResult.reject("cartIdisEmpty","상품을 선택해주세요.");
            SiteUser user = this.userService.findByUsername(principal.getName());
            List<Cart> cartList = user.getCartList();
            model.addAttribute("cartList",cartList);
            return "/cart/list";
        }
        this.cartService.remove(cartSelectForm.getCartId());
        return "redirect:/cart/list";
    }


    @PostMapping("/buy")
    public String buyProducts(Model model, Principal principal, OrderDetailCreateForm orderDetailCreateForm, @Valid CartSelectForm cartSelectForm, BindingResult bindingResult) {
        SiteUser user = this.userService.findByUsername(principal.getName());
        List<Cart> cartList = user.getCartList();

        if (cartSelectForm.getCartId() == null) {
            bindingResult.reject("cartIdisEmpty","상품을 선택해주세요.");
            model.addAttribute("cartList",cartList);
            return "/cart/list";
        }

        List<Product> productList = this.cartService.getProductListByCartId(cartSelectForm.getCartId());

        int totalPrice = 0;

        for ( int i = 0 ; i < productList.size(); i ++) {
            totalPrice += this.ordersService.getTotalPrice(productList.get(i),user);
        }

        model.addAttribute("productList", productList);
        model.addAttribute("user", user);
        model.addAttribute("totalPrice", totalPrice);


        return "/orders/detail";
    }

}
