package com.example.ecommerce.domain.review.entity;


import com.example.ecommerce.global.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@Getter
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class Review extends BaseEntity {

    @Column
    private Long userId;

    @Column
    private Long productId;

    @Column
    private String title;

    @Column
    private String content;

    @Column
    private Integer liker;

    @Column
    private Integer score;

    @Column
    private Long replyId;
}
