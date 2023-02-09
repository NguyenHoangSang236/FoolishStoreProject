package com.backend.core.entity;

import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

@Getter
@Setter
@Entity
@Table(name = "login_accounts")
@DynamicInsert
@DynamicUpdate
@Data
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
    Staff staff;


    public Account() {}

    public Account(String name, String password) {
        this.userName = name;
        this.password = password;
    }

    public Account(int id, String name, String pass, String role) {
        this.id = id;
        this.userName = name;
        this.password = pass;
        this.role = role;
    }

    public Account(String userName, String password, Customer customer) {
        this.userName = userName;
        this.password = password;
        this.customer = customer;
    }

    public Account(String name, String pass, String role) {
        this.userName = name;
        this.password = pass;
        this.role = role;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Staff getStaff() {
        return staff;
    }

    public void setStaff(Staff staff) {
        this.staff = staff;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}

