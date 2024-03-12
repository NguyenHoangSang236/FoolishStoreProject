package com.backend.core.infrastructure.business.product.repository;

import com.backend.core.entity.product.model.ProductImagesManagement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductImagesManagementRepository extends JpaRepository<ProductImagesManagement, Integer> {
}
