package com.backend.core.entities.tableentity;

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
@Table(name = "delivery")
@DynamicInsert
@DynamicUpdate
@NoArgsConstructor
@AllArgsConstructor
public class Delivery {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true)
    int id;

    @Column(name = "shipping_order_code")
    String shippingOrderCode;

    @Column(name = "ship_date")
    Date shipDate;

    @Column(name = "expected_delivery_time")
    Date expectedDeliveryTime;

    @OneToOne
    @JoinColumn(name = "invoice_id", referencedColumnName = "ID")
    private Invoice invoice;


//    public String convertByteImamgeToBase64String() {
//        return "data:image/jpeg;base64," + ValueRender.convertByteToString(this.evidenceImage);
//    }
}
