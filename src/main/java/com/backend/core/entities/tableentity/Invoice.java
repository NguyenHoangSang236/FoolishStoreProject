package com.backend.core.entities.tableentity;

import com.backend.core.enums.InvoiceEnum;
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
    @Column(name = "ID", unique = true)
    int id;

    @Column(name = "invoice_date")
    Date invoiceDate;

    @Column(name = "pay_date")
    Date payDate;

    @Column(name = "Payment_Status")
    String paymentStatus;

    @Column(name = "order_status")
    String orderStatus;

    @Column(name = "payment_method")
    String paymentMethod;

    @Column(name = "currency")
    String currency;

    @Column(name = "note")
    String note;

    @Column(name = "description")
    String description;

    @Column(name = "refund_percentage")
    double refundPercentage;

    @Column(name = "total_price")
    double totalPrice;

    @Column(name = "delivery_fee")
    double deliveryFee;

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

    @JsonIgnore
    @OneToOne(mappedBy = "invoice")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Refund refund;

    @ManyToOne
    @JoinColumn(name = "receiver_account_id")
    private OnlinePaymentAccount receiverPaymentAccount;


    public Invoice(int id, Date invoiceDate, String paymentStatus, String orderStatus, String paymentMethod, String currency,
                   String note, String description, double refundPercentage, double totalPrice, double deliveryFee, String reason, String onlinePaymentAccount,
                   String adminAcceptance, Delivery delivery, List<InvoicesWithProducts> invoicesWithProducts, Customer customer) {
        this.id = id;
        this.invoiceDate = invoiceDate;
        this.paymentStatus = paymentStatus;
        this.orderStatus = orderStatus;
        this.paymentMethod = paymentMethod;
        this.currency = currency;
        this.note = note;
        this.description = description;
        this.refundPercentage = refundPercentage;
        this.totalPrice = totalPrice;
        this.reason = reason;
        this.onlinePaymentAccount = onlinePaymentAccount;
        this.adminAcceptance = adminAcceptance;
        this.delivery = delivery;
        this.deliveryFee = deliveryFee;
        this.invoicesWithProducts = invoicesWithProducts;
        this.customer = customer;
    }

    // check invoice can be updated or not
    public boolean isUpdatable() {
        if (!this.orderStatus.equals(InvoiceEnum.FAILED.name())
                && !this.orderStatus.equals(InvoiceEnum.SUCCESS.name())
                && !this.orderStatus.equals(InvoiceEnum.CUSTOMER_CANCEL.name())
                && !this.orderStatus.equals(InvoiceEnum.SHIPPER_CANCEL.name())) {
            return true;
        } else return false;
    }

    // check if there is any product whose quantity is higher than its available quantity
    public String getOutOfStockProduct() {
        for (InvoicesWithProducts invoiceProduct : this.invoicesWithProducts) {
            ProductManagement productMng = invoiceProduct.getProductManagement();

            if (productMng.getAvailableQuantity() < invoiceProduct.getQuantity()) {
                return productMng.getProduct().getName();
            }
        }

        return null;
    }
}
