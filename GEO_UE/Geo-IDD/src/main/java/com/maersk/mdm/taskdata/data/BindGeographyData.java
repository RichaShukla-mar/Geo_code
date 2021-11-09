package com.maersk.mdm.taskdata.data;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

import org.apache.http.ParseException;
import org.apache.log4j.Logger;

import com.ibm.mq.MQException;
import com.maersk.mdm.taskdata.jaxb.Geography;
import com.maersk.mdm.taskdata.userexit.DefAreaType;
import com.maersk.mdm.taskdata.userexit.DefinedArea;
import com.maersk.mdm.taskdata.userexit.MaerskConfException;
import com.maersk.mdm.taskdata.util.GeographyJaxBTranslator;
import com.maersk.mdm.taskdata.util.PublishService;
import com.maersk.mdm.taskdata.util.Utility;
import com.siperian.bdd.userexits.datamodel.BDDObject;
import com.siperian.bdd.userexits.operations.OperationContext;
import com.siperian.bdd.userexits.operations.OperationExecutionError;
import com.siperian.bdd.userexits.operations.OperationResult;
import com.siperian.bdd.userexits.utils.BDDMessagesLocalizationGate;

public class BindGeographyData {
	private static final Logger LOG = Logger.getLogger(BindGeographyData.class);
	private static BDDObject bddObject;
	private OperationContext operationContext;
	private static String ORSId; 
	Connection connection = null;
	private BDDMessagesLocalizationGate bddMessagesLocalizationGate;
	private static Geography geographyEvent;
	private Properties properties; 
	
	public BindGeographyData() {
		super();
	}


	public BindGeographyData(BDDObject bddObject, String mdmORSid, OperationContext operationContext,
			BDDMessagesLocalizationGate localizationGate, Geography geoEvent) {
		this.bddObject=bddObject;
		this.operationContext=operationContext;
		this.bddMessagesLocalizationGate=localizationGate;
		this.ORSId=mdmORSid;
		this.geographyEvent=geoEvent;

	}


	public OperationResult setGeographyData() throws MaerskConfException, SQLException {
		
		try{
			LOG.info("Inside setGeographyData method");
		
		DefinedArea definedArea = new DefinedArea(bddObject);
		String geoTypeName=bddObject.getObjectName().toUpperCase();
		LOG.info("Object Being created/updated is ::"+geoTypeName);
		switch(geoTypeName) {
		
		case "CONTINENT" :
			BindContinentData continentData = new BindContinentData(bddObject, ORSId, operationContext,
					bddMessagesLocalizationGate, geographyEvent);
			geographyEvent=continentData.bindContinentData();
			break;
			
		case "COUNTRY" :
			BindCountryData countryData = new BindCountryData(bddObject, ORSId, operationContext,
					bddMessagesLocalizationGate, geographyEvent);
			geographyEvent=countryData.bindCountryData();
			break;
			
		case "STATEPROV" :
			BindStateProvinceData stateOrProvienceData = new BindStateProvinceData(bddObject, ORSId, operationContext,
					bddMessagesLocalizationGate, geographyEvent);
			geographyEvent=stateOrProvienceData.bindStateorProvinceData();
			break;
			
		case "CITY" :
			BindCityData cityData = new BindCityData(bddObject, ORSId, operationContext,
					bddMessagesLocalizationGate, geographyEvent);
			geographyEvent=cityData.bindCityData();
			break;
			
		case "SITE" :
			BindSiteData sideData = new BindSiteData(bddObject, ORSId, operationContext,
					bddMessagesLocalizationGate, geographyEvent);
			geographyEvent=sideData.bindSiteData();
			break;
			
		case "POSTALCODE" :
			BindPostalCodeData postalCodeData = new BindPostalCodeData(bddObject, ORSId, operationContext,
					bddMessagesLocalizationGate, geographyEvent);
			geographyEvent=postalCodeData.bindPostalCodeData();
			break;
			
		case "BUSINESSDEFINEDAREA" :
			BindBDAData bdaData = new BindBDAData(bddObject, ORSId, operationContext,
					bddMessagesLocalizationGate, geographyEvent);
			geographyEvent=bdaData.bindBDAData();
			break;
			
			
		/*case "CITYSUBAREA" : BindSubCityData subCityArea = new
			  BindSubCityData(bddObject, ORSId, operationContext,
			  bddMessagesLocalizationGate, geographyEvent);
			  geographyEvent=subCityArea.bindSubCityData();
			  break;*/
		case "CITYSUBAREA" : 
			BindCityData cityData1 = new BindCityData(bddObject, ORSId, operationContext,
					bddMessagesLocalizationGate, geographyEvent);
			geographyEvent=cityData1.bindCityData();
			break;
			 
		}
		
		if(geographyEvent!=null) {
			GeographyJaxBTranslator jaxBConverter= new GeographyJaxBTranslator();
			String isJaxBTranslated=jaxBConverter.sendDataToJAXBTranslation(geographyEvent);
			if(isJaxBTranslated!=null) {
				LOG.info("Inside isJAXBTransDone check.. Before Sending to JMS");
				PublishService mqInterface = Utility.getMQInterface();
				LOG.info("mqinterface Object created" +mqInterface.toString());
				loadPropertyFile();
				LOG.info("Property file initiated");							
				int geoQueueMessage = mqInterface.send(isJaxBTranslated, properties);						
		        LOG.info("Inside isJAXBTransDone check.. After Sending to JMS");
				LOG.info(geoQueueMessage);
				if(geoQueueMessage==0) {
					LOG.info("Sucessfully updated ");
				return OperationResult.OK;
				}
				else {
					LOG.info("Response isn't successful ::" + geoQueueMessage);
					return new OperationResult(new OperationExecutionError("SIP-40004", new String[]{geoQueueMessage + "Error while sending data to JMS"},bddMessagesLocalizationGate));
				}
			} else {
				LOG.info("Exception while JAXB conversion of IDD Objects");
				return new OperationResult(new OperationExecutionError("SIP-40005", new String[]{"Exception while JAXB conversion of IDD Objects"},bddMessagesLocalizationGate));
				}
			}
			
		
		}catch(MaerskConfException | ParseException | IOException ex) {
			LOG.info("Error is " + ex);
			String errorMessage = "MaerskCustom. Unknown exception happend. ";
            LOG.error(errorMessage, ex);
            return new OperationResult(new OperationExecutionError("SIP-40006", new String[]{errorMessage + ex.getMessage()},bddMessagesLocalizationGate));
			} catch (MQException e) {
				LOG.info("Error is " + e);
				String errorMessage = "MaerskCustom. Unknown exception happend. ";
	            LOG.error(e);
	            return new OperationResult(new OperationExecutionError("SIP-40007", new String[]{errorMessage + e.getMessage()},bddMessagesLocalizationGate));
				
		}
		return null;
		}	
	private void loadPropertyFile() {
    InputStream inputStream = null;
    try {
    	LOG.info("Inside loadPropertyFile method");
      this.properties = new Properties();
      inputStream = this.getClass().getResourceAsStream("/GEOQueue.properties");
      properties.load(inputStream);
      LOG.info("PropertyFile initialize is successful");
    } catch (FileNotFoundException e) {
      e.printStackTrace();
      LOG.info("Exception is::"+e.getMessage());
    } catch (IOException e) {
      e.printStackTrace();
      LOG.info("Exception is::"+e.getMessage());
    }

  }

}
