package com.maersk.facility.userexit.util;

import java.io.IOException;
import java.util.Properties;

import org.apache.http.ParseException;
import org.apache.log4j.Logger;

import com.maersk.facility.mq.MQPublisher;

public class PublishFacilityViaMQ implements PublishService {
	private static final Logger log = Logger.getLogger(PublishFacilityViaMQ.class);

	@Override
	public int send(String facilityEvent, Properties properties)
			throws NumberFormatException, ParseException, IOException {
		int status=-1;
		log.info("Sending via MQ ");
		String requestXml = facilityEvent.toString();
		log.info("Result XML " + requestXml);
		MQPublisher publisher = new MQPublisher(properties);
		status = publisher.publishMessages(requestXml);
		log.info("Message published status 0 is sucessfull and -1 is failed : " + status);
		log.info("Message Browsed done ");
		log.info("RESULT " + status);
		return status;
	}
}
