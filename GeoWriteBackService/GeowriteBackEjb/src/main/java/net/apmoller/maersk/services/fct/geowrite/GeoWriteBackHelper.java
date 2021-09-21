package net.apmoller.maersk.services.fct.geowrite;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.annotation.Resource;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import org.apache.commons.configuration.CompositeConfiguration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.log4j.Logger;

import net.apmoller.maersk.services.fct.geowrite.messaging.PropUtil;
import net.apmoller.services.cmd.schemas.FacilityLifeCycleStatusEnum;
import net.apmoller.services.cmd.schemas.RetrieveFacilityResponse;

import com.siperian.sif.client.EjbSiperianClient;
import com.siperian.sif.client.SiperianClient;
import com.siperian.sif.message.Field;
import com.siperian.sif.message.Record;
import com.siperian.sif.message.RecordKey;
import com.siperian.sif.message.mrm.PutRequest;
import com.siperian.sif.message.mrm.PutResponse;
import com.siperian.sif.message.mrm.DeleteRequest;
import com.siperian.sif.message.mrm.DeleteResponse;
/**
 * Session Bean implementation class GeoWriteBackHelper
 *
 */
public class GeoWriteBackHelper {

	/**
	 * Default constructor.
	 */
	public GeoWriteBackHelper() {

	}

	/** The config. */
	private CompositeConfiguration config = null;
	
	
	/** The Constant LOGGER. */
	private static final Logger LOGGER = Logger.getLogger(GeoWriteBackHelper.class.getName());

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

	/**
	 * Gets the SIF client.
	 *
	 * @return the SIF client
	 */
	private SiperianClient getSIFClient() {
		this.initProperties();
		SiperianClient sifClient = null;
		Properties prop = new Properties();
		prop.setProperty("siperian-client.orsId", config.getString("siperian-client.orsId"));
		prop.setProperty("siperian-client.username", config.getString("siperian-client.username"));
		prop.setProperty("siperian-client.password", config.getString("siperian-client.password"));
		prop.setProperty("siperian-client.protocol", config.getString("siperian-client.protocol"));
		prop.setProperty("java.naming.provider.url", config.getString("java.naming.provider.url"));
		prop.setProperty("java.naming.security.principal", config.getString("java.naming.security.principal"));
		prop.setProperty("java.naming.security.credentials", config.getString("java.naming.security.credentials"));
		prop.setProperty("java.naming.factory.initial", config.getString("java.naming.factory.initial"));
		prop.setProperty("java.naming.factory.url.pkgs", config.getString("java.naming.factory.url.pkgs"));
		sifClient = EjbSiperianClient.newSiperianClient(prop);
		return sifClient;

	}

/*	public List<PutResponse> insertGeoData(RetrieveFacilityResponse response, GeoWriteBackVO data,
			String altcodetyptyperowid, String rkstaltcodetyprowid, String rktsaltcodetyprowid, String rktscode,
			String gdasitetyptyperowid, String altmodeltyptyperowid, String exisitngsiteid, GeoRowID georowid,
			String customsloccode, String customsloctyptyperowid) throws ParseException*/ 
	
