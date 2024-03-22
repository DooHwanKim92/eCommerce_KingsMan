package com.example.ecommerce.domain.wishlist.service;

import com.example.ecommerce.domain.cart.entity.Cart;
import com.example.ecommerce.domain.product.entity.Product;
import com.example.ecommerce.domain.user.entity.SiteUser;
import com.example.ecommerce.domain.wishlist.entity.Wishlist;
import com.example.ecommerce.domain.wishlist.repository.WishlistRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

    public void removeList(List<Long> wishId, SiteUser user) {
        for(int i = 0 ; i < wishId.size(); i ++) {
            Wishlist wish = this.findById(wishId.get(i));
            this.wishlistRepository.delete(wish);
        }
    }

    private Wishlist findById(Long id) {
        Optional<Wishlist> wishlist = this.wishlistRepository.findById(id);
        if(wishlist.isEmpty()) {
            return null;
        }
        return wishlist.get();
    }

    public List<Wishlist> getListByIdList(List<Long> wishId) {
        List<Wishlist> wishList = new ArrayList<>();
        for ( int i = 0 ; i < wishId.size(); i++) {
            wishList.add(this.findById(wishId.get(i)));
        }
        return wishList;
    }

    public void removeByProduct(SiteUser user, Product product) {
        for (int i = 0 ; i < user.getWishList().size(); i ++) {
            if(product == user.getWishList().get(i).getProduct()) {
                this.wishlistRepository.delete(user.getWishList().get(i));
            }
        }
    }
}
