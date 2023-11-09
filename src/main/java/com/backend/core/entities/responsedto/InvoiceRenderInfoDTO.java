package com.backend.core.entities.responsedto;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Data
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
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

    @Column(name = "Delivery_type")
    String deliveryType;

    @Column(name = "Delivery_fee")
    double deliveryFee;

    @Column(name = "Payment_Status")
    String paymentStatus;

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
}