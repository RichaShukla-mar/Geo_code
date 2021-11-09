package com.maersk.mdm.taskdata.util;


import java.io.IOException;
import java.util.Properties;
import org.apache.http.ParseException;
import com.maersk.mdm.taskdata.jaxb.Geography;

public interface PublishService {

  public int send(String geoEvent, Properties properties) throws NumberFormatException, ParseException, IOException;

}
