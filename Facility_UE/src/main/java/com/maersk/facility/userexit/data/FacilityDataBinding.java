package com.maersk.facility.userexit.data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.naming.InitialContext;
import javax.sql.DataSource;

import org.apache.log4j.Logger;

import com.maersk.facility.userexit.bean.Facility;
import com.maersk.facility.userexit.bean.FacilityAddress;
import com.maersk.facility.userexit.bean.FacilityAltCode;
import com.maersk.facility.userexit.bean.FacilityContactDetail;
import com.maersk.facility.userexit.bean.FacilityDetail;
import com.maersk.facility.userexit.bean.FacilityOpeningHour;
import com.maersk.facility.userexit.bean.FacilityParent;
import com.maersk.facility.userexit.bean.FacilityService;
import com.maersk.facility.userexit.bean.FacilityTransportDetail;
import com.maersk.facility.userexit.bean.FacilityType;
import com.maersk.facility.userexit.bean.FacilityWTCSMatch;
import com.maersk.facility.userexit.bean.SMDSFacilityEvent;
import com.maersk.facility.userexit.utils.Utils;
import com.siperian.bdd.userexits.datamodel.BDDObject;
import com.siperian.bdd.userexits.operations.AbstractBaseOperationPlugin;
import com.siperian.bdd.userexits.operations.OperationContext;
import com.siperian.bdd.userexits.operations.OperationExecutionError;
import com.siperian.bdd.userexits.operations.OperationResult;
import com.siperian.bdd.userexits.operations.OperationType;
import com.siperian.bdd.userexits.utils.BDDMessagesLocalizationGate;

/**
 * @author AJA350
 *
 */
public class FacilityDataBinding  extends AbstractBaseOperationPlugin{

	private static BDDObject bddObject;
	private OperationContext operationContext;
	private static String oRSID; 
	Connection connection = null;
	private static final Logger LOG = Logger.getLogger(FacilityDataBinding.class);
	private BDDMessagesLocalizationGate bddMessagesLocalizationGate;
	private static SMDSFacilityEvent facilityEvent;
	private static String geoIDForPublish="";
	private static String city="";
	private static String CITY_ROWID ="";
	public FacilityDataBinding() {
		super();
	}

	public FacilityDataBinding(BDDObject savingObject, OperationContext operationContext,
			BDDMessagesLocalizationGate bddMessagesLocalizationGate,String mdmId,SMDSFacilityEvent factEvent, String geoIDForPublish) {
		super();
		bddObject=savingObject;
		this.operationContext = operationContext;
		this.bddMessagesLocalizationGate = bddMessagesLocalizationGate;
		oRSID = mdmId;
		facilityEvent=factEvent;
		this.geoIDForPublish=geoIDForPublish;
	}

	public SMDSFacilityEvent generateFacilityData() {
		
		bindFacilityData(facilityEvent);
		bindFacilityAddr(facilityEvent);
		bindFacilityDetail(facilityEvent);
		bindFacilityAltCode(facilityEvent);
		bindFacilityParent(facilityEvent);
		bindFacilityOpnHour(facilityEvent);
		bindFacilityTransDetail(facilityEvent);
		bindFacilityService(facilityEvent);
		bindFacilityContDetail(facilityEvent);
		return facilityEvent;
	}

	private OperationResult bindFacilityData(SMDSFacilityEvent facilityEvent) {
		// TODO Auto-generated method stub
		try {
			LOG.info("Inside try for bindOprFacilityData");
			Facility oprFacility = new Facility();
			LOG.info("Facility Object created:: " +oprFacility);
			LOG.info("Just Checking" + bddObject.isCreated());
			oprFacility.setFacilityEventType(bddObject.isCreated() ? "Create" : "Update");
			oprFacility.setFacilityType("Operational Facility");
			oprFacility.setFacilityExtExposed(Utils.validate(bddObject.getValue("C_FCT_FACILITY|EXT_EXPOSED")).toString());
			oprFacility.setFacilityExtOwned(Utils.validate(bddObject.getValue("C_FCT_FACILITY|EXT_OWNED")).toString());
			oprFacility.setFacilityName(Utils.validate(bddObject.getValue("C_FCT_FACILITY|FACILITY_NAME")).toString());
			String statusCd = Utils.validate(bddObject.getValue("C_FCT_FACILITY|STATUS_CD")).toString();
			LOG.info("statusCd is :: " + statusCd);
			if (statusCd.equalsIgnoreCase("A")) {
				oprFacility.setFacilityStatus("Active");
			} else if (statusCd.equalsIgnoreCase("I")) {
				oprFacility.setFacilityStatus("InActive");
			} else if (statusCd.equalsIgnoreCase("P")) {
				oprFacility.setFacilityStatus("Pending");
			}
			oprFacility.setFacilityUrl(Utils.validate(bddObject.getValue("C_FCT_FACILITY|URL")).toString());
			LOG.info(oprFacility.toString());
			LOG.info("OprFacility is ::" +oprFacility);
			

			facilityEvent.setFacility(oprFacility);
			LOG.info("After setting Facility Object in SMDSFacilityEvent");
		} catch (Exception ex) {
			LOG.info("Exception in binding oprFacility" +ex.getStackTrace());
			ex.printStackTrace();
			//return new OperationResult(new OperationExecutionError("SIP-60001", bddMessagesLocalizationGate));
			return new OperationResult(new OperationExecutionError("SIP-60001",
			          new String[] {"Error while binding bindFacilityData"},
			          getLocalizationGate()));
		}
		return OperationResult.OK;

	}

