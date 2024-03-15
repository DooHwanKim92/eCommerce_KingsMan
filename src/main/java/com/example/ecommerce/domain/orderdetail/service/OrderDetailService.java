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
import java.util.Optional;

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

        int point = 0;

        if(user.getGrade().equals("브론즈")) {
            point = (int) (totalPrice * 0.001);
            // 0.1% 적립
        } else if (user.getGrade().equals("실버")) {
            point = (int) (totalPrice * 0.002);
        } else if (user.getGrade().equals("골드")) {
            point = (int) (totalPrice * 0.003);
        } else if (user.getGrade().equals("에메랄드")) {
            point = (int) (totalPrice * 0.005);
        } else if (user.getGrade().equals("다이아몬드")) {
            point = (int) (totalPrice * 0.008);
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
                .savingPoint(point)
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

    public OrderDetail findById(Long id) {
        Optional<OrderDetail> orderDetail = this.orderDetailRepository.findById(id);
        if (orderDetail.isEmpty()) {
            return null;
        }
        return orderDetail.get();
    }

    public void removeById(Long id) {
        OrderDetail orderDetail = this.findById(id);
        this.orderDetailRepository.delete(orderDetail);
    }
}
