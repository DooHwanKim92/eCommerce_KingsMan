package com.example.ecommerce.domain.optiondetail.service;


import com.example.ecommerce.domain.option.entity.Option;
import com.example.ecommerce.domain.optiondetail.OptionDetailCreateForm;
import com.example.ecommerce.domain.optiondetail.entity.OptionDetail;
import com.example.ecommerce.domain.optiondetail.repository.OptionDetailRepository;
import com.example.ecommerce.domain.product.entity.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OptionDetailService {

    private final OptionDetailRepository optionDetailRepository;
    public OptionDetail create(Option option, OptionDetailCreateForm optionDetailCreateForm) {

        OptionDetail optionDetail = OptionDetail.builder()
                .option(option)
                .detail(optionDetailCreateForm.getDetail())
                .price("0")
                .amount("0")
                .build();

        this.optionDetailRepository.save(optionDetail);

        return optionDetail;
    }
}
