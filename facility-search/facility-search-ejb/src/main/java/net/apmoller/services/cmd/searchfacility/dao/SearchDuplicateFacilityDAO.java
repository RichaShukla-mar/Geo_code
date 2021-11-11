package net.apmoller.services.cmd.searchfacility.dao;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.ejb.PostActivate;
import javax.ejb.Stateless;
import javax.interceptor.Interceptors;
import javax.sql.DataSource;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.ejb.interceptor.SpringBeanAutowiringInterceptor;

import com.siperian.sif.client.EjbSiperianClient;
import com.siperian.sif.client.SiperianClient;
import com.siperian.sif.message.Field;
import com.siperian.sif.message.MatchType;
import com.siperian.sif.message.Record;
import com.siperian.sif.message.mrm.SearchMatchRequest;
import com.siperian.sif.message.mrm.SearchMatchResponse;

import net.apmoller.services.cmd.searchfacility.util.SearchUtil;



/**
 * Session Bean implementation class SearchDuplicateDAO.
 */
@Stateless(mappedName = "SearchDuplicateDAO")
@Interceptors(SpringBeanAutowiringInterceptor.class)
public class SearchDuplicateFacilityDAO implements SearchDuplicateFacilityDAOLocal, SearchDuplicateFacilityDAORemote {

	/**
	 * Default constructor.
	 */
	public SearchDuplicateFacilityDAO() {

	}

	/** The Constant LOGGER. */
	private static final Logger LOGGER = Logger.getLogger(SearchDuplicateFacilityDAO.class.getName());

	/** The Constant RECORDS_TO_RETURN. */
	private static final int RECORDS_TO_RETURN = 31;

	/** The properties. */
	@Autowired
	@Qualifier("springEnvironmentProperties")
	public Properties			properties;
	@Autowired
	@Qualifier("springBusinessProperties")
	public Properties			businessProperties;

	/**
	 * Container managed Datasource.
	 */
	@Resource(name = "MDM_DATASOURCE1")
	private DataSource searchDatasource;


	@PostConstruct
	@PostActivate
	private void initProperties() {
		/*SearchUtil searchUtil = new SearchUtil();
		try {
			properties = searchUtil.loadProperties(DaoConstants.SIP_PROPERTIES_FILE_NAME);
			businessProperties = searchUtil.loadProperties(DaoConstants.BUSINESS_PROPERTIES_FILE_NAME);
		} catch (IOException e) {
			LOGGER.warn(e.getLocalizedMessage());
		}*/
		initLogger();
	}

	/**
	 * Gets the SIF client.
	 *
	 * @return the SIF client
	 */
	private SiperianClient getSIFClient() {
		SiperianClient sifClient = null;
		sifClient = EjbSiperianClient.newSiperianClient(properties);
		return sifClient;
	}

	private void initLogger() {
		org.apache.logging.log4j.core.LoggerContext context = (org.apache.logging.log4j.core.LoggerContext) LogManager.getContext(false);
		File file = new File(properties.getProperty("fct.search.external.log.config.file"));
		if (file.exists()) {
			context.setConfigLocation(file.toURI());
		}
	}

