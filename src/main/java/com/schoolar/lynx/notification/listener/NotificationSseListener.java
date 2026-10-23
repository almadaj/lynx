package com.schoolar.lynx.notification.listener;

import com.schoolar.lynx.notification.NotificationService;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

import com.schoolar.lynx.notification.event.NotificationCreatedForUserEvent;

@Component
public class NotificationSseListener {

    private final NotificationService notificationService;

    public NotificationSseListener(
            NotificationService notificationService
    ) {
        this.notificationService = notificationService;
    }

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void handle(NotificationCreatedForUserEvent event) {
        notificationService.sendToUser(
                event.userId(),
                event.notification()
        );
    }
}