	public List<PutResponse> insertGeoData(RetrieveFacilityResponse response, GeoWriteBackVO data,
			String altcodetyptyperowid, String rkstaltcodetyprowid, String rktsaltcodetyprowid, String rktscode,
			String gdasitetyptyperowid, String altmodeltyptyperowid, String exisitngsiteid, GeoRowID georowid,
			String customsloccode, String customsloctyptyperowid, String facilityuncodecodetypetyprowid,
			String facilityuncodecodetypetypcode, String facilityuncodecode2typetyprowid,
			String facilityuncodecode2typetypcode,
			HashMap<String, String> unCodesToInsert) throws ParseException,SQLException{
		
		List<PutResponse> responselist = new ArrayList<PutResponse>();
		LOGGER.info("##################GEOWRiTEBACK PUT STARTS##################");
		LOGGER.info("Doing SIF Put Operation on GDA DNFN Area");
		LOGGER.info("==========================================");
		PutResponse response1 = this.saveOrUpdate(this.getGdaDfndAreaPutRequest(response, data, georowid));
		LOGGER.info("Response of the Put on GDA DFND Area " + response1.getActionType());
		LOGGER.info("Row iD of the Put on GDA DFND Area " + response1.getRecordKey().getRowid());
		LOGGER.info("==========================================");

		LOGGER.info("Doing SIF Put Operation on GDA DNFN Area Rel");
		LOGGER.info("==========================================");
		PutResponse response2 = this.saveOrUpdate(
				this.getGdaDfndAreaRelPutRequest(response, data, response1.getRecordKey().getRowid(), georowid));
		LOGGER.info("Response of the Put on GDA DFND Area Rel " + response2.getActionType());
		LOGGER.info("Row iD of the Put on GDA DFND Area Rel " + response2.getRecordKey().getRowid());
		LOGGER.info("==========================================");

		LOGGER.info("Doing SIF Put Operation on Alt Code GEOID ");
		LOGGER.info("==========================================");
		PutResponse response3 = this.saveOrUpdate(this.getAltCodePutRequest(response, data, altcodetyptyperowid,
				response1.getRecordKey().getRowid(), georowid));
		LOGGER.info("Response of the Put on GEOID Alt Code " + response3.getActionType());
		LOGGER.info("Row iD of the Put on GEOID Alt Code " + response3.getRecordKey().getRowid());
		LOGGER.info("==========================================");

		if (response.getFacility().getFacilityIDs().getFacilityRKSTCode() != null) {
			LOGGER.info("Doing SIF Put Operation on Alt Code RKST");
			LOGGER.info("==========================================");
			PutResponse response31 = this.saveOrUpdate(this.getRKSTAltCodePutRequest(response, data,
					rkstaltcodetyprowid, response1.getRecordKey().getRowid(), georowid));
			LOGGER.info("Response of the Put on RKST  Alt Code " + response31.getActionType());
			LOGGER.info("Row iD of the Put on RKST Alt Code " + response31.getRecordKey().getRowid());
			LOGGER.info("Doing SIF Put Operation on Alt Code Model");
			LOGGER.info("==========================================");
			PutResponse response32 = this.saveOrUpdate(this.getRKSTAltCodeModelPutRequest(response, data,
					altmodeltyptyperowid, response1.getRecordKey().getRowid(), georowid));
			LOGGER.info("Response of the Put on RKST  Alt Code Model" + response32.getActionType());
			LOGGER.info("Row iD of the Put on RKST Alt Code  Model" + response32.getRecordKey().getRowid());
			LOGGER.info("==========================================");
		}

		if (!rktscode.trim().equals("")) {
			LOGGER.info("Doing SIF Put Operation on Alt Code RKTS");
			LOGGER.info("==========================================");
			PutResponse response32 = this.saveOrUpdate(this.getRKTSAltCodePutRequest(response, rktscode, data,
					rktsaltcodetyprowid, response1.getRecordKey().getRowid(), georowid));
			LOGGER.info("Response of the Put on RKTS Alt Code " + response32.getActionType());
			LOGGER.info("Row iD of the Put on RKTS Alt Code " + response32.getRecordKey().getRowid());
			LOGGER.info("==========================================");
		}

		if (customsloccode != null && !customsloccode.equals("")) {
			LOGGER.info("Doing SIF Put Operation on GDA ALT CODE Customs Location");
			LOGGER.info("==========================================");
			PutResponse response33 = this.saveOrUpdate(this.getCustomsLocPutRequest(response, customsloccode, data, customsloctyptyperowid, response1.getRecordKey().getRowid(), georowid));
			LOGGER.info("Response of the Put on GDA Site " + response33.getActionType());
			LOGGER.info("Row iD of the Put on GDA Site  " + response33.getRecordKey().getRowid());
			LOGGER.info("==========================================");
		}

		//Added Changes Below-Anil
		
		//First for UNLOC
		if (!facilityuncodecodetypetypcode.trim().equals("")) {
			LOGGER.info("Doing SIF Put Operation on Alt Code UN LOC");
			LOGGER.info("==========================================");
			PutResponse response34 = this.saveOrUpdate(this.getRKTSAltCodePutRequest(response, facilityuncodecodetypetypcode, data,
					facilityuncodecodetypetyprowid, response1.getRecordKey().getRowid(), georowid));
			LOGGER.info("Response of the Put on  UN LOC Alt Code " + response34.getActionType());
			LOGGER.info("Row iD of the Put on  UN LOC Alt Code " + response34.getRecordKey().getRowid());
			LOGGER.info("==========================================");
		}
		// Second for UN Return 	
		if (!facilityuncodecode2typetypcode.trim().equals("")) {
			LOGGER.info("Doing SIF Put Operation on Alt Code UN Return");
			LOGGER.info("==========================================");
			PutResponse response35 = this.saveOrUpdate(this.getRKTSAltCodePutRequest(response, facilityuncodecode2typetypcode, data,
					facilityuncodecode2typetyprowid, response1.getRecordKey().getRowid(), georowid));
			LOGGER.info("Response of the Put on  UN Return Alt Code " + response35.getActionType());
			LOGGER.info("Row iD of the Put on  UN Return Alt Code " + response35.getRecordKey().getRowid());
			LOGGER.info("==========================================");
		}
		
		//Third for UN Lookup 
		if (!unCodesToInsert.isEmpty()) {
			LOGGER.info("unCodesToInsert isn't empty");
			for (Map.Entry<String,String> entry : unCodesToInsert.entrySet()) {
				LOGGER.info("Doing SIF Put Operation on Alt Code UNCode "+entry.getKey() +"  And Code Type is "+entry.getValue());
			LOGGER.info("==========================================");
			PutResponse response36 = this.saveOrUpdate(this.getFactCodesAltCodePutRequest(response, entry.getKey(), data,
					entry.getValue(), response1.getRecordKey().getRowid(), georowid));
			LOGGER.info("Response of the Put on UNCode  " + response36.getActionType());
			LOGGER.info("Row iD of the Put on UNCode  " + response36.getRecordKey().getRowid());
			LOGGER.info("==========================================");
			}
		}
		
		//Added Changes Above-Anil
		LOGGER.info("Doing SIF Put Operation on GDA Site");
		LOGGER.info("==========================================");
		PutResponse response4 = this.saveOrUpdate(this.getGdaSitePutRequest(response, data, gdasitetyptyperowid,
				response1.getRecordKey().getRowid(), georowid));
		LOGGER.info("Response of the Put on GDA Site " + response4.getActionType());
		LOGGER.info("Row iD of the Put on GDA Site  " + response4.getRecordKey().getRowid());
		LOGGER.info("==========================================");

		LOGGER.info("Doing SIF Put Operation on Temp Site");
		LOGGER.info("==========================================");
		PutResponse response5 = this.saveOrUpdate(
				this.getTmpSitePhyLclPutRequest(response, data, response1.getRecordKey().getRowid(), georowid));
		LOGGER.info("Response of the Put on Temp Site " + response5.getActionType());
		LOGGER.info("Row iD of the Put on Temp Site " + response5.getRecordKey().getRowid());
		LOGGER.info("==========================================");

		responselist.add(response1);
		responselist.add(response2);
		responselist.add(response3);
		responselist.add(response5);

		return responselist;
	}

