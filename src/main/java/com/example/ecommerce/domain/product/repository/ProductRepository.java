package com.example.ecommerce.domain.product.repository;


import com.example.ecommerce.domain.product.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {



    @Query("select"
            + " Product p"
            + " from"
            + " Product p"
            + " order by"
            + " p.purchasing "
            + " desc"
            + " limit"
            + " 10")
    List<Product> getBestSeller();

}
