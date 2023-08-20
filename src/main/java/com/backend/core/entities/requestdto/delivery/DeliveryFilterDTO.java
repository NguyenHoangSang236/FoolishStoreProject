package com.backend.core.entities.requestdto.delivery;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DeliveryFilterDTO {
    @JsonProperty(value = "country")
    String country;

    @JsonProperty(value = "city")
    String city;

    @JsonProperty(value = "address")
    String address;

    @JsonProperty(value = "paymentStatus")
    String paymentStatus;

    @JsonProperty(value = "paymentMethod")
    String paymentMethod;
}