	public PutRequest getGdaDfndAreaPutRequest(RetrieveFacilityResponse response, GeoWriteBackVO data,
			GeoRowID georowid) throws ParseException {
		FacilityLifeCycleStatusEnum flag = response.getFacility().getFacilityLifecycleStatus();
		LOGGER.info("Creating Gda DFND Area Put request");
		PutRequest wtcspartyputRequest = new PutRequest();
		RecordKey recordkey = new RecordKey();
		Record record = new Record();
		record.setSiperianObjectUid("BASE_OBJECT.C_GDA_DFND_AREA");

		if (response.getFacility().getFacilityName().length() <= 35) {
			record.setField(new Field("NAME", response.getFacility().getFacilityName()));
		} else {
			LOGGER.info("TRIMMING facilityname " + response.getFacility().getFacilityName().substring(0, 35));
			String facilityname = response.getFacility().getFacilityName().substring(0, 35);
			record.setField(new Field("NAME", facilityname));
		}

		record.setField(new Field("TYP_TYPE_CD", "GDA.SITE"));

		if (response.getFacilityAddress().getLatitude() != null) {
			record.setField(new Field("LAT_GEOSPTL", response.getFacilityAddress().getLatitude()));
		}

		if (response.getFacilityAddress().getLongitude() != null) {
			record.setField(new Field("LNG_GEOSPTL", response.getFacilityAddress().getLongitude()));
		}

		SimpleDateFormat form = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
		GregorianCalendar gc1 = response.getFacility().getFacilityAuditData().getCreationDate().toGregorianCalendar();
		String validfromdate = form.format(gc1.getTime());

		LOGGER.info("VALID_FROM_DT date for put call" + validfromdate);
		record.setField(new Field("VALID_FROM_DT", validfromdate));

		if (response.getFacility().getFacilityLifecycleStatus().value().equalsIgnoreCase("I")) {
			// Calendar cal = Calendar.getInstance();
			// cal.add(Calendar.DATE, -1);
			Date validthrudt = new Date();
			String validthrudtstring = form.format(validthrudt);
			LOGGER.info("VALID_THRU_DT date for put call for inactive facility " + validthrudtstring);
			record.setField(new Field("VALID_THRU_DT", validthrudtstring));
		} else {
			LOGGER.info("VALID_THRU_DT date for put call for Non inactive facility 31-12-9999 00:00:00");
			record.setField(new Field("VALID_THRU_DT", "31-12-9999 00:00:00"));
		}

		if (flag.equals(FacilityLifeCycleStatusEnum.A)) {
			LOGGER.info("GEO Translating to " + flag + " -> Y");
			record.setField(new Field("ACTIVE_FLAG", 'Y'));
		} else if (flag.equals(FacilityLifeCycleStatusEnum.I) || flag.equals(FacilityLifeCycleStatusEnum.P)) {
			LOGGER.info("GEO Translating to " + flag + " -> N");
			record.setField(new Field("ACTIVE_FLAG", 'N'));
		}

		// 3. check recordKey for empty strings, set to null if found
		if ("".equals(recordkey.getRowid())) {
			recordkey.setRowid(null);
		}
		if ("".equals(recordkey.getSourceKey())) {
			recordkey.setSourceKey(null);
		}
		if (georowid.getGdaAreaRowid() != null) {
			LOGGER.info("Setting Existing GEO GDA AREA ROWID  " + georowid.getGdaAreaRowid());
			recordkey.setRowid(georowid.getGdaAreaRowid());
			recordkey.setSourceKey("GWB:" + georowid.getGdaAreaRowid());
			wtcspartyputRequest.setGenerateSourceKey(false);
		} else {
			wtcspartyputRequest.setGenerateSourceKey(true);
		}
		recordkey.setSystemName("MDO");
		wtcspartyputRequest.setRecordKey(recordkey);
		wtcspartyputRequest.setRecord(record);

		return wtcspartyputRequest;
	}

