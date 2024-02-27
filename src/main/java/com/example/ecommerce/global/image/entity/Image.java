package com.example.ecommerce.global.image.entity;


import com.example.ecommerce.domain.option.entity.Option;
import com.example.ecommerce.domain.optiondetail.entity.OptionDetail;
import com.example.ecommerce.domain.product.entity.Product;
import com.example.ecommerce.global.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@Getter
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class Image extends BaseEntity {

    @Column
    // Entity 이름 = Product, Review
    private String typeName;

    @Column
    // Entity Id = product.id
    private Long typeId;

    @Column
    private String imgURL;

    @ManyToOne
    // 상품 이미지
    private Product product;

}
