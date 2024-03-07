package com.example.ecommerce.domain.product.repository;


import com.example.ecommerce.domain.product.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {



    @Query("select"
            + " p"
            + " from"
            + " Product p"
            + " order by"
            + " p.purchasing "
            + " desc"
            + " limit"
            + " 10")
    List<Product> getBestSeller();

    @Query("select"
            + " p"
            + " from"
            + " Product p"
            + " order by"
            + " p.createDate "
            + " desc"
            + " limit"
            + " 10")
    List<Product> getNewProducts();

    @Query("select"
            + " p"
            + " from"
            + " Product p"
            + " order by"
            + " p.score "
            + " desc"
            + " limit"
            + " 10")
    List<Product> getHighQualityProducts();

    @Query("select "
            + "distinct p "
            + "from Product p "
            + "left outer join SiteUser u1 on p.user = u1 "
            + "where "
            + "   p.name like %:kw% "
            + "   or p.content like %:kw% "
            + "   or u1.sellerName like %:kw% ")
    List<Product> findAllByKeyword(@Param("kw") String kw);

}
