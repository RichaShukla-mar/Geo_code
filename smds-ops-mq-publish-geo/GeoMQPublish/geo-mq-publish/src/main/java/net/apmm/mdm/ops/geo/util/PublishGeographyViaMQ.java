package net.apmm.mdm.ops.geo.util;

import lombok.extern.slf4j.Slf4j;
import net.apmm.mdm.ops.geo.mq.MQPublisher;
import org.apache.http.ParseException;

import java.io.IOException;
import java.util.Properties;

@Slf4j
public class PublishGeographyViaMQ implements PublishService{
    @Override
    public int send(String geoEvent, Properties properties) throws NumberFormatException, ParseException, IOException {
        log.info("Sending via MQ ");
        String requestXml = geoEvent.toString();
        log.info("Result XML " + requestXml);
        MQPublisher publisher = new MQPublisher(properties);
        int status = publisher.publishMessages(requestXml);
        log.info("Message published status 0 is sucessfull and -1 is failed : " + status);
        log.info("Message Browsed done ");
        log.info("RESULT " + status);
        return status;
    }
}
