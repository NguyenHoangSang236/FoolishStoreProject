package com.backend.core.repository.customQuery;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public class CustomQueryRepository {
    @PersistenceContext
    private EntityManager entityManager;


    public CustomQueryRepository() {
    }

    public CustomQueryRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @SuppressWarnings("unchecked")
    public <T> List<T> getBindingFilteredList(String query, Class<T> entity) {
        return this.entityManager.createNativeQuery(query, entity).getResultList();
    }


//	@Transactional
//	public void insertCatalogWithProducts(int catalogId, String productName) {
//	    this.entityManager.createNativeQuery("INSERT INTO catalog_with_products (catalog_id, product_name) VALUES (?,?)")
//	      .setParameter(1, catalogId)
//	      .setParameter(2, productName)
//	      .executeUpdate();
//	}


    @Transactional
    public void deleteCartById(int cartId) {
        this.entityManager.createNativeQuery("DELETE FROM cart WHERE (`id` = ?)")
                .setParameter(1, cartId)
                .executeUpdate();
    }


    @Transactional
    public void deleteCatalogsWithProductsById(int catalogId) {
        this.entityManager.createNativeQuery("DELETE FROM catalogs_with_products WHERE (`catalog_id` = ?)")
                .setParameter(1, catalogId)
                .executeUpdate();
    }
}
