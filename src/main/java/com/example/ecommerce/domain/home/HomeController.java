package com.example.ecommerce.domain.home;


import com.example.ecommerce.domain.product.entity.Product;
import com.example.ecommerce.domain.product.service.ProductService;
import com.example.ecommerce.global.image.entity.Image;
import com.example.ecommerce.global.image.service.ImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class HomeController {

    private final ImageService imageService;

    private final ProductService productService;

    @GetMapping("/")
    public String root(Model model) {
        List<Product> bestSellerList = this.productService.getBestSeller();
        List<Product> newProductList = this.productService.getNewProducts();
        List<Product> highQualityProducts = this.productService.getHighQualityProducts();
        List<Image> mainImages = this.imageService.getMainImageList();
        model.addAttribute("bestSellerList",bestSellerList);
        model.addAttribute("newProductList",newProductList);
        model.addAttribute("highQualityProducts",highQualityProducts);
        model.addAttribute("mainImages",mainImages);
        return "home/index";
    }

    @GetMapping("/home/image")
    public String mainImage(Model model) {
        List<Image> mainImages = this.imageService.getMainImageList();
        model.addAttribute("mainImages",mainImages);
        return "/home/image";
    }

    @PostMapping("/home/image")
    public String mainImagePost(@RequestParam(value = "mainImg") List<MultipartFile> mainImg) throws IOException {
        this.imageService.createMainPageImg(mainImg);
        return "redirect:/";
    }

}
