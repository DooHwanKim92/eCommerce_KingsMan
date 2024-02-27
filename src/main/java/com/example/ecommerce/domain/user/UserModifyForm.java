package com.example.ecommerce.domain.user;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserModifyForm {

    @NotEmpty(message = "변경하실 비밀번호를 입력해주세요.")
    private String password1;

    @NotEmpty(message = "비밀번호 확인을 입력해주세요.")
    private String password2;

    @NotEmpty(message = "변경하실 닉네임을 입력해주세요.")
    private String nickname;

    @NotEmpty(message = "변경하실 이메일을 입력해주세요.")
    @Email(message = "올바른 이메일 형식이 아니에요 😅")
    private String email;

    @NotEmpty(message = "변경하실 전화번호를 입력해주세요.")
    @Pattern(regexp = "^01([0|1|6|7|8|9])-?([0-9]{3,4})-?([0-9]{4})$", message = "올바른 전화번호 형식이 아니에요 😅")
    private String phoneNumber;

    @NotEmpty(message = "변경하실 주소를 입력해주세요.")
    private String address;

}
