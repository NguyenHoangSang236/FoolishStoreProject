package com.backend.core.repository;

import java.util.List;

import com.backend.core.entity.renderdto.ProductRenderInfoDTO;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class CustomQueryRepository {
	@PersistenceContext
    private EntityManager entityManager;
    
	
	@SuppressWarnings("unchecked")
	public List<Object[]> getBindingFilteredList(String query) {
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
