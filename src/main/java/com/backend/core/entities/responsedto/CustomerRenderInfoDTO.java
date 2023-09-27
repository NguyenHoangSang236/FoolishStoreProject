package com.backend.core.entities.responsedto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "customer_info_for_ui")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CustomerRenderInfoDTO {
    @Id
    @Column(name = "id", unique = true)
    int id;

    @Column(name = "account_id", unique = true)
    int accountId;

    @Column(name = "user_name", unique = true)
    String userName;

    @JsonIgnore
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
}
