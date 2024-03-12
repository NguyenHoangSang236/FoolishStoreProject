package com.backend.core.infrastructure.business.invoice.repository;

import com.backend.core.entity.invoice.model.InvoicesWithProducts;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;

@Repository
public class InvoicesWithProductsRepository {
    @PersistenceContext
    EntityManager entityManager;


    @Transactional
    public void insertInvoicesWithProducts(InvoicesWithProducts invoicesWithProducts) {
        entityManager.createNativeQuery("INSERT INTO invoices_with_products (invoice_id, product_management_id, quantity) VALUES (?,?,?)")
                .setParameter(1, invoicesWithProducts.getId().getInvoiceId())
                .setParameter(2, invoicesWithProducts.getId().getProductManagementId())
                .setParameter(3, invoicesWithProducts.getQuantity())
                .executeUpdate();
    }

    @Transactional
    public void removeInvoicesWithProducts(InvoicesWithProducts invoicesWithProducts) {
        entityManager.remove(invoicesWithProducts);
    }
}
