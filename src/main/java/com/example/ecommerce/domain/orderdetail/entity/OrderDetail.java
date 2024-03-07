package com.example.ecommerce.domain.orderdetail.entity;


import com.example.ecommerce.domain.orders.entity.Orders;
import com.example.ecommerce.domain.product.entity.Product;
import com.example.ecommerce.domain.user.entity.SiteUser;
import com.example.ecommerce.global.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.boot.context.properties.bind.DefaultValue;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class OrderDetail extends BaseEntity {

    @OneToMany(mappedBy = "orderDetail",cascade = CascadeType.REMOVE)
    private List<Orders> ordersList;

    @ManyToOne
    private SiteUser user;

    @Column
    private String receiverName;

    @Column
    private String receiverAddress;

    @Column
    private String receiverPhoneNumber;

    @Column
    private String howToPay;

    @Column
    // 주문 시 결제한 금액
    private Integer totalPrice;

    @Column
    // 구매 여부
    private boolean isPurchase;

}
