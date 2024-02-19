package com.example.ecommerce.domain.category.entity;


import com.example.ecommerce.domain.product.entity.Product;
import com.example.ecommerce.global.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
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
public class Category extends BaseEntity {

    @Column(unique = true)
    private String name;

    @OneToMany(mappedBy = "category")
    private List<Product> productList;

}
