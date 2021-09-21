package net.apmoller.maersk.services.fct.geowrite.messaging;

import java.io.File;
import java.io.IOException;
import java.util.Properties;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.Stateless;
import javax.interceptor.Interceptors;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.apache.commons.configuration.CompositeConfiguration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.ejb.interceptor.SpringBeanAutowiringInterceptor;
import org.springframework.jms.core.JmsTemplate;

import com.ibm.mq.jms.MQQueueConnectionFactory;
import com.ibm.mq.jms.MQTopic;
import com.ibm.mq.jms.MQTopicConnection;
import com.ibm.mq.jms.MQTopicConnectionFactory;
import com.ibm.mq.jms.MQTopicSession;
import com.ibm.msg.client.jms.JmsConstants;
import com.ibm.msg.client.wmq.common.CommonConstants;

import net.apmoller.maersk.services.fct.geowrite.DaoConstants;

// TODO: Auto-generated Javadoc
/**
 * Session Bean implementation class CustomerPublishManager.
 */

@Stateless(mappedName="GeoPublishManager")
@Interceptors(SpringBeanAutowiringInterceptor.class)
public class GeoPublishManager implements GeoPublishManagerRemote, GeoPublishManagerLocal {

	/** The Constant LOGGER. */
	private static final Logger LOGGER = Logger.getLogger(GeoPublishManager.class);

	
	/** The connection factory. */
	@Autowired
	@Qualifier("connectionFactory")
	ConnectionFactory connectionFactory;

	/** The producer template. */
	@Qualifier("producerTemplate")
	JmsTemplate producerTemplate;

	/**
	 * Default constructor.
	 */
	public GeoPublishManager() {
		LOGGER.info("EJB inited");
	}

	/**
	 * Inits the logger.
	 */
	private void initLogger() {
		org.apache.logging.log4j.core.LoggerContext context = (org.apache.logging.log4j.core.LoggerContext) LogManager
				.getContext(false);
		File file = new File(config.getString("fct.geowriteback.external.log.config.file"));
		if (file.exists()) {
			context.setConfigLocation(file.toURI());
		}
	}

	/**
	 * Inits the properties.
	 */
	@PostConstruct
	public void initEJB() {
		LOGGER.info("EJB  @PostConstruct");
		initProperties();
		initLogger();
	}

	/**
	 * Destroy ejb.
	 */
	@PreDestroy
	public void destroyEJB() {
		LOGGER.info("EJB  @PreDestry");
		if (session != null) {

			try {
				session.close();
			} catch (JMSException e) {
				// TODO Auto-generated catch block
				LOGGER.fatal(e);
			}
		}

		if (conn != null) {
			try {
				conn.stop();
			} catch (JMSException e) {
				// TODO Auto-generated catch block
				LOGGER.fatal(e);
			}
			try {
				conn.close();
			} catch (JMSException e) {
				LOGGER.fatal(e);
			}
		}
	}
	CompositeConfiguration config = null;

	/**
	 * Inits the properties.
	 */
	private void initProperties() {

		try {
			LOGGER.info("#####LOADING Internal and external proerties###");
			InitialContext ic = new InitialContext();
			LOGGER.info("CMD External Environment Property Location : "+ic.lookup("FCT_ENVIRONMENT_CONFIG_LOCATION"));
			LOGGER.info("CMD External Business Property Location : "+ic.lookup("FCT_BUSINESS_CONFIG_LOCATION"));
			
			config = new CompositeConfiguration();
			config.addConfiguration(
					new PropertiesConfiguration((String)ic.lookup("FCT_ENVIRONMENT_CONFIG_LOCATION")),true);
		} catch ( ConfigurationException | NamingException e) {
			LOGGER.fatal(e.getLocalizedMessage());
		}

	}

	/** The conn. */
	MQTopicConnection conn = null;

	/** The session. */
	MQTopicSession session = null;

	/*
	 * (non-Javadoc)
	 * 
	 * @see net.apmoller.cmd.publishcustomer.ejb.BvdPublishManagerRemote#hBeat()
	 */
	@Override
	public String hBeat() throws NamingException {
		String[] configFiles = { "/beanRefContext.xml", "/app-config.xml" };
		ApplicationContext context = new ClassPathXmlApplicationContext(configFiles);
		return "SUCCESS";
	}

}
