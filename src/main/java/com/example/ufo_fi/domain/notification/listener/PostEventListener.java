package com.example.ufo_fi.domain.notification.listener;

import com.example.ufo_fi.domain.notification.event.PostEvent;
import com.example.ufo_fi.domain.notification.service.NotificationSettingsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
@RequiredArgsConstructor
public class PostEventListener {

    private final NotificationSettingsService notificationService;

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void handelPostEvent(PostEvent event) {
        Long sellerId = event.getSellerId();
        // notificationService.sendMessage();
    }
}
