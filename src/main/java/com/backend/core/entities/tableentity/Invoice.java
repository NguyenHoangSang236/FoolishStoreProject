package com.backend.core.entities.tableentity;

import com.backend.core.entities.requestdto.invoice.InvoicesWithProducts;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.Date;
import java.util.List;

@Data
@Entity
@Getter
@Setter
@DynamicInsert
@DynamicUpdate
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "invoice")
public class Invoice {
    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", unique = true)
    int id;

    @Column(name = "invoice_date")
    Date invoiceDate;

    @Column(name = "pay_date")
    Date payDate;

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

    @JsonIgnore
    @Column(name = "online_payment_account")
    String onlinePaymentAccount;

    @Column(name = "admin_acceptance")
    String adminAcceptance;
    @JsonIgnore
    @OneToMany(mappedBy = "invoice", cascade = {CascadeType.ALL})
    List<InvoicesWithProducts> invoicesWithProducts;
    @OneToOne(mappedBy = "invoice")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Delivery delivery;
    @JsonIgnore
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "admin_in_charge_id")
    private Staff staff;


    public Invoice(int id, Date invoiceDate, String deliveryStatus, int paymentStatus, String paymentMethod, String currency,
                   String intent, String description, double refundPercentage, double totalPrice, String reason, String onlinePaymentAccount,
                   String adminAcceptance, Delivery delivery, List<InvoicesWithProducts> invoicesWithProducts, Customer customer) {
        this.id = id;
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
        this.delivery = delivery;
        this.invoicesWithProducts = invoicesWithProducts;
        this.customer = customer;
    }
}
