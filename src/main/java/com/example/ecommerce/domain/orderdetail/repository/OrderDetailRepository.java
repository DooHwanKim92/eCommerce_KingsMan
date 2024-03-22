package com.example.ecommerce.domain.orderdetail.repository;

import com.example.ecommerce.domain.orderdetail.entity.OrderDetail;
import com.example.ecommerce.domain.user.entity.SiteUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface OrderDetailRepository extends JpaRepository<OrderDetail, Long> {

    @Query("select "
            + "distinct o "
            + "from OrderDetail o "
            + "where "
            + "createDate >= :start "
            + "and modifiedDate <= :end")
    List<OrderDetail> findByPeriod(@Param("start") LocalDateTime start, @Param("end") LocalDateTime end);
}
