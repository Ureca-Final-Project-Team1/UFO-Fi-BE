package com.example.ufo_fi.v2.payment.presentation.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class SlackRecoverReq {
    private String token;
    private String team_id;
    private String user_id;
    private String user_name;
    private String command;
    private String text;
    private String response_url;
}
