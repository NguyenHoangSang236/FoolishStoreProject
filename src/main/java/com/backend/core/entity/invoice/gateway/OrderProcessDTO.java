package com.backend.core.entity.invoice.gateway;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderProcessDTO {
    @JsonProperty("id")
    int id;

    @JsonProperty("adminAction")
    String adminAction;

    @JsonProperty("reason")
    String reason;
}
