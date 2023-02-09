package com.backend.core.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "cart")
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", unique = true)
    int id;

    @Column(name = "Quantity")
    int quantity;

    @Column(name = "Buying_Status")
    int buyingStatus;

    @Column(name = "select_status")
    int selectStatus;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    Customer customer;

    @ManyToOne
    @JoinColumn(name = "product_id")
    Product product;


    public Cart() {}

    public Cart(int id, int quantity, int buyingStatus, int selectStatus, Customer customer, Product product) {
        super();
        this.id = id;
        this.quantity = quantity;
        this.buyingStatus = buyingStatus;
        this.selectStatus = selectStatus;
        this.customer = customer;
        this.product = product;
    }

    public Cart(int id, int quantity, int buyingStatus, int selectStatus) {
        super();
        this.id = id;
        this.quantity = quantity;
        this.buyingStatus = buyingStatus;
        this.selectStatus = selectStatus;
    }




//    public double totalPrice() {
//        double result = 0;
//
//        int quantity = this.quantity;
//        double productPrice = this.product.getPrice();
//        result += quantity * productPrice;
//
//        return result;
//    }

//    public String formatedTotalPrice() {
//        return ValueRender.formatDoubleNumber(this.totalPrice());
//    }
//
//    public String formatedPrice() {
//        return ValueRender.formatDoubleNumber(this.product.getPrice());
//    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getBuyingStatus() {
        return buyingStatus;
    }

    public void setBuyingStatus(int buyingStatus) {
        this.buyingStatus = buyingStatus;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public int getSelectStatus() {
        return selectStatus;
    }

    public void setSelectStatus(int selectStatus) {
        this.selectStatus = selectStatus;
    }
}
