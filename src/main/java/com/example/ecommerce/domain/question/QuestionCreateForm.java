package com.example.ecommerce.domain.question;


import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class QuestionCreateForm {

    @NotEmpty
    private String category;

    @NotEmpty
    private String productName;

    @NotEmpty(message = "제목을 입력해주세요")
    private String title;

    @NotEmpty(message = "내용을 입력해주세요.")
    private String content;


}
