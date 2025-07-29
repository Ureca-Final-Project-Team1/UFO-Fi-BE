package com.example.ufo_fi.global.log;

import java.lang.reflect.Method;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.aspectj.lang.ProceedingJoinPoint;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LogTrace {
    private String traceId;
    private Long userId;
    private Object requestBody;
    private LocalDateTime requestStartAt;
    private LogMethodTrace logMethodTrace;

    public void appendLogMethodTrace(Method method){
        if(logMethodTrace == null){
            logMethodTrace = new LogMethodTrace();
        }
        logMethodTrace.appendMethod(method.getName());
    }
}
