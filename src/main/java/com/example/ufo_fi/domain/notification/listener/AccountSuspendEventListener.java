package com.example.ufo_fi.domain.notification.listener;

import com.example.ufo_fi.domain.notification.event.AccountSuspendEvent;
import com.example.ufo_fi.domain.notification.processor.AccountSuspendNotificationProcessor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
@RequiredArgsConstructor
public class AccountSuspendEventListener {
    private final AccountSuspendNotificationProcessor accountSuspendNotificationProcessor;

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void handleAccountSuspended(AccountSuspendEvent event) {
        accountSuspendNotificationProcessor.process(event);
    }
}
