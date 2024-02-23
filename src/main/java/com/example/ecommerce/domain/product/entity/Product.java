package com.example.ecommerce.domain.product.entity;

import com.example.ecommerce.domain.cart.entity.Cart;
import com.example.ecommerce.domain.category.entity.Category;
import com.example.ecommerce.domain.option.entity.Option;
import com.example.ecommerce.domain.review.entity.Review;
import com.example.ecommerce.domain.user.entity.SiteUser;
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
public class Product extends BaseEntity {

    @ManyToOne
    private SiteUser user;

    @Column
    private String name;

    @Column
    private String content;

    @Column
    // 판매횟수
    private Integer purchasing;

    @Column
    // 할인율
    private String discount;

    @Column
    // 신상여부
    private boolean isNew;

    @Column
    // 대표이미지
    private String representImg;

    @ManyToOne
    private Category category;

    @OneToMany(mappedBy = "product", cascade = CascadeType.REMOVE)
    private List<Review> reviewList;

    @OneToMany(mappedBy = "product", cascade = CascadeType.REMOVE)
    private List<Option> optionList;

    @OneToMany(mappedBy = "product", cascade = CascadeType.REMOVE)
    private List<Image> imageList;

}
