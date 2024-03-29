package com.example.ecommerce.domain.product.entity;

import com.example.ecommerce.domain.cart.entity.Cart;
import com.example.ecommerce.domain.category.entity.Category;
import com.example.ecommerce.domain.option.entity.Option;
import com.example.ecommerce.domain.orderdetail.entity.OrderDetail;
import com.example.ecommerce.domain.orders.entity.Orders;
import com.example.ecommerce.domain.question.entity.Question;
import com.example.ecommerce.domain.review.entity.Review;
import com.example.ecommerce.domain.user.entity.SiteUser;
import com.example.ecommerce.domain.wishlist.entity.Wishlist;
import com.example.ecommerce.global.BaseEntity;
import com.example.ecommerce.global.image.entity.Image;
import com.example.ecommerce.global.rebate.entity.Rebate;
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
    // 판매자
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
    // 할인 시 할인된 금액
    private Integer DCPrice;


    private List<String> tag;


    @Column
    // 평점
    private float score;


    @Column
    // 판매금액
    private Integer income;

    @Column
    // 신상여부
    // 일정 기간이 지나면 자동으로 바뀌도록 구현필요
    private boolean isNew;

    @OneToMany(mappedBy = "product",cascade = CascadeType.REMOVE)
    private List<Cart> cartList;

    @OneToMany(mappedBy = "product",cascade = CascadeType.REMOVE)
    private List<Wishlist> wishList;

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

    @OneToMany(mappedBy = "product", cascade = CascadeType.REMOVE)
    private List<Orders> ordersList;

    @OneToMany(mappedBy = "product", cascade = CascadeType.REMOVE)
    private List<OrderDetail> orderDetailList;

    @OneToMany(mappedBy = "product", cascade = CascadeType.REMOVE)
    private List<Question> questionList;

    @ManyToOne
    private Rebate rebate;

}
