package com.backend.core.entity.invoice.gateway;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class InvoiceFilterDTO implements Serializable {
    @JsonProperty("adminAcceptance")
    String adminAcceptance;

    @JsonProperty("paymentStatus")
    String paymentStatus;

    @JsonProperty("orderStatus")
    String orderStatus;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSZ")
    @JsonProperty("startInvoiceDate")
    Date startInvoiceDate;

    @JsonProperty("endInvoiceDate")
    Date endInvoiceDate;

    @JsonProperty("paymentMethod")
    String paymentMethod;
}
