package com.backend.core.entities.requestdto.refund;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RefundFilterDTO {
    @JsonProperty("refundDate")
    Date refundDate;

    @JsonProperty("paymentMethod")
    String paymentMethod;

    @JsonProperty("reason")
    String reason;

    @JsonProperty("invoiceId")
    int invoiceId;

    @JsonProperty("adminId")
    int adminId;
}
