package net.apmoller.maersk.services.fct.geowrite;

import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import javax.ejb.EJBContext;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import javax.xml.namespace.QName;

import org.apache.commons.configuration.CompositeConfiguration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.springframework.transaction.annotation.Transactional;

import com.siperian.sif.message.mrm.PutResponse;

import net.apmoller.maersk.services.fct.geowrite.messaging.PropUtil;
import net.apmoller.services.cmd.definitions.FacilitySearchService;
import net.apmoller.services.cmd.definitions.RetrieveFacilityFault;
import net.apmoller.services.cmd.definitions.SearchFacility;
import net.apmoller.services.cmd.schemas.FacilityCategoryEnum;
import net.apmoller.services.cmd.schemas.FacilityCategoryOperationalType;
import net.apmoller.services.cmd.schemas.RetrieveFacilityRequest;
import net.apmoller.services.cmd.schemas.RetrieveFacilityResponse;
import net.apmoller.services.cmd.schemas.RetrieveRequestFacilityIDsType;

/**
 * Session Bean implementation class GeoWriteBackDao
 *
 */
@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
@Stateless(mappedName = "GeoWriteBackDao")
public class GeoWriteBackDao implements GeoWriteBackDaoLocal, GeoWriteBackDaoRemote {
	/** The Constant LOGGER for the SDNServiceEAO.class. */
		private static final Logger LOGGER = Logger.getLogger(GeoWriteBackDao.class.getName());

	/**
	 * Default constructor.
	 */
	public GeoWriteBackDao() {
		LOGGER.log(Level.INFO, "Geo write back created");
	}

	/** The config. */
	private CompositeConfiguration config = null;

	@Resource(name = "MDM_DATASOURCE")
	private DataSource fctDatasource;

	/** The ejb context. */
	@Resource
	EJBContext ejbContext;

	private void initProperties() {
		try {
			InitialContext ic = new InitialContext();
			config = new CompositeConfiguration();
			config.addConfiguration(new PropertiesConfiguration((String) ic.lookup("FCT_ENVIRONMENT_CONFIG_LOCATION")),
					true);
			config.addConfiguration(new PropertiesConfiguration((String) ic.lookup("FCT_BUSINESS_CONFIG_LOCATION")),
					true);
		} catch (NamingException | ConfigurationException e) {
			LOGGER.fatal(e.getLocalizedMessage());
		}
	}

