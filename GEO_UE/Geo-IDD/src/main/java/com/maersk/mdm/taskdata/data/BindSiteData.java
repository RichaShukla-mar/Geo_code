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

import com.maersk.mdm.taskdata.jaxb.Geography;
import com.maersk.mdm.taskdata.jaxb.Geography.Site;
import com.maersk.mdm.taskdata.jaxb.Geography.Site.AlternateCodes;
import com.maersk.mdm.taskdata.jaxb.Geography.Site.AlternateCodes.AlternateCode;
import com.maersk.mdm.taskdata.jaxb.Geography.Site.AlternateNames;
import com.maersk.mdm.taskdata.jaxb.Geography.Site.AlternateNames.AlternateName;
import com.maersk.mdm.taskdata.jaxb.Geography.Site.BDA;
import com.maersk.mdm.taskdata.jaxb.Geography.Site.BDA.BDAType;
import com.maersk.mdm.taskdata.jaxb.Geography.Site.GeoFence;
import com.maersk.mdm.taskdata.jaxb.Geography.Site.GeoFence.GeoFenceTypes;
import com.maersk.mdm.taskdata.jaxb.Geography.Site.Parent;
import com.maersk.mdm.taskdata.userexit.IDDConstants;
import com.maersk.mdm.taskdata.util.Utility;
import com.siperian.bdd.userexits.datamodel.BDDObject;
import com.siperian.bdd.userexits.operations.OperationContext;
import com.siperian.bdd.userexits.utils.BDDMessagesLocalizationGate;

/**
 * @author AJA350
 *
 */
public class BindSiteData {

	private static final Logger LOG = Logger.getLogger(BindGeographyData.class);
	private static BDDObject bddObject;
	private OperationContext operationContext;
	private static String ORSId; 
	Connection connection = null;
	private BDDMessagesLocalizationGate bddMessagesLocalizationGate;
	private static Geography geographyEvent;
	private static Site siteEvent;

	public BindSiteData() {
		super();
	}

	public BindSiteData(BDDObject bddObject, String oRSId, OperationContext operationContext,
			BDDMessagesLocalizationGate bddMessagesLocalizationGate, Geography geographyEvent) {
		this.bddObject=bddObject;
		this.operationContext=operationContext;
		this.bddMessagesLocalizationGate=bddMessagesLocalizationGate;
		this.ORSId=oRSId;
		this.geographyEvent=geographyEvent;
		this.siteEvent=new Site();

	}

	public Geography bindSiteData() throws SQLException {
		LOG.info("Inside method bindSiteData");
		geographyEvent=bindSiteAllDetails(siteEvent);
		return geographyEvent;
	}

