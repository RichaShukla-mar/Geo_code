package com.maersk.mdm.taskdata.data;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.maersk.mdm.taskdata.jaxb.Geography;
import com.maersk.mdm.taskdata.jaxb.Geography.PostalCode;
import com.maersk.mdm.taskdata.jaxb.Geography.City.Country;
import com.maersk.mdm.taskdata.jaxb.Geography.PostalCode.AlternateCodes;
import com.maersk.mdm.taskdata.jaxb.Geography.PostalCode.AlternateCodes.AlternateCode;
import com.maersk.mdm.taskdata.jaxb.Geography.PostalCode.BDA;
import com.maersk.mdm.taskdata.jaxb.Geography.PostalCode.BDA.BDAType;
import com.maersk.mdm.taskdata.jaxb.Geography.PostalCode.Parent;
import com.maersk.mdm.taskdata.userexit.IDDConstants;
import com.maersk.mdm.taskdata.util.Utility;
import com.siperian.bdd.userexits.datamodel.BDDObject;
import com.siperian.bdd.userexits.operations.OperationContext;
import com.siperian.bdd.userexits.utils.BDDMessagesLocalizationGate;

public class BindPostalCodeData {

	private static final Logger LOG = Logger.getLogger(BindGeographyData.class);
	private static BDDObject bddObject;
	private OperationContext operationContext;
	private static String ORSId; 
	Connection connection = null;
	private BDDMessagesLocalizationGate bddMessagesLocalizationGate;
	private static Geography geographyEvent;
	 private static PostalCode postalCodeEvent; 


	public BindPostalCodeData() {
		super();
	}

	public BindPostalCodeData(BDDObject bddObject, String oRSId, OperationContext operationContext,
			BDDMessagesLocalizationGate bddMessagesLocalizationGate, Geography geographyEvent) {
		this.bddObject=bddObject;
		this.operationContext=operationContext;
		this.bddMessagesLocalizationGate=bddMessagesLocalizationGate;
		this.ORSId=oRSId;
		this.geographyEvent=geographyEvent;
		this.postalCodeEvent=new PostalCode();
	}

	public Geography bindPostalCodeData() throws SQLException {
		LOG.info("Inside method bindPostalCodeData");
		geographyEvent=bindpostalCodeAllDetails(postalCodeEvent);
		return geographyEvent;
	}

