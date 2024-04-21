package com.backend.core.entity.cart.gateway;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AddressNameDTO implements Serializable {
    @NotEmpty
    @JsonProperty("fromDistrict")
    String fromDistrict;

    @NotEmpty
    @JsonProperty("fromProvince")
    String fromProvince;

    @NotEmpty
    @JsonProperty("fromWard")
    String fromWard;

    @NotEmpty
    @JsonProperty("fromAddress")
    String fromAddress;

    @NotEmpty
    @JsonProperty("toDistrict")
    String toDistrict;

    @NotEmpty
    @JsonProperty("toProvince")
    String toProvince;

    @NotEmpty
    @JsonProperty("toWard")
    String toWard;

    @NotEmpty
    @JsonProperty("toAddress")
    String toAddress;
}
