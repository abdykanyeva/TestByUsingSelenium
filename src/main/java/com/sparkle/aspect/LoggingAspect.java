package com.sparkle.aspect;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
@AllArgsConstructor
public class LoggingAspect {

    private String getUsername() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }

    @Pointcut("execution(* com.cydeo.controller.*.*(..))")
    public void anyControllerPC() {}

    @Before("anyControllerPC()")
    public void beforeAnyControllerPC(JoinPoint joinPoint){
        log.info("Before -> Method: {}, User: {}"
                , joinPoint.getSignature().toShortString()
                , getUsername());
    }

    @AfterReturning(pointcut = "anyControllerPC()", returning = "results")
    public void afterReturningAnyController(JoinPoint joinPoint, Object results) {
        log.info("After Returning -> Method: {}, User: {}, Results: {}"
                , joinPoint.getSignature().toShortString()
                , getUsername()
                , results.toString());
    }

    @AfterThrowing(pointcut = "anyControllerPC()", throwing = "exception")
    public void afterThrowingAnyController(JoinPoint joinPoint, Exception exception) {
        log.info("After Throwing -> Method: {}, User: {}, Results: {}"
                , joinPoint.getSignature().toShortString()
                , getUsername()
                , exception.getMessage());
    }

}
