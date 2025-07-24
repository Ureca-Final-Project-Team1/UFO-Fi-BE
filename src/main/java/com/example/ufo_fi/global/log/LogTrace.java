package com.example.ufo_fi.global.log;

import java.lang.reflect.Method;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class LogTrace {
    private String errorName;
    private String userId;
    private String requestBody;
    private Exception exception;
    private LocalDateTime requestStartAt;
    private LogMethodTrace logMethodTrace;

    public void appendLogMethodTrace(Method method, Object... args){
        if(logMethodTrace == null){
            logMethodTrace = new LogMethodTrace();
        }
        logMethodTrace.appendMethod(method.getName(), args);
    }
}
