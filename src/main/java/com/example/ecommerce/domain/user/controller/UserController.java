package com.example.ecommerce.domain.user.controller;


import com.example.ecommerce.domain.confirm.ConfirmForm;
import com.example.ecommerce.domain.confirm.entity.Confirm;
import com.example.ecommerce.domain.confirm.service.ConfirmService;
import com.example.ecommerce.domain.user.UserCreateForm;
import com.example.ecommerce.domain.user.UserModifyForm;
import com.example.ecommerce.domain.user.entity.SiteUser;
import com.example.ecommerce.domain.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

@Controller
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    private final ConfirmService confirmService;

    @GetMapping("/signup")
    public String signup(UserCreateForm userCreateForm) {
        return "user/signup";
    }

    @PostMapping("/signup")
    public String signupPost(@Valid UserCreateForm userCreateForm, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "/user/signup";
        }
        if (this.userService.findByUsername(userCreateForm.getUsername()) != null) {
            bindingResult.rejectValue("username", "usernameDoubling",
                    "이미 사용중인 ID에요! 다른 아이디를 생각해주세요.");
            return "/user/signup";
        }
        if (!userCreateForm.getPassword1().equals(userCreateForm.getPassword2())) {
            bindingResult.rejectValue("password2", "passwordInCorrect",
                    "2개의 패스워드가 일치하지 않아요! 다시 입력해주세요😅");
            return "/user/signup";
        }
        if (this.userService.findByNickname(userCreateForm.getNickname()) != null) {
            bindingResult.rejectValue("nickname", "nicknameDoubling",
                    "이미 사용중인 닉네임입니다! 다른 닉네임을 사용해주세요.");
            return "/user/signup";
        }
        if (this.userService.findByEmail(userCreateForm.getEmail()) != null) {
            bindingResult.rejectValue("nickname", "nicknameDoubling",
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
    public String modify(UserModifyForm userModifyForm) {
        return "/user/modify";
    }

    @PostMapping("/modify")
    public String modifyPost(@Valid UserModifyForm userModifyForm, BindingResult bindingResult, Principal principal) {
        if (bindingResult.hasErrors()) {
            return "/user/modify";
        }
        if (!userModifyForm.getPassword1().equals(userModifyForm.getPassword2())) {
            bindingResult.rejectValue("password2", "passwordInCorrect",
                    "2개의 패스워드가 일치하지 않아요! 다시 입력해주세요😅");
            return "/user/modify";
        }
        if (this.userService.findByNickname(userModifyForm.getNickname()) != null) {
            bindingResult.rejectValue("nickname", "nicknameDoubling",
                    "이미 사용중인 닉네임입니다! 다른 닉네임을 사용해주세요.");
            return "/user/modify";
        }
        if (this.userService.findByEmail(userModifyForm.getEmail()) != null) {
            bindingResult.rejectValue("nickname", "nicknameDoubling",
                    "이미 사용중인 이메일이네요! 다른 이메일을 입력해주세요.");
            return "/user/modify";
        }
        SiteUser modifyUser = userService.findByUsername(principal.getName());
        this.userService.modify(userModifyForm, modifyUser);
        return "/logout";
    }

    @GetMapping("/point")
    public String userPoint() {
        return "/user/point";
    }

    @GetMapping("/buylist")
    public String userBuyList() {
        return "/user/buylist";
    }

    @GetMapping("/confirm")
    public String salesConfirm(Model model, ConfirmForm confirmForm, Principal principal) {
        Confirm confirm = this.userService.findByUsername(principal.getName()).getConfirm();
        model.addAttribute("confirm",confirm);
        return "/user/confirm";
    }

    @PostMapping("/confirm")
    public String salesConfirmPost(@Valid ConfirmForm confirmForm, BindingResult bindingResult, Principal principal) {
        if (bindingResult.hasErrors()) {
            return "/user/confirm";
        }
        if (this.confirmService.findBySellerName(confirmForm.getSellerName()) != null ) {
            bindingResult.rejectValue("sellername", "nameDoubling",
                    "이미 등록된 사업자명입니다.");
            return "/user/confirm";
        }
        if (this.confirmService.findBySellerNumber(confirmForm.getSellerNumber()) != null ) {
            bindingResult.rejectValue("sellernumber", "numberDoubling",
                    "이미 등록된 사업자번호입니다.");
            return "/user/confirm";
        }
        SiteUser user = this.userService.findByUsername(principal.getName());
        this.confirmService.create(confirmForm, user);
        return "redirect:/user/confirm";
    }

}
