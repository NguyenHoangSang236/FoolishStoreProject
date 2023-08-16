package com.backend.core.repository.product;

import com.backend.core.entities.tableentity.ProductImagesManagement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductImagesManagementRepository extends JpaRepository<ProductImagesManagement, Integer> {
}
