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
        if(bindingResult.hasErrors()) {
            return "/user/signup";
        }
        if(this.userService.findByUsername(userCreateForm.getUsername())!=null) {
            bindingResult.rejectValue("username","usernameDoubling",
                    "이미 사용중인 ID에요! 다른 아이디를 생각해주세요.");
            return "/user/signup";
        }
        if(!userCreateForm.getPassword1().equals(userCreateForm.getPassword2())) {
            bindingResult.rejectValue("password2", "passwordInCorrect",
                    "2개의 패스워드가 일치하지 않아요! 다시 입력해주세요😅");
            return "/user/signup";
        }
        if(this.userService.findByNickname(userCreateForm.getNickname())!=null) {
            bindingResult.rejectValue("nickname","nicknameDoubling",
                    "이미 사용중인 닉네임입니다! 다른 닉네임을 사용해주세요.");
            return "/user/signup";
        }
        if(this.userService.findByEmail(userCreateForm.getEmail())!=null) {
            bindingResult.rejectValue("nickname","nicknameDoubling",
                    "이미 사용중인 이메일이네요! 다른 이메일을 입력해주세요.");
            return "/user/signup";
        }
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
