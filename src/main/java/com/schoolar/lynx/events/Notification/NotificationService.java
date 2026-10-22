package com.schoolar.lynx.events.Notification;

import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@Service
public class NotificationService {

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

    public void notify(String eventName, String message) {
        clients.removeIf(emitter -> {
            try {
                emitter.send(
                        SseEmitter.event()
                                .name(eventName)
                                .data(message)
                );
                return false;
            } catch (IOException e) {
                emitter.completeWithError(e);
                return true;
            }
        });
    }
}
