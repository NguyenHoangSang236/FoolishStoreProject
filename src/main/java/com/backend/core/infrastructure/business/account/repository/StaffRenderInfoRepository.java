package com.backend.core.infrastructure.business.account.repository;

import com.backend.core.infrastructure.business.account.dto.StaffRenderInfoDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StaffRenderInfoRepository extends JpaRepository<StaffRenderInfoDTO, Integer> {
    @Query(value = "select * from staff_info_for_ui where user_name = :userNameVal", nativeQuery = true)
    StaffRenderInfoDTO getStaffInfoByUserName(@Param("userNameVal") String userName);

    @Query(value = "select * from staff_info_for_ui where account_id = :idVal", nativeQuery = true)
    StaffRenderInfoDTO getStaffInfoByAccountId(@Param("idVal") int id);

    @Query(value = "select * from staff_info_for_ui where position = 'admin' limit :limit offset :startLine", nativeQuery = true)
    List<StaffRenderInfoDTO> getAdminInfoList(@Param("startLine") int startLine, @Param("limit") int limit);

    @Query(value = "select * from staff_info_for_ui where position = 'shipper' limit :limit offset :startLine", nativeQuery = true)
    List<StaffRenderInfoDTO> getShipperInfoList(@Param("startLine") int startLine, @Param("limit") int limit);
}
