package com.example.ecommerce.domain.orders;


import jakarta.persistence.Column;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class OrdersCreateForm {

    @NotEmpty(message = "옵션을 선택해주세요.")
    private List<String> option;

    @NotEmpty(message = "수량을 입력해주세요.")
    private String amount;

}
