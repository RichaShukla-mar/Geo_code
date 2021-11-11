package com.aop.aspects;

import com.jamonapi.Monitor;
import com.jamonapi.MonitorFactory;


public privileged aspect Jamonaspect {
	static boolean flag= true;
    pointcut publicMethodExecuted1(): execution(* *(..)) && if(flag);
    Object around(): publicMethodExecuted1() {
        String method = thisJoinPoint.getSignature().toShortString();
        Monitor monitor = MonitorFactory.start(method);
        Object ret = proceed();
        monitor.stop();
        return ret;
   }

    after(): publicMethodExecuted1() {
       
    }
}