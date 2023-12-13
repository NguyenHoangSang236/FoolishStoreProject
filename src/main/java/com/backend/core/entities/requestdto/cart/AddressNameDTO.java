package com.backend.core.entities.requestdto.cart;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AddressNameDTO {
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
