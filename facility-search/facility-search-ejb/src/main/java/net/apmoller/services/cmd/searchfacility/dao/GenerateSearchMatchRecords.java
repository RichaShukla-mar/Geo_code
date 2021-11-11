package net.apmoller.services.cmd.searchfacility.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.apache.log4j.Logger;

import com.siperian.sif.message.Field;

/**
 * The Class GenerateSearchMatchRecords.
 */
public class GenerateSearchMatchRecords {

	private static final int PAD_LENGTH = 14;
	private static final Logger LOGGER = Logger.getLogger(GenerateSearchMatchRecords.class.getName());
	/**
	 * Generate search match fields.
	 *
	 * @param inputCustomer
	 *            the inputCustomer
	 * @return the array list
	 * @throws SQLException
	 */
	public ArrayList<Field> generateSearchMatchFields(SearchDuplicateFacilityVO inputFacility, Connection conn) throws SQLException {
		ArrayList<Field> matchColumnFields = new ArrayList<Field>();
		/*Facility name  fuzzy*/
		Field fuzzyfctName = new Field("Organization_Name");
		fuzzyfctName.setStringValue(inputFacility.getFacilityName());
		matchColumnFields.add(fuzzyfctName);
		LOGGER.info("Organization_Name"+inputFacility.getFacilityName());
		/* Facility type*/
		Field exactFctType = new Field("CLASS_CD");
		exactFctType.setStringValue(inputFacility.getFacilityCategory());
		matchColumnFields.add(exactFctType);
		LOGGER.info("CLASS_CD"+inputFacility.getFacilityCategory());
		/* Facility Status*/
		Field exactFctStatus = new Field("STATUS");
		if(inputFacility.getFacilityLifecycleStatus()==null ||inputFacility.getFacilityLifecycleStatus().isEmpty())
		{
			inputFacility.setFacilityLifecycleStatus("A");
		}
		exactFctStatus.setStringValue(inputFacility.getFacilityLifecycleStatus());
		matchColumnFields.add(exactFctStatus);
		LOGGER.info("STATUS"+inputFacility.getFacilityLifecycleStatus());
		/*CountryRowid exact*/
		Field exactCountry = new Field("CTRY_ROWID");
		String countryCode = inputFacility.getIsoCountryCode();
//		String countryName = getCountryName(countryCode, conn);
		String countryRowId = getCountryRowid(null, countryCode, conn);
		if (countryRowId != null) {
			exactCountry.setStringValue(pad(countryRowId, PAD_LENGTH, ' '));
			matchColumnFields.add(exactCountry);
			LOGGER.info("CTRY_ROWID"+pad(countryRowId, PAD_LENGTH, ' '));
		}

		/*Region or RegionRowid exact*/
		if (null != inputFacility.getRegion() ) {
			if(inputFacility.getRegion().length()>0)
			{
			String regionName = inputFacility.getRegion();
			String regionCode = inputFacility.getRegion();
			Field exactRegion = new Field("TRTY_ROWID");
			String regionRowId = getRegionRowid(regionName, regionCode,null,countryCode, conn);
			if (null != regionRowId) {
				exactRegion.setStringValue(pad(regionRowId, PAD_LENGTH, ' '));
				matchColumnFields.add(exactRegion);
				LOGGER.info("TRTY_ROWID"+pad(regionRowId, PAD_LENGTH, ' '));
			}
			}

		}
		/*City exact*/
		Field exactCity = new Field("CITY_ROWID");
		String cityRowId = getCityRowid(inputFacility.getRegion(), inputFacility.getRegion(),null,countryCode,inputFacility.getCity(), conn);
		if (null != cityRowId) {
			exactCity.setStringValue(pad(cityRowId, PAD_LENGTH, ' '));
			matchColumnFields.add(exactCity);
			LOGGER.info("CITY_ROWID"+pad(cityRowId, PAD_LENGTH, ' '));
		}
		/*Street fuzzy*/
		Field fuzzyAddrPart2 = new Field("Address_Part1");
		fuzzyAddrPart2.setStringValue(inputFacility.getStreetName());
		matchColumnFields.add(fuzzyAddrPart2);
		LOGGER.info("Address_Part1"+inputFacility.getStreetName());
		/*Operational facility type*/
		if("OPS".equalsIgnoreCase(inputFacility.getFacilityCategory()))
		{
			for (String OPSType : inputFacility.getFacilityTypes()) {
				Field exactOplType = new Field("TYP_TYPE_CD");
				exactOplType.setStringValue(OPSType);
				matchColumnFields.add(exactOplType);
			}
		}
		return matchColumnFields;
	}
	public String pad(String str, int size, char padChar) {
		StringBuilder padded = new StringBuilder(str);
		while (padded.length() < size) {
			padded.append(padChar);
		}
		return padded.toString();
	}
	public String mdmNullMatchColDef(String matchCol) {

		String retMatchCol = "";
		if (null != matchCol) {
			retMatchCol = matchCol + " ";
		}
		return retMatchCol;
	}
	public String getCountryRowid(String countryName, String countryCode, Connection conn) throws SQLException {

		String countryRowid = null;
		String sqlQuery = "WITH  CNTRY_TAB AS"
		+" (" + " SELECT C_GDA_DFND_AREA.ROWID_OBJECT, C_GDA_DFND_AREA.NAME CNTRY_NAME, C_ALT_CODE.CODE CNTRY_CODE" + " FROM C_ALT_CODE ,  C_GDA_DFND_AREA , C_TYP_TYPE"
		+ " WHERE C_ALT_CODE.GDA_DFND_AREA_ROWID = C_GDA_DFND_AREA.ROWID_OBJECT" + " AND C_GDA_DFND_AREA.TYP_TYPE_CD = 'GDA.COUNTRY' " + " AND C_TYP_TYPE.CODE = 'ALT_CODE.RKST' " + " AND C_ALT_CODE.TYP_TYPE_ROWID = C_TYP_TYPE.ROWID_OBJECT "
		+ ") " + " SELECT DISTINCT TRIM(ROWID_OBJECT) ROWID_OBJECT " + " FROM   CNTRY_TAB " + " WHERE  ( UPPER(CNTRY_NAME) = UPPER(?) OR UPPER(CNTRY_CODE) = UPPER(?)) " + " AND    ROWNUM < 2";
		PreparedStatement stmt = conn.prepareStatement(sqlQuery);
		stmt.setString(1, countryName);
		stmt.setString(2, countryCode);
		LOGGER.info(sqlQuery);
		LOGGER.info("1"+countryName);
		LOGGER.info("2"+countryCode);
		ResultSet rs = stmt.executeQuery();
		while (rs.next()) {
			countryRowid = rs.getString("ROWID_OBJECT");
			LOGGER.info("countryRowid"+countryRowid);
		}
		stmt.close();
		return countryRowid;
	}



