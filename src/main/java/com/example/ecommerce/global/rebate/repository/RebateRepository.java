package com.example.ecommerce.global.rebate.repository;


import com.example.ecommerce.global.rebate.entity.Rebate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RebateRepository extends JpaRepository<Rebate, Long> {
}
