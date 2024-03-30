package com.backend.core.entity.invoice.key;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Embeddable
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class InvoicesWithProductsPrimaryKeys implements Serializable {
    private static final long serialVersionUID = -3919178711820914812L;

    @Column(name = "product_management_id", nullable = false)
    int productManagementId;

    @Column(name = "invoice_id", nullable = false)
    int invoiceId;
}
