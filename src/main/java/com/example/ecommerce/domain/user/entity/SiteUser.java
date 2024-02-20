package com.example.ecommerce.domain.user.entity;


import com.example.ecommerce.domain.cart.entity.Cart;
import com.example.ecommerce.domain.question.entity.Question;
import com.example.ecommerce.global.BaseEntity;
import jakarta.persistence.CascadeType;
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
    private String address;

    @Column
    private String role;
    // user, seller, admin

    @Column
    private Integer point;

    @Column
    private String grade;

    @Column
    private char isSeller;

    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE)
    private List<Cart> cartList;

    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE)
    private List<Question> questionList;

}
