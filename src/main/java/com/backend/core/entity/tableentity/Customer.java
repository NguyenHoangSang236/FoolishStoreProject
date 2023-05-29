package com.backend.core.entity.tableentity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Objects;

@Entity
@Getter
@Setter
@Table(name = "customers")
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", unique = true)
    int id;

    @Column(name = "Name")
    @NotEmpty(message = "Customer's name can not be empty")
    String name;

    @Column(name = "Email")
    @Email(message = "Invalid email !!")
    String email;

    @Column(name = "Phone_Number")
    @NotEmpty(message = "Customer's phone number can not be empty")
    String phoneNumber;

    @Column(name = "Avatar")
    String image;

    @Column(name = "Country")
    String country;

    @Column(name = "Address")
    String address;

    @Column(name = "City")
    String city;

    @JsonIgnoreProperties("account")
    @OneToOne()
    private Account account;

    @OneToMany(mappedBy = "customer")
    private List<Invoice> invoices;

    @OneToMany(mappedBy = "customer")
    private List<Cart> carts;

    @OneToMany(mappedBy = "customer")
    private List<Comment> comments;



    public Customer() {}


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Customer customer = (Customer) o;
        return id == customer.id && name.equals(customer.name) && email.equals(customer.email) && phoneNumber.equals(customer.phoneNumber) && image.equals(customer.image) && Objects.equals(country, customer.country) && Objects.equals(address, customer.address) && Objects.equals(city, customer.city) && account.equals(customer.account) && Objects.equals(invoices, customer.invoices) && Objects.equals(carts, customer.carts) && Objects.equals(comments, customer.comments);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, email, phoneNumber, image, country, address, city, account, invoices, carts, comments);
    }

    @Override
    public String toString() {
        return "Customer{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", image='" + image + '\'' +
                ", country='" + country + '\'' +
                ", address='" + address + '\'' +
                ", city='" + city + '\'' +
                ", account=" + account +
                ", invoices=" + invoices +
                ", carts=" + carts +
                ", comments=" + comments +
                '}';
    }
}
