package com.example.ecommerce.domain.wishlist;


import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class WishSelectForm {

    @NotNull(message = "상품을 선택해주세요!")
    List<Long> wishId;

}