	private OperationResult bindFacilityDetail(SMDSFacilityEvent facilityEvent) {
		// TODO Auto-generated method stub
		try {
			LOG.info("Inside try for bindFacilityDetail");
			List<FacilityDetail> facilityDetailList = new ArrayList<>();
			List<BDDObject> Facility_Details = bddObject.getChildren("Facility_Details");
			for (BDDObject factDetails : Facility_Details) {
				LOG.info("Is Facility Details Removed ::" +factDetails.isRemoved());
				if(!factDetails.isRemoved()) {
				LOG.info("Inside for loop");
				FacilityDetail facilityDetails = new FacilityDetail();

				facilityDetails.setWeightLimitCraneKg(
						Utils.validate(factDetails.getValue("C_FCT_OPS|WEIGHT_LMT_CRANE")).toString());
				facilityDetails.setWeightLimitYardKg(
						Utils.validate(factDetails.getValue("C_FCT_OPS|WEIGHT_LMT_YARD")).toString());
				facilityDetails.setVesselAgent(
						Utils.validate(factDetails.getValue("C_FCT_OPS|VESSEL_AGENT")).toString());
				facilityDetails.setGPSFlag(
						Utils.validate(factDetails.getValue("C_FCT_OPS|GPS_FLAG")).toString());
				facilityDetails.setGSMFlag(
						Utils.validate(factDetails.getValue("C_FCT_OPS|GSM_FLAG")).toString());
				facilityDetails.setOceanFreightPricing(
						Utils.validate(factDetails.getValue("C_FCT_OPS|OCE_FRGHT_PR")).toString());
				facilityDetailList.add(facilityDetails);
				
				LOG.info("Inside try for bindFacilityType");
				List<FacilityType> facilityTypeList = new ArrayList<>();
				List<BDDObject> Facility_types = factDetails.getChildren("Facility_Type");
				for (BDDObject factType : Facility_types) {
					LOG.info("Is Facility Type Removed::"+factType.isRemoved());
					if(!factType.isRemoved()) {
					LOG.info("Inside loop for bindFacilityType");
					FacilityType facilityType = new FacilityType();
					
					LOG.info("CODE is ::" + factType.getValue("C_TYP_TYPE|NAME").toString());
					facilityType.setCode(Utils.validate(factType.getValue("C_TYP_TYPE|CODE")).toString());
					
					LOG.info("TYP_MSTR_TYPE_CD is ::" + factType.getValue("C_TYP_TYPE|TYP_MSTR_TYPE_CD").toString());
					facilityType.setMasterType(
							Utils.validate(factType.getValue("C_TYP_TYPE|TYP_MSTR_TYPE_CD")).toString());
					
					LOG.info("NAME is ::" + factType.getValue("C_TYP_TYPE|NAME").toString());
					facilityType.setName(Utils.validate(factType.getValue("C_TYP_TYPE|NAME")).toString());
					
					
					String validThroughDateValue = Utils.getRelDateFromTable(factType, "C_FCT_OPS_TYP_REL");
					LOG.info("validThroughDateValue for FacilityDetail is :: " + validThroughDateValue);
					if(factType.isCreated()) {
						facilityType.setValidThroughDate(Utils.changeInputCreateDate(Utils.validate(validThroughDateValue).toString()));
					}else{
						facilityType.setValidThroughDate(Utils.validate(validThroughDateValue).toString());
					}
					
					facilityTypeList.add(facilityType);
						}
					}
				
				LOG.info("Setting FacilityDetails to FacilityTypeList");
				facilityDetails.setFacilityTypes(facilityTypeList);
				}
			}
			LOG.info("Setting Facility facilityDetailList  to facilityDetails for FacilityEvent");
			facilityEvent.setFacilityDetail(facilityDetailList);
		} catch (Exception ex) {
			LOG.info("Exception in binding bindFacilityDetail " + ex.getMessage());
			ex.printStackTrace();
			//return new OperationResult(new OperationExecutionError("SIP-60002", bddMessagesLocalizationGate));
			return new OperationResult(new OperationExecutionError("SIP-60002",
			          new String[] {"Error while binding bindFacilityDetail"},
			          getLocalizationGate()));
		}
		return OperationResult.OK;

	}

