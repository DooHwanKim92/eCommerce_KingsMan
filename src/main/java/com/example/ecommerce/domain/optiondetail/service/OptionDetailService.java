package com.example.ecommerce.domain.optiondetail.service;


import com.example.ecommerce.domain.option.entity.Option;
import com.example.ecommerce.domain.optiondetail.OptionDetailCreateForm;
import com.example.ecommerce.domain.optiondetail.entity.OptionDetail;
import com.example.ecommerce.domain.optiondetail.repository.OptionDetailRepository;
import com.example.ecommerce.domain.product.entity.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OptionDetailService {

    private final OptionDetailRepository optionDetailRepository;
    public OptionDetail create(Option option, OptionDetailCreateForm optionDetailCreateForm) {

        OptionDetail optionDetail = OptionDetail.builder()
                .option(option)
                .detail(optionDetailCreateForm.getDetail())
                .build();

        this.optionDetailRepository.save(optionDetail);

        return optionDetail;
    }

    public List<String> getOptionList(Product product) {
        Option option1 = product.getOptionList().get(0);
        Option option2 = product.getOptionList().get(1);
        List<String> optionNames = new ArrayList<>();
        String optionName = "";
        for (int i = 0; i < option1.getOptionDetailList().size(); i++) {
            for (int j = 0; j < option2.getOptionDetailList().size(); j++) {
                optionName = option1.getOptionDetailList().get(i).getDetail() + " - " + option2.getOptionDetailList().get(j).getDetail();
                optionNames.add(optionName);
            }
        }
        return optionNames;
    }


}
