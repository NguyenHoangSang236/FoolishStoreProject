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

    @Column(name = "product_management_id", nullable = false)
    int productManagementId;

    @Column(name = "invoice_id", nullable = false)
    int invoiceId;


    public InvoicesWithProductsPrimaryKeys() {}

    public InvoicesWithProductsPrimaryKeys(int productManagementId, int invoiceId) {
        super();
        this.productManagementId = productManagementId;
        this.invoiceId = invoiceId;
    }


    public int getProductManagementId() {
        return productManagementId;
    }

    public void setProductManagementId(int productManagementId) {
        this.productManagementId = productManagementId;
    }

    public int getInvoiceId() {
        return invoiceId;
    }

    public void setInvoiceId(int invoiceId) {
        this.invoiceId = invoiceId;
    }
}