	public PutRequest getGdaDfndAreaRelPutRequest(RetrieveFacilityResponse response, GeoWriteBackVO data, String rowid,
			GeoRowID georowid) throws ParseException {
		LOGGER.info("Creating Gda DFND Area Rel Put request");
		PutRequest wtcspartyputRequest = new PutRequest();
		RecordKey recordkey = new RecordKey();
		Record record = new Record();
		record.setSiperianObjectUid("BASE_OBJECT.C_GDA_DFND_AREA_REL");
		LOGGER.info("Type type code " + data.getTyptypecode());
		record.setField(new Field("TYP_TYPE_CD", data.getTyptypecode()));
		LOGGER.info("Child rowid  " + rowid);
		record.setField(new Field("GDA_DFND_AREA_CHLD_ROWID", rowid));
		LOGGER.info("Parent rowid  " + data.getAddressRowid());
		record.setField(new Field("GDA_DFND_AREA_PRNT_ROWID", data.getAddressRowid()));

		SimpleDateFormat form = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
		LOGGER.info("Valid From Date  " + data.getValidFromDate());
		String sqlstartdate = form.format(data.getValidFromDate());
		LOGGER.info("Valid thru Date  " + data.getValidThruDate());
		String sqlenddate = form.format(data.getValidThruDate());
		record.setField(new Field("VALID_FROM_DT", sqlstartdate));
		if (data.getValidThruDate() != null) {
			record.setField(new Field("VALID_THRU_DT", sqlenddate));
		} else {
			record.setField(new Field("VALID_THRU_DT", "31-12-9999 00:00:00"));
		}
		// 3. check recordKey for empty strings, set to null if found
		if ("".equals(recordkey.getRowid())) {
			recordkey.setRowid(null);
		}
		if ("".equals(recordkey.getSourceKey())) {
			recordkey.setSourceKey(null);
		}
		if (georowid.getGdaAreaRelRowid() != null) {
			LOGGER.info("Setting Existing GEO GDA AREA REL ROWID  " + georowid.getGdaAreaRelRowid());
			recordkey.setRowid(georowid.getGdaAreaRelRowid());
			recordkey.setSourceKey("GWB:" + georowid.getGdaAreaRelRowid());
			wtcspartyputRequest.setGenerateSourceKey(false);
		} else {
			wtcspartyputRequest.setGenerateSourceKey(true);
		}
		recordkey.setSystemName("MDO");
		wtcspartyputRequest.setRecordKey(recordkey);
		wtcspartyputRequest.setRecord(record);

		return wtcspartyputRequest;
	}

