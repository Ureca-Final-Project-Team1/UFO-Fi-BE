package com.example.ufo_fi.domain.notification.listener;

import com.example.ufo_fi.domain.notification.event.CreatedPostEvent;
import com.example.ufo_fi.domain.notification.processor.CreatedPostNotificationProcessor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
@RequiredArgsConstructor
public class CreatedPostEventListener {

    private final CreatedPostNotificationProcessor createdPostNotificationProcessor;

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void handleCreatedPostEvent(CreatedPostEvent event) {
        createdPostNotificationProcessor.process(event);
    }
}
