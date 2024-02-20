package com.example.ecommerce.domain.orderdetail.entity;


import com.example.ecommerce.global.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@Entity
@Getter
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class OrderDetail extends BaseEntity {

    @Column
    private Integer productAmount;

    @Column
    private String howToPay;

    @Column
    private String receiverName;

    @Column
    private String receiverAddress;

    @Column
    private String receiverPhoneNumber;

    @Column
    private String transportState;

    @Column
    private LocalDateTime productArrivedAt;
}
