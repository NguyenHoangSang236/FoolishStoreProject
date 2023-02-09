package com.backend.core.entity.embededkey;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public class InvoicesWithProductsPrimaryKeys implements Serializable {
    /**
     *
     */
    private static final long serialVersionUID = -3919178711820914812L;

    @Column(name = "product_id", nullable = false)
    int productId;

    @Column(name = "invoice_id", nullable = false)
    int invoiceId;


    public InvoicesWithProductsPrimaryKeys() {}

    public InvoicesWithProductsPrimaryKeys(int productId, int invoiceId) {
        super();
        this.productId = productId;
        this.invoiceId = invoiceId;
    }


    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public int getInvoiceId() {
        return invoiceId;
    }

    public void setInvoiceId(int invoiceId) {
        this.invoiceId = invoiceId;
    }
}
