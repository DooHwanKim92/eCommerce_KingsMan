package com.example.ecommerce.global.rebate.controller;


import com.example.ecommerce.domain.product.service.ProductService;
import com.example.ecommerce.domain.user.entity.SiteUser;
import com.example.ecommerce.domain.user.service.UserService;
import com.example.ecommerce.global.rebate.entity.Rebate;
import com.example.ecommerce.global.rebate.service.RebateService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.time.LocalDateTime;

@Controller
@RequiredArgsConstructor
@RequestMapping("/rebate")
public class RebateController {

    private final RebateService rebateService;

    private final UserService userService;

    private final ProductService productService;

    @PostMapping("/create")
    public String createRebate(@RequestParam(value = "startDate")String startDate, @RequestParam(value = "endDate") String endDate, Principal principal, Model model) {

        SiteUser user = this.userService.findByUsername(principal.getName());

        LocalDateTime startDateTime = this.rebateService.formattingStartDate(startDate);
        LocalDateTime endDateTime = this.rebateService.formattingEndDate(endDate);

        Rebate rebate = this.rebateService.create(user, startDateTime, endDateTime);

        int totalIncome = this.productService.getIncome(rebate.getProductList());
        int totalPurchashing = this.productService.getPurchasing(rebate.getProductList());

        model.addAttribute("rebate",rebate);
        model.addAttribute("totalIncome",totalIncome);
        model.addAttribute("totalPurchashing",totalPurchashing);

        return "/rebate/list";
    }


}
