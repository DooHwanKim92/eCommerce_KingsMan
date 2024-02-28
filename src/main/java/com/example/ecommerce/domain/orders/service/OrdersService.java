package com.example.ecommerce.domain.orders.service;


import com.example.ecommerce.domain.orders.OrdersCreateForm;
import com.example.ecommerce.domain.orders.entity.Orders;
import com.example.ecommerce.domain.orders.repository.OrdersRepository;
import com.example.ecommerce.domain.product.entity.Product;
import com.example.ecommerce.domain.user.entity.SiteUser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrdersService {

    private final OrdersRepository ordersRepository;

    public Orders create(SiteUser user, Product product, OrdersCreateForm ordersCreateForm) {

        Orders orders = Orders.builder()
                .user(user)
                .product(product)
                .option1(ordersCreateForm.getOption1())
                .option2(ordersCreateForm.getOption2())
                .isPurchase(true)
                .build();

        this.ordersRepository.save(orders);

        return orders;
    }
}
