package com.backend.core.repository.delivery;

import com.backend.core.entities.tableentity.DeliveryType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DeliveryTypeRepository extends JpaRepository<DeliveryType, Integer> {
    @Query(value = "select * from delivery_type where name = :name", nativeQuery = true)
    DeliveryType getDeliveryTypeByName(@Param("name") String name);

    @Query(value = "select * from delivery_type", nativeQuery = true)
    List<DeliveryType> getAllDeliveryTypes();
}
