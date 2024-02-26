package com.example.ecommerce.domain.option.entity;


import com.example.ecommerce.domain.optiondetail.entity.OptionDetail;
import com.example.ecommerce.domain.product.entity.Product;
import com.example.ecommerce.global.BaseEntity;
import com.example.ecommerce.global.image.entity.Image;
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
public class Option extends BaseEntity {

    @ManyToOne
    private Product product;

    @Column
    // color, size ...
    private String name;

    @OneToMany(mappedBy = "option", cascade = CascadeType.REMOVE)
    private List<OptionDetail> optionDetailList;
}
