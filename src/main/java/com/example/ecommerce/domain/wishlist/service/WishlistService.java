package com.example.ecommerce.domain.wishlist.service;

import com.example.ecommerce.domain.wishlist.repository.WishlistRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class WishlistService {


    private final WishlistRepository wishlistRepository;

}