	private Geography bindpostalCodeAllDetails(PostalCode postalCodeEvent) throws SQLException {
		LOG.info("Inside method bindpostalCodeAllDetails");
		LOG.info("Bind PostalCode General details");
		postalCodeEvent.setName(Utility.validate(bddObject.getValue(IDDConstants.MDM_TABLE_DEFAREA_COLUMN_NAME_NAME)).toString());
		String status=Utility.validate(bddObject.getValue(IDDConstants.MDM_TABLE_DEFAREA_COLUMN_NAME_STATUS)).toString();
		if(status!=null) {
				if(status.equalsIgnoreCase("Y"))
					postalCodeEvent.setStatus("Active");
				else if(status.equalsIgnoreCase("N"))
					postalCodeEvent.setStatus("Inactive");
		}
		//postalCodeEvent.setValidFrom(Utility.validate(bddObject.getValue(IDDConstants.MDM_TABLE_DEFAREA_COLUMN_NAME_VALID_FROM)).toString());
		//postalCodeEvent.setValidTo(Utility.validate(bddObject.getValue(IDDConstants.MDM_TABLE_DEFAREA_COLUMN_NAME_VALID_TO)).toString());
		
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
		postalCodeEvent.setValidFrom(validFromdate.toString());
		postalCodeEvent.setValidTo(validTodate.toString());
		
		
		postalCodeEvent.setDescription(Utility.validate(bddObject.getValue(IDDConstants.MDM_TABLE_DEFAREA_COLUMN_NAME_DESCRIPTION)).toString());
		
		LOG.info("Bind PostalCode AlternateCode");
		AlternateCodes pstlAlternateCode= new AlternateCodes();
		List<AlternateCode> pstlAlternateCodeValues= new ArrayList<AlternateCode>();
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
			pstlAlternateCodeValues.add(altCode);
		}
	}
		pstlAlternateCode.setAlternateCode(pstlAlternateCodeValues);
		postalCodeEvent.setAlternateCodes(pstlAlternateCode);
	
		LOG.info("Bind PostalCode Parent");
		List<BDDObject> bddPstlCdParent=bddObject.getChildren("Hierarchy");
		if(bddPstlCdParent!=null && bddPstlCdParent.size()>0) {
			Parent bdapstlCd= new Parent();
			bdapstlCd.setName(Utility.validate(bddPstlCdParent.get(0).getValue(IDDConstants.MDM_TABLE_DEFAREA_COLUMN_NAME_NAME)).toString());
		String typeCd=Utility.validate(bddPstlCdParent.get(0).getValue(IDDConstants.MDM_TABLE_DEFAREA_COLUMN_NAME_TYPE_CD)).toString();
		String getTypeCdQuery=Utility.getDatabaseLookupValue("C_TYP_TYPE", typeCd, "NAME", "CODE");
		String typCdValue=Utility.getValueFromDB(operationContext, getTypeCdQuery);
		bdapstlCd.setType(typCdValue);
		/******************    200293: Add the Alternate Code to the parent start ***********************/
		com.maersk.mdm.taskdata.jaxb.Geography.PostalCode.Parent.AlternateCodes PostalCDParentAltCodes = new com.maersk.mdm.taskdata.jaxb.Geography.PostalCode.Parent.AlternateCodes();
		List<com.maersk.mdm.taskdata.jaxb.Geography.PostalCode.Parent.AlternateCodes.AlternateCode> PostalCDParentAltCode= PostalCDParentAltCodes.getAlternateCode();
		
		String parentRowidObject=Utility.validate(bddPstlCdParent.get(0).getValue(IDDConstants.MDM_TABLE_DEFAREA_COLUMN_NAME_ROWID_OBJECT)).toString();
		LOG.info("parentRowid:" + parentRowidObject);
		PostalCDParentAltCode=(List<com.maersk.mdm.taskdata.jaxb.Geography.PostalCode.Parent.AlternateCodes.AlternateCode>) Utility.getParentAltCodes(parentRowidObject,operationContext,"POSTALCODE");
		
			PostalCDParentAltCodes.setAlternateCode(PostalCDParentAltCode);
			bdapstlCd.setAlternateCodes(PostalCDParentAltCodes);
		
		/*****************************200293: Add the Alternate Code to the parent end *****************/	
		postalCodeEvent.setParent(bdapstlCd);
		/*****************************200293: Add the Country Element to Postal Code start  *****************/	
		LOG.info("Bind Country to PostalCode");
		Geography.PostalCode.Country cnt = new Geography.PostalCode.Country();
		if (typCdValue!=null) {
			com.maersk.mdm.taskdata.jaxb.Geography.PostalCode.Country.AlternateCodes PSCountryAltCodes = new com.maersk.mdm.taskdata.jaxb.Geography.PostalCode.Country.AlternateCodes();
			List<com.maersk.mdm.taskdata.jaxb.Geography.PostalCode.Country.AlternateCodes.AlternateCode> PSCountryAltCode = new ArrayList<com.maersk.mdm.taskdata.jaxb.Geography.PostalCode.Country.AlternateCodes.AlternateCode>();
			if ("GDA.COUNTRY".equalsIgnoreCase(typeCd)) {				
				cnt.setName(Utility.validate(bddPstlCdParent.get(0).getValue(IDDConstants.MDM_TABLE_DEFAREA_COLUMN_NAME_NAME)).toString());	
			    	PSCountryAltCode=(List<com.maersk.mdm.taskdata.jaxb.Geography.PostalCode.Country.AlternateCodes.AlternateCode>) Utility.getParentAltCodes(parentRowidObject,operationContext,"POSTALCOUNTRY");
					PSCountryAltCodes.setAlternateCode(PSCountryAltCode);
					cnt.setAlternateCodes(PSCountryAltCodes);
					postalCodeEvent.setCountry(cnt);	
			
		}else if ("GDA.STATE/PROV".equalsIgnoreCase(typeCd)) {
			// Set Country name 
			String CountryRowid=Utility.getCountryfromState(parentRowidObject,operationContext);				
			String getCountryNameQuery = Utility.getDatabaseLookupValue("C_GDA_DFND_AREA", CountryRowid, "NAME", "ROWID_OBJECT");
			String CountryName = Utility.getValueFromDB(operationContext, getCountryNameQuery);
			cnt.setName(CountryName);
			
			// Set AltCode
		    	PSCountryAltCode=(List<com.maersk.mdm.taskdata.jaxb.Geography.PostalCode.Country.AlternateCodes.AlternateCode>) Utility.getParentAltCodes(CountryRowid,operationContext,"POSTALCOUNTRY");
				PSCountryAltCodes.setAlternateCode(PSCountryAltCode);
				cnt.setAlternateCodes(PSCountryAltCodes);
				postalCodeEvent.setCountry(cnt);	

		}else {
			// else it is city. Fetch the StateRowid and then CountryRowid or directly countryID.
			         // Check whether city directly connected to the state or to the country.
			         LOG.info("Inside Else Postal Code belings to City");
			         int cntFlag = Utility.IsCityROWIDRelCountry(parentRowidObject, operationContext);
			         String CountryRowid=null;
			         // Set Country name 
			         if (cntFlag > 0) {
			        	 LOG.info("City Directly Connected to Country");
			        	 CountryRowid=Utility.getCountryfromCity(parentRowidObject,operationContext);
			        	 LOG.info("CountryRowid" + CountryRowid);
			         }
			         else {
			        	 LOG.info("City  Connected to State");
						String StateRowid=Utility.getStatefromCity(parentRowidObject,operationContext);		
						CountryRowid=Utility.getCountryfromState(StateRowid,operationContext);	
						LOG.info("CountryRowid" + CountryRowid);
			         }
			            String getCountryNameQuery = Utility.getDatabaseLookupValue("C_GDA_DFND_AREA", CountryRowid, "NAME", "ROWID_OBJECT");
						String CountryName = Utility.getValueFromDB(operationContext, getCountryNameQuery);
						cnt.setName(CountryName);
						// Set AltCode
					    	PSCountryAltCode=(List<com.maersk.mdm.taskdata.jaxb.Geography.PostalCode.Country.AlternateCodes.AlternateCode>) Utility.getParentAltCodes(CountryRowid,operationContext,"POSTALCOUNTRY");
							PSCountryAltCodes.setAlternateCode(PSCountryAltCode);
							cnt.setAlternateCodes(PSCountryAltCodes);
							postalCodeEvent.setCountry(cnt);	

		}
			/*****************************200293: Add the Country Element to Postal Code end  *****************/	
		}
		}
		
		LOG.info("Bind PostalCode BDA");
		BDA postalCdBDA= new BDA();
		List<BDAType> postalCdBDAValues= new ArrayList<BDAType>();
		List<BDDObject> bddpostalCdBDA=bddObject.getChildren("BDA");
		
		for(BDDObject bda : bddpostalCdBDA) {
			if(!bda.isRemoved()) {
			BDAType bdaPstlCd= new BDAType();
			com.maersk.mdm.taskdata.jaxb.Geography.PostalCode.BDA.BDAType.AlternateCodes postalCodeBdaAltCodes=new com.maersk.mdm.taskdata.jaxb.Geography.PostalCode.BDA.BDAType.AlternateCodes();
			List<com.maersk.mdm.taskdata.jaxb.Geography.PostalCode.BDA.BDAType.AlternateCodes.AlternateCode> postalCodebdaAltCode=postalCodeBdaAltCodes.getAlternateCode();
			
			bdaPstlCd.setName(Utility.validate(bda.getValue(IDDConstants.MDM_TABLE_DEFAREA_COLUMN_NAME_NAME)).toString());
			String typeTypeRowid = Utility.validate(bda.getValue(IDDConstants.MDM_TABLE_DEFAREA_COLUMN_NAME_TYPE_CD)).toString();
			LOG.info("typeTypeRowid is ::" + typeTypeRowid);
			String getTypeCdQuery=Utility.getDatabaseLookupValue("C_TYP_TYPE", typeTypeRowid, "NAME", "CODE");
			String bdaValue = Utility.getValueFromDB(operationContext, getTypeCdQuery);
			bdaPstlCd.setType(bdaValue);
			
			String parentRowidObject=Utility.validate(bda.getValue(IDDConstants.MDM_TABLE_DEFAREA_COLUMN_NAME_ROWID_OBJECT)).toString();
			LOG.info("parentRowid:" + parentRowidObject);
			postalCodebdaAltCode= (List<com.maersk.mdm.taskdata.jaxb.Geography.PostalCode.BDA.BDAType.AlternateCodes.AlternateCode>) Utility.getBDAAltCodes(parentRowidObject,operationContext,"POSTALCODE");			
			postalCodeBdaAltCodes.setAlternateCode(postalCodebdaAltCode);
			bdaPstlCd.setAlternateCodes(postalCodeBdaAltCodes);
			postalCdBDAValues.add(bdaPstlCd);
			
			}
			}
		postalCdBDA.setBdaType(postalCdBDAValues);
		postalCodeEvent.setBDA(postalCdBDA);
		
		geographyEvent.setPostalCode(postalCodeEvent);
		return geographyEvent;
	}

}
