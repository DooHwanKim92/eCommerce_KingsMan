package com.example.ecommerce.domain.orderdetail.controller;


import com.example.ecommerce.domain.orderdetail.OrderDetailCreateForm;
import com.example.ecommerce.domain.orderdetail.entity.OrderDetail;
import com.example.ecommerce.domain.orderdetail.service.OrderDetailService;
import com.example.ecommerce.domain.orders.entity.Orders;
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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/orderdetail")
@RequiredArgsConstructor
public class OrderDetailController {

    private final OrderDetailService orderDetailService;

    private final ProductService productService;

    private final UserService userService;

    private final OrdersService ordersService;

    @PostMapping("/{id}")
    //구매 확정
    // 상품 판매 회수 증가 필요, 해당 상품에 대한 구매 유저의 구매 여부 true 변경 필요
    public String orderDetail(Model model, @PathVariable(value = "id") Long id, Principal principal, @Valid OrderDetailCreateForm orderDetailCreateForm, BindingResult bindingResult) {
        Product product = this.productService.findById(id);
        SiteUser user = this.userService.findByUsername(principal.getName());

        int totalPrice = 0;

        totalPrice = this.ordersService.getTotalPrice(product, user);

        if(totalPrice==0) {
            return String.format("redirect:/product/detail/%d",id);
        }

        model.addAttribute("product", product);
        model.addAttribute("user", user);
        model.addAttribute("totalPrice", totalPrice);

        if(bindingResult.hasErrors()) {
            return "/orders/detail";
        }

        List<Orders> ordersList = new ArrayList<>();

        for (int i = 0; i < product.getOrdersList().size(); i++) {
            if(product.getOrdersList().get(i).getUser() == user && product.getOrdersList().get(i).getProduct() == product) {
                Orders orders = this.ordersService.findById(product.getOrdersList().get(i).getId());
                ordersList.add(orders);
            }
        }

        OrderDetail orderDetail = this.orderDetailService.create(ordersList, user, orderDetailCreateForm, totalPrice);



        model.addAttribute("orderDetail",orderDetail);

        return "/orders/complete";
    }


}
