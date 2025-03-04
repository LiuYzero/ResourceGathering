package com.liuyang.toolbox.ResourceGathering.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class TimeCostAspect {
    private static final Logger logger = LoggerFactory.getLogger(TimeCostAspect.class);


    @Around("execution(* com.liuyang.toolbox.ResourceGathering.controller..*.*(..))")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        long startTime = System.currentTimeMillis();

        Object result = joinPoint.proceed();

        long endTime = System.currentTimeMillis();
        long costTime = endTime - startTime;

        logger.info("Method [{}] executed in {} ms", joinPoint.getSignature().toShortString(), costTime);

        return result;
    }
}
