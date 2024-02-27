package com.example.ecommerce.domain.confirm;


import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ConfirmForm {

    @NotEmpty(message = "사업자명을 입력해주세요.")
    private String sellerName;

    @NotEmpty(message = "사업자 등록번호를 입력해주세요.")
    private String sellerNumber;

}
