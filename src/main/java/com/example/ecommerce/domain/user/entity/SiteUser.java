package com.example.ecommerce.domain.user.entity;


import com.example.ecommerce.domain.alarm.entity.Alarm;
import com.example.ecommerce.domain.cart.entity.Cart;
import com.example.ecommerce.domain.confirm.entity.Confirm;
import com.example.ecommerce.domain.orderdetail.entity.OrderDetail;
import com.example.ecommerce.domain.orders.entity.Orders;
import com.example.ecommerce.domain.product.entity.Product;
import com.example.ecommerce.domain.question.entity.Question;
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
public class SiteUser extends BaseEntity {

    @Column(unique = true)
    // 사용자 계정 ID
    private String username;

    @Column
    private String age;

    @Column
    private char sex;

    @Column(unique = true)
    // 웹에서 사용하는 사용자 닉네임
    private String nickname;

    @Column
    private String password;

    @Column(unique = true)
    private String email;

    @Column
    private String phoneNumber;

    @Column
    private List<String> address;

    @Column
    // user, seller, admin
    private String role;

    @Column
    // 포인트
    private Integer point;

    @Column
    // 회원 등급
    private String grade;

    @Column
    // 판매권한 여부 Y, N
    private char isSeller;

    @Column
    // 판매자 등록 시 사용할 판매자(사업자)명
    private String sellerName;

    @Column
    // 판매자(사업자)번호
    private String sellerNumber;

    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE)
    // 장바구니
    private List<Cart> cartList;

    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE)
    // 문의내역
    private List<Question> questionList;

    @OneToOne(mappedBy = "user")
    // 판매권한신청
    private Confirm confirm;

    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE)
    private List<Alarm> alarmList;

    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE)
    // 판매(등록)한 상품 리스트
    private List<Product> sellProductList;

    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE)
    // 선택한 상품 옵션
    private List<Orders> ordersList;

    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE)
    // 구매 내역
    private List<OrderDetail> orderDetailList;

}
