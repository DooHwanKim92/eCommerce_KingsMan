package com.example.ecommerce.domain.orders;


import jakarta.persistence.Column;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrdersCreateForm {

    @NotEmpty(message = "옵션1을 선택해주세요.")
    private String option1;

    @NotEmpty(message = "옵션2를 선택해주세요.")
    private String option2;

    @NotEmpty(message = "수량을 입력해주세요.")
    private String amount;

}
