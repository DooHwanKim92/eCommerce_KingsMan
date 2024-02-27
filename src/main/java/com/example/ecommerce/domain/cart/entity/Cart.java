package com.example.ecommerce.domain.cart.entity;


import com.example.ecommerce.domain.product.entity.Product;
import com.example.ecommerce.domain.user.entity.SiteUser;
import com.example.ecommerce.global.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Entity
@Getter
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class Cart extends BaseEntity {


    @ManyToOne
    private SiteUser user;

    @ManyToOne
    // 왜 얘가 중복될 수 없다는 거지?
    private Product product;


}
