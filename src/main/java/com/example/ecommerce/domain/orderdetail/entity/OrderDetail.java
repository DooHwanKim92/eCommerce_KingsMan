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

    @ManyToOne
    // 구매한 상품
    private Product product;

    @ManyToOne
    // 구매자
    private SiteUser user;

    @Column
    private String productOption;

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
    // 구매 시 적립 포인트
    private Integer savingPoint;

    @Column
    // 구매 시 사용한 포인트
    private Integer usedPoint;

    @Column
    // 구매 여부
    private boolean isPurchase;

}
