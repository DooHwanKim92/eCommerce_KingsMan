package com.example.ecommerce.domain.user;


import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class UserCreateForm {
    @Size(min = 4, max = 12, message = "아이디는 4~12자까지 사용할 수 있어요 😅")
    @NotEmpty(message = "아이디를 입력해주세요.")
    private String username;

    @NotEmpty(message = "비밀번호를 입력해주세요.")
    private String password1;

    @NotEmpty(message = "비밀번호 확인을 입력해주세요.")
    private String password2;

    @NotEmpty(message = "닉네임을 입력해주세요.")
    private String nickname;

    @NotNull(message = "성별을 선택해주세요.")
    private char sex;

    @Size(min = 1, max = 2, message = "나이는 0 ~ 99살까지 입력할 수 있어요 😅")
    @NotEmpty(message = "나이를 입력해주세요.")
    private String age;

    @NotEmpty(message = "이메일을 입력해주세요.")
    @Email(message = "올바른 이메일 형식이 아니에요 😅")
    private String email;

    @NotEmpty(message = "전화번호를 입력해주세요.")
    @Pattern(regexp = "^01([0|1|6|7|8|9])-?([0-9]{3,4})-?([0-9]{4})$", message = "올바른 전화번호 형식이 아니에요 😅")
    private String phoneNumber;

    @NotEmpty(message = "주소를 입력해주세요.")
    private List<String> address;

}
