package com.maersk.facility.userexit.util;

 


import java.io.IOException;
import java.util.Properties;
import org.apache.http.ParseException;
import com.maersk.facility.userexit.bean.SMDSFacilityEvent;

 

public interface PublishService {

	public int send(String facilityEvent, Properties properties) throws NumberFormatException, ParseException, IOException;

 

}