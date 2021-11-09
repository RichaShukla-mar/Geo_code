package com.maersk.facility.mq;

import java.util.Hashtable;
import java.util.Properties;

// Include the MQ package
import com.ibm.mq.MQC;
import com.ibm.mq.MQException;
import com.ibm.mq.MQGetMessageOptions;
import com.ibm.mq.MQMessage;
import com.ibm.mq.MQQueue;
import com.ibm.mq.MQQueueManager;
import com.ibm.mq.constants.MQConstants;

public class MQBrowse {

  private MQQueueManager qMgr;
 private Properties properties; 

  public MQBrowse(Properties properties) {
   this.properties = properties;
  }


  public String browse(String facilityName) {
    String output = "";
    try {

      Hashtable<String, Object> props = new Hashtable<String, Object>();
      props.put(MQConstants.CHANNEL_PROPERTY, properties.get("CHANNEL"));
      props.put(MQConstants.PORT_PROPERTY, Integer.parseInt(properties.getProperty("MQ_PORT")));
      props.put(MQConstants.HOST_NAME_PROPERTY, properties.getProperty("MQ_HOST"));

      String qManager = properties.getProperty("QUEUE_MANAGER");
      String queueName = properties.getProperty("RKST_RESPONSE_QUEUE");
      int openOptions = MQC.MQOO_INPUT_EXCLUSIVE | MQC.MQOO_BROWSE;
      qMgr = new MQQueueManager(qManager, props);
      MQQueue myQueue = qMgr.accessQueue(queueName, openOptions, null, null, null);

     
      MQGetMessageOptions gmo = new MQGetMessageOptions();
      gmo.options = MQC.MQGMO_WAIT | MQC.MQGMO_BROWSE_FIRST;
      MQMessage myMessage = new MQMessage();

      boolean done = false;
      do {
        try {
          myMessage.clearMessage();
          myMessage.correlationId = MQC.MQCI_NONE;
          myMessage.messageId = MQC.MQMI_NONE;
          myQueue.get(myMessage, gmo);
          String msg = myMessage.readString(myMessage.getMessageLength());

          String vsCode = "";

          if (msg.contains("<VESSEL>") && msg.contains("</VESSEL>")) {
            vsCode = msg.split("<VESSEL>")[1].split("</VESSEL>")[0];
            if (vsCode.equalsIgnoreCase(facilityName)) {
              System.out.println("Browsed message: " + msg);
              if (msg.contains("<Replymessage>") && msg.contains("</Replymessage>")) {
                output = msg.split("<Replymessage>")[1].split("</Replymessage>")[0].trim();
              }
              gmo.options = MQC.MQGMO_MSG_UNDER_CURSOR;
              myQueue.get(myMessage, gmo);
              done = true;

            }
          }
          gmo.options = MQC.MQGMO_WAIT | MQC.MQGMO_BROWSE_NEXT;

        } catch (MQException ex) {
          System.out.println("MQ exception: CC = " + ex.completionCode + " RC = " + ex.reasonCode);
          done = true;
        } catch (java.io.IOException ex) {
          System.out.println("Java exception: " + ex);
          done = true;
        }

      } while (!done);

      myQueue.close();
      qMgr.disconnect();
      return output;
    } catch (MQException ex) {
      System.out.println("MQ exception: CC = " + ex.completionCode + " RC = " + ex.reasonCode);
    }
    return null;
  }

}
