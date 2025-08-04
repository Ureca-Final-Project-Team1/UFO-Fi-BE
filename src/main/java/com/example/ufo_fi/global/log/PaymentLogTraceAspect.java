package com.example.ufo_fi.global.log;

import com.example.ufo_fi.global.log.meta.BasicLogInfo;
import com.example.ufo_fi.global.log.meta.PaymentLogMethodTrace;
import com.example.ufo_fi.global.log.meta.PaymentLogTrace;
import com.example.ufo_fi.v2.auth.application.principal.DefaultUserPrincipal;
import com.example.ufo_fi.v2.payment.domain.payment.entity.FailLog;
import com.example.ufo_fi.v2.payment.infrastructure.toss.response.ConfirmResult;
import com.example.ufo_fi.v2.payment.persistence.FailLogRepository;
import com.example.ufo_fi.v2.payment.persistence.PaymentRepository;
import com.example.ufo_fi.v2.payment.presentation.dto.request.ConfirmReq;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
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
@RequiredArgsConstructor
public class PaymentLogTraceAspect {

    private final FailLogRepository failLogRepository;

    @Pointcut("""
        execution(* com.example.ufo_fi.v2.payment.presentation.PaymentController..*(..)) ||
        execution(* com.example.ufo_fi.v2.payment.domain.payment.state..*(..)) ||
        execution(* com.example.ufo_fi.v2.payment.domain.payment.PaymentStateContext.*(..)) ||
        execution(* com.example.ufo_fi.v2.payment.domain.payment.StateMetaData.*(..))
    """)
    private void paymentLogTraceTarget() {}

    @Around("paymentLogTraceTarget()")
    public Object traceLog(ProceedingJoinPoint joinPoint) throws Throwable {
        PaymentLogTrace logTrace = PaymentLogTraceContext.get();

        boolean isFirst = (logTrace == null);
        if(isFirst){
            logTrace = createLogTrace(joinPoint);
            PaymentLogTraceContext.set(logTrace);
        }

        methodTrace(logTrace, joinPoint);

        try {
            Object result = joinPoint.proceed();

            if(result instanceof ConfirmResult confirmResult){
                logTrace.appendConfirmResult(result);
            }

            return result;
        } finally{
            if(isFirst){
                failLogRepository.save(FailLog.from(logTrace));
                PaymentLogTraceContext.clear();
            }
        }
    }

    private PaymentLogTrace createLogTrace(ProceedingJoinPoint joinPoint) {
        PaymentLogTrace paymentLogTrace = new PaymentLogTrace();
        addBasicInfo(paymentLogTrace);
        addConfirmReq(joinPoint, paymentLogTrace);
        return paymentLogTrace;
    }

    private void methodTrace(PaymentLogTrace logTrace, ProceedingJoinPoint joinPoint) {
        logTrace.appendLogMethodTrace(joinPoint);

        PaymentLogMethodTrace logMethodTrace = logTrace.getLogMethodTrace();

        logMethodTrace.appendMethod(joinPoint.getSignature().toShortString());
    }

    private void addBasicInfo(PaymentLogTrace paymentLogTrace) {
        paymentLogTrace.appendBasicLogInfo(BasicLogInfo.of(
            extractUserIdFromSecurityContextHolder(),
            LocalDateTime.now()
        ));
    }

    private void addConfirmReq(ProceedingJoinPoint joinPoint, PaymentLogTrace paymentLogTrace) {
        Object[] args = joinPoint.getArgs();
        for (Object arg : args) {
            if (arg instanceof ConfirmReq confirmReq) {
                paymentLogTrace.appendConfirmRequest(confirmReq);
            }
        }
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