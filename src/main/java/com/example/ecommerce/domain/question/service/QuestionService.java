package com.example.ecommerce.domain.question.service;


import com.example.ecommerce.domain.question.QuestionCreateForm;
import com.example.ecommerce.domain.question.entity.Question;
import com.example.ecommerce.domain.question.repository.QuestionRepository;
import com.example.ecommerce.domain.user.entity.SiteUser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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

    public List<Question> findAll() {
        return this.questionRepository.findAll();
    }

    public Question findById(Long id) {
        Optional<Question> question = this.questionRepository.findById(id);
        if(question.isEmpty()) {
            return null;
        }
        return question.get();
    }
}
