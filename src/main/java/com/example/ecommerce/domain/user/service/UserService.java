package com.example.ecommerce.domain.user.service;


import com.example.ecommerce.domain.alarm.entity.Alarm;
import com.example.ecommerce.domain.cart.entity.Cart;
import com.example.ecommerce.domain.confirm.entity.Confirm;
import com.example.ecommerce.domain.product.entity.Product;
import com.example.ecommerce.domain.user.UserCreateForm;
import com.example.ecommerce.domain.user.UserModifyForm;
import com.example.ecommerce.domain.user.entity.SiteUser;
import com.example.ecommerce.domain.user.repository.UserRepository;
import com.example.ecommerce.domain.wishlist.entity.Wishlist;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    public SiteUser findByUsername(String username) {
        Optional<SiteUser> user = this.userRepository.findByUsername(username);
        if (user.isEmpty()) {
            return null;
        }
        return user.get();
    }

    public void signup(UserCreateForm userCreateForm) {
        List<Cart> cartList = new ArrayList<>();
        List<Alarm> alarmList = new ArrayList<>();
        SiteUser user = SiteUser.builder()
                .username(userCreateForm.getUsername())
                .password(passwordEncoder.encode(userCreateForm.getPassword2()))
                .nickname(userCreateForm.getNickname())
                .sex(userCreateForm.getSex())
                .age(userCreateForm.getAge())
                .email(userCreateForm.getEmail())
                .phoneNumber(userCreateForm.getPhoneNumber())
                .address(userCreateForm.getAddress())
                .role("user")
                .point(0)
                .grade("브론즈")
                .isSeller('N')
                .cartList(cartList)
                .alarmList(alarmList)
                .build();

        this.userRepository.save(user);

        if(user.getUsername().equals("admin")) {
            SiteUser admin = user.toBuilder()
                    .role("admin")
                    .grade("다이아몬드")
                    .build();

            this.userRepository.save(admin);
        }
    }

    public void addCart(SiteUser loginedUser, Cart cart) {
        List<Cart> userCartList = loginedUser.getCartList();
        userCartList.add(cart);
        SiteUser user = loginedUser.toBuilder()
                .cartList(userCartList)
                .build();

        this.userRepository.save(user);
    }

    public SiteUser findByNickname(String nickname) {
        Optional<SiteUser> user = this.userRepository.findByNickname(nickname);
        if (user.isEmpty()) {
            return null;
        }
        return user.get();
    }

    public SiteUser findByEmail(String email) {
        Optional<SiteUser> user = this.userRepository.findByEmail(email);
        if (user.isEmpty()) {
            return null;
        }
        return user.get();
    }

    public void modify(UserModifyForm userModifyForm, SiteUser modifyUser) {
        SiteUser user = modifyUser.toBuilder()
                .password(passwordEncoder.encode(userModifyForm.getPassword2()))
                .nickname(userModifyForm.getNickname())
                .email(userModifyForm.getEmail())
                .phoneNumber(userModifyForm.getPhoneNumber())
                .address(userModifyForm.getAddress())
                .build();

        this.userRepository.save(user);
    }

    public void acceptSalesConfirm(SiteUser user, Alarm alarm, Confirm confirm) {
        List<Alarm> alarmList = user.getAlarmList();
        alarmList.add(alarm);
        SiteUser acceptUser = user.toBuilder()
                .role("seller")
                .isSeller('Y')
                .sellerName(confirm.getSellerName())
                .sellerNumber(confirm.getSellerNumber())
                .bank(confirm.getBank())
                .bankAccount(confirm.getBankAccount())
                .alarmList(alarmList)
                .build();

        this.userRepository.save(acceptUser);
    }

    public void denySalesConfirm(SiteUser user, Alarm alarm) {
        List<Alarm> alarmList = user.getAlarmList();
        alarmList.add(alarm);
        SiteUser denyUser = user.toBuilder()
                .alarmList(alarmList)
                .build();

        this.userRepository.save(denyUser);
    }

    public void createSellProduct(SiteUser user, Product product) {
        List<Product> sellProductList = user.getSellProductList();
        sellProductList.add(product);
        SiteUser sellUser = user.toBuilder()
                .sellProductList(sellProductList)
                .build();

        this.userRepository.save(sellUser);
    }

    public SiteUser findById(Long id) {
        Optional<SiteUser> user = this.userRepository.findById(id);
        if(user.isEmpty()) {
            return null;
        }
        return user.get();
    }

    public List<SiteUser> findAll() {
        return this.userRepository.findAll();
    }

    public void addWishList(SiteUser user, Wishlist wishlist) {
        List<Wishlist> wishlists = user.getWishList();
        wishlists.add(wishlist);
        SiteUser addWishUser = user.toBuilder()
                .wishList(wishlists)
                .build();

        this.userRepository.save(addWishUser);
    }

    public void addPoint(SiteUser user, Integer savingPoint) {

        int point = user.getPoint() + savingPoint;

        SiteUser addPointUser = user.toBuilder()
                .point(point)
                .build();

        this.userRepository.save(addPointUser);
    }

    public void usePoint(SiteUser user, int usedPoint) {
        SiteUser usePointUser = user.toBuilder()
                .point(user.getPoint() - usedPoint)
                .build();

        this.userRepository.save(usePointUser);
    }
}
