package net.apmm.mdm.ops.geo.mq;


import java.io.IOException;
import java.util.Hashtable;
import java.util.Properties;

import javax.jms.DeliveryMode;
import javax.jms.JMSException;
import javax.jms.QueueConnection;
import javax.jms.QueueSender;
import javax.jms.QueueSession;
import javax.jms.Session;
import javax.jms.TextMessage;

import com.ibm.mq.MQMessage;
import com.ibm.mq.headers.MQMD;
import com.ibm.mq.headers.MQHeaderList;
import lombok.extern.slf4j.Slf4j;


import com.ibm.mq.MQException;
import com.ibm.mq.MQMessage;
import com.ibm.mq.MQPutMessageOptions;
import com.ibm.mq.MQQueue;
import com.ibm.mq.MQQueueManager;
import com.ibm.mq.constants.CMQC;
import com.ibm.mq.constants.MQConstants;
import com.ibm.mq.jms.JMSC;
import com.ibm.mq.jms.MQQueueConnectionFactory;
import com.ibm.msg.client.jms.JmsConstants;

@Slf4j
public class MQPublisher {

    private Properties properties;

    public MQPublisher(Properties properties) {
        this.properties = properties;
    }


    public int publishMessages(String message) {
        log.info("Publish message strated "); int status=1;


        try {
            status=1;
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
            //textMessage.setJMSExpiration(-1);//message expiration
            textMessage.setJMSExpiration(0);//message expiration
            textMessage.setJMSDeliveryMode(DeliveryMode.PERSISTENT); //message delivery mode either persistent or non-persistemnt

            /*Create sender queue */
            QueueSender queueSender = queueSession.createSender(queueSession.createQueue(properties.getProperty("REQUEST_QUEUE")));
            //queueSender.setTimeToLive(2*1000);
            log.info("Geo Message to sent with header ::"+textMessage.toString() );
            queueSender.setTimeToLive(0);
            queueSender.send(textMessage);

            /*After sending a message we get message id */
            log.info("after sending a message we get message id "+ textMessage.getJMSMessageID());
            String jmsCorrelationID = " JMSCorrelationID = '" + textMessage.getJMSMessageID() + "'";

            queueSender.close();
            //queueReceiver.close();
            queueSession.close();
            queueConnection.close();
            status=0;


        } catch (JMSException e) { status=1; log.error(e.getMessage());
            e.printStackTrace(); } catch (Exception e) { status=1;
            log.error(e.getMessage()); e.printStackTrace(); } return status;

    }
    /*
     * public int publishMessages(String messages) {
     * log.info("Publish message strated "); int status=1; // Create a connection to
     * the queue manager Hashtable<String, Object> props = new Hashtable<String,
     * Object>(); props.put(MQConstants.CHANNEL_PROPERTY,
     * properties.get("CHANNEL")); props.put(MQConstants.PORT_PROPERTY,
     * Integer.parseInt(properties.getProperty("MQ_PORT")));
     * props.put(MQConstants.HOST_NAME_PROPERTY, properties.getProperty("MQ_HOST"));
     *
     * String qManager = properties.getProperty("QUEUE_MANAGER"); String queueName =
     * properties.getProperty("REQUEST_QUEUE"); log.info("Queue Manager "+
     * qManager); log.info("Queue Name "+queueName); MQQueueManager qMgr = null; try
     * { qMgr = new MQQueueManager(qManager, props);
     *
     * // MQOO_OUTPUT = Open the queue to put messages. The queue is opened for use
     * with subsequent MQPUT calls. // MQOO_INPUT_AS_Q_DEF = Open the queue to get
     * messages using the queue-defined default. // The queue is opened for use with
     * subsequent MQGET calls. The type of access is either // shared or exclusive,
     * depending on the value of the DefInputOpenOption queue attribute. // int
     * openOptions = MQConstants.MQOO_OUTPUT | MQConstants.MQOO_INPUT_AS_Q_DEF; int
     * openOptions= CMQC.MQOO_OUTPUT + CMQC.MQOO_FAIL_IF_QUIESCING;
     *
     * // creating destination MQQueue queue = qMgr.accessQueue(queueName,
     * openOptions);
     *
     * // specify the message options... MQPutMessageOptions pmo = new
     * MQPutMessageOptions(); // default // MQPMO_ASYNC_RESPONSE = The
     * MQPMO_ASYNC_RESPONSE option requests that an MQPUT or MQPUT1 operation // is
     * completed without the application waiting for the queue manager to complete
     * the call. // Using this option can improve messaging performance,
     * particularly for applications using client bindings. pmo.options =
     * MQConstants.MQPMO_SYNC_RESPONSE;
     *
     * // create message MQMessage message = new MQMessage();
     *
     * // MQFMT_STRING = The application message data can be either an SBCS string
     * (single-byte character set), // or a DBCS string (double-byte character set).
     * Messages of this format can be converted // if the MQGMO_CONVERT option is
     * specified on the MQGET call. message.format = MQConstants.MQFMT_STRING;
     * message.writeString(messages); queue.put(message, pmo); status=0;
     * queue.close();
     *
     *
     * } catch (MQException e) {
     * log.error("MQException Error while sending XML thru MQ "+ e.getMessage());
     * status = 1; } catch (IOException e) {
     * log.error("IOException Error while sending XML thru MQ "+ e.getMessage());
     * status = 1; }catch(Exception e) {
     * log.error("Exception Error while sending XML thru MQ "+ e.getMessage()); }
     * finally { try { qMgr.disconnect(); } catch (MQException e) { status = 1; } }
     * return status ; }
     */
}
