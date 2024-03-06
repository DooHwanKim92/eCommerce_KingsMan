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
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CartService {

    private final CartRepository cartRepository;

    public Cart addProduct(SiteUser user, Product product) {
        Cart addCart = Cart.builder()
                .user(user)
                .product(product)
                .build();

        this.cartRepository.save(addCart);

        return addCart;
    }


    public List<Product> getProductListFindByUser(SiteUser user) {
        List<Product> productList = new ArrayList<>();
        for ( int i = 0; i < user.getCartList().size(); i++) {
            productList.add(user.getCartList().get(i).getProduct());
        }
        return productList;
    }

    public void remove(List<Long> id) {
        for(int i = 0; i < id.size(); i ++) {
            this.cartRepository.delete(this.findById(id.get(i)));
        }
    }

    public Cart findById(Long id) {
        Optional<Cart> cart = this.cartRepository.findById(id);
        if(cart.isEmpty()) {
            return null;
        }
        return cart.get();
    }

}
