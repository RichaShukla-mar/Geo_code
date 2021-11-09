/*
 *   <copyright 
 *   notice="lm-source-program" 
 *   pids="5724-H72,5655-R36,5655-L82,5724-L26," 
 *   years="2008,2016" 
 *   crc="2597352500" > 
 *   Licensed Materials - Property of IBM  
 *    
 *   5724-H72,5655-R36,5655-L82,5724-L26, 
 *    
 *   (C) Copyright IBM Corp. 2008, 2016 All Rights Reserved.  
 *    
 *   US Government Users Restricted Rights - Use, duplication or  
 *   disclosure restricted by GSA ADP Schedule Contract with  
 *   IBM Corp.  
 *   </copyright> 
 */

/**
 *   "JmqiPut" is a sample JMQI program to put messages on a message    
 *   queue, and is an example of the use of MQPUT.                  
 *                                                                  
 *      -- messages are sent to the queue named by the parameter    
 *                                                                  
 *      -- gets lines from StdIn, and adds each to target
 *         queue, taking each line of text as the content           
 *         of a datagram message; the sample stops when a null      
 *         line (or EOF) is read.                                   
 *         New-line characters are removed.                         
 *                                                                  
 *      -- writes a message for each MQI reason other than          
 *         MQRC_NONE; stops if there is a MQI completion code       
 *         of MQCC_FAILED                                           
 *                                                                  
 *    Program logic:                                                
 *         MQOPEN target queue for OUTPUT                           
 *         while end of input file not reached,                     
 *         .  read next line of text                                
 *         .  MQPUT datagram message with text line as data         
 *         MQCLOSE target queue                                     
 *
 *
 *
 * "JmqiPut" has the following parameters                          
 *       required:                                                  
 *                 (1) The name of the target queue                 
 *       optional:                                                  
 *                 (2) Queue manager name                           
 *                 (3) The open options                             
 *                 (4) The close options                            
 *                 (5) The name of the target queue manager         
 *                 (6) The name of the dynamic queue                                          
 *                                                                                                         
 *
 * To connect to the Queue manager in Client mode, specify "MQSERVER" as an environment variable 
 * or a system parameter as follows:
 *     MQSERVER=ChannelName/TransportType/ConnectionName                                                                                                          
 *                                                                                                         
 *                                                      
 *                                                                                                         
 * For example:
 *     bindings mode:
 *         java com.ibm.mq.jmqi.samples.JmqiPut QUEUE QMGR
 *         
 *     client mode:
 *         java -DMQSERVER=SYSTEM.DEF.SVRCONN/TCP/localhost(1414) com.ibm.mq.jmqi.samples.JmqiPut QUEUE QMGR          
 */
package com.ibm.mq.jmqi.samples;

// Trace instrumented 28-May-2015 16:00:35  // AUTOINSERTEDTRACE

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;

import com.ibm.mq.constants.CMQC;
import com.ibm.mq.jmqi.JmqiDefaultPropertyHandler;
import com.ibm.mq.jmqi.JmqiDefaultThreadPoolFactory;
import com.ibm.mq.jmqi.JmqiEnvironment;
import com.ibm.mq.jmqi.JmqiFactory;
import com.ibm.mq.jmqi.JmqiMQ;
import com.ibm.mq.jmqi.JmqiPropertyHandler;
import com.ibm.mq.jmqi.JmqiThreadPoolFactory;
import com.ibm.mq.jmqi.MQCNO;
import com.ibm.mq.jmqi.MQMD;
import com.ibm.mq.jmqi.MQOD;
import com.ibm.mq.jmqi.MQPMO;
import com.ibm.mq.jmqi.handles.Hconn;
import com.ibm.mq.jmqi.handles.Hobj;
import com.ibm.mq.jmqi.handles.Phconn;
import com.ibm.mq.jmqi.handles.Phobj;
import com.ibm.mq.jmqi.handles.Pint;
import com.ibm.msg.client.commonservices.trace.Trace;

/**
 * Sample program to put a message to a queue
 */
public class JmqiPut extends SampleFramework {

  static { // AUTOINSERTEDTRACE
    if (Trace.isOn) {
      Trace.data("com.ibm.mq.jmqi.samples.JmqiPut", "static", "SCCS id", "@(#) MQMBID sn=p904-L171030.1 su=_fzLDAL1hEeet95U9U8zg4w pn=com.ibm.mq.jmqi/src/com/ibm/mq/jmqi/samples/JmqiPut.java");
    }
  }
  // AUTOINSERTEDTRACE

  /** The SCCSID which is expanded when the file is extracted from CMVC */
  public static final String sccsid1 = "@(#) MQMBID sn=p904-L171030.1 su=_fzLDAL1hEeet95U9U8zg4w pn=com.ibm.mq.jmqi/src/com/ibm/mq/jmqi/samples/JmqiPut.java";

  /**
   * Run the sample JmqiGet program
   * 
   * @param args
   */
  public static void main(String[] args) {
    if (Trace.isOn) { // AUTOINSERTEDTRACE
      Trace.entry("com.ibm.mq.jmqi.samples.JmqiPut", "main(String [ ])", new Object[]{args});
    }

    System.out.println("JmqiPut Start");

    try {
      JmqiPut program = new JmqiPut();
      program.perform(args);
    }
    catch (Exception e) {
      if (Trace.isOn) { // AUTOINSERTEDTRACE
        Trace.catchBlock("com.ibm.mq.jmqi.samples.JmqiPut", "main(String [ ])", e);
      }
      e.printStackTrace();
    }

    System.out.println("JmqiPut End");
    if (Trace.isOn) { // AUTOINSERTEDTRACE
      Trace.exit("com.ibm.mq.jmqi.samples.JmqiPut", "main(String [ ])");
    }
    return;
  }

