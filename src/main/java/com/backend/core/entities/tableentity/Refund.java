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
@DynamicInsert
@DynamicUpdate
@Table(name = "refund")
public class Refund {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", unique = true)
    int id;

    @Column(name = "refund_money")
    double refundMoney;

    @Column(name = "date")
    Date date;

    @Column(name = "content")
    String content;

    @Column(name = "evident_image")
    String evidentImage;

    @Column(name = "status")
    String status;

    @ManyToOne
    @JoinColumn(name = "in_charge_admin_id")
    Staff staff;

    @OneToOne
    @JoinColumn(name = "invoice_id", referencedColumnName = "ID")
    Invoice invoice;
}
