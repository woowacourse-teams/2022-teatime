package com.woowacourse.teatime.teatime.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.jboss.logging.MDC;
import org.springframework.stereotype.Component;

@Component
@Aspect
@Slf4j
public class LogAspect {

    @Around("execution(* com.woowacourse.teatime..*(..))")
    public Object logExecution(ProceedingJoinPoint joinPoint) throws Throwable {
        long starTime = System.currentTimeMillis();
        Object result = joinPoint.proceed();
        long endTime = System.currentTimeMillis();

        String correlationId = (String) MDC.get("correlationId");
        long executionTimeMillis = endTime - starTime;

        final String className = joinPoint.getSignature()
                .getDeclaringType()
                .getSimpleName();
        final String methodName = joinPoint.getSignature()
                .getName();

        log.info("[{}]{}.{} took {}ms", correlationId, className, methodName, executionTimeMillis);

        return result;
    }
}
