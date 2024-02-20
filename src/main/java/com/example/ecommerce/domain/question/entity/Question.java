package com.example.ecommerce.domain.question.entity;


import com.example.ecommerce.domain.answer.entity.Answer;
import com.example.ecommerce.domain.user.entity.SiteUser;
import com.example.ecommerce.global.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
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
public class Question extends BaseEntity {

    @ManyToOne
    private SiteUser user;

    @Column
    private String productName;

    @Column
    private String category;

    @Column
    private String title;

    @Column
    private String content;

    @OneToOne(mappedBy = "question")
    private Answer answer;

    @Column
    private String isAnswered;

}
