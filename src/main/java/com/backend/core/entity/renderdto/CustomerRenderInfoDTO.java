package com.backend.core.entity.renderdto;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

@Entity
@Table(name = "customer_info_for_ui")
@Getter
@Setter
public class CustomerRenderInfoDTO {
    @Id
    @Column(name = "id", unique = true)
    int id;

    @Column(name = "account_id", unique = true)
    int accountId;

    @Column(name = "user_name", unique = true)
    String userName;

    @Column(name = "password")
    String password;

    @Column(name = "status")
    String status;

    @Column(name = "name")
    String name;

    @Column(name = "email")
    String email;

    @Column(name = "phone_number")
    String phoneNumber;

    @Column(name = "address")
    String address;

    @Column(name = "city")
    String city;

    @Column(name = "country")
    String country;

    @Column(name = "avatar")
    String avatar;


    public CustomerRenderInfoDTO(int id, int accountId, String userName, String password, String status, String name, String email, String phoneNumber, String address, String city, String country, String avatar) {
        this.id = id;
        this.accountId = accountId;
        this.userName = userName;
        this.password = password;
        this.status = status;
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.city = city;
        this.country = country;
        this.avatar = avatar;
    }

    public CustomerRenderInfoDTO() {}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CustomerRenderInfoDTO that = (CustomerRenderInfoDTO) o;
        return id == that.id && accountId == that.accountId && userName.equals(that.userName) && password.equals(that.password) && status.equals(that.status) && name.equals(that.name) && email.equals(that.email) && phoneNumber.equals(that.phoneNumber) && Objects.equals(address, that.address) && Objects.equals(city, that.city) && Objects.equals(country, that.country) && Objects.equals(avatar, that.avatar);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, accountId, userName, password, status, name, email, phoneNumber, address, city, country, avatar);
    }

    @Override
    public String toString() {
        return "CustomerRenderInfoDTO{" +
                "id=" + id +
                ", accountId=" + accountId +
                ", userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                ", status='" + status + '\'' +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", address='" + address + '\'' +
                ", city='" + city + '\'' +
                ", country='" + country + '\'' +
                ", avatar='" + avatar + '\'' +
                '}';
    }
}
