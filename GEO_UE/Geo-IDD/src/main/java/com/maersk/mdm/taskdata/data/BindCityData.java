/**
 * 
 */
package com.maersk.mdm.taskdata.data;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.text.ParseException;

import org.apache.log4j.Logger;

import com.maersk.mdm.taskdata.jaxb.Geography;
import com.maersk.mdm.taskdata.jaxb.Geography.City;
import com.maersk.mdm.taskdata.jaxb.Geography.City.AlternateCodes;
import com.maersk.mdm.taskdata.jaxb.Geography.City.AlternateCodes.AlternateCode;
import com.maersk.mdm.taskdata.jaxb.Geography.City.AlternateNames;
import com.maersk.mdm.taskdata.jaxb.Geography.City.AlternateNames.AlternateName;
import com.maersk.mdm.taskdata.jaxb.Geography.City.BDA;
import com.maersk.mdm.taskdata.jaxb.Geography.City.BDA.BDAType;
import com.maersk.mdm.taskdata.jaxb.Geography.City.Country;
import com.maersk.mdm.taskdata.jaxb.Geography.City.Parent;
import com.maersk.mdm.taskdata.jaxb.Geography.City.SubCityParent;
import com.maersk.mdm.taskdata.userexit.IDDConstants;
import com.maersk.mdm.taskdata.util.Utility;
import com.siperian.bdd.userexits.datamodel.BDDObject;
import com.siperian.bdd.userexits.operations.OperationContext;
import com.siperian.bdd.userexits.utils.BDDMessagesLocalizationGate;

/**
 * @author AJA350
 *
 */
public class BindCityData {

	private static final Logger LOG = Logger.getLogger(BindGeographyData.class);
	private static BDDObject bddObject;
	private OperationContext operationContext;
	private static String ORSId;
	Connection connection = null;
	private BDDMessagesLocalizationGate bddMessagesLocalizationGate;
	private static Geography geographyEvent;
	private static City cityEvent;

	public BindCityData() {
		super();
	}

	public BindCityData(BDDObject bddObject, String oRSId, OperationContext operationContext,
			BDDMessagesLocalizationGate bddMessagesLocalizationGate, Geography geographyEvent) {

		this.bddObject = bddObject;
		this.operationContext = operationContext;
		this.bddMessagesLocalizationGate = bddMessagesLocalizationGate;
		this.ORSId = oRSId;
		this.geographyEvent = geographyEvent;
		this.cityEvent = new City();

	}

	public Geography bindCityData() throws SQLException {
		LOG.info("Inside method bindCityData");
		geographyEvent = bindCityAllDetails(cityEvent);
		return geographyEvent;
	}

