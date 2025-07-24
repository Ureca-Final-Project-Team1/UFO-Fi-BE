package com.example.ufo_fi.domain.slack.infrastructure;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * 슬랙 채널명을 모아두는 enum 클래스입니다.
 */
@Getter
@RequiredArgsConstructor
public enum SlackChannel {
    ERROR("#예외채널"),

    ;

    private final String channel;
}
