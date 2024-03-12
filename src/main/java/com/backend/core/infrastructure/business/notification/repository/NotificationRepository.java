package com.backend.core.infrastructure.business.notification.repository;

import com.backend.core.entity.notification.model.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface NotificationRepository extends JpaRepository<Notification, Integer> {
    @Query(value = "select * from notification where id = :id", nativeQuery = true)
    Notification getNotificationById(@Param("id") int id);

}
