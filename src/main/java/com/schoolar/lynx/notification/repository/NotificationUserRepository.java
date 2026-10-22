package com.schoolar.lynx.notification.repository;

import com.schoolar.lynx.domain.model.NotificationUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface NotificationUserRepository extends JpaRepository<NotificationUser, UUID> {
    List<NotificationUser> findAllByUserIdOrderByNotificationCreatedAtDesc(
            UUID userId
    );
}
