package com.backend.core.entity.delivery.gateway;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DeliveryActionOnOrderDTO implements Serializable {
    @JsonProperty(value = "id")
    int id;

    @JsonProperty(value = "action")
    String action;
}
