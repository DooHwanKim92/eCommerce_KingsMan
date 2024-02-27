package com.example.ecommerce.domain.confirm.service;


import com.example.ecommerce.domain.confirm.ConfirmForm;
import com.example.ecommerce.domain.confirm.entity.Confirm;
import com.example.ecommerce.domain.confirm.repository.ConfirmRepository;
import com.example.ecommerce.domain.user.entity.SiteUser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ConfirmService {

    private final ConfirmRepository confirmRepository;
    public Confirm findBySellerName(String sellername) {
        Optional<Confirm> confirm = this.confirmRepository.findBySellerName(sellername);
        if (confirm.isEmpty()) {
            return null;
        }
        return confirm.get();
    }

    public Confirm findBySellerNumber(String sellernumber) {
        Optional<Confirm> confirm = this.confirmRepository.findBySellerNumber(sellernumber);
        if(confirm.isEmpty()) {
            return null;
        }
        return confirm.get();
    }

    public void create(ConfirmForm confirmForm, SiteUser user) {
        Confirm confirm = Confirm.builder()
                .user(user)
                .sellerName(confirmForm.getSellerName())
                .sellerNumber(confirmForm.getSellerNumber())
                .isConfirmed("진행중")
                .build();

        this.confirmRepository.save(confirm);
    }

    public List<Confirm> findAll() {
        return this.confirmRepository.findAll();
    }

    public Confirm findById(Long id) {
        Optional<Confirm> confirm = this.confirmRepository.findById(id);
        if(confirm.isEmpty()) {
            return null;
        }
        return confirm.get();
    }

    public void accept(Confirm confirm) {
        this.confirmRepository.delete(confirm);
    }

    public void deny(Confirm confirm) {
        this.confirmRepository.delete(confirm);
    }
}