	private Geography bindSiteAllDetails(Site siteEvent) throws SQLException {
		LOG.info("Inside method bindSiteAllDetails");
		siteEvent.setName(Utility.validate(bddObject.getValue(IDDConstants.MDM_TABLE_DEFAREA_COLUMN_NAME_NAME)).toString());
		String status=Utility.validate(bddObject.getValue(IDDConstants.MDM_TABLE_DEFAREA_COLUMN_NAME_STATUS)).toString();
		if(status!=null) {
				if(status.equalsIgnoreCase("Y"))
					siteEvent.setStatus("Active");
				else if(status.equalsIgnoreCase("N"))
					siteEvent.setStatus("Inactive");
		}
		//siteEvent.setValidFrom(Utility.validate(bddObject.getValue(IDDConstants.MDM_TABLE_DEFAREA_COLUMN_NAME_VALID_FROM)).toString());
		//siteEvent.setValidTo(Utility.validate(bddObject.getValue(IDDConstants.MDM_TABLE_DEFAREA_COLUMN_NAME_VALID_TO)).toString());
		
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
		siteEvent.setValidFrom(validFromdate.toString());
		siteEvent.setValidTo(validTodate.toString());
		
		
		siteEvent.setLongitude(Utility.validate(bddObject.getValue(IDDConstants.MDM_TABLE_DEFAREA_COLUMN_NAME_LONGITUDE)).toString());
		siteEvent.setLatitude(Utility.validate(bddObject.getValue(IDDConstants.MDM_TABLE_DEFAREA_COLUMN_NAME_LATITUDE)).toString());
		siteEvent.setDescription(Utility.validate(bddObject.getValue(IDDConstants.MDM_TABLE_DEFAREA_COLUMN_NAME_DESCRIPTION)).toString());
		List<BDDObject> WorkAroundReason=bddObject.getChildren("WorkAroundReason");
		if(WorkAroundReason!=null && WorkAroundReason.size()>0) {
			String workaroundReason=Utility.validate(WorkAroundReason.get(0).getValue(IDDConstants.MDM_TABLE_C_TMP_WRKRND_RSN_COLUMN_NAME_WR)).toString();
				if(workaroundReason!=null) {
						String workaroundReasonQuery=Utility.getDatabaseLookupValue("C_TYP_TYPE", workaroundReason, "NAME", "ROWID_OBJECT");	
							String workaroundReasonValue=Utility.getValueFromDB(operationContext, workaroundReasonQuery);
							siteEvent.setWorkaroundReason(workaroundReasonValue);
				}
		}	
		if(WorkAroundReason==null || WorkAroundReason.isEmpty()) {
			siteEvent.setWorkaroundReason("");
		}
		List<BDDObject> siteSiteType=bddObject.getChildren("SiteType");
		if(siteSiteType!=null && siteSiteType.size()>0) {
		String typeTypeRowid = Utility.validate(siteSiteType.get(0).getValue(IDDConstants.MDM_TABLE_SITE_COLUMN_NAME_SITE_TYPE)).toString();
		if(typeTypeRowid!=null) {
			LOG.info("typeTypeRowid is ::" + typeTypeRowid);
		String getAltCodeQuery = Utility.getDatabaseLookupValue("C_TYP_TYPE", typeTypeRowid, "NAME", "ROWID_OBJECT");
		String altCodeValue = Utility.getValueFromDB(operationContext, getAltCodeQuery);
		siteEvent.setSiteType(altCodeValue);
		}
		String gpsFlag=Utility.validate(siteSiteType.get(0).getValue(IDDConstants.MDM_TABLE_SITE_COLUMN_NAME_GPS_FLAG)).toString();
		if(gpsFlag!=null) {
			if(gpsFlag.equalsIgnoreCase("Y"))
				siteEvent.setGPSFlag("Yes");
			else if(gpsFlag.equalsIgnoreCase("N"))
				siteEvent.setGPSFlag("No");
		}
		String gsmFlag=Utility.validate(siteSiteType.get(0).getValue(IDDConstants.MDM_TABLE_SITE_COLUMN_NAME_GSM_FLAG)).toString();
		if(gsmFlag!=null) {
			if(gsmFlag.equalsIgnoreCase("Y"))
				siteEvent.setGSMFlag("Yes");
			else if(gsmFlag.equalsIgnoreCase("N"))
				siteEvent.setGSMFlag("No");
			}			
		}
		List<BDDObject> siteLocation=bddObject.getChildren("SitePhysicalLocation");
		if(siteLocation!=null && siteLocation.size()>0) {
		siteEvent.setStreetNumber(Utility.validate(siteLocation.get(0).getValue(IDDConstants.MDM_TABLE_TMP_SITE_PHYS_LCN_COLUMN_NAME_STREETNO)).toString());
		siteEvent.setAddressLine1(Utility.validate(siteLocation.get(0).getValue(IDDConstants.MDM_TABLE_SITE_COLUMN_NAME_ADDR_LN1)).toString());
		siteEvent.setAddressLine2(Utility.validate(siteLocation.get(0).getValue(IDDConstants.MDM_TABLE_SITE_COLUMN_NAME_ADDR_LN2)).toString());
		siteEvent.setAddressLine3(Utility.validate(siteLocation.get(0).getValue(IDDConstants.MDM_TABLE_SITE_COLUMN_NAME_ADDR_LN3)).toString());
		siteEvent.setPostalCode(Utility.validate(siteLocation.get(0).getValue(IDDConstants.MDM_TABLE_SITE_COLUMN_NAME_POSTAL_CODE)).toString());
		}
		LOG.info("Bind Site AlternateName");
		AlternateNames siteAlternateName= new AlternateNames();
		List<AlternateName> siteAlternateNameValues= new ArrayList<AlternateName>();
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
			siteAlternateNameValues.add(altName);
		}
		}
		siteAlternateName.setAlternateName(siteAlternateNameValues);
		siteEvent.setAlternateNames(siteAlternateName);
		
		LOG.info("Bind Site AlternateCode");
		AlternateCodes siteAlternateCode= new AlternateCodes();
		List<AlternateCode> siteAlternateCodeValues= new ArrayList<AlternateCode>();
		List<BDDObject> bddObjectAltCode=bddObject.getChildren("AlternateCode");
		for(BDDObject alternateCode : bddObjectAltCode) {
			if(!alternateCode.isRemoved()) {
				AlternateCode altCode= new AlternateCode();
			String typerowid = Utility.validate(alternateCode.getValue("C_ALT_CODE|TYP_TYPE_ROWID")).toString();
			LOG.info("typeTypeRowid is ::" + typerowid);
			String getAltCdQuery = Utility.getDatabaseLookupValue("C_TYP_TYPE", typerowid, "NAME", "ROWID_OBJECT");
			String altCdValue = Utility.getValueFromDB(operationContext, getAltCdQuery);
			altCode.setCodeType(altCdValue);
			altCode.setCode(Utility.validate(alternateCode.getValue(IDDConstants.MDM_TABLE_ALTCODE_COLUMN_NAME_VALUE)).toString());
			siteAlternateCodeValues.add(altCode);
		}
		}
		siteAlternateCode.setAlternateCode(siteAlternateCodeValues);
		siteEvent.setAlternateCodes(siteAlternateCode);
		
		LOG.info("Bind Site GeoFence");
		GeoFence siteFence=new GeoFence();
		List<GeoFenceTypes> siteFenceTypes=new ArrayList<GeoFenceTypes>();
		List<BDDObject> bddsiteGeoFence= bddObject.getChildren("GeoFence");
		for(BDDObject geoFence: bddsiteGeoFence) {
			if(!geoFence.isRemoved()) {
				GeoFenceTypes geoFenceData= new GeoFenceTypes();
			
			geoFenceData.setName(Utility.validate(geoFence.getValue(IDDConstants.MDM_TABLE_DFND_PMTR_COLUMN_NAME_NAME)).toString());
			geoFenceData.setGeoFenceType(Utility.validate(geoFence.getValue(IDDConstants.MDM_TABLE_DFND_PMTR_COLUMN_NAME_NAME)).toString());
			siteFenceTypes.add(geoFenceData);
		}
		}
		siteFence.setGeoFenceTypes(siteFenceTypes);
		siteEvent.setGeoFence(siteFence);
		
		LOG.info("Bind Site Parent");
		List<BDDObject> bddSiteParent=bddObject.getChildren("Hierarchy");
		if(bddSiteParent!=null && bddSiteParent.size()>0) {
			Parent bdaCountry= new Parent();
		bdaCountry.setName(Utility.validate(bddSiteParent.get(0).getValue(IDDConstants.MDM_TABLE_DEFAREA_COLUMN_NAME_NAME)).toString());
		String typeCd=Utility.validate(bddSiteParent.get(0).getValue(IDDConstants.MDM_TABLE_DEFAREA_COLUMN_NAME_TYPE_CD)).toString();
		String getTypeCdQuery=Utility.getDatabaseLookupValue("C_TYP_TYPE", typeCd, "NAME", "CODE");
		String typCdValue=Utility.getValueFromDB(operationContext, getTypeCdQuery);
		bdaCountry.setType(typCdValue);
		/******************    200293: Add the Alternate Code to the parent start ***********************/
		com.maersk.mdm.taskdata.jaxb.Geography.Site.Parent.AlternateCodes siteParentAltCodes = new com.maersk.mdm.taskdata.jaxb.Geography.Site.Parent.AlternateCodes();
		List<com.maersk.mdm.taskdata.jaxb.Geography.Site.Parent.AlternateCodes.AlternateCode> siteParentAltCode= siteParentAltCodes.getAlternateCode();
		
		String parentRowidObject=Utility.validate(bddSiteParent.get(0).getValue(IDDConstants.MDM_TABLE_DEFAREA_COLUMN_NAME_ROWID_OBJECT)).toString();
		LOG.info("parentRowid:" + parentRowidObject);
		siteParentAltCode=(List<com.maersk.mdm.taskdata.jaxb.Geography.Site.Parent.AlternateCodes.AlternateCode>) Utility.getParentAltCodes(parentRowidObject,operationContext,"SITE");
				
			siteParentAltCodes.setAlternateCode(siteParentAltCode);
			bdaCountry.setAlternateCodes(siteParentAltCodes);		
		/*****************************200293: Add the Alternate Code to the parent end *****************/	
		
		siteEvent.setParent(bdaCountry);
		
		
		/*****************************200293: Add the Country to the Site Start *****************/	
		
		LOG.info("Bind Country to Site");
		Geography.Site.Country cnt = new Geography.Site.Country();
		if (typCdValue!=null) {
			com.maersk.mdm.taskdata.jaxb.Geography.Site.Country.AlternateCodes siteCountryAltCodes = new com.maersk.mdm.taskdata.jaxb.Geography.Site.Country.AlternateCodes();
			List<com.maersk.mdm.taskdata.jaxb.Geography.Site.Country.AlternateCodes.AlternateCode> siteCountryAltCode = siteCountryAltCodes.getAlternateCode();
			int cntFlag = Utility.IsCityROWIDRelCountry(parentRowidObject, operationContext);
	         String CountryRowid=null;	         
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
			// Set Country name 				
					String getCountryNameQuery = Utility.getDatabaseLookupValue("C_GDA_DFND_AREA", CountryRowid, "NAME", "ROWID_OBJECT");
					String CountryName = Utility.getValueFromDB(operationContext, getCountryNameQuery);
					cnt.setName(CountryName);
					// Set AltCode
					    siteCountryAltCode=(List<com.maersk.mdm.taskdata.jaxb.Geography.Site.Country.AlternateCodes.AlternateCode>) Utility.getParentAltCodes(CountryRowid,operationContext,"SITECOUNTRY");
						siteCountryAltCodes.setAlternateCode(siteCountryAltCode);				
						cnt.setAlternateCodes(siteCountryAltCodes);
						siteEvent.setCountry(cnt);	

		
		
		/*****************************200293: Add the Country to the Site end *****************/	
		}
		}
		
		LOG.info("Bind Site BDA");
		BDA siteBDA= new BDA();
		List<BDAType> siteBDAValues= new ArrayList<BDAType>();
		List<BDDObject> bddSiteBDA=bddObject.getChildren("BDA");
		
		for(BDDObject bda : bddSiteBDA) {
			if(!bda.isRemoved()) {
				BDAType bdaSite= new BDAType();
			com.maersk.mdm.taskdata.jaxb.Geography.Site.BDA.BDAType.AlternateCodes siteBdaAltCodes=new com.maersk.mdm.taskdata.jaxb.Geography.Site.BDA.BDAType.AlternateCodes();
			List<com.maersk.mdm.taskdata.jaxb.Geography.Site.BDA.BDAType.AlternateCodes.AlternateCode> siteBdaAltCode=siteBdaAltCodes.getAlternateCode();
			
			bdaSite.setName(Utility.validate(bda.getValue(IDDConstants.MDM_TABLE_DEFAREA_COLUMN_NAME_NAME)).toString());
			String typeRowid = Utility.validate(bda.getValue(IDDConstants.MDM_TABLE_DEFAREA_COLUMN_NAME_TYPE_CD)).toString();
			LOG.info("typeTypeRowid is ::" + typeRowid);
			String getTypeCdQuery=Utility.getDatabaseLookupValue("C_TYP_TYPE", typeRowid, "NAME", "CODE");
			String bdaValue = Utility.getValueFromDB(operationContext, getTypeCdQuery);
			bdaSite.setType(bdaValue);
			
			String parentRowidObject=Utility.validate(bda.getValue(IDDConstants.MDM_TABLE_DEFAREA_COLUMN_NAME_ROWID_OBJECT)).toString();
			LOG.info("parentRowid:" + parentRowidObject);
			siteBdaAltCode= (List<com.maersk.mdm.taskdata.jaxb.Geography.Site.BDA.BDAType.AlternateCodes.AlternateCode>) Utility.getBDAAltCodes(parentRowidObject,operationContext,"SITE");			
			siteBdaAltCodes.setAlternateCode(siteBdaAltCode);
			bdaSite.setAlternateCodes(siteBdaAltCodes);
			siteBDAValues.add(bdaSite);
			}
			}
		siteBDA.setBdaType(siteBDAValues);
		siteEvent.setBDA(siteBDA);
		
		
		geographyEvent.setSite(siteEvent);
		return geographyEvent;
	}

}
