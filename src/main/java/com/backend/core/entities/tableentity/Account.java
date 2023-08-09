package com.backend.core.entities.tableentity;

import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
@Entity
@Table(name = "login_accounts")
@DynamicInsert
@DynamicUpdate
public class Account implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", unique = true)
    int id;

    @Column(name = "User_Name")
    @NotEmpty(message = "User name can not be null")
    String userName;

    @Column(name = "Password")
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


    public Account() {
    }


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

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(this.role));
    }

    @Override
    public String getUsername() {
        return this.userName;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}

