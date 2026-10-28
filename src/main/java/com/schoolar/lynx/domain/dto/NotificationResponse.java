package com.schoolar.lynx.domain.dto;
import com.schoolar.lynx.domain.enums.EventType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class NotificationResponse{
    UUID id;
    String title;
    String message;
    EventType type;
    LocalDateTime createdAt;
    LocalDateTime readAt;
}