	public PutRequest getAltCodePutRequest(RetrieveFacilityResponse response, GeoWriteBackVO data,
			String altcodetyptyperowid, String rowid, GeoRowID georowid) {
		LOGGER.info("Creating GEO ID Alt Code Put request");
		PutRequest wtcspartyputRequest = new PutRequest();
		RecordKey recordkey = new RecordKey();
		Record record = new Record();
		record.setSiperianObjectUid("BASE_OBJECT.C_ALT_CODE");
		record.setField(new Field("TYP_TYPE_ROWID", altcodetyptyperowid));
		record.setField(new Field("GDA_DFND_AREA_ROWID", rowid));
		record.setField(new Field("CODE", response.getFacility().getFacilityIDs().getFacilityGEOId()));

		// 3. check recordKey for empty strings, set to null if found
		if ("".equals(recordkey.getRowid())) {
			recordkey.setRowid(null);
		}
		if ("".equals(recordkey.getSourceKey())) {
			recordkey.setSourceKey(null);
		}
		if (georowid.getAltcodemap().get(altcodetyptyperowid) != null) {
			LOGGER.info(
					"Setting Existing Alt Code Rowid for GEOID " + georowid.getAltcodemap().get(altcodetyptyperowid));
			recordkey.setRowid(georowid.getAltcodemap().get(altcodetyptyperowid));
			recordkey.setSourceKey("GWB:" + georowid.getAltcodemap().get(altcodetyptyperowid));
			wtcspartyputRequest.setGenerateSourceKey(false);
		} else {
			wtcspartyputRequest.setGenerateSourceKey(true);
		}
		recordkey.setSystemName("MDO");
		wtcspartyputRequest.setRecordKey(recordkey);
		wtcspartyputRequest.setRecord(record);
		return wtcspartyputRequest;
	}

	public PutRequest getRKSTAltCodePutRequest(RetrieveFacilityResponse response, GeoWriteBackVO data,
			String rkstaltcodetyptyperowid, String rowid, GeoRowID georowid) {
		LOGGER.info("Creating RKST Alt Code Put request");
		PutRequest wtcspartyputRequest = new PutRequest();
		RecordKey recordkey = new RecordKey();
		Record record = new Record();
		record.setSiperianObjectUid("BASE_OBJECT.C_ALT_CODE");
		record.setField(new Field("TYP_TYPE_ROWID", rkstaltcodetyptyperowid));
		record.setField(new Field("GDA_DFND_AREA_ROWID", rowid));
		record.setField(new Field("CODE", response.getFacility().getFacilityIDs().getFacilityRKSTCode()));

		// 3. check recordKey for empty strings, set to null if found
		if ("".equals(recordkey.getRowid())) {
			recordkey.setRowid(null);
		}
		if ("".equals(recordkey.getSourceKey())) {
			recordkey.setSourceKey(null);
		}
		if (georowid.getAltcodemap().get(rkstaltcodetyptyperowid) != null) {
			LOGGER.info("Setting Existing Alt Code Rowid  " + georowid.getAltcodemap().get(rkstaltcodetyptyperowid));
			recordkey.setRowid(georowid.getAltcodemap().get(rkstaltcodetyptyperowid));
			recordkey.setSourceKey("GWB:" + georowid.getAltcodemap().get(rkstaltcodetyptyperowid));
			wtcspartyputRequest.setGenerateSourceKey(false);
		} else {
			wtcspartyputRequest.setGenerateSourceKey(true);
		}
		recordkey.setSystemName("MDO");
		wtcspartyputRequest.setRecordKey(recordkey);
		wtcspartyputRequest.setRecord(record);

		return wtcspartyputRequest;
	}

	public PutRequest getRKSTAltCodeModelPutRequest(RetrieveFacilityResponse response, GeoWriteBackVO data,
			String rkstaltcodemodeltyptyperowid, String rowid, GeoRowID georowid) {
		LOGGER.info("Creating RKST Alt Code Model Put request");
		PutRequest wtcspartyputRequest = new PutRequest();
		RecordKey recordkey = new RecordKey();
		Record record = new Record();
		record.setSiperianObjectUid("BASE_OBJECT.C_ALT_CODE");
		record.setField(new Field("TYP_TYPE_ROWID", rkstaltcodemodeltyptyperowid));
		record.setField(new Field("GDA_DFND_AREA_ROWID", rowid));
		record.setField(
				new Field("CODE", response.getFacility().getFacilityIDs().getFacilityRKSTCode().substring(0, 5)));

		// 3. check recordKey for empty strings, set to null if found
		if ("".equals(recordkey.getRowid())) {
			recordkey.setRowid(null);
		}
		if ("".equals(recordkey.getSourceKey())) {
			recordkey.setSourceKey(null);
		}
		if (georowid.getAltcodemap().get(rkstaltcodemodeltyptyperowid) != null) {
			LOGGER.info(
					"Setting Existing Alt Code Rowid  " + georowid.getAltcodemap().get(rkstaltcodemodeltyptyperowid));
			recordkey.setRowid(georowid.getAltcodemap().get(rkstaltcodemodeltyptyperowid));
			recordkey.setSourceKey("GWB:" + georowid.getAltcodemap().get(rkstaltcodemodeltyptyperowid));
			wtcspartyputRequest.setGenerateSourceKey(false);
		} else {
			wtcspartyputRequest.setGenerateSourceKey(true);
		}
		recordkey.setSystemName("MDO");
		wtcspartyputRequest.setRecordKey(recordkey);
		wtcspartyputRequest.setRecord(record);

		return wtcspartyputRequest;
	}

