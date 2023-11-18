package com.backend.core.repository.delivery;

import com.backend.core.entities.tableentity.Delivery;
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
