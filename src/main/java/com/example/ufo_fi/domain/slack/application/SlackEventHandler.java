package com.example.ufo_fi.domain.slack.application;

import com.example.ufo_fi.global.log.LogTrace;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
@RequiredArgsConstructor
public class SlackEventHandler {
    private final SlackService slackService;
    private final SlackMessageBuilder slackMessageBuilder;

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void handleSlackMessage(LogTrace logTrace){
        String message = slackMessageBuilder.buildMessage(logTrace);
        slackService.sendSlackMessage(message);
    }
}
