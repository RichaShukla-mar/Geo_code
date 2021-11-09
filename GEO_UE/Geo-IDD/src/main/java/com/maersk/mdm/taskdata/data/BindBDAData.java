/**
 * 
 */
package com.maersk.mdm.taskdata.data;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
//import org.apache.openjpa.lib.log.Log;

import com.maersk.mdm.taskdata.jaxb.Geography;
import com.maersk.mdm.taskdata.jaxb.Geography.BusinessDefinedArea;
import com.maersk.mdm.taskdata.jaxb.Geography.BusinessDefinedArea.AlternateCodes;
import com.maersk.mdm.taskdata.jaxb.Geography.BusinessDefinedArea.AlternateCodes.AlternateCode;
import com.maersk.mdm.taskdata.jaxb.Geography.BusinessDefinedArea.Locations;
import com.maersk.mdm.taskdata.jaxb.Geography.BusinessDefinedArea.Locations.Location;
import com.maersk.mdm.taskdata.jaxb.Geography.BusinessDefinedArea.ParentBDA;
import com.maersk.mdm.taskdata.userexit.IDDConstants;
import com.maersk.mdm.taskdata.util.Utility;
import com.siperian.bdd.userexits.datamodel.BDDObject;
import com.siperian.bdd.userexits.operations.OperationContext;
import com.siperian.bdd.userexits.utils.BDDMessagesLocalizationGate;

/**
 * @author AJA350
 *
 */
public class BindBDAData {
	private static final Logger LOG = Logger.getLogger(BindGeographyData.class);
	private static BDDObject bddObject;
	private OperationContext operationContext;
	private static String ORSId; 
	Connection connection = null;
	private BDDMessagesLocalizationGate bddMessagesLocalizationGate;
	private static Geography geographyEvent;
	private static BusinessDefinedArea businessDefinedEvent;

	public BindBDAData() {
		super();
	}

	public BindBDAData(BDDObject bddObject, String oRSId, OperationContext operationContext,
			BDDMessagesLocalizationGate bddMessagesLocalizationGate, Geography geographyEvent) {
		this.bddObject=bddObject;
		this.operationContext=operationContext;
		this.bddMessagesLocalizationGate=bddMessagesLocalizationGate;
		this.ORSId=oRSId;
		this.geographyEvent=geographyEvent;
		this.businessDefinedEvent=new BusinessDefinedArea();
	}

	public Geography bindBDAData() throws SQLException {
		LOG.info("Inside method bindSiteData");
		geographyEvent=bindBusinessDefinedAreaAllData(businessDefinedEvent);
		return geographyEvent;
	}

