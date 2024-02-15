package com.example.ecommerce.domain.user.entity;


import com.example.ecommerce.global.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@Getter
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class SiteUser extends BaseEntity {

    @Column(unique = true)
    // 사용자 이름
    private String username;

    @Column
    private Integer age;

    @Column
    private char sex;

    @Column
    // 사용자 id
    private String nickname;

    @Column
    private String password;

    @Column
    private String email;

    @Column
    private String phoneNumber;

    @Column
    private String address;

    @Column
    private String role;

    @Column
    private Integer point;

    @Column
    private String grade;

    @Column
    private char isSeller;

}
