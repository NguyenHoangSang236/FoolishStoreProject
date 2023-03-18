package com.backend.core.repository;

import com.backend.core.entity.renderdto.ProductRenderInfoDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProductRenderInfoRepository extends JpaRepository<ProductRenderInfoDTO, Integer> {
    @Query(value = "select * from product_info_for_ui", nativeQuery = true)
    List<ProductRenderInfoDTO> getAllProducts();

    @Query(value = "select * from top_8_best_sell_products", nativeQuery = true)
    List<ProductRenderInfoDTO> getTop8BestSellProducts();

    @Query(value = "select * from product_info_for_ui group by name order by id desc limit 8", nativeQuery = true)
    List<ProductRenderInfoDTO> get8NewArrivalProducts();

    @Query(value = "select * from product_info_for_ui where discount > 0 group by name order by discount desc limit 8", nativeQuery = true)
    List<ProductRenderInfoDTO> get8HotDiscountProducts();
}