	public String getCountryCode(String countryRowID, Connection conn) throws SQLException {
		String countryCode = null;
		String sqlQuery = "WITH CNTRY_TAB AS ("
				+ "				SELECT C_GDA_DFND_AREA.ROWID_OBJECT, C_GDA_DFND_AREA.NAME CNTRY_NAME, C_ALT_CODE.CODE CNTRY_CODE "
				+ "			FROM C_ALT_CODE, C_GDA_DFND_AREA , C_TYP_TYPE "
				+ "				WHERE C_ALT_CODE.GDA_DFND_AREA_ROWID = C_GDA_DFND_AREA.ROWID_OBJECT  "
				+ "				AND C_GDA_DFND_AREA.TYP_TYPE_CD = 'GDA.COUNTRY' "
				+ "				AND C_TYP_TYPE.CODE = 'ALT_CODE.RKST' "
				+ "				AND C_ALT_CODE.TYP_TYPE_ROWID = C_TYP_TYPE.ROWID_OBJECT"
				+ "				)SELECT DISTINCT  CNTRY_CODE CODE FROM   CNTRY_TAB  "
				+ "				WHERE TRIM(ROWID_OBJECT) =TRIM(?) "
				+ "				AND    ROWNUM < 2"   ;
		PreparedStatement stmt = null;
		stmt = conn.prepareStatement(sqlQuery);
		stmt.setString(1, countryRowID);
		ResultSet rs = stmt.executeQuery();
		LOGGER.info(sqlQuery);
		LOGGER.info("1"+countryRowID);
		while (rs.next()) {
			countryCode = rs.getString("CODE");
			LOGGER.info("countryCode"+countryCode);
		}
		stmt.close();
		return countryCode;
	}



