package com.example.ecommerce.domain.confirm.repository;


import com.example.ecommerce.domain.confirm.entity.Confirm;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ConfirmRepository extends JpaRepository<Confirm, Long> {
    Optional<Confirm> findBySellerName(String sellername);

    Optional<Confirm> findBySellerNumber(String sellernumber);
}
