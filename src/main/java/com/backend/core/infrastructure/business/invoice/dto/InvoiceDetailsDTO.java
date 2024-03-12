package com.backend.core.infrastructure.business.invoice.dto;

import com.backend.core.entity.invoice.model.Invoice;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class InvoiceDetailsDTO {
    @JsonProperty("invoice")
    Invoice invoice;

    @JsonProperty("invoiceProducts")
    List<InvoiceProductInfoDTO> invoiceProducts;
}
