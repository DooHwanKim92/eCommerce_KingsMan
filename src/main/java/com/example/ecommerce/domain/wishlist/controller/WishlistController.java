package com.example.ecommerce.domain.wishlist.controller;


import com.example.ecommerce.domain.cart.entity.Cart;
import com.example.ecommerce.domain.cart.service.CartService;
import com.example.ecommerce.domain.orders.service.OrdersService;
import com.example.ecommerce.domain.product.entity.Product;
import com.example.ecommerce.domain.product.service.ProductService;
import com.example.ecommerce.domain.user.entity.SiteUser;
import com.example.ecommerce.domain.user.service.UserService;
import com.example.ecommerce.domain.wishlist.WishSelectForm;
import com.example.ecommerce.domain.wishlist.entity.Wishlist;
import com.example.ecommerce.domain.wishlist.service.WishlistService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/wish")
@RequiredArgsConstructor
public class WishlistController {


    private final WishlistService wishlistService;

    private final UserService userService;

    private final ProductService productService;

    private final OrdersService ordersService;

    private final CartService cartService;

    @GetMapping("/add/{id}")
    public String addWish(Model model, @PathVariable(value = "id") Long id, Principal principal) {
        SiteUser user = this.userService.findByUsername(principal.getName());
        Product product = this.productService.findById(id);

        String isWish = "";
        isWish = this.wishlistService.isWish(user, product);

        model.addAttribute("isWish",isWish);

        if(this.ordersService.findByUserandProduct(user,product).isEmpty()) {
            return String.format("redirect:/product/detail/%d",id);
        }

        this.userService.addWishList(user, this.wishlistService.addProduct(user, product));

        return String.format("redirect:/product/detail/%d",id);
    }

    @GetMapping("/list")
    public String wishList(Model model, Principal principal, WishSelectForm wishSelectForm) {
        SiteUser user = this.userService.findByUsername(principal.getName());
        List<Wishlist> wishList = user.getWishList();

        model.addAttribute("wishList",wishList);

        return "/wishlist/list";
    }

    // list에서 여러 상품 찜목록에서 삭제
    @PostMapping("/remove")
    public String removeWish(Model model, Principal principal, @Valid WishSelectForm wishSelectForm, BindingResult bindingResult) {
        SiteUser user = this.userService.findByUsername(principal.getName());
        List<Wishlist> wishList = user.getWishList();
        model.addAttribute("wishList",wishList);

        if (wishSelectForm.getWishId() == null) {
            bindingResult.reject("wishIdisEmpty","상품을 선택해주세요.");
            return "/wishlist/list";
        }

        this.wishlistService.removeList(wishSelectForm.getWishId(), user);

        return "/wishlist/list";
    }

    // 상품 단건 찜목록 해제
    @GetMapping("/remove/{id}")
    public String removeOne(Model model, @PathVariable(value = "id") Long id, Principal principal) {
        SiteUser user = this.userService.findByUsername(principal.getName());
        Product product = this.productService.findById(id);

        this.wishlistService.removeByProduct(user, product);

        return String.format("redirect:/product/detail/%d",id);
    }

    @PostMapping("/move")
    public String moveToCart(Model model, Principal principal, @Valid WishSelectForm wishSelectForm, BindingResult bindingResult) {
        SiteUser user = this.userService.findByUsername(principal.getName());
        List<Wishlist> wishList = user.getWishList();
        model.addAttribute("wishList",wishList);

        if (wishSelectForm.getWishId() == null) {
            bindingResult.reject("wishIdisEmpty","상품을 선택해주세요.");
            return "/wishlist/list";
        }

        List<Wishlist> selectedWishList = this.wishlistService.getListByIdList(wishSelectForm.getWishId());

        this.cartService.wishMoveToCart(selectedWishList, user);
        this.wishlistService.removeList(wishSelectForm.getWishId(), user);

        return "/wishlist/list";
    }





}
