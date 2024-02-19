package com.example.ecommerce.domain.user.controller;


import com.example.ecommerce.domain.user.UserCreateForm;
import com.example.ecommerce.domain.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/signup")
    public String signup(UserCreateForm userCreateForm) {
        return "user/signup";
    }

    @PostMapping("/signup")
    public String signupPost(@Valid UserCreateForm userCreateForm, BindingResult bindingResult) {
        this.userService.signupCheckError(userCreateForm, bindingResult);
        this.userService.signup(userCreateForm);
        return "redirect:/";
    }

    @GetMapping("/login")
    public String login() {
        return "user/login";
    }

    @GetMapping("/logout")
    public String logout() {
        return "redirect:/";
    }

    @GetMapping("/profile")
    public String profile() {
        return "/user/profile";
    }

    @GetMapping("/modify")
    public String modify() {
        return "/user/modify";
    }



}