	@Override
	public Boolean geoWriteBack(String geoid, Boolean isgeocode)
			throws MalformedURLException, RetrieveFacilityFault, SQLException, ParseException {
		try {
			LOGGER.info("***********************************************");
			LOGGER.info("*************Facility GeoWrite Back Started *************" + geoid);
			LOGGER.info("GEO ID Recieved : " + geoid);
			LOGGER.info("Loading Properties");
			this.initProperties();
			//this.logAllTypeFromConfig(config);
			Map<String, List<String>> opstypemap = new HashMap<String, List<String>>();
			LOGGER.info("Creating FCT type and Geo type mapping");
			List<String> fcttype1 = new ArrayList<String>();
			fcttype1.add(config.getString("FCT_TYPE.TERMINAL"));
			fcttype1.add(config.getString("FCT_TYPE.WATERWAY"));
			//Added Below Changes-Anil
			fcttype1.add(config.getString("FCT_TYPE.ONOFFH_SITE"));
			fcttype1.add(config.getString("FCT_TYPE.CANAL_SITE"));
			fcttype1.add(config.getString("FCT_TYPE.BUNKER_SITE"));
			//Added Above Changes-Anil
			opstypemap.put(config.getString("SITE_TYPE.TMNL"), fcttype1);

			List<String> fcttype2 = new ArrayList<String>();
			fcttype2.add(config.getString("FCT_TYPE.RAILHEAD"));
			opstypemap.put(config.getString("SITE_TYPE.RAIL_TMNL"), fcttype2);

			List<String> fcttype3 = new ArrayList<String>();
			fcttype3.add(config.getString("FCT_TYPE.BARGE_PIER"));
			opstypemap.put(config.getString("SITE_TYPE.BARGE_TMNL"), fcttype3);

			List<String> fcttype4 = new ArrayList<String>();
			fcttype4.add(config.getString("FCT_TYPE.TRUCKER"));
			fcttype4.add(config.getString("FCT_TYPE.CFS"));
			fcttype4.add(config.getString("FCT_TYPE.MAIN_N_REP"));
			opstypemap.put(config.getString("SITE_TYPE.CONTR_FSTN"), fcttype4);

			List<String> fcttype5 = new ArrayList<String>();
			fcttype5.add(config.getString("FCT_OPS_TYPE.ICD"));
			fcttype5.add(config.getString("FCT_TYPE.OffHIRE_DEP"));
			fcttype5.add(config.getString("FCT_TYPE.REEFER_DEP"));
			fcttype5.add(config.getString("FCT_TYPE.COLD_STORE"));
			fcttype5.add(config.getString("FCT_TYPE.PCC_DEP"));
			opstypemap.put(config.getString("SITE_TYPE.DEPOT"), fcttype5);

			List<String> fcttype6 = new ArrayList<String>();
			fcttype6.add(config.getString("FCT_TYPE.CUST_POOL_F"));
			fcttype6.add(config.getString("FCT_TYPE.WAREHOUSE"));
			opstypemap.put(config.getString("SITE_TYPE.CUST_LOCN"), fcttype6);

			List<String> fcttype7 = new ArrayList<String>();
			fcttype7.add(config.getString("FCT_TYPE.CTR_PROD"));
			opstypemap.put(config.getString("SITE_TYPE.CTR._PRODN"), fcttype7);

			LOGGER.info("#############Calling retrieve facility service##############");
			URL baseUrl = net.apmoller.services.cmd.definitions.FacilitySearchService.class.getResource(".");
			LOGGER.info("BaseUrl is ::"+baseUrl);
			URL url = new URL(baseUrl, config.getString("FACILITY_SEARCH_SERVICE"));
			FacilitySearchService facilityservice = new FacilitySearchService(url,
					new QName("http://services.apmoller.net/cmd/definitions", "FacilitySearchService"));
			SearchFacility searchfacility = facilityservice.getSearchFacilityPort();
			RetrieveFacilityRequest retrieveRequest = new RetrieveFacilityRequest();
			RetrieveRequestFacilityIDsType requesttype = new RetrieveRequestFacilityIDsType();
			requesttype.setFacilityGEOId(geoid);
			retrieveRequest.setFacilityIDs(requesttype);
			RetrieveFacilityResponse response = searchfacility.retrieveFacility(retrieveRequest);

			if (!response.getFacility().getFacilityCategory().equals(FacilityCategoryEnum.OPS)) {
				LOGGER.info("No GeoWrite Back for Non OPS Facility !!!" + geoid);
				return false;
			}
			LOGGER.info("GWB For Facility Name "+response.getFacility().getFacilityName());
			LOGGER.info("GWB For Facility STATUS  "+response.getFacility().getFacilityLifecycleStatus().value());
			LOGGER.info("Getting Address rowid and valid dates");
			GeoWriteBackVO geodata = this.getGeoWriteData(response.getFacility().getFacilityIDs().getFacilityGEOId(),
					response.getFacilityAddress().getCityDetails().getCityGeoID());

			LOGGER.info("Getting ALL Required typ type rowids");
			String facilitygeoidaltcodetypetyprowid = this.getFacilityAltCodesTypeTypRowid("ALT_CODE.GEOID");
			String facilityrkstaltcodetypetyprowid = this.getFacilityAltCodesTypeTypRowid("ALT_CODE.RKST");
			String facilityrktsaltcodetypetyprowid = this.getFacilityAltCodesTypeTypRowid("ALT_CODE.RKTS");
			String facilityaltcodemodeltypetyprowid = this.getFacilityAltCodesTypeTypRowid("ALT_CODE.MODEL");
			String facilitycustomscodetypetyprowid = this.getFacilityAltCodesTypeTypRowid("ALT_CODE.CUSTOMSLOC");
			//Added below changes-Anil
			
			String facilityuncodecodetypetyprowid = this.getFacilityAltCodesTypeTypRowid("ALT_CODE.UN_CODE");
			String facilityuncodecode2typetyprowid = this.getFacilityAltCodesTypeTypRowid("ALT_CODE.UN_CODE2");
			String facilityuncodecode1typetyprowid = this.getFacilityAltCodesTypeTypRowid("ALT_CODE.UN_CODE1");
			
			//Added Above changes-Anil
			LOGGER.info("Facility Geo ID Type Alt Code typ type ROW ID " + facilitygeoidaltcodetypetyprowid);
			LOGGER.info("Facility RKST ID TypeAlt Code typ type ROW ID " + facilityrkstaltcodetypetyprowid);
			LOGGER.info("Facility RKTS Alt Code  Type typ type ROW ID " + facilityrktsaltcodetypetyprowid);
			LOGGER.info("Facility Alt Code Model Type typ type ROW ID " + facilityaltcodemodeltypetyprowid);
			LOGGER.info("Facility CUSTOMERSCODE  Type typ type ROW ID " + facilitycustomscodetypetyprowid);
			//Added Below change-Anil
				
			LOGGER.info("Facility UNCode  Type typ type ROW ID " + facilityuncodecodetypetyprowid);
			LOGGER.info("Facility UNCode 2  Type typ type ROW ID " + facilityuncodecode2typetyprowid);
			LOGGER.info("Facility UNCode 1  Type typ type ROW ID " + facilityuncodecode1typetyprowid);
			//Added above changes-Anil

			if (facilitygeoidaltcodetypetyprowid == null || facilityrkstaltcodetypetyprowid == null
					|| facilityrktsaltcodetypetyprowid == null || facilityaltcodemodeltypetyprowid == null || facilitycustomscodetypetyprowid==null) {
				LOGGER.info("Required typ type rowid is null");
				return false;
			}

			if (facilitygeoidaltcodetypetyprowid.equals("")) {
				LOGGER.info("Not Recieved Facility Alt Code typ type ROW ID");
				return false;
			}
			
			if (response.getFacility().getFacilityTypes() == null
					|| response.getFacility().getFacilityTypes().getFacilityTypeDetails() == null
					|| response.getFacility().getFacilityTypes().getFacilityTypeDetails().size() == 0) {
				LOGGER.info("Not Recieved Facility type");
				LOGGER.info("Not Recieved Facility type ::" +response.getFacility().getFacilityTypes());
				LOGGER.info("Not Recieved Facility type ::" +response.getFacility().getFacilityTypes().getFacilityTypeDetails());
				LOGGER.info("Not Recieved Facility type ::" +response.getFacility().getFacilityTypes().getFacilityTypeDetails().size());
				return false;
			}

			String facilitygdasitetyptyperowid = "";
			LOGGER.info("Matching Facility type with Geo type");
			List<FacilityCategoryOperationalType> facilitytypelist = response.getFacility().getFacilityTypes()
					.getFacilityTypeDetails();
			List<String> listgeotype = new ArrayList<String>();
			LOGGER.info("Number of Facility type" + facilitytypelist.size());
			LOGGER.info("Matching Facility type with Geo type" + facilitytypelist);

			for (FacilityCategoryOperationalType type : facilitytypelist) {
				LOGGER.info("opstypemap Map value is  " + opstypemap.size());
				LOGGER.info("opstypemap Map value is  " + opstypemap);
				LOGGER.info("Matching Facility Type is  " + type.getFacilityType());
				for (Map.Entry<String, List<String>> entry : opstypemap.entrySet()) {
					LOGGER.info("Geo Type Matching is  " + entry.getKey());
					for (String str : entry.getValue()) {
						if (str.equals(type.getFacilityType())) {
							LOGGER.info("Matched Geo Type is  " + entry.getKey());
							LOGGER.info("Matched Facility Type is  " + type.getFacilityType());
							listgeotype.add(entry.getKey());
						}
					}
				}
			}
			String selectedgeotype = this.getSiteType(listgeotype);
			LOGGER.info("Highest Ranked geotype from the facility  " + selectedgeotype);
			facilitygdasitetyptyperowid = this.getFacilityGdaSiteTypeTypRowid(selectedgeotype);
			LOGGER.info("Facility Type ROW ID " + facilitygdasitetyptyperowid);
			if (facilitygdasitetyptyperowid.equals("")) {
				LOGGER.info("Not Recieved Facility Site typ type Rowid");
				return false;
			}
			String facilityrowid=this.getFacilityRowid(geoid);
			LOGGER.info("Calling Geo Write Back Helper");
			LOGGER.info("Faclity Rowid  " + facilityrowid);
			String rktscode = this.getFacilityRKTSAltCodesRowid(this.getFacilityRowid(geoid),
					facilityrktsaltcodetypetyprowid);
			LOGGER.info("RKTS Rowid  : " + rktscode);
			//Added Changes Below-Anil
			
			String facilityuncodecodetypetypcode="";
			String facilityuncodecode2typetypcode="";
			String facilityrktstypetypcode="";
			List<String> facilityuncodecode1typetypcode=new ArrayList<String>();
			
			HashMap<String, String> unCodesLkpToInsert=new HashMap<>();
			if(facilityuncodecodetypetyprowid!=null) {
			facilityuncodecodetypetypcode = this.getCodeTypeForFacilitiesAltCodes(facilityrowid,
					facilityuncodecodetypetyprowid);
			LOGGER.info("facilityuncodecodetypetypcode Code  : " + facilityuncodecodetypetypcode);
				
			}

			if(facilityrktsaltcodetypetyprowid!=null) {
				facilityrktstypetypcode = this.getCodeTypeForFacilitiesAltCodes(facilityrowid,
						facilityrktsaltcodetypetyprowid);
				LOGGER.info("facilityrktsaltcodetypetyprowid Code  : " + facilityrktstypetypcode);

			}

			if(facilityuncodecode2typetyprowid!=null) {
				 facilityuncodecode2typetypcode = this.getCodeTypeForFacilitiesAltCodes(facilityrowid,facilityuncodecode2typetyprowid);
			LOGGER.info("facilityuncodecode2typetypcode Code  : " + facilityuncodecode2typetypcode);
				
			}
			if(facilityuncodecode1typetyprowid!=null && !facilityuncodecode1typetyprowid.equalsIgnoreCase("")) {
			 facilityuncodecode1typetypcode = this.getUnLookupFacilitiesAltCodes(facilityrowid,
					facilityuncodecode1typetyprowid);
			LOGGER.info("facilityuncodecode1typetypcode Code  : " + facilityuncodecode1typetypcode);
					if(!facilityuncodecode1typetypcode.isEmpty()) {
							for(String unLookupCodes:facilityuncodecode1typetypcode) {
									unCodesLkpToInsert.put(unLookupCodes,facilityuncodecode1typetyprowid);
							}
					}
			}
			GeoWriteBackHelper geowritebackhelper = new GeoWriteBackHelper();
			boolean toDeleteExistingUNCode=false;
			//Added Above Changes-Anil
			GeoRowID georowid = new GeoRowID();
			String gdaarearowid = this.getGeoGDAAreaRowid(geoid);
			if (gdaarearowid != null) {
				toDeleteExistingUNCode=true;
				LOGGER.info("Getting GEO ROW ID for update " + gdaarearowid);
				georowid.setGdaAreaRowid(gdaarearowid);
				LOGGER.info("GEO ID " + geoid);
				LOGGER.info("CITY ID " + response.getFacilityAddress().getCityDetails().getCityGeoID());
				LOGGER.info("GDAAREA ROWID  " + gdaarearowid);
				String existingcityrowid=this.getExistingCityRowID(georowid.getGdaAreaRowid());
				if(!existingcityrowid.equals(geodata.getAddressRowid())){
					LOGGER.fatal("Cannot Update Existing City "+existingcityrowid+" AND NEW CITY ROWID "+geodata.getAddressRowid());
					return false;
				}
				georowid.setGdaAreaRelRowid(this.getGeoGDAAreaRelRowid(geoid,
						response.getFacilityAddress().getCityDetails().getCityGeoID(), gdaarearowid));
				this.getAltCode(gdaarearowid, georowid.getAltcodemap(),facilityuncodecode1typetyprowid);
				georowid.setGdaSiteRowid(this.getGDASiteRowid(gdaarearowid, selectedgeotype));
				georowid.setTmpPhylcnRowid(this.getPhyLcnRowId(gdaarearowid));
			}

			//georowid.setUnAltcodemap(this.getUnAltCode(gdaarearowid,facilityuncodecode1typetyprowid));

			if(toDeleteExistingUNCode) {
				UpdateAndDeleteAltCD(geoid, facilityuncodecodetypetyprowid, facilityuncodecode2typetyprowid, facilityuncodecode1typetyprowid, facilityrowid, geowritebackhelper, toDeleteExistingUNCode, georowid, gdaarearowid);
			}

			String customsloc=this.getCustomsLocCode(facilityrowid, facilitycustomscodetypetyprowid);
			
			geowritebackhelper.insertGeoData(response, geodata, facilitygeoidaltcodetypetyprowid,
					facilityrkstaltcodetypetyprowid, facilityrktsaltcodetypetyprowid, rktscode,
					facilitygdasitetyptyperowid, facilityaltcodemodeltypetyprowid,
					this.getExistingGdaSiteRowid(response.getFacility().getFacilityIDs().getFacilityGEOId()), georowid,
					customsloc, facilitycustomscodetypetyprowid, facilityuncodecodetypetyprowid,
					facilityuncodecodetypetypcode, facilityuncodecode2typetyprowid, facilityuncodecode2typetypcode,
					unCodesLkpToInsert);
			//
		//	this.getAltCode(gdaarearowid, georowid.getAltcodemap(),facilityuncodecode1typetyprowid);
			
			//

			} catch (MalformedURLException | RetrieveFacilityFault | SQLException | ParseException e) {
			LOGGER.info("Exception in GeowriteBack service ::"+e);
			LOGGER.info("Exception in GeowriteBack service 001::"+e.getLocalizedMessage());
			LOGGER.info("Exception in GeowriteBack service 002::"+e.getMessage());
			LOGGER.info("Exception in GeowriteBack service 003::"+e.getCause());
			LOGGER.info("Exception in GeowriteBack service 004::"+e.getStackTrace());
			LOGGER.info("Exception in GeowriteBack service 005::"+e.toString());
			ejbContext.setRollbackOnly();
			throw e;
		}
		catch(Exception e) {
			LOGGER.info("Exception in GeowriteBack service 01::"+e.getLocalizedMessage());
			LOGGER.info("Exception in GeowriteBack service 02::"+e.getMessage());
			LOGGER.info("Exception in GeowriteBack service 03::"+e.getCause());
			LOGGER.info("Exception in GeowriteBack service 04::"+e.getStackTrace());
			LOGGER.info("Exception in GeowriteBack service 05::"+e.toString());
			e.printStackTrace();
		}
		LOGGER.info("###################GEO WRITE BACK COMPLETED################");
		return true;
	}