	private Geography bindCityAllDetails(City cityEvent) throws SQLException {
		LOG.info("Inside method bindCityAllDetails");

		LOG.info("Bind City General Details");
		cityEvent.setName(
				Utility.validate(bddObject.getValue(IDDConstants.MDM_TABLE_DEFAREA_COLUMN_NAME_NAME)).toString());
		String status = Utility.validate(bddObject.getValue(IDDConstants.MDM_TABLE_DEFAREA_COLUMN_NAME_STATUS))
				.toString();
		if (status != null) {
			if (status.equalsIgnoreCase("Y"))
				cityEvent.setStatus("Active");
			else if (status.equalsIgnoreCase("N"))
				cityEvent.setStatus("Inactive");
		}
		Object validFromdate=Utility.validate(bddObject.getValue(IDDConstants.MDM_TABLE_DEFAREA_COLUMN_NAME_VALID_FROM));
		LOG.info("Valid From Date is "+validFromdate);
		Object validTodate=Utility.validate(bddObject.getValue(IDDConstants.MDM_TABLE_DEFAREA_COLUMN_NAME_VALID_TO));
		if(bddObject.isCreated()) {
			try {
				validFromdate=Utility.changeInputCreateDate(validFromdate.toString());
				validTodate=Utility.changeInputCreateDate(validTodate.toString());
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		cityEvent.setValidFrom(validFromdate.toString());
		//cityEvent.setValidFrom(Utility.validate(bddObject.getValue(IDDConstants.MDM_TABLE_DEFAREA_COLUMN_NAME_VALID_FROM)).toString());
		LOG.info("Valid To Date is "+validTodate);
		cityEvent.setValidTo(validTodate.toString());
		// cityEvent.setValidTo(Utility.validate(bddObject.getValue(IDDConstants.MDM_TABLE_DEFAREA_COLUMN_NAME_VALID_TO)).toString());
		cityEvent.setLatitude(
				Utility.validate(bddObject.getValue(IDDConstants.MDM_TABLE_DEFAREA_COLUMN_NAME_LATITUDE)).toString());
		cityEvent.setLongitude(
				Utility.validate(bddObject.getValue(IDDConstants.MDM_TABLE_DEFAREA_COLUMN_NAME_LONGITUDE)).toString());
		/*************** Richa Change Start **************/
		cityEvent.setDescription(Utility
				.validate(bddObject.getValue(IDDConstants.MDM_TABLE_DEFAREA_COLUMN_NAME_DESCRIPTION)).toString());
		
		String timeZone=Utility.validate(bddObject.getValue(IDDConstants.MDM_TABLE_DEFAREA_COLUMN_NAME_TIMEZONE)).toString();
		if(timeZone!=null) {
		/******************    200293: TIMEZONE WILL BE CODE INSTEAD OF NAME START ***********************/
		String timeZoneQuery=Utility.getDatabaseLookupValue("C_TDS_TMZ", timeZone, "CODE", "ROWID_OBJECT");	
		/******************    200293: TIMEZONE WILL BE CODE INSTEAD OF NAME END ***********************/
				String getTimeZoneValue=Utility.getValueFromDB(operationContext, timeZoneQuery);
				LOG.info("getTimeZoneValue is ::"+getTimeZoneValue);
				cityEvent.setTimeZone(getTimeZoneValue);
		}
		String dayLightSavingTime=Utility.validate(bddObject.getValue(IDDConstants.MDM_TABLE_DEFAREA_COLUMN_NAME_DST)).toString();
		if(dayLightSavingTime!=null) {
			/******************    200293: TIMEZONE WILL BE CODE INSTEAD OF NAME START***********************/
				String dayLightSavingQuery=Utility.getDatabaseLookupValue("C_TDS_DST", dayLightSavingTime, "CODE", "ROWID_OBJECT");
			/******************    200293: TIMEZONE WILL BE CODE INSTEAD OF NAME START***********************/
				String dalightSavingValue=Utility.getValueFromDB(operationContext,dayLightSavingQuery);
				LOG.info("dalightSavingValue is ::"+dalightSavingValue);
				if(dalightSavingValue!=null && dalightSavingValue.trim().equalsIgnoreCase("EMPT")) {
					cityEvent.setDaylightSavingTime("");
				}else {
				cityEvent.setDaylightSavingTime(dalightSavingValue);
				}
		}
		/******************    ADDITION OF DST OFFSET AND DST START END DATE -- STARTS ***********************/
		String offsetQuery=Utility.validate(bddObject.getValue(IDDConstants.MDM_TABLE_DEFAREA_COLUMN_NAME_TIMEZONE)).toString();
		if(offsetQuery!=null) {
			String utcOffsetMinutesQuery=Utility.getDatabaseLookupValue("C_TDS_TMZ", offsetQuery, "UTC_OFFSET_MINS", "ROWID_OBJECT");
			String utcOffsetInMinutesValue=Utility.getValueFromDB(operationContext,utcOffsetMinutesQuery);
			LOG.info("utcOffsetInMinutesValue is ::"+utcOffsetInMinutesValue);
			cityEvent.setUtOffSetMinutes(utcOffsetInMinutesValue);
		}
		//if(dayLightSavingTime!=null ) {
		if(offsetQuery!=null ) {
			String daylightSavingStart=Utility.getMaxPeriodDate(operationContext,offsetQuery,"PERIOD_START_TM");
			cityEvent.setDaylightSavingStart(daylightSavingStart);
			String daylightSavingEnd=Utility.getMaxPeriodDate(operationContext,offsetQuery,"PERIOD_END_TM");;
			cityEvent.setDaylightSavingEnd(daylightSavingEnd);
			String daylightSavingShiftMinutes=Utility.getMaxPeriodDate(operationContext,offsetQuery,"SHIFT_MINS");
			cityEvent.setDaylightSavingShiftMinutes(daylightSavingShiftMinutes);
			}
		LOG.info("BEFORE CHECK dayLightSavingTime::"+offsetQuery);//+" AND::"+offsetQuery.length());
//		if(dayLightSavingTime==null ||dayLightSavingTime.equalsIgnoreCase("")) {
		if(offsetQuery==null ||offsetQuery.equalsIgnoreCase("")) {
			LOG.info("Inside else dayLightSavingTime");
			cityEvent.setDaylightSavingStart("");
			cityEvent.setDaylightSavingEnd("");
			cityEvent.setDaylightSavingShiftMinutes("");
			LOG.info("cityEvent.setDaylightSavingStart::"+cityEvent.getDaylightSavingStart());
			LOG.info("cityEvent.setDaylightSavingEnd::"+cityEvent.getDaylightSavingEnd());
			LOG.info("cityEvent.setDaylightSavingShiftMinutes::"+cityEvent.getDaylightSavingShiftMinutes());
		}
			/******************
			 * ADDITION OF DST OFFSET AND DST START END DATE -- ENDS
			 ***********************/
			if (bddObject.getObjectName().equalsIgnoreCase("City")) {
			List<BDDObject> WorkAroundReason = bddObject.getChildren("WorkAroundReason");
			if (WorkAroundReason != null && WorkAroundReason.size() > 0) {
				String workaroundReason = Utility.validate(
						WorkAroundReason.get(0).getValue(IDDConstants.MDM_TABLE_C_TMP_WRKRND_RSN_COLUMN_NAME_WR))
						.toString();
				if (workaroundReason != null) {
					String workaroundReasonQuery = Utility.getDatabaseLookupValue("C_TYP_TYPE", workaroundReason,
							"NAME", "ROWID_OBJECT");
					String workaroundReasonValue = Utility.getValueFromDB(operationContext, workaroundReasonQuery);
					cityEvent.setWorkaroundReason(workaroundReasonValue);
				}
			}
			if (WorkAroundReason == null || WorkAroundReason.isEmpty()) {
				cityEvent.setWorkaroundReason("");
			}
			}

			List<BDDObject> cityDetails = bddObject.getChildren("CityDetails");
			if (cityDetails != null && cityDetails.size() > 0) {
				String portFlag = Utility
						.validate(cityDetails.get(0).getValue(IDDConstants.MDM_TABLE_GDACITY_COLUMN_NAME_PORT_FLAG))
						.toString();
				// String
				// isMaerskCity=Utility.validate(cityDetails.get(0).getValue(IDDConstants.MDM_TABLE_GDACITY_COLUMN_NAME_MAERSK_CITY)).toString();
				if (portFlag != null) {
					if (portFlag.equalsIgnoreCase("Y"))
						cityEvent.setPortFlag("Yes");
					else if (portFlag.equalsIgnoreCase("N"))
						cityEvent.setPortFlag("No");
				}
				/*
				 * if(isMaerskCity!=null) {
				 * if(isMaerskCity.equalsIgnoreCase("Y"))
				 * cityEvent.setIsMaerskCity("Yes"); else
				 * if(isMaerskCity.equalsIgnoreCase("N"))
				 * cityEvent.setIsMaerskCity("No"); }
				 */

				cityEvent.setOlsonTimezone(Utility
						.validate(cityDetails.get(0).getValue(IDDConstants.MDM_TABLE_GDACITY_COLUMN_NAME_OLSONTZ))
						.toString());
			}

		

		if (bddObject.getObjectName().equalsIgnoreCase("CitySubArea")) {
			/*String portFlag = "No";
			cityEvent.setPortFlag(portFlag);

			cityEvent.setTimeZone("");

			cityEvent.setDaylightSavingTime("");

			cityEvent.setUTCOffsetMinutes("");

			cityEvent.setDaylightSavingStart("");
			cityEvent.setDaylightSavingEnd("");
			cityEvent.setDaylightSavingShiftMinutes("");*/

			String workaroundReason = null;
			cityEvent.setWorkaroundReason("");
		/*	cityEvent.setOlsonTimezone("");*/

		}

		/*************** Richa City Change END ***************/

		LOG.info("Bind City AlternateName");
		AlternateNames cityAlternateName = new AlternateNames();
		List<AlternateName> cityAlternateNameValues = new ArrayList<AlternateName>();
		List<BDDObject> bddObjectAltName = bddObject.getChildren("AlternateName");
		for (BDDObject alternateName : bddObjectAltName) {
			if (!alternateName.isRemoved()) {
				AlternateName altName = new AlternateName();
				altName.setName(Utility
						.validate(alternateName.getValue(IDDConstants.MDM_TABLE_ALTNAME_COLUMN_NAME_NAME)).toString());
				altName.setDescription(
						Utility.validate(alternateName.getValue(IDDConstants.MDM_TABLE_ALTNAME_COLUMN_NAME_DESCRIPTION))
								.toString());
				String isStatus = Utility
						.validate(alternateName.getValue(IDDConstants.MDM_TABLE_ALTNAME_COLUMN_NAME_STATUS)).toString();
				if (isStatus != null) {
					if (isStatus.equalsIgnoreCase("Y"))
						altName.setStatus("Active");
					else if (isStatus.equalsIgnoreCase("N"))
						altName.setStatus("Inactive");
				}
				cityAlternateNameValues.add(altName);
			}
		}
		cityAlternateName.setAlternateName(cityAlternateNameValues);
		cityEvent.setAlternateNames(cityAlternateName);

		LOG.info("Bind country AlternateCode");
		AlternateCodes cityAlternateCode = new AlternateCodes();
		List<AlternateCode> cityAlternateCodeValues = new ArrayList<AlternateCode>();
		List<BDDObject> bddObjectAltCode = bddObject.getChildren("AlternateCode");
		for (BDDObject alternateCode : bddObjectAltCode) {
			if (!alternateCode.isRemoved()) {
				AlternateCode altCode = new AlternateCode();
				String typeTypeRowid = Utility.validate(alternateCode.getValue("C_ALT_CODE|TYP_TYPE_ROWID")).toString();
				LOG.info("typeTypeRowid is ::" + typeTypeRowid);
				String getAltCodeQuery = Utility.getDatabaseLookupValue("C_TYP_TYPE", typeTypeRowid, "NAME",
						"ROWID_OBJECT");
				String altCodeValue = Utility.getValueFromDB(operationContext, getAltCodeQuery);
				altCode.setCodeType(altCodeValue);
				altCode.setCode(Utility
						.validate(alternateCode.getValue(IDDConstants.MDM_TABLE_ALTCODE_COLUMN_NAME_VALUE)).toString());
				cityAlternateCodeValues.add(altCode);
			}
		}
		cityAlternateCode.setAlternateCode(cityAlternateCodeValues);
		cityEvent.setAlternateCodes(cityAlternateCode);

        LOG.info("Bind City Parent");
        List<BDDObject> bddCityParent=bddObject.getChildren("Hierarchy");
        if (bddObject.getObjectName().equalsIgnoreCase("City")) {
        if(bddCityParent!=null && bddCityParent.size()>0) {
                      Parent bdaCity= new Parent();
        bdaCity.setName(Utility.validate(bddCityParent.get(0).getValue(IDDConstants.MDM_TABLE_DEFAREA_COLUMN_NAME_NAME)).toString());
        String typeCd=Utility.validate(bddCityParent.get(0).getValue(IDDConstants.MDM_TABLE_DEFAREA_COLUMN_NAME_TYPE_CD)).toString();
        String getTypeCdQuery=Utility.getDatabaseLookupValue("C_TYP_TYPE", typeCd, "NAME", "CODE");
        String typCdValue=Utility.getValueFromDB(operationContext, getTypeCdQuery);
        bdaCity.setType(typCdValue);
        /******************    200293: Add the Alternate Code to the parent start ***********************/
com.maersk.mdm.taskdata.jaxb.Geography.City.Parent.AlternateCodes cityParentAltCodes = new com.maersk.mdm.taskdata.jaxb.Geography.City.Parent.AlternateCodes();
List<com.maersk.mdm.taskdata.jaxb.Geography.City.Parent.AlternateCodes.AlternateCode> cityParentAltCode= cityParentAltCodes.getAlternateCode();
        
        String parentRowidObject=Utility.validate(bddCityParent.get(0).getValue(IDDConstants.MDM_TABLE_DEFAREA_COLUMN_NAME_ROWID_OBJECT)).toString();
        LOG.info("parentRowid:" + parentRowidObject);
cityParentAltCode=(List<com.maersk.mdm.taskdata.jaxb.Geography.City.Parent.AlternateCodes.AlternateCode>) Utility.getParentAltCodes(parentRowidObject,operationContext,"CITY");
cityParentAltCodes.setAlternateCode(cityParentAltCode);
                      bdaCity.setAlternateCodes(cityParentAltCodes);                                                                         
                                                   
        /*****************************200293: Add the Alternate Code to the parent end *****************/ 
        cityEvent.setParent(bdaCity);
        /******************    200293: Added Parent Type Code to add country Start***********************/
        LOG.info("Bind City Country");
        Country  cnt = new Country();
        if (typCdValue!=null) {
             com.maersk.mdm.taskdata.jaxb.Geography.City.Country.AlternateCodes cityCountryAltCodes = new com.maersk.mdm.taskdata.jaxb.Geography.City.Country.AlternateCodes();
        List<com.maersk.mdm.taskdata.jaxb.Geography.City.Country.AlternateCodes.AlternateCode> cityCountryAltCode = new ArrayList<com.maersk.mdm.taskdata.jaxb.Geography.City.Country.AlternateCodes.AlternateCode>();
        //           cityCountryAltCodes = (com.maersk.mdm.taskdata.jaxb.Geography.City.Country.AlternateCodes) cityParentAltCodes;
                      if ("Country".equalsIgnoreCase(typCdValue)) {
                      cnt.setName(Utility.validate(bddCityParent.get(0).getValue(IDDConstants.MDM_TABLE_DEFAREA_COLUMN_NAME_NAME)).toString());
                      cityCountryAltCode=(List<com.maersk.mdm.taskdata.jaxb.Geography.City.Country.AlternateCodes.AlternateCode>) Utility.getParentAltCodes(parentRowidObject,operationContext,"CITYCOUNTRT");
                                    cityCountryAltCodes.setAlternateCode(cityCountryAltCode);
                                    cnt.setAlternateCodes(cityCountryAltCodes);
                                     cityEvent.setCountry(cnt);
        }
//else it is state.
                      else {
                                     // Set Country name
                                     LOG.info("Inside the else. it is not Country");
                                     String CountryRowid=Utility.getCountryfromState(parentRowidObject,operationContext);                                                          
                                     String getCountryNameQuery = Utility.getDatabaseLookupValue("C_GDA_DFND_AREA", CountryRowid, "NAME", "ROWID_OBJECT");
                                     String CountryName = Utility.getValueFromDB(operationContext, getCountryNameQuery);
                                     cnt.setName(CountryName);                                                                                            
                                     // Set AltCode
                                     cityCountryAltCode=(List<com.maersk.mdm.taskdata.jaxb.Geography.City.Country.AlternateCodes.AlternateCode>) Utility.getParentAltCodes(CountryRowid,operationContext,"CITYCOUNTRT");
                                                   cityCountryAltCodes.setAlternateCode(cityCountryAltCode);
                                                   cnt.setAlternateCodes(cityCountryAltCodes);
                                                   cityEvent.setCountry(cnt);

                      /******************    200293: Added Parent Type Code to add country end ***********************/
        }
        
        }
        }
	}
     /***********Richa code Added for CitySubArea********/
        LOG.info("Bind parent and SubCityParent for CITYSubArea");
        //List<BDDObject> bddCityParent=bddObject.getChildren("Hierarchy");
        if (bddObject.getObjectName().equalsIgnoreCase("CitySubArea")) {
            if(bddCityParent!=null && bddCityParent.size()>0) {
                 Parent cityParent= new Parent();
                 SubCityParent subCityParent = new SubCityParent();
                 String parentRowidObject=Utility.validate(bddCityParent.get(0).getValue(IDDConstants.MDM_TABLE_DEFAREA_COLUMN_NAME_ROWID_OBJECT)).toString();
                 LOG.info("parentRowid:" + parentRowidObject);
             
                 /******************get Parent****************/
                 LOG.info("Bind City Parent");
                String getCityParentRowid = Utility.getParentfromCity(parentRowidObject,operationContext);
                LOG.info("Utility getCityParentRowid" + getCityParentRowid);
               String getCityPrntName = Utility.getDatabaseLookupValue("C_GDA_DFND_AREA", getCityParentRowid, "NAME",
						"ROWID_OBJECT");
				String CityPrntNm = Utility.getValueFromDB(operationContext, getCityPrntName);
				cityParent.setName(CityPrntNm);
				String getCityPrntTyp = Utility.getDatabaseLookupValue("C_GDA_DFND_AREA", getCityParentRowid, "TYP_TYPE_CD",
						"ROWID_OBJECT");
				String CityPrntTyp = Utility.getValueFromDB(operationContext, getCityPrntTyp);
				if (CityPrntTyp != null) {
					if (CityPrntTyp.equalsIgnoreCase("GDA.STATE/PROV"))
						cityParent.setType("State/Prov");
					else if (CityPrntTyp.equalsIgnoreCase("GDA.COUNTRY"))
						cityParent.setType("Country");
				}
				com.maersk.mdm.taskdata.jaxb.Geography.City.Parent.AlternateCodes cityParentAltCodes1 = new com.maersk.mdm.taskdata.jaxb.Geography.City.Parent.AlternateCodes();
				List<com.maersk.mdm.taskdata.jaxb.Geography.City.Parent.AlternateCodes.AlternateCode> cityParentAltCode1= cityParentAltCodes1.getAlternateCode();
				cityParentAltCode1=(List<com.maersk.mdm.taskdata.jaxb.Geography.City.Parent.AlternateCodes.AlternateCode>) Utility.getParentAltCodes(getCityParentRowid,operationContext,"CITY");
				cityParentAltCodes1.setAlternateCode(cityParentAltCode1);
				cityParent.setAlternateCodes(cityParentAltCodes1);  
				cityEvent.setParent(cityParent);
				
                 /******************get Parent****************/
                 subCityParent.setName(Utility.validate(bddCityParent.get(0).getValue(IDDConstants.MDM_TABLE_DEFAREA_COLUMN_NAME_NAME)).toString());
            String typeCd=Utility.validate(bddCityParent.get(0).getValue(IDDConstants.MDM_TABLE_DEFAREA_COLUMN_NAME_TYPE_CD)).toString();
            String getTypeCdQuery=Utility.getDatabaseLookupValue("C_TYP_TYPE", typeCd, "NAME", "CODE");
            String typCdValue=Utility.getValueFromDB(operationContext, getTypeCdQuery);
            subCityParent.setType(typCdValue);
            /******************    200293: Add the Alternate Code to the parent start ***********************/
    com.maersk.mdm.taskdata.jaxb.Geography.City.SubCityParent.AlternateCodes cityParentAltCodes = new com.maersk.mdm.taskdata.jaxb.Geography.City.SubCityParent.AlternateCodes();
    List<com.maersk.mdm.taskdata.jaxb.Geography.City.SubCityParent.AlternateCodes.AlternateCode> cityParentAltCode= cityParentAltCodes.getAlternateCode();
            
            /*String parentRowidObject=Utility.validate(bddCityParent.get(0).getValue(IDDConstants.MDM_TABLE_DEFAREA_COLUMN_NAME_ROWID_OBJECT)).toString();
            LOG.info("parentRowid:" + parentRowidObject);*/
    cityParentAltCode=(List<com.maersk.mdm.taskdata.jaxb.Geography.City.SubCityParent.AlternateCodes.AlternateCode>) Utility.getParentAltCodes(parentRowidObject,operationContext,"SUBCITY");
    cityParentAltCodes.setAlternateCode(cityParentAltCode);
    subCityParent.setAlternateCodes(cityParentAltCodes);                                                                         
                                                       
            /*****************************200293: Add the Alternate Code to the parent end *****************/ 
            cityEvent.setSubCityParent(subCityParent);
            /******************    200293: Added Parent Type Code to add country Start***********************/
            LOG.info("Bind SubCity Country");
            Country  cnt = new Country();
            if (typCdValue!=null) {
            	com.maersk.mdm.taskdata.jaxb.Geography.City.Country.AlternateCodes cityCountryAltCodes = new com.maersk.mdm.taskdata.jaxb.Geography.City.Country.AlternateCodes();
				List<com.maersk.mdm.taskdata.jaxb.Geography.City.Country.AlternateCodes.AlternateCode> cityCountryAltCode = new ArrayList<com.maersk.mdm.taskdata.jaxb.Geography.City.Country.AlternateCodes.AlternateCode>();

				int cntFlag = Utility.IsCityROWIDRelCountry(parentRowidObject, operationContext);
				String CountryRowid = null;
				if (cntFlag > 0) {
					LOG.info("City Directly Connected to Country");
					CountryRowid = Utility.getCountryfromCity(parentRowidObject, operationContext);
					LOG.info("CountryRowid" + CountryRowid);
				} else {
					LOG.info("City  Connected to State");
					String StateRowid = Utility.getStatefromCity(parentRowidObject, operationContext);
					CountryRowid = Utility.getCountryfromState(StateRowid, operationContext);
					LOG.info("CountryRowid" + CountryRowid);
				}

				String getCountryNameQuery = Utility.getDatabaseLookupValue("C_GDA_DFND_AREA", CountryRowid, "NAME",
						"ROWID_OBJECT");
				String CountryName = Utility.getValueFromDB(operationContext, getCountryNameQuery);
				cnt.setName(CountryName);
				// set Alt Code
				cityCountryAltCode = (List<com.maersk.mdm.taskdata.jaxb.Geography.City.Country.AlternateCodes.AlternateCode>) Utility
						.getParentAltCodes(CountryRowid, operationContext, "CITYCOUNTRT");
				cityCountryAltCodes.setAlternateCode(cityCountryAltCode);
				cnt.setAlternateCodes(cityCountryAltCodes);
				cityEvent.setCountry(cnt);

                          /******************    200293: Added Parent Type Code to add country end ***********************/
            }
            
            }
            }
    	
            
        /***********Richa code Added for CitySubArea********/
        
        
        LOG.info("Bind City BDA");
                      BDA cityBDA= new BDA();
                      List<BDAType> cityBDAType= new ArrayList<BDAType>();
                      List<BDDObject> bddCityBDA=bddObject.getChildren("BDA");
                      
                      for(BDDObject bda : bddCityBDA) {
                                     if(!bda.isRemoved()) {
                                     BDAType bdaCity= new BDAType();
                      com.maersk.mdm.taskdata.jaxb.Geography.City.BDA.BDAType.AlternateCodes cityBdaAltCodes=new com.maersk.mdm.taskdata.jaxb.Geography.City.BDA.BDAType.AlternateCodes();
                      List<com.maersk.mdm.taskdata.jaxb.Geography.City.BDA.BDAType.AlternateCodes.AlternateCode> citybdaAltCode=cityBdaAltCodes.getAlternateCode();

                      bdaCity.setName(Utility.validate(bda.getValue(IDDConstants.MDM_TABLE_DEFAREA_COLUMN_NAME_NAME)).toString());
                                     String typeTypeRowid = Utility.validate(bda.getValue(IDDConstants.MDM_TABLE_DEFAREA_COLUMN_NAME_TYPE_CD)).toString();
                                     LOG.info("typeTypeRowid is ::" + typeTypeRowid);
                                     String getTypeCdQuery=Utility.getDatabaseLookupValue("C_TYP_TYPE", typeTypeRowid, "NAME", "CODE");
                                     String bdaValue = Utility.getValueFromDB(operationContext, getTypeCdQuery);
                                     bdaCity.setType(bdaValue);
                                     
                                     String parentRowidObject=Utility.validate(bda.getValue(IDDConstants.MDM_TABLE_DEFAREA_COLUMN_NAME_ROWID_OBJECT)).toString();
                                     LOG.info("parentRowid:" + parentRowidObject);
                      citybdaAltCode=(List<com.maersk.mdm.taskdata.jaxb.Geography.City.BDA.BDAType.AlternateCodes.AlternateCode>)Utility.getBDAAltCodes(parentRowidObject, operationContext, "CITY"); 
                                    cityBdaAltCodes.setAlternateCode(citybdaAltCode);
                                    bdaCity.setAlternateCodes(cityBdaAltCodes);
                                     cityBDAType.add(bdaCity);
                      }
                      }
                      cityBDA.setBdaType(cityBDAType);
                      
                      cityEvent.setBDA(cityBDA);
                      //cityEvent.setBda(bda);(cityBDA);
        
        geographyEvent.setCity(cityEvent);
        return geographyEvent;
}

}

