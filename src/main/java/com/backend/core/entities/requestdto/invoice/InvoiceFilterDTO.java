package com.backend.core.entities.requestdto.invoice;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class InvoiceFilterDTO {
    @JsonProperty("adminAcceptance")
    String adminAcceptance;

    @JsonProperty("paymentStatus")
    String paymentStatus;

    @JsonProperty("orderStatus")
    String orderStatus;

    @JsonProperty("startInvoiceDate")
    Date startInvoiceDate;

    @JsonProperty("endInvoiceDate")
    Date endInvoiceDate;

    @JsonProperty("paymentMethod")
    String paymentMethod;
}