	private Geography bindBusinessDefinedAreaAllData(BusinessDefinedArea businessDefinedEvent) throws SQLException {
		LOG.info("Inside method bindBusinessDefinedAreaAllData");
		LOG.info("Bind BDA General Details");
		businessDefinedEvent.setName(Utility.validate(bddObject.getValue(IDDConstants.MDM_TABLE_DEFAREA_COLUMN_NAME_NAME)).toString());
		String status=Utility.validate(bddObject.getValue(IDDConstants.MDM_TABLE_DEFAREA_COLUMN_NAME_STATUS)).toString();
		if(status!=null) {
				if(status.equalsIgnoreCase("Y"))
					businessDefinedEvent.setStatus("Active");
				else if(status.equalsIgnoreCase("N"))
					businessDefinedEvent.setStatus("Inactive");
		}
		//businessDefinedEvent.setValidFrom(Utility.validate(bddObject.getValue(IDDConstants.MDM_TABLE_DEFAREA_COLUMN_NAME_VALID_FROM)).toString());
		//businessDefinedEvent.setValidTo(Utility.validate(bddObject.getValue(IDDConstants.MDM_TABLE_DEFAREA_COLUMN_NAME_VALID_TO)).toString());
		
		Object validFromdate=Utility.validate(bddObject.getValue(IDDConstants.MDM_TABLE_DEFAREA_COLUMN_NAME_VALID_FROM));
		Object validTodate=Utility.validate(bddObject.getValue(IDDConstants.MDM_TABLE_DEFAREA_COLUMN_NAME_VALID_TO));
		if(bddObject.isCreated()) {
			try {
				validFromdate=Utility.changeInputCreateDate(validFromdate.toString());
				validTodate=Utility.changeInputCreateDate(validTodate.toString());
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		LOG.info("Valid From Date is "+validFromdate);
		LOG.info("Valid To Date is "+validTodate);
		businessDefinedEvent.setValidFrom(validFromdate.toString());
		businessDefinedEvent.setValidTo(validTodate.toString());
		
		List<BDDObject> bddbdaType= bddObject.getChildren("BDAType");
		if(bddbdaType!=null && bddbdaType.size()>0) {
			businessDefinedEvent.setBDAType(Utility.validate(bddbdaType.get(0).getValue(IDDConstants.MDM_TABLE_DEFAREA_COLUMN_NAME_BDATYPE)).toString());
		}
		LOG.info("Bind BDA AlternateCode");
		AlternateCodes bdaAlternateCodes=new AlternateCodes();
		List<BDDObject> bddObjectAltCode=bddObject.getChildren("AlternateCode");
		List<AlternateCode> altCodeValues=new ArrayList<AlternateCode>();
		for(BDDObject alternateCode : bddObjectAltCode) {
			if(!alternateCode.isRemoved()) {
			AlternateCode altCode= new AlternateCode();
			String typeTypeRowid = Utility.validate(alternateCode.getValue("C_ALT_CODE|TYP_TYPE_ROWID")).toString();
			LOG.info("typeTypeRowid is ::" + typeTypeRowid);
			String getAltCodeQuery = Utility.getDatabaseLookupValue("C_TYP_TYPE", typeTypeRowid, "NAME", "ROWID_OBJECT");
			String altCodeValue = Utility.getValueFromDB(operationContext, getAltCodeQuery);
			altCode.setCodeType(altCodeValue);
			altCode.setCode(Utility.validate(alternateCode.getValue(IDDConstants.MDM_TABLE_ALTCODE_COLUMN_NAME_VALUE)).toString());
			altCodeValues.add(altCode);
			}
		}
		bdaAlternateCodes.setAlternateCode(altCodeValues);
		businessDefinedEvent.setAlternateCodes(bdaAlternateCodes);
		
		
		LOG.info("Bind BDA Parent");
		List<BDDObject> businessDefParent=bddObject.getChildren("BDAParent");
		if(businessDefParent!=null && businessDefParent.size()>0) {
			ParentBDA bdaParent= new ParentBDA();
			bdaParent.setName(Utility.validate(businessDefParent.get(0).getValue(IDDConstants.MDM_TABLE_DEFAREA_COLUMN_NAME_NAME)).toString());
			/******************    200293: Add the Alternate Code to the parent start ***********************/
			com.maersk.mdm.taskdata.jaxb.Geography.BusinessDefinedArea.ParentBDA.AlternateCodes BDAParentAltCodes = new com.maersk.mdm.taskdata.jaxb.Geography.BusinessDefinedArea.ParentBDA.AlternateCodes();
			List<com.maersk.mdm.taskdata.jaxb.Geography.BusinessDefinedArea.ParentBDA.AlternateCodes.AlternateCode> BDAParentAltCode= BDAParentAltCodes.getAlternateCode();
			
			String parentRowidObject=Utility.validate(businessDefParent.get(0).getValue(IDDConstants.MDM_TABLE_DEFAREA_COLUMN_NAME_ROWID_OBJECT)).toString();
			LOG.info("parentRowid:" + parentRowidObject);
			BDAParentAltCode =(List<com.maersk.mdm.taskdata.jaxb.Geography.BusinessDefinedArea.ParentBDA.AlternateCodes.AlternateCode>) Utility.getParentAltCodes(parentRowidObject,operationContext,"BUSINESSDEFINEDAREA");
			
				BDAParentAltCodes.setAlternateCode(BDAParentAltCode);
				bdaParent.setAlternateCodes(BDAParentAltCodes);
						
			/*****************************200293: Add the Alternate Code to the parent end *****************/	
			businessDefinedEvent.setParentBDA(bdaParent);
		}
		LOG.info("Bind BDA Child");
		Locations bdalocation= new Locations();
		List<Location> bdaLocationValue=new ArrayList<Location>();
		List<BDDObject> bdaLocations=bddObject.getChildren("BDAChild");		
		
		
		
		for(BDDObject bdLoc:bdaLocations) {
			if(!bdLoc.isRemoved()) {
			Location bdaLocationObj= new Location();
			com.maersk.mdm.taskdata.jaxb.Geography.BusinessDefinedArea.Locations.Location.AlternateCodes bdaLocBdaAltCodes=new com.maersk.mdm.taskdata.jaxb.Geography.BusinessDefinedArea.Locations.Location.AlternateCodes();
			List<com.maersk.mdm.taskdata.jaxb.Geography.BusinessDefinedArea.Locations.Location.AlternateCodes.AlternateCode> bdaLocBdaAltCode=bdaLocBdaAltCodes.getAlternateCode();

			bdaLocationObj.setName(Utility.validate(bdLoc.getValue(IDDConstants.MDM_TABLE_DEFAREA_COLUMN_NAME_NAME)).toString());
			LOG.info("Name set is ::"+Utility.validate(bdLoc.getValue(IDDConstants.MDM_TABLE_DEFAREA_COLUMN_NAME_NAME)).toString());
			String typeTypeRowid = Utility.validate(bdLoc.getValue(IDDConstants.MDM_TABLE_DEFAREA_COLUMN_NAME_TYPE_CD)).toString();
			LOG.info("typeTypeRowid is ::" + typeTypeRowid);
			
			//String getAltCodeQuery = Utility.getDatabaseLookupValue("C_TYP_TYPE", typeTypeRowid, "NAME", "ROWID_OBJECT");
			//String altCodeValue = Utility.getValueFromDB(operationContext, getAltCodeQuery);
			LOG.info("Alt Code Type is ::"+typeTypeRowid.substring(4));
			bdaLocationObj.setType(typeTypeRowid.substring(4));
			
			String parentRowidObject=Utility.validate(bdLoc.getValue(IDDConstants.MDM_TABLE_DEFAREA_COLUMN_NAME_ROWID_OBJECT)).toString();
			LOG.info("parentRowid:" + parentRowidObject);
			bdaLocBdaAltCode= (List<com.maersk.mdm.taskdata.jaxb.Geography.BusinessDefinedArea.Locations.Location.AlternateCodes.AlternateCode>) Utility.getBDAAltCodes(parentRowidObject,operationContext,"BUSINESSDEFINEDAREA");			
			bdaLocBdaAltCodes.setAlternateCode(bdaLocBdaAltCode);
			bdaLocationObj.setAlternateCodes(bdaLocBdaAltCodes);
			bdaLocationValue.add(bdaLocationObj);
		}
		}
		bdalocation.setLocation(bdaLocationValue);
		businessDefinedEvent.setLocations(bdalocation);
		LOG.info("BDA Object is "+businessDefinedEvent.toString());
		geographyEvent.setBusinessDefinedArea(businessDefinedEvent);
		return geographyEvent;
	}

}
