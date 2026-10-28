package com.schoolar.lynx.notification;

import com.schoolar.lynx.domain.dto.NotificationResponse;
import com.schoolar.lynx.domain.dto.UserResponseDTO;
import com.schoolar.lynx.domain.enums.Role;
import com.schoolar.lynx.domain.mapper.NotificationMapper;
import com.schoolar.lynx.domain.model.*;
import com.schoolar.lynx.repository.CompanyRepository;
import com.schoolar.lynx.notification.repository.NotificationRepository;
import com.schoolar.lynx.notification.repository.NotificationUserRepository;
import com.schoolar.lynx.repository.UserCompanyRepository;
import com.schoolar.lynx.security.AuthenticatedUserService;
import com.schoolar.lynx.utils.MapperUtil;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

@Service
@AllArgsConstructor
public class NotificationService {
    private final NotificationUserRepository notificationUserRepository;
    private final AuthenticatedUserService authenticatedUserService;

//    public NotificationService(
//            NotificationUserRepository notificationUserRepository,
//            AuthenticatedUserService authenticatedUserService
//    ) {
//        this.notificationUserRepository = notificationUserRepository;
//        this.authenticatedUserService = authenticatedUserService;
//    }

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

    public NotificationResponse findNotificationById(UUID notificationId){
        User loggedUser = authenticatedUserService.get();
        UUID userId = loggedUser.getId();
        NotificationUser delivery = notificationUserRepository
                .findByIdAndUserId(notificationId, userId)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Notificação não encontrada"
                ));

        return NotificationMapper.toDTO(delivery);
    }

    @Transactional
    public void markAsRead(UUID notificationId) {
        User loggedUser = authenticatedUserService.get();
        UUID userId = loggedUser.getId();
        NotificationUser delivery = notificationUserRepository
                .findByIdAndUserId(notificationId, userId)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Notificação não encontrada"
                ));

        if (delivery.getReadAt() == null) {
            delivery.setReadAt(LocalDateTime.now());
        }
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
