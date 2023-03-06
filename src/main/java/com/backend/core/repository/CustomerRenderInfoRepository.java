package com.backend.core.repository;

import com.backend.core.entity.Account;
import com.backend.core.entity.renderdto.CartRenderInfoDTO;
import com.backend.core.entity.renderdto.CustomerRenderInfoDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerRenderInfoRepository extends JpaRepository<CustomerRenderInfoDTO, Integer> {
    @Query(value = "select * from customer_info_for_ui where user_name = :userNameVal and password = :passwordVal", nativeQuery = true)
    CustomerRenderInfoDTO getCustomerInfoByUserNameAndPassword(@Param("userNameVal") String userName, @Param("passwordVal") String password);
}
