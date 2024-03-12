package com.example.ecommerce.domain.orderdetail.service;


import com.example.ecommerce.domain.orderdetail.OrderDetailCreateForm;
import com.example.ecommerce.domain.orderdetail.entity.OrderDetail;
import com.example.ecommerce.domain.orderdetail.repository.OrderDetailRepository;
import com.example.ecommerce.domain.orders.entity.Orders;
import com.example.ecommerce.domain.product.entity.Product;
import com.example.ecommerce.domain.product.service.ProductService;
import com.example.ecommerce.domain.user.entity.SiteUser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderDetailService {

    private final ProductService productService;

    private final OrderDetailRepository orderDetailRepository;

    public OrderDetail create(Product product, SiteUser user, List<Orders> ordersList, OrderDetailCreateForm orderDetailCreateForm, int totalPrice) {

        String options = "";
        for ( int i = 0 ; i < ordersList.size(); i ++ ) {
            options += "옵션 : ";
            options += ordersList.get(i).getOption();
            options += ", ";
            options += "수량 : ";
            options += ordersList.get(i).getAmount();
        }

        OrderDetail orderDetail = OrderDetail.builder()
                .product(product)
                .user(user)
                .productOption(options)
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

    public List<Product> getProductListById(List<Long> productId) {
        List<Product> productList = new ArrayList<>();

        for ( int i = 0 ; i < productId.size(); i++) {
            productList.add(this.productService.findById(productId.get(i)));
        }

        return productList;
    }
}
