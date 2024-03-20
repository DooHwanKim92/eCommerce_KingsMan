package com.example.ecommerce.global.rebate.service;


import com.example.ecommerce.domain.orderdetail.service.OrderDetailService;
import com.example.ecommerce.domain.product.entity.Product;
import com.example.ecommerce.domain.product.service.ProductService;
import com.example.ecommerce.domain.user.entity.SiteUser;
import com.example.ecommerce.global.rebate.entity.Rebate;
import com.example.ecommerce.global.rebate.repository.RebateRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RebateService {

    private final RebateRepository rebateRepository;

    private final ProductService productService;

    private final OrderDetailService orderDetailService;

    public LocalDateTime formattingStartDate(String startDate) {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime dateTime = LocalDateTime.parse(startDate + " 00:00:00", formatter);

        return dateTime;
    }


    public LocalDateTime formattingEndDate(String endDate) {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime dateTime = LocalDateTime.parse(endDate + " 23:59:59", formatter);

        return dateTime;
    }

    public Rebate create(SiteUser user, LocalDateTime startDateTime, LocalDateTime endDateTime) {

        List<Product> productList = this.orderDetailService.getRebateList(user,startDateTime,endDateTime);

        Rebate rebate = Rebate.builder()
                .user(user)
                .startDate(startDateTime)
                .endDate(endDateTime)
                .productList(productList)
                .build();

        this.rebateRepository.save(rebate);

        return rebate;
    }
}
