package com.backend.core.infrastructure.business.product.repository;

import com.backend.core.entity.product.model.ProductImagesManagement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductImagesManagementRepository extends JpaRepository<ProductImagesManagement, Integer> {
    @Query(value = "select * from product_images_management where product_id = :productId and color = :color", nativeQuery = true)
    ProductImagesManagement getProductImagesByProductIdAndColor(@Param("productId") int productId, @Param("color") String color);
}