	public PutRequest getRKTSAltCodePutRequest(RetrieveFacilityResponse response, String RKTSCode, GeoWriteBackVO data,
			String rkstaltcodetyptyperowid, String rowid, GeoRowID georowid) {
		LOGGER.info("Creating RKTS Alt Code Put request");
		PutRequest wtcspartyputRequest = new PutRequest();
		RecordKey recordkey = new RecordKey();
		Record record = new Record();
		record.setSiperianObjectUid("BASE_OBJECT.C_ALT_CODE");
		record.setField(new Field("TYP_TYPE_ROWID", rkstaltcodetyptyperowid));
		record.setField(new Field("GDA_DFND_AREA_ROWID", rowid));
		record.setField(new Field("CODE", RKTSCode));

		// 3. check recordKey for empty strings, set to null if found
		if ("".equals(recordkey.getRowid())) {
			recordkey.setRowid(null);
		}
		if ("".equals(recordkey.getSourceKey())) {
			recordkey.setSourceKey(null);
		}
		if (georowid.getAltcodemap().get(rkstaltcodetyptyperowid) != null) {
			LOGGER.info("Setting Existing Alt Code Rowid  " + georowid.getAltcodemap().get(rkstaltcodetyptyperowid));
			recordkey.setRowid(georowid.getAltcodemap().get(rkstaltcodetyptyperowid));
			recordkey.setSourceKey("GWB:" + georowid.getAltcodemap().get(rkstaltcodetyptyperowid));
			wtcspartyputRequest.setGenerateSourceKey(false);
		} else {
			wtcspartyputRequest.setGenerateSourceKey(true);
		}
		recordkey.setSystemName("MDO");
		wtcspartyputRequest.setRecordKey(recordkey);
		wtcspartyputRequest.setRecord(record);
		return wtcspartyputRequest;
	}

	public PutRequest getCustomsLocPutRequest(RetrieveFacilityResponse response, String customsloccode,
			GeoWriteBackVO data, String customsloccodetyptyperowid, String rowid, GeoRowID georowid) {
		LOGGER.info("Creating Customs  Alt Code Put request");
		PutRequest wtcspartyputRequest = new PutRequest();
		RecordKey recordkey = new RecordKey();
		Record record = new Record();
		record.setSiperianObjectUid("BASE_OBJECT.C_ALT_CODE");
		record.setField(new Field("TYP_TYPE_ROWID", customsloccodetyptyperowid));
		record.setField(new Field("GDA_DFND_AREA_ROWID", rowid));
		record.setField(new Field("CODE", customsloccode));

		// 3. check recordKey for empty strings, set to null if found
		if ("".equals(recordkey.getRowid())) {
			recordkey.setRowid(null);
		}
		if ("".equals(recordkey.getSourceKey())) {
			recordkey.setSourceKey(null);
		}
		if (georowid.getAltcodemap().get(customsloccodetyptyperowid) != null) {
			LOGGER.info("Setting Existing Alt Code Rowid  " + georowid.getAltcodemap().get(customsloccodetyptyperowid));
			recordkey.setRowid(georowid.getAltcodemap().get(customsloccodetyptyperowid));
			recordkey.setSourceKey("GWB:" + georowid.getAltcodemap().get(customsloccodetyptyperowid));
			wtcspartyputRequest.setGenerateSourceKey(false);
		} else {
			wtcspartyputRequest.setGenerateSourceKey(true);
		}
		recordkey.setSystemName("MDO");
		wtcspartyputRequest.setRecordKey(recordkey);
		wtcspartyputRequest.setRecord(record);
		return wtcspartyputRequest;
	}

