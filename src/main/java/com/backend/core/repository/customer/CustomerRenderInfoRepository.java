package com.backend.core.repository.customer;

import com.backend.core.entities.renderdto.CustomerRenderInfoDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerRenderInfoRepository extends JpaRepository<CustomerRenderInfoDTO, Integer> {
    @Query(value = "select * from customer_info_for_ui where user_name = :userNameVal", nativeQuery = true)
    CustomerRenderInfoDTO getCustomerInfoByUserName(@Param("userNameVal") String userName);

    @Query(value = "select * from customer_info_for_ui where account_id = :idVal", nativeQuery = true)
    CustomerRenderInfoDTO getCustomerInfoByAccountId(@Param("idVal") int id);

    @Query(value = "select * from customer_info_for_ui limit :limit offset :startLine", nativeQuery = true)
    List<CustomerRenderInfoDTO> getCustomerInfoList(@Param("startLine") int startLine, @Param("limit") int limit);
}
