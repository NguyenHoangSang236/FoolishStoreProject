package com.backend.core.repository;

import com.backend.core.entities.embededkey.InvoicesWithProductsPrimaryKeys;
import com.backend.core.entities.renderdto.InvoiceDetailRenderInfoDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InvoiceDetailsRenderInfoRepository extends JpaRepository<InvoiceDetailRenderInfoDTO, InvoicesWithProductsPrimaryKeys> {
    @Query(value = "select * from invoice_with_products_info_for_ui where invoice_id = :idVal", nativeQuery = true)
    List<InvoiceDetailRenderInfoDTO> getInvoiceItemsByInvoiceId(@Param("idVal") int invoiceId);
}
