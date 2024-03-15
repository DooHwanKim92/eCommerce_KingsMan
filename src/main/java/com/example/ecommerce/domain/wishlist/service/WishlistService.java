package com.example.ecommerce.domain.wishlist.service;

import com.example.ecommerce.domain.cart.entity.Cart;
import com.example.ecommerce.domain.product.entity.Product;
import com.example.ecommerce.domain.user.entity.SiteUser;
import com.example.ecommerce.domain.wishlist.entity.Wishlist;
import com.example.ecommerce.domain.wishlist.repository.WishlistRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class WishlistService {


    private final WishlistRepository wishlistRepository;

    public Wishlist addProduct(SiteUser user, Product product) {
        Wishlist addWish = Wishlist.builder()
                .user(user)
                .product(product)
                .build();

        this.wishlistRepository.save(addWish);

        return addWish;
    }

    public String isWish(SiteUser user, Product product) {
        List<Wishlist> wishList = user.getWishList();
        if(wishList==null) {
            return "false";
        }
        for(int i = 0 ; i < wishList.size(); i++) {
            if(wishList.get(i).getProduct() == product) {
                return "true";
            }
        }
        return "false";
    }
}
