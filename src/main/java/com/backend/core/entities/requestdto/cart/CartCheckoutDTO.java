package com.backend.core.entities.requestdto.cart;

import com.fasterxml.jackson.annotation.JsonProperty;
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
}
