package com.backend.core.entity.account.model;

import com.backend.core.entity.delivery.model.DeviceFcmToken;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "login_accounts")
@DynamicInsert
@DynamicUpdate
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Account implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true)
    int id;

    @Column(name = "user_name")
    @NotEmpty(message = "User name can not be null")
    String userName;

    @Column(name = "password")
    String password;

    @Column(name = "role")
    String role;

    @Column(name = "status")
    String status;

    @JsonIgnore
    @Column(name = "current_jwt")
    String currentJwt;

    @OneToOne(mappedBy = "account", cascade = CascadeType.ALL)
    Customer customer;

    @OneToOne(mappedBy = "account", cascade = CascadeType.ALL)
    Staff staff;

    @OneToMany(mappedBy = "account", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    List<DeviceFcmToken> deviceFcmTokens;

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

