package com.maersk.facility.userexit.util;

import java.util.Hashtable;

import javax.jms.JMSException;
import javax.jms.Queue;
import javax.jms.QueueConnection;
import javax.jms.QueueConnectionFactory;
import javax.jms.QueueSender;
import javax.jms.QueueSession;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.apache.log4j.Logger;

import com.maersk.facility.userexit.SendForApprovalHandler;

public class FacilityMQUpsert {
	
	
	/**
	 * 
	 */
	public FacilityMQUpsert() {
		super();
	}
	public final static String JNDI_FACTORY="weblogic.jndi.WLInitialContextFactory";
	 // Defines the JMS context factory.
	 public final static String JMS_FACTORY="siperian.mrm.jms.xaconnectionfactory";
	 // Defines the queue.
	 public final static String QUEUE="queue/GeoQueue";
	 
	 public final static String WEBLOGIC_USER_NAME="weblogic";
	 public final static String WEBLOGIC_USER_PWD="Weblogic2018ipc";
	 
	 private QueueConnectionFactory qconFactory;
	 private QueueConnection qcon;
	 private QueueSession qsession;
	 private QueueSender qsender;
	 private Queue queue;
	 private TextMessage msg;
	
	private static final Logger LOG = Logger.getLogger((String)FacilityMQUpsert.class.getName());
	 private void sendJMSmsg() {
			// TODO Auto-generated method stub
		 
		 
	    	LOG.info("Inside sendJMSmsg "); 
	    	try{
	    			Hashtable env = new Hashtable();
	    			env.put(Context.INITIAL_CONTEXT_FACTORY, JNDI_FACTORY);
	    			env.put(Context.PROVIDER_URL, "t3://10.255.84.170:7001");
	    			env.put(Context.SECURITY_PRINCIPAL,WEBLOGIC_USER_NAME);
	    			env.put(Context.SECURITY_CREDENTIALS,WEBLOGIC_USER_PWD);
	    			InitialContext ctx = new InitialContext(env);
	    			LOG.info("InitialContext Created... ");
	    			qconFactory = (QueueConnectionFactory) ctx.lookup(JMS_FACTORY);
	    			LOG.info("ConnectionFactory lookup done... ");
	    		    qcon = qconFactory.createQueueConnection();
	    		    LOG.info("Queue Connection Created... ");
	    		    qsession = qcon.createQueueSession(false, Session.AUTO_ACKNOWLEDGE);
	    		    LOG.info("Queue Session Created... ");
	    		    queue = (Queue) ctx.lookup(QUEUE);
	    		    LOG.info("Queue lookup done... ");
	    		    qsender = qsession.createSender(queue);
	    		    LOG.info("Sender Created... ");
	    		    msg = qsession.createTextMessage();
	    		    LOG.info("Create text message Created... ");
	    		    qcon.start();
	    		    msg.setText("Sending Message to JMS queue");
	    		    qsender.send(msg);
	    	}
	    	catch (NamingException ne){
	    		LOG.info("Caught in  the NamingException "); 
	    		ne.printStackTrace();
	    	}
	    	catch (JMSException je){
	    		LOG.info("Caught in  the JMSException "); 
	    		je.printStackTrace();
	    	}
			
		}
	 public static void main(String[] args) {
		 FacilityMQUpsert mqupsrt= new FacilityMQUpsert();
		 mqupsrt.sendJMSmsg();
	 }
	
}
