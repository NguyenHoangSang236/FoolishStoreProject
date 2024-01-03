package com.backend.core.repository.product;

import com.backend.core.entities.responsedto.AuthenProductRenderInfoDTO;
import com.backend.core.entities.responsedto.ProductRenderInfoDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AuthenProductRenderInfoRepository extends JpaRepository<AuthenProductRenderInfoDTO, Integer> {
    @Query(value = "select * from product_full_info_for_ui where product_id = :productId", nativeQuery = true)
    List<AuthenProductRenderInfoDTO> getAuthenProductFullDetails(@Param("productId") int productId);
}
