package com.example.ecommerce.global.image.controller;


import com.example.ecommerce.global.image.service.ImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class ImageController {


    private final ImageService imageService;


}
