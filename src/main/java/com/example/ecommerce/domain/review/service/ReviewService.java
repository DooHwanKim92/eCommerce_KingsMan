package com.example.ecommerce.domain.review.service;


import com.example.ecommerce.domain.product.entity.Product;
import com.example.ecommerce.domain.review.ReviewCreateForm;
import com.example.ecommerce.domain.review.entity.Review;
import com.example.ecommerce.domain.review.repository.ReviewRepository;
import com.example.ecommerce.domain.user.entity.SiteUser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;
    public Review create(SiteUser user, Product product, ReviewCreateForm reviewCreateForm) {
        Review review = Review.builder()
                .user(user)
                .product(product)
                .title(reviewCreateForm.getTitle())
                .content(reviewCreateForm.getContent())
                .score(reviewCreateForm.getScore())
                .build();

        this.reviewRepository.save(review);

        return review;
    }
}
