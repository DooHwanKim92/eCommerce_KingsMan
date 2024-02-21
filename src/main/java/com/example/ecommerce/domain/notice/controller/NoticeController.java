package com.example.ecommerce.domain.notice.controller;


import com.example.ecommerce.domain.notice.NoticeCreateForm;
import com.example.ecommerce.domain.notice.entity.Notice;
import com.example.ecommerce.domain.notice.service.NoticeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/notice")
@RequiredArgsConstructor
public class NoticeController {


    private final NoticeService noticeService;

    @GetMapping("/list")
    public String noticeList(Model model) {
        List<Notice> noticeList = this.noticeService.findAll();
        model.addAttribute("noticeList",noticeList);
        return "/notice/list";
    }

    @GetMapping("/create")
    public String createNotice(NoticeCreateForm noticeCreateForm) {
        return "/notice/create";
    }

    @PostMapping("/create")
    public String createNoticePost(@Valid NoticeCreateForm noticeCreateForm, BindingResult bindingResult) {

        if(bindingResult.hasErrors()) {
            return "/notice/create";
        }

        this.noticeService.create(noticeCreateForm);

        return "redirect:/notice/list";
    }
}