	public PutRequest getGdaSitePutRequest(RetrieveFacilityResponse response, GeoWriteBackVO data,
			String fctgdasitetyptyperowid, String rowid, GeoRowID georowid) {
		LOGGER.info("Creating GDA SITE Put request");
		PutRequest wtcspartyputRequest = new PutRequest();
		LOGGER.info("Facility Gda Site Typ type Row iD " + fctgdasitetyptyperowid);

		RecordKey recordkey = new RecordKey();
		Record record = new Record();
		record.setSiperianObjectUid("BASE_OBJECT.C_GDA_SITE");
		record.setField(new Field("GDA_DFND_AREA_ROWID", rowid));
		record.setField(new Field("TYP_TYPE_ROWID", fctgdasitetyptyperowid));
		record.setField(new Field("GPS_FLAG", response.getFacilityAttributes().isGPSFlag()));
		record.setField(new Field("GSM_FLAG", response.getFacilityAttributes().isGSMFlag()));
		// 3. check recordKey for empty strings, set to null if found
		if ("".equals(recordkey.getRowid())) {
			recordkey.setRowid(null);
		}
		if ("".equals(recordkey.getSourceKey())) {
			recordkey.setSourceKey(null);
		}
		if (georowid.getGdaSiteRowid() != null) {
			LOGGER.info("Setting Existing Site Rowid rowid for update " + georowid.getGdaSiteRowid());
			recordkey.setRowid(georowid.getGdaSiteRowid());
			recordkey.setSourceKey("GWB:" + georowid.getGdaSiteRowid());
			wtcspartyputRequest.setGenerateSourceKey(false);
		} else {
			wtcspartyputRequest.setGenerateSourceKey(true);
		}
		recordkey.setSystemName("MDO");
		wtcspartyputRequest.setRecordKey(recordkey);
		wtcspartyputRequest.setRecord(record);
		return wtcspartyputRequest;
	}

	public PutRequest getTmpSitePhyLclPutRequest(RetrieveFacilityResponse response, GeoWriteBackVO data, String rowid,
			GeoRowID georowid) {
		LOGGER.info("Creating Temp SITE Put request");
		PutRequest wtcspartyputRequest = new PutRequest();
		RecordKey recordkey = new RecordKey();
		Record record = new Record();
		record.setSiperianObjectUid("BASE_OBJECT.C_TMP_SITE_PHYS_LCN");
		record.setField(new Field("GDA_SITE_ROWID", rowid));

		if (response.getFacilityAddress().getHouseNo() != null) {
			if (response.getFacilityAddress().getHouseNo().length() <= 10) {
				record.setField(new Field("STREET_NO", response.getFacilityAddress().getHouseNo()));
			} else {
				LOGGER.info("TRIMMING HOUSE NU " + response.getFacilityAddress().getHouseNo().substring(0, 10));
				String housenum = response.getFacilityAddress().getHouseNo().substring(0, 10);
				record.setField(new Field("STREET_NO", housenum));
			}
		}

		if (response.getFacilityAddress().getStreetName() != null) {

			if (response.getFacilityAddress().getStreetName().length() <= 40) {
				record.setField(new Field("ADDR_LN_1", response.getFacilityAddress().getStreetName()));
			} else {
				LOGGER.info("TRIMMING streetnm " + response.getFacilityAddress().getStreetName().substring(0, 40));
				String streetnm = response.getFacilityAddress().getStreetName().substring(0, 40);
				record.setField(new Field("ADDR_LN_1", streetnm));
			}
		}

		if (response.getFacilityAddress().getBuilding() != null) {

			if (response.getFacilityAddress().getBuilding().length() <= 40) {
				record.setField(new Field("ADDR_LN_2", response.getFacilityAddress().getBuilding()));
			} else {
				LOGGER.info("TRIMMING BUILIDNG " + response.getFacilityAddress().getBuilding().substring(0, 40));
				String building = response.getFacilityAddress().getBuilding().substring(0, 40);
				record.setField(new Field("ADDR_LN_2", building));
			}
		}

		if (response.getFacilityAddress().getSuburb() != null) {
			if (response.getFacilityAddress().getSuburb().length() <= 40) {
				record.setField(new Field("ADDR_LN_3", response.getFacilityAddress().getSuburb()));
			} else {
				LOGGER.info("TRIMMING suburb " + response.getFacilityAddress().getSuburb().substring(0, 40));
				String suburb = response.getFacilityAddress().getSuburb().substring(0, 40);
				record.setField(new Field("ADDR_LN_3", suburb));
			}

		}
		if (response.getFacilityAddress().getPostalCode() != null) {
			if (response.getFacilityAddress().getPostalCode().length() <= 13) {
				record.setField(new Field("PSTL_CD", response.getFacilityAddress().getPostalCode()));
			} else {
				LOGGER.info("TRIMMING PSTCODE " + response.getFacilityAddress().getPostalCode().substring(0, 13));
				String pstcd = response.getFacilityAddress().getPostalCode().substring(0, 13);
				record.setField(new Field("PSTL_CD", pstcd));
			}
		}
		// 3. check recordKey for empty strings, set to null if found
		if ("".equals(recordkey.getRowid())) {
			recordkey.setRowid(null);
		}
		if ("".equals(recordkey.getSourceKey())) {
			recordkey.setSourceKey(null);
		}
		if (georowid.getTmpPhylcnRowid() != null) {
			LOGGER.info("Setting Existing Temp Phy location rowid for update " + georowid.getTmpPhylcnRowid());
			recordkey.setRowid(georowid.getTmpPhylcnRowid());
			recordkey.setSourceKey("GWB:" + georowid.getTmpPhylcnRowid());
			wtcspartyputRequest.setGenerateSourceKey(false);
		} else {
			wtcspartyputRequest.setGenerateSourceKey(true);
		}
		recordkey.setSystemName("MDO");
		wtcspartyputRequest.setRecordKey(recordkey);
		wtcspartyputRequest.setRecord(record);

		return wtcspartyputRequest;
	}

