package com.example.ecommerce.domain.alarm.controller;


import com.example.ecommerce.domain.alarm.entity.Alarm;
import com.example.ecommerce.domain.alarm.service.AlarmService;
import com.example.ecommerce.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/alarm")
public class AlarmController {

    private final UserService userService;

    private final AlarmService alarmService;


    @GetMapping("/list")
    public String getList(Model model, Principal principal) {
        List<Alarm> alarmList = this.userService.findByUsername(principal.getName()).getAlarmList();
        model.addAttribute("alarmList",alarmList);
        return "/alarm/list";
    }

    @GetMapping("/detail/{id}")
    public String alarmDetail(Model model, @PathVariable(value = "id") Long id) {
        Alarm alarm = this.alarmService.findById(id);
        model.addAttribute("alarm",alarm);
        this.alarmService.check(alarm);
        return "/alarm/detail";
    }

    @GetMapping("/remove/{id}")
    public String removeAlarm(@PathVariable(value = "id") Long id) {
        Alarm alarm = this.alarmService.findById(id);
        this.alarmService.remove(alarm);
        return "/alarm/list";
    }
}
