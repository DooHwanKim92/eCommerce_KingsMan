package com.example.ecommerce.domain.product.entity;

import com.example.ecommerce.domain.cart.entity.Cart;
import com.example.ecommerce.global.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;



@Entity
@Getter
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class Product extends BaseEntity {

    @Column
    private String sellerName;

    @Column
    private String category;

    @Column
    private String name;

    @Column
    private String content;

    @Column
    private Integer amount;

    @Column
    private Integer price;

    @Column
    private Integer purchasing;

    @Column
    private Integer discount;

    @Column
    private boolean isNew;

    @Column
    private Long reviewId;


}
