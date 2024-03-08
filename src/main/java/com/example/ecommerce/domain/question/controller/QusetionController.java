package com.example.ecommerce.domain.question.controller;


import com.example.ecommerce.domain.product.entity.Product;
import com.example.ecommerce.domain.product.service.ProductService;
import com.example.ecommerce.domain.question.QuestionCreateForm;
import com.example.ecommerce.domain.question.entity.Question;
import com.example.ecommerce.domain.question.service.QuestionService;
import com.example.ecommerce.domain.user.entity.SiteUser;
import com.example.ecommerce.domain.user.service.UserService;
import com.example.ecommerce.global.request.Request;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.Banner;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/question")
public class QusetionController {

    private final QuestionService questionService;

    private final UserService userService;

    private final ProductService productService;

    @GetMapping("/list")
    public String list() {
        return "/question/list";
    }

    @GetMapping("/create")
    public String create(QuestionCreateForm questionCreateForm) {
        return "/question/create";
    }

    @PostMapping("/create")
    public String createPost(@Valid QuestionCreateForm questionCreateForm, BindingResult bindingResult, Principal principal) {
        SiteUser user = this.userService.findByUsername(principal.getName());
        if(bindingResult.hasErrors()) {
            return "/question/create";
        }
        this.questionService.create(questionCreateForm, user);
        return "/question/list";
    }

    @GetMapping("/mylist")
    public String myQuestionList(Model model, Principal principal) {
        if (this.userService.findByUsername(principal.getName()).getRole().equals("admin")) {
            List<Question> questionList = this.questionService.findAll();
            model.addAttribute("questionList",questionList);
        } else {
            List<Question> questionList = this.userService.findByUsername(principal.getName()).getQuestionList();
            model.addAttribute("questionList",questionList);
        }
        return "/question/mylist";
    }

    @GetMapping("/detail/{id}")
    public String questionDetail(Model model, @PathVariable(value = "id")Long id) {
        Question question = this.questionService.findById(id);
        model.addAttribute("question",question);
        return "/question/detail";
    }

    @GetMapping("/create/product/{id}")
    public String createProductQuestion(QuestionCreateForm questionCreateForm, @PathVariable(value = "id") Long id, Model model, Principal principal) {
        Product product = this.productService.findById(id);
        SiteUser user = this.userService.findByUsername(principal.getName());
        boolean isPurchase = false;
        for(int i = 0; i <user.getOrderDetailList().size(); i++) {
            if(user.getOrderDetailList().get(i).getProduct() == product) {
                isPurchase = user.getOrderDetailList().get(i).isPurchase();
            }
        }
        if(!isPurchase) {
            return String.format("redirect:/product/detail/%d",id);
        }
        model.addAttribute("product", product);
        return "/question/create_product";
    }

    @PostMapping("/create/product/{id}")
    public String createProductQuestionPost(@Valid QuestionCreateForm questionCreateForm, BindingResult bindingResult, Principal principal, @PathVariable(value = "id") Long id, Model model) {
        SiteUser user = this.userService.findByUsername(principal.getName());
        Product product = this.productService.findById(id);
        if(bindingResult.hasErrors()) {
            model.addAttribute("product", product);
            return "/question/create_product";
        }
        this.questionService.createProductQuestion(questionCreateForm, user, product);
        return String.format("redirect:/product/detail/%d",id);
    }

}