	private OperationResult bindFacilityAltCode(SMDSFacilityEvent facilityEvent) {
		try {
			boolean isGeoIDAdded=true;
			LOG.info("Inside try for bindFacilityAltCode");
			List<FacilityAltCode> factAltCodeList = new ArrayList<>();
			List<BDDObject> facilityAltCodes = bddObject.getChildren("Alternate_Codes");
			
			for (BDDObject factAltCodes : facilityAltCodes) {
				if(!factAltCodes.isRemoved()) {
					FacilityAltCode factAltCode = new FacilityAltCode();
					LOG.info("Is AltCode Removed::"+factAltCodes.isRemoved());
				factAltCode.setAltCode(
						Utils.validate(factAltCodes.getValue("C_FCT_ALT_CODES|CODE")).toString());
				String typeTypeRowid = Utils.validate(factAltCodes.getValue("C_FCT_ALT_CODES|TYP_TYPE_ROWID"))
						.toString();
				LOG.info("typeTypeRowid is ::" + typeTypeRowid);
				String getAltName = Utils.getRelationShipDate("C_TYP_TYPE", typeTypeRowid, "NAME", "ROWID_OBJECT");
				String AltName = Utils.getValueFromDB(operationContext, getAltName);
				factAltCode.setTypeCode(AltName);
				factAltCodeList.add(factAltCode);
				}						
			}
			if(bddObject.isCreated()) {
				FacilityAltCode factAltCode = new FacilityAltCode();
				LOG.info("Inside to set GeoId For Create Case :: " +geoIDForPublish);
				factAltCode.setTypeCode("GEOID");
				factAltCode.setAltCode(geoIDForPublish);
				factAltCodeList.add(factAltCode);
				}


			facilityEvent.setFacilityAltCode(factAltCodeList);
		} catch (Exception ex) {
			LOG.info("Exception in binding bindFacilityAltCode" + ex.getMessage());
			ex.printStackTrace();
			//return new OperationResult(new OperationExecutionError("SIP-60003", bddMessagesLocalizationGate));
			return new OperationResult(new OperationExecutionError("SIP-60003",
			          new String[] {"Error while binding bindFacilityAltCode"},
			          getLocalizationGate()));
		}
		return OperationResult.OK;
	}

	private OperationResult bindFacilityOpnHour(SMDSFacilityEvent facilityEvent) {
		// TODO Auto-generated method stub

		try {
			LOG.info("Inside try for bindFacilityOpnHour");
			List<FacilityOpeningHour> factOpeningHoursList = new ArrayList<>();
			List<BDDObject> opening_hours = bddObject.getChildren("Opening_Hours");

			for (BDDObject factOpnHours : opening_hours) {
				LOG.info("IS Facility Opening Hour Removed::"+factOpnHours.isRemoved());
				if(!factOpnHours.isRemoved()) {
				FacilityOpeningHour factOpeningHours = new FacilityOpeningHour();
				factOpeningHours.setCloseTimeMinutes(
						Utils.validate(factOpnHours.getValue("C_FCT_OPNH|CLOSE_TIME_MINS")).toString());
				factOpeningHours
						.setDay(Utils.validate(factOpnHours.getValue("C_FCT_OPNH|DAY")).toString());
				factOpeningHours.setOpenTimeHours(
						Utils.validate(factOpnHours.getValue("C_FCT_OPNH|OPEN_TIME_HRS")).toString());
				factOpeningHours.setOpenTimeMinutes(
						Utils.validate(factOpnHours.getValue("C_FCT_OPNH|OPEN_TIME_MINS")).toString());
				factOpeningHours.setCloseTimeHours(
						Utils.validate(factOpnHours.getValue("C_FCT_OPNH|CLOSE_TIME_HRS")).toString());
				
				factOpeningHoursList.add(factOpeningHours);
			}
			}
			facilityEvent.setFacilityOpeningHour(factOpeningHoursList);
		} catch (Exception ex) {
			LOG.info("Exception in binding bindFacilityOpnHour" + ex.getMessage());
			ex.printStackTrace();
			//return new OperationResult(new OperationExecutionError("SIP-60004", bddMessagesLocalizationGate));
			return new OperationResult(new OperationExecutionError("SIP-60004",
			          new String[] {"Error while binding bindFacilityOpnHour"},
			          getLocalizationGate()));
		}
		return OperationResult.OK;
	}

	private OperationResult bindFacilityTransDetail(SMDSFacilityEvent facilityEvent) {
		// TODO Auto-generated method stub
		try {
			LOG.info("Inside try for bindFacilityTransDetail");
			List<FacilityTransportDetail> factTransportDetailsList = new ArrayList<>();

			List<BDDObject> factTrans_details = bddObject.getChildren("Transport_Details");

			for (BDDObject factTrDtls : factTrans_details) {
				LOG.info("Is Facility Transport Details Removed::"+factTrDtls.isRemoved());
				if(!factTrDtls.isRemoved()) {
				FacilityTransportDetail factTransportDetails = new FacilityTransportDetail();
				factTransportDetails.setTransportMode(
						Utils.validate(factTrDtls.getValue("C_FCT_TRNSP_MODE|TRNSP_NAME")).toString());
				factTransportDetails.setTransportCode(
						Utils.validate(factTrDtls.getValue("C_FCT_TRNSP_MODE|TRNSP_CD")).toString());
				factTransportDetails.setTransportDescription(
						Utils.validate(factTrDtls.getValue("C_FCT_TRNSP_MODE|TRNSP_DESC")).toString());
				String validThroughDateValue = Utils.getRelDateFromTable(factTrDtls, "C_FCT_TRNSP_REL");
				LOG.info("validThroughDateValue for TransportDetails is :: " + validThroughDateValue);
				if(factTrDtls.isCreated()) {
					factTransportDetails.setValidThroughDate(Utils.changeInputCreateDate(Utils.validate(validThroughDateValue).toString()));
				}
				else{
					factTransportDetails.setValidThroughDate(Utils.validate(validThroughDateValue).toString());
				}
				LOG.info("written RelationValidThroughDate for Transport");
				factTransportDetailsList.add(factTransportDetails);
			}
			}
			facilityEvent.setFacilityTransDetail(factTransportDetailsList);
		} catch (Exception ex) {
			LOG.info("Exception in binding bindFacilityTransDetail" + ex.getMessage());
			ex.printStackTrace();
			//return new OperationResult(new OperationExecutionError("SIP-60005", bddMessagesLocalizationGate));
			return new OperationResult(new OperationExecutionError("SIP-60005",
			          new String[] {"Error while binding bindFacilityTransDetail"},
			          getLocalizationGate()));
		}
		return OperationResult.OK;
	}

