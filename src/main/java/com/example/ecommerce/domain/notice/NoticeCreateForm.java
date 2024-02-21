package com.example.ecommerce.domain.notice;


import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NoticeCreateForm {


    private String category;

    @NotEmpty(message = "제목을 입력해야합니다.")
    private String title;

    @NotEmpty(message = "내용을 입력해야합니다.")
    private String content;

}
