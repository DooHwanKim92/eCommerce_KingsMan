package com.example.ecommerce.domain.option.service;


import com.example.ecommerce.domain.option.OptionCreateForm;
import com.example.ecommerce.domain.option.entity.Option;
import com.example.ecommerce.domain.option.repository.OptionRepository;
import com.example.ecommerce.domain.optiondetail.entity.OptionDetail;
import com.example.ecommerce.domain.product.entity.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OptionService {


    private final OptionRepository optionRepository;
    public Option create(OptionCreateForm optionCreateForm, Product product) {
        Option option = Option.builder()
                .product(product)
                .name(optionCreateForm.getOptionName())
                .build();

        this.optionRepository.save(option);

        return option;
    }

    public Option findById(Long id) {
        Optional<Option> option = this.optionRepository.findById(id);
        if(option.isEmpty()) {
            return null;
        }
        return option.get();
    }

    public void addDetail(Option option, OptionDetail optionDetail) {
        List<OptionDetail> optionDetailList = option.getOptionDetailList();
        optionDetailList.add(optionDetail);
        Option detailOption = option.toBuilder()
                .optionDetailList(optionDetailList)
                .build();

        this.optionRepository.save(detailOption);
    }

}
