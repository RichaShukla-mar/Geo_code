/**
 * 
 */
package com.maersk.mdm.taskdata.data;

import java.sql.Connection;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.maersk.mdm.taskdata.jaxb.Geography;
import com.maersk.mdm.taskdata.jaxb.Geography.StateProvince;
import com.maersk.mdm.taskdata.jaxb.Geography.StateProvince.AlternateCodes;
import com.maersk.mdm.taskdata.jaxb.Geography.StateProvince.AlternateCodes.AlternateCode;
import com.maersk.mdm.taskdata.jaxb.Geography.StateProvince.AlternateNames;
import com.maersk.mdm.taskdata.jaxb.Geography.StateProvince.AlternateNames.AlternateName;
import com.maersk.mdm.taskdata.jaxb.Geography.StateProvince.BDA;
import com.maersk.mdm.taskdata.jaxb.Geography.StateProvince.BDA.BDAType;
import com.maersk.mdm.taskdata.jaxb.Geography.StateProvince.Parent;
import com.maersk.mdm.taskdata.userexit.IDDConstants;
import com.maersk.mdm.taskdata.util.Utility;
import com.siperian.bdd.userexits.datamodel.BDDObject;
import com.siperian.bdd.userexits.operations.OperationContext;
import com.siperian.bdd.userexits.utils.BDDMessagesLocalizationGate;

/**
 * @author AJA350
 *
 */
public class BindStateProvinceData {

	private static final Logger LOG = Logger.getLogger(BindContinentData.class);
	private static BDDObject bddObject;
	private OperationContext operationContext;
	private static String ORSId; 
	private static Connection connection = null;
	private static StateProvince StateProvinceEvent;
	private BDDMessagesLocalizationGate bddMessagesLocalizationGate;
	private static Geography geographyEvent;
	
	
	public BindStateProvinceData() {
		super();
	}

	public BindStateProvinceData(BDDObject bddObject, String oRSId, OperationContext operationContext,
			BDDMessagesLocalizationGate bddMessagesLocalizationGate, Geography geographyEvent) {
		this.bddObject=bddObject;
		this.operationContext=operationContext;
		this.bddMessagesLocalizationGate=bddMessagesLocalizationGate;
		this.ORSId=oRSId;
		this.geographyEvent=geographyEvent;		
		this.StateProvinceEvent=new StateProvince();
	}

	public Geography bindStateorProvinceData() throws SQLException {
		LOG.info("Inside method bindStateorProvinceData");
		geographyEvent=bindStateProvinceAllDetails(StateProvinceEvent);
		return geographyEvent;
	}

