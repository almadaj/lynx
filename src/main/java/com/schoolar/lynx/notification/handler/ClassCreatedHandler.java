package com.schoolar.lynx.notification.handler;

import com.schoolar.lynx.domain.dto.NotificationResponse;
import com.schoolar.lynx.domain.enums.EventType;
import com.schoolar.lynx.domain.enums.Role;
import com.schoolar.lynx.domain.model.Company;
import com.schoolar.lynx.domain.model.Notification;
import com.schoolar.lynx.domain.model.NotificationUser;
import com.schoolar.lynx.domain.model.UserCompany;
import com.schoolar.lynx.events.classes.ClassCreatedEvent;
import com.schoolar.lynx.notification.event.NotificationCreatedForUserEvent;
import com.schoolar.lynx.notification.repository.NotificationRepository;
import com.schoolar.lynx.notification.repository.NotificationUserRepository;
import com.schoolar.lynx.repository.CompanyRepository;
import com.schoolar.lynx.repository.UserCompanyRepository;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class ClassCreatedHandler {
    private static final List<Role> RECIPIENT_ROLES = List.of(
            Role.HEADTEACHER,
            Role.PRINCIPAL,
            Role.ADMIN
    );
    private final CompanyRepository companyRepository;
    private final NotificationRepository notificationRepository;
    private final NotificationUserRepository notificationUserRepository;
    private final UserCompanyRepository userCompanyRepository;
    private final ApplicationEventPublisher eventPublisher;

    public ClassCreatedHandler(
            NotificationRepository notificationRepository,
            NotificationUserRepository notificationUserRepository,
            UserCompanyRepository userCompanyRepository,
            CompanyRepository companyRepository,
            ApplicationEventPublisher eventPublisher
    ) {
        this.notificationRepository = notificationRepository;
        this.notificationUserRepository = notificationUserRepository;
        this.userCompanyRepository = userCompanyRepository;
        this.companyRepository = companyRepository;
        this.eventPublisher = eventPublisher;
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

        List<NotificationUser> savedDeliveries =
                notificationUserRepository.saveAll(deliveries);

        for (NotificationUser delivery : savedDeliveries) {
            NotificationResponse notificationResponse =
                    new NotificationResponse(
                            delivery.getId(),
                            notification.getTitle(),
                            notification.getMessage(),
                            notification.getType(),
                            notification.getCreatedAt(),
                            false
                    );

            eventPublisher.publishEvent(
                    new NotificationCreatedForUserEvent(
                            delivery.getUser().getId(),
                            notificationResponse
                    )
            );
        }
    }
}
