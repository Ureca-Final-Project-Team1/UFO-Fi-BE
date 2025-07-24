package com.example.ufo_fi.domain.slack.infrastructure;

import com.example.ufo_fi.domain.slack.application.SlackMessageBuilder;
import com.slack.api.Slack;
import com.slack.api.methods.MethodsClient;
import com.slack.api.methods.SlackApiException;
import com.slack.api.methods.request.chat.ChatPostMessageRequest;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class SlackNotifier {
    private final SlackMessageBuilder slackMessageBuilder;

    @Value(value = "${slack.token}")
    private String slackToken;

    public void sendSlackMessage(){
        try{
            MethodsClient methods = Slack.getInstance().methods(slackToken);

            ChatPostMessageRequest request = ChatPostMessageRequest.builder()
                    .channel(SlackChannel.ERROR.getChannel())
                    .text(slackMessageBuilder.buildMessage())
                    .build();

            methods.chatPostMessage(request);
        } catch (SlackApiException | IOException e) {
            log.error(e.getMessage());
        }
    }
}
