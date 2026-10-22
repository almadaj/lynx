package com.schoolar.lynx.notification;

import com.schoolar.lynx.domain.dto.CompanyResponseDTO;
import com.schoolar.lynx.domain.dto.NotificationResponse;
import com.schoolar.lynx.domain.dto.RegisterCompanyDTO;
import jakarta.validation.Valid;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.List;

@RestController
@RequestMapping("/api/notifications")
public class NotificationController {

    private final NotificationService notificationService;

    public NotificationController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

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
}
