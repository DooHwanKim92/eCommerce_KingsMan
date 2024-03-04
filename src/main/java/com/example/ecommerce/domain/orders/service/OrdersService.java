package com.example.ecommerce.domain.orders.service;


import com.example.ecommerce.domain.orders.OrdersCreateForm;
import com.example.ecommerce.domain.orders.entity.Orders;
import com.example.ecommerce.domain.orders.repository.OrdersRepository;
import com.example.ecommerce.domain.product.entity.Product;
import com.example.ecommerce.domain.user.entity.SiteUser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OrdersService {

    private final OrdersRepository ordersRepository;

    public Orders findById(Long id) {
        Optional<Orders> orders = this.ordersRepository.findById(id);
        if (orders.isEmpty()) {
            return null;
        }
        return orders.get();
    }

    public Orders create(SiteUser user, Product product, OrdersCreateForm ordersCreateForm) {

        Orders orders = Orders.builder()
                .user(user)
                .product(product)
                .option(ordersCreateForm.getOption())
                .isPurchase(true)
                .amount(ordersCreateForm.getAmount())
                .build();

        this.ordersRepository.save(orders);

        return orders;
    }

    public void removeById(Long id) {
        Orders orders = this.findById(id);
        this.ordersRepository.delete(orders);
    }

    public int getTotalPrice(Product product, SiteUser user) {
        int totalPrice = 0;

        for(int i = 0 ; i < product.getOrdersList().size(); i++) {
            if(user==product.getOrdersList().get(i).getUser()) {
                totalPrice += Integer.parseInt(product.getPrice().replace(",","")) * Integer.parseInt(product.getOrdersList().get(i).getAmount());
            }
        }

        return totalPrice;
    }
}
