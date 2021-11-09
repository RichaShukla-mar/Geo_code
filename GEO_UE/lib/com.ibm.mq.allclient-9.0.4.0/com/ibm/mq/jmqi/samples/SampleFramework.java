/*
 *   <copyright 
 *   notice="lm-source-program" 
 *   pids="5724-H72,5655-R36,5655-L82,5724-L26," 
 *   years="2008,2012" 
 *   crc="4222035255" > 
 *   Licensed Materials - Property of IBM  
 *    
 *   5724-H72,5655-R36,5655-L82,5724-L26, 
 *    
 *   (C) Copyright IBM Corp. 2008, 2012 All Rights Reserved.  
 *    
 *   US Government Users Restricted Rights - Use, duplication or  
 *   disclosure restricted by GSA ADP Schedule Contract with  
 *   IBM Corp.  
 *   </copyright> 
 */
package com.ibm.mq.jmqi.samples;
// Trace instrumented 10-Apr-2014 10:15:18  // AUTOINSERTEDTRACE
import com.ibm.msg.client.commonservices.trace.Trace;

import com.ibm.mq.constants.CMQC;
import com.ibm.mq.exits.MQCD;
import com.ibm.mq.jmqi.JmqiEnvironment;
import com.ibm.mq.jmqi.JmqiException;
import com.ibm.mq.jmqi.JmqiFactory;
import com.ibm.mq.jmqi.JmqiMQ;
import com.ibm.mq.jmqi.MQCNO;

/**
 * Framework for the sample Jmqi programs
 */
public class SampleFramework {
  static {  // AUTOINSERTEDTRACE
    if (Trace.isOn) {
      Trace.data( "com.ibm.mq.jmqi.samples.SampleFramework", "static", "SCCS id",
        "@(#) MQMBID sn=p904-L171030.1 su=_fzLDAL1hEeet95U9U8zg4w pn=com.ibm.mq.jmqi/src/com/ibm/mq/jmqi/samples/SampleFramework.java");
    }
  }
     // AUTOINSERTEDTRACE


  /** The SCCSID which is expanded when the file is extracted from CMVC */
  public static final String sccsid = "@(#) MQMBID sn=p904-L171030.1 su=_fzLDAL1hEeet95U9U8zg4w pn=com.ibm.mq.jmqi/src/com/ibm/mq/jmqi/samples/SampleFramework.java";

  private String queueManagerName = "";
  private String queueName = "SYSTEM.DEFAULT.LOCAL.QUEUE";
  private String channelName;
  private String transport;
  private String connectionName;

  private int openOptions;
  private int closeOptions = CMQC.MQCO_NONE;
  private String targetQueueManager = "";
  private String dynamicQueue = "";

  /**
   * Extract the Queue Manager name and the Queue name from the command line arguments
   * 
   * @param args
   * @param min
   * @param max
   * @throws Exception
   */
  public void parseCommandLineArgs(String[] args, int min, int max) throws Exception {
    if (Trace.isOn) {  // AUTOINSERTEDTRACE
      Trace.entry( this, "com.ibm.mq.jmqi.samples.SampleFramework",
        "parseCommandLineArgs(String [ ],int,int)", new Object[]{ args, Integer.valueOf(min),
        Integer.valueOf(max)});
    }

    if (args.length < min) {
      Exception traceRet1 =  new Exception("Not enough arguments");
      if (Trace.isOn) {  // AUTOINSERTEDTRACE
        Trace.throwing( this, "com.ibm.mq.jmqi.samples.SampleFramework",
          "parseCommandLineArgs(String [ ],int,int)", traceRet1, 1);
      }
      throw traceRet1;
    }
    else if (args.length > max) {
      Exception traceRet2 =  new Exception("Too many arguments");
      if (Trace.isOn) {  // AUTOINSERTEDTRACE
        Trace.throwing( this, "com.ibm.mq.jmqi.samples.SampleFramework",
          "parseCommandLineArgs(String [ ],int,int)", traceRet2, 2);
      }
      throw traceRet2;
    }

    if (args.length >= 1) {
      queueName = args[0];
    }
    if (args.length >= 2) {
      queueManagerName = args[1];
    }
    if (args.length >= 3) {
      openOptions = Integer.parseInt(args[2]);
    }
    if (args.length >= 4) {
      closeOptions = Integer.parseInt(args[3]);
    }
    if (args.length >= 5) {
      targetQueueManager = args[4];
    }
    if (args.length >= 6) {
      dynamicQueue = args[5];
    }
    if (Trace.isOn) {  // AUTOINSERTEDTRACE
      Trace.exit( this, "com.ibm.mq.jmqi.samples.SampleFramework",
        "parseCommandLineArgs(String [ ],int,int)");
    }
    return;
  }

