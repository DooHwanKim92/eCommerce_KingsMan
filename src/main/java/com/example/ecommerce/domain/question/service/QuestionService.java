package com.example.ecommerce.domain.question.service;


import com.example.ecommerce.domain.question.QuestionCreateForm;
import com.example.ecommerce.domain.question.entity.Question;
import com.example.ecommerce.domain.question.repository.QuestionRepository;
import com.example.ecommerce.domain.user.entity.SiteUser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class QuestionService {

    private final QuestionRepository questionRepository;
    public void create(QuestionCreateForm questionCreateForm, SiteUser user) {
        Question question = Question.builder()
                .user(user)
                .category(questionCreateForm.getCategory())
                .productName(questionCreateForm.getProductName())
                .title(questionCreateForm.getTitle())
                .content(questionCreateForm.getContent())
                .isAnswered("N")
                .build();

        this.questionRepository.save(question);
    }
}
