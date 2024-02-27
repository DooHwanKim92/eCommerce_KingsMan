package com.example.ecommerce.domain.answer.service;


import com.example.ecommerce.domain.answer.AnswerCreateForm;
import com.example.ecommerce.domain.answer.entity.Answer;
import com.example.ecommerce.domain.answer.repository.AnswerRepository;
import com.example.ecommerce.domain.question.entity.Question;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AnswerService {

    private final AnswerRepository answerRepository;

    public Answer create(AnswerCreateForm answerCreateForm, Question question) {
        Answer answer = Answer.builder()
                .content(answerCreateForm.getContent())
                .question(question)
                .build();

        this.answerRepository.save(answer);
        return answer;
    }
}