	private OperationResult bindFacilityAddr(SMDSFacilityEvent facilityEvent) {
		// TODO Auto-generated method stub
		LOG.info("Inside bindFacilityAddr method ");
		LOG.info("Got MDM ORs ID ::" +oRSID);
		Connection conn = getDatabaseConnection(oRSID);
		LOG.info("Got Connection::" +conn);
		try {
			LOG.info("Inside try for bindFacilityAddress");
			List<FacilityAddress> facilityAddressList = new ArrayList<>();

			List<BDDObject> facility_Addressess = bddObject.getChildren("Facility_Address");
			for (BDDObject factAddress : facility_Addressess) {
			LOG.info("Is Address removed::"+factAddress.isRemoved());
			if(!factAddress.isRemoved()) {
				LOG.info("Inside the loop for bindFacilityAddress");
				FacilityAddress FacilityAddress = new FacilityAddress();
				FacilityAddress.setHouseNumber(
						Utils.validate(factAddress.getValue("C_CTM_PSTL_ADDR|HOUSE_NUM")).toString());
				FacilityAddress.setStreet(
						Utils.validate(factAddress.getValue("C_CTM_PSTL_ADDR|STREET")).toString());

				String factAddressCityRowid = Utils.validate(factAddress.getValue("C_CTM_PSTL_ADDR|CITY_ROWID"))
						.toString();// TODO
				String query = Utils.getRelationShipDate("C_GDA_DFND_AREA", factAddressCityRowid, "NAME",
						"ROWID_OBJECT");
				city = Utils.getValueFromDB(operationContext, query);
				LOG.info("City is :: " + city);
				FacilityAddress.setCity(city); // TODO
				
				//City Code
				CITY_ROWID = factAddress.getValue("C_CTM_PSTL_ADDR|CITY_ROWID").toString();
				LOG.info("CITY Rowid is " + CITY_ROWID);
				String cityCode=fetchCityCode(conn,CITY_ROWID);
				LOG.info("City Code is ::" +cityCode);
				//FacilityAddress.setCityCd(cityCode);
				//City Code
				FacilityAddress.setPostalCode(Utils.validate(factAddress.getValue("C_CTM_PSTL_ADDR|PSTCD")).toString());
				FacilityAddress.setPoBox(
						Utils.validate(factAddress.getValue("C_CTM_PSTL_ADDR|PO_BOX")).toString());
				FacilityAddress.setDistrict(
						Utils.validate(factAddress.getValue("C_CTM_PSTL_ADDR|DSTRCT")).toString());
				
				String factAddressCountryRowid = Utils.validate(factAddress.getValue("C_CTM_PSTL_ADDR|CTRY_ROWID"))
						.toString();// TODO
				String queryCountry = Utils.getRelationShipDate("C_GDA_DFND_AREA", factAddressCountryRowid, "NAME",
						"ROWID_OBJECT");
				String CTRY_ROWID = factAddress.getValue("C_CTM_PSTL_ADDR|CTRY_ROWID").toString();
				LOG.info("Country Rowid is " + CTRY_ROWID);
				String countryCode=FetchCountryCode(conn,CTRY_ROWID);
				LOG.info("Country Code is ::" +countryCode);
				String country = Utils.getValueFromDB(operationContext, queryCountry);
				LOG.info("Country is :: " + country);
				
				String factAddressRowid = Utils.validate(factAddress.getValue("C_CTM_PSTL_ADDR|TRTY_ROWID")).toString();// TODO
				String queryTerr = Utils.getRelationShipDate("C_GDA_DFND_AREA", factAddressRowid, "NAME",
						"ROWID_OBJECT");
				String territory = Utils.getValueFromDB(operationContext, queryTerr);
				LOG.info("Territory is :: " + territory);
				//String Region_Code = response.getRecord().getField("Territory").getStringValue();
				LOG.info("Region/Territory Code is ::"+territory);
				String Get_Territory = getTerritoryCode(conn, territory, CTRY_ROWID);
				LOG.info("Territory is ::"+Get_Territory);
				if(Get_Territory!=null)
				{	
			
				FacilityAddress.setTerritory(Get_Territory);
				}

				FacilityAddress.setCountry(countryCode);
				FacilityAddress.setAddressLine2(
						Utils.validate(factAddress.getValue("C_CTM_PSTL_ADDR|ADDR_LN_2")).toString());
				FacilityAddress.setAddressLine3(
						Utils.validate(factAddress.getValue("C_CTM_PSTL_ADDR|ADDR_LN_3")).toString());
				FacilityAddress.setLatitude(
						Utils.validate(factAddress.getValue("C_CTM_PSTL_ADDR|LAT_GEOSPTL")).toString());
				FacilityAddress.setLongitude(
						Utils.validate(factAddress.getValue("C_CTM_PSTL_ADDR|LNG_GEOSPTL")).toString());
				
				// facilityEvent.setFacilityAddress(factAddress);
				facilityAddressList.add(FacilityAddress);

				LOG.info("Done writing facilityAddress to facilityAddressList");
			}
			}
			facilityEvent.setFacilityAddress(facilityAddressList);
			LOG.info("Done writing facilityAddress to facilityEvent");
		} catch (Exception ex) {
			LOG.info("Exception in binding bindFacilityAddress" + ex.getMessage());
			ex.printStackTrace();
			//return new OperationResult(new OperationExecutionError("SIP-60006", bddMessagesLocalizationGate));
			return new OperationResult(new OperationExecutionError("SIP-60006",
			          new String[] {"Error while binding bindFacilityAddr"},
			          getLocalizationGate()));
		} finally {
			LOG.info("We are inside finally method");
			try {
				conn.close();
				LOG.info((Object) "--------Connection closed BeforeEverything");
			} catch (Exception e) {
				LOG.info("Exception while closing the connection ::"+e.getMessage());
			}

		}
		return OperationResult.OK;

	}
	
	

