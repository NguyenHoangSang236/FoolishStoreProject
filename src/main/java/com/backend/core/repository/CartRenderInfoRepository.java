package com.backend.core.repository;

import com.backend.core.entity.renderdto.CartRenderInfoDTO;
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
}
