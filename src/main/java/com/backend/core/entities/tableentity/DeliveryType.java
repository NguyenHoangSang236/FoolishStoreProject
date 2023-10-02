package com.backend.core.entities.tableentity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

@Entity
@Getter
@Setter
@Table(name = "delivery_type")
@DynamicInsert
@DynamicUpdate
public class DeliveryType {
    @Id
    @Column(name = "id", unique = true)
    int id;

    @Column(name = "name")
    String name;

    @Column(name = "price")
    double price;

    @Column(name = "condition")
    String condition;
}
