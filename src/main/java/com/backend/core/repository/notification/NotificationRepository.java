package com.backend.core.repository.notification;

import com.backend.core.entities.tableentity.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificationRepository extends JpaRepository<Notification, Integer> {

}
