package com.backend.core.entity.dto;

import com.backend.core.entity.Invoice;
import com.backend.core.entity.Product;
import com.backend.core.entity.embededkey.InvoicesWithProductsPrimaryKeys;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Entity
@Data
@Getter
@Setter
@Table(name = "invoices_with_products")
public class InvoicesWithProducts {
    @EmbeddedId
    InvoicesWithProductsPrimaryKeys id;

    @ManyToOne
    @MapsId("productId")
    @JoinColumn(name = "product_id")
    Product product;

    @ManyToOne
    @MapsId("invoiceId")
    @JoinColumn(name = "invoice_id")
    Invoice invoice;

    @Column(name = "quantity")
    int quantity;


    public InvoicesWithProducts() {}

    public InvoicesWithProducts(InvoicesWithProductsPrimaryKeys id, Product product, Invoice invoice, int quantity) {
        super();
        this.id = id;
        this.product = product;
        this.invoice = invoice;
        this.quantity = quantity;
    }


//    public String formattedProductTotalPrice() {
//        return ValueRender.formatDoubleNumber(this.quantity * (this.product.getPrice() * ((100 - this.product.getDiscount()) / 100)));
//    }`
}
