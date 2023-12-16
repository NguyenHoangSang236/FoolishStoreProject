package com.backend.core.repository.product;

import com.backend.core.entities.tableentity.ProductManagement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductManagementRepository extends JpaRepository<ProductManagement, Integer> {
//	@Query(value = "select * from products_management where product_id = :idVal order by id desc limit 1", nativeQuery = true)
//	ProductManagement getLastestProductManagementInfoByProductId(@Param("idVal") int productId);
//
//
@Query(value = "select size from products_management where product_id = :productId and color = :color", nativeQuery = true)
List<String> getSizeListByProductIdAndColor(@Param("productId") int productId, @Param("color") String color);


    @Query(value = "select * from products_management where id = :idVal", nativeQuery = true)
    ProductManagement getProductManagementById(@Param("idVal") int id);


    @Query(value = "select pm.id from products_management pm join products p on pm.product_id = p.id where pm.color = :colorVal and pm.size = :sizeVal and product_id = :productIdVal", nativeQuery = true)
    int getProductsManagementIdByProductIDAndColorAndSize(@Param("productIdVal") int productId, @Param("colorVal") String color, @Param("sizeVal") String size);

    @Query(value = "select pm.* from products_management pm join products p on pm.product_id = p.id where pm.color = :colorVal and pm.size = :sizeVal and product_id = :productIdVal", nativeQuery = true)
    ProductManagement getProductsManagementByProductIDAndColorAndSize(@Param("productIdVal") int productId, @Param("colorVal") String color, @Param("sizeVal") String size);

    @Query(value = "select pm.* from products_management pm join products p on pm.product_id = p.id where pm.color = :colorVal and product_id = :productIdVal", nativeQuery = true)
    List<ProductManagement> getProductsManagementListByProductIDAndColor(@Param("productIdVal") int productId, @Param("colorVal") String color);
}
