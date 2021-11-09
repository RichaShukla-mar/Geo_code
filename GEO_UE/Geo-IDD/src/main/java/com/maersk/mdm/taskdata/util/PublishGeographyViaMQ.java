package com.maersk.mdm.taskdata.util;


import java.io.IOException;
import java.util.Properties;
import com.maersk.mdm.taskdata.mq.MQPublisher;
import org.apache.http.ParseException;
import org.apache.log4j.Logger;



public class PublishGeographyViaMQ implements PublishService {
	private static final Logger log = Logger.getLogger(PublishGeographyViaMQ.class);

	@Override
	public int send(String facilityEvent, Properties properties)
			throws NumberFormatException, ParseException, IOException {
		log.info("Sending via MQ ");
		String requestXml = facilityEvent.toString();
		log.info("Result XML " + requestXml);
		MQPublisher publisher = new MQPublisher(properties);
		int status = publisher.publishMessages(requestXml);
		log.info("Message published status 0 is sucessfull and -1 is failed : " + status);
		log.info("Message Browsed done ");
		log.info("RESULT " + status);
		return status;
	}
}
