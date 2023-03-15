package com.backend.core.repository;

import java.util.List;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class ProductCustomQueryRepository {
	@PersistenceContext
    private EntityManager entityManager;
    
	
	@SuppressWarnings("unchecked")
	public List<Object[]> getFilteredProducts(String query) {
        return this.entityManager.createNativeQuery(query).getResultList();
    }
	
	
	@Transactional
	public void insertCatalogWithProducts(int catalogId, String productName) {
	    entityManager.createNativeQuery("INSERT INTO catalog_with_products (catalog_id, product_name) VALUES (?,?)")
	      .setParameter(1, catalogId)
	      .setParameter(2, productName)
	      .executeUpdate();
	}
	
	
	
}