	private void UpdateAndDeleteAltCD(String geoid, String facilityuncodecodetypetyprowid, String facilityuncodecode2typetyprowid, String facilityuncodecode1typetyprowid, String facilityrowid, GeoWriteBackHelper geowritebackhelper, boolean toDeleteExistingUNCode, GeoRowID georowid, String gdaarearowid) throws SQLException {
		List<String> facilityUNAltCodes=getFactUnLkpDeletedCodes(this.getFacilityRowid(geoid), facilityuncodecode1typetyprowid);
		if(toDeleteExistingUNCode ==true) {
			LOGGER.info("This is an Update.. Check Deleted codes in Facility.. Delete in Geo Starts");
			LOGGER.info("Check if unCodesToInsert is empty ::"+facilityUNAltCodes.isEmpty());
				if(!facilityUNAltCodes.isEmpty()) {
					for(String code:facilityUNAltCodes) {
						LOGGER.info("InActive Record is Facility is ::"+code);
						String firstQuery="firstQuery";
						//Richa Change
					  List<String> existingActiveCodes=getExistingGeoCode(georowid,code, facilityrowid,firstQuery);
					  for(String existingActiveCode:existingActiveCodes) {
						  LOGGER.info("Existing UNCode to Delete in Geo is ::" + existingActiveCode);
						  if (existingActiveCode != null) {
							  if (!existingActiveCode.equalsIgnoreCase("")) {
								  LOGGER.info("Record is active in Geo, Inactive in Facility ::" + existingActiveCode);
								  geowritebackhelper.deleteUNCodeXref(existingActiveCode);
							  }
						  }
					  }
					   }
				}
		}

		List<String> facilityAltCodes=getFactAltDeletedCodes(this.getFacilityRowid(geoid), facilityuncodecode1typetyprowid);
		if(toDeleteExistingUNCode ==true) {
			LOGGER.info("This is an Update.. Check Deleted codes in Facility.. Delete in Geo Starts");
				LOGGER.info("Check if unCodesToInsert is empty ::"+facilityAltCodes.isEmpty());
					if(!facilityAltCodes.isEmpty())
						for(String code:facilityAltCodes) {
							LOGGER.info("InActive Record is Facility is ::"+code);
							String secondQuery="secondQuery";
							//Richa Change
							List<String> existingActiveCodes=getExistingGeoCode(georowid,code, facilityrowid,secondQuery);
							for(String existingActiveCode : existingActiveCodes) {
								LOGGER.info("Existing UNCode to Delete in Geo is ::" + existingActiveCode);
								if (existingActiveCode != null) {
									if (!existingActiveCode.equalsIgnoreCase("")) {
										LOGGER.info("Record is active in Geo, Inactive in Facility ::" + existingActiveCode);
										geowritebackhelper.deleteUNCodeXref(existingActiveCode);
									}
								}
								/*if (facilityrktsaltcodetypetyprowid.equals("23")){
									if (existingActiveCode != null) {
										if (!existingActiveCode.equalsIgnoreCase("")) {
											LOGGER.info("Record is active in Geo, Inactive in Facility ::" + existingActiveCode);
											geowritebackhelper.deleteUNCodeXref(existingActiveCode);
										}
									}
							}*/
							}
				}
		}
		//Delete cases where UNLookup has been updated
		List<String> facilityupdatedUNAltCodes = getupdatedUNLookupCodes(this.getFacilityRowid(geoid),
				facilityuncodecode1typetyprowid, facilityuncodecode2typetyprowid, facilityuncodecodetypetyprowid,
				gdaarearowid);
		if(toDeleteExistingUNCode ==true) {
			LOGGER.info("This is an Update.. Check Deleted codes in Facility.. Delete in Geo Starts");
				LOGGER.info("Check if unCodesToInsert is empty ::"+facilityupdatedUNAltCodes.isEmpty());
					if(!facilityupdatedUNAltCodes.isEmpty())
						for(String code:facilityupdatedUNAltCodes) {
							{
								LOGGER.info("Record is active in Geo, NOT present in Facility ::"+code);
								geowritebackhelper.deleteUNCodeXref(code);
							 }
						}
				}
	}

