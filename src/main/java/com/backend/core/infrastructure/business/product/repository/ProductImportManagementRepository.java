package com.backend.core.infrastructure.business.product.repository;

import com.backend.core.entity.product.model.ProductImportManagement;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductImportManagementRepository extends JpaRepository<ProductImportManagement, Integer> {
}
