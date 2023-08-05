package com.backend.core.entities.requestdto.invoice;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderProcessDTO {
    @JsonProperty("id")
    int id;

    @JsonProperty("adminAction")
    String adminAction;

    @JsonProperty("reason")
    String reason;


    public OrderProcessDTO() {
    }

    public OrderProcessDTO(int id, String adminAction, String reason) {
        this.id = id;
        this.adminAction = adminAction;
        this.reason = reason;
    }
}
