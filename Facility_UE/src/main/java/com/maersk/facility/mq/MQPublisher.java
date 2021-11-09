package com.maersk.facility.mq;

import java.util.Properties;

import javax.jms.DeliveryMode;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Queue;
import javax.jms.QueueConnection;
import javax.jms.QueueReceiver;
import javax.jms.QueueSender;
import javax.jms.QueueSession;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.axis.transport.jms.JMSConstants;
import org.apache.log4j.Logger;
//import org.apache.openjpa.lib.log.Log;

import com.ibm.mq.jms.JMSC;
import com.ibm.mq.jms.MQQueueConnectionFactory;
import com.ibm.msg.client.jms.JmsConstants;

public class MQPublisher {
	private static final Logger log = Logger.getLogger(MQPublisher.class);
	  private Properties properties;
	  private QueueSender queueSender = null;
	  private QueueReceiver queueReceiver = null;
	  private QueueSession queueSession = null;
	  private QueueConnection queueConnection = null;

	  
	public MQPublisher(Properties properties) {
		this.properties = properties;
	}

	
	  public int publishMessages(String message) {
		  log.info("Publish message strated "); 
		  int status=1;
		  
		  
		  try {
	          /*MQ Configuration*/
	          MQQueueConnectionFactory mqQueueConnectionFactory = new MQQueueConnectionFactory();
	          //mqQueueConnectionFactory.setConnectionNameList("SCRBCSEDEFRM632(1434),scrbcsederue632(1434)");
	          mqQueueConnectionFactory.setConnectionNameList(properties.getProperty("MQ_HOST"));
	          log.info("MQ Host is ::"+mqQueueConnectionFactory.getConnectionNameList());
	          // QueueConnection queueConnection = mqQueueConnectionFactory.createQueueConnection("infapprd",null);


	          mqQueueConnectionFactory.setChannel(properties.getProperty("CHANNEL"));//communications link
	          //mqQueueConnectionFactory.setPort(1434);
	          log.info("MQ Channel is ::"+mqQueueConnectionFactory.getChannel());
	          mqQueueConnectionFactory.setQueueManager(properties.getProperty("QUEUE_MANAGER"));//service provider
	          log.info("Queue Manager is ::"+mqQueueConnectionFactory.getQueueManager());
	          mqQueueConnectionFactory.setTransportType(JMSC.MQJMS_TP_CLIENT_MQ_TCPIP);
	         mqQueueConnectionFactory.setIntProperty(JmsConstants.JMS_IBM_MQMD_EXPIRY, -1);
	          
	         
	          /*Create Connection */
	          //QueueConnection queueConnection = mqQueueConnectionFactory.createQueueConnection();
	          QueueConnection queueConnection = mqQueueConnectionFactory.createQueueConnection(properties.getProperty("MQ_USER"),null);
	          
	          
	          queueConnection.start();

	          /*Create session */
	          QueueSession queueSession = queueConnection.createQueueSession(false, Session.AUTO_ACKNOWLEDGE);
	          
	          //String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><SMDSFacilityEvent><Facility><FacilityEventType>Update</FacilityEventType><FacilityType>Operational Facility</FacilityType><FacilityExtExposed>Y</FacilityExtExposed><FacilityExtOwned>N</FacilityExtOwned><FacilityName>PPGEO CG</FacilityName><FacilityStatus>Active</FacilityStatus><FacilityUrl></FacilityUrl></Facility><FacilityAddress><FacilityAddress><HouseNumber>9</HouseNumber><Street>STRANDVAEGEN</Street><City>Stockholm</City><PostalCode>114 56</PostalCode><PoBox>Sweden</PoBox><District>Stockholm</District><Country>SE</Country><AddressLine2></AddressLine2><AddressLine3>HEDVIG ELEONORA</AddressLine3><Latitude>59.3293</Latitude><Longitude>8.0686</Longitude></FacilityAddress></FacilityAddress><FacilityDetails><FacilityDetail><WeightLimitCraneKg></WeightLimitCraneKg><WeightLimitYardKg></WeightLimitYardKg><VesselAgent></VesselAgent><GPSFlag></GPSFlag><GSMFlag></GSMFlag><OceanFreightPricing>N</OceanFreightPricing><FacilityType><Name>Cold Store</Name><Code>FCT_TYPE.COLD_STORE</Code><MasterType>FCT_TYPE</MasterType><ValidThroughDate>2017-06-30T00:00:00.000Z</ValidThroughDate></FacilityType></FacilityDetail></FacilityDetails><FacilityAlternateCodes><FacilityAlternateCode><AltCode>SESTO58</AltCode><TypeCode>RKST</TypeCode></FacilityAlternateCode><FacilityAlternateCode><AltCode>STO58</AltCode><TypeCode>RKTS</TypeCode></FacilityAlternateCode><FacilityAlternateCode><AltCode>HOFI0CNH4WUIR</AltCode><TypeCode>GEOID</TypeCode></FacilityAlternateCode><FacilityAlternateCode><AltCode>1234</AltCode><TypeCode>CUSTOMSLOC</TypeCode></FacilityAlternateCode></FacilityAlternateCodes><FacilityOpeningHours/><FacilityTransportModes/><FacilityServices><FacilityService><ServiceDesc>New</ServiceDesc><ServiceName>PayG</ServiceName><ValidThroughDate>2017-06-30T00:00:00.000Z</ValidThroughDate></FacilityService></FacilityServices><FacilityContactDetails/></SMDSFacilityEvent>";
	          TextMessage textMessage = queueSession.createTextMessage(message);
	          //textMessage.setJMSReplyTo(queue);
	          textMessage.setJMSType("mcd://xmlns");//message type
	          textMessage.setJMSExpiration(0);//message expiration
	          textMessage.setJMSDeliveryMode(DeliveryMode.PERSISTENT); //message delivery mode either persistent or non-persistemnt
	          

	          /*Create sender queue */
	          QueueSender queueSender = queueSession.createSender(queueSession.createQueue(properties.getProperty("REQUEST_QUEUE")));
	          queueSender.setTimeToLive(0);
	          
	          log.info("JMS Message Header is "+textMessage.toString());
	          queueSender.send(textMessage);
	          
	          
	          /*After sending a message we get message id */
	          log.info("after sending a message we get message id "+ textMessage.getJMSMessageID());
	          String jmsCorrelationID = " JMSCorrelationID = '" + textMessage.getJMSMessageID() + "'";
	          queueSender.close();
	          //queueReceiver.close();
	          queueSession.close();
	          queueConnection.close();
	          status=0;


	      } catch (JMSException e) { 
	    	  status=1; 
	    	  log.error(e.getMessage());
		  e.printStackTrace(); 
		  } catch (Exception e) { 
			  status=1;
		  log.error(e.getMessage()); 
		  e.printStackTrace(); 
		  } 
		  return status;
		  
		  }
}
