package com.maersk.mdm.taskdata.data;

import java.sql.Connection;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import com.maersk.mdm.taskdata.jaxb.Geography;
import com.maersk.mdm.taskdata.jaxb.Geography.Continent;
import com.maersk.mdm.taskdata.jaxb.Geography.Continent.AlternateCodes;
import com.maersk.mdm.taskdata.jaxb.Geography.Continent.AlternateCodes.AlternateCode;
import com.maersk.mdm.taskdata.jaxb.Geography.Continent.AlternateNames;
import com.maersk.mdm.taskdata.jaxb.Geography.Continent.AlternateNames.AlternateName;
import com.maersk.mdm.taskdata.userexit.IDDConstants;
import com.maersk.mdm.taskdata.util.Utility;
import com.siperian.bdd.userexits.datamodel.BDDObject;
import com.siperian.bdd.userexits.operations.OperationContext;
import com.siperian.bdd.userexits.utils.BDDMessagesLocalizationGate;

public class BindContinentData {
	private static final Logger LOG = Logger.getLogger(BindContinentData.class);
	private static BDDObject bddObject;
	private static Continent continent;
	private OperationContext operationContext;
	private static String ORSId; 
	Connection connection = null;
	private BDDMessagesLocalizationGate bddMessagesLocalizationGate;
	private static Geography geographyEvent;
	 
	
	public BindContinentData() {
		super();
	}


	public BindContinentData(BDDObject bddObject, String mdmORSid, OperationContext operationContext,
			BDDMessagesLocalizationGate localizationGate, Geography geoEvent) {
		this.bddObject=bddObject;
		this.operationContext=operationContext;
		this.bddMessagesLocalizationGate=localizationGate;
		this.ORSId=mdmORSid;
		this.geographyEvent=geoEvent;
		this.continent=new Continent();

	}


	public Geography bindContinentData() {
		LOG.info("Inside method bindContinentData");
		geographyEvent=bindContinentAllDetails(continent);
		return geographyEvent;
		
	}


	private Geography bindContinentAllDetails(Continent continentEvent) {
		LOG.info("Inside method bindContinentAllDetails");
		LOG.info("Bind Continent General Details");
		continentEvent.setName(Utility.validate(bddObject.getValue(IDDConstants.MDM_TABLE_DEFAREA_COLUMN_NAME_NAME)).toString());
		String status=Utility.validate(bddObject.getValue(IDDConstants.MDM_TABLE_DEFAREA_COLUMN_NAME_STATUS)).toString();
		if(status!=null) {
				if(status.equalsIgnoreCase("Y"))
					continentEvent.setStatus("Active");
				else if(status.equalsIgnoreCase("N"))
					continentEvent.setStatus("Inactive");
		}
		//continentEvent.setValidFrom(Utility.validate(bddObject.getValue(IDDConstants.MDM_TABLE_DEFAREA_COLUMN_NAME_VALID_FROM)).toString());
		//continentEvent.setValidTo(Utility.validate(bddObject.getValue(IDDConstants.MDM_TABLE_DEFAREA_COLUMN_NAME_VALID_TO)).toString());
		
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
		continentEvent.setValidFrom(validFromdate.toString());
		continentEvent.setValidTo(validTodate.toString());

		
		continentEvent.setDescription(Utility.validate(bddObject.getValue(IDDConstants.MDM_TABLE_DEFAREA_COLUMN_NAME_DESCRIPTION)).toString());
		List<BDDObject> WorkAroundReason=bddObject.getChildren("WorkAroundReason");
		if(WorkAroundReason!=null && WorkAroundReason.size()>0) {
			String workaroundReason=Utility.validate(WorkAroundReason.get(0).getValue(IDDConstants.MDM_TABLE_C_TMP_WRKRND_RSN_COLUMN_NAME_WR)).toString();
			if(workaroundReason!=null) {
					String workaroundReasonQuery=Utility.getDatabaseLookupValue("C_TYP_TYPE", workaroundReason, "NAME", "ROWID_OBJECT");	
						String workaroundReasonValue=Utility.getValueFromDB(operationContext, workaroundReasonQuery);
							continentEvent.setWorkaroundReason(workaroundReasonValue);
			}
	}
		if(WorkAroundReason==null || WorkAroundReason.isEmpty()) {
			continentEvent.setWorkaroundReason("");
		}
		LOG.info("Bind Continent AlternateName");
		AlternateNames continentAlternateName= new AlternateNames();
		List<AlternateName> continentAlternateNameValues= new ArrayList<AlternateName>();
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
			continentAlternateNameValues.add(altName);
		}
		}
		continentAlternateName.setAlternateName(continentAlternateNameValues);
		continentEvent.setAlternateNames(continentAlternateName);
		
		LOG.info("Bind Continent AlternateCode");
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
		continentEvent.setAlternateCodes(continentAlternateCode);
		
		geographyEvent.setContinent(continentEvent);
		return geographyEvent;
	}

}
