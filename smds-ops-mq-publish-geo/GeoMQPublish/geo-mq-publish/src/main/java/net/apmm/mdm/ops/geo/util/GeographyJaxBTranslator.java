package net.apmm.mdm.ops.geo.util;

import lombok.extern.slf4j.Slf4j;
import java.io.StringWriter;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import net.apmm.mdm.ops.geo.jaxb.Geography;
import net.apmm.mdm.ops.geo.jaxb.Geography.City;

import org.apache.http.HttpResponse;

import com.ibm.mq.MQException;


@Slf4j
public class GeographyJaxBTranslator {

    public static String sendDataToJAXBTranslation(Geography cityEvent) throws MQException{
        log.info("Inside sendDataToJAXBTranslation method");
        try {
            log.info("Inside Try block" + cityEvent.toString());
            // Create JAXB Context
            JAXBContext jaxbContext = JAXBContext.newInstance(Geography.class);
            log.info("Creating Marshaller");
            Marshaller marshaller = jaxbContext.createMarshaller();
            log.info("Geo Object :: " + cityEvent.toString());
            log.info("Inside Try block before writing to file ::"+cityEvent.toString());
            StringWriter sw = new StringWriter();
            log.info("Inside Try block after writing to file ::"+sw);
            marshaller.marshal(cityEvent, sw);
            log.info("Inside Try block after writing to file" + sw.toString());
            return sw.toString();
        } catch (JAXBException e) {
            e.printStackTrace();
            log.info("Exception caught in sendDataToJAXBTranslation " + e.getMessage());
            log.info("Exception caught in sendDataToJAXBTranslation " + e.getCause());

            return null;
        }



    }

    public HttpResponse publishToJms() {
        // TODO Auto-generated method stub
        return null;
    }
}
