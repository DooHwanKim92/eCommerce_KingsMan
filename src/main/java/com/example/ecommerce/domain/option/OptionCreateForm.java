package com.example.ecommerce.domain.option;


import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OptionCreateForm {

    @NotEmpty(message = "옵션을 입력해주세요.")
    private String optionName;

}
