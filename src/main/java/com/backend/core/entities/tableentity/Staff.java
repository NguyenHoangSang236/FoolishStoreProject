package com.backend.core.entities.tableentity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
@Entity
@Table(name = "staffs")
@DynamicInsert
@DynamicUpdate
public class Staff {
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
    @JoinColumn(name = "Account_ID", referencedColumnName = "id")
    private Account account;

    @JsonIgnore
    @OneToMany(mappedBy = "staff")
    private List<Invoice> invoices;

    @JsonIgnore
    @OneToMany(mappedBy = "staff")
    private List<Delivery> deliveries;


    public Staff() {
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Staff staff = (Staff) o;
        return id == staff.id && Objects.equals(name, staff.name) && Objects.equals(birthDate, staff.birthDate) && Objects.equals(hometown, staff.hometown) && Objects.equals(position, staff.position) && Objects.equals(email, staff.email) && Objects.equals(phoneNumber, staff.phoneNumber) && Objects.equals(image, staff.image) && Objects.equals(account, staff.account) && Objects.equals(invoices, staff.invoices) && Objects.equals(deliveries, staff.deliveries);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, birthDate, hometown, position, email, phoneNumber, image, account, invoices, deliveries);
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
                ", deliveries=" + deliveries +
                '}';
    }
}

