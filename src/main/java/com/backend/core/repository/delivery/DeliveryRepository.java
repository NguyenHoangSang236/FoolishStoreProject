package com.backend.core.repository.delivery;

import com.backend.core.entities.tableentity.Delivery;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface DeliveryRepository extends JpaRepository<Delivery, Integer> {
	@Query(value = "select * from delivery d join invoice i on d.invoice_id = i.id where shipper_id = :idVal and delivery_status = 'shipping'", nativeQuery = true)
	List<Delivery> getShippingDeliveryListByShipperId(@Param("idVal") int id);


	@Query(value = "select * from delivery where invoice_id = :invoiceId", nativeQuery = true)
	Delivery getDeliveryByInvoiceId(@Param("invoiceId") int id);
}
