package com.maersk.facility.userexit.util;

 

import java.io.StringWriter;

 

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

 

import org.apache.http.HttpResponse;
import org.apache.log4j.Logger;

 

import com.maersk.facility.userexit.bean.SMDSFacilityEvent;

 


/**
 * @author AJA350
 *
 */
public class FacilityJaxBTranslator {

 

    private static final Logger LOG = Logger.getLogger((String) FacilityJaxBTranslator.class.getName());

 

    public static String sendDataToJAXBTranslation(SMDSFacilityEvent facilityEvent) {
        /*
         * LOG.info("Inside sendDataToJAXBTranslation method"); try {
         * LOG.info("Inside Try block" + facilityEvent.toString()); // Create JAXB
         * Context JAXBContext jaxbContext =
         * JAXBContext.newInstance(SMDSFacilityEvent.class);
         * 
         * // Create Marshaller LOG.info("Creating Marshaller"); Marshaller
         * jaxbMarshaller = jaxbContext.createMarshaller(); LOG.info("Fact Object :: " +
         * facilityEvent.toString());
         * 
         * // Required formatting?? //
         * jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
         * LOG.info("Inside Try block before writing to file"); // Store XML to File //
         * File file = new //
         * File("/opt/informatica/mdmipc/smds_ipc/1020/server/facilityOutbound.xml");
         * StringWriter sw = new StringWriter();
         * 
         * LOG.info("Inside Try block after writing to file"); // Writes XML file to
         * file-system jaxbMarshaller.marshal(facilityEvent, sw);
         * LOG.info("Inside Try block after writing to file" + sw.toString()); return
         * sw.toString(); } catch (JAXBException e) { e.printStackTrace();
         * LOG.info("Exception caught in sendDataToJAXBTranslation " + e.getMessage());
         * return null; } //return null;
         */
        

 

        LOG.info("Inside sendDataToJAXBTranslation method");
        try {
            LOG.info("Inside Try block" + facilityEvent.toString());
            // Create JAXB Context
            JAXBContext jaxbContext = JAXBContext.newInstance(SMDSFacilityEvent.class);
            LOG.info("Creating Marshaller");
            //Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
            //Added Below From Abhishek
            
            Marshaller marshaller = jaxbContext.createMarshaller();
           
               
            //Added Above from Abhishek
            LOG.info("Fact Object :: " + facilityEvent.toString());
            LOG.info("Inside Try block before writing to file");
            StringWriter sw = new StringWriter();
            
            LOG.info("Inside Try block after writing to file");
            // Writes XML file to file-system
            marshaller.marshal(facilityEvent, sw);
            LOG.info("Inside Try block after writing to file" + sw.toString());
            return sw.toString();
        } catch (JAXBException e) {
            e.printStackTrace();
            LOG.info("Exception caught in sendDataToJAXBTranslation " + e.getMessage());
            return null;
        }
        
    
    
    }

 

    public HttpResponse publishToJms() {
        // TODO Auto-generated method stub
        return null;
    }

 

}