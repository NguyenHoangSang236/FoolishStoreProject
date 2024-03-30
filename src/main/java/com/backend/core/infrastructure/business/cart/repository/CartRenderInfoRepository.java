package com.backend.core.infrastructure.business.cart.repository;

import com.backend.core.infrastructure.business.cart.dto.CartRenderInfoDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartRenderInfoRepository extends JpaRepository<CartRenderInfoDTO, Integer> {
    @Query(value = "select * from cart_item_info_for_ui where customer_id = :idVal and buying_status = 'NOT_BOUGHT_YET' limit :limit offset :startLine", nativeQuery = true)
    List<CartRenderInfoDTO> getFullCartListByCustomerId(@Param("idVal") int id, @Param("startLine") int startLine, @Param("limit") int limit);

    @Query(value = "select * from cart_item_info_for_ui where customer_id = :idVal and buying_status = 'NOT_BOUGHT_YET' and select_status = 1", nativeQuery = true)
    List<CartRenderInfoDTO> getSelectedCartItemListByCustomerId(@Param("idVal") int id);

    @Query(value = "select p.height \n" +
            "from cart_item_info_for_ui cartItem join products p on cartItem.product_id = p.id \n" +
            "where p.id = :productId \n" +
            "group by p.height",
            nativeQuery = true)
    int getCartItemHeight(@Param("productId") int id);

    @Query(value = "select p.width \n" +
            "from cart_item_info_for_ui cartItem join products p on cartItem.product_id = p.id \n" +
            "where p.id = :productId \n" +
            "group by p.width",
            nativeQuery = true)
    int getCartItemWidth(@Param("productId") int id);

    @Query(value = "select p.weight \n" +
            "from cart_item_info_for_ui cartItem join products p on cartItem.product_id = p.id \n" +
            "where p.id = :productId \n" +
            "group by p.weight",
            nativeQuery = true)
    int getCartItemWeight(@Param("productId") int id);

    @Query(value = "select p.length \n" +
            "from cart_item_info_for_ui cartItem join products p on cartItem.product_id = p.id \n" +
            "where p.id = :productId \n" +
            "group by p.length",
            nativeQuery = true)
    int getCartItemLength(@Param("productId") int id);
}
