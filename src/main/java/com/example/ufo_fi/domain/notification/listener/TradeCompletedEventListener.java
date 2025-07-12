package com.example.ufo_fi.domain.notification.listener;

import com.example.ufo_fi.domain.notification.event.TradeCompletedEvent;
import com.example.ufo_fi.domain.notification.service.NotificationSettingsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
@RequiredArgsConstructor
public class TradeCompletedEventListener {

    private final NotificationSettingsService notificationService;

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void handleTradeCompleted(TradeCompletedEvent event) {
        Long sellerId = event.getSellerId();
        // notificationService.sendMessage();
    }
}
