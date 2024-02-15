package com.example.ecommerce.domain.question.entity;


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
public class Question extends BaseEntity {

    @Column
    private Long userId;

    @Column
    private Long productId;

    @Column
    private String category;

    @Column
    private String title;

    @Column
    private String content;

    @Column
    private Long answerId;
}
