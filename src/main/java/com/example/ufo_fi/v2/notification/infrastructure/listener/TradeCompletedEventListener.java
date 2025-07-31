package com.example.ufo_fi.v2.notification.infrastructure.listener;

import com.example.ufo_fi.v2.notification.application.processor.TradeCompletedNotificationProcessor;
import com.example.ufo_fi.v2.notification.domain.event.TradeCompletedEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
@RequiredArgsConstructor
public class TradeCompletedEventListener {

    private final TradeCompletedNotificationProcessor tradeCompletedNotificationProcessor;

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void handleTradeCompleted(TradeCompletedEvent event) {
        tradeCompletedNotificationProcessor.process(event);
    }
}
