package com.backend.core.entity.tableentity;

import java.util.Date;
import java.util.List;

import com.backend.core.entity.interfaces.PurchaseCalculation;
import com.backend.core.entity.tableentity.Customer;
import com.backend.core.entity.tableentity.Delivery;
import com.backend.core.service.CalculationService;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.backend.core.entity.dto.InvoicesWithProducts;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Entity
@Getter
@Setter
@Table(name = "invoice")
public class Invoice implements PurchaseCalculation {
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
    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;


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





    public int getId() {
        return id;
    }


    public void setId(int id) {
        this.id = id;
    }


    public Date getInvoiceDate() {
        return invoiceDate;
    }


    public void setInvoiceDate(Date invoiceDate) {
        this.invoiceDate = invoiceDate;
    }


    public String getDeliveryStatus() {
        return deliveryStatus;
    }


    public void setDeliveryStatus(String deliveryStatus) {
        this.deliveryStatus = deliveryStatus;
    }


    public int getPaymentStatus() {
        return paymentStatus;
    }


    public void setPaymentStatus(int paymentStatus) {
        this.paymentStatus = paymentStatus;
    }


    public String getPaymentMethod() {
        return paymentMethod;
    }


    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }


    public String getCurrency() {
        return currency;
    }


    public void setCurrency(String currency) {
        this.currency = currency;
    }


    public double getRefundPercentage() {
        return refundPercentage;
    }


    public void setRefundPercentage(double refundPercentage) {
        this.refundPercentage = refundPercentage;
    }


    public String getReason() {
        return reason;
    }


    public void setReason(String reason) {
        this.reason = reason;
    }


    public Delivery getDelivery() {
        return delivery;
    }


    public void setDelivery(Delivery delivery) {
        this.delivery = delivery;
    }


    public List<InvoicesWithProducts> getInvoicesWithProducts() {
        return invoicesWithProducts;
    }


    public void setInvoicesWithProducts(List<InvoicesWithProducts> invoicesWithProducts) {
        this.invoicesWithProducts = invoicesWithProducts;
    }


    public Customer getCustomer() {
        return customer;
    }


    public void setCustomer(Customer customer) {
        this.customer = customer;
    }


    public String getIntent() {
        return intent;
    }


    public void setIntent(String intent) {
        this.intent = intent;
    }


    public String getDescription() {
        return description;
    }


    public void setDescription(String description) {
        this.description = description;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getAdminAcceptance() {
        return adminAcceptance;
    }

    public void setAdminAcceptance(String adminAcceptance) {
        this.adminAcceptance = adminAcceptance;
    }

    public String getOnlinePaymentAccount() {
        return onlinePaymentAccount;
    }

    public void setOnlinePaymentAccount(String onlinePaymentAccount) {
        this.onlinePaymentAccount = onlinePaymentAccount;
    }

    @Override
    public double calculation(CalculationService calculationService) {
        return calculationService.getTotalPriceFromProductsList(this, this.invoicesWithProducts);
    }
}
