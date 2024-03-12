package com.backend.core.infrastructure.business.invoice.repository;

import com.backend.core.entity.invoice.key.InvoicesWithProductsPrimaryKeys;
import com.backend.core.infrastructure.business.invoice.dto.InvoiceProductInfoDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InvoiceDetailsRenderInfoRepository extends JpaRepository<InvoiceProductInfoDTO, InvoicesWithProductsPrimaryKeys> {
    @Query(value = "select * from invoice_with_products_info_for_ui where invoice_id = :idVal", nativeQuery = true)
    List<InvoiceProductInfoDTO> getInvoiceItemsByInvoiceId(@Param("idVal") int invoiceId);
}
