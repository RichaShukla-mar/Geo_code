package net.apmoller.maersk.services.fct.geowrite.messaging;

import java.io.IOException;
import java.io.StringReader;
import java.util.Properties;

import javax.ejb.EJB;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.listener.SessionAwareMessageListener;
import org.springframework.stereotype.Service;

import com.ibm.jms.JMSBytesMessage;
import com.ibm.jms.JMSTextMessage;

import net.apmoller.maersk.services.fct.geowrite.DaoConstants;
import net.apmoller.maersk.services.fct.geowrite.GeoWriteBackDaoRemote;
import net.apmoller.services.cmd.schemas.PublishFacilityCode;

// TODO: Auto-generated Javadoc
/**
 * The listener interface for receiving jmsMessage events. The class that is
 * interested in processing a jmsMessage event implements this interface, and
 * the object created with that class is registered with a component using the
 * component's <code>addJmsMessageListener<code> method. When the jmsMessage
 * event occurs, that object's appropriate method is invoked.
 *
 * @see JmsMessageEvent
 */
@Service
/**
 * Listener Implement Spring SessionAwareMessageListener Interface
 *
 */
public class JmsMessageListener implements SessionAwareMessageListener {

	/** The Constant LOGGER. */
	private static final Logger LOGGER = Logger.getLogger(JmsMessageListener.class);

	/** The producer template. */
	@Autowired
	@Qualifier("producerTemplate")
	JmsTemplate producerTemplate;

	/**
	 * Instantiates a new jms message listener.
	 */
	public JmsMessageListener() {
		super();
		LOGGER.info("Spring BvdJmsMessageListener Starts");
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.springframework.jms.listener.SessionAwareMessageListener#onMessage(
	 * javax.jms.Message, javax.jms.Session)
	 */
	@Override
	public void onMessage(Message message, Session session) throws JMSException {
		LOGGER.info("************* On GeoWriteBack Message Starts **************");
		LOGGER.info("************* Message Recieved **************");
		String tMessage = null;
		if (message instanceof JMSBytesMessage) {
			tMessage = getTextFromBytesMessage(message);
		} else if (message instanceof JMSTextMessage) {
			tMessage = extractMessageFromTextMessage(message);
		}
		LOGGER.info("Message ID recieved :: " + message.getJMSMessageID());
		LOGGER.info(tMessage);
		try {
			PublishFacilityCode publishgeocode = getGeoCodeFromQueueMessage(tMessage);

			LOGGER.info("Calling the Geo Write Back: ");
			InitialContext ic = new InitialContext();

			GeoWriteBackDaoRemote remote = (GeoWriteBackDaoRemote) ic
					.lookup("GeoWriteBackDao\\#net/apmoller/maersk/services/fct/geowrite/GeoWriteBackDaoRemote");
			if (publishgeocode.getFacilityID().getFacilityGEOId()!=null) {
				LOGGER.info("GEO CODE RECIEVED " + publishgeocode.getFacilityID().getFacilityGEOId());
				remote.geoWriteBack(publishgeocode.getFacilityID().getFacilityGEOId(),true);
			}
			LOGGER.info("*************** On MEssage End **************");
		} catch (JAXBException e) {
			LOGGER.warn(e.getMessage());
		} catch (Exception e) {
			LOGGER.warn(e.getMessage());
		}

	}

	/**
	 * Gets the text from bytes message.
	 *
	 * @param inmsg
	 *            the inmsg
	 * @return the text from bytes message
	 */
	private String getTextFromBytesMessage(Message inmsg) {
		JMSBytesMessage msg = (JMSBytesMessage) inmsg;
		int length = 0;
		String text = null;
		try {
			length = new Long(msg.getBodyLength()).intValue();
			byte[] b = new byte[length];
			msg.readBytes(b, length);
			text = new String(b, "UTF-8");
		} catch (Exception e) {
			LOGGER.warn(e.getMessage());
		}
		return text;
	}

	/**
	 * Extract message from text message.
	 *
	 * @param inmsg
	 *            the inmsg
	 * @return the string
	 */
	private String extractMessageFromTextMessage(Message inmsg) {
		String text = null;
		try {
			text = ((JMSTextMessage) inmsg).getText();
		} catch (JMSException e) {
			LOGGER.warn(e.getMessage());
		}
		return text;
	}

	/**
	 * Gets the customer code from queue message.
	 *
	 * @param text
	 *            the text
	 * @return the customer code from queue message
	 * @throws JAXBException
	 *             the JAXB exception
	 */
	private PublishFacilityCode getGeoCodeFromQueueMessage(String text) throws JAXBException {
		JAXBContext jaxbContext = JAXBContext.newInstance(PublishFacilityCode.class);
		Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
		StringReader reader = new StringReader(text);
		PublishFacilityCode publfacilitycode = (PublishFacilityCode) unmarshaller.unmarshal(reader);

		return publfacilitycode;
	}
}