	private String getFacilityAltCodesTypeTypRowid(String str) throws SQLException {
		String fctaltcodetyptyperowid = "";
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT ROWID_OBJECT ");
		sb.append("  FROM   C_TYP_TYPE");
		sb.append(" WHERE  HUB_STATE_IND           = 1");
		sb.append(" AND    CODE                   = (?)    ");
		sb.append(" AND    ROWNUM < 2");
		PreparedStatement stmt = null;
		try (Connection con = fctDatasource.getConnection();) {
			stmt = con.prepareStatement(sb.toString());
			stmt.setString(1, str);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				fctaltcodetyptyperowid = rs.getString("ROWID_OBJECT");
			}
		}
		return fctaltcodetyptyperowid;

	}
	private String getFacilityRowid(String geocide) throws SQLException {
		String fctrowid = "";
		StringBuilder sb = new StringBuilder();
		sb.append("select FCT_ROWID from c_fct_alt_codes where CODE=? AND ROWNUM < 2");
		PreparedStatement stmt = null;
		try (Connection con = fctDatasource.getConnection();) {
			stmt = con.prepareStatement(sb.toString());
			stmt.setString(1, geocide);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				fctrowid = rs.getString("FCT_ROWID");
			}
		}
		return fctrowid;
	}

//	private String getFacilityAltCodesModelTypeTypRowid(String str) throws SQLException {
//		String fctaltcodetyptyperowid = "";
//		StringBuilder sb = new StringBuilder();
//		sb.append("SELECT ROWID_OBJECT ");
//		sb.append("  FROM   C_TYP_TYPE");
//		sb.append(" WHERE  HUB_STATE_IND           = 1");
//		sb.append(" AND    CODE                   = (?)    ");
//		sb.append(" AND    ROWNUM < 2");
//		PreparedStatement stmt = null;
//		try (Connection con = fctDatasource.getConnection();) {
//			stmt = con.prepareStatement(sb.toString());
//			stmt.setString(1, str);
//			ResultSet rs = stmt.executeQuery();
//			while (rs.next()) {
//				fctaltcodetyptyperowid = rs.getString("ROWID_OBJECT");
//			}
//		}
//		return fctaltcodetyptyperowid;
//
//	}

	private String getFacilityRKTSAltCodesRowid(String fctrowid, String typtyperowid) throws SQLException {
		String fctrktsaltcoderowid = "";
		StringBuilder sb = new StringBuilder();
		LOGGER.info("Richa change1");
		sb.append("select CODE from c_fct_alt_codes where HUB_STATE_IND= 1 AND FCT_ROWID=? and TYP_TYPE_ROWID=? AND ROWNUM < 2");
		//Richa Change added hubstate
		PreparedStatement stmt = null;
		try (Connection con = fctDatasource.getConnection();) {
			stmt = con.prepareStatement(sb.toString());
			stmt.setString(1, fctrowid);
			stmt.setString(2, typtyperowid);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				fctrktsaltcoderowid = rs.getString("CODE");
			}
		}
		return fctrktsaltcoderowid;

	}

	private String getFacilityGdaSiteTypeTypRowid(String opsfaciltytype) throws SQLException {
		String gdasitetyptyperowid = "";
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT ROWID_OBJECT ");
		sb.append(" FROM   C_TYP_TYPE");
		sb.append(" WHERE  HUB_STATE_IND           = 1");
		sb.append(" AND    CODE                   = (?)    ");
		sb.append(" AND    ROWNUM < 2");
		PreparedStatement stmt = null;
		try (Connection con = fctDatasource.getConnection();) {
			stmt = con.prepareStatement(sb.toString());
			stmt.setString(1, opsfaciltytype);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				gdasitetyptyperowid = rs.getString("ROWID_OBJECT");
			}
		}
		return gdasitetyptyperowid;

	}

	private GeoWriteBackVO getGeoWriteData(String geoid, String geocityid) throws SQLException {
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT  DISTINCT ");
		sb.append(" 'GDA_REL.SITE_IN_CITY' AS TYP_TYPE_CD, ");
		sb.append(" C_CTM_PSTL_ADDR.CITY_ROWID GDA_DFND_AREA_PRNT_ROWID, ");
		sb.append(" VALID_FROM_DT, VALID_THRU_DT");
		sb.append(" FROM C_FCT_ADDR_REL");
		sb.append(" INNER JOIN C_CTM_PSTL_ADDR ON C_CTM_PSTL_ADDR.ROWID_OBJECT = C_FCT_ADDR_REL.ADDR_ROWID");
		sb.append(" INNER JOIN C_ALT_CODE ON C_ALT_CODE.GDA_DFND_AREA_ROWID = C_CTM_PSTL_ADDR.CITY_ROWID");
		sb.append(" INNER JOIN C_FCT_ALT_CODES ON C_FCT_ALT_CODES.FCT_ROWID = C_FCT_ADDR_REL.FCT_ROWID");
		sb.append(
				" WHERE C_ALT_CODE.TYP_TYPE_ROWID = ( SELECT ROWID_OBJECT FROM C_TYP_TYPE WHERE CODE = 'ALT_CODE.GEOID' )");
		sb.append(
				" AND   C_FCT_ALT_CODES.TYP_TYPE_ROWID = ( SELECT ROWID_OBJECT FROM C_TYP_TYPE WHERE CODE = 'ALT_CODE.GEOID' )");
		sb.append(" ");
		sb.append(" AND   C_ALT_CODE.CODE = (?)");
		sb.append(" AND   C_FCT_ALT_CODES.CODE = (?)");
		sb.append(" AND ROWNUM < 2");
		GeoWriteBackVO geowritebackkdata = new GeoWriteBackVO();
		PreparedStatement stmt = null;
		try (Connection con = fctDatasource.getConnection();) {
			stmt = con.prepareStatement(sb.toString());
			stmt.setString(1, geocityid);
			stmt.setString(2, geoid);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				LOGGER.info("Type type code " + rs.getString(1));
				LOGGER.info("City Row id " + rs.getString(2));
				LOGGER.info("Valid from date  " + rs.getDate(3).toString());
				LOGGER.info("Valid thru date " + rs.getDate(4).toString());
				geowritebackkdata.setTyptypecode(rs.getString(1));
				geowritebackkdata.setAddressRowid(rs.getString(2));
				geowritebackkdata.setValidThruDate(rs.getDate(4));
				geowritebackkdata.setValidFromDate(rs.getDate(3));
			}
		}
		return geowritebackkdata;

	}

	private String getExistingGdaSiteRowid(String geocode) throws SQLException {
		String gdarowid = "";
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT C_GDA_SITE.ROWID_OBJECT ");
		sb.append(" FROM C_GDA_SITE ");
		sb.append(" INNER JOIN C_FCT_ALT_CODES ON C_FCT_ALT_CODES.FCT_ROWID = C_GDA_SITE.GDA_DFND_AREA_ROWID");
		sb.append(" WHERE TRIM(C_FCT_ALT_CODES.CODE) = TRIM(?) ");
		sb.append(" AND ROWNUM < 2");
		PreparedStatement stmt = null;
		try (Connection con = fctDatasource.getConnection();) {
			stmt = con.prepareStatement(sb.toString());
			stmt.setString(1, geocode);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				gdarowid = rs.getString(1);
			}
		}

		return gdarowid;
	}

	private String getSiteType(List<String> listgeotype) {
		String selectedgeotype = null;

		for (String str : listgeotype) {
			LOGGER.info("Evaluating Rank1");
			if (config.getString("RANK1").equals(str)) {
				LOGGER.info("RANK1 Type :" + config.getString("RANK1"));
				selectedgeotype = str;
				return selectedgeotype;
			}
		}
		for (String str : listgeotype) {
			LOGGER.info("Evaluating Rank2");
			if (config.getString("RANK2").equals(str)) {
				LOGGER.info("RANK2 Type :" + config.getString("RANK2"));
				selectedgeotype = str;
				return selectedgeotype;
			}
		}
		for (String str : listgeotype) {
			LOGGER.info("Evaluating Rank3");
			if (config.getString("RANK3").equals(str)) {
				LOGGER.info("RANK3 Type :" + config.getString("RANK3"));
				selectedgeotype = str;
				return selectedgeotype;
			}
		}
		for (String str : listgeotype) {
			LOGGER.info("Evaluating Rank4");
			if (config.getString("RANK4").equals(str)) {
				LOGGER.info("RANK4 Type :" + config.getString("RANK4"));
				selectedgeotype = str;
				return selectedgeotype;
			}
		}
		for (String str : listgeotype) {
			LOGGER.info("Evaluating Rank5");
			if (config.getString("RANK5").equals(str)) {
				LOGGER.info("RANK5 Type :" + config.getString("RANK5"));
				selectedgeotype = str;
				return selectedgeotype;
			}
		}
		for (String str : listgeotype) {
			LOGGER.info("Evaluating Rank6");
			if (config.getString("RANK6").equals(str)) {
				LOGGER.info("RANK6 Type :" + config.getString("RANK6"));
				selectedgeotype = str;
				return selectedgeotype;

			}
		}
		for (String str : listgeotype) {
			LOGGER.info("Evaluating Rank7");
			if (config.getString("RANK7").equals(str)) {
				LOGGER.info("RANK7 Type :" + config.getString("RANK7"));
				selectedgeotype = str;
				return selectedgeotype;
			}
		}
		return selectedgeotype;
	}

	protected String getGeoGDAAreaRowid(String geoid) throws SQLException {
		String rowid = null;
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT C_GDA_DFND_AREA.ROWID_OBJECT C_GDA_DFND_AREA_MDM_ID");
		sb.append(" FROM  C_GDA_DFND_AREA");
		sb.append(" INNER JOIN C_ALT_CODE ON ( C_ALT_CODE.GDA_DFND_AREA_ROWID = C_GDA_DFND_AREA.ROWID_OBJECT ");
		sb.append("                              AND C_GDA_DFND_AREA.TYP_TYPE_CD = 'GDA.SITE' ");
		sb.append("                                AND TRIM(C_ALT_CODE.CODE) = TRIM(?)");
		sb.append("                         )");
		sb.append(" INNER JOIN C_TYP_TYPE ON ( C_ALT_CODE.TYP_TYPE_ROWID = C_TYP_TYPE.ROWID_OBJECT ");
		sb.append("                            AND C_TYP_TYPE.CODE = 'ALT_CODE.GEOID' ");
		sb.append("                         )");
		sb.append(" WHERE C_GDA_DFND_AREA.HUB_STATE_IND = 1");
		sb.append(" AND   ROWNUM < 2");
		try (Connection con = fctDatasource.getConnection();) {
			PreparedStatement stmt = null;
			stmt = con.prepareStatement(sb.toString());
			stmt.setString(1, geoid);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				rowid = rs.getString(1);
			}
		}
		LOGGER.info("Geo GDA AREA ROWID : " + rowid);
		return rowid;

	}

	protected String getGeoGDAAreaRelRowid(String geoid, String cityid, String gdaarearowid) throws SQLException {
		String rowid = null;
		StringBuilder sb = new StringBuilder();
		sb.append("WITH GDA_PRNT_ROWID AS ");
		sb.append("( ");
		sb.append("SELECT  DISTINCT   ");
		sb.append("C_CTM_PSTL_ADDR.CITY_ROWID GDA_PRNT_MDM_ID ");
		sb.append("FROM C_FCT_ADDR_REL ");
		sb.append("INNER JOIN C_CTM_PSTL_ADDR ON C_CTM_PSTL_ADDR.ROWID_OBJECT = C_FCT_ADDR_REL.ADDR_ROWID ");
		sb.append("INNER JOIN C_ALT_CODE ON C_ALT_CODE.GDA_DFND_AREA_ROWID = C_CTM_PSTL_ADDR.CITY_ROWID ");
		sb.append("INNER JOIN C_FCT_ALT_CODES ON C_FCT_ALT_CODES.FCT_ROWID = C_FCT_ADDR_REL.FCT_ROWID ");
		sb.append(
				"WHERE C_ALT_CODE.TYP_TYPE_ROWID = ( SELECT ROWID_OBJECT FROM C_TYP_TYPE WHERE CODE = 'ALT_CODE.GEOID' ) ");
		sb.append(
				"AND   C_FCT_ALT_CODES.TYP_TYPE_ROWID = ( SELECT ROWID_OBJECT FROM C_TYP_TYPE WHERE CODE = 'ALT_CODE.GEOID' ) ");
		sb.append("AND   VALID_THRU_DT >= SYSDATE ");
		sb.append("AND   C_ALT_CODE.CODE = (?) ");
		sb.append("AND   C_FCT_ALT_CODES.CODE = (?) ");
		sb.append("AND ROWNUM < 2 ");
		sb.append(") ");
		sb.append("SELECT C_GDA_DFND_AREA_REL.ROWID_OBJECT C_GDA_REL_MDM_ID ");
		sb.append("FROM  C_GDA_DFND_AREA_REL ");
		sb.append(
				"INNER JOIN GDA_PRNT_ROWID ON TRIM(GDA_PRNT_ROWID.GDA_PRNT_MDM_ID) = TRIM(C_GDA_DFND_AREA_REL.GDA_DFND_AREA_PRNT_ROWID) ");
		sb.append("WHERE TYP_TYPE_CD = 'GDA_REL.SITE_IN_CITY' ");
		sb.append("AND   TRIM(C_GDA_DFND_AREA_REL.GDA_DFND_AREA_CHLD_ROWID) = TRIM(?) ");
		sb.append("AND   C_GDA_DFND_AREA_REL.HUB_STATE_IND = 1 ");
		sb.append("AND   ROWNUM < 2 ");
		try (Connection con = fctDatasource.getConnection();) {
			PreparedStatement stmt = null;
			stmt = con.prepareStatement(sb.toString());
			stmt.setString(1, cityid.trim());
			stmt.setString(2, geoid.trim());
			stmt.setString(3, gdaarearowid.trim());
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				LOGGER.info("Getting rel roiwd  " + gdaarearowid);
				rowid = rs.getString("C_GDA_REL_MDM_ID");
			}
		}
		LOGGER.info("GDA AREA REL ROWID  : " + rowid);
		return rowid;
	}

	protected void getAltCode(String gdaarearowid, Map<String, String> altcodemap,String facilityuncodecode1typetyprowid) throws SQLException {
		LOGGER.info("Inside getAltCode method..");
		StringBuilder sb = new StringBuilder();
		/*
		 * sb.append("SELECT ROWID_OBJECT, TYP_TYPE_ROWID, GDA_DFND_AREA_ROWID, CODE ");
		 * sb.append("FROM C_ALT_CODE ");
		 * sb.append("WHERE TRIM(GDA_DFND_AREA_ROWID) = TRIM(?) ");
		 * sb.append("AND   HUB_STATE_IND = 1 and TYP_TYPE_ROWID not in('"
		 * +facilityuncodecode1typetyprowid.trim()+"')");
		 */
		sb.append("SELECT ROWID_OBJECT, TYP_TYPE_ROWID, GDA_DFND_AREA_ROWID, CODE ");
		sb.append("FROM C_ALT_CODE ");
		sb.append("WHERE TRIM(GDA_DFND_AREA_ROWID) = TRIM(?) ");
		sb.append("AND   HUB_STATE_IND = 1");
		
		
		try (Connection con = fctDatasource.getConnection();) {
			PreparedStatement stmt = null;
			stmt = con.prepareStatement(sb.toString());
			stmt.setString(1, gdaarearowid);
			LOGGER.info("SQL to run Inside getAltCode method.."+sb.toString());
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				LOGGER.info("TYP TYPE ROWID : " + rs.getString(2) + "  ROWID : " + rs.getString(1));
				if(rs.getString(2).equalsIgnoreCase(facilityuncodecode1typetyprowid)) {
					LOGGER.info("Inside If getAltCode method.. Adding Key "+rs.getString(4) +" and Value ::"+rs.getString(1));
					altcodemap.put(rs.getString(4), rs.getString(1));
				}
				else{
					LOGGER.info("Inside Else if getAltCode method.. Adding Key "+rs.getString(2) +" and Value ::"+rs.getString(1));
					altcodemap.put(rs.getString(2), rs.getString(1));
				}
				
				
				}
			}
		
		return;
	}

	protected HashMap<String, String> getUnAltCode(String gdaarearowid,String facilityuncodecode1typetyprowid) throws SQLException {
		LOGGER.info("Inside getUnAltCode method..");
		HashMap<String, String> unAltcodemap= new HashMap<String, String>();
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT ROWID_OBJECT, TYP_TYPE_ROWID, GDA_DFND_AREA_ROWID, CODE ");
		sb.append("FROM C_ALT_CODE ");
		sb.append("WHERE TRIM(GDA_DFND_AREA_ROWID) = TRIM(?) ");
		sb.append("AND   HUB_STATE_IND = 1 and TYP_TYPE_ROWID in('"+facilityuncodecode1typetyprowid+"')");
		try (Connection con = fctDatasource.getConnection();) {
			PreparedStatement stmt = null;
			stmt = con.prepareStatement(sb.toString());
			stmt.setString(1, gdaarearowid);
			LOGGER.info("SQL to run Inside getUnAltCode method.."+sb.toString());
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				LOGGER.info("TYP TYPE ROWID : " + rs.getString(2) + "  ROWID : " + rs.getString(1) + "CODE :" +rs.getString(4));
				unAltcodemap.put(rs.getString(4), rs.getString(1));
				}
			}
		
		return unAltcodemap;
	}
	
	protected String getGDASiteRowid(String gdaarearowid, String sitetype) throws SQLException {
		StringBuilder sb = new StringBuilder();
		String rowid = null;
		sb.append("SELECT ROWID_OBJECT, TYP_TYPE_ROWID, GDA_DFND_AREA_ROWID ");
		sb.append("FROM  C_GDA_SITE ");
		sb.append("WHERE HUB_STATE_IND = 1 ");
		sb.append("AND   TRIM(GDA_DFND_AREA_ROWID) = TRIM(?) ");
		sb.append("AND   ROWNUM < 2 ");
		PreparedStatement stmt = null;
		try (Connection con = fctDatasource.getConnection();) {
			stmt = con.prepareStatement(sb.toString());
			stmt.setString(1, gdaarearowid);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				rowid = rs.getString(1);
			}
		}
		LOGGER.info("GDA Site ROWID  " + rowid);
		return rowid;
	}

	protected String getPhyLcnRowId(String gdaarearowid) throws SQLException {
		String rowid = null;
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT ROWID_OBJECT TMP_SITE_PHYS_LCN_MDM_ID ");
		sb.append("FROM C_TMP_SITE_PHYS_LCN ");
		sb.append("WHERE TRIM(GDA_SITE_ROWID) = TRIM(?) ");
		sb.append("AND   HUB_STATE_IND = 1 ");
		sb.append("AND   ROWNUM < 2 ");
		PreparedStatement stmt = null;
		try (Connection con = fctDatasource.getConnection();) {
			stmt = con.prepareStatement(sb.toString());
			stmt.setString(1, gdaarearowid);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				rowid = rs.getString(1);
			}
		}
		LOGGER.info("Temp Physical Site Location ROWID  " + rowid);
		return rowid;
	}
	
	protected String getExistingCityRowID(String childrowid) throws SQLException{
		String rowid = null;
		String sb = "SELECT ROWID_OBJECT, GDA_DFND_AREA_PRNT_ROWID " +
				"FROM   C_GDA_DFND_AREA_REL " +
				"WHERE  GDA_DFND_AREA_CHLD_ROWID = (?) AND ROWNUM < 2 AND TYP_TYPE_CD='GDA_REL.SITE_IN_CITY' AND HUB_STATE_IND=1";
		PreparedStatement stmt = null;
		try (Connection con = fctDatasource.getConnection();) {
			stmt = con.prepareStatement(sb);
			stmt.setString(1, childrowid);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				rowid = rs.getString("GDA_DFND_AREA_PRNT_ROWID");
			}
		}
		LOGGER.info("Existing City ROWID   " + rowid);
		return rowid;
	}

	protected String getCustomsLocCode(String fctrowid,String customsloctyptyperowid) throws SQLException{
		String customscode = null;
		String sb = "select CODE from C_FCT_ALT_CODES where HUB_STATE_IND=1 and TYP_TYPE_ROWID=? and FCT_ROWID=?";
		PreparedStatement stmt = null;
		try (Connection con = fctDatasource.getConnection();) {
			stmt = con.prepareStatement(sb);
			stmt.setString(1, customsloctyptyperowid);
			stmt.setString(2, fctrowid);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				customscode = rs.getString("CODE");
			}
		}
		LOGGER.info("Customs Loc COde   " + customscode);
		return customscode;
	}
	
	
	protected void logAllTypeFromConfig(CompositeConfiguration config) {
		LOGGER.info("#############LOGGING ALL TYPES AND RANKING ");
		LOGGER.info("####  FACILITY TYPES   #####");
		LOGGER.info(config.getString("FCT_TYPE.TERMINAL"));
		LOGGER.info(config.getString("FCT_TYPE.WATERWAY"));
		LOGGER.info(config.getString("FCT_TYPE.RAILHEAD"));
		LOGGER.info(config.getString("FCT_TYPE.BARGE_PIER"));
		LOGGER.info(config.getString("FCT_TYPE.TRUCKER"));
		LOGGER.info(config.getString("FCT_TYPE.CFS"));
		LOGGER.info(config.getString("FCT_TYPE.MAIN_N_REP"));
		LOGGER.info(config.getString("FCT_OPS_TYPE.ICD"));
		LOGGER.info(config.getString("FCT_TYPE.OffHIRE_DEP"));
		LOGGER.info(config.getString("FCT_TYPE.REEFER_DEP"));
		LOGGER.info(config.getString("FCT_TYPE.COLD_STORE"));
		LOGGER.info(config.getString("FCT_TYPE.CUST_POOL_F"));
		LOGGER.info(config.getString("FCT_TYPE.WAREHOUSE"));
		LOGGER.info(config.getString("FCT_TYPE.CTR_PROD"));
		LOGGER.info("####  SITE TYPES   #####");
		LOGGER.info(config.getString("SITE_TYPE.TMNL"));
		LOGGER.info(config.getString("SITE_TYPE.RAIL_TMNL"));
		LOGGER.info(config.getString("SITE_TYPE.BARGE_TMNL"));
		LOGGER.info(config.getString("SITE_TYPE.CONTR_FSTN"));
		LOGGER.info(config.getString("SITE_TYPE.DEPOT"));
		LOGGER.info(config.getString("SITE_TYPE.CUST_LOCN"));
		LOGGER.info(config.getString("SITE_TYPE.CTR._PRODN"));
		LOGGER.info("####  RANKING SITE TYPES IN DESCENDING ORDER #####");
		LOGGER.info(config.getString("RANK1"));
		LOGGER.info(config.getString("RANK2"));
		LOGGER.info(config.getString("RANK3"));
		LOGGER.info(config.getString("RANK4"));
		LOGGER.info(config.getString("RANK5"));
		LOGGER.info(config.getString("RANK6"));
		LOGGER.info(config.getString("RANK7"));
	}
	
	//Added Changes Below
	private String getCodeTypeForFacilitiesAltCodes(String fctrowid, String typtyperowid) throws SQLException {
		String altcodeRowid = "";
		LOGGER.info("inside getCodeTypeForFacilitiesAltCodes");
		LOGGER.info("TypTypeRowid is ::" +typtyperowid);
		LOGGER.info("FactRowid is ::" +fctrowid);
		
		StringBuilder sb = new StringBuilder();
		sb.append("select CODE from c_fct_alt_codes where FCT_ROWID=? and HUB_STATE_IND=1 AND TYP_TYPE_ROWID=? AND ROWNUM < 2");
		PreparedStatement stmt = null;
		try (Connection con = fctDatasource.getConnection();) {
			stmt = con.prepareStatement(sb.toString());
			stmt.setString(1, fctrowid);
			stmt.setString(2, typtyperowid);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				altcodeRowid = rs.getString("CODE");
				LOGGER.info("AltCode Added inside getCodeTypeForFacilitiesAltCodes is ::" +altcodeRowid);
			}
		}
		return altcodeRowid;

	}
	
	private List<String> getUnLookupFacilitiesAltCodes(String fctrowid, String typtyperowid) throws SQLException {
		List<String> altcodeRowid = new ArrayList<>();
		LOGGER.info("inside getUnLookupFacilitiesAltCodes");
		
		LOGGER.info("TypTypeRowid is ::" +typtyperowid);
		LOGGER.info("FactRowid is ::" +fctrowid);
		StringBuilder sb = new StringBuilder();
		sb.append("select CODE from c_fct_alt_codes where FCT_ROWID=? and HUB_STATE_IND=1 AND TYP_TYPE_ROWID=?");
		PreparedStatement stmt = null;
		try (Connection con = fctDatasource.getConnection();) {
			stmt = con.prepareStatement(sb.toString());
			stmt.setString(1, fctrowid);
			stmt.setString(2, typtyperowid);
			LOGGER.info("Query to run inside getUnLookupFacilitiesAltCodes is ::" +sb.toString());
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				altcodeRowid.add(rs.getString("CODE"));
			}
		}
		return altcodeRowid;

	}
	
	private List<String> getFactUnLkpDeletedCodes(String fctrowid,String facilityuncodecode1typetypcode) throws SQLException {
		List<String> altcodeRowid = new ArrayList<>();
		LOGGER.info("Inside method getFactAltDeletedCodes");
		LOGGER.info("GetCodeType for ::" +fctrowid);
		StringBuilder sb = new StringBuilder();
		sb.append("select CODE from c_fct_alt_codes where FCT_ROWID='"+fctrowid +"' and HUB_STATE_IND=-1  AND TYP_TYPE_ROWID like '"+facilityuncodecode1typetypcode+"%'");
		PreparedStatement stmt = null;
		try (Connection con = fctDatasource.getConnection();) {
			stmt = con.prepareStatement(sb.toString());
			LOGGER.info("Query inside getFactUnLkpDeletedCodes ::" +sb.toString());
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				LOGGER.info("Adding alt Code Type from deleted facility inside getFactUnLkpDeletedCodes ::" +rs.getString("CODE"));
				altcodeRowid.add(rs.getString("CODE"));
			}
		}
		return altcodeRowid;

	}

	private List<String> getupdatedUNLookupCodes(String fctrowid,String facilityuncodecode1typetypcode,
			String facilityuncodecode2typetypcode, String facilityuncodecodetypetypcode,String gdaAreaRowid) throws SQLException {
		List<String> altcodeRowid = new ArrayList<>();
		LOGGER.info("Inside method getupdatedUNLookupCodes");
		LOGGER.info("FacilityRowid for ::" +fctrowid);
		LOGGER.info("GdaArea Rowid for ::" +gdaAreaRowid);
		LOGGER.info("TYP_TYPE_CD is ::" +facilityuncodecode1typetypcode +" "+facilityuncodecode2typetypcode +" "+facilityuncodecodetypetypcode );
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT ROWID_XREF FROM C_ALT_CODE_XREF WHERE ROWID_OBJECT IN(SELECT ROWID_OBJECT FROM C_ALT_CODE where  "
				+ "TYP_TYPE_ROWID in ('"+facilityuncodecode1typetypcode+"','"+facilityuncodecode2typetypcode+"','"+facilityuncodecodetypetypcode+"') and "
				+ "GDA_DFND_AREA_ROWID ='"+gdaAreaRowid+"' AND HUB_STATE_IND=1 AND CODE " + 
				"NOT IN(SELECT CODE FROM C_FCT_ALT_CODES WHERE FCT_ROWID='"+fctrowid+"' AND TYP_TYPE_ROWID in ('"+facilityuncodecode1typetypcode+"','"+facilityuncodecode2typetypcode+"','"+facilityuncodecodetypetypcode+"')" +
				"))");
		PreparedStatement stmt = null;
		try (Connection con = fctDatasource.getConnection();) {
			stmt = con.prepareStatement(sb.toString());
			LOGGER.info("Query inside getupdatedUNLookupCodes ::" +sb.toString());
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				LOGGER.info("Adding alt Code Type from deleted facility inside getupdatedUNLookupCodes ::" +rs.getString("ROWID_XREF"));
				altcodeRowid.add(rs.getString("ROWID_XREF"));
			}
		}
		return altcodeRowid;

	}
	
	private List<String> getFactAltDeletedCodes(String fctrowid,String facilityuncodecode1typetypcode) throws SQLException {
		List<String> altcodeRowid = new ArrayList<>();
		LOGGER.info("Inside method getFactAltDeletedCodes");
		LOGGER.info("GetCodeType for ::" +fctrowid);
		StringBuilder sb = new StringBuilder();
		sb.append("select TYP_TYPE_ROWID from c_fct_alt_codes where FCT_ROWID='"+fctrowid +"' and HUB_STATE_IND=-1  AND TYP_TYPE_ROWID  not like '"+facilityuncodecode1typetypcode+"'");
		PreparedStatement stmt = null;
		try (Connection con = fctDatasource.getConnection();) {
			stmt = con.prepareStatement(sb.toString());
			LOGGER.info("Query inside getFactAltDeletedCodes ::" +sb.toString());
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				LOGGER.info("Adding alt Code Type from deleted facility inside getFactAltDeletedCodes ::" +rs.getString("TYP_TYPE_ROWID"));
				altcodeRowid.add(rs.getString("TYP_TYPE_ROWID"));
			}
		}
		return altcodeRowid;

	}

	//Richa Change to make list
	public List<String> getExistingGeoCode(GeoRowID geoRowid,String code,String fctRowid,String queryNumber)  throws SQLException {
		//String unCodeRowid=null;
		List<String> unCodeRowid = new ArrayList<>(); //Richa change
		try{
			LOGGER.info("UNCode Rowid for GeoRowid::" +geoRowid);
				LOGGER.info("UNCode Code for TypTypeRowid::" +code);
					String geoRowidObject="";
						if(geoRowid!=null) {
							geoRowidObject=geoRowid.getAltcodemap().get(code);
				 			LOGGER.info("geoRowidObject attempt 02::" +geoRowidObject); 
				 			//}
				 
				}
				LOGGER.info("C_ALT_CODE ROWID_OBJECT is ::" +geoRowidObject);
		StringBuilder sb = new StringBuilder();
//		sb.append("select ROWID_XREF from C_ALT_CODE_XREF where ROWID_OBJECT='"+geoRowidObject+"'"
	//			+ " and HUB_STATE_IND=1 AND CODE IN(SELECT CODE FROM C_FCT_ALT_CODES WHERE HUB_STATE_IND=-1 AND CODE='"+code+"')");
		if(queryNumber.equalsIgnoreCase("firstQuery")) {
			//Richa commented
			LOGGER.info("Richa change2");
			/*sb.append("select ROWID_XREF from C_ALT_CODE_XREF where "
					+ " HUB_STATE_IND=1 AND CODE IN(SELECT CODE FROM C_FCT_ALT_CODES WHERE HUB_STATE_IND=-1 AND FCT_ROWID='"
					+ fctRowid + "'  AND CODE='" + code + "')");*/
			sb.append("select A.rowid_xref from C_ALT_CODE_XREF A" +
			"INNER JOIN C_ALT_CODE B ON B.ROWID_OBJECT=A.ROWID_OBJECT AND B.HUB_STATE_IND =1 " +
							"INNER JOIN C_GDA_DFND_AREA C ON C.ROWID_OBJECT=B.GDA_DFND_AREA_ROWID AND TYP_TYPE_CD ='GDA.SITE' " +
			"INNER JOIN C_FCT_ALT_CODES D ON B.CODE = D.CODE and B.TYP_TYPE_ROWID = D.TYP_TYPE_ROWID WHERE "+
			"D.HUB_STATE_IND=-1 AND D.FCT_ROWID='" + fctRowid + "'  AND D.CODE='" + code + "'");
		}
		else {
			//Richa commented
			/*sb.append("select ROWID_XREF from C_ALT_CODE_XREF where "
					+ " HUB_STATE_IND=1 AND CODE IN(SELECT CODE FROM C_FCT_ALT_CODES WHERE HUB_STATE_IND=-1 AND FCT_ROWID='"
					+ fctRowid + "'  AND TYP_TYPE_ROWID='" + code + "')");*/
			LOGGER.info("Richa change3");
			sb.append("select A.rowid_xref from C_ALT_CODE_XREF A " +
					"INNER JOIN C_ALT_CODE B ON B.ROWID_OBJECT=A.ROWID_OBJECT AND B.HUB_STATE_IND =1 " +
					"INNER JOIN C_GDA_DFND_AREA C ON C.ROWID_OBJECT=B.GDA_DFND_AREA_ROWID AND TYP_TYPE_CD ='GDA.SITE' " +
					"INNER JOIN C_FCT_ALT_CODES D ON B.CODE = D.CODE and B.TYP_TYPE_ROWID = D.TYP_TYPE_ROWID WHERE "+
					"D.HUB_STATE_IND=-1 AND D.FCT_ROWID='" + fctRowid + "'  AND D.TYP_TYPE_ROWID='" + code + "'");
		}
			
			LOGGER.info("Query to get ROWID_XREF is ::" +sb.toString());
		PreparedStatement stmtement = null;
		try (Connection con = fctDatasource.getConnection();) {
			stmtement = con.prepareStatement(sb.toString());
			//stmtement.setString(1, geoRowidObject);
			ResultSet rs = stmtement.executeQuery();
			while (rs.next()) {
				LOGGER.info("ROWID_XREF is ::" +rs.getString("ROWID_XREF"));
				//unCodeRowid=rs.getString("ROWID_XREF");
				unCodeRowid.add(rs.getString("ROWID_XREF"));//Richa change
			}
		}
		return unCodeRowid;


	
}catch(Exception ex) {
	LOGGER.info("Error in getExistingUnCode method ::"+ex.getCause());
	LOGGER.info("Error in getExistingUnCode method ::"+ex.getMessage());
	LOGGER.info("Error in getExistingUnCode method ::"+ex.getLocalizedMessage());
	LOGGER.info("Error in getExistingUnCode method ::"+ex.getStackTrace());
	ex.printStackTrace();
}
		return unCodeRowid;
}
	
		//Added Above Changes
}
