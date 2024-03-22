package com.example.ecommerce.domain.answer.entity;


import com.example.ecommerce.domain.question.entity.Question;
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
public class Answer extends BaseEntity {

    @Column
    private String content;

    @OneToOne
    private Question question;
}