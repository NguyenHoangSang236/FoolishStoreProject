package com.backend.core.entity.refund.model;

import com.backend.core.entity.account.model.Staff;
import com.backend.core.entity.invoice.model.Invoice;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
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
@NoArgsConstructor
@AllArgsConstructor
public class Refund {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", unique = true)
    int id;

    @Column(name = "refund_money")
    double refundMoney;

    @Column(name = "date")
    Date date;

    @Column(name = "evident_image")
    String evidentImage;

    @Column(name = "status")
    String status;

    @ManyToOne
    @JoinColumn(name = "in_charge_admin_id")
    Staff staff;

    @JsonIgnore
    @OneToOne
    @JoinColumn(name = "invoice_id", referencedColumnName = "ID")
    Invoice invoice;

    @JsonProperty("invoiceId")
    public int invoiceID() {
        return this.invoice.getId();
    }
}
