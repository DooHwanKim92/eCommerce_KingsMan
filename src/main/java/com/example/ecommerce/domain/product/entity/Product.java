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
    // 가격
    private String price;

    @Column
    // 할인율
    private String discount;

    @Column
    // 신상여부
    // 일정 기간이 지나면 자동으로 바뀌도록 구현필요
    private boolean isNew;

    @OneToMany(mappedBy = "product", cascade = CascadeType.REMOVE)
    private List<Cart> cartList;

    @OneToOne
    // 대표이미지 thumnail
    private Image representImg;

    @ManyToOne
    private Category category;

    @OneToMany(mappedBy = "product", cascade = CascadeType.REMOVE)
    private List<Review> reviewList;

    @OneToMany(mappedBy = "product", cascade = CascadeType.REMOVE)
    private List<Option> optionList;

    @OneToMany(mappedBy = "product", cascade = CascadeType.REMOVE)
    // 상품 이미지들
    private List<Image> imageList;

}