	private OperationResult bindFacilityService(SMDSFacilityEvent facilityEvent) {
		// TODO Auto-generated method stub
		try {
			LOG.info("Inside try for bindFacilityService");
			LOG.info("Got MDM ORs ID ::" +oRSID);
			Connection conn = getDatabaseConnection(oRSID);
			LOG.info("Got Connection::" +conn);
			List<FacilityService> factServicesList = new ArrayList<>();

			List<BDDObject> facility_service = bddObject.getChildren("Facility_Services");
			for (BDDObject factServ : facility_service) {
				LOG.info("Is Facility Service Removed::"+factServ.isRemoved());
				if(!factServ.isRemoved()) {
				FacilityService factServices = new FacilityService();
				LOG.info("FACT OFF_DESC is::" + Utils.validate(factServ.getValue("C_FCT_OFF|OFF_DESC")).toString());
				factServices.setServiceDesc(
						Utils.validate(factServ.getValue("C_FCT_OFF|OFF_DESC")).toString());
				//
				String facilitySrNm=Utils.validate(factServ.getValue("C_FCT_OFF|OFF_NAME")).toString();
				String facilitySrCd=Utils.validate(getFacilitySrCd(conn,facilitySrNm)).toString();
				factServices.setFacilityServicesCd(facilitySrCd);
				LOG.info("FACT OFF_NAME is::" + Utils.validate(factServ.getValue("C_FCT_OFF|OFF_NAME")).toString());
				factServices.setServiceName(
						Utils.validate(factServ.getValue("C_FCT_OFF|OFF_NAME")).toString());

				

				String validThroughDateValue = Utils.getRelDateFromTable(factServ, "C_FCT_OFF_REL");
				LOG.info("validThroughDateValue for factServices is :: " + validThroughDateValue);
				if(factServ.isCreated()) {
					factServices.setValidThroughDate(Utils.changeInputCreateDate(Utils.validate(validThroughDateValue).toString()));
				}
				else{
					factServices.setValidThroughDate(Utils.validate(validThroughDateValue).toString());
				}

				factServicesList.add(factServices);
			}
			}
			facilityEvent.setFacilityService(factServicesList);
		} catch (Exception ex) {
			LOG.info("Exception in binding bindFacilityService" + ex.getMessage());
			ex.printStackTrace();
			//return new OperationResult(new OperationExecutionError("SIP-60007", bddMessagesLocalizationGate));
			return new OperationResult(new OperationExecutionError("SIP-60007",
			          new String[] {"Error while binding bindFacilityService"},
			          getLocalizationGate()));
		}
		return OperationResult.OK;
	}


