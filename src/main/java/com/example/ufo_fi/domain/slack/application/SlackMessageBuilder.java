package com.example.ufo_fi.domain.slack.application;

import com.example.ufo_fi.global.log.LogTrace;
import com.example.ufo_fi.global.log.LogTraceContext;
import org.springframework.stereotype.Component;

@Component
public class SlackMessageBuilder {
    private final static String MESSAGE_TEMPLATE =
            """
            *[에러 메시지: %s]*
            ---
            - 사용자 ID : %s
            - 발생 시각 : %s
            - 요청
            ```%s```
            - 예외 유형 : %s
            - 에러 메시지 : %s
            - 상태 전이(메서드 흐름)
            ```%s```
            """;

    public String buildMessage(LogTrace logTrace){
        return MESSAGE_TEMPLATE.formatted(
                logTrace.getErrorName(),
                logTrace.getUserId(),
                logTrace.getRequestStartAt(),
                logTrace.getRequestBody(),
                logTrace.getException().getClass().getSimpleName(),
                logTrace.getException().getMessage(),
                logTrace.getLogMethodTrace().getTracedMethods()
        );
    }
}
