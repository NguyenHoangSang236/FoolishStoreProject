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
public class DeliveryActionOnOrderDTO {
    @JsonProperty(value = "id")
    int id;

    @JsonProperty(value = "action")
    String action;
}
