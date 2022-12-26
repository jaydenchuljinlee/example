package com.ms.license.aop;

import brave.Tracer;
import com.ms.license.annotation.ThirdPartyLog;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@RequiredArgsConstructor
@Slf4j
@Aspect
@Component
public class ThirdPartyLoggingAop {
    private final Tracer tracer;

    private static final String LOG_NAME = "THIRD_PARTY_LOG";

    @Around("@annotation(com.ms.license.annotation.ThirdPartyLog)")
    public void doTrace(final ProceedingJoinPoint joinPoint) throws Throwable {
        final Object[] args = joinPoint.getArgs();

        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();

        ThirdPartyLog thirdPartyLog = methodSignature.getMethod().getAnnotation(ThirdPartyLog.class);

        log.info("[trace] -> {}", Arrays.toString(thirdPartyLog.name()));

        brave.Span newSpan = tracer.nextSpan().name(LOG_NAME);

        try(Tracer.SpanInScope ws = tracer.withSpanInScope(newSpan.start())) {
            joinPoint.proceed();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            newSpan.tag("peer.service", "thirdParty");
            newSpan.annotate("cr");
            newSpan.finish();
        }
    }
}
