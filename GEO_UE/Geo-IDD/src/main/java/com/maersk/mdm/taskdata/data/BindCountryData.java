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
import com.maersk.mdm.taskdata.jaxb.Geography.Country;
import com.maersk.mdm.taskdata.jaxb.Geography.Country.AlternateCodes;
import com.maersk.mdm.taskdata.jaxb.Geography.Country.AlternateCodes.AlternateCode;
import com.maersk.mdm.taskdata.jaxb.Geography.Country.AlternateNames;
import com.maersk.mdm.taskdata.jaxb.Geography.Country.AlternateNames.AlternateName;
import com.maersk.mdm.taskdata.jaxb.Geography.Country.BDA;
import com.maersk.mdm.taskdata.jaxb.Geography.Country.BDA.BDAType;
import com.maersk.mdm.taskdata.jaxb.Geography.Country.Parent;
import com.maersk.mdm.taskdata.userexit.IDDConstants;
import com.maersk.mdm.taskdata.util.Utility;
import com.siperian.bdd.userexits.datamodel.BDDObject;
import com.siperian.bdd.userexits.operations.OperationContext;
import com.siperian.bdd.userexits.utils.BDDMessagesLocalizationGate;
/**
 * @author AJA350
 *
 */
public class BindCountryData {
	
	private static final Logger LOG = Logger.getLogger(BindContinentData.class);
	private static BDDObject bddObject;
	private OperationContext operationContext;
	private static String ORSId; 
	Connection connection = null;
	private BDDMessagesLocalizationGate bddMessagesLocalizationGate;
	private static Geography geographyEvent;
	private static Country countryEvent; 
	public BindCountryData() {
	super();
	}

	public BindCountryData(BDDObject bddObject, String oRSId, OperationContext operationContext,
			BDDMessagesLocalizationGate bddMessagesLocalizationGate, Geography geographyEvent) {
		// TODO Auto-generated constructor stub
		this.bddObject=bddObject;
		this.operationContext=operationContext;
		this.bddMessagesLocalizationGate=bddMessagesLocalizationGate;
		this.ORSId=oRSId;
		this.geographyEvent=geographyEvent;
		this.countryEvent=new Country();

	}

	public Geography bindCountryData() {
		LOG.info("Inside method bindCountryData");
		try {
			geographyEvent=bindCountryAllDetails(countryEvent);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			LOG.info("Error in Generating Country Event.");
			e.printStackTrace();
		}
		return geographyEvent;
		
	}

