package com.aop.aspects;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;

/**
 * @author maba, 2012-09-18
 */
@Aspect
public class AspectL {

    @Pointcut("execution(* mainmethod(..))")
    public void defineEntryPoint() {
    }

    @Before("defineEntryPoint()")
    public void aaa(JoinPoint joinPoint) {

    }

    @After("defineEntryPoint()")
    public void bbb(JoinPoint joinPoint) {

    }
}
