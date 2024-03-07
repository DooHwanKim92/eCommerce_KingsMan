package com.example.ecommerce.domain.orderdetail.service;


import com.example.ecommerce.domain.orderdetail.OrderDetailCreateForm;
import com.example.ecommerce.domain.orderdetail.entity.OrderDetail;
import com.example.ecommerce.domain.orderdetail.repository.OrderDetailRepository;
import com.example.ecommerce.domain.orders.entity.Orders;
import com.example.ecommerce.domain.product.entity.Product;
import com.example.ecommerce.domain.user.entity.SiteUser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderDetailService {

    private final OrderDetailRepository orderDetailRepository;

    public OrderDetail create(List<Orders> ordersList, SiteUser user, OrderDetailCreateForm orderDetailCreateForm, int totalPrice) {

        OrderDetail orderDetail = OrderDetail.builder()
                .ordersList(ordersList)
                .user(user)
                .receiverName(orderDetailCreateForm.getName())
                .receiverAddress(orderDetailCreateForm.getPostnum() +", "+ orderDetailCreateForm.getRoadname() +", "+ orderDetailCreateForm.getGroundname() +", "+ orderDetailCreateForm.getDetailaddress() +", "+ orderDetailCreateForm.getEtc())
                .receiverPhoneNumber(orderDetailCreateForm.getPhoneNumber())
                .howToPay(orderDetailCreateForm.getHowToPay())
                .totalPrice(totalPrice)
                .isPurchase(true)
                .build();

        this.orderDetailRepository.save(orderDetail);

        return orderDetail;
    }
}