	public String getTypeCode(String FctID, Connection conn) throws SQLException {
		String countryCode = null;
		String sqlQuery = "SELECT TTYPE.CODE CODE FROM C_FCT_ALT_CODES ALTCODE,C_TYP_TYPE TTYPE "
				+ "WHERE  ALTCODE.TYP_TYPE_ROWID=TTYPE.ROWID_OBJECT AND ALTCODE.CODE=(?)";
		PreparedStatement stmt = null;
		stmt = conn.prepareStatement(sqlQuery);
		stmt.setString(1, FctID);
		ResultSet rs = stmt.executeQuery();
		while (rs.next()) {
			countryCode = rs.getString("CODE");
		}
		stmt.close();
		return countryCode;
	}
	public String getOPSTypeCode(String ttypeID, Connection conn) throws SQLException {
		String countryCode = null;
		String sqlQuery = "SELECT CODE FROM C_TYP_TYPE WHERE ROWID_OBJECT =(?) ";
		PreparedStatement stmt = null;
		stmt = conn.prepareStatement(sqlQuery);
		stmt.setString(1, ttypeID);
		ResultSet rs = stmt.executeQuery();
		while (rs.next()) {
			countryCode = rs.getString("CODE");
		}
		stmt.close();
		return countryCode;
	}

	public String getCountryName(String countrycode, Connection conn) throws SQLException {

		String countryName = null;
		String sqlQuery = "SELECT DISTINCT COUNTRY_NAME.NAME NAME  "
				+ "FROM   C_ALT_CODE COUNTRY_CODE,  C_GDA_DFND_AREA COUNTRY_NAME "
				+ "WHERE  COUNTRY_CODE.GDA_DFND_AREA_ROWID = COUNTRY_NAME.ROWID_OBJECT "
				+ "AND    COUNTRY_NAME.TYP_TYPE_CD = 'GDA.COUNTRY' "
				+ "AND    COUNTRY_NAME.ACTIVE_FLAG = 'Y' "
				+ "AND TRIM(COUNTRY_CODE.CODE)= TRIM(?) AND ROWNUM < 2";
		PreparedStatement stmt = null;
		stmt = conn.prepareStatement(sqlQuery);
		stmt.setString(1, countrycode);
		LOGGER.info("getCountryName---->"+sqlQuery+"---->"+countrycode+"<----");
		ResultSet rs = stmt.executeQuery();
		while (rs.next()) {
			countryName = rs.getString("NAME");
			LOGGER.info("countryName"+countryName);
		}
		stmt.close();
		return countryName;
	}



