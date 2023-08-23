package com.backend.core.repository.delivery;

import com.backend.core.entities.renderdto.DeliveryOrderRenderInfoDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface DeliveryRenderInfoRepository extends JpaRepository<DeliveryOrderRenderInfoDTO, Integer> {
    @Query(value = "select * from delivery_info_for_ui where invoice_id = :invoiceId", nativeQuery = true)
    DeliveryOrderRenderInfoDTO getDeliveryRenderInfoByInvoiceId(@Param("invoiceId") int id);

    @Query(value = "select * from delivery_info_for_ui where invoice_id = :invoiceId and shipper_id= :shipperId", nativeQuery = true)
    DeliveryOrderRenderInfoDTO getDeliveryRenderInfoByInvoiceIdAndShipperId(@Param("invoiceId") int invoiceId, @Param("shipperId") int shipperId);
}
