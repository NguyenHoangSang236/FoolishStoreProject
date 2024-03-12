package com.backend.core.infrastructure.business.firebase.repository;

import com.backend.core.entity.delivery.model.DeviceFcmToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface DeviceFcmTokenRepository extends JpaRepository<DeviceFcmToken, Integer> {
    @Query(value = "select * from device_fcm_token where phone_fcm_token = :token", nativeQuery = true)
    DeviceFcmToken getDeviceFcmToken(@Param("token") String token);
}