	private Geography bindStateProvinceAllDetails(StateProvince StateProvinceEvent) throws SQLException {
		LOG.info("Inside method bindStateProvinceAllDetails");
		LOG.info("Bind StateProvince general details");
		StateProvinceEvent.setName(Utility.validate(bddObject.getValue(IDDConstants.MDM_TABLE_DEFAREA_COLUMN_NAME_NAME)).toString());
		String status=Utility.validate(bddObject.getValue(IDDConstants.MDM_TABLE_DEFAREA_COLUMN_NAME_STATUS)).toString();
		if(status!=null) {
				if(status.equalsIgnoreCase("Y"))
					StateProvinceEvent.setStatus("Active");
				else if(status.equalsIgnoreCase("N"))
					StateProvinceEvent.setStatus("Inactive");
		}
		//StateProvinceEvent.setValidFrom(Utility.validate(bddObject.getValue(IDDConstants.MDM_TABLE_DEFAREA_COLUMN_NAME_VALID_FROM)).toString());
		//StateProvinceEvent.setValidTo(Utility.validate(bddObject.getValue(IDDConstants.MDM_TABLE_DEFAREA_COLUMN_NAME_VALID_TO)).toString());
		
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
		StateProvinceEvent.setValidFrom(validFromdate.toString());
		StateProvinceEvent.setValidTo(validTodate.toString());
		
		
		String timeZone=Utility.validate(bddObject.getValue(IDDConstants.MDM_TABLE_DEFAREA_COLUMN_NAME_TIMEZONE)).toString();
		if(timeZone!=null) {
			/******************    200293: TIMEZONE WILL BE CODE INSTEAD OF NAME START ***********************/
				String timeZoneQuery=Utility.getDatabaseLookupValue("C_TDS_TMZ", timeZone, "CODE", "ROWID_OBJECT");	
			/******************    200293: TIMEZONE WILL BE CODE INSTEAD OF NAME END ***********************/
				String getTimeZoneValue=Utility.getValueFromDB(operationContext, timeZoneQuery);
				StateProvinceEvent.setTimeZone(getTimeZoneValue);
		}
		String dayLightSavingTime=Utility.validate(bddObject.getValue(IDDConstants.MDM_TABLE_DEFAREA_COLUMN_NAME_DST)).toString();
		if(dayLightSavingTime!=null) {
			/******************    200293: TIMEZONE WILL BE CODE INSTEAD OF NAME START***********************/
				String dayLightSavingQuery=Utility.getDatabaseLookupValue("C_TDS_DST", dayLightSavingTime, "CODE", "ROWID_OBJECT");
			/******************    200293: TIMEZONE WILL BE CODE INSTEAD OF NAME END***********************/
				String dalightSavingValue=Utility.getValueFromDB(operationContext,dayLightSavingQuery);
				StateProvinceEvent.setDaylightSavingTime(dalightSavingValue);
		}
		StateProvinceEvent.setDescription(Utility.validate(bddObject.getValue(IDDConstants.MDM_TABLE_DEFAREA_COLUMN_NAME_DESCRIPTION)).toString());
		List<BDDObject> WorkAroundReason=bddObject.getChildren("WorkAroundReason");
		if(WorkAroundReason!=null && WorkAroundReason.size()>0) {
			String workaroundrsn=Utility.validate(WorkAroundReason.get(0).getValue(IDDConstants.MDM_TABLE_C_TMP_WRKRND_RSN_COLUMN_NAME_WR)).toString();
				if(workaroundrsn!=null) {
						String workaroundReasonQuery=Utility.getDatabaseLookupValue("C_TYP_TYPE", workaroundrsn, "NAME", "ROWID_OBJECT");	
							String workaroundReasonValue=Utility.getValueFromDB(operationContext, workaroundReasonQuery);
							StateProvinceEvent.setWorkaroundReason(workaroundReasonValue);
				}
		}
		if(WorkAroundReason==null || WorkAroundReason.isEmpty()) {
			StateProvinceEvent.setWorkaroundReason("");
		}
		LOG.info("Bind StateProvince AlternateName");
		AlternateNames countryAlternateName= new AlternateNames();
		List<AlternateName> countryAlternateNameValues= new ArrayList<AlternateName>();
		List<BDDObject> bddObjectAltName=bddObject.getChildren("AlternateName");
		for(BDDObject alternateName : bddObjectAltName) {
			if(!alternateName.isRemoved()) {
				AlternateName altName= new AlternateName();
			altName.setName(Utility.validate(alternateName.getValue(IDDConstants.MDM_TABLE_ALTNAME_COLUMN_NAME_NAME)).toString());
			altName.setDescription(Utility.validate(alternateName.getValue(IDDConstants.MDM_TABLE_ALTNAME_COLUMN_NAME_DESCRIPTION)).toString());
			String isStatus=Utility.validate(alternateName.getValue(IDDConstants.MDM_TABLE_ALTNAME_COLUMN_NAME_STATUS)).toString();
			if(isStatus!=null) {
				if(isStatus.equalsIgnoreCase("Y"))
					altName.setStatus("Active");
				else if(isStatus.equalsIgnoreCase("N"))
					altName.setStatus("Inactive");
			}
			countryAlternateNameValues.add(altName);
			}
			}
		countryAlternateName.setAlternateName(countryAlternateNameValues);
		StateProvinceEvent.setAlternateNames(countryAlternateName);
		
		LOG.info("Bind StateProvince AlternateCode");
		AlternateCodes continentAlternateCode= new AlternateCodes();
		List<AlternateCode> continentAlternateCodeValues= new ArrayList<AlternateCode>();
		List<BDDObject> bddObjectAltCode=bddObject.getChildren("AlternateCode");
		for(BDDObject alternateCode : bddObjectAltCode) {
			if(!alternateCode.isRemoved()) {
				AlternateCode altCode= new AlternateCode();
			String typeTypeRowid = Utility.validate(alternateCode.getValue("C_ALT_CODE|TYP_TYPE_ROWID")).toString();
			LOG.info("typeTypeRowid is ::" + typeTypeRowid);
			String getAltCodeQuery = Utility.getDatabaseLookupValue("C_TYP_TYPE", typeTypeRowid, "NAME", "ROWID_OBJECT");
			String altCodeValue = Utility.getValueFromDB(operationContext, getAltCodeQuery);
			altCode.setCodeType(altCodeValue);
			altCode.setCode(Utility.validate(alternateCode.getValue(IDDConstants.MDM_TABLE_ALTCODE_COLUMN_NAME_VALUE)).toString());
			continentAlternateCodeValues.add(altCode);
			}
			}
		continentAlternateCode.setAlternateCode(continentAlternateCodeValues);
		
		StateProvinceEvent.setAlternateCodes(continentAlternateCode);
		
		
		LOG.info("Bind StateProvince Parent");
		List<BDDObject> bddStateProvParent=bddObject.getChildren("Hierarchy");
			if(bddStateProvParent!=null && bddStateProvParent.size()>0) {
				Parent bdaStateProv= new Parent();
				bdaStateProv.setName(Utility.validate(bddStateProvParent.get(0).getValue(IDDConstants.MDM_TABLE_DEFAREA_COLUMN_NAME_NAME)).toString());
			String typeCd=Utility.validate(bddStateProvParent.get(0).getValue(IDDConstants.MDM_TABLE_DEFAREA_COLUMN_NAME_TYPE_CD)).toString();
			String getTypeCdQuery=Utility.getDatabaseLookupValue("C_TYP_TYPE", typeCd, "NAME", "CODE");
			String typCdValue=Utility.getValueFromDB(operationContext, getTypeCdQuery);
			bdaStateProv.setType(typCdValue);
			/******************    200293: Add the Alternate Code to the parent start ***********************/
			com.maersk.mdm.taskdata.jaxb.Geography.StateProvince.Parent.AlternateCodes stateParentAltCodes = new com.maersk.mdm.taskdata.jaxb.Geography.StateProvince.Parent.AlternateCodes();
			List<com.maersk.mdm.taskdata.jaxb.Geography.StateProvince.Parent.AlternateCodes.AlternateCode> stateParentAltCode= stateParentAltCodes.getAlternateCode();
			
			String parentRowidObject=Utility.validate(bddStateProvParent.get(0).getValue(IDDConstants.MDM_TABLE_DEFAREA_COLUMN_NAME_ROWID_OBJECT)).toString();
			LOG.info("parentRowid:" + parentRowidObject);
			stateParentAltCode=(List<com.maersk.mdm.taskdata.jaxb.Geography.StateProvince.Parent.AlternateCodes.AlternateCode>) Utility.getParentAltCodes(parentRowidObject,operationContext,"STATEPROV");
				stateParentAltCodes.setAlternateCode(stateParentAltCode);
				bdaStateProv.setAlternateCodes(stateParentAltCodes);
						
			/*****************************200293: Add the Alternate Code to the parent end *****************/	
			
			StateProvinceEvent.setParent(bdaStateProv);
			}

		
		
			LOG.info("Bind StateProvince BDA");
			BDA stateProvBDA= new BDA();
			List<BDAType> stateProvBDAType= new ArrayList<BDAType>();
			List<BDDObject> bddCountryBDA=bddObject.getChildren("BDA");
			
			for(BDDObject bda : bddCountryBDA) {
				if(!bda.isRemoved()) {
				BDAType bdaStateProv= new BDAType();
				com.maersk.mdm.taskdata.jaxb.Geography.StateProvince.BDA.BDAType.AlternateCodes stateBdaAltCodes=new com.maersk.mdm.taskdata.jaxb.Geography.StateProvince.BDA.BDAType.AlternateCodes();
				List<com.maersk.mdm.taskdata.jaxb.Geography.StateProvince.BDA.BDAType.AlternateCodes.AlternateCode> statebdaAltCode=stateBdaAltCodes.getAlternateCode();
				
				bdaStateProv.setName(Utility.validate(bda.getValue(IDDConstants.MDM_TABLE_DEFAREA_COLUMN_NAME_NAME)).toString());
				String typeTypeRowid = Utility.validate(bda.getValue(IDDConstants.MDM_TABLE_DEFAREA_COLUMN_NAME_TYPE_CD)).toString();
				LOG.info("typeTypeRowid is ::" + typeTypeRowid);
				String getTypeCdQuery=Utility.getDatabaseLookupValue("C_TYP_TYPE", typeTypeRowid, "NAME", "CODE");
				String bdaValue = Utility.getValueFromDB(operationContext, getTypeCdQuery);
				bdaStateProv.setType(bdaValue);
				
				String parentRowidObject=Utility.validate(bda.getValue(IDDConstants.MDM_TABLE_DEFAREA_COLUMN_NAME_ROWID_OBJECT)).toString();
				LOG.info("parentRowid:" + parentRowidObject);
				statebdaAltCode= (List<com.maersk.mdm.taskdata.jaxb.Geography.StateProvince.BDA.BDAType.AlternateCodes.AlternateCode>) Utility.getBDAAltCodes(parentRowidObject,operationContext,"STATEPROV");			
				stateBdaAltCodes.setAlternateCode(statebdaAltCode);
				bdaStateProv.setAlternateCodes(stateBdaAltCodes);
				stateProvBDAType.add(bdaStateProv);
				}
				}
			stateProvBDA.setBdaType(stateProvBDAType);
			StateProvinceEvent.setBDA(stateProvBDA);
		
		geographyEvent.setStateProvince(StateProvinceEvent);
		return geographyEvent;
	}

}
