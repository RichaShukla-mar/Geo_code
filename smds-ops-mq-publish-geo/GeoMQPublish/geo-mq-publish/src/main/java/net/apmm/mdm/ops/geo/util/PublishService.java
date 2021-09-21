package net.apmm.mdm.ops.geo.util;

import java.io.IOException;
import java.util.Properties;

import lombok.extern.slf4j.Slf4j;
import org.apache.http.ParseException;
import net.apmm.mdm.ops.geo.jaxb.Geography;


public interface PublishService {
    public int send(String geoEvent, Properties properties) throws NumberFormatException, ParseException, IOException;

}
