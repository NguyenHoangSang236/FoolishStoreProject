package com.backend.core.entity.invoice.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import java.io.Serializable;
import java.util.List;

@Data
@Entity
@Getter
@Setter
@DynamicInsert
@DynamicUpdate
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "online_payment_accounts")
public class OnlinePaymentAccount implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", unique = true)
    int id;

    @Column(name = "receiver_account")
    String receiverAccount;

    @Column(name = "receiver_name")
    String receiverName;

    @Column(name = "additional_info")
    String additionalInfo;

    @Column(name = "type")
    String type;

    @JsonIgnore
    @OneToMany(mappedBy = "receiverPaymentAccount", cascade = {CascadeType.ALL})
    List<Invoice> invoices;
}
