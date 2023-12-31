package com.backend.core.entities.responsedto;

import com.backend.core.entities.tableentity.Invoice;
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
