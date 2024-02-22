package com.example.ecommerce.domain.product;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductCreateForm {

    private String category;

    private String name;

    private String content;

    private String discount;
}
