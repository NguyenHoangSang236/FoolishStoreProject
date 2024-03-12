package com.backend.core.entity.invoice.model;

import com.backend.core.entity.invoice.key.InvoicesWithProductsPrimaryKeys;
import com.backend.core.entity.product.model.ProductManagement;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashMap;
import java.util.Map;
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

    public Map<String, Objects> getGhnItemJson() {
        Map map = new HashMap<>();

        map.put("name", this.productManagement.getProduct().getName());
        map.put("quantity", this.quantity);
        map.put("height", this.productManagement.getProduct().getHeight());
        map.put("weight", this.productManagement.getProduct().getWeight());
        map.put("length", this.productManagement.getProduct().getLength());
        map.put("width", this.productManagement.getProduct().getWidth());

        return map;
    }
}
