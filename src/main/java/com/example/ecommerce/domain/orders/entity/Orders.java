package com.example.ecommerce.domain.orders.entity;


import com.example.ecommerce.domain.product.entity.Product;
import com.example.ecommerce.domain.user.entity.SiteUser;
import com.example.ecommerce.global.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@Getter
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class Orders extends BaseEntity {

    @ManyToOne
    private SiteUser user;

    @ManyToOne
    private Product product;

    @Column
    private boolean isPurchase;

    @Column
    private String option1;

    @Column
    private String option2;
}
