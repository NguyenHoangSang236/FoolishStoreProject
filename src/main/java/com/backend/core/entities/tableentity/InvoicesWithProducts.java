package com.backend.core.entities.tableentity;

import com.backend.core.entities.embededkey.InvoicesWithProductsPrimaryKeys;
import jakarta.persistence.*;
import lombok.*;

import java.util.Objects;

@Entity
@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "invoices_with_products")
public class InvoicesWithProducts {
    @EmbeddedId
    InvoicesWithProductsPrimaryKeys id;

    @ManyToOne
    @MapsId("productId")
    @JoinColumn(name = "product_management_id")
    ProductManagement productManagement;

    @ManyToOne
    @MapsId("invoiceId")
    @JoinColumn(name = "invoice_id")
    Invoice invoice;

    @Column(name = "quantity")
    int quantity;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        InvoicesWithProducts that = (InvoicesWithProducts) o;
        return quantity == that.quantity && id.equals(that.id) && productManagement.equals(that.productManagement) && invoice.equals(that.invoice);
    }


    @Override
    public int hashCode() {
        return Objects.hash(id, productManagement, invoice, quantity);
    }

    @Override
    public String toString() {
        return "InvoicesWithProducts{" +
                "id=" + id +
                ", productManagement=" + productManagement +
                ", invoice=" + invoice +
                ", quantity=" + quantity +
                '}';
    }


    //    public String formattedProductTotalPrice() {
//        return ValueRender.formatDoubleNumber(this.quantity * (this.product.getPrice() * ((100 - this.product.getDiscount()) / 100)));
//    }`
}