	public String getRegionRowid(String regionName, String regionCode, String ctyName, String ctyCode,Connection conn) throws SQLException {

		String regionRowid = null;

		StringBuffer sb = new StringBuffer();
		sb.append("WITH CNTRY_CD_DETAILS AS "+
		"(	"+
		"SELECT DISTINCT "+
		"C_GDA_DFND_AREA.ROWID_OBJECT COUNTRY_MDM_ID, "+
		"C_GDA_DFND_AREA.NAME  COUNTRY_NAME, "+
		"TRIM(C_ALT_CODE.CODE) COUNTRY_CODE "+
		"FROM C_ALT_CODE "+
		"INNER JOIN C_GDA_DFND_AREA ON (C_ALT_CODE.GDA_DFND_AREA_ROWID = C_GDA_DFND_AREA.ROWID_OBJECT) "+
		"WHERE C_GDA_DFND_AREA.TYP_TYPE_CD = 'GDA.COUNTRY' "+
		"AND   C_GDA_DFND_AREA.ACTIVE_FLAG = 'Y' "+
		"AND   C_ALT_CODE.TYP_TYPE_ROWID = ( SELECT ROWID_OBJECT FROM C_TYP_TYPE WHERE CODE = 'ALT_CODE.RKST' ) "+
		"AND   (TRIM(C_ALT_CODE.CODE) = (?) OR UPPER(C_GDA_DFND_AREA.NAME) = UPPER((?)) ) "+
		") , "+
		"REGION_INFO AS "+
		"(  "+
		"SELECT DISTINCT  "+
		"C_GDA_DFND_AREA.ROWID_OBJECT REGION_MDM_ID, "+
		"C_GDA_DFND_AREA.NAME                            REGION_NAME,  "+
		"TRIM(C_ALT_CODE.CODE)        REGION_CODE "+
		"FROM C_ALT_CODE "+
		"INNER JOIN C_GDA_DFND_AREA ON (C_ALT_CODE.GDA_DFND_AREA_ROWID = C_GDA_DFND_AREA.ROWID_OBJECT) "+
		"WHERE C_GDA_DFND_AREA.TYP_TYPE_CD = 'GDA.STATE/PROV' "+
		"AND   C_GDA_DFND_AREA.ACTIVE_FLAG = 'Y' "+
		"AND   C_ALT_CODE.TYP_TYPE_ROWID = ( SELECT ROWID_OBJECT FROM C_TYP_TYPE WHERE CODE = 'ALT_CODE.ISO_TRTY' ) "+
		"AND   (TRIM(C_ALT_CODE.CODE) = (?) OR UPPER(C_GDA_DFND_AREA.NAME) = UPPER((?)) ) "+
		") "+
		"SELECT DISTINCT  "+
		"REGION_MDM_ID, "+
		"REGION_CODE, "+
		"REGION_NAME, "+
		"COUNTRY_CODE, "+
		"COUNTRY_MDM_ID "+
		"FROM REGION_INFO  "+
		"INNER JOIN C_GDA_DFND_AREA_REL ON (C_GDA_DFND_AREA_REL.GDA_DFND_AREA_CHLD_ROWID = REGION_INFO.REGION_MDM_ID) "+
		"INNER JOIN CNTRY_CD_DETAILS ON (C_GDA_DFND_AREA_REL.GDA_DFND_AREA_PRNT_ROWID = CNTRY_CD_DETAILS.COUNTRY_MDM_ID) "+
		"WHERE C_GDA_DFND_AREA_REL.TYP_TYPE_CD  = 'GDA_REL.TRTY_IN_CTRY' ")
		;

		PreparedStatement stmt = null;
		stmt = conn.prepareStatement(sb.toString());
		LOGGER.info("getRegionRowid---->"+sb.toString());
		if(ctyCode!=null)
		{
			stmt.setString(1, ctyCode);
			LOGGER.info("1"+ctyCode);
		}else{
			stmt.setString(1, "NULL");
			LOGGER.info("1"+"NULL");
		}
		if(ctyName!=null)
		{
			stmt.setString(2, ctyName);
			LOGGER.info("2"+ctyName);
		}else{
			stmt.setString(2, "NULL");
			LOGGER.info("2"+"NULL");
		}
		if(regionCode!=null)
		{
			stmt.setString(3, regionCode);
			LOGGER.info("3"+regionCode);
		}else{
			stmt.setString(3, "NULL");
			LOGGER.info("3"+"NULL");
		}
		if(regionName!=null)
		{
			stmt.setString(4, regionName);
			LOGGER.info("4"+regionName);
		}else{
			stmt.setString(4, "NULL");
			LOGGER.info("4"+"NULL");
		}
		ResultSet rs = stmt.executeQuery();
		while (rs.next()) {
			regionRowid = rs.getString("REGION_MDM_ID");
			LOGGER.info("REGION_MDM_ID"+regionRowid);
		}
		stmt.close();
		return regionRowid;
	}

