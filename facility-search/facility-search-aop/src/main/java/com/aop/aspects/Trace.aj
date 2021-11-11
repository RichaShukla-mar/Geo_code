package com.aop.aspects;

import org.apache.log4j.Logger;
import org.apache.logging.log4j.LogManager;

public privileged aspect Trace {
            private static final Logger LOGGER = Logger       
			.getLogger(Trace.class.getName());
			static boolean flag= true;
    pointcut publicMethodExecuted(): execution(* *(..)) && if(flag);
    Object around(): publicMethodExecuted() {
      long startTime = System.currentTimeMillis();
      Object result = proceed();
      long endtime = System.currentTimeMillis();
      LOGGER.info("----------------------------------------------------*-----------------------------------------------------");
      LOGGER.info("Method Name      	 :->	"+thisJoinPointStaticPart.getSignature().getName());
      LOGGER.info("Time taken for execution :->	"+(endtime-startTime)+"ms");
      LOGGER.info("----------------------------------------------------*-----------------------------------------------------");
      return result;
   }

    after(): publicMethodExecuted() {
       
    }
}