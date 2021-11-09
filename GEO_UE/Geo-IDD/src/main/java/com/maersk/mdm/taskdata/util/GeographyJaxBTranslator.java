package com.maersk.mdm.taskdata.util;

import java.io.StringWriter;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import org.apache.http.HttpResponse;
import org.apache.log4j.Logger;

import com.ibm.mq.MQException;
import com.maersk.mdm.taskdata.jaxb.Geography;


/**
 * @author AJA350
 *
 */
public class GeographyJaxBTranslator {

	private static final Logger LOG = Logger.getLogger((String) GeographyJaxBTranslator.class.getName());

	public static String sendDataToJAXBTranslation(Geography geoEvent) throws MQException{
				LOG.info("Inside sendDataToJAXBTranslation method");
		try {
			LOG.info("Inside Try block" + geoEvent.toString());
			// Create JAXB Context
			JAXBContext jaxbContext = JAXBContext.newInstance(Geography.class);
			LOG.info("Creating Marshaller");
			Marshaller marshaller = jaxbContext.createMarshaller();
        	LOG.info("Geo Object :: " + geoEvent.toString());
			LOG.info("Inside Try block before writing to file ::"+geoEvent.toString());
			StringWriter sw = new StringWriter();
			LOG.info("Inside Try block after writing to file ::"+sw);
			marshaller.marshal(geoEvent, sw);
			LOG.info("Inside Try block after writing to file" + sw.toString());
			return sw.toString();
		} catch (JAXBException e) {
			e.printStackTrace();
			LOG.info("Exception caught in sendDataToJAXBTranslation " + e.getMessage());
			LOG.info("Exception caught in sendDataToJAXBTranslation " + e.getCause());

			return null;
		}
		
	
	
	}

	public HttpResponse publishToJms() {
		// TODO Auto-generated method stub
		return null;
	}

}
