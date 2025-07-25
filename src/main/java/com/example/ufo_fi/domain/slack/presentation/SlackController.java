package com.example.ufo_fi.domain.slack.presentation;

import com.example.ufo_fi.domain.slack.application.SlackService;
import com.example.ufo_fi.domain.slack.presentation.dto.request.SlackRestoreReq;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class SlackController {
    private final SlackService slackService;

    @PostMapping("/slack/command")
    public void checkConnection(@ModelAttribute SlackRestoreReq slackRestoreReq){

    }
}