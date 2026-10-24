package com.schoolar.lynx.notification;

import com.schoolar.lynx.domain.dto.NotificationResponse;
import com.schoolar.lynx.domain.enums.Role;
import com.schoolar.lynx.domain.model.*;
import com.schoolar.lynx.repository.CompanyRepository;
import com.schoolar.lynx.notification.repository.NotificationRepository;
import com.schoolar.lynx.notification.repository.NotificationUserRepository;
import com.schoolar.lynx.repository.UserCompanyRepository;
import com.schoolar.lynx.security.AuthenticatedUserService;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

@Service
public class NotificationService {
    private final NotificationUserRepository notificationUserRepository;
    private final AuthenticatedUserService authenticatedUserService;

    public NotificationService(
            NotificationUserRepository notificationUserRepository,
            AuthenticatedUserService authenticatedUserService
    ) {
        this.notificationUserRepository = notificationUserRepository;
        this.authenticatedUserService = authenticatedUserService;
    }

    private final Map<UUID, List<SseEmitter>> clients = new ConcurrentHashMap<>();

    public SseEmitter subscribe() {
        User user = authenticatedUserService.get();
        SseEmitter emitter = new SseEmitter(0L);

        clients.computeIfAbsent(
                user.getId(),
                id -> new CopyOnWriteArrayList<>()
        ).add(emitter);

        emitter.onCompletion(() -> removeClient(user.getId(), emitter));
        emitter.onTimeout(() -> removeClient(user.getId(), emitter));
        emitter.onError(error -> removeClient(user.getId(), emitter));

        try {
            emitter.send(
                    SseEmitter.event()
                            .name("connected")
                            .data("Conexão SSE estabelecida")
            );
        } catch (IOException e) {
            removeClient(user.getId(), emitter);
            emitter.completeWithError(e);
        }

        return emitter;
    }

    @Transactional
    public List<NotificationResponse> findMyNotifications() {
        User loggedUser = authenticatedUserService.get();
        return notificationUserRepository
                .findAllByUserIdOrderByNotificationCreatedAtDesc(loggedUser.getId())
                .stream()
                .map(notificationUser -> {
                    Notification notification = notificationUser.getNotification();

                    return new NotificationResponse(
                            notificationUser.getId(),
                            notification.getTitle(),
                            notification.getMessage(),
                            notification.getType(),
                            notification.getCreatedAt(),
                            notificationUser.getReadAt()
                    );
                })
                .toList();
    }

    private void removeClient(UUID userId, SseEmitter emitter) {
        clients.computeIfPresent(userId, (id, userClients) -> {
            userClients.remove(emitter);
            return userClients.isEmpty() ? null : userClients;
        });
    }

    public void sendToUser(UUID userId, NotificationResponse notification) {
        List<SseEmitter> userClients = clients.get(userId);

        if (userClients == null || userClients.isEmpty()) {
            return;
        }

        for (SseEmitter emitter : userClients) {
            try {
                emitter.send(
                        SseEmitter.event()
                                .name("notification")
                                .data(notification)
                );
            } catch (IOException e) {
                removeClient(userId, emitter);
                emitter.completeWithError(e);
            }
        }
    }
}
