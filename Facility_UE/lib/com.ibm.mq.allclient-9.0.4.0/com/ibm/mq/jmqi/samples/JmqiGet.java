/*
 *   <copyright 
 *   notice="lm-source-program" 
 *   pids="5724-H72" 
 *   years="2008,2016" 
 *   crc="1869436356" >
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
 * "JmqiGet" is a sample JMQI program to get messages from a          
 * message queue, and is an example of MQGET.                     
 *                                                                  
 *      -- sample reads from message queue named in the parameter   
 *                                                                  
 *      -- displays the contents of the message queue,              
 *         assuming each message data to represent a line of        
 *         text to be written                                       
 *                                                                  
 *         messages are removed from the queue                      
 *                                                                  
 *      -- writes a message for each MQI reason other than          
 *         MQRC_NONE; stops if there is a MQI completion code       
 *         of MQCC_FAILED                                           
 *
 *
 *   Program logic:                                                 
 *      Take name of input queue from the parameter                 
 *      MQOPEN queue for INPUT                                      
 *      while no MQI failures,
 *      .  MQGET next message, remove from queue                    
 *      .  print the result                                         
 *      .  (no message available counts as failure, and loop ends)  
 *      MQCLOSE the subject queue  
 *      
 *
 *
 * "JmqiGet" has the following parameters                          
 *       required:                                                  
 *                 (1) The name of the source queue                 
 *       optional:                                                  
 *                 (2) Queue manager name                           
 *                 (3) The open options                             
 *                 (4) The close options                            
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
 *         java com.ibm.mq.jmqi.samples.JmqiGet QUEUE QMGR
 *         
 *     client mode:
 *         java -DMQSERVER=SYSTEM.DEF.SVRCONN/TCP/localhost(1414) com.ibm.mq.jmqi.samples.JmqiGet QUEUE QMGR          
 */
package com.ibm.mq.jmqi.samples;

// Trace instrumented 28-May-2015 16:00:35  // AUTOINSERTEDTRACE

import java.nio.ByteBuffer;

import com.ibm.mq.constants.CMQC;
import com.ibm.mq.jmqi.JmqiDefaultPropertyHandler;
import com.ibm.mq.jmqi.JmqiDefaultThreadPoolFactory;
import com.ibm.mq.jmqi.JmqiEnvironment;
import com.ibm.mq.jmqi.JmqiFactory;
import com.ibm.mq.jmqi.JmqiMQ;
import com.ibm.mq.jmqi.JmqiPropertyHandler;
import com.ibm.mq.jmqi.JmqiThreadPoolFactory;
import com.ibm.mq.jmqi.MQCNO;
import com.ibm.mq.jmqi.MQGMO;
import com.ibm.mq.jmqi.MQMD;
import com.ibm.mq.jmqi.MQOD;
import com.ibm.mq.jmqi.handles.Hconn;
import com.ibm.mq.jmqi.handles.Hobj;
import com.ibm.mq.jmqi.handles.Phconn;
import com.ibm.mq.jmqi.handles.Phobj;
import com.ibm.mq.jmqi.handles.Pint;
import com.ibm.msg.client.commonservices.trace.Trace;

/**
 * Sample program to put a message to a queue
 */
public class JmqiGet extends SampleFramework {

  static { // AUTOINSERTEDTRACE
    if (Trace.isOn) {
      Trace.data("com.ibm.mq.jmqi.samples.JmqiGet", "static", "SCCS id", "@(#) MQMBID sn=p904-L171030.1 su=_fzLDAL1hEeet95U9U8zg4w pn=com.ibm.mq.jmqi/src/com/ibm/mq/jmqi/samples/JmqiGet.java");
    }
  }
  // AUTOINSERTEDTRACE

  /** The SCCSID which is expanded when the file is extracted from CMVC */
  public static final String sccsid1 = "@(#) MQMBID sn=p904-L171030.1 su=_fzLDAL1hEeet95U9U8zg4w pn=com.ibm.mq.jmqi/src/com/ibm/mq/jmqi/samples/JmqiGet.java";