	public String getCityRowid(String regionName, String regionCode, String ctyName, String ctyCode,String city,Connection conn) throws SQLException {

		String regionRowid = null;

		StringBuffer sb = new StringBuffer();
		sb.append("   	WITH	    ");
		sb.append("   	GEO_CITY_WITH_REGION AS	    ");
		sb.append("   	(	    ");
		sb.append("   	SELECT 	    ");
		sb.append("   	DISTINCT 	    ");
		sb.append("   	'GEO CITY WITH REGION' AS DATA_SOURCE, Z4.CODE COUNTRY_CODE, Z3.NAME REGION_NAME, 	    ");
		sb.append("   	Z6.CODE REGION_CODE, Z9.NAME CITY_NAME, Z9.ROWID_OBJECT CITY_MDM_ID	    ");
		sb.append("   	FROM  C_GDA_DFND_AREA_REL Z1, C_GDA_DFND_AREA Z2, C_GDA_DFND_AREA Z3, C_ALT_CODE Z4, 	    ");
		sb.append("   	      C_ALT_CODE Z6, C_GDA_DFND_AREA Z9, C_GDA_DFND_AREA_REL Z8	    ");
		sb.append("   	WHERE Z1.GDA_DFND_AREA_PRNT_ROWID = Z2.ROWID_OBJECT	    ");
		sb.append("   	AND   Z1.GDA_DFND_AREA_CHLD_ROWID = Z3.ROWID_OBJECT	    ");
		sb.append("   	AND   Z1.TYP_TYPE_CD        = 'GDA_REL.TRTY_IN_CTRY'	    ");
		sb.append("   	AND   Z8.TYP_TYPE_CD        = 'GDA_REL.CITY_IN_TRTY'	    ");
		sb.append("   	AND   Z8.GDA_DFND_AREA_PRNT_ROWID = Z3.ROWID_OBJECT	    ");
		sb.append("   	AND   Z8.GDA_DFND_AREA_CHLD_ROWID = Z9.ROWID_OBJECT	    ");
		sb.append("   	AND   Z2.TYP_TYPE_CD        = 'GDA.COUNTRY'	    ");
		sb.append("   	AND   Z3.TYP_TYPE_CD        = 'GDA.STATE/PROV'	    ");
		sb.append("   	AND   Z9.TYP_TYPE_CD        = 'GDA.CITY'	    ");
		sb.append("   	AND   Z2.ACTIVE_FLAG        = 'Y'	    ");
		sb.append("   	AND   Z3.ACTIVE_FLAG        = 'Y'	    ");
		sb.append("   	AND   Z9.ACTIVE_FLAG        = 'Y'	    ");
		sb.append("   	AND   Z2.ROWID_OBJECT       = Z4.GDA_DFND_AREA_ROWID	    ");
		sb.append("   	AND   Z4.TYP_TYPE_ROWID = ( SELECT ROWID_OBJECT FROM C_TYP_TYPE WHERE CODE = 'ALT_CODE.RKST')	    ");
		sb.append("   	AND   Z3.ROWID_OBJECT = Z6.GDA_DFND_AREA_ROWID	    ");
		sb.append("   	AND   Z6.TYP_TYPE_ROWID = ( SELECT ROWID_OBJECT FROM C_TYP_TYPE WHERE CODE = 'ALT_CODE.ISO_TRTY')	    ");
		sb.append("   	AND   (Z4.CODE = (?) OR UPPER(TRIM(Z2.NAME)) = UPPER(TRIM(?)) )	    ");
		sb.append("   	AND   UPPER(TRIM(Z9.NAME)) = UPPER(TRIM(?)) 	    ");
		sb.append("   	AND   (Z6.CODE IS NOT NULL OR UPPER(TRIM(Z3.NAME)) IS NOT NULL OR Z6.CODE = (?) OR UPPER(TRIM(Z3.NAME)) = UPPER(TRIM(?)) )	    ");
		sb.append("   	),	    ");
		sb.append("   	GEO_CITY_WITHOUT_REGION AS	    ");
		sb.append("   	(	    ");
		sb.append("   	SELECT 	    ");
		sb.append("   	DISTINCT 'GEO CITY WITHOUT REGION' AS DATA_SOURCE, K4.CODE COUNTRY_CODE, NULL REGION_NAME, NULL REGION_CODE, 	    ");
		sb.append("   	K3.NAME CITY_NAME, K3.ROWID_OBJECT CITY_MDM_ID	    ");
		sb.append("   	FROM  C_GDA_DFND_AREA_REL K1, C_GDA_DFND_AREA K2, C_GDA_DFND_AREA K3, C_ALT_CODE K4	    ");
		sb.append("   	WHERE K1.GDA_DFND_AREA_PRNT_ROWID = K2.ROWID_OBJECT	    ");
		sb.append("   	AND   K1.GDA_DFND_AREA_CHLD_ROWID = K3.ROWID_OBJECT	    ");
		sb.append("   	AND   K1.TYP_TYPE_CD        = 'GDA_REL.CITY_IN_CTRY'	    ");
		sb.append("   	AND   K2.TYP_TYPE_CD        = 'GDA.COUNTRY'	    ");
		sb.append("   	AND   K3.TYP_TYPE_CD        = 'GDA.CITY'	    ");
		sb.append("   	AND   K2.ACTIVE_FLAG        = 'Y'	    ");
		sb.append("   	AND   K3.ACTIVE_FLAG        = 'Y'	    ");
		sb.append("   	AND   K2.ROWID_OBJECT       = K4.GDA_DFND_AREA_ROWID	    ");
		sb.append("   	AND   K4.TYP_TYPE_ROWID     = ( SELECT ROWID_OBJECT FROM C_TYP_TYPE WHERE CODE = 'ALT_CODE.RKST')	    ");
		sb.append("   	AND   (K4.CODE = (?) OR UPPER(TRIM(K2.NAME)) = UPPER(TRIM(?)) )	    ");
		sb.append("   	AND   UPPER(TRIM(K3.NAME)) = UPPER(TRIM(?)) 	    ");
		sb.append("   	)	    ");
		sb.append("   	SELECT * FROM GEO_CITY_WITH_REGION	    ");
		sb.append("   	UNION ALL 	    ");
		sb.append("   	SELECT * FROM GEO_CITY_WITHOUT_REGION	    ");
		PreparedStatement stmt = null;
		stmt = conn.prepareStatement(sb.toString());
		LOGGER.info("getCityRowid---->"+sb.toString());;
		if(ctyCode!=null)
		{
			stmt.setString(1, ctyCode);
			LOGGER.info("1"+ctyCode);
		}else{
			stmt.setString(1, "NULL");
			LOGGER.info("1"+"NULL");
		}
		if(ctyName!=null)
		{
			stmt.setString(2, ctyName);
			LOGGER.info("2"+ctyName);
		}else{
			stmt.setString(2, "NULL");
			LOGGER.info("2"+"NULL");
		}
		if(city!=null)
		{
			stmt.setString(3, city);
			LOGGER.info("3"+city);
		}else{
			stmt.setString(3, "NULL");
			LOGGER.info("3"+"NULL");
		}
		if(regionCode!=null)
		{
			stmt.setString(4, regionCode);
			LOGGER.info("4"+regionCode);
		}else{
			stmt.setString(4, "NULL");
			LOGGER.info("4"+"NULL");
		}
		if(regionName!=null)
		{
			stmt.setString(5, regionName);
			LOGGER.info("5"+regionName);
		}else{
			stmt.setString(5, "NULL");
			LOGGER.info("5"+"NULL");
		}
		if(ctyCode!=null)
		{
			stmt.setString(6, ctyCode);
			LOGGER.info("6"+ctyCode);
		}else{
			stmt.setString(6, "NULL");
			LOGGER.info("6"+"NULL");
		}
		if(ctyName!=null)
		{
			stmt.setString(7, ctyName);
			LOGGER.info("7"+ctyName);
		}else{
			stmt.setString(7, "NULL");
			LOGGER.info("7"+"NULL");
		}
		if(city!=null)
		{
			stmt.setString(8, city);
			LOGGER.info("8"+city);
		}else{
			stmt.setString(8, "NULL");
			LOGGER.info("8"+"NULL");
		}
		ResultSet rs = stmt.executeQuery();
		while (rs.next()) {
			regionRowid = rs.getString("CITY_MDM_ID");
			LOGGER.info("Result--->CITY_MDM_ID"+regionRowid);
		}
		stmt.close();
		return regionRowid;
	}
}
