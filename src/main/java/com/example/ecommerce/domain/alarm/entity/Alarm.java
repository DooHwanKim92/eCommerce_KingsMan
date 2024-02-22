package com.example.ecommerce.domain.alarm.entity;


import com.example.ecommerce.domain.user.entity.SiteUser;
import com.example.ecommerce.global.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@Getter
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class Alarm extends BaseEntity {

    @ManyToOne
    private SiteUser user;

    @Column
    private String title;

    @Column
    private String content;

    @Column
    private boolean isChecked;


}
