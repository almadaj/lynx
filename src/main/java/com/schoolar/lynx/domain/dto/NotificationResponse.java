package com.schoolar.lynx.domain.dto;
import com.schoolar.lynx.domain.enums.EventType;

import java.time.LocalDateTime;
import java.util.UUID;

public record NotificationResponse(
        UUID id,
        String title,
        String message,
        EventType type,
        LocalDateTime createdAt,
        LocalDateTime readAt
) {}