	/**
	 * Save or update party role.
	 *
	 * @param * the party role cp request
	 * @return the cleanse put response
	 */
	public PutResponse saveOrUpdate(PutRequest wtcsrequest) {
		LOGGER.info("Calling SIF Put request  on ORS ID  " + wtcsrequest.getOrsId());
		return (PutResponse) getSIFClient().process(wtcsrequest);
	}
//Added Below Changes-Anil
	public PutRequest getFactCodesAltCodePutRequest(RetrieveFacilityResponse response, String altCodeType, GeoWriteBackVO data,
			String altcodetyptyperowid, String rowid, GeoRowID georowid) {
		LOGGER.info("Creating  Alt Code Put request ::"+altCodeType);
		LOGGER.info("Creating  Alt Code For UN Code Rowid ::"+altCodeType);
		LOGGER.info("Creating  Alt Code For UN Code  ::"+altcodetyptyperowid);
		PutRequest wtcspartyputRequest = new PutRequest();
		RecordKey recordkey = new RecordKey();
		Record record = new Record();
		record.setSiperianObjectUid("BASE_OBJECT.C_ALT_CODE");
		record.setField(new Field("TYP_TYPE_ROWID", altcodetyptyperowid));
		record.setField(new Field("GDA_DFND_AREA_ROWID", rowid));
		record.setField(new Field("CODE", altCodeType));

		// 3. check recordKey for empty strings, set to null if found
		if ("".equals(recordkey.getRowid())) {
			recordkey.setRowid(null);
		}
		if ("".equals(recordkey.getSourceKey())) {
			recordkey.setSourceKey(null);
		}
		
			if (georowid.getAltcodemap().get(altCodeType) != null) {
			LOGGER.info("Setting Existing Alt Code Rowid  " + georowid.getAltcodemap().get(altCodeType));
			recordkey.setRowid(georowid.getAltcodemap().get(altCodeType));
			recordkey.setSourceKey("GWB:" + georowid.getAltcodemap().get(altCodeType));
			wtcspartyputRequest.setGenerateSourceKey(false);
			}
		
			else {
			wtcspartyputRequest.setGenerateSourceKey(true);
		}
		recordkey.setSystemName("MDO");
		wtcspartyputRequest.setRecordKey(recordkey);
		wtcspartyputRequest.setRecord(record);
		return wtcspartyputRequest;
	}
	public DeleteResponse deleteUNCodeXref(String existingUNCode)
	{
		DeleteResponse deleteResponseList = new DeleteResponse();
		LOGGER.info("Delete Request Starts for UNCode "+existingUNCode);
		LOGGER.info("==========================================");
		LOGGER.info("ROWID Xref to Delete is  " + existingUNCode);
				DeleteRequest request = new DeleteRequest();
				RecordKey recordKey = new RecordKey();
				//recordKey.setSourceKey(existingUNCode);
				recordKey.setRowidXref(existingUNCode);
				recordKey.setSystemName("MDO");
				ArrayList recordKeys = new ArrayList();
				recordKeys.add(recordKey);
				request.setRecordKeys(recordKeys); // Required
				request.setSiperianObjectUid("BASE_OBJECT.C_ALT_CODE"); //Required
				
				DeleteResponse responseDelete = (DeleteResponse) getSIFClient().process(request);
				LOGGER.info("Response of the Delete on UNCode  " + responseDelete);
				LOGGER.info("Response of the Delete on UNCode  " + responseDelete.getMessage());
				
				return responseDelete;
			}
	}
	
