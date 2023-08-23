package com.backend.core.entities.tableentity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import java.util.Date;

@Entity
@Getter
@Setter
@Table(name = "delivery")
@DynamicInsert
@DynamicUpdate
public class Delivery {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true)
    int id;

    @Column(name = "delivery_date")
    Date deliveryDate;

    @Column(name = "expected_delivery_date")
    Date expectedDeliveryDate;

    @Column(name = "current_status")
    String currentStatus;

    @Column(name = "additional_shipper_comment")
    String additionalShipperComment;

    @Lob
    @Column(name = "evidence_image")
    byte[] evidenceImage;

    @ManyToOne
    @JoinColumn(name = "shipper_id")
    Staff staff;

    @OneToOne
    @JoinColumn(name = "invoice_id", referencedColumnName = "ID")
    private Invoice invoice;


    public Delivery() {
    }

    public Delivery(Date deliveryDate, String currentStatus, String additionalShipperComment, byte[] evidenceImage,
                    Staff staff, Invoice invoice) {
        super();
        this.deliveryDate = deliveryDate;
        this.currentStatus = currentStatus;
        this.additionalShipperComment = additionalShipperComment;
        this.evidenceImage = evidenceImage;
        this.staff = staff;
        this.invoice = invoice;
    }

    public Delivery(String currentStatus, Staff staff, Invoice invoice) {
        super();
        this.currentStatus = currentStatus;
        this.staff = staff;
        this.invoice = invoice;
    }


//    public String convertByteImamgeToBase64String() {
//        return "data:image/jpeg;base64," + ValueRender.convertByteToString(this.evidenceImage);
//    }
}
