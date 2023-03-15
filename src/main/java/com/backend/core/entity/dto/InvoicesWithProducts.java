package com.backend.core.entity.dto;

import com.backend.core.entity.tableentity.Invoice;
import com.backend.core.entity.tableentity.Product;
import com.backend.core.entity.embededkey.InvoicesWithProductsPrimaryKeys;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        InvoicesWithProducts that = (InvoicesWithProducts) o;
        return quantity == that.quantity && id.equals(that.id) && product.equals(that.product) && invoice.equals(that.invoice);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, product, invoice, quantity);
    }

    @Override
    public String toString() {
        return "InvoicesWithProducts{" +
                "id=" + id +
                ", product=" + product +
                ", invoice=" + invoice +
                ", quantity=" + quantity +
                '}';
    }

    //    public String formattedProductTotalPrice() {
//        return ValueRender.formatDoubleNumber(this.quantity * (this.product.getPrice() * ((100 - this.product.getDiscount()) / 100)));
//    }`
}
