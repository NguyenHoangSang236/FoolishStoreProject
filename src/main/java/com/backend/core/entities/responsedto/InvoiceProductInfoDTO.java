package com.backend.core.entities.responsedto;

import com.backend.core.entities.embededkey.InvoicesWithProductsPrimaryKeys;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "invoice_with_products_info_for_ui")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class InvoiceProductInfoDTO {
    @JsonIgnore
    @EmbeddedId
    InvoicesWithProductsPrimaryKeys id;

    @Column(name = "product_id")
    int productId;

    @JsonIgnore
    @Column(name = "customer_id")
    int customerId;

    @Column(name = "name")
    String name;

    @Column(name = "color")
    String color;

    @Column(name = "size")
    String size;

    @Column(name = "selling_price")
    double sellingPrice;

    @Column(name = "discount")
    double discount;

    @Column(name = "quantity")
    int quantity;

    @Column(name = "image_1")
    String image1;
}
