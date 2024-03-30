package com.backend.core.infrastructure.business.cart.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CartCheckoutInfoDTO {
    @JsonProperty("subtotal")
    double subtotal;

    @JsonProperty("total")
    double total;

    @JsonProperty("shippingFee")
    double shippingFee;
}