  /**
   * First look for the "MQSERVER" environment variable, then look for the "MQSERVER" environment
   * variable. Then extract the channelName and connectionName from the "MQSERVER" string
   * 
   * The format of the "MQSERVER" string is: MQSERVER=ChannelName/TransportType/ConnectionName
   * 
   * @throws Exception
   */
  public void parseSystemProperties() throws Exception {
    if (Trace.isOn) {  // AUTOINSERTEDTRACE
      Trace.entry( this, "com.ibm.mq.jmqi.samples.SampleFramework", "parseSystemProperties()");
    }

    String mqserver = null;

    // First try the "MQSERVER" environment variable.
    // NOTE: "System.getenv()" is not supported on all JMVs.
    // For example, the IBM 1.4.2 JVM throws the following:
    // java.lang.Error: getenv no longer supported, use properties and -D instead: MQSERVER
    try {
      mqserver = System.getenv("MQSERVER");
    }
    catch (Throwable t) {
      if (Trace.isOn) {  // AUTOINSERTEDTRACE
        Trace.catchBlock( this, "com.ibm.mq.jmqi.samples.SampleFramework",
          "parseSystemProperties()", t);
      }
      // Intentional no-op
      // t.printStackTrace();
    }

    // If we did not find the "MQSERVER" environment variable.
    // then look for the "MQSERVER" system property
    if (mqserver == null) {
      mqserver = System.getProperty("MQSERVER");
    }

    // Extract the channelName and connectionName from the "MQSERVER" string
    if (mqserver != null) {

      String[] words = mqserver.split("/");

      if (words.length == 3) {
        channelName = words[0];
        transport = words[1];
        connectionName = words[2];

        if ("tcp".equals(transport.toLowerCase()) == false) {
          Exception traceRet1 =  new Exception("transport is not valid");
          if (Trace.isOn) {  // AUTOINSERTEDTRACE
            Trace.throwing( this, "com.ibm.mq.jmqi.samples.SampleFramework",
              "parseSystemProperties()", traceRet1, 1);
          }
          throw traceRet1;
        }
      }
      else {
        Exception traceRet2 =  new Exception("\"MQSERVER\" is not valid");
        if (Trace.isOn) {  // AUTOINSERTEDTRACE
          Trace.throwing( this, "com.ibm.mq.jmqi.samples.SampleFramework",
            "parseSystemProperties()", traceRet2, 2);
        }
        throw traceRet2;
      }
    }
    if (Trace.isOn) {  // AUTOINSERTEDTRACE
      Trace.exit( this, "com.ibm.mq.jmqi.samples.SampleFramework", "parseSystemProperties()");
    }
    return;
  }

  /**
   * Return an instance of an JmqiMQ
   * 
   * @param env
   * @return an instance of a JmqiMQ
   * @throws JmqiException
   */
  public JmqiMQ getMQInstance(JmqiEnvironment env) throws JmqiException {
    if (Trace.isOn) {  // AUTOINSERTEDTRACE
      Trace.entry( this, "com.ibm.mq.jmqi.samples.SampleFramework",
        "getMQInstance(JmqiEnvironment)", new Object[]{ env});
    }

    int bindingType = JmqiFactory.LOCAL_SERVER;
    if ((channelName != null) && (connectionName != null)) {
      bindingType = JmqiFactory.REMOTE;
    }

    int options = 0;
    JmqiMQ mq = env.getMQI(bindingType, options);
    if (Trace.isOn) {  // AUTOINSERTEDTRACE
      Trace.exit( this, "com.ibm.mq.jmqi.samples.SampleFramework",
        "getMQInstance(JmqiEnvironment)", mq);
    }
    return mq;
  }

