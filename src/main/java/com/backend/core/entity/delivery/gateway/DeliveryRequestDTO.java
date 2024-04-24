package com.backend.core.entity.delivery.gateway;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class DeliveryRequestDTO implements Serializable {
    @JsonProperty("shipping_order_code")
    String shippingOrderCode;

    @JsonProperty("invoice_id")
    int invoiceId;
}
