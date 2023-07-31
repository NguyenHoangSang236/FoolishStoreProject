package com.backend.core.entities.tableentity;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.backend.core.entities.dto.invoice.InvoicesWithProducts;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Entity
@Getter
@Setter
@DynamicInsert
@DynamicUpdate
@Table(name = "invoice")
public class Invoice {
    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", unique = true)
    int id;

    @Column(name = "Invoice_Date")
    Date invoiceDate;

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

    @Column(name = "online_payment_account")
    String onlinePaymentAccount;

    @Column(name = "admin_acceptance")
    String adminAcceptance;

    @OneToOne(mappedBy = "invoice")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Delivery delivery;

    @JsonIgnore
    @OneToMany(mappedBy = "invoice", cascade={CascadeType.ALL})
    List<InvoicesWithProducts> invoicesWithProducts;

    @JsonIgnore
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "admin_in_charge_id")
    private Staff staff;


    public Invoice() {}

    public Invoice(int id, Customer customer, Date invoiceDate) {
        super();
        this.id = id;
        this.invoiceDate = invoiceDate;
        this.customer = customer;
    }

    public Invoice(int id, Date invoiceDate, String deliveryStatus, int paymentStatus, String paymentMethod, String currency,
                   String intent, String description, double refundPercentage, String reason, Delivery delivery,
                   List<InvoicesWithProducts> invoicesWithProducts, Customer customer, String adminAcceptance) {
        super();
        this.id = id;
        this.invoiceDate = invoiceDate;
        this.deliveryStatus = deliveryStatus;
        this.paymentStatus = paymentStatus;
        this.paymentMethod = paymentMethod;
        this.currency = currency;
        this.intent = intent;
        this.description = description;
        this.refundPercentage = refundPercentage;
        this.reason = reason;
        this.delivery = delivery;
        this.invoicesWithProducts = invoicesWithProducts;
        this.customer = customer;
        this.adminAcceptance = adminAcceptance;
    }

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

    public Invoice(int id, Date invoiceDate, String deliveryStatus, int paymentStatus, String paymentMethod, String currency,
                   String intent, String description, Customer customer, double totalPrice, String adminAcceptance) {
        super();
        this.id = id;
        this.invoiceDate = invoiceDate;
        this.deliveryStatus = deliveryStatus;
        this.paymentStatus = paymentStatus;
        this.paymentMethod = paymentMethod;
        this.currency = currency;
        this.intent = intent;
        this.description = description;
        this.customer = customer;
        this.totalPrice = totalPrice;
        this.adminAcceptance = adminAcceptance;
    }

    public Invoice(int id, Date invoiceDate, String deliveryStatus, int paymentStatus, String paymentMethod, String currency,
                   String intent, String description, double refundPercentage, double totalPrice, String reason, String onlinePaymentAccount, String adminAcceptance) {
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
    }



    //    public String formattedTotalPrice() {
//        return ValueRender.formatDoubleNumber(this.totalPrice);
//    }

//    public String formattedInvoiceDate() {
//        return ValueRender.formatDateDMY(this.invoiceDate);
//    }

}
