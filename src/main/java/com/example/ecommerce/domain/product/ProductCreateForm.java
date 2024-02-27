package com.example.ecommerce.domain.product;


import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductCreateForm {

    @NotEmpty
    private String category;

    @NotEmpty(message = "상품 이름을 입력해주세요.")
    private String name;

    @NotEmpty(message = "상품 설명을 입력해주세요.")
    private String content;

    @NotEmpty(message = "상품 가격을 입력해주세요.")
    private String price;

    @NotEmpty(message = "할인율을 입력해주세요 '0~100'")
    private String discount;
}
