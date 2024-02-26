package com.example.ecommerce.domain.optiondetail.controller;


import com.example.ecommerce.domain.option.entity.Option;
import com.example.ecommerce.domain.option.service.OptionService;
import com.example.ecommerce.domain.optiondetail.OptionDetailCreateForm;
import com.example.ecommerce.domain.optiondetail.entity.OptionDetail;
import com.example.ecommerce.domain.optiondetail.service.OptionDetailService;
import com.example.ecommerce.domain.product.entity.Product;
import com.example.ecommerce.domain.product.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/option_detail")
public class OptionDetailController {

    private final OptionDetailService optionDetailService;

    private final ProductService productService;

    private final OptionService optionService;

    @GetMapping("/create/{id}")
    public String createOptionDetail(Model model, @PathVariable(value = "id") Long id, OptionDetailCreateForm optionDetailCreateForm) {
        Product product = this.productService.findById(id);
        if (product == null) {
            product = this.productService.findByOptionId(id);
        }
        List<Option> optionList = product.getOptionList();
        model.addAttribute("optionList", optionList);
        model.addAttribute("product",product);
        return "/product/create_option_detail";
    }

    @PostMapping("/create/{id}")
    public String createOptionDetailPost(Model model, @Valid OptionDetailCreateForm optionDetailCreateForm, BindingResult bindingResult, @PathVariable(value = "id") Long id) {
        Product product = this.productService.findByOptionId(id);
        List<Option> optionList = product.getOptionList();
        model.addAttribute("optionList", optionList);
        model.addAttribute("product", product);
        if (bindingResult.hasErrors()) {
            return "/product/create_option_detail";
        }

        Option option = this.optionService.findById(id);
        OptionDetail optionDetail = this.optionDetailService.create(option, optionDetailCreateForm);
        this.optionService.addDetail(option, optionDetail);

        return String.format("redirect:/option_detail/create/%d",product.getId());
    }

    @GetMapping("/create_last/{id}")
    public String lastSelectOptionDetail(Model model, @PathVariable(value = "id") Long id) {
        Product product = this.productService.findById(id);
        List<OptionDetail> optionDetailList = product.getOptionList().get(0).getOptionDetailList();

        model.addAttribute("product",product);

        return "/product/create_last";
    }

}
