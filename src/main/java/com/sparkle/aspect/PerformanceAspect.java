package com.sparkle.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class PerformanceAspect {

    @Pointcut("@annotation(com.sparkle.annotation.ExecutionTime)")
    public void executionTimePC() {
    }

    @Around("executionTimePC()")
    public Object aroundAnyExecutionTimeAdvice(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {

        long beforeExecution = System.currentTimeMillis();
        Object result = null;
        log.info("Execution starts: ");

        try{
            result=proceedingJoinPoint.proceed();
        }catch (Throwable throwable){
            throwable.printStackTrace();
        }
        long afterExecution = System.currentTimeMillis();
        log.info("Time taken to execute: {} ms - Method: {}" , (afterExecution - beforeExecution),
                proceedingJoinPoint.getSignature().toShortString());
        return result;
    }

}
