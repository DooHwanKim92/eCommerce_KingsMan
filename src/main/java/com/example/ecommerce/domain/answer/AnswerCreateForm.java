package com.example.ecommerce.domain.answer;


import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AnswerCreateForm {

    @NotEmpty(message = "답변을 입력해주세요.")
    private String content;


}
