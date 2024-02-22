package com.example.ecommerce.domain.alarm.controller;


import com.example.ecommerce.domain.alarm.entity.Alarm;
import com.example.ecommerce.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/alarm")
public class AlarmController {

    private final UserService userService;


    @GetMapping("/list")
    public String getList(Model model, Principal principal) {
        List<Alarm> alarmList = this.userService.findByUsername(principal.getName()).getAlarmList();
        model.addAttribute("alarmList",alarmList);
        return "/alarm/list";
    }
}
