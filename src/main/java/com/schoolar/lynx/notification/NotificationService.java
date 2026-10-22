package com.schoolar.lynx.notification;

import com.schoolar.lynx.domain.dto.NotificationResponse;
import com.schoolar.lynx.domain.enums.EventType;
import com.schoolar.lynx.domain.enums.Role;
import com.schoolar.lynx.domain.model.*;
import com.schoolar.lynx.events.classes.ClassCreatedEvent;
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
import java.util.concurrent.CopyOnWriteArrayList;

@Service
public class NotificationService {
    private static final List<Role> RECIPIENT_ROLES = List.of(
            Role.HEADTEACHER,
            Role.PRINCIPAL,
            Role.ADMIN
    );

    private final NotificationRepository notificationRepository;
    private final NotificationUserRepository notificationUserRepository;
    private final UserCompanyRepository userCompanyRepository;
    private final CompanyRepository companyRepository;
    private final AuthenticatedUserService authenticatedUserService;

    public NotificationService(
            NotificationRepository notificationRepository,
            NotificationUserRepository notificationUserRepository,
            UserCompanyRepository userCompanyRepository,
            CompanyRepository companyRepository,
            AuthenticatedUserService authenticatedUserService
    ) {
        this.notificationRepository = notificationRepository;
        this.notificationUserRepository = notificationUserRepository;
        this.userCompanyRepository = userCompanyRepository;
        this.companyRepository = companyRepository;
        this.authenticatedUserService = authenticatedUserService;
    }

    private final List<SseEmitter> clients = new CopyOnWriteArrayList<>();

    public SseEmitter subscribe() {
        SseEmitter emitter = new SseEmitter(0L);

        clients.add(emitter);

        emitter.onCompletion(() -> clients.remove(emitter));
        emitter.onTimeout(() -> clients.remove(emitter));
        emitter.onError(error -> clients.remove(emitter));

        try {
            emitter.send(
                    SseEmitter.event()
                            .name("connected")
                            .data("Conexão SSE estabelecida")
            );
        } catch (IOException e) {
            clients.remove(emitter);
            emitter.completeWithError(e);
        }

        return emitter;
    }

    @Transactional
    public void handleClassCreated(ClassCreatedEvent event) {
        Company company = companyRepository.findById(event.companyId())
                .orElseThrow(() ->
                        new IllegalArgumentException("Instituição não encontrada")
                );

        Notification notification = Notification.builder()
                .company(company)
                .type(EventType.CLASS_CREATED)
                .title("Nova turma criada")
                .message("A turma " + event.name() + " foi criada.")
                .build();

        notificationRepository.save(notification);

        List<UserCompany> recipients =
                userCompanyRepository
                        .findAllByCompanyIdAndActiveTrueAndRoleIn(
                                event.companyId(),
                                RECIPIENT_ROLES
                        );

        List<NotificationUser> deliveries = recipients.stream()
                .map(recipient -> NotificationUser.builder()
                        .notification(notification)
                        .user(recipient.getUser())
                        .build())
                .toList();

        notificationUserRepository.saveAll(deliveries);
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
                            notificationUser.getReadAt() != null
                    );
                })
                .toList();
    }
}
