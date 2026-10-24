package com.hospital.doctors.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Order(1)
public class LoggingAspect {
    private static final Logger LOGGER = LoggerFactory.getLogger(LoggingAspect.class);

    @Around("execution(public * com.hospital.doctors.controller..*(..))"
            + " || execution(public * com.hospital.doctors.services..*(..))")
    public Object logMethodCall(ProceedingJoinPoint joinPoint) throws Throwable {
        String layer = joinPoint.getTarget().getClass().getSimpleName();
        String method = joinPoint.getSignature().getName();
        String operation = layer + "." + method;
        long startedAt = System.nanoTime();

        LOGGER.info("flow.start layer={} operation={} argumentTypes={}",
                layer, operation, argumentTypes(joinPoint));
        try {
            Object result = joinPoint.proceed();
            long durationMs = (System.nanoTime() - startedAt) / 1_000_000;
            LOGGER.info("flow.success layer={} operation={} durationMs={}",
                    layer, operation, durationMs);
            return result;
        } catch (Throwable exception) {
            long durationMs = (System.nanoTime() - startedAt) / 1_000_000;
            LOGGER.error("flow.failure layer={} operation={} durationMs={} exceptionType={}",
                    layer, operation, durationMs, exception.getClass().getSimpleName(), exception);
            throw exception;
        }
    }

    private String argumentTypes(ProceedingJoinPoint joinPoint) {
        Object[] arguments = joinPoint.getArgs();
        if (arguments.length == 0) {
            return "[]";
        }
        StringBuilder types = new StringBuilder("[");
        for (int i = 0; i < arguments.length; i++) {
            if (i > 0) {
                types.append(", ");
            }
            types.append(arguments[i] == null ? "null" : arguments[i].getClass().getSimpleName());
        }
        return types.append(']').toString();
    }
}
