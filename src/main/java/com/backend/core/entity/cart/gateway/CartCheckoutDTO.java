package com.backend.core.entity.cart.gateway;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.annotation.Nullable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CartCheckoutDTO {
    @JsonProperty("fromDistrictId")
    int fromDistrictId;

    @JsonProperty("toDistrictId")
    int toDistrictId;

    @JsonProperty("fromProvinceId")
    int fromProvinceId;

    @JsonProperty("toProvinceId")
    int toProvinceId;

    @JsonProperty("fromWardCode")
    String fromWardCode;

    @JsonProperty("toWardCode")
    String toWardCode;

    @JsonProperty("serviceId")
    int serviceId;

    @Nullable
    @JsonProperty("address")
    String address;

    @Nullable
    @JsonProperty("note")
    String note;

    @Nullable
    @JsonProperty("paymentMethod")
    String paymentMethod;
}
