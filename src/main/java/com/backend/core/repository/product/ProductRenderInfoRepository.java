package com.backend.core.repository.product;

import com.backend.core.entities.responsedto.ProductRenderInfoDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductRenderInfoRepository extends JpaRepository<ProductRenderInfoDTO, Integer> {
    @Query(value = "select * from product_info_for_ui limit :limit offset :startLine", nativeQuery = true)
    List<ProductRenderInfoDTO> getAllProducts(@Param("startLine") int startLine, @Param("limit") int limit);

    @Query(value = "select * from top_8_best_sell_products", nativeQuery = true)
    List<ProductRenderInfoDTO> getTop8BestSellProducts();

    @Query(value = "select * from new_arrival_products", nativeQuery = true)
    List<ProductRenderInfoDTO> get8NewArrivalProducts();

    @Query(value = "select * from hot_discount_products", nativeQuery = true)
    List<ProductRenderInfoDTO> get8HotDiscountProducts();

    @Query(value = "select * from product_info_for_ui where name like %:nameVal% group by name order by product_id limit :limit offset :startLine", nativeQuery = true)
    List<ProductRenderInfoDTO> getProductsByName(@Param("nameVal") String productName, @Param("startLine") int startLine, @Param("limit") int limit);

    @Query(value = "select * from product_info_for_ui where color = :colorVal and product_id = :idVal", nativeQuery = true)
    ProductRenderInfoDTO getProductByIdAndColor(@Param("idVal") int id, @Param("colorVal") String color);

    @Query(value = "select * from product_info_for_ui where product_id = :productId", nativeQuery = true)
    List<ProductRenderInfoDTO> getProductDetails(@Param("productId") int productId);
}
