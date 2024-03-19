package com.example.ecommerce.global.rebate.service;


import com.example.ecommerce.domain.user.entity.SiteUser;
import com.example.ecommerce.global.rebate.repository.RebateRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
@RequiredArgsConstructor
public class RebateService {

    private final RebateRepository rebateRepository;

    public LocalDateTime formattingStartDate(String startDate) {

        LocalDateTime dateTime = LocalDateTime.parse(startDate, DateTimeFormatter.ISO_LOCAL_DATE);

        return dateTime;
    }


    public LocalDateTime formattingEndDate(String endDate) {

        LocalDateTime dateTime = LocalDateTime.parse(endDate, DateTimeFormatter.ISO_LOCAL_DATE);

        return dateTime;
    }

    public void getSelectedRebateList(SiteUser user, LocalDateTime startDateTime, LocalDateTime endDateTime) {

    }
}
