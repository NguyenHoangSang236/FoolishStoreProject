package com.backend.core.repository;

import com.backend.core.entities.renderdto.ProductRenderInfoDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductRenderInfoRepository extends JpaRepository<ProductRenderInfoDTO, Integer> {
    @Query(value = "select * from product_info_for_ui limit :limit offset :startLine", nativeQuery = true)
    List<ProductRenderInfoDTO> getAllProducts(@Param("startLine") int startLine, @Param("limit") int limit);;

    @Query(value = "select * from top_8_best_sell_products", nativeQuery = true)
    List<ProductRenderInfoDTO> getTop8BestSellProducts();

    @Query(value = "select * from new_arrival_products", nativeQuery = true)
    List<ProductRenderInfoDTO> get8NewArrivalProducts();

    @Query(value = "select * from hot_discount_products", nativeQuery = true)
    List<ProductRenderInfoDTO> get8HotDiscountProducts();

    @Query(value = "select * from product_info_for_ui where name like %:nameVal% group by name order by product_id limit :limit offset :startLine", nativeQuery = true)
    List<ProductRenderInfoDTO> getProductsByName(@Param("nameVal") String productName, @Param("startLine") int startLine, @Param("limit") int limit);

    @Query(value = "SELECT  `pm`.`id` AS `id`,\n" +
            "        `pm`.`product_id` AS `product_id`,\n" +
            "        `p`.`name` AS `name`,\n" +
            "        `pm`.`size` AS `size`,\n" +
            "        `p`.`selling_price` AS `selling_price`,\n" +
            "        `p`.`discount` AS `discount`,\n" +
            "        `p`.`brand` AS `brand`,\n" +
            "        `pm`.`color` AS `color`,\n" +
            "        `pm`.`available_quantity` AS `available_quantity`,\n" +
            "        `pim`.`image_1` AS `image_1`,\n" +
            "        `pim`.`image_2` AS `image_2`,\n" +
            "        `pim`.`image_3` AS `image_3`,\n" +
            "        `pim`.`image_4` AS `image_4`,\n" +
            "        `p`.`description` AS `description`,\n" +
            "        `p`.`overall_rating` AS `overall_rating`\n" +
            "    FROM\n" +
            "        ((`products` `p`\n" +
            "        JOIN `product_images_management` `pim` ON ((`p`.`id` = `pim`.`product_id`)))\n" +
            "        JOIN `products_management` `pm` ON ((`pm`.`product_id` = `p`.`id`)))\n" +
            "    WHERE `pim`.`color` = `pm`.`color` and pm.product_id = :productId", nativeQuery = true)
    List<ProductRenderInfoDTO> getProductDetails(@Param("productId") int productId);
}
