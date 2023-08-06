package com.backend.core.entities.renderdto;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

@Entity
@Table(name = "staff_info_for_ui")
@Getter
@Setter
public class StaffRenderInfoDTO {
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

    @Column(name = "position")
    String position;

    @Column(name = "avatar")
    String avatar;


    public StaffRenderInfoDTO(int id, int accountId, String userName, String password, String status, String name, String email, String phoneNumber, String position, String avatar) {
        this.id = id;
        this.accountId = accountId;
        this.userName = userName;
        this.password = password;
        this.status = status;
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.position = position;
        this.avatar = avatar;
    }

    public StaffRenderInfoDTO() {
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StaffRenderInfoDTO that = (StaffRenderInfoDTO) o;
        return id == that.id && accountId == that.accountId && userName.equals(that.userName) && password.equals(that.password) && status.equals(that.status) && name.equals(that.name) && email.equals(that.email) && phoneNumber.equals(that.phoneNumber) && position.equals(that.position) && avatar.equals(that.avatar);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, accountId, userName, password, status, name, email, phoneNumber, position, avatar);
    }

    @Override
    public String toString() {
        return "StaffRenderInfoDTO{" +
                "id=" + id +
                ", accountId=" + accountId +
                ", userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                ", status='" + status + '\'' +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", position='" + position + '\'' +
                ", avatar='" + avatar + '\'' +
                '}';
    }
}