  /**
   * @return the name of the Queue Manager
   */
  public String getQueueManagerName() {
    if (Trace.isOn) {  // AUTOINSERTEDTRACE
      Trace.data( this, "com.ibm.mq.jmqi.samples.SampleFramework", "getQueueManagerName()",
        "getter", queueManagerName);
    }
    return queueManagerName;
  }

  /**
   * @return the name of the Queue
   */
  public String getQueueName() {
    if (Trace.isOn) {  // AUTOINSERTEDTRACE
      Trace.data( this, "com.ibm.mq.jmqi.samples.SampleFramework", "getQueueName()", "getter",
        queueName);
    }
    return queueName;
  }

  /**
   * @param openOptions
   */
  public void setOpenOptions(int openOptions) {
    if (Trace.isOn) {  // AUTOINSERTEDTRACE
      Trace.data( this, "com.ibm.mq.jmqi.samples.SampleFramework", "setOpenOptions(int)", "setter",
        Integer.valueOf(openOptions));
    }
    this.openOptions = openOptions;
  }

  /**
   * @return openOptions
   */
  public int getOpenOptions() {
    if (Trace.isOn) {  // AUTOINSERTEDTRACE
      Trace.data( this, "com.ibm.mq.jmqi.samples.SampleFramework", "getOpenOptions()", "getter",
        Integer.valueOf(openOptions));
    }
    return openOptions;
  }

  /**
   * @return closeOptions
   */
  public int getCloseOptions() {
    if (Trace.isOn) {  // AUTOINSERTEDTRACE
      Trace.data( this, "com.ibm.mq.jmqi.samples.SampleFramework", "getCloseOptions()", "getter",
        Integer.valueOf(closeOptions));
    }
    return closeOptions;
  }

  /**
   * @param closeOptions
   */
  public void setCloseOptions(int closeOptions) {
    if (Trace.isOn) {  // AUTOINSERTEDTRACE
      Trace.data( this, "com.ibm.mq.jmqi.samples.SampleFramework", "setCloseOptions(int)",
        "setter", Integer.valueOf(closeOptions));
    }
    this.closeOptions = closeOptions;
  }

  /**
   * @return targetQueueManager
   */
  public String getTargetQueueManagerName() {
    if (Trace.isOn) {  // AUTOINSERTEDTRACE
      Trace.data( this, "com.ibm.mq.jmqi.samples.SampleFramework", "getTargetQueueManagerName()",
        "getter", targetQueueManager);
    }
    return targetQueueManager;
  }

  /**
   * @return dynamicQueue
   */
  public String getDynamicQueueName() {
    if (Trace.isOn) {  // AUTOINSERTEDTRACE
      Trace.data( this, "com.ibm.mq.jmqi.samples.SampleFramework", "getDynamicQueueName()",
        "getter", dynamicQueue);
    }
    return dynamicQueue;
  }

  /**
   * Compute the MQCNO from the appropriate properties and cache the result in an instance field.
   * 
   * @param env
   * 
   * @return The Connect options to be used when connecting to the Queue manager
   */
  public MQCNO getConnectOptions(JmqiEnvironment env) {
    if (Trace.isOn) {  // AUTOINSERTEDTRACE
      Trace.entry( this, "com.ibm.mq.jmqi.samples.SampleFramework",
        "getConnectOptions(JmqiEnvironment)", new Object[]{ env});
    }
    MQCNO connectOptions = env.newMQCNO();
    if ((connectionName != null) && (channelName != null)) {
      MQCD clientConn = env.newMQCD();
      connectOptions.setClientConn(clientConn);
      clientConn.setConnectionName(connectionName);
      clientConn.setChannelName(channelName);
    }
    if (Trace.isOn) {  // AUTOINSERTEDTRACE
      Trace.exit( this, "com.ibm.mq.jmqi.samples.SampleFramework",
        "getConnectOptions(JmqiEnvironment)", connectOptions);
    }
    return connectOptions;
  }
}
