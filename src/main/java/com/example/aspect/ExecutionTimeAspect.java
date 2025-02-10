package com.example.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class ExecutionTimeAspect {

    @Around("@annotation(com.example.annotation.LogExecutionTime)")
    public Object logExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
        long startTime = System.currentTimeMillis();

        Object result = joinPoint.proceed(); // Proceed with method execution

        long endTime = System.currentTimeMillis();
        System.err.println(joinPoint.getSignature() + " executed in " + (endTime - startTime) + " ms");
        System.err.println("Thread :"+Thread.currentThread().getName());
        return result;
    }
}
