package com.example.ecommerce.domain.orders.service;


import com.example.ecommerce.domain.orders.OrdersCreateForm;
import com.example.ecommerce.domain.orders.entity.Orders;
import com.example.ecommerce.domain.orders.repository.OrdersRepository;
import com.example.ecommerce.domain.product.entity.Product;
import com.example.ecommerce.domain.user.entity.SiteUser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
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

        for (int i = 0; i < product.getOrdersList().size(); i++) {
            if (user == product.getOrdersList().get(i).getUser()) {
                if (product.getDiscount().equals("0")) {
                    totalPrice += Integer.parseInt(product.getPrice().replace(",", "")) * Integer.parseInt(product.getOrdersList().get(i).getAmount());
                } else {
                    totalPrice += product.getDCPrice() * Integer.parseInt(product.getOrdersList().get(i).getAmount());
                }
            }
        }

        return totalPrice;
    }

    public List<Orders> findByUserandProduct(SiteUser user, Product product) {
        List<Orders> ordersList = new ArrayList<>();
        for (int i = 0; i < product.getOrdersList().size(); i++) {
            if (user == product.getOrdersList().get(i).getUser()) {
                ordersList.add(product.getOrdersList().get(i));
            }
        }
        return ordersList;
    }
}
