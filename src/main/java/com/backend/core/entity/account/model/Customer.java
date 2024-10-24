package com.backend.core.entity.account.model;

import com.backend.core.entity.cart.model.Cart;
import com.backend.core.entity.comment.model.Comment;
import com.backend.core.entity.comment.model.CommentLike;
import com.backend.core.entity.invoice.model.Invoice;
import com.backend.core.infrastructure.business.account.dto.CustomerRenderInfoDTO;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

@Entity
@Getter
@Setter
@Table(name = "customers")
@DynamicInsert
@DynamicUpdate
@AllArgsConstructor
@NoArgsConstructor
public class Customer implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true)
    int id;

    @Column(name = "name")
    @NotEmpty(message = "Customer's name can not be empty")
    String name;

    @Column(name = "email")
    @Email(message = "Invalid email !!")
    String email;

    @Column(name = "phone_Number")
    @NotEmpty(message = "Customer's phone number can not be empty")
    String phoneNumber;

    @Column(name = "avatar")
    String image;

    @Column(name = "country")
    String country;

    @Column(name = "address")
    String address;

    @Column(name = "city")
    String city;

    @JsonIgnore
    @OneToOne
    @JoinColumn(name = "account_id", referencedColumnName = "id")
    private Account account;

    @JsonIgnore
    @OneToMany(mappedBy = "customer", cascade = {CascadeType.ALL})
    private List<Invoice> invoices;

    @JsonIgnore
    @OneToMany(mappedBy = "customer", cascade = {CascadeType.ALL})
    private List<Cart> carts;

    @JsonIgnore
    @OneToMany(mappedBy = "customer", cascade = {CascadeType.ALL})
    private List<CommentLike> commentLikes;

    @JsonIgnore
    @JsonBackReference
    @OneToMany(mappedBy = "customer", cascade = {CascadeType.ALL})
    private List<Comment> comments;


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


    public void setCustomerInfoFromRenderInfo(CustomerRenderInfoDTO customerRenderDTO) {
        if (customerRenderDTO.getName() != null && !customerRenderDTO.getName().isEmpty() && !customerRenderDTO.getName().isBlank()) {
            this.setName(customerRenderDTO.getName());
        }

        if (customerRenderDTO.getEmail() != null && !customerRenderDTO.getEmail().isEmpty() && !customerRenderDTO.getEmail().isBlank()) {
            this.setEmail(customerRenderDTO.getEmail());
        }

        if (customerRenderDTO.getPhoneNumber() != null && !customerRenderDTO.getPhoneNumber().isEmpty() && !customerRenderDTO.getPhoneNumber().isBlank()) {
            this.setPhoneNumber(customerRenderDTO.getPhoneNumber());
        }

        if (customerRenderDTO.getCountry() != null && !customerRenderDTO.getCountry().isEmpty() && !customerRenderDTO.getCountry().isBlank()) {
            this.setCountry(customerRenderDTO.getCountry());
        }

        if (customerRenderDTO.getCity() != null && !customerRenderDTO.getCity().isEmpty() && !customerRenderDTO.getCity().isBlank()) {
            this.setCity(customerRenderDTO.getCity());
        }

        if (customerRenderDTO.getAddress() != null && !customerRenderDTO.getAddress().isEmpty() && !customerRenderDTO.getAddress().isBlank()) {
            this.setAddress(customerRenderDTO.getAddress());
        }
    }
}
