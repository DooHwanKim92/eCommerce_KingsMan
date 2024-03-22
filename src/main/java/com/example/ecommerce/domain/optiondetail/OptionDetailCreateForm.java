package com.example.ecommerce.domain.optiondetail;


import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OptionDetailCreateForm {


    @NotEmpty(message = "상세옵션을 입력해주세요.")
    private String detail;

    private String price;

    private String amount;



}