	private Connection getConnection() throws SQLException {
		Connection c = searchDatasource.getConnection();
		return c;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.test.ejb.SearchDuplicateDAOLocal#performSearchMatchRequest(net.
	 * apmoller .services.cmd.schemas.SearchDuplicateCustomerRequest)
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List<SearchDuplicateFacilityVO> performSearchMatchRequest(SearchDuplicateFacilityVO parameters) throws SQLException {

		SearchMatchResponse response = new SearchMatchResponse();
		List<SearchDuplicateFacilityVO> responseList = new ArrayList<SearchDuplicateFacilityVO>();
		try (Connection conn = getConnection();) {
			GenerateSearchMatchRecords generateSearchMatchRecords = new GenerateSearchMatchRecords();
			SearchMatchRequest searchMatchReq = new SearchMatchRequest();
			ArrayList<Field> matchColumnFields = new ArrayList<Field>();
			//Record rec = new Record();
			//ArrayList<Record> matchRecordList = new ArrayList<Record>();
			searchMatchReq.setRecordsToReturn(RECORDS_TO_RETURN);
			searchMatchReq.setSiperianObjectUid(businessProperties.getProperty("siperian.object.uid"));
			searchMatchReq.setReturnTotal(true);
			searchMatchReq.setMatchType(MatchType.BOTH);
			matchColumnFields = generateSearchMatchRecords.generateSearchMatchFields(parameters, conn);
			//rec.setFields(matchColumnFields);
			searchMatchReq.setMatchColumnFields(matchColumnFields);
			//matchRecordList.add(rec);
			//searchMatchReq.setRecordsToMatch(matchRecordList);
			searchMatchReq.setMatchRuleSetUid(businessProperties.getProperty("mdm.matchruleset.uid"));
			searchMatchReq.setDisablePaging(false);
			searchMatchReq.setIncludePending(false);
			long startTime = System.currentTimeMillis();
			LOGGER.info("********************************************************");
			LOGGER.info(searchMatchReq.toString());
			LOGGER.info("********************************************************");
			response = (SearchMatchResponse) getSIFClient().process(searchMatchReq);
			long endTime = System.currentTimeMillis();
			LOGGER.info("Searchmatch took :" + (endTime - startTime) + " ms for : " + response.getRecordCount() + " records");
			int i = 0;

			Map<String,String> regionMap= new HashMap<>();
			for (Iterator iter = response.getRecords().iterator(); iter.hasNext();) {
				i++;

				if(i>30){
					break;
				}


				Record record = (Record) iter.next();

				Collection fields = record.getFields();

				Map<String, String> fieldMaps = new HashMap<String, String>();
				List<String> typList =null;
				for (Iterator fieldIter = fields.iterator(); fieldIter.hasNext();) {
					Field f = (Field) fieldIter.next();

					if (f.getName() != null && f.getValue() != null) {
						fieldMaps.put(f.getName(), f.getValue().toString());
					}
				}

				if (fieldMaps.get("STATUS_CD").equalsIgnoreCase("A"))
				{
				SearchDuplicateFacilityVO responseVO = new SearchDuplicateFacilityVO();
				String typeCode=generateSearchMatchRecords.getTypeCode(fieldMaps.get("FCT_ALT_CODE"), conn);
				String oPStypeCode=generateSearchMatchRecords.getOPSTypeCode(fieldMaps.get("FCT_OPS_TYPE_ROWID"), conn);
				if(typeCode.contains("GEOID"))
				{
					responseVO.setFacilityGEOId(fieldMaps.get("FCT_ALT_CODE"));
				}else{
					responseVO.setFacilityRKSTCode(fieldMaps.get("FCT_ALT_CODE"));
				}
				String fctRowID=getFacilityrowid(conn,fieldMaps.get("FCT_ALT_CODE"));
				responseVO.setFacilityID(fctRowID);
				if (responseList.contains(responseVO)) {
					responseVO = responseList.get(Collections.binarySearch(responseList, responseVO));
					typList = responseVO.getFacilityTypes();
				} else {
					typList = new ArrayList<String>();
					responseList.add(responseVO);
				}
				responseVO.setFacilityName(fieldMaps.get("FACILITY_NAME"));
				responseVO.setFacilityCategory(fieldMaps.get("CLASS_CD"));
				responseVO.setFacilityLifecycleStatus(fieldMaps.get("STATUS_CD"));
				if (!typList.contains(oPStypeCode)) {
				typList.add(oPStypeCode);
				}
				responseVO.setFacilityTypes(typList);
				responseVO.setCity(fieldMaps.get("CITY"));
				String countryCode=generateSearchMatchRecords.getCountryCode(fieldMaps.get("CTRY_ROWID"), conn);
				responseVO.setIsoCountryCode(countryCode);
				String regionID=fieldMaps.get("TRTY_ROWID");
				String region=null;
				if(StringUtils.isNotBlank(regionMap.get(regionID))){
					region=regionMap.get(regionID);
				}else{
				 region=getRegion(conn,regionID);
				 regionMap.put(regionID, region);
				}
				responseVO.setRegion(region);
				responseVO.setHouseNo(fieldMaps.get("HOUSE_NUM"));
				responseVO.setPostalCode(fieldMaps.get("PSTCD"));
				responseVO.setStreetName(fieldMaps.get("STREET"));
				responseVO.setLongitude(fieldMaps.get("LNG_GEOSPTL"));
				responseVO.setLatitude(fieldMaps.get("LAT_GEOSPTL"));
				}
			}

		} catch (SQLException e) {
			LOGGER.warn(e.getLocalizedMessage());
			throw e;
		}
		return responseList;

	}

	private String getFacilityrowid(Connection conn,String id) throws SQLException
	{
		String fctid = null;
		String sqlQuery = "SELECT FCT_ROWID FROM C_FCT_ALT_CODES WHERE CODE= TRIM(?) AND ROWNUM < 2";
		PreparedStatement stmt = null;
		stmt = conn.prepareStatement(sqlQuery);
		stmt.setString(1, id);
		ResultSet rs = stmt.executeQuery();
		while (rs.next()) {
			fctid = rs.getString("FCT_ROWID");
		}
		stmt.close();
		return fctid;
	}

	private String getRegion(Connection conn,String id) throws SQLException
	{
		String regionCode = null;

		StringBuilder sb = new StringBuilder();
		sb.append("SELECT DISTINCT ");
		sb.append("C_ALT_CODE.CODE REGION_ISO_CD ");
		sb.append("FROM C_ALT_CODE ");
		sb.append("WHERE C_ALT_CODE.HUB_STATE_IND  = 1 ");
		sb.append("AND   C_ALT_CODE.GDA_DFND_AREA_ROWID = (?) ");
		sb.append("AND   C_ALT_CODE.TYP_TYPE_ROWID     = ");
		sb.append("( ");
		sb.append("SELECT ROWID_OBJECT FROM C_TYP_TYPE WHERE CODE = 'ALT_CODE.ISO_TRTY' ");
		sb.append(") ");

		String sqlQuery = sb.toString();
		PreparedStatement stmt = null;
		stmt = conn.prepareStatement(sqlQuery);
		stmt.setString(1, id);
		ResultSet rs = stmt.executeQuery();
		while (rs.next()) {
			regionCode = rs.getString("REGION_ISO_CD");
		}
		stmt.close();
		return regionCode;
	}
}