	private OperationResult bindFacilityContDetail(SMDSFacilityEvent facilityEvent) {
		// TODO Auto-generated method stub
		try {
			LOG.info("Inside try for bindFacilityContDetail");
			List<FacilityContactDetail> factContactDetailsList = new ArrayList<>();
			List<BDDObject> facility_contact = bddObject.getChildren("Facility_Contact_Details");
			for (BDDObject factCont : facility_contact) {
				LOG.info("Is Contact Details Removed::"+factCont.isRemoved());
				if(!factCont.isRemoved()) {
				FacilityContactDetail factContactDetails = new FacilityContactDetail();
				factContactDetails.setFirstName(
						Utils.validate(factCont.getValue("C_FCT_CONT|FRST_NM")).toString());
				factContactDetails.setLastName(
						Utils.validate(factCont.getValue("C_FCT_CONT|LST_NM")).toString());
				factContactDetails.setJobTitle(
						Utils.validate(factCont.getValue("C_FCT_CONT|JOB_TITLE")).toString());
				factContactDetails.setDepartment(
						Utils.validate(factCont.getValue("C_FCT_CONT|DEPT")).toString());

				String contIntDialRowidPh = Utils.validate(factCont.getValue("C_FCT_CONT|INTL_DIALNG_ROWID_PH"))
						.toString();
				LOG.info("contIntDialRowidPh is ::" + contIntDialRowidPh);
				String query = Utils.getRelationShipDate("C_CTM_INTL_DIALNG_CD", contIntDialRowidPh, "DAILNG_CD_DESC",
						"ROWID_OBJECT");
				String contIntDialValPh = Utils.getValueFromDB(operationContext, query);
				factContactDetails.setInternationalDialingCdPhone(contIntDialValPh);

				factContactDetails.setExtension(
						Utils.validate(factCont.getValue("C_FCT_CONT|EXTN")).toString());
				factContactDetails.setPhoneNumber(
						Utils.validate(factCont.getValue("C_FCT_CONT|PH_NUM")).toString());

				String contIntDialRowidMob = Utils.validate(factCont.getValue("C_FCT_CONT|INTL_DIALNG_ROWID_MOB"))
						.toString();
				LOG.info("contIntDialRowidMob is ::" + contIntDialRowidMob);
				String query01 = Utils.getRelationShipDate("C_CTM_INTL_DIALNG_CD", contIntDialRowidMob,
						"DAILNG_CD_DESC", "ROWID_OBJECT");
				String contIntDialValMob = Utils.getValueFromDB(operationContext, query);
				factContactDetails.setInternationalDialingCdMobile(contIntDialValMob);

				factContactDetails.setMobileNumber(
						Utils.validate(factCont.getValue("C_FCT_CONT|MOB_NUM")).toString());

				String contIntDialRowidFax = Utils.validate(factCont.getValue("C_FCT_CONT|INTL_DIALNG_ROWID_FAX"))
						.toString();
				LOG.info("contIntDialRowidFax is ::" + contIntDialRowidFax);
				String query02 = Utils.getRelationShipDate("C_CTM_INTL_DIALNG_CD", contIntDialRowidFax,
						"DAILNG_CD_DESC", "ROWID_OBJECT");
				String contIntDialValFax = Utils.getValueFromDB(operationContext, query02);
				factContactDetails.setInternaltionalDialingCodeFax(contIntDialValFax);

				factContactDetails.setFaxNmbr(
						Utils.validate(factCont.getValue("C_FCT_CONT|FAX")).toString());
				factContactDetails.setEmailAddress(
						Utils.validate(factCont.getValue("C_FCT_CONT|EMAIL")).toString());

				String validThroughDateValue = Utils.getRelDateFromTable(factCont, "C_FCT_CONT_REL");
				LOG.info("validThroughDateValue for bindFacilityContDetail is :: " + validThroughDateValue);
				if(factCont.isCreated()) {
					factContactDetails.setValidThroughDate(Utils.changeInputCreateDate(Utils.validate(validThroughDateValue).toString()));
				}else{
					factContactDetails.setValidThroughDate(Utils.validate(validThroughDateValue).toString());
				}

				// facilityEvent.setFacilityContDetail(factContactDetails);
				factContactDetailsList.add(factContactDetails);
			}
			}
			facilityEvent.setFacilityContactDetail(factContactDetailsList);
		} catch (Exception ex) {
			LOG.info("Exception in binding bindFacilityContDetail" + ex.getMessage());
			ex.printStackTrace();
			//return new OperationResult(new OperationExecutionError("SIP-60008", bddMessagesLocalizationGate));
			return new OperationResult(new OperationExecutionError("SIP-60008",
			          new String[] {"Error while binding bindFacilityContDetail"},
			          getLocalizationGate()));
		}
		return OperationResult.OK;
	}

	private OperationResult bindFacilityWTCSMtch(SMDSFacilityEvent facilityEvent) {
		// TODO Auto-generated method stub
		try {
			LOG.info("Inside try for bindFacilityWTCSMtch");
			List<FacilityWTCSMatch> factWTCSmtchList = new ArrayList<>();

			List<BDDObject> facility_wtcs_mtch = bddObject.getChildren("WTCSFacilityMatch");
			for (BDDObject factwtcs : facility_wtcs_mtch) {
				FacilityWTCSMatch factWTCSmtch = new FacilityWTCSMatch();
				factWTCSmtch.setFactWtcsName1(
						Utils.validate(factwtcs.getValue("C_WTCS_SANCTION_DETAILS|NAME1")).toString());
				factWTCSmtch.setFactWtcsAddrLn1(
						Utils.validate(factwtcs.getValue("C_WTCS_SANCTION_DETAILS|ADDR_LN_1")).toString());
				factWTCSmtch.setFactWtcsCityNm(
						Utils.validate(factwtcs.getValue("C_WTCS_SANCTION_DETAILS|CITY_NAME")).toString());
				factWTCSmtch.setFactWtcsDistrictNm(
						Utils.validate(factwtcs.getValue("C_WTCS_SANCTION_DETAILS|DISTRICT_NAME")).toString());
				factWTCSmtch.setFactWtcsPostalCd(
						Utils.validate(factwtcs.getValue("C_WTCS_SANCTION_DETAILS|POST_CODE")).toString());
				factWTCSmtch.setFactWtcsCountryCd(
						Utils.validate(factwtcs.getValue("C_WTCS_SANCTION_DETAILS|COUNTRY_CODE")).toString());
				factWTCSmtch.setFactWtcsCountryNm(
						Utils.validate(factwtcs.getValue("C_WTCS_SANCTION_DETAILS|COUNTRY_NAME")).toString());
				factWTCSmtch.setFactWtcsSubjectArea(
						Utils.validate(factwtcs.getValue("C_WTCS_SANCTION_DETAILS|SUBJECT_AREA")).toString());
				factWTCSmtch.setFactWtcsInterRunId(
						Utils.validate(factwtcs.getValue("C_WTCS_SANCTION_DETAILS|INTRFC_RUN_ID")).toString());
				factWTCSmtch.setFactWtcsCategory(
						Utils.validate(factwtcs.getValue("C_WTCS_SANCTION_DETAILS|CTGRY")).toString());
				factWTCSmtch.setFactWtcsProgramme(
						Utils.validate(factwtcs.getValue("C_WTCS_SANCTION_DETAILS|PROGRM")).toString());
				// facilityEvent.setFacilityWTCSMtch(factWTCSmtch);
				factWTCSmtchList.add(factWTCSmtch);

			}
			facilityEvent.setFacilityWTCSMtch(factWTCSmtchList);

		} catch (Exception ex) {
			LOG.info("Exception in binding bindFacilityWTCSMtch" + ex.getMessage());
			ex.printStackTrace();
			return new OperationResult(new OperationExecutionError("SIP-60009",
			          new String[] {"Error while binding bindFacilityWTCSMtch"},
			          getLocalizationGate()));
		}
		return OperationResult.OK;
	}

