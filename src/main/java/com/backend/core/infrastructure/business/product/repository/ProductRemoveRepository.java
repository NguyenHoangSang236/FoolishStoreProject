package com.backend.core.infrastructure.business.product.repository;


import com.backend.core.entity.product.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRemoveRepository extends JpaRepository<Product, Integer> {
//    @Modifying
//    @Transactional
//    @Query(value = "delete from catalog_with_products where product_name = :nameVal", nativeQuery = true)
//    void deleteProductFromCatalogWithProducts(@Param("nameVal") String productName);
//
//
//    @Modifying
//    @Transactional
//    @Query(value = "delete from comments where product_id = :idVal", nativeQuery = true)
//    void deleteFromProductComments(@Param("idVal") int id);
//
//
//    @Modifying
//    @Transactional
//    @Query(value = "delete from products_management where product_id = :idVal", nativeQuery = true)
//    void deleteFromProductProductsManagement(@Param("idVal") int id);
}
