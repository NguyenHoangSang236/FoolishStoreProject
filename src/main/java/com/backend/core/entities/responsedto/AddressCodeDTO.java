package com.backend.core.entities.responsedto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AddressCodeDTO {
    @NotEmpty
    @JsonProperty("fromDistrictId")
    int fromDistrictId;

    @NotEmpty
    @JsonProperty("fromProvinceId")
    int fromProvinceId;

    @NotEmpty
    @JsonProperty("fromWardCode")
    String fromWardCode;

    @NotEmpty
    @JsonProperty("toDistrictId")
    int toDistrictId;

    @NotEmpty
    @JsonProperty("toProvinceId")
    int toProvinceId;

    @NotEmpty
    @JsonProperty("toWardCode")
    String toWardCode;
}
