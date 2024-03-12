package com.example.ecommerce.domain.orderdetail;


import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class OrderDetailCreateForm {

    List<Long> productId;

    @NotEmpty(message = "받는 사람의 이름을 입력해주세요.")
    private String name;

    @NotEmpty(message = "우편번호를 입력해주세요.")
    private String postnum;

    @NotEmpty(message = "도로명주소를 입력해주세요.")
    private String roadname;

    @NotEmpty(message = "지번주소를 입력해주세요.")
    private String groundname;

    @NotEmpty(message = "상세주소를 입력해주세요.")
    private String detailaddress;

    private String etc;

    @NotEmpty(message = "받는 사람의 전화번호를 입력해주세요.")
    private String phoneNumber;

    private String howToPay;

}
