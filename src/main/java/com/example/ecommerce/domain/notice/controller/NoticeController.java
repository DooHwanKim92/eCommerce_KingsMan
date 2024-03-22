package com.example.ecommerce.domain.notice.controller;


import com.example.ecommerce.domain.notice.NoticeCreateForm;
import com.example.ecommerce.domain.notice.entity.Notice;
import com.example.ecommerce.domain.notice.service.NoticeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.hibernate.engine.jdbc.mutation.spi.BindingGroup;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
        if(noticeCreateForm.getCategory().equals("유형선택")) {
            bindingResult.rejectValue("category", "categoryInCorrect",
                    "카테고리를 선택해주세요 😅");
            return "/notice/create";
        }

        this.noticeService.create(noticeCreateForm);

        return "redirect:/notice/list";
    }

    @GetMapping("/detail/{id}")
    public String noticeDetail(Model model, @PathVariable(value = "id") Long id) {
        Notice notice = this.noticeService.findById(id);
        model.addAttribute("notice",notice);
        return "/notice/detail";
    }

    @GetMapping("/modify/{id}")
    public String noticeModify(NoticeCreateForm noticeCreateForm, Model model, @PathVariable(value = "id") Long id) {
        Notice notice = this.noticeService.findById(id);
        model.addAttribute("notice",notice);
        return "/notice/modify";
    }

    @PostMapping("/modify/{id}")
    public String noticeModifyPost(@PathVariable(value = "id") Long id ,@Valid NoticeCreateForm noticeCreateForm, BindingResult bindingResult) {
        if(noticeCreateForm.getCategory().equals("유형선택")) {
            bindingResult.rejectValue("category", "categoryInCorrect",
                    "카테고리를 선택해주세요 😅");
            return "/notice/modify";
        }
        if(bindingResult.hasErrors()) {
            return "/notice/modify";
        }
        Notice notice = this.noticeService.findById(id);
        this.noticeService.modify(noticeCreateForm, notice);

        return String.format("redirect:/notice/detail/%d",id);
    }

    @GetMapping("/remove/{id}")
    public String noticeRemove(@PathVariable(value = "id") Long id) {
        this.noticeService.remove(id);
        return "redirect:/notice/list";
    }


}
