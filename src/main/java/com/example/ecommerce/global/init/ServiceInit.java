//package com.example.ecommerce.global.init;
//
//import com.example.ecommerce.domain.category.service.CategoryService;
//import lombok.RequiredArgsConstructor;
//import org.springframework.beans.factory.InitializingBean;
//import org.springframework.stereotype.Component;
//
//@Component
//@RequiredArgsConstructor
//public class ServiceInit implements InitializingBean {
//
//    private final CategoryService categoryService;
//
//
//    @Override
//    public void afterPropertiesSet() {
//        this.init();
//    }
//
//
//    public void init() {
//        this.createCategory();
//    }
//
//    // 어플리케이션 실행 시 카테고리 생성
//    // "의류/잡화"
//    // "식품"
//    // "생활용품"
//    // "가전디지털"
//    // "도서/음반/DVD"
//    // "문구/오피스"
//    // "헬스/건강식품"
//    public void createCategory() {
//        this.categoryService.create();
//    }
//
//
//}
