package com.example.ecommerce.domain.confirm.controller;


import com.example.ecommerce.domain.confirm.entity.Confirm;
import com.example.ecommerce.domain.confirm.service.ConfirmService;
import com.example.ecommerce.domain.user.entity.SiteUser;
import com.example.ecommerce.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/confirm")
public class ConfirmController {

    private final ConfirmService confirmService;

    private final UserService userService;

    public Confirm findBySellername(String sellername) {
        return this.confirmService.findBySellerName(sellername);
    }

    public Confirm findBySellernumber(String sellernumber) {
        return this.confirmService.findBySellerNumber(sellernumber);
    }

    @GetMapping("/list")
    public String confirmList(Model model) {
        List<Confirm> confirmList = this.confirmService.findAll();
        model.addAttribute("confirmList",confirmList);
        return "/salesconfirm/list";
    }

    @GetMapping("/accept/{id}")
    public String confirmAccept(@PathVariable(value = "id") Long id, Model model) {
        List<Confirm> confirmList = this.confirmService.findAll();
        model.addAttribute("confirmList",confirmList);

        Confirm confirm = this.confirmService.findById(id);
        SiteUser user = confirm.getUser();

        this.confirmService.accept(confirm);
        this.userService.acceptSalesConfirm(user);

        return "redirect:/confirm/list";
    }

}
