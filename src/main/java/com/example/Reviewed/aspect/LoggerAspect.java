package com.example.Reviewed.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@Aspect
@Component
public class LoggerAspect {
    // Create a Logger instance for this class
    private static final Logger logger = LoggerFactory.getLogger(LoggerAspect.class);

    //explaination on how to use execution
    //("execution(* com.example.service.*.*(..))")
    //execution(modifiers-pattern? return-type-pattern declaring-type-pattern? method-name-pattern(param-pattern) throws-pattern?)
//    @Before("execution(* com.example.Reviewed.controller.*.*(..)) || execution(* com.example.Reviewed.serviceimpl.*.*(..))")
//    public void logBefore(JoinPoint joinPoint) {
//        logger.info("Entering method: {}", joinPoint.getSignature());
//    }
//
//    @After("execution(* com.example..*(..))")
//    public void afterAdvice(JoinPoint joinPoint) {
//        System.out.println("After: " + joinPoint.getSignature());
//    }
//
//    @AfterReturning(
//            pointcut = "execution(* com.example..*(..))",
//            returning = "result"
//    )
//    public void afterReturningAdvice(JoinPoint joinPoint, Object result) {
//        System.out.println("Returned from: " + joinPoint.getSignature() + ", result: " + result);
//    }
//
//
//    @AfterThrowing(
//            pointcut = "execution(* com.example..*(..))",
//            throwing = "ex"
//    )
//    public void afterThrowingAdvice(JoinPoint joinPoint, Throwable ex) {
//        System.out.println("Exception in: " + joinPoint.getSignature() + ", message: " + ex.getMessage());
//    }

    @Around("execution(* com.example.Reviewed.controller.*.*(..)) || execution(* com.example.Reviewed.serviceimpl.*.*(..))")
    public Object aroundAdvice(ProceedingJoinPoint joinPoint) throws Throwable {
        logger.info("Entering method: {}", joinPoint.getSignature());
        Object[] args = joinPoint.getArgs();
        for (int i = 0; i < args.length; i++) {
            if(args[i]!=null)
            System.out.println("Arg " + i + ": " + args[i].toString());
        }
        Object result = joinPoint.proceed();

        logger.info("Exiting method: {}", joinPoint.getSignature());
         // Executes the method
        // Log or process the return value
        if(result!=null) logger.info("Method returned: {}", result.toString());
        return result;
    }







}
