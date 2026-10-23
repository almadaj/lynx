package com.schoolar.lynx.notification.event;

import com.schoolar.lynx.domain.dto.NotificationResponse;

import java.util.UUID;

public record NotificationCreatedForUserEvent(
        UUID userId,
        NotificationResponse notification
) {}