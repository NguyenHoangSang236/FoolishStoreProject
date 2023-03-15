package com.backend.core.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.backend.core.entity.tableentity.ProductManagement;

@Repository
public interface ProductManagementRepository extends JpaRepository<ProductManagement, Integer>{
	@Query(value = "select * from products_management where product_id = :idVal order by id desc limit 1", nativeQuery = true)
	ProductManagement getLastestProductManagementInfoByProductId(@Param("idVal") int productId);
	
	
	@Query(value = "select id from products_management order by id desc limit 1", nativeQuery = true)
	int getLastProductManagementId();


	@Query(value = "select * from products_management where id = :idVal", nativeQuery = true)
	ProductManagement getProductManagementById(@Param("idVal") int id);


	@Query(value = "select pm.id from products_management pm join products p on pm.product_id = p.id where color = :colorVal and size = :sizeVal and product_id = :productIdVal", nativeQuery = true)
	int getPrductsManagementIdByProductIDAndColorAndSize(@Param("productIdVal") int productId, @Param("colorVal") String color, @Param("sizeVal") String size);
}
