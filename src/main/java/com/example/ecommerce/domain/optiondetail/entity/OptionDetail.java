package com.example.ecommerce.domain.optiondetail.entity;


import com.example.ecommerce.domain.option.entity.Option;
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
public class OptionDetail extends BaseEntity {

    @ManyToOne
    private Option option;

    @Column
    // option이 color라면 red,blue,green ...
    // option이 size라면 95,100,105 ...
    private String detail;

    @Column
    // 상품 가격
    private Integer price;

    @Column
    // 상품 수량
    private String amount;


}
