package com.schoolar.lynx.notification;

import com.schoolar.lynx.domain.dto.NotificationResponse;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/notifications")
@RequiredArgsConstructor
public class NotificationController {
    private final NotificationService notificationService;

    @GetMapping(
            value = "/stream",
            produces = MediaType.TEXT_EVENT_STREAM_VALUE
    )
    public SseEmitter stream() {
        return notificationService.subscribe();
    }

    @GetMapping
    public ResponseEntity<List<NotificationResponse>> getMyNotifications(){
        return ResponseEntity.ok(notificationService.findMyNotifications());
    }

    @GetMapping("/{id}")
    public ResponseEntity<NotificationResponse> getNotificationById(@PathVariable UUID id){
        return ResponseEntity.ok(notificationService.findNotificationById(id));
    }

    @PostMapping("/{id}/read")
    public void setReadNotification(@PathVariable UUID id){
        notificationService.markAsRead(id);
    }
}
