package com.backend.core.entity.account.model;

import com.backend.core.entity.invoice.model.Invoice;
import com.backend.core.entity.refund.model.Refund;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
@Entity
@Table(name = "staffs")
@DynamicInsert
@DynamicUpdate
@NoArgsConstructor
@AllArgsConstructor
public class Staff implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", unique = true)
    int id;

    @Column(name = "Name")
    String name;

    @Column(name = "Birth_Date")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Temporal(TemporalType.DATE)
    Date birthDate;

    @Column(name = "Hometown")
    String hometown;

    @Column(name = "Position")
    String position;

    @Email(message = "Invalid email !!")
    @Column(name = "Email")
    String email;

    @Column(name = "Phone_Number")
    String phoneNumber;

    @Column(name = "Avatar")
    String image;

    @JsonIgnore
    @OneToOne
    @JoinColumn(name = "account_id", referencedColumnName = "id")
    private Account account;

    @JsonIgnore
    @OneToMany(mappedBy = "staff")
    private List<Invoice> invoices;

    @JsonIgnore
    @OneToMany(mappedBy = "staff")
    private List<Refund> refunds;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Staff staff = (Staff) o;
        return id == staff.id && Objects.equals(name, staff.name) && Objects.equals(birthDate, staff.birthDate) && Objects.equals(hometown, staff.hometown) && Objects.equals(position, staff.position) && Objects.equals(email, staff.email) && Objects.equals(phoneNumber, staff.phoneNumber) && Objects.equals(image, staff.image) && Objects.equals(account, staff.account) && Objects.equals(invoices, staff.invoices);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, birthDate, hometown, position, email, phoneNumber, image, account, invoices);
    }

    @Override
    public String toString() {
        return "Staff{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", birthDate=" + birthDate +
                ", hometown='" + hometown + '\'' +
                ", position='" + position + '\'' +
                ", email='" + email + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", image='" + image + '\'' +
                ", account=" + account +
                ", invoices=" + invoices +
                '}';
    }
}

