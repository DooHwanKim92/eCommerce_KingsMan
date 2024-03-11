package com.example.ecommerce.domain.review.controller;


import com.example.ecommerce.domain.product.entity.Product;
import com.example.ecommerce.domain.product.service.ProductService;
import com.example.ecommerce.domain.review.ReviewCreateForm;
import com.example.ecommerce.domain.review.service.ReviewService;
import com.example.ecommerce.domain.user.entity.SiteUser;
import com.example.ecommerce.domain.user.service.UserService;
import com.example.ecommerce.global.image.service.ImageService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.Principal;

@Controller
@RequestMapping("/review")
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;

    private final ProductService productService;

    private final UserService userService;

    private final ImageService imageService;

    @GetMapping("/create/{id}")
    public String createReview(Model model, @PathVariable(value = "id") Long id, ReviewCreateForm reviewCreateForm, Principal principal) {
        Product product = this.productService.findById(id);
        SiteUser user = this.userService.findByUsername(principal.getName());
        boolean isPurchase = false;
        for(int i = 0; i <user.getOrderDetailList().size(); i++) {
            if(user.getOrderDetailList().get(i).getProduct() == product) {
                isPurchase = user.getOrderDetailList().get(i).isPurchase();
            }
        }
        if(!isPurchase) {
            return String.format("redirect:/product/detail/%d",id);
        }
        model.addAttribute("product",product);
        return "/review/create";
    }

    @PostMapping("/create/{id}")
    public String createPost(Model model, @Valid ReviewCreateForm reviewCreateForm, BindingResult bindingResult, @PathVariable(value = "id") Long id, Principal principal, @RequestParam(value = "reviewImg") MultipartFile reviewImg) throws IOException {
        Product product = this.productService.findById(id);
        SiteUser user = this.userService.findByUsername(principal.getName());
        model.addAttribute("product",product);

        if(bindingResult.hasErrors()) {
            return "/review/create";
        }

        this.imageService.createReviewImg(this.reviewService.create(user,product,reviewCreateForm), reviewImg);
        return String.format("redirect:/product/detail/%d",id);
    }

}