  /**
   * Run the program
   * 
   * @param args
   * @throws Exception
   */
  public void perform(String[] args) throws Exception {
    if (Trace.isOn) { // AUTOINSERTEDTRACE
      Trace.entry(this, "com.ibm.mq.jmqi.samples.JmqiPut", "perform(String [ ])", new Object[]{
          args});
    }

    // ****************************************************************
    // * Get the input parameters
    // ****************************************************************
    setOpenOptions(CMQC.MQOO_OUTPUT | CMQC.MQOO_FAIL_IF_QUIESCING);
    parseCommandLineArgs(args, 1, 6);
    parseSystemProperties();

    // ****************************************************************
    // * Initialise the Jmqi
    // ****************************************************************
    JmqiThreadPoolFactory threadPool = new JmqiDefaultThreadPoolFactory();
    JmqiPropertyHandler propertyHandler = new JmqiDefaultPropertyHandler();
    JmqiEnvironment env = JmqiFactory.getInstance(threadPool, propertyHandler);

    JmqiMQ mq = getMQInstance(env);

    Pint cc = env.newPint(0);
    Pint rc = env.newPint(0);

    // ****************************************************************
    // * Connect to queue manager
    // ****************************************************************
    Phconn phconn = env.newPhconn();
    String qmname = getQueueManagerName();
    MQCNO connectOptions = getConnectOptions(env);
    mq.MQCONNX(qmname, connectOptions, phconn, cc, rc);
    if (rc.x != CMQC.MQRC_NONE) {
      Exception traceRet1 = new Exception("MQCONN ended with reason code " + rc.x);
      if (Trace.isOn) { // AUTOINSERTEDTRACE
        Trace.throwing(this, "com.ibm.mq.jmqi.samples.JmqiPut", "perform(String [ ])", traceRet1,
            1);
      }
      throw traceRet1;
    }
    Hconn hconn = phconn.getHconn();

    try {
      // ****************************************************************
      // * Open the target message queue for output
      // ****************************************************************
      MQOD mqod = env.newMQOD();
      mqod.setObjectName(getQueueName());
      mqod.setObjectQMgrName(getTargetQueueManagerName());
      mqod.setDynamicQName(getDynamicQueueName());
      int options = getOpenOptions();
      Phobj phobj = env.newPhobj();
      mq.MQOPEN(hconn, mqod, options, phobj, cc, rc);
      if (rc.x != CMQC.MQRC_NONE) {
        Exception traceRet2 = new Exception("MQOPEN ended with reason code " + rc.x);
        if (Trace.isOn) { // AUTOINSERTEDTRACE
          Trace.throwing(this, "com.ibm.mq.jmqi.samples.JmqiPut", "perform(String [ ])", traceRet2
              , 2);
        }
        throw traceRet2;
      }
      Hobj hobj = phobj.getHobj();

      // ****************************************************************
      // Read lines from the file and put them to the message queue
      // Loop until null line or end of file, or there is a failure
      // ****************************************************************
      MQMD mqmd = env.newMQMD();
      mqmd.setFormat(CMQC.MQFMT_STRING);
      mqmd.setCodedCharSetId(1208);

      MQPMO mqpmo = env.newMQPMO();

      InputStreamReader in = new InputStreamReader(System.in, Charset.defaultCharset());
      BufferedReader stdin = new BufferedReader(in);
      String message;

      while (((message = stdin.readLine()) != null) && (message.length() != 0)) {

        byte[] buffer = message.getBytes("UTF-8");
        ByteBuffer byteBuffer = ByteBuffer.wrap(buffer);
        int bufferLength = buffer.length;
        mq.MQPUT(hconn, hobj, mqmd, mqpmo, bufferLength, byteBuffer, cc, rc);
        if (rc.x != CMQC.MQRC_NONE) {
          Exception traceRet3 = new Exception("MQPUT ended with reason code " + rc.x);
          if (Trace.isOn) { // AUTOINSERTEDTRACE
            Trace.throwing(this, "com.ibm.mq.jmqi.samples.JmqiPut", "perform(String [ ])",
                traceRet3, 3);
          }
          throw traceRet3;
        }
      }

      // ****************************************************************
      // * Close the source queue (if it was opened)
      // ****************************************************************
      options = getCloseOptions();
      mq.MQCLOSE(hconn, phobj, options, cc, rc);
      if (rc.x != CMQC.MQRC_NONE) {
        Exception traceRet4 = new Exception("MQCLOSE ended with reason code " + rc.x);
        if (Trace.isOn) { // AUTOINSERTEDTRACE
          Trace.throwing(this, "com.ibm.mq.jmqi.samples.JmqiPut", "perform(String [ ])", traceRet4
              , 4);
        }
        throw traceRet4;
      }
    }
    finally {
      if (Trace.isOn) { // AUTOINSERTEDTRACE
        Trace.finallyBlock(this, "com.ibm.mq.jmqi.samples.JmqiPut", "perform(String [ ])");
      }
      // ****************************************************************
      // * Make sure to disconnect from the Queue Manager
      // ****************************************************************
      mq.MQDISC(phconn, cc, rc);
      if (rc.x != CMQC.MQRC_NONE) {
        Exception traceRet5 = new Exception("MQDISC ended with reason code " + rc.x);
        if (Trace.isOn) { // AUTOINSERTEDTRACE
          Trace.throwing(this, "com.ibm.mq.jmqi.samples.JmqiPut", "perform(String [ ])", traceRet5
              , 5);
        }
        throw traceRet5;
      }
    }
    if (Trace.isOn) { // AUTOINSERTEDTRACE
      Trace.exit(this, "com.ibm.mq.jmqi.samples.JmqiPut", "perform(String [ ])");
    }
    return;
  }
}
