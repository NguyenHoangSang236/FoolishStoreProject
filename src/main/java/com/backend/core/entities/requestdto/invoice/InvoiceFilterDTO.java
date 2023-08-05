package com.backend.core.entities.requestdto.invoice;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class InvoiceFilterDTO {
    @JsonProperty("adminAcceptance")
    String adminAcceptance;

    @JsonProperty("paymentStatus")
    int paymentStatus;

    @JsonProperty("deliveryStatus")
    String deliveryStatus;

    @JsonProperty("startInvoiceDate")
    Date startInvoiceDate;

    @JsonProperty("endInvoiceDate")
    Date endInvoiceDate;

    @JsonProperty("paymentMethod")
    String paymentMethod;

    @JsonProperty("type")
    String type;


    public InvoiceFilterDTO(String adminAcceptance, int paymentStatus, String deliveryStatus, Date startInvoiceDate, Date endInvoiceDate, String paymentMethod, String type) {
        this.adminAcceptance = adminAcceptance;
        this.paymentStatus = paymentStatus;
        this.deliveryStatus = deliveryStatus;
        this.startInvoiceDate = startInvoiceDate;
        this.endInvoiceDate = endInvoiceDate;
        this.paymentMethod = paymentMethod;
        this.type = type;
    }

    public InvoiceFilterDTO() {}
}
