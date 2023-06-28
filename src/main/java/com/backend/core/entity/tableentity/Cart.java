package com.backend.core.entity.tableentity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

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
    String buyingStatus;

    @Column(name = "Select_status")
    int selectStatus;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "customer_id")
    Customer customer;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "product_management_id")
    ProductManagement productManagement;


    public Cart() {}

    public Cart(Customer customer, ProductManagement productManagement, int quantity, String buyingStatus, int selectStatus) {
        this.quantity = quantity;
        this.buyingStatus = buyingStatus;
        this.customer = customer;
        this.productManagement = productManagement;
        this.selectStatus = selectStatus;
    }

    public Cart(Customer customer, ProductManagement productManagement, int quantity) {
        this.quantity = quantity;
        this.customer = customer;
        this.productManagement = productManagement;
    }

    public double totalPrice() {
        double result = 0;

        int quantity = this.quantity;
        double productPrice = this.productManagement.getProduct().getSellingPrice();
        result += quantity * productPrice;

        return result;
    }

    public void addQuantity(int quant) {
        this.quantity += quant;
    }

//    public String formatedTotalPrice() {
//        return ValueRender.formatDoubleNumber(this.totalPrice());
//    }
//
//    public String formatedPrice() {
//        return ValueRender.formatDoubleNumber(this.product.getPrice());
//    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cart cart = (Cart) o;
        return id == cart.id && quantity == cart.quantity && selectStatus == cart.selectStatus && Objects.equals(buyingStatus, cart.buyingStatus) && Objects.equals(customer, cart.customer) && Objects.equals(productManagement, cart.productManagement);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, quantity, buyingStatus, selectStatus, customer, productManagement);
    }

    @Override
    public String toString() {
        return "Cart{" +
                "id=" + id +
                ", quantity=" + quantity +
                ", buyingStatus='" + buyingStatus + '\'' +
                ", selectStatus=" + selectStatus +
                ", customer=" + customer +
                ", productManagement=" + productManagement +
                '}';
    }
}
