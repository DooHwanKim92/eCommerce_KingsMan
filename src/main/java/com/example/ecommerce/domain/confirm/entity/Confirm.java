package com.example.ecommerce.domain.confirm.entity;

import com.example.ecommerce.domain.user.entity.SiteUser;
import com.example.ecommerce.global.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@Getter
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class Confirm extends BaseEntity {

    @OneToOne
    private SiteUser user;

    @Column(unique = true)
    private String sellerName;

    @Column(unique = true)
    private String sellerNumber;

    @Column
    private String isConfirmed;

}
