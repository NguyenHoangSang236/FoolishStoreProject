package com.backend.core.repository;

import com.backend.core.entities.renderdto.StaffRenderInfoDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface StaffRenderInfoRepository extends JpaRepository<StaffRenderInfoDTO, Integer> {
    @Query(value = "select * from staff_info_for_ui where user_name = :userNameVal and password = :passwordVal", nativeQuery = true)
    StaffRenderInfoDTO getStaffInfoByUserNameAndPassword(@Param("userNameVal") String userName, @Param("passwordVal") String password);
}
