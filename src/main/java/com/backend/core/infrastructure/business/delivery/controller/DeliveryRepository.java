package com.backend.core.infrastructure.business.delivery.controller;

import com.backend.core.entity.delivery.model.Delivery;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface DeliveryRepository extends JpaRepository<Delivery, Integer> {
    @Query(value = "select * from delivery where shipping_order_code = :code", nativeQuery = true)
    List<Delivery> getDeliveryByShippingOrderCode(@Param("code") String code);
}
