package com.schoolar.lynx.domain.mapper;

import com.schoolar.lynx.domain.dto.NotificationResponse;
import com.schoolar.lynx.domain.model.Notification;
import com.schoolar.lynx.domain.model.NotificationUser;


public class NotificationMapper {
    public static NotificationResponse toDTO(NotificationUser delivery) {
        if (delivery == null) return null;

        Notification notification = delivery.getNotification();

        return new NotificationResponse(
                notification.getId(),
                notification.getTitle(),
                notification.getMessage(),
                notification.getType(),
                notification.getCreatedAt(),
                delivery.getReadAt()
        );
    }
}
