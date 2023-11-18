package com.backend.core.entities.requestdto.delivery;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class DeliveryRequestDTO {
    @JsonProperty("shipping_order_code")
    String shippingOrderCode;

    @JsonProperty("invoice_id")
    int invoiceId;
}