	@Override
	public OperationType getOperationType() {
		// TODO Auto-generated method stub
		return null;
	}

	public Connection getDatabaseConnection(String mdmORSid) {

		try {
			String dataSourceName = "jdbc/siperian-" + mdmORSid.toLowerCase() + "-ds";
			LOG.info((Object) ("The MDM DataSource name is ::" + dataSourceName));
			InitialContext ctx = new InitialContext();
			DataSource ds = (DataSource) ctx.lookup(dataSourceName);
			connection = ds.getConnection();
			LOG.info((Object) "getConnection end - success");
			return connection;
		} catch (Exception e) {
			LOG.info((Object) ("getConnection Exception ::" + e.getLocalizedMessage()));
			return null;
		}

	}
	public String FetchCountryCode(Connection conn, String CNTRY_ROWID) {
		String CNTRY_CD = "";
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT SUBSTR(CODE,0,2) AS CNTRY_CD FROM C_ALT_CODE WHERE TYP_TYPE_ROWID= ");
		sb.append("(SELECT ROWID_OBJECT FROM C_TYP_TYPE WHERE CODE='ALT_CODE.RKST') ");
		sb.append("AND GDA_DFND_AREA_ROWID= ");
		sb.append("'" + CNTRY_ROWID + "'");
		LOG.info((Object) sb);
		try {
			PreparedStatement stmt = null;
			stmt = conn.prepareStatement(sb.toString());
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				CNTRY_CD = rs.getString("CNTRY_CD");
				LOG.info((Object) ("CNTRY_CD is ::" + CNTRY_CD));
			}
			return CNTRY_CD;
		} catch (Exception e) {
			LOG.info((Object) ("FetchCountryCode Exception ::" + e.getLocalizedMessage()));
			return "Error in Fetching the Code";
		}
	}

	public String getTerritoryCode(Connection conn, String Region_Code, String CTRY_ROWID) throws SQLException {
		String region=null;
		String countryRowid=null;
		String TRTY_CD=null;
		if(Region_Code!=null && CTRY_ROWID!=null) {
		region=Region_Code.trim();
		countryRowid=CTRY_ROWID.trim();
		 String regionQuery="SELECT TRTY.ROWID_OBJECT AS TRTY_ROWID, TRTY.NAME AS TRTY_NAME,\r\n" + 
			 		"     ALT_CODE.CODE AS TRTY_CODE, CTRY.ROWID_OBJECT AS CTRY_ROWID, TYP_TYPE.CODE AS TYP_TYPE_CODE,\r\n" + 
			 		"     REL.HUB_STATE_IND FROM C_GDA_DFND_AREA TRTY INNER JOIN C_GDA_DFND_AREA_REL\r\n" + 
			 		"     REL ON TRTY.ROWID_OBJECT = REL.GDA_DFND_AREA_CHLD_ROWID INNER JOIN\r\n" + 
			 		"     C_GDA_DFND_AREA CTRY ON CTRY.ROWID_OBJECT = REL.GDA_DFND_AREA_PRNT_ROWID\r\n" + 
			 		"     INNER JOIN C_ALT_CODE ALT_CODE ON TRTY.ROWID_OBJECT = ALT_CODE.GDA_DFND_AREA_ROWID\r\n" + 
			 		"     INNER JOIN C_TYP_TYPE TYP_TYPE ON TYP_TYPE.ROWID_OBJECT = ALT_CODE.TYP_TYPE_ROWID\r\n" + 
			 		"     WHERE REL.TYP_TYPE_CD = 'GDA_REL.TRTY_IN_CTRY'\r\n" + 
			 		"     AND TRTY.TYP_TYPE_CD = 'GDA.STATE/PROV'\r\n" + 
			 		"     AND TRTY.ACTIVE_FLAG = 'Y' and TYP_TYPE.CODE='ALT_CODE.ISO_TRTY' " + 
			 		"AND CTRY.ROWID_OBJECT='"+countryRowid+"' AND UPPER(TRTY.NAME) LIKE UPPER('%"+region+"%')";
		 
		 
		LOG.info("Query to get Territory Code is :: "+regionQuery);
		try {
			LOG.info("Inside Try to Execute Query for getTerritoryCode method");
			PreparedStatement stmt = null;
			stmt = conn.prepareStatement(regionQuery);
			LOG.info("Statement Created"+stmt);
			ResultSet rs = stmt.executeQuery(regionQuery);
			LOG.info("ResultSet Executed"+rs);
			while (rs.next()) {
				LOG.info("Inside while loop for restultSet");
				TRTY_CD = rs.getString("TRTY_CODE");
				LOG.info((Object) ("TRTY_CODE is ::" + TRTY_CD));
			}
			
		} catch(SQLException ex) {
			ex.printStackTrace();
			LOG.info("SQL Exception is ::"+ex.getMessage());
		}catch (Exception e) {
			e.printStackTrace();
			LOG.info("Message is "+e.getStackTrace());
			LOG.info((Object) ("getTerritoryCode Exception ::" + e.getMessage()));
			return TRTY_CD;
			}
		}
		return TRTY_CD;
		}
	
	
	public String fetchCityCode(Connection conn, String CITY_ROWID) {
		String CITY_CD = "";
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT CODE AS CITY_CD FROM C_ALT_CODE WHERE TYP_TYPE_ROWID= ");
		sb.append("(SELECT ROWID_OBJECT FROM C_TYP_TYPE WHERE CODE='ALT_CODE.RKST') ");
		sb.append("AND GDA_DFND_AREA_ROWID= ");
		sb.append("'" + CITY_ROWID + "'");
		LOG.info((Object) sb);
		try {
			PreparedStatement stmt = null;
			stmt = conn.prepareStatement(sb.toString());
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				CITY_CD = rs.getString("CITY_CD");
				LOG.info((Object) ("CITY_CD is ::" + CITY_CD));
			}
			return CITY_CD;
		} catch (Exception e) {
			LOG.info((Object) ("FetchCountryCode Exception ::" + e.getLocalizedMessage()));
			return "Error in Fetching the Code";
		}
	}
	
	
	private String getFacilitySrCd(Connection conn, String FACTSR_NM) {
		String GRP_CD = "";
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT GRP_CD FROM C_FCT_OFF WHERE OFF_NAME= ");
		sb.append("'" + FACTSR_NM + "'");
		LOG.info((Object) sb);
		LOG.info("Query to get FacilityCd is ::" +sb.toString());
		try {
			PreparedStatement stmt = null;
			stmt = conn.prepareStatement(sb.toString());
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				GRP_CD = rs.getString("GRP_CD");
				LOG.info((Object) ("GRP_CD is ::" + GRP_CD));
			}
			return GRP_CD;
		} catch (Exception e) {
			LOG.info((Object) ("getFacilitySrCd Exception ::" + e.getLocalizedMessage()));
			return "Error in Fetching the Code";
		}
	}
	private OperationResult bindFacilityParent(SMDSFacilityEvent facilityEvent) {
		Connection conn = getDatabaseConnection(oRSID);
		FacilityParent factParent= new FacilityParent();
		factParent.setParentName(city);
		List<FacilityAltCode> factAltCodes=new ArrayList<FacilityAltCode>();
		HashMap<String, String> altCodesValues=new HashMap<>();
		altCodesValues=getCityAltCodesValues(conn, CITY_ROWID );
		for(Entry<String, String> e: altCodesValues.entrySet()){
			FacilityAltCode factAltCode = new FacilityAltCode();
		    	factAltCode.setTypeCode(e.getValue());
		    	factAltCode.setAltCode(e.getKey());
		    	factAltCodes.add(factAltCode);
		}

		factParent.setFacilityParentAltCodes(factAltCodes);
		facilityEvent.setFacilityParent(factParent);
		return OperationResult.OK;
	}
	

	public HashMap<String, String> getCityAltCodesValues(Connection conn, String CITY_ROWID) {
		HashMap<String, String> atlCodeValue=new HashMap<String, String>();
		String CITY_CD = "";
		String typTypeRowid="";
		String typTypeCode="";
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT TYP_TYPE_ROWID,CODE FROM C_ALT_CODE WHERE ");
		sb.append("HUB_STATE_IND ='1' AND GDA_DFND_AREA_ROWID= ");
		sb.append("'" + CITY_ROWID + "'");
		LOG.info((Object) sb);
		try {
			PreparedStatement stmt = null;
			stmt = conn.prepareStatement(sb.toString());
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				typTypeRowid=rs.getString("TYP_TYPE_ROWID");
				LOG.info((Object) ("TypTypeRowid is ::" + typTypeRowid));
				CITY_CD = rs.getString("CODE");
				LOG.info((Object) ("CODE is ::" + CITY_CD));
				StringBuilder sbQ = new StringBuilder();
				sbQ.append("SELECT NAME FROM C_TYP_TYPE WHERE ");
				sbQ.append(" ROWID_OBJECT=");
				sbQ.append("'" + typTypeRowid.trim() + "'");
				try {
					stmt = conn.prepareStatement(sbQ.toString());
					ResultSet rs01 = stmt.executeQuery();
					while (rs01.next()) {
					typTypeCode=rs01.getString("NAME");
					LOG.info((Object) ("TypTypeCode is ::" + typTypeCode));
					}
			} catch (Exception e) {
				LOG.info((Object) ("getCityAltCodesValues Exception ::" + e.getLocalizedMessage()));
				return null;
			}
				atlCodeValue.put(CITY_CD, typTypeCode);
		} 
			}catch (Exception e) {
			LOG.info((Object) ("getCityAltCodesValues Exception ::" + e.getLocalizedMessage()));
			return null;
		}
		return atlCodeValue;
	}
}