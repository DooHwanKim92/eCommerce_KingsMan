package com.example.ecommerce.domain.user;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserCreateForm {

    private String username;

    private String password1;

    private String password2;

    private String nickname;

    private char sex;

    private String age;

    private String email;

    private String phoneNumber;

    private String address;

}
