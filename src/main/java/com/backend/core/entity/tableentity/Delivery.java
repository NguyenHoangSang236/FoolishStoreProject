package com.backend.core.entity.tableentity;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "delivery")
public class Delivery{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true)
    int id;

    @Column(name = "delivery_date")
    Date deliveryDate;

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


    public Delivery() {}

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