  /**
   * Run the sample JmqiGet program
   * 
   * @param args
   */
  public static void main(String[] args) {
    if (Trace.isOn) { // AUTOINSERTEDTRACE
      Trace.entry("com.ibm.mq.jmqi.samples.JmqiGet", "main(String [ ])", new Object[]{args});
    }

    System.out.println("JmqiGet Start");

    try {
      JmqiGet program = new JmqiGet();
      program.perform(args);
    }
    catch (Exception e) {
      if (Trace.isOn) { // AUTOINSERTEDTRACE
        Trace.catchBlock("com.ibm.mq.jmqi.samples.JmqiGet", "main(String [ ])", e);
      }
      e.printStackTrace();
    }

    System.out.println("JmqiGet End");
    if (Trace.isOn) { // AUTOINSERTEDTRACE
      Trace.exit("com.ibm.mq.jmqi.samples.JmqiGet", "main(String [ ])");
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
      Trace.entry(this, "com.ibm.mq.jmqi.samples.JmqiGet", "perform(String [ ])", new Object[]{
          args});
    }

    // ****************************************************************
    // * Get the input parameters
    // ****************************************************************
    setOpenOptions(CMQC.MQOO_INPUT_AS_Q_DEF | CMQC.MQOO_FAIL_IF_QUIESCING);
    parseCommandLineArgs(args, 1, 4);
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

    byte[] buffer = new byte[4096];
    ByteBuffer byteBuffer = ByteBuffer.wrap(buffer);
    int bufferLength = buffer.length;
    Pint dataLength = env.newPint(0);

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
        Trace.throwing(this, "com.ibm.mq.jmqi.samples.JmqiGet", "perform(String [ ])", traceRet1,
            1);
      }
      throw traceRet1;
    }
    Hconn hconn = phconn.getHconn();

    try {
      // ****************************************************************
      // * Open the named message queue for input; exclusive or shared
      // * use of the queue is controlled by the queue definition here
      // ****************************************************************
      MQOD mqod = env.newMQOD();
      mqod.setObjectName(getQueueName());
      int options = getOpenOptions();
      Phobj phobj = env.newPhobj();
      mq.MQOPEN(hconn, mqod, options, phobj, cc, rc);
      if (rc.x != CMQC.MQRC_NONE) {
        Exception traceRet2 = new Exception("MQOPEN ended with reason code " + rc.x);
        if (Trace.isOn) { // AUTOINSERTEDTRACE
          Trace.throwing(this, "com.ibm.mq.jmqi.samples.JmqiGet", "perform(String [ ])", traceRet2
              , 2);
        }
        throw traceRet2;
      }
      Hobj hobj = phobj.getHobj();

      // ****************************************************************
      // * Get messages from the message queue
      // * Loop until there is a failure
      // ****************************************************************
      MQMD mqmd = env.newMQMD();
      mqmd.setFormat(CMQC.MQFMT_STRING);
      mqmd.setCodedCharSetId(1208);

      MQGMO mqgmo = env.newMQGMO();
      mqgmo.setVersion(CMQC.MQGMO_VERSION_2);
      mqgmo.setMatchOptions(CMQC.MQMO_NONE);
      mqgmo.setOptions(CMQC.MQGMO_WAIT | CMQC.MQGMO_CONVERT);
      mqgmo.setWaitInterval(15000);

      boolean more = true;
      while (more) {
        mq.MQGET(hconn, hobj, mqmd, mqgmo, bufferLength, byteBuffer, dataLength, cc, rc);
        switch (rc.x) {
          case CMQC.MQRC_NONE :
            byte[] message = byteBuffer.array();
            String messageText = new String(message, 0, dataLength.x, "UTF-8"); // specifying the charset is probably a good idea!
            System.out.println("message <" + messageText + ">");
            break;
          case CMQC.MQRC_NO_MSG_AVAILABLE :
            System.out.println("no more messages");
            more = false;
            break;
          default : {
            Exception traceRet3 = new Exception("MQGET ended with reason code " + rc.x);
            if (Trace.isOn) { // AUTOINSERTEDTRACE
              Trace.throwing(this, "com.ibm.mq.jmqi.samples.JmqiGet", "perform(String [ ])",
                  traceRet3, 3);
            }
            throw traceRet3;
          }
        }
        byteBuffer.clear();
      }

      // ****************************************************************
      // * Close the source queue (if it was opened)
      // ****************************************************************
      options = getCloseOptions();
      mq.MQCLOSE(hconn, phobj, options, cc, rc);
      if (rc.x != CMQC.MQRC_NONE) {
        Exception traceRet4 = new Exception("MQCLOSE ended with reason code " + rc.x);
        if (Trace.isOn) { // AUTOINSERTEDTRACE
          Trace.throwing(this, "com.ibm.mq.jmqi.samples.JmqiGet", "perform(String [ ])", traceRet4
              , 4);
        }
        throw traceRet4;
      }
    }
    finally {
      if (Trace.isOn) { // AUTOINSERTEDTRACE
        Trace.finallyBlock(this, "com.ibm.mq.jmqi.samples.JmqiGet", "perform(String [ ])");
      }
      // ****************************************************************
      // * Make sure to disconnect from the Queue Manager
      // ****************************************************************
      mq.MQDISC(phconn, cc, rc);
      if (rc.x != CMQC.MQRC_NONE) {
        Exception traceRet5 = new Exception("MQDISC ended with reason code " + rc.x);
        if (Trace.isOn) { // AUTOINSERTEDTRACE
          Trace.throwing(this, "com.ibm.mq.jmqi.samples.JmqiGet", "perform(String [ ])", traceRet5
              , 5);
        }
        throw traceRet5;
      }
    }
    if (Trace.isOn) { // AUTOINSERTEDTRACE
      Trace.exit(this, "com.ibm.mq.jmqi.samples.JmqiGet", "perform(String [ ])");
    }
    return;
  }
}
