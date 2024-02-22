package com.example.ecommerce.domain.optiondetail.repository;


import com.example.ecommerce.domain.optiondetail.entity.OptionDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OptionDetailRepository extends JpaRepository<OptionDetail, Long> {
}
