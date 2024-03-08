package com.example.ecommerce.domain.review.entity;


import com.example.ecommerce.domain.product.entity.Product;
import com.example.ecommerce.domain.reply.entity.Reply;
import com.example.ecommerce.domain.user.entity.SiteUser;
import com.example.ecommerce.global.BaseEntity;
import com.example.ecommerce.global.image.entity.Image;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Entity
@Getter
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class Review extends BaseEntity {

    @ManyToOne
    private SiteUser user;

    @ManyToOne
    private Product product;

    @Column
    private String title;

    @Column
    private String content;

    @Column
    private Integer liker;

    @Column
    private Integer score;

    @OneToOne(mappedBy = "review",cascade = CascadeType.REMOVE)
    private Image reviewImg;

}
