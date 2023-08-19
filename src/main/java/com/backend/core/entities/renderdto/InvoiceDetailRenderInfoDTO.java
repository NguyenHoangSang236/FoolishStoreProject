package com.backend.core.entities.renderdto;

import com.backend.core.entities.embededkey.InvoicesWithProductsPrimaryKeys;
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
public class InvoiceDetailRenderInfoDTO {
    @EmbeddedId
    InvoicesWithProductsPrimaryKeys id;

    @Column(name = "product_id")
    int productId;

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

    @Column(name = "overall_rating")
    int overallRating;

    @Column(name = "image_1")
    String image1;

    @Column(name = "image_2")
    String image2;

    @Column(name = "image_3")
    String image3;

    @Column(name = "image_4")
    String image4;
}
