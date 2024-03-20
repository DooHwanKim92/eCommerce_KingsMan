package com.example.ecommerce.global.rebate.entity;


import com.example.ecommerce.domain.product.entity.Product;
import com.example.ecommerce.domain.user.entity.SiteUser;
import com.example.ecommerce.global.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class Rebate extends BaseEntity {

    // Seller -> 판매자
    @ManyToOne
    private SiteUser user;

    @OneToMany(mappedBy = "rebate")
    private List<Product> productList;

    private LocalDateTime startDate;

    private LocalDateTime endDate;

}
