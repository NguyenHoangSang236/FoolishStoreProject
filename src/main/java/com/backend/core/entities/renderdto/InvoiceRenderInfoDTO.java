package com.backend.core.entities.renderdto;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Data
@Entity
@Getter
@Setter
@Table(name = "invoice")
public class InvoiceRenderInfoDTO {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", unique = true)
    int id;

    @Column(name = "Customer_ID")
    int customerId;

    @Column(name = "Invoice_Date")
    Date invoiceDate;

    @Column(name = "Delivery_Status")
    String deliveryStatus;

    @Column(name = "Payment_Status")
    int paymentStatus;

    @Column(name = "payment_method")
    String paymentMethod;

    @Column(name = "currency")
    String currency;

    @Column(name = "intent")
    String intent;

    @Column(name = "description")
    String description;

    @Column(name = "refund_percentage")
    double refundPercentage;

    @Column(name = "total_price")
    double totalPrice;

    @Column(name = "reason")
    String reason;

    @Column(name = "online_payment_account")
    String onlinePaymentAccount;

    @Column(name = "admin_acceptance")
    String adminAcceptance;

    public InvoiceRenderInfoDTO(int id, int customerId, Date invoiceDate, int paymentStatus, String deliveryStatus, double refundPercentage, String reason, String currency, String paymentMethod, String description, String intent, String adminAcceptance, double totalPrice, String onlinePaymentAccount) {
        this.id = id;
        this.customerId = customerId;
        this.invoiceDate = invoiceDate;
        this.deliveryStatus = deliveryStatus;
        this.paymentStatus = paymentStatus;
        this.paymentMethod = paymentMethod;
        this.currency = currency;
        this.intent = intent;
        this.description = description;
        this.refundPercentage = refundPercentage;
        this.totalPrice = totalPrice;
        this.reason = reason;
        this.onlinePaymentAccount = onlinePaymentAccount;
        this.adminAcceptance = adminAcceptance;
    }

    public InvoiceRenderInfoDTO() {}
}
