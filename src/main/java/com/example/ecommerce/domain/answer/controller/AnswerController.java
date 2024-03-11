package com.example.ecommerce.domain.answer.controller;


import com.example.ecommerce.domain.alarm.service.AlarmService;
import com.example.ecommerce.domain.answer.AnswerCreateForm;
import com.example.ecommerce.domain.answer.entity.Answer;
import com.example.ecommerce.domain.answer.service.AnswerService;
import com.example.ecommerce.domain.question.entity.Question;
import com.example.ecommerce.domain.question.service.QuestionService;
import com.example.ecommerce.domain.user.entity.SiteUser;
import com.example.ecommerce.domain.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/answer")
public class AnswerController {

    private final QuestionService questionService;

    private final AnswerService answerService;

    private final AlarmService alarmService;


    @PostMapping("/create/{id}")
    public String createAnswer(@Valid AnswerCreateForm answerCreateForm, BindingResult bindingResult, @PathVariable(value = "id") Long id) {
        Question question = this.questionService.findById(id);
        if (bindingResult.hasErrors()) {
            return "/question/detail";
        }
        Answer answer = this.answerService.create(answerCreateForm, question);
        this.questionService.isAnswered(question);
        this.alarmService.questionAnswered(answer,question.getUser());
        return String.format("redirect:/question/detail/%d",id);
    }

    @GetMapping("/product/{id}")
    public String getProductQuestion(AnswerCreateForm answerCreateForm, @PathVariable(value = "id") Long id, Model model) {
        Question question = this.questionService.findById(id);
        model.addAttribute("question",question);
        return "/question/detail_product";
    }

    @PostMapping("/create/product/{id}")
    public String createProductAnswer(Model model,@Valid AnswerCreateForm answerCreateForm, BindingResult bindingResult, @PathVariable(value = "id") Long id) {
        Question question = this.questionService.findById(id);
        if (bindingResult.hasErrors()) {
            model.addAttribute("question",question);
            return "/question/detail_product";
        }
        Answer answer = this.answerService.create(answerCreateForm, question);
        this.questionService.isAnswered(question);
        this.alarmService.questionAnswered(answer,question.getUser());
        return String.format("redirect:/question/detail_product/%d",id);
    }

}
