package com.example.ufo_fi.domain.notification.listener;

import com.example.ufo_fi.domain.notification.event.AccountSuspendEvent;
import com.example.ufo_fi.domain.notification.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
@RequiredArgsConstructor
public class AccountSuspendedEventListener {

    private final NotificationService notificationService;

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void handleAccountSuspended(AccountSuspendEvent event) {
        Long userId = event.getUserId();
        // notificationService.sendMessage();
    }
}
