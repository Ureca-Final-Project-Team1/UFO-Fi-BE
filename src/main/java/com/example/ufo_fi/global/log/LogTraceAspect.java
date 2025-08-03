package com.example.ufo_fi.global.log;

import com.example.ufo_fi.global.json.JsonUtil;
import com.example.ufo_fi.v2.auth.application.principal.DefaultUserPrincipal;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Component
public class LogTraceAspect {

    @Pointcut("""
        execution(* com.example.ufo_fi.domain.payment.presentation.PaymentController..*(..)) ||
        execution(* com.example.ufo_fi.domain.payment.domain.payment.state..*(..)) ||
        execution(* com.example.ufo_fi.domain.payment.domain.payment.PaymentStateContext.*(..)) ||
        execution(* com.example.ufo_fi.domain.payment.domain.payment.StateMetaData.*(..))
    """)
    private void paymentLogTraceTarget() {}

    @Around("paymentLogTraceTarget()")
    public Object traceLog(ProceedingJoinPoint joinPoint) throws Throwable {
        LogTrace logTrace = LogTraceContext.get();
        boolean isFirst = (logTrace == null);

        if(isFirst){
            logTrace = createLogTrace(joinPoint);
            LogTraceContext.set(logTrace);
        }

        methodTrace(logTrace, joinPoint);

        try {
            return joinPoint.proceed();
        } finally{
            if(isFirst){
                LogTraceContext.clear();
            }
        }
    }

    private void methodTrace(LogTrace logTrace, ProceedingJoinPoint joinPoint) {
        LogMethodTrace logMethodTrace = logTrace.getLogMethodTrace();

        logMethodTrace.appendMethod(joinPoint.getSignature().toShortString());
    }

    private LogTrace createLogTrace(ProceedingJoinPoint joinPoint) {
            return LogTrace.builder()
                    .traceId(UUID.randomUUID().toString())
                    .userId(extractUserIdFromSecurityContextHolder())
                    .requestBody(JsonUtil.toJson(joinPoint.getArgs()))
                    .requestStartAt(LocalDateTime.now())
                    .logMethodTrace(new LogMethodTrace())
                    .build();
    }

    private Long extractUserIdFromSecurityContextHolder() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            return null;
        }

        Object principal = authentication.getPrincipal();
        if (principal instanceof DefaultUserPrincipal userPrincipal) {
            return userPrincipal.getId();
        }
        return null;
    }
}