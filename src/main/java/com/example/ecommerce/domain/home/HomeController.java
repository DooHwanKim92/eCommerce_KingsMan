package com.example.ecommerce.domain.home;


import com.example.ecommerce.global.image.entity.Image;
import com.example.ecommerce.global.image.service.ImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
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

    @GetMapping("/")
    public String root() {
        return "home/index";
    }

    @GetMapping("/home/image")
    public String mainImage() {
        return "/home/image";
    }

    @PostMapping("/home/image")
    public String mainImagePost(@RequestParam(value = "mainImg") List<MultipartFile> mainImg) throws IOException {
        this.imageService.createMainPageImg(mainImg);
        return "redirect:/";
    }

}
