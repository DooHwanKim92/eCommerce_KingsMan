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

    private final ProductService productService;

    public Cart addProduct(SiteUser user, Product product) {
        Cart addCart = Cart.builder()
                .user(user)
                .product(product)
                .build();

        this.cartRepository.save(addCart);

        return addCart;
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

    public List<Product> getProductListByCartId(List<Long> cartId) {
        List<Cart> cartList = new ArrayList<>();
        for(int i = 0 ; i < cartId.size(); i++) {
            cartList.add(this.findById(cartId.get(i)));
        }

        List<Product> productList = new ArrayList<>();
        for(int i = 0; i < cartList.size(); i++) {
            productList.add(cartList.get(i).getProduct());
        }
        return productList;
    }

    public void removeList(List<Product> productList, SiteUser user) {
        List<Cart> cartList = user.getCartList();
        for(int i = 0; i < productList.size(); i ++) {
            for(int j = 0; j < cartList.size(); j++) {
                if(cartList.get(j).getProduct() == productList.get(i)) {
                    this.cartRepository.delete(cartList.get(j));
                }
            }
        }
    }

    public void removeByProduct(Product product, SiteUser user) {
        List<Cart> cartList = user.getCartList();
        for(int j = 0; j < cartList.size(); j++) {
            if(cartList.get(j).getProduct() == product) {
                this.cartRepository.delete(cartList.get(j));
            }
        }
    }
}
