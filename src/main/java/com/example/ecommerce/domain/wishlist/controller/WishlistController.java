package com.example.ecommerce.domain.wishlist.controller;


import com.example.ecommerce.domain.wishlist.service.WishlistService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/wish")
@RequiredArgsConstructor
public class WishlistController {


    private final WishlistService wishlistService;


}
