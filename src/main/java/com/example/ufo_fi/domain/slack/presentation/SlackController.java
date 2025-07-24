package com.example.ufo_fi.domain.slack.presentation;

import com.example.ufo_fi.domain.slack.application.SlackService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class SlackController {
    private final SlackService slackService;

    @PostMapping("/slack/command")
    public void checkConnection(

    ){

    }

    @PostMapping("/slack/heart-beat")
    public ResponseEntity<?> handleSlackEvent(){

    }
}