package com.example.ecommerce.global.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
    @Value("${custom.fileDirPath}")
    private String fileDirPath;

    @Override
    // 저장된 이미지 파일의 경로를 읽어오는데 필요함
    public void addResourceHandlers (ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/file/**")
                .addResourceLocations("file:///" + fileDirPath + "/");
    }
}

