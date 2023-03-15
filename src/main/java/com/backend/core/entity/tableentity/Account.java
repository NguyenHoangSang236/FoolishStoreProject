package com.backend.core.entity.tableentity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import java.util.Objects;

@Getter
@Setter
@Entity
@Table(name = "login_accounts")
@DynamicInsert
@DynamicUpdate
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", unique = true)
    int id;

    @Column(name = "User_Name")
    @NotEmpty(message = "User name can not be null")
    String userName;

    @Column(name = "Password")
    @NotEmpty(message = "Password can not be null")
    String password;

    @Column(name = "Role")
    String role;

    @Column(name = "Status")
    String status;

    @OneToOne(mappedBy = "account", cascade = CascadeType.ALL)
    @Valid
    Customer customer;

    @OneToOne(mappedBy = "account", cascade = CascadeType.ALL)
    @Valid
    Staff staff;


    public Account() {}


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Account account = (Account) o;
        return id == account.id && userName.equals(account.userName) && password.equals(account.password) && role.equals(account.role) && status.equals(account.status) && Objects.equals(customer, account.customer) && Objects.equals(staff, account.staff);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, userName, password, role, status, customer, staff);
    }

    @Override
    public String toString() {
        return "Account{" +
                "id=" + id +
                ", userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                ", role='" + role + '\'' +
                ", status='" + status + '\'' +
                ", customer=" + customer +
                ", staff=" + staff +
                '}';
    }
}

