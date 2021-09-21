package net.apmm.mdm.ops.geo.service;

import com.ibm.mq.MQException;
import lombok.extern.slf4j.Slf4j;
import net.apmm.mdm.ops.geo.data.BindCityData;
import net.apmm.mdm.ops.geo.exception.PublishException;
import net.apmm.mdm.ops.geo.jaxb.Geography.City;
import net.apmm.mdm.ops.geo.jaxb.Geography;
import net.apmm.mdm.ops.geo.model.StatusResponse;
import net.apmm.mdm.ops.geo.util.GeographyJaxBTranslator;
import net.apmm.mdm.ops.geo.util.PublishService;
import net.apmm.mdm.ops.geo.util.Utility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.UUID;

@Service
@Slf4j
public class PublishGeographyMQService {

    private Properties properties;

    @Autowired
    JdbcTemplate smdsJdbcTemplate;

    private String messageId = UUID.randomUUID().toString();
    public StatusResponse publishGeographyDetails(String geoRowID) {
        StatusResponse resp = null;
        try {



            BindCityData BindCity = new BindCityData(smdsJdbcTemplate);


            Geography rescity = BindCity.bindAllCityDetails(geoRowID);

            log.info("rescity:" + rescity.toString());

            if (rescity != null) {
                GeographyJaxBTranslator jaxBConverter = new GeographyJaxBTranslator();
                String isJaxBTranslated = jaxBConverter.sendDataToJAXBTranslation(rescity);

                if (isJaxBTranslated != null)
                {
                    log.info("Inside isJAXBTransDone check.. Before Sending to JMS");
                    PublishService mqInterface = Utility.getMQInterface();
                    log.info("mqinterface Object created" + mqInterface.toString());
                    loadPropertyFile();
                    log.info("Property file initiated");
                    int geoQueueMessage = mqInterface.send(isJaxBTranslated, properties);
                    log.info("Inside isJAXBTransDone check.. After Sending to JMS");
                    log.info(String.valueOf(geoQueueMessage));
                    if (geoQueueMessage == 0) {
                        log.info("Sucessfully updated ");
                        resp =  StatusResponse.builder().geoRowID(geoRowID).geoRequestId(UUID.randomUUID().toString()).build();
                        
                    }
                    else
                    {
                        throw new PublishException("Error while pushing message to mq");

                     }
                }
                else
                    {

                        throw new PublishException("Exception while JAXB conversion");

                    }
            }



        } 
        
        
        
        catch (MQException mqException) {
            mqException.printStackTrace();
        } catch (IOException ioException) {
            ioException.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
            throw new PublishException("Error while pushing message to mq :: " + e);
        }

        return resp;

    }




    private void loadPropertyFile() {
        InputStream inputStream = null;
        try {
            log.info("Inside loadPropertyFile method");
            this.properties = new Properties();
            inputStream = this.getClass().getResourceAsStream("/GEOQueue.properties");
            properties.load(inputStream);
            log.info("PropertyFile initialize is successful");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            log.info("Exception is::"+e.getMessage());
        } catch (IOException e) {
            e.printStackTrace();
            log.info("Exception is::"+e.getMessage());
        }

    }




    }


