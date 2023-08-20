package com.backend.core.entities.renderdto;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Entity
@Getter
@Setter
@Table(name = "delivery_info_for_ui")
@AllArgsConstructor
@NoArgsConstructor
public class DeliveryOrderRenderInfoDTO {
    @Column(name = "id")
    String id;

    @Column(name = "shipper_name")
    String shipperName;

    @Column(name = "shipper_id")
    int shipperId;

    @Column(name = "shipper_phone_number")
    String shipperPhoneNumber;

    @Column(name = "shipper_avatar")
    String shipperAvatar;

    @Id
    @Column(name = "invoice_id")
    int invoiceId;

    @Column(name = "invoice_date")
    Date invoiceDate;

    @Column(name = "delivery_date")
    Date deliveryDate;

    @Column(name = "invoice_delivery_status")
    String invoiceDeliveryStatus;

    @Column(name = "current_delivery_status")
    String currentDeliveryStatus;

    @Column(name = "payment_status")
    String paymentStatus;

    @Column(name = "payment_method")
    String paymentMethod;

    @Column(name = "description")
    String description;

    @Column(name = "total_price")
    double totalPrice;

    @Column(name = "customer_id")
    String customerId;

    @Column(name = "customer_name")
    String customerName;

    @Column(name = "customer_phone_number")
    String customerPhoneNumber;

    @Column(name = "address")
    String address;

    @Column(name = "city")
    String city;

    @Column(name = "country")
    String country;

    @Column(name = "note")
    String note;

    @Column(name = "customer_avatar")
    String customerAvatar;

    @Column(name = "additional_shipper_comment")
    String additionalShipperComment;

    @Column(name = "evidence_image")
    String evidenceImage;
}
