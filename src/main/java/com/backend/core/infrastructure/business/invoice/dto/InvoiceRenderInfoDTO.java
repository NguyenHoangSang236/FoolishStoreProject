package com.backend.core.infrastructure.business.invoice.dto;

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

    @Column(name = "order_status")
    String orderStatus;

    @Column(name = "Payment_Status")
    String paymentStatus;

    @Column(name = "payment_method")
    String paymentMethod;

    @Column(name = "currency")
    String currency;

    @Column(name = "description")
    String description;

    @Column(name = "refund_percentage")
    double refundPercentage;

    @Column(name = "total_price")
    double totalPrice;

    @Column(name = "delivery_fee")
    double deliveryFee;

    @Column(name = "district_id")
    int districtId;

    @Column(name = "ward_code")
    String wardCode;

    @Column(name = "reason")
    String reason;

    @Column(name = "address")
    String address;

    @Column(name = "online_payment_account")
    String onlinePaymentAccount;

    @Column(name = "admin_acceptance")
    String adminAcceptance;
}
