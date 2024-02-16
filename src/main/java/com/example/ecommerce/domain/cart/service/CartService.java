package com.example.ecommerce.domain.cart.service;


import com.example.ecommerce.domain.cart.entity.Cart;
import com.example.ecommerce.domain.cart.repository.CartRepository;
import com.example.ecommerce.domain.product.entity.Product;
import com.example.ecommerce.domain.product.service.ProductService;
import com.example.ecommerce.domain.user.entity.SiteUser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CartService {

    private final CartRepository cartRepository;

    private final ProductService productService;

    public Cart addProduct(SiteUser user, Long productId) {
        Cart cart = Cart.builder()
                .user(user)
                .productId(productId)
                .build();

        this.cartRepository.save(cart);

        return cart;
    }

    public List<Product> findProductByCart(List<Cart> cartList) {
        List<Product> productList = new ArrayList<>();
        for(int i = 0; i < cartList.size(); i++) {
            Product product = this.productService.findById(cartList.get(i).getProductId());
            productList.add(product);
        }
        return productList;
    }
}