	private Geography bindCountryAllDetails(Country countryEvent) throws SQLException {
		LOG.info("Inside method bindCountryAllDetails");
		
		LOG.info("Bind Country General Details");
		countryEvent.setName(Utility.validate(bddObject.getValue(IDDConstants.MDM_TABLE_DEFAREA_COLUMN_NAME_NAME)).toString());
		String status=Utility.validate(bddObject.getValue(IDDConstants.MDM_TABLE_DEFAREA_COLUMN_NAME_STATUS)).toString();
		if(status!=null) {
				if(status.equalsIgnoreCase("Y"))
					countryEvent.setStatus("Active");
				else if(status.equalsIgnoreCase("N"))
					countryEvent.setStatus("Inactive");
		}
		//countryEvent.setValidFrom(Utility.validate(bddObject.getValue(IDDConstants.MDM_TABLE_DEFAREA_COLUMN_NAME_VALID_FROM)).toString());
		//countryEvent.setValidTo(Utility.validate(bddObject.getValue(IDDConstants.MDM_TABLE_DEFAREA_COLUMN_NAME_VALID_TO)).toString());
		
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
		countryEvent.setValidFrom(validFromdate.toString());
		countryEvent.setValidTo(validTodate.toString());
		
		
		String timeZone=Utility.validate(bddObject.getValue(IDDConstants.MDM_TABLE_DEFAREA_COLUMN_NAME_TIMEZONE)).toString();
		if(timeZone!=null) {
			/******************    200293: TIMEZONE WILL BE CODE INSTEAD OF NAME START ***********************/
		String timeZoneQuery=Utility.getDatabaseLookupValue("C_TDS_TMZ", timeZone, "CODE", "ROWID_OBJECT");	
		/******************    200293: TIMEZONE WILL BE CODE INSTEAD OF NAME END ***********************/
				String getTimeZoneValue=Utility.getValueFromDB(operationContext, timeZoneQuery);
				countryEvent.setTimeZone(getTimeZoneValue);
		}
		String dayLightSavingTime=Utility.validate(bddObject.getValue(IDDConstants.MDM_TABLE_DEFAREA_COLUMN_NAME_DST)).toString();
		if(dayLightSavingTime!=null) {
			/******************    200293: TIMEZONE WILL BE CODE INSTEAD OF NAME START***********************/
				String dayLightSavingQuery=Utility.getDatabaseLookupValue("C_TDS_DST", dayLightSavingTime, "CODE", "ROWID_OBJECT");
			/******************    200293: TIMEZONE WILL BE CODE INSTEAD OF NAME END ***********************/
				String dalightSavingValue=Utility.getValueFromDB(operationContext,dayLightSavingQuery);
				if(dalightSavingValue!=null && !dalightSavingValue.equalsIgnoreCase("") && dalightSavingValue.trim().equalsIgnoreCase("EMPT")) {
					countryEvent.setDaylightSavingTime("");
					}
					else {
						countryEvent.setDaylightSavingTime(dalightSavingValue);	
					}
						
		}
		if(dayLightSavingTime==null || dayLightSavingTime.equalsIgnoreCase("")) {
						countryEvent.setDaylightSavingTime("");
		}
		countryEvent.setDescription(Utility.validate(bddObject.getValue(IDDConstants.MDM_TABLE_DEFAREA_COLUMN_NAME_DESCRIPTION)).toString());
		List<BDDObject> WorkAroundReason=bddObject.getChildren("WorkAroundReason");
			if(WorkAroundReason!=null && WorkAroundReason.size()>0) {
				String workaroundReason=Utility.validate(WorkAroundReason.get(0).getValue(IDDConstants.MDM_TABLE_C_TMP_WRKRND_RSN_COLUMN_NAME_WR)).toString();
					if(workaroundReason!=null) {
							String workaroundReasonQuery=Utility.getDatabaseLookupValue("C_TYP_TYPE", workaroundReason, "NAME", "ROWID_OBJECT");	
								String workaroundReasonValue=Utility.getValueFromDB(operationContext, workaroundReasonQuery);
									countryEvent.setWorkaroundReason(workaroundReasonValue);
					}
			}
			if(WorkAroundReason==null || WorkAroundReason.isEmpty()) {
				countryEvent.setWorkaroundReason("");
			}
		
		List<BDDObject> countryDetails=bddObject.getChildren("CountryDetails");
			for(BDDObject cntrydtls: countryDetails) {
			String isRestrcted=Utility.validate(cntrydtls.getValue(IDDConstants.MDM_TABLE_GDACOUNTRY_COLUMN_CNTRY_RSTRCTD_FLAG)).toString();
					if(isRestrcted!=null) {
						if(isRestrcted.equalsIgnoreCase("Y"))
							countryEvent.setRestricted("Yes");
						else if(isRestrcted.equalsIgnoreCase("N"))
							countryEvent.setRestricted("No");
					}
			String postalCdMndtryFlag=Utility.validate(cntrydtls.getValue(IDDConstants.MDM_TABLE_GDACOUNTRY_COLUMN_NAME_POSTAL_CD_MND)).toString();
			if(postalCdMndtryFlag!=null) {
				if(postalCdMndtryFlag.equalsIgnoreCase("O"))
					countryEvent.setPostalCodeMandatoryFlag("Optional");
				else if(postalCdMndtryFlag.equalsIgnoreCase("M"))
					countryEvent.setPostalCodeMandatoryFlag("Mandatory");
				}
			countryEvent.setStateProvinceMandatory(Utility.validate(cntrydtls.getValue(IDDConstants.MDM_TABLE_GDACOUNTRY_COLUMN_CNTRY_STATE_MND_FLAG)).toString());
		
		}
		List<BDDObject> CountryDialingCode=bddObject.getChildren("CountryDialingCode");
			for(BDDObject ctryDlCd: CountryDialingCode) {
			countryEvent.setDialingCode(Utility.validate(ctryDlCd.getValue(IDDConstants.MDM_TABLE_GDACOUNTRY_COLUMN_CNTRY_DL_CD)).toString());
			countryEvent.setDialingCodedescription(Utility.validate(ctryDlCd.getValue(IDDConstants.MDM_TABLE_GDACOUNTRY_COLUMN_CNTRY__DL_CD_DESC)).toString());
			}
		
		LOG.info("Bind country AlternateName");
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
		countryEvent.setAlternateNames(countryAlternateName);
		
		LOG.info("Bind country AlternateCode");
		AlternateCodes countryAlternateCode= new AlternateCodes();
		List<AlternateCode> countryAlternateCodeValues= new ArrayList<AlternateCode>();
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
			countryAlternateCodeValues.add(altCode);
			}
			}
		countryAlternateCode.setAlternateCode(countryAlternateCodeValues);
		countryEvent.setAlternateCodes(countryAlternateCode);
		
		LOG.info("Bind country Parent");
		List<BDDObject> bddCountryParent=bddObject.getChildren("Hierarchy");
			if(bddCountryParent!=null && bddCountryParent.size()>0) {
			Parent bdaCountry= new Parent();
			bdaCountry.setName(Utility.validate(bddCountryParent.get(0).getValue(IDDConstants.MDM_TABLE_DEFAREA_COLUMN_NAME_NAME)).toString());
			String typeCd=Utility.validate(bddCountryParent.get(0).getValue(IDDConstants.MDM_TABLE_DEFAREA_COLUMN_NAME_TYPE_CD)).toString();
			String getTypeCdQuery=Utility.getDatabaseLookupValue("C_TYP_TYPE", typeCd, "NAME", "CODE");
			String typCdValue=Utility.getValueFromDB(operationContext, getTypeCdQuery);
			bdaCountry.setType(typCdValue);
			/******************    200293: Add the Alternate Code to the parent start ***********************/
			com.maersk.mdm.taskdata.jaxb.Geography.Country.Parent.AlternateCodes countryParentAltCodes = new com.maersk.mdm.taskdata.jaxb.Geography.Country.Parent.AlternateCodes();
			List<com.maersk.mdm.taskdata.jaxb.Geography.Country.Parent.AlternateCodes.AlternateCode> countryParentAltCode= countryParentAltCodes.getAlternateCode();
			
			String parentRowidObject=Utility.validate(bddCountryParent.get(0).getValue(IDDConstants.MDM_TABLE_DEFAREA_COLUMN_NAME_ROWID_OBJECT)).toString();
			LOG.info("parentRowid:" + parentRowidObject);
			    countryParentAltCode= (List<com.maersk.mdm.taskdata.jaxb.Geography.Country.Parent.AlternateCodes.AlternateCode>) Utility.getParentAltCodes(parentRowidObject,operationContext,"COUNTRY");			
				countryParentAltCodes.setAlternateCode(countryParentAltCode);
				bdaCountry.setAlternateCodes(countryParentAltCodes);	
				countryEvent.setParent(bdaCountry);
			}			
			/*****************************200293: Add the Alternate Code to the parent end *****************/
			LOG.info("Bind country BDA");
		BDA countryBDA= new BDA();
		List<BDAType> countryBDAValues= new ArrayList<BDAType>();
		List<BDDObject> bddCountryBDA=bddObject.getChildren("BDA");
	
		
		for(BDDObject bda : bddCountryBDA) {
			if(!bda.isRemoved()) {
				BDAType bdaCtry= new BDAType();
			com.maersk.mdm.taskdata.jaxb.Geography.Country.BDA.BDAType.AlternateCodes countryBdaAltCodes=new com.maersk.mdm.taskdata.jaxb.Geography.Country.BDA.BDAType.AlternateCodes();
			List<com.maersk.mdm.taskdata.jaxb.Geography.Country.BDA.BDAType.AlternateCodes.AlternateCode> countrybdaAltCode=countryBdaAltCodes.getAlternateCode();
			bdaCtry.setName(Utility.validate(bda.getValue(IDDConstants.MDM_TABLE_DEFAREA_COLUMN_NAME_NAME)).toString());
			String typeTypeRowid = Utility.validate(bda.getValue(IDDConstants.MDM_TABLE_DEFAREA_COLUMN_NAME_TYPE_CD)).toString();
			LOG.info("typeTypeRowid is ::" + typeTypeRowid);
			String getTypeCdQuery=Utility.getDatabaseLookupValue("C_TYP_TYPE", typeTypeRowid, "NAME", "CODE");
			String bdaValue = Utility.getValueFromDB(operationContext, getTypeCdQuery);
			bdaCtry.setType(bdaValue);
			
			String parentRowidObject=Utility.validate(bda.getValue(IDDConstants.MDM_TABLE_DEFAREA_COLUMN_NAME_ROWID_OBJECT)).toString();
			LOG.info("parentRowid:" + parentRowidObject);
			countrybdaAltCode= (List<com.maersk.mdm.taskdata.jaxb.Geography.Country.BDA.BDAType.AlternateCodes.AlternateCode>) Utility.getBDAAltCodes(parentRowidObject,operationContext,"COUNTRY");			
			countryBdaAltCodes.setAlternateCode(countrybdaAltCode);
			bdaCtry.setAlternateCodes(countryBdaAltCodes);
			countryBDAValues.add(bdaCtry);
			}
			}

		countryBDA.setBdaType(countryBDAValues);
		countryEvent.setBDA(countryBDA);
		geographyEvent.setCountry(countryEvent);
		return geographyEvent;
	}	
}
