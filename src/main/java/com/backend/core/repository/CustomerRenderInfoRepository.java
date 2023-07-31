package com.backend.core.repository;

import com.backend.core.entities.renderdto.CustomerRenderInfoDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRenderInfoRepository extends JpaRepository<CustomerRenderInfoDTO, Integer> {
    @Query(value = "select * from customer_info_for_ui where user_name = :userNameVal and password = :passwordVal", nativeQuery = true)
    CustomerRenderInfoDTO getCustomerInfoByUserNameAndPassword(@Param("userNameVal") String userName, @Param("passwordVal") String password);
}
