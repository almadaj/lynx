package com.schoolar.lynx.notification.event;

import com.schoolar.lynx.notification.dto.NotificationResponse;

import java.util.UUID;

public record NotificationCreatedForUserEvent(
        UUID userId,
        NotificationResponse notification
) {}