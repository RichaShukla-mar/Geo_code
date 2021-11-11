package net.apmoller.services.cmd.searchfacility.dao;

import java.sql.Connection;
import java.util.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import net.apmoller.services.cmd.definitions.XMLGregorianCalendarConversionUtil;


/**
 * The Class QueryHelper.
 */
public class QueryHelper {

	/** The Constant LOGGER. */
	private static final Logger LOGGER = Logger.getLogger(QueryHelper.class.getName());

	/**
	 * Gets the static part of the query in StringBuffer.
	 *
	 * @return the query in StringBuffer
	 */
	protected StringBuilder getSearchFacilityQuery(Properties prop) {

		StringBuilder sb = new StringBuilder();
		sb.append("   	WITH  OPERATIONAL_FCT_INFO AS	     ");
		sb.append("   	  ( SELECT C_FCT_OPS.ROWID_OBJECT,	     ");
		sb.append("   	    C_FCT_OPS.FCT_ROWID,	     ");
		sb.append("   	    C_FCT_OPS_TYP_REL.TYP_TYPE_ROWID FCT_OPS_TYPE_ROWID,	     ");
		sb.append("   	    C_TYP_TYPE.CODE FCT_OPS_TYPE_CD,	     ");
		sb.append("   	    C_TYP_TYPE.NAME FCT_OPS_TYPE_NM	     ");
		sb.append("   	  FROM C_FCT_OPS	     ");
		sb.append("   	  INNER JOIN C_FCT_OPS_TYP_REL	     ");
		sb.append("   	  ON C_FCT_OPS_TYP_REL.FCT_OPS_ROWID = C_FCT_OPS.ROWID_OBJECT	     ");
		sb.append("   	  INNER JOIN C_TYP_TYPE	     ");
		sb.append("   	  ON C_FCT_OPS_TYP_REL.TYP_TYPE_ROWID   = C_TYP_TYPE.ROWID_OBJECT	     ");
		sb.append("   	  WHERE C_FCT_OPS_TYP_REL.HUB_STATE_IND = 1	     ");
		sb.append("   	  ),	     ");
		sb.append("   	  OPERATIONAL_FCT_OFF_INFO AS	     ");
		sb.append("   	  (SELECT C_FCT_OFF_REL.FCT_ROWID,	     ");
		sb.append("   	    C_FCT_OFF_REL.OFFERING_ROWID,	     ");
		sb.append("   	    C_FCT_OFF.OFF_NAME FCT_OFFERING_NM,	     ");
		sb.append("   	    C_FCT_OFF.OFF_DESC FCT_OFFERING_DESC,	     ");
		sb.append("   	    C_FCT_OFF.VAS_CD FCT_OFFERING_CD,	     ");
		sb.append("   	    C_FCT_OFF.GRP_CD FCT_OFFERING_GRP_CD,	     ");
		sb.append("   	    C_FCT_OFF.IS_ACTIVE_IND FCT_OFFERING_STATUS	     ");
		sb.append("   	  FROM C_FCT_OFF_REL	     ");
		sb.append("   	  INNER JOIN C_FCT_OFF	     ");
		sb.append("   	  ON C_FCT_OFF.ROWID_OBJECT = C_FCT_OFF_REL.OFFERING_ROWID	     ");
		sb.append("   	  INNER JOIN C_FCT_OFF_GRP	     ");
		sb.append("   	  ON C_FCT_OFF_GRP.GRP_CD           = C_FCT_OFF.GRP_CD	     ");
		sb.append("   	  WHERE C_FCT_OFF_REL.HUB_STATE_IND = 1	     ");
		sb.append("   	  ),	     ");
		sb.append("   	  COUNTRY_DETAILS AS	     ");
		sb.append("   	  (SELECT C_GDA_DFND_AREA.ROWID_OBJECT COUNTRY_MDM_ID,	     ");
		sb.append("   	    C_GDA_DFND_AREA.NAME COUNTRY_NAME,	     ");
		sb.append("   	    C_ALT_CODE.CODE COUNTRY_GEOID,	     ");
		sb.append("   	    COUNTRY_CODE.CODE COUNTRY_CODE	     ");
		sb.append("   	  FROM C_GDA_DFND_AREA	     ");
		sb.append("   	  INNER JOIN C_ALT_CODE	     ");
		sb.append("   	  ON C_ALT_CODE.GDA_DFND_AREA_ROWID = C_GDA_DFND_AREA.ROWID_OBJECT	     ");
		sb.append("   	  INNER JOIN C_TYP_TYPE	     ");
		sb.append("   	  ON C_ALT_CODE.TYP_TYPE_ROWID = C_TYP_TYPE.ROWID_OBJECT	     ");
		sb.append("   	  INNER JOIN C_ALT_CODE COUNTRY_CODE	     ");
		sb.append("   	  ON COUNTRY_CODE.GDA_DFND_AREA_ROWID = C_GDA_DFND_AREA.ROWID_OBJECT	     ");
		sb.append("   	  INNER JOIN C_TYP_TYPE ISO_CTRY_TYPE	     ");
		sb.append("   	  ON COUNTRY_CODE.TYP_TYPE_ROWID      = ISO_CTRY_TYPE.ROWID_OBJECT	     ");
		sb.append("   	  WHERE C_GDA_DFND_AREA.HUB_STATE_IND = 1	     ");
		sb.append("   	  AND C_GDA_DFND_AREA.ACTIVE_FLAG     = 'Y'	     ");
		sb.append("   	  AND C_GDA_DFND_AREA.TYP_TYPE_CD     = 'GDA.COUNTRY'	     ");
		sb.append("   	  AND C_TYP_TYPE.CODE                 = 'ALT_CODE.GEOID'	     ");
		sb.append("   	  AND ISO_CTRY_TYPE.CODE              = 'ALT_CODE.RKST'	     ");
		sb.append("   	  ),	     ");
		sb.append("   	  CITY_DETAILS AS	     ");
		sb.append("   	  (SELECT C_GDA_DFND_AREA.ROWID_OBJECT CITY_MDM_ID,	     ");
		sb.append("   	    C_GDA_DFND_AREA.NAME CITY_NAME,	     ");
		sb.append("   	    C_ALT_CODE.CODE CITY_GEOID	     ");
		sb.append("   	  FROM C_GDA_DFND_AREA	     ");
		sb.append("   	  INNER JOIN C_ALT_CODE	     ");
		sb.append("   	  ON C_ALT_CODE.GDA_DFND_AREA_ROWID = C_GDA_DFND_AREA.ROWID_OBJECT	     ");
		sb.append("   	  INNER JOIN C_TYP_TYPE	     ");
		sb.append("   	  ON C_ALT_CODE.TYP_TYPE_ROWID        = C_TYP_TYPE.ROWID_OBJECT	     ");
		sb.append("   	  WHERE C_GDA_DFND_AREA.HUB_STATE_IND = 1	     ");
		sb.append("   	  AND C_GDA_DFND_AREA.ACTIVE_FLAG     = 'Y'	     ");
		sb.append("   	  AND C_GDA_DFND_AREA.TYP_TYPE_CD     = 'GDA.CITY'	     ");
		sb.append("   	  AND C_TYP_TYPE.CODE                 = 'ALT_CODE.GEOID'	     ");
		sb.append("   	  ),	     ");
		sb.append("   	  REGION_DETAILS AS	     ");
		sb.append("   	  ( SELECT DISTINCT C_GDA_DFND_AREA.ROWID_OBJECT REGION_MDM_ID,	     ");
		sb.append("   	    C_GDA_DFND_AREA.NAME REGION_NAME,	     ");
		sb.append("   	    C_ALT_CODE.CODE REGION_GEOID,	     ");
		sb.append("   	    REGION_CODE.CODE REGION_CODE	     ");
		sb.append("   	  FROM C_GDA_DFND_AREA	     ");
		sb.append("   	  INNER JOIN C_ALT_CODE	     ");
		sb.append("   	  ON C_ALT_CODE.GDA_DFND_AREA_ROWID = C_GDA_DFND_AREA.ROWID_OBJECT	     ");
		sb.append("   	  INNER JOIN C_TYP_TYPE	     ");
		sb.append("   	  ON C_ALT_CODE.TYP_TYPE_ROWID = C_TYP_TYPE.ROWID_OBJECT	     ");
		sb.append("   	  INNER JOIN C_ALT_CODE REGION_CODE	     ");
		sb.append("   	  ON REGION_CODE.GDA_DFND_AREA_ROWID = C_GDA_DFND_AREA.ROWID_OBJECT	     ");
		sb.append("   	  INNER JOIN C_TYP_TYPE ISO_TRTY_TYPE	     ");
		sb.append("   	  ON REGION_CODE.TYP_TYPE_ROWID       = ISO_TRTY_TYPE.ROWID_OBJECT	     ");
		sb.append("   	  WHERE C_GDA_DFND_AREA.HUB_STATE_IND = 1	     ");
		sb.append("   	  AND C_GDA_DFND_AREA.ACTIVE_FLAG     = 'Y'	     ");
		sb.append("   	  AND C_GDA_DFND_AREA.TYP_TYPE_CD     = 'GDA.STATE/PROV'	     ");
		sb.append("   	  AND C_TYP_TYPE.CODE                 = 'ALT_CODE.GEOID'	     ");
		sb.append("   	  AND ISO_TRTY_TYPE.CODE              = 'ALT_CODE.ISO_TRTY'	     ");
		sb.append("   	  )	     ");
		sb.append("   	SELECT KK.*	     ");
		sb.append("   	FROM	     ");
		sb.append("   	  (SELECT C_FCT_FACILITY.ROWID_OBJECT FACILITY_MDM_ID,	     ");
		sb.append("   	    C_FCT_FACILITY.HUB_STATE_IND HUB_STATE_IND,	     ");
		sb.append("   	    C_FCT_FACILITY.FACILITY_NAME FACILITY_NAME,	     ");
		sb.append("   	    C_FCT_FACILITY.EXT_OWNED EXT_OWNED,	     ");
		sb.append("   	    C_FCT_FACILITY.CLASS_CD FCT_CATEGORY,	     ");
		sb.append("   	    FCT_CATEGORY.DESCRIPTION FCT_CATEGORY_DESC,	     ");
		sb.append("   	    C_FCT_FACILITY.STATUS_CD FACILITY_STATUS,	     ");
		sb.append("   	    FCT_ALT_CODES_TYPE.CODE FCT_ALT_CODE_TYP_CD,	     ");
		sb.append("   	    FCT_ALT_CODES_TYPE.NAME FCT_ALT_CODE_TYP_NM,	     ");
		sb.append("   	    C_FCT_ALT_CODES.CODE FCT_ALT_CODE,	     ");
		sb.append("   	    C_FCT_FACILITY.EXT_EXPOSED EXT_EXPOSED,	     ");
		sb.append("   	    C_FCT_FACILITY.URL FACILITY_URL,	     ");
		sb.append("   	    OPERATIONAL_FCT_INFO.FCT_OPS_TYPE_CD FCT_OPS_TYPE_CD,	     ");
		sb.append("   	    OPERATIONAL_FCT_INFO.FCT_OPS_TYPE_NM FCT_OPS_TYPE_NM,	     ");
		sb.append("   	    C_CTM_PSTL_ADDR.PO_BOX PO_BOX,	     ");
		sb.append("   	    C_CTM_PSTL_ADDR.STREET STREET,	     ");
		sb.append("   	    C_CTM_PSTL_ADDR.HOUSE_NUM HOUSE_NUM,	     ");
		sb.append("   	    C_CTM_PSTL_ADDR.ADDR_LN_2 BUILDING,	     ");
		sb.append("   	    C_CTM_PSTL_ADDR.ADDR_LN_3 SUBURB,	     ");
		sb.append("   	    C_CTM_PSTL_ADDR.DSTRCT DISTRICT,	     ");
		sb.append("   	    CITY_DETAILS.CITY_NAME CITY,	     ");
		sb.append("   	    CITY_DETAILS.CITY_GEOID CITY_GEOID,	     ");
		sb.append("   	    C_CTM_PSTL_ADDR.PSTCD POST_CODE,	     ");
		sb.append("   	    C_CTM_PSTL_ADDR.TRTY_ROWID TRTY_ROWID,	     ");
		sb.append("   	    REGION_DETAILS.REGION_CODE REGION_CODE,	     ");
		sb.append("   	    REGION_DETAILS.REGION_GEOID REGION_GEOID,	     ");
		sb.append("   	    REGION_DETAILS.REGION_NAME REGION_NAME,	     ");
		sb.append("   	    C_CTM_PSTL_ADDR.CTRY_ROWID CTRY_ROWID,	     ");
		sb.append("   	    COUNTRY_DETAILS.COUNTRY_CODE COUNTRY_CODE,	     ");
		sb.append("   	    COUNTRY_DETAILS.COUNTRY_GEOID COUNTRY_GEOID,	     ");
		sb.append("   	    COUNTRY_DETAILS.COUNTRY_NAME COUNTRY_NAME,	     ");
		sb.append("   	    C_CTM_PSTL_ADDR.LNG_GEOSPTL LNG_GEOSPATIAL,	     ");
		sb.append("   	    C_CTM_PSTL_ADDR.LAT_GEOSPTL LAT_GEOSPATIAL,	     ");
		sb.append("   	    OPERATIONAL_FCT_OFF_INFO.FCT_OFFERING_NM FCT_OFFERING_NM,	     ");
		sb.append("   	    OPERATIONAL_FCT_OFF_INFO.FCT_OFFERING_DESC FCT_OFFERING_DESC,	     ");
		sb.append("   	    OPERATIONAL_FCT_OFF_INFO.FCT_OFFERING_CD FCT_OFFERING_CD,	     ");
		sb.append("   	    OPERATIONAL_FCT_OFF_INFO.FCT_OFFERING_GRP_CD FCT_OFFERING_GRP_CD,	     ");
		sb.append("   	    OPERATIONAL_FCT_OFF_INFO.FCT_OFFERING_STATUS FCT_OFFERING_STATUS	     ");
		sb.append("   	  FROM C_FCT_FACILITY	     ");
		sb.append("   	  INNER JOIN C_FCT_CLASS FCT_CATEGORY	     ");
		sb.append("   	  ON FCT_CATEGORY.CODE = C_FCT_FACILITY.CLASS_CD	     ");
		sb.append("   	  INNER JOIN C_FCT_STATUS	     ");
		sb.append("   	  ON C_FCT_STATUS.CODE = C_FCT_FACILITY.STATUS_CD	     ");
		sb.append("   	  INNER JOIN C_FCT_ALT_CODES	     ");
		sb.append("   	  ON C_FCT_ALT_CODES.FCT_ROWID = C_FCT_FACILITY.ROWID_OBJECT	     ");
		sb.append("   	  INNER JOIN C_TYP_TYPE FCT_ALT_CODES_TYPE	     ");
		sb.append("   	  ON C_FCT_ALT_CODES.TYP_TYPE_ROWID = FCT_ALT_CODES_TYPE.ROWID_OBJECT	     ");
		sb.append("   	  INNER JOIN C_FCT_ADDR_REL	     ");
		sb.append("   	  ON C_FCT_ADDR_REL.FCT_ROWID = C_FCT_FACILITY.ROWID_OBJECT	     ");
		sb.append("   	  INNER JOIN C_CTM_PSTL_ADDR	     ");
		sb.append("   	  ON C_FCT_ADDR_REL.ADDR_ROWID = C_CTM_PSTL_ADDR.ROWID_OBJECT	     ");
		sb.append("   	  INNER JOIN COUNTRY_DETAILS	     ");
		sb.append("   	  ON C_CTM_PSTL_ADDR.CTRY_ROWID = COUNTRY_DETAILS.COUNTRY_MDM_ID	     ");
		sb.append("   	  INNER JOIN CITY_DETAILS	     ");
		sb.append("   	  ON C_CTM_PSTL_ADDR.CITY_ROWID = CITY_DETAILS.CITY_MDM_ID	     ");
		sb.append("   	  LEFT OUTER JOIN REGION_DETAILS	     ");
		sb.append("   	  ON C_CTM_PSTL_ADDR.TRTY_ROWID = REGION_DETAILS.REGION_MDM_ID	     ");
		sb.append("   	  LEFT OUTER JOIN OPERATIONAL_FCT_INFO	     ");
		sb.append("   	  ON OPERATIONAL_FCT_INFO.FCT_ROWID = C_FCT_FACILITY.ROWID_OBJECT	     ");
		sb.append("   	  LEFT OUTER JOIN OPERATIONAL_FCT_OFF_INFO	     ");
		sb.append("   	  ON OPERATIONAL_FCT_OFF_INFO.FCT_ROWID = C_FCT_FACILITY.ROWID_OBJECT	     ");
		sb.append("   	  ) KK	     ");
		sb.append("   	WHERE KK.HUB_STATE_IND = 1	     ");
		sb.append("   	AND (	     ");
		sb.append("   	  CASE	     ");
		sb.append("   	    WHEN ((:FCT_CATEGORY_CD) IS NULL)	     ");
		sb.append("   	    THEN (1)	     ");
		sb.append("   	    WHEN ((:FCT_CATEGORY_CD) IS NOT NULL)	     ");
		sb.append("   	    AND (KK.FCT_CATEGORY      = (:FCT_CATEGORY_CD))	     ");
		sb.append("   	    THEN 1	     ");
		sb.append("   	    ELSE 0	     ");
		sb.append("   	  END ) = 1	     ");
		sb.append(" AND FCT_ALT_CODE_TYP_NM <> 'RKTS'");
		sb.append(" AND ROWNUM <= " + prop.getProperty("record.limit"));
		return sb;
	}

	/**
	 * Gets the static part of the query in StringBuffer.
	 *
	 * @return the query in StringBuffer
	 */
	protected StringBuilder getRetrieveFacilityQuery() {
		StringBuilder sb = new StringBuilder();
		sb.append("       	WITH COUNTRY_GEOID AS	   ");
		sb.append("       	  (SELECT C_GDA_DFND_AREA.ROWID_OBJECT COUNTRY_MDM_ID,	   ");
		sb.append("       	    C_GDA_DFND_AREA.NAME COUNTRY_NAME,	   ");
		sb.append("       	    C_ALT_CODE.CODE COUNTRY_GEOID,	   ");
		sb.append("       	    COUNTRY_CODE.CODE COUNTRY_CODE	   ");
		sb.append("       	  FROM C_GDA_DFND_AREA	   ");
		sb.append("       	  INNER JOIN PKG_RETRIEVE_FACILITY	   ");
		sb.append("       	  ON PKG_RETRIEVE_FACILITY.CTRY_ROWID   = C_GDA_DFND_AREA.ROWID_OBJECT	   ");
		sb.append("       	  AND PKG_RETRIEVE_FACILITY.FCT_ALT_CODE= (?)	   ");
		sb.append("       	  INNER JOIN C_ALT_CODE	   ");
		sb.append("       	  ON C_ALT_CODE.GDA_DFND_AREA_ROWID = C_GDA_DFND_AREA.ROWID_OBJECT	   ");
		sb.append("       	  INNER JOIN C_TYP_TYPE	   ");
		sb.append("       	  ON C_ALT_CODE.TYP_TYPE_ROWID = C_TYP_TYPE.ROWID_OBJECT	   ");
		sb.append("       	  INNER JOIN C_ALT_CODE COUNTRY_CODE	   ");
		sb.append("       	  ON COUNTRY_CODE.GDA_DFND_AREA_ROWID = C_GDA_DFND_AREA.ROWID_OBJECT	   ");
		sb.append("       	  INNER JOIN C_TYP_TYPE ISO_CTRY_TYPE	   ");
		sb.append("       	  ON COUNTRY_CODE.TYP_TYPE_ROWID      = ISO_CTRY_TYPE.ROWID_OBJECT	   ");
		sb.append("       	  WHERE C_GDA_DFND_AREA.HUB_STATE_IND = 1	   ");
		sb.append("       	  AND C_GDA_DFND_AREA.ACTIVE_FLAG     = 'Y'	   ");
		sb.append("       	  AND C_GDA_DFND_AREA.TYP_TYPE_CD     = 'GDA.COUNTRY'	   ");
		sb.append("       	  AND C_TYP_TYPE.CODE                 = 'ALT_CODE.GEOID'	   ");
		sb.append("       	  AND ISO_CTRY_TYPE.CODE              = 'ALT_CODE.RKST'	   ");
		sb.append("       	  ),	   ");
		sb.append("       	  CITY_GEOID AS	   ");
		sb.append("       	  (SELECT C_GDA_DFND_AREA.ROWID_OBJECT CITY_MDM_ID,	   ");
		sb.append("       	    C_GDA_DFND_AREA.NAME CITY_NAME,	   ");
		sb.append("       	    C_ALT_CODE.CODE CITY_GEOID	   ");
		sb.append("       	  FROM C_GDA_DFND_AREA	   ");
		sb.append("       	  INNER JOIN PKG_RETRIEVE_FACILITY	   ");
		sb.append("       	  ON PKG_RETRIEVE_FACILITY.CITY_ROWID   = C_GDA_DFND_AREA.ROWID_OBJECT	   ");
		sb.append("       	  AND PKG_RETRIEVE_FACILITY.FCT_ALT_CODE= (?)	   ");
		sb.append("       	  INNER JOIN C_ALT_CODE	   ");
		sb.append("       	  ON C_ALT_CODE.GDA_DFND_AREA_ROWID = C_GDA_DFND_AREA.ROWID_OBJECT	   ");
		sb.append("       	  INNER JOIN C_TYP_TYPE	   ");
		sb.append("       	  ON C_ALT_CODE.TYP_TYPE_ROWID        = C_TYP_TYPE.ROWID_OBJECT	   ");
		sb.append("       	  WHERE C_GDA_DFND_AREA.HUB_STATE_IND = 1	   ");
		sb.append("       	  AND C_GDA_DFND_AREA.ACTIVE_FLAG     = 'Y'	   ");
		sb.append("       	  AND C_GDA_DFND_AREA.TYP_TYPE_CD     = 'GDA.CITY'	   ");
		sb.append("       	  AND C_TYP_TYPE.CODE                 = 'ALT_CODE.GEOID'	   ");
		sb.append("       	  ),	   ");
		sb.append("       	  REGION_GEOID AS	   ");
		sb.append("       	  ( SELECT DISTINCT C_GDA_DFND_AREA.ROWID_OBJECT REGION_MDM_ID,	   ");
		sb.append("       	    C_GDA_DFND_AREA.NAME REGION_NAME,	   ");
		sb.append("       	    C_ALT_CODE.CODE REGION_GEOID,	   ");
		sb.append("       	    REGION_CODE.CODE REGION_CODE	   ");
		sb.append("       	  FROM C_GDA_DFND_AREA	   ");
		sb.append("       	  INNER JOIN PKG_RETRIEVE_FACILITY	   ");
		sb.append("       	  ON PKG_RETRIEVE_FACILITY.TRTY_ROWID   = C_GDA_DFND_AREA.ROWID_OBJECT	   ");
		sb.append("       	  AND PKG_RETRIEVE_FACILITY.FCT_ALT_CODE= (?)	   ");
		sb.append("       	  INNER JOIN C_ALT_CODE	   ");
		sb.append("       	  ON C_ALT_CODE.GDA_DFND_AREA_ROWID = C_GDA_DFND_AREA.ROWID_OBJECT	   ");
		sb.append("       	  INNER JOIN C_TYP_TYPE	   ");
		sb.append("       	  ON C_ALT_CODE.TYP_TYPE_ROWID = C_TYP_TYPE.ROWID_OBJECT	   ");
		sb.append("       	  INNER JOIN C_ALT_CODE REGION_CODE	   ");
		sb.append("       	  ON REGION_CODE.GDA_DFND_AREA_ROWID = C_GDA_DFND_AREA.ROWID_OBJECT	   ");
		sb.append("       	  INNER JOIN C_TYP_TYPE ISO_TRTY_TYPE	   ");
		sb.append("       	  ON REGION_CODE.TYP_TYPE_ROWID       = ISO_TRTY_TYPE.ROWID_OBJECT	   ");
		sb.append("       	  WHERE C_GDA_DFND_AREA.HUB_STATE_IND = 1	   ");
		sb.append("       	  AND C_GDA_DFND_AREA.ACTIVE_FLAG     = 'Y'	   ");
		sb.append("       	  AND C_GDA_DFND_AREA.TYP_TYPE_CD     = 'GDA.STATE/PROV'	   ");
		sb.append("       	  AND C_TYP_TYPE.CODE                 = 'ALT_CODE.GEOID'	   ");
		sb.append("       	  AND ISO_TRTY_TYPE.CODE              = 'ALT_CODE.ISO_TRTY'	   ");
		sb.append("       	  ),	   ");
		sb.append("       	  OPERATIONAL_FCT_INFO AS	   ");
		sb.append("       	  (SELECT C_FCT_OPS.ROWID_OBJECT,	   ");
		sb.append("       	    C_FCT_OPS.FCT_ROWID,	   ");
		sb.append("       	    C_TYP_TYPE.CODE FCT_OPS_TYPE_CD,	   ");
		sb.append("       	    C_TYP_TYPE.NAME FCT_OPS_TYPE_NM,	   ");
		sb.append("       	    WEIGHT_LMT_CRANE,	   ");
		sb.append("       	    WEIGHT_LMT_YARD,	   ");
		sb.append("       	    VESSEL_AGENT,	   ");
		sb.append("       	    GPS_FLAG,	   ");
		sb.append("       	    GSM_FLAG,	   ");
		sb.append("       	    OCE_FRGHT_PR,	   ");
		sb.append("       	    C_FCT_OPS_TYP_REL.VALID_FROM_DT FCT_OPS_TYPE_START_DT,	   ");
		sb.append("       	    C_FCT_OPS_TYP_REL.VALID_THRU_DT FCT_OPS_TYPE_END_DT	   ");
		sb.append("       	  FROM C_FCT_OPS	   ");
		sb.append("       	  INNER JOIN PKG_RETRIEVE_FACILITY	   ");
		sb.append("       	  ON PKG_RETRIEVE_FACILITY.ROWID_OBJECT = C_FCT_OPS.FCT_ROWID	   ");
		sb.append("       	  AND PKG_RETRIEVE_FACILITY.FCT_ALT_CODE= (?)	   ");
		sb.append("       	  LEFT OUTER JOIN	   ");
		sb.append("       	    (SELECT *	   ");
		sb.append("       	    FROM C_FCT_OPS_TYP_REL	   ");
		sb.append("       	    WHERE HUB_STATE_IND                                            = 1	   ");
		sb.append("       	    AND TRUNC(NVL(C_FCT_OPS_TYP_REL.VALID_THRU_DT,SYSDATE),'DDD') >= TRUNC(SYSDATE,'DDD')	   ");
		sb.append("       	    ) C_FCT_OPS_TYP_REL	   ");
		sb.append("       	  ON C_FCT_OPS_TYP_REL.FCT_OPS_ROWID = C_FCT_OPS.ROWID_OBJECT	   ");
		sb.append("       	  LEFT OUTER JOIN C_TYP_TYPE	   ");
		sb.append("       	  ON C_FCT_OPS_TYP_REL.TYP_TYPE_ROWID = C_TYP_TYPE.ROWID_OBJECT	   ");
		sb.append("       	  WHERE C_FCT_OPS.HUB_STATE_IND       = 1	   ");
		sb.append("       	  ),	   ");
		sb.append("       	  FCT_OFFERING_INFO AS	   ");
		sb.append("       	  (SELECT C_FCT_OFF.GRP_CD,	   ");
		sb.append("       	    C_FCT_OFF.VAS_CD,	   ");
		sb.append("       	    C_FCT_OFF.OFF_NAME,	   ");
		sb.append("       	    C_FCT_OFF.OFF_DESC,	   ");
		sb.append("       	    C_FCT_OFF_REL.FCT_ROWID,	   ");
		sb.append("       	    C_FCT_OFF_REL.VALID_FROM_DT FCT_OFFERING_START_DT,	   ");
		sb.append("       	    C_FCT_OFF_REL.VALID_THRU_DT FCT_OFFERING_END_DT	   ");
		sb.append("       	  FROM C_FCT_OFF	   ");
		sb.append("       	  INNER JOIN C_FCT_OFF_REL	   ");
		sb.append("       	  ON C_FCT_OFF_REL.OFFERING_ROWID = C_FCT_OFF.ROWID_OBJECT	   ");
		sb.append("       	  INNER JOIN PKG_RETRIEVE_FACILITY	   ");
		sb.append("       	  ON PKG_RETRIEVE_FACILITY.ROWID_OBJECT                      = C_FCT_OFF_REL.FCT_ROWID	   ");
		sb.append("       	  AND PKG_RETRIEVE_FACILITY.FCT_ALT_CODE                     = (?)	   ");
		sb.append("       	  WHERE C_FCT_OFF.HUB_STATE_IND                              = 1	   ");
		sb.append("       	  AND C_FCT_OFF_REL.HUB_STATE_IND                            = 1	   ");
		sb.append("       	  AND TRUNC(NVL(C_FCT_OFF_REL.VALID_THRU_DT,SYSDATE),'DDD') >= TRUNC(SYSDATE,'DDD')	   ");
		sb.append("       	  ),	   ");
		sb.append("       	  FCT_TRANSPORT_INFO AS	   ");
		sb.append("       	  (SELECT TRNSP_CD,	   ");
		sb.append("       	    TRNSP_NAME,	   ");
		sb.append("       	    TRNSP_DESC,	   ");
		sb.append("       	    FCT_ROWID,	   ");
		sb.append("       	    C_FCT_TRNSP_REL.VALID_FROM_DT TRNSP_START_DT,	   ");
		sb.append("       	    C_FCT_TRNSP_REL.VALID_THRU_DT TRNSP_END_DT	   ");
		sb.append("       	  FROM C_FCT_TRNSP_MODE	   ");
		sb.append("       	  INNER JOIN C_FCT_TRNSP_REL	   ");
		sb.append("       	  ON C_FCT_TRNSP_REL.TRNSP_ROWID = C_FCT_TRNSP_MODE.ROWID_OBJECT	   ");
		sb.append("       	  INNER JOIN PKG_RETRIEVE_FACILITY	   ");
		sb.append("       	  ON PKG_RETRIEVE_FACILITY.ROWID_OBJECT                        = C_FCT_TRNSP_REL.FCT_ROWID	   ");
		sb.append("       	  AND PKG_RETRIEVE_FACILITY.FCT_ALT_CODE                       = (?)	   ");
		sb.append("       	  WHERE C_FCT_TRNSP_MODE.HUB_STATE_IND                         = 1	   ");
		sb.append("       	  AND C_FCT_TRNSP_REL.HUB_STATE_IND                            = 1	   ");
		sb.append("       	  AND TRUNC(NVL(C_FCT_TRNSP_REL.VALID_THRU_DT,SYSDATE),'DDD') >= TRUNC(SYSDATE,'DDD')	   ");
		sb.append("       	  )	   ");
		sb.append("       	SELECT PKG_RETRIEVE_FACILITY.ROWID_OBJECT FCT_MDM_ID,	   ");
		sb.append("       	  CLASS_CD FCT_CATEGORY,	   ");
		sb.append("       	  FACILITY_NAME FCT_NAME,	   ");
		sb.append("       	  PKG_RETRIEVE_FACILITY.HUB_STATE_IND FCT_HUB_STATE,	   ");
		sb.append("       	  STATUS_CD FCT_STATUS,	   ");
		sb.append("       	  PKG_RETRIEVE_FACILITY.CREATE_DATE FCT_CREATE_DT,	   ");
		sb.append("       	  PKG_RETRIEVE_FACILITY.UPDATED_BY FCT_LAST_UPDATED_BY,	   ");
		sb.append("       	  PKG_RETRIEVE_FACILITY.CREATOR FCT_CREATOR,	   ");
		sb.append("       	  PKG_RETRIEVE_FACILITY.DELETED_BY FCT_DELETED_BY,	   ");
		sb.append("       	  PKG_RETRIEVE_FACILITY.LAST_UPDATE_DATE FCT_LAST_UPDATE_DT,	   ");
		sb.append("       	  PKG_RETRIEVE_FACILITY.LAST_ROWID_SYSTEM LAST_ROWID_SYSTEM,	   ");
		sb.append("       	  PKG_RETRIEVE_FACILITY.DELETED_DATE FCT_DELETED_DT,	   ");
		sb.append("       	  EXT_OWNED EXT_OWNED_FLAG,	   ");
		sb.append("       	  EXT_EXPOSED EXT_EXPOSED_FLAG,	   ");
		sb.append("       	  URL FCT_URL,	   ");
		sb.append("       	  DODAAC   DODAAC,	 ");
		sb.append("       	  FCT_ALT_CODE_HUB_STATE FCT_ALT_CODE_HUB_STATE,	   ");
		sb.append("       	  TYP_TYPE_ROWID FCT_ALT_CD_TYP_ROWID,	   ");
		sb.append("       	  ALT_CODE_TYPE_CD ALT_CODE_TYPE_CD,	   ");
		sb.append("       	  ALT_CODE_TYPE_NM ALT_CODE_TYPE_NM,	   ");
		sb.append("       	  FCT_ALT_CODE FCT_ALT_CODE,	   ");
		sb.append("       	  FCT_ADDR_REL_HUB_STATE FCT_ADDR_REL_HUB_STATE,	   ");
		sb.append("       	  ADDR_ROWID ADDR_MDM_ID,	   ");
		sb.append("       	  FCT_ADDR_VALID_FROM_DT FCT_ADDR_VALID_FROM_DT,	   ");
		sb.append("       	  FCT_ADDR_VALID_THRU_DT FCT_ADDR_VALID_THRU_DT,	   ");
		sb.append("       	  ADDR_HUB_STATE ADDR_HUB_STATE,	   ");
		sb.append("       	  ADDR_LAST_UPDATE_DT ADDR_LAST_UPDATE_DT,	   ");
		sb.append("       	  ADDR_UPDATED_BY ADDR_UPDATED_BY,	   ");
		sb.append("       	  ADDR_CREATE_DT ADDR_CREATE_DT,	   ");
		sb.append("       	  ADDR_CREATOR ADDR_CREATOR,	   ");
		sb.append("       	  HOUSE_NUM HOUSE_NUM,	   ");
		sb.append("       	  DSTRCT DISTRICT,	   ");
		sb.append("       	  PSTCD POSTAL_CODE,	   ");
		sb.append("       	  TAX_JURN_CD TAX_JURN_CD,	   ");
		sb.append("       	  PO_BOX PO_BOX,	   ");
		sb.append("       	  STREET STREET,	   ");
		sb.append("       	  ADDR_LN_2 BUILDING_NUM,	   ");
		sb.append("       	  ADDR_LN_3 SUBURB,	   ");
		sb.append("       	  CITY_MDM_ID CITY_MDM_ID,	   ");
		sb.append("       	  CITY_GEOID CITY_GEOID,	   ");
		sb.append("       	  CITY_GEOID.CITY_NAME CITY,	   ");
		sb.append("       	  LAT_GEOSPTL LAT_GEOSPTL,	   ");
		sb.append("       	  LNG_GEOSPTL LNG_GEOSPTL,	   ");
		sb.append("       	  TRTY_ROWID REGION_MDM_ID,	   ");
		sb.append("       	  REGION_GEOID REGION_GEOID,	   ");
		sb.append("       	  REGION_CODE ISO_REGION_CODE,	   ");
		sb.append("       	  REGION_NAME REGION_NAME,	   ");
		sb.append("       	  CTRY_ROWID CTRY_MDM_ID,	   ");
		sb.append("       	  COUNTRY_GEOID COUNTRY_GEOID,	   ");
		sb.append("       	  COUNTRY_CODE ISO_COUNTRY_CODE,	   ");
		sb.append("       	  COUNTRY_NAME COUNTRY_NAME,	   ");
		sb.append("       	  C_FCT_DFND_REL.GDA_ROWID GDA_ROWID,	   ");
		sb.append("       	  C_FCT_DFND_REL.VALID_FROM_DT FCT_DFND_REL_VALID_FROM_DT,	   ");
		sb.append("       	  C_FCT_DFND_REL.VALID_THRU_DT FCT_DFND_REL_VALID_THRU_DT,	   ");
		sb.append("       	  DECODE(OPEN_TIME_HRS	   ");
		sb.append("       	  || ':'	   ");
		sb.append("       	  || OPEN_TIME_MINS,':', NULL,OPEN_TIME_HRS	   ");
		sb.append("       	  || ':'	   ");
		sb.append("       	  || OPEN_TIME_MINS ) FCT_DAY_OPEN,	   ");
		sb.append("       	  DECODE(CLOSE_TIME_HRS	   ");
		sb.append("       	  || ':'	   ");
		sb.append("       	  || CLOSE_TIME_MINS,':', NULL,CLOSE_TIME_HRS	   ");
		sb.append("       	  || ':'	   ");
		sb.append("       	  || CLOSE_TIME_MINS ) FCT_DAY_CLOSE,	   ");
		sb.append("       	  DAY FCT_WORKING_DAYS,	   ");
		sb.append("       	  FCT_OPS_TYPE_CD FCT_OPS_TYPE_CD,	   ");
		sb.append("       	  FCT_OPS_TYPE_NM FCT_OPS_TYPE_NM,	   ");
		sb.append("       	  FCT_OPS_TYPE_START_DT FCT_OPS_TYPE_START_DT,	   ");
		sb.append("       	  FCT_OPS_TYPE_END_DT FCT_OPS_TYPE_END_DT,	   ");
		sb.append("       	  WEIGHT_LMT_CRANE WEIGHT_LMT_CRANE,	   ");
		sb.append("       	  WEIGHT_LMT_YARD WEIGHT_LMT_YARD,	   ");
		sb.append("       	  VESSEL_AGENT VESSEL_AGENT,	   ");
		sb.append("       	  GPS_FLAG GPS_FLAG,	   ");
		sb.append("       	  GSM_FLAG GSM_FLAG,	   ");
		sb.append("       	  OCE_FRGHT_PR OCE_FRGHT_PR,	   ");
		sb.append("       	  GRP_CD GRP_CD,	   ");
		sb.append("       	  VAS_CD FCT_OFFERING_CD,	   ");
		sb.append("       	  OFF_NAME FCT_OFFERING_NAME,	   ");
		sb.append("       	  OFF_DESC FCT_OFFERING_DESC,	   ");
		sb.append("       	  FCT_OFFERING_START_DT FCT_OFFERING_START_DT,	   ");
		sb.append("       	  FCT_OFFERING_END_DT FCT_OFFERING_END_DT,	   ");
		sb.append("       	  TRNSP_CD FCT_TRANSPORT_CD,	   ");
		sb.append("       	  TRNSP_NAME FCT_TRANSPORT_NAME,	   ");
		sb.append("       	  TRNSP_DESC FCT_TRANSPORT_DESC,	   ");
		sb.append("       	  TRNSP_START_DT TRNSP_START_DT,	   ");
		sb.append("       	  TRNSP_END_DT FCT_TRNSP_END_DT	   ");
		sb.append("       	FROM PKG_RETRIEVE_FACILITY	   ");
		sb.append("       	LEFT OUTER JOIN C_FCT_DFND_REL	   ");
		sb.append("       	ON C_FCT_DFND_REL.FCT_ROWID = PKG_RETRIEVE_FACILITY.ROWID_OBJECT	   ");
		sb.append("       	INNER JOIN COUNTRY_GEOID	   ");
		sb.append("       	ON COUNTRY_GEOID.COUNTRY_MDM_ID = PKG_RETRIEVE_FACILITY.CTRY_ROWID	   ");
		sb.append("       	INNER JOIN CITY_GEOID    	   ");
		sb.append("       	ON CITY_GEOID.CITY_MDM_ID       = PKG_RETRIEVE_FACILITY.CITY_ROWID	   ");
		sb.append("       	LEFT OUTER JOIN C_FCT_OPNH	   ");
		sb.append("       	ON C_FCT_OPNH.FCT_ROWID = PKG_RETRIEVE_FACILITY.ROWID_OBJECT	   ");
		sb.append("       	LEFT OUTER JOIN REGION_GEOID	   ");
		sb.append("       	ON REGION_GEOID.REGION_MDM_ID = PKG_RETRIEVE_FACILITY.TRTY_ROWID	   ");
		sb.append("       	LEFT OUTER JOIN OPERATIONAL_FCT_INFO	   ");
		sb.append("       	ON OPERATIONAL_FCT_INFO.FCT_ROWID = PKG_RETRIEVE_FACILITY.ROWID_OBJECT	   ");
		sb.append("       	LEFT OUTER JOIN FCT_OFFERING_INFO	   ");
		sb.append("       	ON FCT_OFFERING_INFO.FCT_ROWID = PKG_RETRIEVE_FACILITY.ROWID_OBJECT	   ");
		sb.append("       	LEFT OUTER JOIN FCT_TRANSPORT_INFO	   ");
		sb.append("       	ON FCT_TRANSPORT_INFO.FCT_ROWID                                             = PKG_RETRIEVE_FACILITY.ROWID_OBJECT	   ");
		sb.append("       	WHERE PKG_RETRIEVE_FACILITY.FCT_ALT_CODE                                    = (?)	   ");
		sb.append("       	AND PKG_RETRIEVE_FACILITY.HUB_STATE_IND                                     = 1	   ");
		sb.append("       	AND TRUNC(NVL(PKG_RETRIEVE_FACILITY.FCT_ADDR_VALID_THRU_DT,SYSDATE),'DDD') >= TRUNC(SYSDATE,'DDD')	   ");
		return sb;
	}



	protected StringBuilder getRetrieveAllFacilityQuery(Properties prop) {
		StringBuilder sb = new StringBuilder();
		sb.append("WITH FACILITY_IDENTIFIER AS (SELECT C_FCT_ALT_CODES.FCT_ROWID,C_FCT_ALT_CODES.CODE,C_FCT_ADDR_REL.ADDR_ROWID,C_CTM_PSTL_ADDR.CTRY_ROWID,C_CTM_PSTL_ADDR.CITY_ROWID,C_CTM_PSTL_ADDR.TRTY_ROWID FROM C_FCT_ALT_CODES INNER JOIN C_FCT_ADDR_REL ON C_FCT_ADDR_REL.FCT_ROWID = C_FCT_ALT_CODES.FCT_ROWID INNER JOIN C_CTM_PSTL_ADDR ON C_FCT_ADDR_REL.ADDR_ROWID = C_CTM_PSTL_ADDR.ROWID_OBJECT WHERE C_FCT_ALT_CODES.CODE = :CODE AND TRUNC ( NVL(C_FCT_ADDR_REL.VALID_THRU_DT,SYSDATE),'DDD' ) >= TRUNC(SYSDATE,'DDD') ), ");
		sb.append("COUNTRY_GEOID AS (SELECT C_GDA_DFND_AREA.ROWID_OBJECT COUNTRY_MDM_ID,C_GDA_DFND_AREA.NAME COUNTRY_NAME,C_ALT_CODE.CODE COUNTRY_GEOID,COUNTRY_CODE.CODE COUNTRY_CODE FROM C_GDA_DFND_AREA INNER JOIN FACILITY_IDENTIFIER ON FACILITY_IDENTIFIER.CTRY_ROWID = C_GDA_DFND_AREA.ROWID_OBJECT INNER JOIN C_ALT_CODE ON C_ALT_CODE.GDA_DFND_AREA_ROWID = C_GDA_DFND_AREA.ROWID_OBJECT INNER JOIN C_TYP_TYPE ON C_ALT_CODE.TYP_TYPE_ROWID = C_TYP_TYPE.ROWID_OBJECT INNER JOIN C_ALT_CODE COUNTRY_CODE ON COUNTRY_CODE.GDA_DFND_AREA_ROWID = C_GDA_DFND_AREA.ROWID_OBJECT INNER JOIN C_TYP_TYPE ISO_CTRY_TYPE ON COUNTRY_CODE.TYP_TYPE_ROWID = ISO_CTRY_TYPE.ROWID_OBJECT WHERE C_GDA_DFND_AREA.HUB_STATE_IND = 1 AND C_GDA_DFND_AREA.ACTIVE_FLAG = 'Y' AND C_GDA_DFND_AREA.TYP_TYPE_CD = 'GDA.COUNTRY' AND C_TYP_TYPE.CODE = 'ALT_CODE.GEOID' AND ISO_CTRY_TYPE.CODE = 'ALT_CODE.RKST' ), ");
		sb.append("CITY_GEOID AS (SELECT C_GDA_DFND_AREA.ROWID_OBJECT CITY_MDM_ID,C_GDA_DFND_AREA.NAME CITY_NAME,C_ALT_CODE.CODE CITY_GEOID FROM C_GDA_DFND_AREA INNER JOIN FACILITY_IDENTIFIER ON FACILITY_IDENTIFIER.CITY_ROWID = C_GDA_DFND_AREA.ROWID_OBJECT INNER JOIN C_ALT_CODE ON C_ALT_CODE.GDA_DFND_AREA_ROWID = C_GDA_DFND_AREA.ROWID_OBJECT INNER JOIN C_TYP_TYPE ON C_ALT_CODE.TYP_TYPE_ROWID = C_TYP_TYPE.ROWID_OBJECT WHERE C_GDA_DFND_AREA.HUB_STATE_IND = 1 AND C_GDA_DFND_AREA.ACTIVE_FLAG = 'Y' AND C_GDA_DFND_AREA.TYP_TYPE_CD = 'GDA.CITY' AND C_TYP_TYPE.CODE = 'ALT_CODE.GEOID' ), ");
		sb.append("REGION_GEOID AS ( SELECT DISTINCT C_GDA_DFND_AREA.ROWID_OBJECT REGION_MDM_ID,C_GDA_DFND_AREA.NAME REGION_NAME,C_ALT_CODE.CODE REGION_GEOID,REGION_CODE.CODE REGION_CODE FROM C_GDA_DFND_AREA INNER JOIN FACILITY_IDENTIFIER ON ( NVL(FACILITY_IDENTIFIER.TRTY_ROWID,'XX') = NVL(C_GDA_DFND_AREA.ROWID_OBJECT,'XX') ) INNER JOIN C_ALT_CODE ON C_ALT_CODE.GDA_DFND_AREA_ROWID = C_GDA_DFND_AREA.ROWID_OBJECT INNER JOIN C_TYP_TYPE ON C_ALT_CODE.TYP_TYPE_ROWID = C_TYP_TYPE.ROWID_OBJECT INNER JOIN C_ALT_CODE REGION_CODE ON REGION_CODE.GDA_DFND_AREA_ROWID = C_GDA_DFND_AREA.ROWID_OBJECT INNER JOIN C_TYP_TYPE ISO_TRTY_TYPE ON REGION_CODE.TYP_TYPE_ROWID = ISO_TRTY_TYPE.ROWID_OBJECT WHERE C_GDA_DFND_AREA.HUB_STATE_IND = 1 AND C_GDA_DFND_AREA.ACTIVE_FLAG = 'Y' AND C_GDA_DFND_AREA.TYP_TYPE_CD = 'GDA.STATE/PROV' AND C_TYP_TYPE.CODE = 'ALT_CODE.GEOID' AND ISO_TRTY_TYPE.CODE = 'ALT_CODE.ISO_TRTY' ), ");
		sb.append("OPERATIONAL_FCT_INFO AS (SELECT C_FCT_OPS.ROWID_OBJECT,C_FCT_OPS.FCT_ROWID,C_TYP_TYPE.CODE FCT_OPS_TYPE_CD,C_TYP_TYPE.NAME FCT_OPS_TYPE_NM,WEIGHT_LMT_CRANE,WEIGHT_LMT_YARD,VESSEL_AGENT,GPS_FLAG,GSM_FLAG,OCE_FRGHT_PR,C_FCT_OPS_TYP_REL.VALID_FROM_DT FCT_OPS_TYPE_START_DT,C_FCT_OPS_TYP_REL.VALID_THRU_DT FCT_OPS_TYPE_END_DT FROM C_FCT_OPS INNER JOIN FACILITY_IDENTIFIER ON FACILITY_IDENTIFIER.FCT_ROWID = C_FCT_OPS.FCT_ROWID LEFT OUTER JOIN (SELECT * FROM C_FCT_OPS_TYP_REL WHERE HUB_STATE_IND = 1 AND TRUNC(NVL(C_FCT_OPS_TYP_REL.VALID_THRU_DT,SYSDATE),'DDD') >= TRUNC(SYSDATE,'DDD') ) C_FCT_OPS_TYP_REL ON C_FCT_OPS_TYP_REL.FCT_OPS_ROWID = C_FCT_OPS.ROWID_OBJECT LEFT OUTER JOIN C_TYP_TYPE ON C_FCT_OPS_TYP_REL.TYP_TYPE_ROWID = C_TYP_TYPE.ROWID_OBJECT WHERE C_FCT_OPS.HUB_STATE_IND = 1 ), ");
		sb.append("FCT_OFFERING_INFO AS (SELECT C_FCT_OFF.GRP_CD,C_FCT_OFF.VAS_CD,C_FCT_OFF.OFF_NAME,C_FCT_OFF.OFF_DESC,C_FCT_OFF_REL.FCT_ROWID,C_FCT_OFF_REL.VALID_FROM_DT FCT_OFFERING_START_DT,C_FCT_OFF_REL.VALID_THRU_DT FCT_OFFERING_END_DT FROM C_FCT_OFF INNER JOIN C_FCT_OFF_REL ON C_FCT_OFF_REL.OFFERING_ROWID = C_FCT_OFF.ROWID_OBJECT INNER JOIN FACILITY_IDENTIFIER ON FACILITY_IDENTIFIER.FCT_ROWID = C_FCT_OFF_REL.FCT_ROWID WHERE C_FCT_OFF.HUB_STATE_IND = 1 AND C_FCT_OFF_REL.HUB_STATE_IND = 1 AND TRUNC(NVL(C_FCT_OFF_REL.VALID_THRU_DT,SYSDATE),'DDD') >= TRUNC(SYSDATE,'DDD') ), ");
		sb.append("FCT_TRANSPORT_INFO AS (SELECT TRNSP_CD,TRNSP_NAME,TRNSP_DESC,FACILITY_IDENTIFIER.FCT_ROWID,C_FCT_TRNSP_REL.VALID_FROM_DT TRNSP_START_DT,C_FCT_TRNSP_REL.VALID_THRU_DT TRNSP_END_DT FROM C_FCT_TRNSP_MODE INNER JOIN C_FCT_TRNSP_REL ON C_FCT_TRNSP_REL.TRNSP_ROWID = C_FCT_TRNSP_MODE.ROWID_OBJECT INNER JOIN FACILITY_IDENTIFIER ON FACILITY_IDENTIFIER.FCT_ROWID = C_FCT_TRNSP_REL.FCT_ROWID WHERE C_FCT_TRNSP_MODE.HUB_STATE_IND = 1 AND C_FCT_TRNSP_REL.HUB_STATE_IND = 1 AND TRUNC(NVL(C_FCT_TRNSP_REL.VALID_THRU_DT,SYSDATE),'DDD') >= TRUNC(SYSDATE,'DDD') ), ");
		sb.append("COMMERCIAL_FCT_INFO AS ");
		sb.append("( ");
		sb.append("   SELECT ");
		sb.append("   C_FCT_COM.FCT_ROWID, ");
		sb.append("   C_TYP_TYPE.CODE COMM_FCT_TYPE_CD, ");
		sb.append("   C_TYP_TYPE.CODE COMM_FCT_TYPE_NM, ");
		sb.append("   C_FCT_BRAND.CODE COMM_FCT_BRAND_CD, ");
		sb.append("   C_FCT_BRAND.NAME COMM_FCT_BRAND_NM, ");
		sb.append("   C_FCT_COMM_FUNC.CODE COMM_FCT_FUNC_CD, ");
		sb.append("   C_FCT_COMM_FUNC.NAME COMM_FCT_FUNC_NM, ");
		sb.append("   C_CTM_INTL_DIALNG_CD.DIALNG_CD COMM_FCT_DIALING_CD, ");
		sb.append("   C_CTM_INTL_DIALNG_CD.DAILNG_CD_DESC COMM_FCT_DAILNG_CD_DESC, ");
		sb.append("   C_FCT_COM.TELECOM_NUM, ");
		sb.append("   C_FCT_COM.IMP_MAIL, ");
		sb.append("   C_FCT_COM.EXP_MAIL ");
		sb.append("   FROM C_FCT_COM ");
		sb.append("   INNER JOIN FACILITY_IDENTIFIER ON FACILITY_IDENTIFIER.FCT_ROWID = C_FCT_COM.FCT_ROWID ");
		sb.append("   INNER JOIN C_FCT_COMM_FUNC ON C_FCT_COM.COMM_FUNC_ROWID = C_FCT_COMM_FUNC.ROWID_OBJECT ");
		sb.append("   LEFT OUTER JOIN C_FCT_BRAND ON C_FCT_COM.FCT_BRAND_ROWID = C_FCT_BRAND.ROWID_OBJECT ");
		sb.append("   INNER JOIN C_TYP_TYPE ON C_FCT_COM.TYP_TYPE_ROWID = C_TYP_TYPE.ROWID_OBJECT ");
		sb.append("   LEFT OUTER JOIN C_CTM_INTL_DIALNG_CD ON C_FCT_COM.INTL_DIALING_ROWID = C_CTM_INTL_DIALNG_CD.ROWID_OBJECT ");
		sb.append(") ");
		sb.append("SELECT ");
		sb.append("DISTINCT PKG_RETRIEVE_FACILITY.ROWID_OBJECT FCT_MDM_ID, ");
		sb.append("CLASS_CD FCT_CATEGORY, ");
		sb.append("FACILITY_NAME FCT_NAME, ");
		sb.append("PKG_RETRIEVE_FACILITY.HUB_STATE_IND FCT_HUB_STATE, ");
		sb.append("STATUS_CD FCT_STATUS, ");
		sb.append("PKG_RETRIEVE_FACILITY.CREATE_DATE FCT_CREATE_DT, ");
		sb.append("PKG_RETRIEVE_FACILITY.UPDATED_BY FCT_LAST_UPDATED_BY, ");
		sb.append("PKG_RETRIEVE_FACILITY.CREATOR FCT_CREATOR, ");
		sb.append("PKG_RETRIEVE_FACILITY.DELETED_BY FCT_DELETED_BY, ");
		sb.append("PKG_RETRIEVE_FACILITY.LAST_UPDATE_DATE FCT_LAST_UPDATE_DT, ");
		sb.append("PKG_RETRIEVE_FACILITY.LAST_ROWID_SYSTEM LAST_ROWID_SYSTEM, ");
		sb.append("PKG_RETRIEVE_FACILITY.DELETED_DATE FCT_DELETED_DT, ");
		sb.append("EXT_OWNED EXT_OWNED_FLAG, ");
		sb.append("EXT_EXPOSED EXT_EXPOSED_FLAG, ");
		sb.append("URL FCT_URL, ");
		sb.append("DODAAC DODAAC, ");
		sb.append("FCT_ALT_CODE_HUB_STATE FCT_ALT_CODE_HUB_STATE, ");
		sb.append("TYP_TYPE_ROWID FCT_ALT_CD_TYP_ROWID, ");
		sb.append("ALT_CODE_TYPE_CD ALT_CODE_TYPE_CD, ");
		sb.append("ALT_CODE_TYPE_NM ALT_CODE_TYPE_NM, ");
		sb.append("FCT_ALT_CODE FCT_ALT_CODE, ");
		sb.append("FCT_ADDR_REL_HUB_STATE FCT_ADDR_REL_HUB_STATE, ");
		sb.append("FACILITY_IDENTIFIER.ADDR_ROWID ADDR_MDM_ID, ");
		sb.append("FCT_ADDR_VALID_FROM_DT FCT_ADDR_VALID_FROM_DT, ");
		sb.append("FCT_ADDR_VALID_THRU_DT FCT_ADDR_VALID_THRU_DT, ");
		sb.append("ADDR_HUB_STATE ADDR_HUB_STATE, ");
		sb.append("ADDR_LAST_UPDATE_DT ADDR_LAST_UPDATE_DT, ");
		sb.append("ADDR_UPDATED_BY ADDR_UPDATED_BY, ");
		sb.append("ADDR_CREATE_DT ADDR_CREATE_DT, ");
		sb.append("ADDR_CREATOR ADDR_CREATOR, ");
		sb.append("HOUSE_NUM HOUSE_NUM, ");
		sb.append("DSTRCT DISTRICT, ");
		sb.append("PSTCD POSTAL_CODE, ");
		sb.append("TAX_JURN_CD TAX_JURN_CD, ");
		sb.append("PO_BOX PO_BOX, ");
		sb.append("STREET STREET, ");
		sb.append("ADDR_LN_2 BUILDING_NUM, ");
		sb.append("ADDR_LN_3 SUBURB, ");
		sb.append("CITY_MDM_ID CITY_MDM_ID, ");
		sb.append("CITY_GEOID CITY_GEOID, ");
		sb.append("CITY_GEOID.CITY_NAME CITY, ");
		sb.append("LAT_GEOSPTL LAT_GEOSPTL, ");
		sb.append("LNG_GEOSPTL LNG_GEOSPTL, ");
		sb.append("FACILITY_IDENTIFIER.TRTY_ROWID REGION_MDM_ID, ");
		sb.append("REGION_GEOID REGION_GEOID, ");
		sb.append("REGION_CODE ISO_REGION_CODE, ");
		sb.append("REGION_NAME REGION_NAME, ");
		sb.append("FACILITY_IDENTIFIER.CTRY_ROWID CTRY_MDM_ID, ");
		sb.append("COUNTRY_GEOID COUNTRY_GEOID, ");
		sb.append("COUNTRY_CODE ISO_COUNTRY_CODE, ");
		sb.append("COUNTRY_NAME COUNTRY_NAME, ");
		sb.append("C_FCT_DFND_REL.GDA_ROWID GDA_ROWID, ");
		sb.append("C_FCT_DFND_REL.VALID_FROM_DT FCT_DFND_REL_VALID_FROM_DT, ");
		sb.append("C_FCT_DFND_REL.VALID_THRU_DT FCT_DFND_REL_VALID_THRU_DT, ");
		sb.append("DECODE(OPEN_TIME_HRS || ':' || OPEN_TIME_MINS,':',NULL,OPEN_TIME_HRS || ':' || OPEN_TIME_MINS ) FCT_DAY_OPEN, ");
		sb.append("DECODE(CLOSE_TIME_HRS || ':' || CLOSE_TIME_MINS,':',NULL,CLOSE_TIME_HRS || ':' || CLOSE_TIME_MINS ) FCT_DAY_CLOSE, ");
		sb.append("DAY FCT_WORKING_DAYS, ");
		sb.append("FCT_OPS_TYPE_CD FCT_OPS_TYPE_CD, ");
		sb.append("FCT_OPS_TYPE_NM FCT_OPS_TYPE_NM, ");
		sb.append("FCT_OPS_TYPE_START_DT FCT_OPS_TYPE_START_DT, ");
		sb.append("FCT_OPS_TYPE_END_DT FCT_OPS_TYPE_END_DT, ");
		sb.append("WEIGHT_LMT_CRANE WEIGHT_LMT_CRANE, ");
		sb.append("WEIGHT_LMT_YARD WEIGHT_LMT_YARD, ");
		sb.append("VESSEL_AGENT VESSEL_AGENT, ");
		sb.append("GPS_FLAG GPS_FLAG, ");
		sb.append("GSM_FLAG GSM_FLAG, ");
		sb.append("OCE_FRGHT_PR OCE_FRGHT_PR, ");
		sb.append("GRP_CD GRP_CD, ");
		sb.append("VAS_CD FCT_OFFERING_CD, ");
		sb.append("OFF_NAME FCT_OFFERING_NAME, ");
		sb.append("OFF_DESC FCT_OFFERING_DESC, ");
		sb.append("FCT_OFFERING_START_DT FCT_OFFERING_START_DT, ");
		sb.append("FCT_OFFERING_END_DT FCT_OFFERING_END_DT, ");
		sb.append("TRNSP_CD FCT_TRANSPORT_CD, ");
		sb.append("TRNSP_NAME FCT_TRANSPORT_NAME, ");
		sb.append("TRNSP_DESC FCT_TRANSPORT_DESC, ");
		sb.append("TRNSP_START_DT TRNSP_START_DT, ");
		sb.append("TRNSP_END_DT FCT_TRNSP_END_DT, ");
		sb.append("COMMERCIAL_FCT_INFO.COMM_FCT_TYPE_CD, ");
		sb.append("COMMERCIAL_FCT_INFO.COMM_FCT_TYPE_NM, ");
		sb.append("COMMERCIAL_FCT_INFO.COMM_FCT_BRAND_CD, ");
		sb.append("COMMERCIAL_FCT_INFO.COMM_FCT_BRAND_NM, ");
		sb.append("COMMERCIAL_FCT_INFO.COMM_FCT_FUNC_CD, ");
		sb.append("COMMERCIAL_FCT_INFO.COMM_FCT_FUNC_CD, ");
		sb.append("COMMERCIAL_FCT_INFO.COMM_FCT_FUNC_NM, ");
		sb.append("COMMERCIAL_FCT_INFO.COMM_FCT_DIALING_CD, ");
		sb.append("COMMERCIAL_FCT_INFO.COMM_FCT_DAILNG_CD_DESC, ");
		sb.append("COMMERCIAL_FCT_INFO.TELECOM_NUM, ");
		sb.append("COMMERCIAL_FCT_INFO.IMP_MAIL, ");
		sb.append("COMMERCIAL_FCT_INFO.EXP_MAIL ");
		sb.append("FROM PKG_RETRIEVE_FACILITY ");
		sb.append("INNER JOIN FACILITY_IDENTIFIER ON PKG_RETRIEVE_FACILITY.ROWID_OBJECT = FACILITY_IDENTIFIER.FCT_ROWID ");
		sb.append("LEFT OUTER JOIN C_FCT_DFND_REL ON C_FCT_DFND_REL.FCT_ROWID = PKG_RETRIEVE_FACILITY.ROWID_OBJECT ");
		sb.append("INNER JOIN COUNTRY_GEOID ON COUNTRY_GEOID.COUNTRY_MDM_ID = PKG_RETRIEVE_FACILITY.CTRY_ROWID ");
		sb.append("INNER JOIN CITY_GEOID ON CITY_GEOID.CITY_MDM_ID = PKG_RETRIEVE_FACILITY.CITY_ROWID ");
		sb.append("LEFT OUTER JOIN C_FCT_OPNH ON C_FCT_OPNH.FCT_ROWID = PKG_RETRIEVE_FACILITY.ROWID_OBJECT ");
		sb.append("LEFT OUTER JOIN REGION_GEOID ON REGION_GEOID.REGION_MDM_ID = PKG_RETRIEVE_FACILITY.TRTY_ROWID ");
		sb.append("LEFT OUTER JOIN OPERATIONAL_FCT_INFO ON OPERATIONAL_FCT_INFO.FCT_ROWID = PKG_RETRIEVE_FACILITY.ROWID_OBJECT ");
		sb.append("LEFT OUTER JOIN FCT_OFFERING_INFO ON FCT_OFFERING_INFO.FCT_ROWID = PKG_RETRIEVE_FACILITY.ROWID_OBJECT ");
		sb.append("LEFT OUTER JOIN FCT_TRANSPORT_INFO ON FCT_TRANSPORT_INFO.FCT_ROWID = PKG_RETRIEVE_FACILITY.ROWID_OBJECT ");
		sb.append("LEFT OUTER JOIN COMMERCIAL_FCT_INFO ON COMMERCIAL_FCT_INFO.FCT_ROWID = PKG_RETRIEVE_FACILITY.ROWID_OBJECT ");
		sb.append("WHERE PKG_RETRIEVE_FACILITY.HUB_STATE_IND = 1 ");
		String logQuery = sb.toString();
		LOGGER.info("Retrieve query::" + logQuery);

		return sb;
	}



	/**
	 * Creates the prepared statement for search.
	 *
	 * @param con
	 *            the connection passed in from the client code.
	 * @param querySearchParty
	 *            the query search party
	 * @param parameterMap
	 *            the parameter map for parameters to be added in where clause
	 * @return the prepared statement constructed out of the input
	 * @throws SQLException
	 *             the SQL exception
	 */
	protected PreparedStatement createPreparedStatementForSearchFacility(Connection con, String querySearchParty, Map<Integer, String> parameterMap) throws SQLException {

		PreparedStatement stmt = con.prepareStatement(querySearchParty, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
		for (int i = 1; i <= parameterMap.size(); i++) {
			stmt.setString(i, (String) parameterMap.get(i));
		}
		LOGGER.warn(stmt.toString());

		return stmt;
	}

	protected String runValidateRkst(Connection con, String FacilityID) throws SQLException {
		String id = null;
		try {
			String sqlQuery = " SELECT CODE RKST FROM  C_FCT_ALT_CODES RKST_CODE WHERE RKST_CODE.TYP_TYPE_ROWID = ( SELECT ROWID_OBJECT FROM C_TYP_TYPE WHERE CODE = 'ALT_CODE.RKST') AND TRIM(CODE) =TRIM(?)";
			PreparedStatement stmt = con.prepareStatement(sqlQuery);
			stmt.setString(1, FacilityID);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				id = rs.getString("RKST");
			}
			stmt.close();
		} catch (Exception e) {
			LOGGER.info(e);
		}
		return id;
	}

	protected String runValidateBuid(Connection con, String FacilityID) throws SQLException {
		String id = null;
		try {
			String sqlQuery = " SELECT CODE RKST FROM  C_FCT_ALT_CODES RKST_CODE WHERE RKST_CODE.TYP_TYPE_ROWID = ( SELECT ROWID_OBJECT FROM C_TYP_TYPE WHERE CODE = 'ALT_CODE_BUID') AND TRIM(CODE) =TRIM(?)";
			PreparedStatement stmt = con.prepareStatement(sqlQuery);
			stmt.setString(1, FacilityID);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				id = rs.getString("RKST");
			}
			stmt.close();
		} catch (Exception e) {
			LOGGER.info(e);
		}
		return id;
	}

	protected String runValidateGeoID(Connection con, String FacilityID) throws SQLException {
		String id = null;
		try {
			String sqlQuery = "  SELECT CODE GEOID FROM  C_FCT_ALT_CODES GEOID WHERE GEOID.TYP_TYPE_ROWID = ( SELECT ROWID_OBJECT FROM C_TYP_TYPE WHERE CODE = 'ALT_CODE.GEOID') AND TRIM(CODE) =TRIM(?)";
			PreparedStatement stmt = con.prepareStatement(sqlQuery);
			stmt.setString(1, FacilityID);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				id = rs.getString("GEOID");
			}
			stmt.close();
		} catch (Exception e) {
			LOGGER.info(e);
		}
		return id;
	}

	/**
	 * Run fecth.
	 *
	 * @paramc
	 *            the c
	 * @paraminputMap
	 *            the input map
	 * @return the list
	 * @throws SQLException
	 *             the SQL exception
	 */
	protected RetrieveFacilityVO runRetrieveFacilityFecth1(Connection con, String FacilityID, Properties prop) throws SQLException {
		LOGGER.info("Invoked runfetch from helper class:: " + this.getClass().getCanonicalName());
		// StringBuilder sb = new QueryHelper().getRetrieveFacilityQuery();
		StringBuilder sb = new QueryHelper().getRetrieveAllFacilityQuery(prop);
		RetrieveFacilityVO vo = new RetrieveFacilityVO();
		PreparedStatement stmt = null;
		ResultSet rs = null;
		NamedParameterStatement p = null;


		try {
			p = new NamedParameterStatement(con, sb.toString());
			LOGGER.info("Query Parameter :--> " + sb);
			if (FacilityID != null) {
				p.setString("CODE", FacilityID);
			}
			/*
			 * PreparedStatement stmt = con.prepareStatement(sb.toString(),
			 * ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			 * stmt.setString(1, FacilityID); stmt.setString(2, FacilityID);
			 * stmt.setString(3, FacilityID); stmt.setString(4, FacilityID);
			 * stmt.setString(5, FacilityID); stmt.setString(6, FacilityID);
			 * stmt.setString(7, FacilityID); LOGGER.info("Retrieve Query:-->" +
			 * sb.toString() + "------->" + FacilityID + "<---------");
			 * ResultSet rs = stmt.executeQuery();
			 */
			rs = p.executeQuery();


			List<FacilityTypeVO> facilityTypes = null;
			List<TransportModesVO> transportMode = new ArrayList<TransportModesVO>();
			Map<String, List<OfferingVO>> facilityOfferingGroupMap = new HashMap<String, List<OfferingVO>>();
			Map<String, List<DayScheduleVO>> dayScheduleMap = new HashMap<String, List<DayScheduleVO>>();
			List<OfferingVO> offrList = null;
			List<DayScheduleVO> dayScheduleList = null;
			while (rs.next()) {
				vo.setFacilityID(rs.getString(DaoConstants.RETRIEVE_FACILITY_RESULTSET_FCT_ID));
				if (rs.getString(DaoConstants.RETRIEVE_FACILITY_RESULTSET_ALT_CODE_TYPE_CD).contains("GEOID")) {
					vo.setGeoID(rs.getString(DaoConstants.RETRIEVE_FACILITY_RESULTSET_FCT_ALT_CODE));
					String rkstCode = getRkstCode(rs.getString(DaoConstants.RETRIEVE_FACILITY_RESULTSET_FCT_ALT_CODE), con);
					if (rkstCode != null) {
						vo.setRkstCode(rkstCode);
					}
				} else if (rs.getString(DaoConstants.RETRIEVE_FACILITY_RESULTSET_ALT_CODE_TYPE_CD).contains("RKST")) {
					vo.setRkstCode(rs.getString(DaoConstants.RETRIEVE_FACILITY_RESULTSET_FCT_ALT_CODE));
					String geoCode = getGeoID(rs.getString(DaoConstants.RETRIEVE_FACILITY_RESULTSET_FCT_ALT_CODE), con);
					if (geoCode != null) {
						vo.setGeoID(geoCode);
					}
				} else if (rs.getString(DaoConstants.RETRIEVE_FACILITY_RESULTSET_ALT_CODE_TYPE_CD).contains("BUID")) {
					vo.setBusniessUnitId(rs.getString(DaoConstants.RETRIEVE_FACILITY_RESULTSET_FCT_ALT_CODE));

				}

				vo.setFacilityName(rs.getString(DaoConstants.RETRIEVE_FACILITY_RESULTSET_FCT_NAME));
				vo.setFacilityCategory(rs.getString(DaoConstants.RETRIEVE_FACILITY_RESULTSET_FCT_CATEGORY));
				if (rs.getString(DaoConstants.RETRIEVE_FACILITY_RESULTSET_FCT_OPS_TYPE_CD) != null) {
					if (facilityTypes == null) {
						facilityTypes = new ArrayList<FacilityTypeVO>();
					}
					if (!facilityTypes.contains(rs.getString(DaoConstants.RETRIEVE_FACILITY_RESULTSET_FCT_OPS_TYPE_CD))) {
						FacilityTypeVO ftvo = null;
						ValidDatePeriodVO vdpvo = null;
						if (rs.getDate(DaoConstants.RETRIEVE_FACILITY_RESULTSET_FCT_OPS_TYPE_ST) != null && rs.getDate(DaoConstants.RETRIEVE_FACILITY_RESULTSET_FCT_OPS_TYPE_END) != null) {
							vdpvo = new ValidDatePeriodVO();
							vdpvo.setValidFrom(XMLGregorianCalendarConversionUtil.asXMLGregorianCalendar(rs.getDate(DaoConstants.RETRIEVE_FACILITY_RESULTSET_FCT_OPS_TYPE_ST)));
							vdpvo.setValidTo(XMLGregorianCalendarConversionUtil.asXMLGregorianCalendar(rs.getDate(DaoConstants.RETRIEVE_FACILITY_RESULTSET_FCT_OPS_TYPE_END)));
						}
						if (vdpvo != null || rs.getString(DaoConstants.RETRIEVE_FACILITY_RESULTSET_FCT_OPS_TYPE_CD) != null) {
							ftvo = new FacilityTypeVO();
							ftvo.setValidDatePeriodVO(vdpvo);
							ftvo.setFacilityType(rs.getString(DaoConstants.RETRIEVE_FACILITY_RESULTSET_FCT_OPS_TYPE_CD));
							if (!facilityTypes.contains(ftvo)) {
								facilityTypes.add(ftvo);
							}
						}
					}
				}
				vo.setFacilityLifecycleStatus(rs.getString(DaoConstants.RETRIEVE_FACILITY_RESULTSET_FCT_STATUS));
				vo.setUrl(rs.getString(DaoConstants.RETRIEVE_FACILITY_RESULTSET_FCT_URL));
				vo.setDodaac(rs.getString(DaoConstants.RETRIEVE_FACILITY_RESULTSET_FCT_DODAAC));
				vo.setWeightLimitOnCranes(rs.getString(DaoConstants.RETRIEVE_FACILITY_RESULTSET_WEIGHT_LMT_CRANE));
				vo.setExternallyExposed(rs.getString(DaoConstants.RETRIEVE_FACILITY_RESULTSET_EXT_EXPOSED_FLAG));
				vo.setExternallyOwned(rs.getString(DaoConstants.RETRIEVE_FACILITY_RESULTSET_EXT_OWNED_FLAG));
				vo.setVesselAgent(rs.getString(DaoConstants.RETRIEVE_FACILITY_RESULTSET_VESSEL_AGENT));
				vo.setOceanFreightPricing(rs.getString(DaoConstants.RETRIEVE_FACILITY_RESULTSET_OCE_FRGHT_PR));
				vo.setWeightLimitInYard(rs.getString(DaoConstants.RETRIEVE_FACILITY_RESULTSET_WEIGHT_LMT_YARD));
				String key = null;
				if (rs.getString(DaoConstants.RETRIEVE_FACILITY_RESULTSET_FCT_WORKING_DAYS) == null) {
					key = "";
				} else {
					key = rs.getString(DaoConstants.RETRIEVE_FACILITY_RESULTSET_FCT_WORKING_DAYS);
				}
				if (dayScheduleMap.containsKey(key)) {
					dayScheduleList = dayScheduleMap.get(key);
					DayScheduleVO dayScheduleVO = new DayScheduleVO();
					dayScheduleVO.setOfficeOpeningHours(rs.getString(DaoConstants.RETRIEVE_FACILITY_RESULTSET_FCT_DAY_OPEN));
					dayScheduleVO.setOfficeClosingHours(rs.getString(DaoConstants.RETRIEVE_FACILITY_RESULTSET_FCT_DAY_CLOSE));
					if (!dayScheduleList.contains(dayScheduleVO)) {
						dayScheduleList.add(dayScheduleVO);
					}
					dayScheduleMap.put(key, dayScheduleList);
				} else {
					dayScheduleList = new ArrayList<DayScheduleVO>();
					DayScheduleVO dayScheduleVO = new DayScheduleVO();
					dayScheduleVO.setOfficeOpeningHours(rs.getString(DaoConstants.RETRIEVE_FACILITY_RESULTSET_FCT_DAY_OPEN));
					dayScheduleVO.setOfficeClosingHours(rs.getString(DaoConstants.RETRIEVE_FACILITY_RESULTSET_FCT_DAY_CLOSE));
					dayScheduleList.add(dayScheduleVO);
					dayScheduleMap.put(key, dayScheduleList);
				}
				vo.setDayScheduleMap(dayScheduleMap);
				if (rs.getString(DaoConstants.RETRIEVE_FACILITY_RESULTSET_FCT_TRANSPORT_CD) != null && !rs.getString(DaoConstants.RETRIEVE_FACILITY_RESULTSET_FCT_TRANSPORT_CD).isEmpty())
					if (!transportMode.contains(rs.getString(DaoConstants.RETRIEVE_FACILITY_RESULTSET_FCT_TRANSPORT_CD))) {
						TransportModesVO tmvo = new TransportModesVO();
						ValidDatePeriodVO vdpvo = new ValidDatePeriodVO();
						if (rs.getDate(DaoConstants.RETRIEVE_FACILITY_RESULTSET_TRNSP_START_DT) != null) {
							vdpvo.setValidFrom(XMLGregorianCalendarConversionUtil.asXMLGregorianCalendar(rs.getDate(DaoConstants.RETRIEVE_FACILITY_RESULTSET_TRNSP_START_DT)));
						}
						if (rs.getDate(DaoConstants.RETRIEVE_FACILITY_RESULTSET_FCT_TRNSP_END_DT) != null) {
							vdpvo.setValidTo(XMLGregorianCalendarConversionUtil.asXMLGregorianCalendar(rs.getDate(DaoConstants.RETRIEVE_FACILITY_RESULTSET_FCT_TRNSP_END_DT)));
						}
						tmvo.setValidDatePeriodVO(vdpvo);
						if (rs.getString(DaoConstants.RETRIEVE_FACILITY_RESULTSET_FCT_TRANSPORT_CD) != null) {
							tmvo.setTransportMode(rs.getString(DaoConstants.RETRIEVE_FACILITY_RESULTSET_FCT_TRANSPORT_CD));
						}
						if (!transportMode.contains(tmvo)) {
							transportMode.add(tmvo);
						}
					}
				vo.setHouseNo(rs.getString(DaoConstants.RETRIEVE_FACILITY_RESULTSET_HOUSE_NUM));
				vo.setStreetName(rs.getString(DaoConstants.RETRIEVE_FACILITY_RESULTSET_STREET));
				vo.setBuilding(rs.getString(DaoConstants.RETRIEVE_FACILITY_RESULTSET_BUILDING_NUM));
				vo.setSuburb(rs.getString(DaoConstants.RETRIEVE_FACILITY_RESULTSET_SUBURB));
				vo.setDistrict(rs.getString(DaoConstants.RETRIEVE_FACILITY_RESULTSET_DISTRICT));
				vo.setCity(rs.getString(DaoConstants.RETRIEVE_FACILITY_RESULTSET_CITY));
				vo.setRegion(rs.getString(DaoConstants.RETRIEVE_FACILITY_RESULTSET_ISO_REGION_CODE));
				vo.setIsoCountryCode(rs.getString(DaoConstants.RETRIEVE_FACILITY_RESULTSET_ISO_COUNTRY_CODE));
				vo.setPostalCode(rs.getString(DaoConstants.RETRIEVE_FACILITY_RESULTSET_POSTAL_CODE));
				vo.setFctCreationDate(XMLGregorianCalendarConversionUtil.asXMLGregorianCalendar(rs.getDate(DaoConstants.RETRIEVE_FACILITY_RESULTSET_FCT_CREATE_DT)));
				vo.setFctCreationUser(rs.getString(DaoConstants.RETRIEVE_FACILITY_RESULTSET_FCT_CREATOR));
				vo.setFctLastUpdateDate(XMLGregorianCalendarConversionUtil.asXMLGregorianCalendar(rs.getDate(DaoConstants.RETRIEVE_FACILITY_RESULTSET_FCT_LAST_UPDATE_DT)));
				vo.setFctLastUpdateUser(rs.getString(DaoConstants.RETRIEVE_FACILITY_RESULTSET_FCT_UPDATED_BY));
				vo.setAddCreationDate(XMLGregorianCalendarConversionUtil.asXMLGregorianCalendar(rs.getDate(DaoConstants.RETRIEVE_FACILITY_RESULTSET_FCT_CREATE_DT)));
				vo.setCreationUser(rs.getString(DaoConstants.RETRIEVE_FACILITY_RESULTSET_ADDR_CREATOR));
				vo.setLastUpdateDate(XMLGregorianCalendarConversionUtil.asXMLGregorianCalendar(rs.getDate(DaoConstants.RETRIEVE_FACILITY_RESULTSET_ADDR_LAST_UPDATE_DT)));
				vo.setLastUpdateUser(rs.getString(DaoConstants.RETRIEVE_FACILITY_RESULTSET_ADDR_UPDATED_BY));
				String offkey = null;
				if (rs.getString(DaoConstants.RETRIEVE_FACILITY_RESULTSET_GRP_CD) == null) {
					offkey = "";
				} else {
					offkey = rs.getString(DaoConstants.RETRIEVE_FACILITY_RESULTSET_GRP_CD);
				}
				if (facilityOfferingGroupMap.containsKey(offkey) && StringUtils.isNotBlank(rs.getString(DaoConstants.RETRIEVE_FACILITY_RESULTSET_FCT_OFFERING_NAME))) {
					offrList = facilityOfferingGroupMap.get(offkey);
					OfferingVO offeringVO = new OfferingVO();
					offeringVO.setOffCode(rs.getString(DaoConstants.RETRIEVE_FACILITY_RESULTSET_FCT_OFFERING_CD));
					offeringVO.setOffName(rs.getString(DaoConstants.RETRIEVE_FACILITY_RESULTSET_FCT_OFFERING_NAME));
					ValidDatePeriodVO validDatePeriodVO = new ValidDatePeriodVO();
					validDatePeriodVO.setValidFrom(XMLGregorianCalendarConversionUtil.asXMLGregorianCalendar(rs.getDate(DaoConstants.RETRIEVE_FACILITY_RESULTSET_FCT_OFFERING_START_DT)));
					validDatePeriodVO.setValidTo(XMLGregorianCalendarConversionUtil.asXMLGregorianCalendar(rs.getDate(DaoConstants.RETRIEVE_FACILITY_RESULTSET_FCT_OFFERING_END_DT)));
					offeringVO.setValidDatePeriodVO(validDatePeriodVO);
					if (!offrList.contains(offeringVO)) {
						offrList.add(offeringVO);
					}
					facilityOfferingGroupMap.put(offkey, offrList);
				} else if (StringUtils.isNotBlank(rs.getString(DaoConstants.RETRIEVE_FACILITY_RESULTSET_FCT_OFFERING_NAME))) {
					offrList = new ArrayList<OfferingVO>();
					OfferingVO offeringVO = new OfferingVO();
					offeringVO.setOffCode(rs.getString(DaoConstants.RETRIEVE_FACILITY_RESULTSET_FCT_OFFERING_CD));
					offeringVO.setOffName(rs.getString(DaoConstants.RETRIEVE_FACILITY_RESULTSET_FCT_OFFERING_NAME));
					ValidDatePeriodVO validDatePeriodVO = new ValidDatePeriodVO();
					validDatePeriodVO.setValidFrom(XMLGregorianCalendarConversionUtil.asXMLGregorianCalendar(rs.getDate(DaoConstants.RETRIEVE_FACILITY_RESULTSET_FCT_OFFERING_START_DT)));
					validDatePeriodVO.setValidTo(XMLGregorianCalendarConversionUtil.asXMLGregorianCalendar(rs.getDate(DaoConstants.RETRIEVE_FACILITY_RESULTSET_FCT_OFFERING_END_DT)));
					offeringVO.setValidDatePeriodVO(validDatePeriodVO);
					offrList.add(offeringVO);
					facilityOfferingGroupMap.put(offkey, offrList);
				}
				vo.setFacilityOfferingGroupMap(facilityOfferingGroupMap);
				vo.setFacilityROWID(rs.getString(DaoConstants.RETRIEVE_FACILITY_RESULTSET_FCT_ID));
				vo.setGpsFlag(rs.getString(DaoConstants.RETRIEVE_FACILITY_RESULTSET_GPS_FLAG));
				vo.setGsmFlag(rs.getString(DaoConstants.RETRIEVE_FACILITY_RESULTSET_GSM_FLAG));
				vo.setCreationDate(XMLGregorianCalendarConversionUtil.asXMLGregorianCalendar(rs.getDate(DaoConstants.RETRIEVE_FACILITY_RESULTSET_FCT_CREATE_DT)));
				vo.setDeletionDate(XMLGregorianCalendarConversionUtil.asXMLGregorianCalendar(rs.getDate(DaoConstants.RETRIEVE_FACILITY_RESULTSET_FCT_DELETED_DT)));
				vo.setCityGeoID(rs.getString(DaoConstants.RETRIEVE_FACILITY_RESULTSET_CITY_GEOID));
				vo.setCountryGeoID(rs.getString(DaoConstants.RETRIEVE_FACILITY_RESULTSET_COUNTRY_GEOID));
				vo.setRegionGeoID(rs.getString(DaoConstants.RETRIEVE_FACILITY_RESULTSET_REGION_GEOID));
				vo.setLatitude(rs.getString(DaoConstants.RETRIEVE_FACILITY_RESULTSET_LAT_GEOSPTL));
				vo.setLongitude(rs.getString(DaoConstants.RETRIEVE_FACILITY_RESULTSET_LNG_GEOSPTL));
				vo.setAddrRowID(rs.getString(DaoConstants.RETRIEVE_FACILITY_RESULTSET_ADDR_MDM_ID));
				vo.setValidFrom(XMLGregorianCalendarConversionUtil.asXMLGregorianCalendar(rs.getDate(DaoConstants.RETRIEVE_FACILITY_RESULTSET_FCT_ADDR_VALID_FROM_DT)));
				vo.setValidTo(XMLGregorianCalendarConversionUtil.asXMLGregorianCalendar(rs.getDate(DaoConstants.RETRIEVE_FACILITY_RESULTSET_FCT_ADDR_VALID_THRU_DT)));
				vo.setFacilityTypes(facilityTypes);
				vo.setTransportModes(transportMode);

				if (prop.getProperty("fct.commercial.switch").equalsIgnoreCase("on")) {
					vo.setBrandCode(rs.getString(DaoConstants.RETRIEVE_FACILITY_RESULTSET_FCT_COMM_FCT_BRAND_CD));
					vo.setBrandName(rs.getString(DaoConstants.RETRIEVE_FACILITY_RESULTSET_FCT_COMM_FCT_BRAND_NM));
					vo.setFunctionCode(rs.getString(DaoConstants.RETRIEVE_FACILITY_RESULTSET_FCT_COMM_FCT_FUNC_CD));
					vo.setFunctionName(rs.getString(DaoConstants.RETRIEVE_FACILITY_RESULTSET_FCT_COMM_FCT_FUNC_NM));
					vo.setDailingCode(rs.getString(DaoConstants.RETRIEVE_FACILITY_RESULTSET_FCT_COMM_FCT_DIALING_CD));
					vo.setDailingDesc(rs.getString(DaoConstants.RETRIEVE_FACILITY_RESULTSET_FCT_COMM_FCT_DAILNG_CD_DESC));
					vo.setTelecomNumber(rs.getString(DaoConstants.RETRIEVE_FACILITY_RESULTSET_FCT_COMM_TELECOM_NUM));
					vo.setImpMail(rs.getString(DaoConstants.RETRIEVE_FACILITY_RESULTSET_FCT_COMM_IMP_MAIL));
					vo.setExpMail(rs.getString(DaoConstants.RETRIEVE_FACILITY_RESULTSET_FCT_COMM_EXP_MAIL));
					vo.setCommercialFctType(rs.getString(DaoConstants.RETRIEVE_FACILITY_RESULTSET_FCT_COMM_FCT_TYPE_CD));
				}



			}
		} catch (SQLException e) {
			LOGGER.fatal(e.getLocalizedMessage());
			throw e;
		} finally {
			if (stmt != null) {
				stmt.close();
			}
			if (rs != null) {
				rs.close();
			}
			if (con != null) {
				con.close();
			}
		}
		return vo;
	}


	protected RetrieveFacilityVO runRetrieveFacilityFecth(Connection con, String facilityId, Properties prop) throws SQLException{

		RetrieveFacilityVO vo = new RetrieveFacilityVO();
		QueryHelper helper = new QueryHelper();
		String classCodeQuery=helper.getCategoryClassCode();
		String factClassCode=helper.getFactClassCodeValue(con,facilityId,classCodeQuery);
		String master_query="";
		LOGGER.info("factClassCode  IS :: "+ factClassCode);
		if(factClassCode!=null) {
			if(factClassCode.trim().equalsIgnoreCase("COMM")) {
				master_query=helper.getRetrieveCommFacilityAllQuery();

			}
			else if(factClassCode.trim().equalsIgnoreCase("OPS")) {
				master_query=getRetrieveOpsFacilityAllQuery();
			}
			else if(factClassCode.trim().equalsIgnoreCase("CUST")) {
				master_query=getRetrieveCustFacilityAllQuery();
			}
		}

		LOGGER.info("MASTER QUERY IS :: "+ master_query);
		//String master_query = helper.getRetrieveFacilityMasterQuery();
		String trans_day_query = helper.getRetrieveFacilityTransDayQuery();
		String offering_query = helper.getRetrieveFacilityOfferingQuery();

		try {
			/*
			 * vo = prepareMasterBean(con, facilityId, prop, master_query, vo); vo =
			 * prepareTransDayBean(con, facilityId, prop, trans_day_query, vo); vo =
			 * prepareOfferingBean(con, facilityId, prop, offering_query, vo);
			 */

			vo = prepareMasterBean(con, facilityId, prop, master_query, vo);
			vo = prepareTransDayBean(con, facilityId, prop, trans_day_query, vo);
			vo = prepareOfferingBean(con, facilityId, prop, offering_query, vo);

		}catch (SQLException e) {
			LOGGER.fatal(e.getLocalizedMessage());
			throw e;
		} finally {
			if (con != null) {
				con.close();
			}
		}

		return vo;

	}

	protected RetrieveFacilityVO prepareMasterBean(Connection con , String facilityId , Properties prop, String query, RetrieveFacilityVO vo) throws SQLException {

		PreparedStatement pstmt = null ;
		ResultSet rs = null ;
		NamedParameterStatement p = null;
		List<FacilityTypeVO> facilityTypes = null;

		try {
			p = new NamedParameterStatement(con, query);
			LOGGER.info("Master Query Parameter :--> " + query);
			if (facilityId != null) {
				try {
					p.setString("CODE", facilityId);
				} catch (Exception e) {
					e.printStackTrace();
					System.out.println(e.getLocalizedMessage());

				}
			}
			System.out.println("1st print "+p.getStatement());
			System.out.println("2nd print "+p.toString());
			rs = p.executeQuery();

			while(rs.next()) {
				vo.setFacilityID(rs.getString(DaoConstants.RETRIEVE_FACILITY_RESULTSET_FCT_ID));

				String alt_code_type_cd = rs.getString(DaoConstants.RETRIEVE_FACILITY_RESULTSET_ALT_CODE_TYPE_CD);
				String fct_alt_code = rs.getString(DaoConstants.RETRIEVE_FACILITY_RESULTSET_FCT_ALT_CODE);

				if (alt_code_type_cd.contains("GEOID")) {
					vo.setGeoID(rs.getString(DaoConstants.RETRIEVE_FACILITY_RESULTSET_FCT_ALT_CODE));
					String rkstCode = getRkstCode(fct_alt_code, con);
					if (rkstCode != null) {
						vo.setRkstCode(rkstCode);
					}
				} else if (alt_code_type_cd.contains("RKST")) {
					vo.setRkstCode(rs.getString(DaoConstants.RETRIEVE_FACILITY_RESULTSET_FCT_ALT_CODE));
					String geoCode = getGeoID(fct_alt_code, con);
					if (geoCode != null) {
						vo.setGeoID(geoCode);
					}
				} else if (alt_code_type_cd.contains("BUID")) {
					vo.setBusniessUnitId(fct_alt_code);

				}

				vo.setFacilityName(rs.getString(DaoConstants.RETRIEVE_FACILITY_RESULTSET_FCT_NAME));
				vo.setFacilityCategory(rs.getString(DaoConstants.RETRIEVE_FACILITY_RESULTSET_FCT_CATEGORY));
				String fct_ops_type_code =null;
				Date start_date = null;
				Date end_date = null;
				if(rs.getString(DaoConstants.RETRIEVE_FACILITY_RESULTSET_FCT_CATEGORY).trim().equalsIgnoreCase("OPS")) {
					fct_ops_type_code = rs.getString(DaoConstants.RETRIEVE_FACILITY_RESULTSET_FCT_OPS_TYPE_CD);
					start_date = rs.getDate(DaoConstants.RETRIEVE_FACILITY_RESULTSET_FCT_OPS_TYPE_ST);
					end_date = rs.getDate(DaoConstants.RETRIEVE_FACILITY_RESULTSET_FCT_OPS_TYPE_END);
				}
				if (fct_ops_type_code != null) {
					if (facilityTypes == null) {
						facilityTypes = new ArrayList<FacilityTypeVO>();
					}
					if (!facilityTypes.contains(fct_ops_type_code)) {
						FacilityTypeVO ftvo = null;
						ValidDatePeriodVO vdpvo = null;
						if (start_date != null && end_date != null) {
							vdpvo = new ValidDatePeriodVO();
							vdpvo.setValidFrom(XMLGregorianCalendarConversionUtil.asXMLGregorianCalendar(start_date));
							vdpvo.setValidTo(XMLGregorianCalendarConversionUtil.asXMLGregorianCalendar(end_date));
						}
						if (vdpvo != null || fct_ops_type_code != null) {
							ftvo = new FacilityTypeVO();
							ftvo.setValidDatePeriodVO(vdpvo);
							ftvo.setFacilityType(fct_ops_type_code);
							if (!facilityTypes.contains(ftvo)) {
								facilityTypes.add(ftvo);
							}
						}
					}
				}

				vo.setFacilityTypes(facilityTypes);

				vo.setFacilityLifecycleStatus(rs.getString(DaoConstants.RETRIEVE_FACILITY_RESULTSET_FCT_STATUS));
				vo.setUrl(rs.getString(DaoConstants.RETRIEVE_FACILITY_RESULTSET_FCT_URL));
				vo.setDodaac(rs.getString(DaoConstants.RETRIEVE_FACILITY_RESULTSET_FCT_DODAAC));
				vo.setExternallyExposed(rs.getString(DaoConstants.RETRIEVE_FACILITY_RESULTSET_EXT_EXPOSED_FLAG));
				vo.setExternallyOwned(rs.getString(DaoConstants.RETRIEVE_FACILITY_RESULTSET_EXT_OWNED_FLAG));

				if(rs.getString(DaoConstants.RETRIEVE_FACILITY_RESULTSET_FCT_CATEGORY).trim().equalsIgnoreCase("OPS")) {
					vo.setWeightLimitOnCranes(rs.getString(DaoConstants.RETRIEVE_FACILITY_RESULTSET_WEIGHT_LMT_CRANE));
					vo.setVesselAgent(rs.getString(DaoConstants.RETRIEVE_FACILITY_RESULTSET_VESSEL_AGENT));
					vo.setOceanFreightPricing(rs.getString(DaoConstants.RETRIEVE_FACILITY_RESULTSET_OCE_FRGHT_PR));
					vo.setWeightLimitInYard(rs.getString(DaoConstants.RETRIEVE_FACILITY_RESULTSET_WEIGHT_LMT_YARD));
					vo.setGpsFlag(rs.getString(DaoConstants.RETRIEVE_FACILITY_RESULTSET_GPS_FLAG));
					vo.setGsmFlag(rs.getString(DaoConstants.RETRIEVE_FACILITY_RESULTSET_GSM_FLAG));
				}
				vo.setHouseNo(rs.getString(DaoConstants.RETRIEVE_FACILITY_RESULTSET_HOUSE_NUM));
				vo.setStreetName(rs.getString(DaoConstants.RETRIEVE_FACILITY_RESULTSET_STREET));
				vo.setBuilding(rs.getString(DaoConstants.RETRIEVE_FACILITY_RESULTSET_BUILDING_NUM));
				vo.setSuburb(rs.getString(DaoConstants.RETRIEVE_FACILITY_RESULTSET_SUBURB));
				vo.setDistrict(rs.getString(DaoConstants.RETRIEVE_FACILITY_RESULTSET_DISTRICT));
				vo.setCity(rs.getString(DaoConstants.RETRIEVE_FACILITY_RESULTSET_CITY));
				vo.setRegion(rs.getString(DaoConstants.RETRIEVE_FACILITY_RESULTSET_ISO_REGION_CODE));
				vo.setIsoCountryCode(rs.getString(DaoConstants.RETRIEVE_FACILITY_RESULTSET_ISO_COUNTRY_CODE));
				vo.setPostalCode(rs.getString(DaoConstants.RETRIEVE_FACILITY_RESULTSET_POSTAL_CODE));
				vo.setFctCreationDate(XMLGregorianCalendarConversionUtil.asXMLGregorianCalendar(rs.getDate(DaoConstants.RETRIEVE_FACILITY_RESULTSET_FCT_CREATE_DT)));
				vo.setFctCreationUser(rs.getString(DaoConstants.RETRIEVE_FACILITY_RESULTSET_FCT_CREATOR));
				vo.setFctLastUpdateDate(XMLGregorianCalendarConversionUtil.asXMLGregorianCalendar(rs.getDate(DaoConstants.RETRIEVE_FACILITY_RESULTSET_FCT_LAST_UPDATE_DT)));
				vo.setFctLastUpdateUser(rs.getString(DaoConstants.RETRIEVE_FACILITY_RESULTSET_FCT_UPDATED_BY));
				vo.setAddCreationDate(XMLGregorianCalendarConversionUtil.asXMLGregorianCalendar(rs.getDate(DaoConstants.RETRIEVE_FACILITY_RESULTSET_FCT_CREATE_DT)));
				vo.setCreationUser(rs.getString(DaoConstants.RETRIEVE_FACILITY_RESULTSET_ADDR_CREATOR));
				vo.setLastUpdateDate(XMLGregorianCalendarConversionUtil.asXMLGregorianCalendar(rs.getDate(DaoConstants.RETRIEVE_FACILITY_RESULTSET_ADDR_LAST_UPDATE_DT)));
				vo.setLastUpdateUser(rs.getString(DaoConstants.RETRIEVE_FACILITY_RESULTSET_ADDR_UPDATED_BY));

				vo.setFacilityROWID(rs.getString(DaoConstants.RETRIEVE_FACILITY_RESULTSET_FCT_ID));
				vo.setCreationDate(XMLGregorianCalendarConversionUtil.asXMLGregorianCalendar(rs.getDate(DaoConstants.RETRIEVE_FACILITY_RESULTSET_FCT_CREATE_DT)));
				vo.setDeletionDate(XMLGregorianCalendarConversionUtil.asXMLGregorianCalendar(rs.getDate(DaoConstants.RETRIEVE_FACILITY_RESULTSET_FCT_DELETED_DT)));
				vo.setCityGeoID(rs.getString(DaoConstants.RETRIEVE_FACILITY_RESULTSET_CITY_GEOID));
				vo.setCountryGeoID(rs.getString(DaoConstants.RETRIEVE_FACILITY_RESULTSET_COUNTRY_GEOID));
				vo.setRegionGeoID(rs.getString(DaoConstants.RETRIEVE_FACILITY_RESULTSET_REGION_GEOID));
				vo.setLatitude(rs.getString(DaoConstants.RETRIEVE_FACILITY_RESULTSET_LAT_GEOSPTL));
				vo.setLongitude(rs.getString(DaoConstants.RETRIEVE_FACILITY_RESULTSET_LNG_GEOSPTL));
				vo.setAddrRowID(rs.getString(DaoConstants.RETRIEVE_FACILITY_RESULTSET_ADDR_MDM_ID));
				vo.setValidFrom(XMLGregorianCalendarConversionUtil.asXMLGregorianCalendar(rs.getDate(DaoConstants.RETRIEVE_FACILITY_RESULTSET_FCT_ADDR_VALID_FROM_DT)));
				vo.setValidTo(XMLGregorianCalendarConversionUtil.asXMLGregorianCalendar(rs.getDate(DaoConstants.RETRIEVE_FACILITY_RESULTSET_FCT_ADDR_VALID_THRU_DT)));

				if (prop.getProperty("fct.commercial.switch").equalsIgnoreCase("on")
						&& rs.getString(DaoConstants.RETRIEVE_FACILITY_RESULTSET_FCT_CATEGORY).trim().equalsIgnoreCase("COMM")) {
					vo.setBrandCode(rs.getString(DaoConstants.RETRIEVE_FACILITY_RESULTSET_FCT_COMM_FCT_BRAND_CD));
					vo.setBrandName(rs.getString(DaoConstants.RETRIEVE_FACILITY_RESULTSET_FCT_COMM_FCT_BRAND_NM));
					vo.setFunctionCode(rs.getString(DaoConstants.RETRIEVE_FACILITY_RESULTSET_FCT_COMM_FCT_FUNC_CD));
					vo.setFunctionName(rs.getString(DaoConstants.RETRIEVE_FACILITY_RESULTSET_FCT_COMM_FCT_FUNC_NM));
					vo.setDailingCode(rs.getString(DaoConstants.RETRIEVE_FACILITY_RESULTSET_FCT_COMM_FCT_DIALING_CD));
					vo.setDailingDesc(rs.getString(DaoConstants.RETRIEVE_FACILITY_RESULTSET_FCT_COMM_FCT_DAILNG_CD_DESC));
					vo.setTelecomNumber(rs.getString(DaoConstants.RETRIEVE_FACILITY_RESULTSET_FCT_COMM_TELECOM_NUM));
					vo.setImpMail(rs.getString(DaoConstants.RETRIEVE_FACILITY_RESULTSET_FCT_COMM_IMP_MAIL));
					vo.setExpMail(rs.getString(DaoConstants.RETRIEVE_FACILITY_RESULTSET_FCT_COMM_EXP_MAIL));
					vo.setCommercialFctType(rs.getString(DaoConstants.RETRIEVE_FACILITY_RESULTSET_FCT_COMM_FCT_TYPE_CD));
				}
			}

		}catch (SQLException e) {
			LOGGER.fatal(e.getLocalizedMessage());
			throw e;
		} finally {
			if (pstmt != null) {
				pstmt.close();
			}
			if (rs != null) {
				rs.close();
			}

		}

		return vo;
	}


	protected RetrieveFacilityVO prepareTransDayBean(Connection con , String facilityId , Properties prop, String query, RetrieveFacilityVO vo) throws SQLException{

		PreparedStatement pstmt = null ;
		ResultSet rs = null ;
		NamedParameterStatement p = null;

		List<TransportModesVO> transportMode = new ArrayList<TransportModesVO>();
		Map<String, List<DayScheduleVO>> dayScheduleMap = new HashMap<String, List<DayScheduleVO>>();
		List<DayScheduleVO> dayScheduleList = null;

		try {
			p = new NamedParameterStatement(con, query);
			LOGGER.info("Master Query Parameter :--> " + query);
			if (facilityId != null) {
				p.setString("CODE", facilityId);
			}
			rs = p.executeQuery();

			while(rs.next()) {

				String fct_working_days = rs.getString(DaoConstants.RETRIEVE_FACILITY_RESULTSET_FCT_WORKING_DAYS);
				String fct_day_open = rs.getString(DaoConstants.RETRIEVE_FACILITY_RESULTSET_FCT_DAY_OPEN);
				String fct_day_close =  rs.getString(DaoConstants.RETRIEVE_FACILITY_RESULTSET_FCT_DAY_CLOSE);

				String key = null;
				if (fct_working_days == null) {
					key = "";
				} else {
					key = fct_working_days;
				}
				if (dayScheduleMap.containsKey(key)) {
					dayScheduleList = dayScheduleMap.get(key);
					DayScheduleVO dayScheduleVO = new DayScheduleVO();
					dayScheduleVO.setOfficeOpeningHours(fct_day_open);
					dayScheduleVO.setOfficeClosingHours(fct_day_close);
					if (!dayScheduleList.contains(dayScheduleVO)) {
						dayScheduleList.add(dayScheduleVO);
					}
					dayScheduleMap.put(key, dayScheduleList);
				} else {
					dayScheduleList = new ArrayList<DayScheduleVO>();
					DayScheduleVO dayScheduleVO = new DayScheduleVO();
					dayScheduleVO.setOfficeOpeningHours(fct_day_open);
					dayScheduleVO.setOfficeClosingHours(fct_day_close);
					dayScheduleList.add(dayScheduleVO);
					dayScheduleMap.put(key, dayScheduleList);
				}
				vo.setDayScheduleMap(dayScheduleMap);


				String fct_transport_code = rs.getString(DaoConstants.RETRIEVE_FACILITY_RESULTSET_FCT_TRANSPORT_CD);
				Date transp_start_date = rs.getDate(DaoConstants.RETRIEVE_FACILITY_RESULTSET_TRNSP_START_DT);
				Date transp_end_date = rs.getDate(DaoConstants.RETRIEVE_FACILITY_RESULTSET_FCT_TRNSP_END_DT);

				if (fct_transport_code != null && !fct_transport_code.isEmpty())
					if (!transportMode.contains(fct_transport_code)) {
						TransportModesVO tmvo = new TransportModesVO();
						ValidDatePeriodVO vdpvo = new ValidDatePeriodVO();
						if (transp_start_date != null) {
							vdpvo.setValidFrom(XMLGregorianCalendarConversionUtil.asXMLGregorianCalendar(transp_start_date));
						}
						if (transp_end_date != null) {
							vdpvo.setValidTo(XMLGregorianCalendarConversionUtil.asXMLGregorianCalendar(transp_end_date));
						}
						tmvo.setValidDatePeriodVO(vdpvo);
						if (fct_transport_code != null) {
							tmvo.setTransportMode(fct_transport_code);
						}
						if (!transportMode.contains(tmvo)) {
							transportMode.add(tmvo);
						}
					}

				vo.setTransportModes(transportMode);
			}

		}catch (SQLException e) {
			LOGGER.fatal(e.getLocalizedMessage());
			throw e;
		} finally {
			if (pstmt != null) {
				pstmt.close();
			}
			if (rs != null) {
				rs.close();
			}

		}

		return vo;
	}

	protected RetrieveFacilityVO prepareOfferingBean(Connection con , String facilityId , Properties prop, String query, RetrieveFacilityVO vo) throws SQLException{

		PreparedStatement pstmt = null ;
		ResultSet rs = null ;
		NamedParameterStatement p = null;
		Map<String, List<OfferingVO>> facilityOfferingGroupMap = new HashMap<String, List<OfferingVO>>();
		List<OfferingVO> offrList = null;

		try {
			p = new NamedParameterStatement(con, query);
			LOGGER.info("Master Query Parameter :--> " + query);
			if (facilityId != null) {
				p.setString("CODE", facilityId);
			}
			rs = p.executeQuery();

			while(rs.next()) {

				String offkey = null;

				String grp_cd = rs.getString(DaoConstants.RETRIEVE_FACILITY_RESULTSET_GRP_CD);
				String off_code = rs.getString(DaoConstants.RETRIEVE_FACILITY_RESULTSET_FCT_OFFERING_CD);
				String fct_offering_name = rs.getString(DaoConstants.RETRIEVE_FACILITY_RESULTSET_FCT_OFFERING_NAME);
				Date fct_offering_start_date = rs.getDate(DaoConstants.RETRIEVE_FACILITY_RESULTSET_FCT_OFFERING_START_DT);
				Date fct_offering_end_date = rs.getDate(DaoConstants.RETRIEVE_FACILITY_RESULTSET_FCT_OFFERING_END_DT);

				if (grp_cd == null) {
					offkey = "";
				} else {
					offkey = grp_cd;
				}
				if (facilityOfferingGroupMap.containsKey(offkey) && StringUtils.isNotBlank(fct_offering_name)) {
					offrList = facilityOfferingGroupMap.get(offkey);
					OfferingVO offeringVO = new OfferingVO();
					offeringVO.setOffCode(off_code);
					offeringVO.setOffName(fct_offering_name);
					ValidDatePeriodVO validDatePeriodVO = new ValidDatePeriodVO();
					validDatePeriodVO.setValidFrom(XMLGregorianCalendarConversionUtil.asXMLGregorianCalendar(fct_offering_start_date));
					validDatePeriodVO.setValidTo(XMLGregorianCalendarConversionUtil.asXMLGregorianCalendar(fct_offering_end_date));
					offeringVO.setValidDatePeriodVO(validDatePeriodVO);
					if (!offrList.contains(offeringVO)) {
						offrList.add(offeringVO);
					}
					facilityOfferingGroupMap.put(offkey, offrList);
				} else if (StringUtils.isNotBlank(fct_offering_name)) {
					offrList = new ArrayList<OfferingVO>();
					OfferingVO offeringVO = new OfferingVO();
					offeringVO.setOffCode(off_code);
					offeringVO.setOffName(fct_offering_name);
					ValidDatePeriodVO validDatePeriodVO = new ValidDatePeriodVO();
					validDatePeriodVO.setValidFrom(XMLGregorianCalendarConversionUtil.asXMLGregorianCalendar(fct_offering_start_date));
					validDatePeriodVO.setValidTo(XMLGregorianCalendarConversionUtil.asXMLGregorianCalendar(fct_offering_end_date));
					offeringVO.setValidDatePeriodVO(validDatePeriodVO);
					offrList.add(offeringVO);
					facilityOfferingGroupMap.put(offkey, offrList);
				}
				vo.setFacilityOfferingGroupMap(facilityOfferingGroupMap);

			}

		}catch (SQLException e) {
			LOGGER.fatal(e.getLocalizedMessage());
			throw e;
		} finally {
			if (pstmt != null) {
				pstmt.close();
			}
			if (rs != null) {
				rs.close();
			}

		}

		return vo;
	}


	protected List<SearchFacilityVO> runSearchFacilityFecthWithNamedQuery(Connection c, Map<String, String> inputMap, SearchFacilityVO parameters, Properties prop) throws SQLException {

		List<SearchFacilityVO> resultList = null;
		String responseFacilityCategory="BLANK";
		String facilityCategory = null;
		StringBuilder sb = new StringBuilder();

		sb.append("WITH FACILITY_ADDRESS AS ");
		sb.append("(SELECT C_FCT_ADDR_REL.FCT_ROWID, ");
		sb.append("C_CTM_PSTL_ADDR.CTRY_ROWID, ");
		sb.append("C_CTM_PSTL_ADDR.CITY_ROWID, ");
		sb.append("C_CTM_PSTL_ADDR.TRTY_ROWID, ");
		sb.append("C_CTM_PSTL_ADDR.PO_BOX, ");
		sb.append("C_CTM_PSTL_ADDR.STREET, ");
		sb.append("C_CTM_PSTL_ADDR.HOUSE_NUM, ");
		sb.append("C_CTM_PSTL_ADDR.ADDR_LN_2, ");
		sb.append("C_CTM_PSTL_ADDR.ADDR_LN_3, ");
		sb.append("C_CTM_PSTL_ADDR.DSTRCT, ");
		sb.append("C_CTM_PSTL_ADDR.PSTCD, ");
		sb.append("C_CTM_PSTL_ADDR.LNG_GEOSPTL, ");
		sb.append("C_CTM_PSTL_ADDR.LAT_GEOSPTL ");
		sb.append("FROM ");
		sb.append("MDM_INFM_SMDS.C_FCT_ADDR_REL ");
		sb.append("INNER JOIN MDM_INFM_SMDS.C_CTM_PSTL_ADDR ON C_CTM_PSTL_ADDR.ROWID_OBJECT = C_FCT_ADDR_REL.ADDR_ROWID ");
		sb.append("INNER JOIN MDM_INFM_SMDS.C_FCT_FACILITY ON C_FCT_FACILITY.ROWID_OBJECT = C_FCT_ADDR_REL.FCT_ROWID ");
		sb.append("WHERE C_CTM_PSTL_ADDR.HUB_STATE_IND = 1  AND C_FCT_FACILITY.CLASS_CD='CUST' ");
		sb.append("AND LOWER ( NVL(C_CTM_PSTL_ADDR.HOUSE_NUM, 'XX') ) LIKE LOWER(NVL((:IN_HOUSE_NUM), NVL(C_CTM_PSTL_ADDR.HOUSE_NUM, 'XX'))) ");
		sb.append("AND LOWER ( NVL(C_CTM_PSTL_ADDR.STREET, 'XX') ) LIKE LOWER(NVL((:IN_STREET), NVL(C_CTM_PSTL_ADDR.STREET, 'XX'))) ");
		sb.append("AND LOWER ( NVL(C_CTM_PSTL_ADDR.PSTCD, 'XX') ) LIKE LOWER(NVL((:IN_POSTAL_CODE), NVL(C_CTM_PSTL_ADDR.PSTCD, 'XX'))) ");
		sb.append("AND LOWER ( NVL(C_CTM_PSTL_ADDR.LNG_GEOSPTL, 'XX') ) LIKE LOWER(NVL((:IN_LONGITUDE), NVL(C_CTM_PSTL_ADDR.LNG_GEOSPTL, 'XX'))) ");
		sb.append("AND LOWER ( NVL(C_CTM_PSTL_ADDR.LAT_GEOSPTL, 'XX') ) LIKE LOWER(NVL((:IN_LATITUDE), NVL(C_CTM_PSTL_ADDR.LAT_GEOSPTL, 'XX')))), ");
		sb.append("COUNTRY_DETAILS AS (SELECT C_GDA_DFND_AREA.ROWID_OBJECT COUNTRY_MDM_ID, C_GDA_DFND_AREA.NAME COUNTRY_NAME, ");
		sb.append("C_ALT_CODE.CODE COUNTRY_GEOID, COUNTRY_CODE.CODE COUNTRY_CODE ");
		sb.append("FROM MDM_INFM_SMDS.C_GDA_DFND_AREA INNER JOIN MDM_INFM_SMDS.C_ALT_CODE ON C_ALT_CODE.GDA_DFND_AREA_ROWID = C_GDA_DFND_AREA.ROWID_OBJECT ");
		sb.append("INNER JOIN MDM_INFM_SMDS.C_TYP_TYPE ON C_ALT_CODE.TYP_TYPE_ROWID = C_TYP_TYPE.ROWID_OBJECT ");
		sb.append("INNER JOIN MDM_INFM_SMDS.C_ALT_CODE COUNTRY_CODE ON COUNTRY_CODE.GDA_DFND_AREA_ROWID = C_GDA_DFND_AREA.ROWID_OBJECT ");
		sb.append("INNER JOIN MDM_INFM_SMDS.C_TYP_TYPE ISO_CTRY_TYPE ON COUNTRY_CODE.TYP_TYPE_ROWID = ISO_CTRY_TYPE.ROWID_OBJECT ");
		sb.append("WHERE C_GDA_DFND_AREA.HUB_STATE_IND = 1 AND C_GDA_DFND_AREA.ACTIVE_FLAG = 'Y' AND C_GDA_DFND_AREA.TYP_TYPE_CD = 'GDA.COUNTRY' ");
		sb.append("AND C_TYP_TYPE.CODE = 'ALT_CODE.GEOID' AND ISO_CTRY_TYPE.CODE = 'ALT_CODE.RKST' ");
		sb.append("AND LOWER ( COUNTRY_CODE.CODE ) LIKE LOWER(NVL((:IN_FCT_CTRY_CD), COUNTRY_CODE.CODE)) ");
		sb.append("AND LOWER ( C_ALT_CODE.CODE ) LIKE LOWER(NVL((:IN_CTRY_GEOID), C_ALT_CODE.CODE )) ), ");
		sb.append("CITY_DETAILS AS (SELECT C_GDA_DFND_AREA.ROWID_OBJECT CITY_MDM_ID, C_GDA_DFND_AREA.NAME CITY_NAME, ");
		sb.append("C_ALT_CODE.CODE CITY_GEOID FROM MDM_INFM_SMDS.C_GDA_DFND_AREA INNER JOIN MDM_INFM_SMDS.C_ALT_CODE ON C_ALT_CODE.GDA_DFND_AREA_ROWID = C_GDA_DFND_AREA.ROWID_OBJECT ");
		sb.append("INNER JOIN MDM_INFM_SMDS.C_TYP_TYPE ON C_ALT_CODE.TYP_TYPE_ROWID = C_TYP_TYPE.ROWID_OBJECT ");
		sb.append("WHERE C_GDA_DFND_AREA.HUB_STATE_IND = 1 AND C_GDA_DFND_AREA.ACTIVE_FLAG = 'Y' ");
		sb.append("AND C_GDA_DFND_AREA.TYP_TYPE_CD = 'GDA.CITY' AND C_TYP_TYPE.CODE = 'ALT_CODE.GEOID' ), ");
		sb.append("REGION_DETAILS AS ( SELECT DISTINCT C_GDA_DFND_AREA.ROWID_OBJECT REGION_MDM_ID, C_GDA_DFND_AREA.NAME REGION_NAME, ");
		sb.append("C_ALT_CODE.CODE REGION_GEOID, REGION_CODE.CODE REGION_CODE FROM MDM_INFM_SMDS.C_GDA_DFND_AREA ");
		sb.append("INNER JOIN MDM_INFM_SMDS.C_ALT_CODE ON C_ALT_CODE.GDA_DFND_AREA_ROWID = C_GDA_DFND_AREA.ROWID_OBJECT ");
		sb.append("INNER JOIN MDM_INFM_SMDS.C_TYP_TYPE ON C_ALT_CODE.TYP_TYPE_ROWID = C_TYP_TYPE.ROWID_OBJECT ");
		sb.append("INNER JOIN MDM_INFM_SMDS.C_ALT_CODE REGION_CODE ON REGION_CODE.GDA_DFND_AREA_ROWID = C_GDA_DFND_AREA.ROWID_OBJECT ");
		sb.append("INNER JOIN MDM_INFM_SMDS.C_TYP_TYPE ISO_TRTY_TYPE ON REGION_CODE.TYP_TYPE_ROWID = ISO_TRTY_TYPE.ROWID_OBJECT ");
		sb.append("WHERE C_GDA_DFND_AREA.HUB_STATE_IND = 1 AND C_GDA_DFND_AREA.ACTIVE_FLAG = 'Y' ");
		sb.append("AND C_GDA_DFND_AREA.TYP_TYPE_CD = 'GDA.STATE/PROV' ");
		sb.append("AND C_TYP_TYPE.CODE = 'ALT_CODE.GEOID'  ");
		sb.append("AND ISO_TRTY_TYPE.CODE = 'ALT_CODE.ISO_TRTY' ) ");
		sb.append("SELECT KK.* FROM ( ");
		sb.append("SELECT ");
		sb.append("C_FCT_FACILITY.ROWID_OBJECT FACILITY_MDM_ID, ");
		sb.append("C_FCT_FACILITY.HUB_STATE_IND HUB_STATE_IND, ");
		sb.append("C_FCT_FACILITY.FACILITY_NAME FACILITY_NAME, ");
		sb.append("C_FCT_FACILITY.EXT_OWNED EXT_OWNED, ");
		sb.append("C_FCT_FACILITY.CLASS_CD FCT_CATEGORY, ");
		sb.append("FCT_CATEGORY.DESCRIPTION FCT_CATEGORY_DESC, ");
		sb.append("C_FCT_FACILITY.STATUS_CD FACILITY_STATUS, ");
		sb.append("FCT_ALT_CODES_TYPE.CODE FCT_ALT_CODE_TYP_CD, ");
		sb.append("FCT_ALT_CODES_TYPE.NAME FCT_ALT_CODE_TYP_NM, ");
		sb.append("C_FCT_ALT_CODES.CODE FCT_ALT_CODE, ");
		sb.append("C_FCT_FACILITY.EXT_EXPOSED EXT_EXPOSED, ");
		sb.append("C_FCT_FACILITY.URL FACILITY_URL, ");
		sb.append("FACILITY_ADDRESS.PO_BOX PO_BOX, ");
		sb.append("FACILITY_ADDRESS.STREET STREET, FACILITY_ADDRESS.HOUSE_NUM HOUSE_NUM, ");
		sb.append("FACILITY_ADDRESS.ADDR_LN_2 BUILDING, FACILITY_ADDRESS.ADDR_LN_3 SUBURB, ");
		sb.append("FACILITY_ADDRESS.DSTRCT DISTRICT, CITY_DETAILS.CITY_NAME CITY, CITY_DETAILS.CITY_GEOID CITY_GEOID, ");
		sb.append("FACILITY_ADDRESS.PSTCD POST_CODE, FACILITY_ADDRESS.TRTY_ROWID TRTY_ROWID, ");
		sb.append("REGION_DETAILS.REGION_CODE REGION_ISO_CODE, REGION_DETAILS.REGION_GEOID REGION_GEOID, ");
		sb.append("REGION_DETAILS.REGION_NAME REGION_NAME, FACILITY_ADDRESS.CTRY_ROWID CTRY_ROWID, ");
		sb.append("COUNTRY_DETAILS.COUNTRY_CODE COUNTRY_CODE, COUNTRY_DETAILS.COUNTRY_GEOID COUNTRY_GEOID, ");
		sb.append("COUNTRY_DETAILS.COUNTRY_NAME COUNTRY_NAME, FACILITY_ADDRESS.LNG_GEOSPTL LNG_GEOSPATIAL, ");
		sb.append("FACILITY_ADDRESS.LAT_GEOSPTL LAT_GEOSPATIAL ");
		sb.append("FROM ");
		sb.append("MDM_INFM_SMDS.C_FCT_FACILITY ");
		sb.append("INNER JOIN MDM_INFM_SMDS.C_FCT_CLASS FCT_CATEGORY ON FCT_CATEGORY.CODE = C_FCT_FACILITY.CLASS_CD ");
		sb.append("INNER JOIN MDM_INFM_SMDS.C_FCT_STATUS ON C_FCT_STATUS.CODE = C_FCT_FACILITY.STATUS_CD ");
		sb.append("INNER JOIN MDM_INFM_SMDS.C_FCT_ALT_CODES ON C_FCT_ALT_CODES.FCT_ROWID = C_FCT_FACILITY.ROWID_OBJECT ");
		sb.append("INNER JOIN MDM_INFM_SMDS.C_TYP_TYPE FCT_ALT_CODES_TYPE ON C_FCT_ALT_CODES.TYP_TYPE_ROWID = FCT_ALT_CODES_TYPE.ROWID_OBJECT ");
		sb.append("INNER JOIN FACILITY_ADDRESS ON ( FACILITY_ADDRESS.FCT_ROWID = C_FCT_FACILITY.ROWID_OBJECT ");
		sb.append("AND LOWER ( NVL(FACILITY_ADDRESS.HOUSE_NUM, 'XX') ) LIKE LOWER(NVL((:IN_HOUSE_NUM), NVL(FACILITY_ADDRESS.HOUSE_NUM, 'XX'))) ");
		sb.append("AND LOWER ( NVL(FACILITY_ADDRESS.STREET, 'XX') ) LIKE LOWER(NVL((:IN_STREET), NVL(FACILITY_ADDRESS.STREET, 'XX'))) ");
		sb.append("AND LOWER ( NVL(FACILITY_ADDRESS.PSTCD, 'XX') ) LIKE LOWER(NVL((:IN_POSTAL_CODE), NVL(FACILITY_ADDRESS.PSTCD, 'XX'))) ");
		sb.append("AND LOWER ( NVL(FACILITY_ADDRESS.LNG_GEOSPTL, 'XX') ) LIKE LOWER(NVL((:IN_LONGITUDE), NVL(FACILITY_ADDRESS.LNG_GEOSPTL, 'XX'))) ");
		sb.append("AND LOWER ( NVL(FACILITY_ADDRESS.LAT_GEOSPTL, 'XX') ) LIKE LOWER(NVL((:IN_LATITUDE), NVL(FACILITY_ADDRESS.LAT_GEOSPTL, 'XX')))) ");
		sb.append("INNER JOIN COUNTRY_DETAILS ON ( FACILITY_ADDRESS.CTRY_ROWID = COUNTRY_DETAILS.COUNTRY_MDM_ID ");
		sb.append("AND LOWER ( COUNTRY_DETAILS.COUNTRY_CODE ) LIKE LOWER(NVL((:IN_FCT_CTRY_CD), COUNTRY_DETAILS.COUNTRY_CODE)) ");
		sb.append("AND LOWER ( COUNTRY_DETAILS.COUNTRY_GEOID ) LIKE LOWER(NVL((:IN_CTRY_GEOID), COUNTRY_DETAILS.COUNTRY_GEOID))) ");
		sb.append("INNER JOIN CITY_DETAILS ON ( FACILITY_ADDRESS.CITY_ROWID = CITY_DETAILS.CITY_MDM_ID ");
		sb.append("AND LOWER ( CITY_DETAILS.CITY_NAME ) LIKE LOWER(NVL((:IN_FCT_CITY_NM), CITY_DETAILS.CITY_NAME)) ");
		sb.append("AND LOWER ( CITY_DETAILS.CITY_GEOID ) LIKE LOWER(NVL((:IN_CITY_GEOID), CITY_DETAILS.CITY_GEOID)) ) ");
		sb.append("LEFT OUTER JOIN REGION_DETAILS ON ( FACILITY_ADDRESS.TRTY_ROWID = REGION_DETAILS.REGION_MDM_ID ");
		sb.append("AND LOWER ( NVL(REGION_DETAILS.REGION_GEOID, 'XX') ) LIKE LOWER(NVL((:IN_TRTY_GEOID), NVL(REGION_DETAILS.REGION_GEOID, 'XX'))) ");
		sb.append("AND LOWER ( NVL(REGION_DETAILS.REGION_CODE, 'XX') ) LIKE LOWER ( NVL((:IN_FCT_ISO_TRTY_CD), NVL(REGION_DETAILS.REGION_CODE, 'XX')) ) ) ");
		sb.append("WHERE C_FCT_FACILITY.CLASS_CD='CUST' ");
		sb.append(") KK ");
		sb.append("WHERE KK.HUB_STATE_IND = 1 ");
		sb.append("AND LOWER ( KK. FACILITY_STATUS ) = LOWER(NVL(:IN_FACILITY_STATUS, KK.FACILITY_STATUS)) ");
		sb.append("AND LOWER ( KK.FACILITY_NAME ) LIKE LOWER(NVL((:IN_FACILITY_NAME), KK.FACILITY_NAME)) ");
		sb.append("AND LOWER(KK.FCT_CATEGORY) LIKE LOWER(NVL((:IN_FCT_CATEGORY), KK.FCT_CATEGORY )) ");


		StringBuilder sb1 = new StringBuilder();

		sb1.append("WITH FACILITY_ADDR AS ");
		sb1.append("(SELECT C_FCT_ADDR_REL.FCT_ROWID, ");
		sb1.append("C_CTM_PSTL_ADDR.CTRY_ROWID, ");
		sb1.append("C_CTM_PSTL_ADDR.CITY_ROWID, ");
		sb1.append("C_CTM_PSTL_ADDR.TRTY_ROWID, ");
		sb1.append("C_CTM_PSTL_ADDR.PO_BOX, ");
		sb1.append("C_CTM_PSTL_ADDR.STREET, ");
		sb1.append("C_CTM_PSTL_ADDR.HOUSE_NUM, ");
		sb1.append("C_CTM_PSTL_ADDR.ADDR_LN_2, ");
		sb1.append("C_CTM_PSTL_ADDR.ADDR_LN_3, ");
		sb1.append("C_CTM_PSTL_ADDR.DSTRCT, ");
		sb1.append("C_CTM_PSTL_ADDR.PSTCD, ");
		sb1.append("C_CTM_PSTL_ADDR.LNG_GEOSPTL, ");
		sb1.append("C_CTM_PSTL_ADDR.LAT_GEOSPTL ");
		sb1.append("FROM ");
		sb1.append("C_FCT_ADDR_REL ");
		sb1.append("INNER JOIN C_CTM_PSTL_ADDR ON C_CTM_PSTL_ADDR.ROWID_OBJECT = C_FCT_ADDR_REL.ADDR_ROWID ");
		sb1.append("INNER JOIN C_FCT_FACILITY ON C_FCT_FACILITY.ROWID_OBJECT = C_FCT_ADDR_REL.FCT_ROWID ");
		sb1.append("WHERE C_CTM_PSTL_ADDR.HUB_STATE_IND = 1 AND C_FCT_FACILITY.CLASS_CD='COMM' ");
		sb1.append("AND LOWER ( NVL(C_CTM_PSTL_ADDR.HOUSE_NUM, 'XX') ) LIKE LOWER(NVL((:IN_HOUSE_NUM), NVL(C_CTM_PSTL_ADDR.HOUSE_NUM, 'XX'))) ");
		sb1.append("AND LOWER ( NVL(C_CTM_PSTL_ADDR.STREET, 'XX') ) LIKE LOWER(NVL((:IN_STREET), NVL(C_CTM_PSTL_ADDR.STREET, 'XX'))) ");
		sb1.append("AND LOWER ( NVL(C_CTM_PSTL_ADDR.PSTCD, 'XX') ) LIKE LOWER(NVL((:IN_POSTAL_CODE), NVL(C_CTM_PSTL_ADDR.PSTCD, 'XX'))) ");
		sb1.append("AND LOWER ( NVL(C_CTM_PSTL_ADDR.LNG_GEOSPTL, 'XX') ) LIKE LOWER(NVL((:IN_LONGITUDE), NVL(C_CTM_PSTL_ADDR.LNG_GEOSPTL, 'XX'))) ");
		sb1.append("AND LOWER ( NVL(C_CTM_PSTL_ADDR.LAT_GEOSPTL, 'XX') ) LIKE LOWER(NVL((:IN_LATITUDE), NVL(C_CTM_PSTL_ADDR.LAT_GEOSPTL, 'XX')))), ");
		sb1.append("COMMERCIAL_FCT_INFO AS (SELECT C_FCT_COM.FCT_ROWID, C_TYP_TYPE.CODE COMM_FCT_TYPE_CD, ");
		sb1.append("C_TYP_TYPE.CODE COMM_FCT_TYPE_NM, C_FCT_BRAND.CODE COMM_FCT_BRAND_CD, C_FCT_BRAND.NAME COMM_FCT_BRAND_NM, ");
		sb1.append("C_FCT_COMM_FUNC.CODE COMM_FCT_FUNC_CD, C_FCT_COMM_FUNC.NAME COMM_FCT_FUNC_NM, C_CTM_INTL_DIALNG_CD.DIALNG_CD, ");
		sb1.append("C_CTM_INTL_DIALNG_CD.DAILNG_CD_DESC, C_FCT_COM.TELECOM_NUM, C_FCT_COM.IMP_MAIL, C_FCT_COM.EXP_MAIL ");
		sb1.append("FROM C_FCT_COM INNER JOIN C_FCT_COMM_FUNC ON C_FCT_COM.COMM_FUNC_ROWID = C_FCT_COMM_FUNC.ROWID_OBJECT ");
		sb1.append("LEFT OUTER JOIN C_FCT_BRAND ON C_FCT_COM.FCT_BRAND_ROWID = C_FCT_BRAND.ROWID_OBJECT ");
		sb1.append("INNER JOIN C_TYP_TYPE ON C_FCT_COM.TYP_TYPE_ROWID = C_TYP_TYPE.ROWID_OBJECT ");
		sb1.append("LEFT OUTER JOIN C_CTM_INTL_DIALNG_CD ON C_FCT_COM.INTL_DIALING_ROWID = C_CTM_INTL_DIALNG_CD.ROWID_OBJECT ), ");
		sb1.append("FACILITY_OFF_INFO AS (SELECT C_FCT_OFF_REL.FCT_ROWID, C_FCT_OFF_REL.OFFERING_ROWID, ");
		sb1.append("C_FCT_OFF.OFF_NAME FCT_OFFERING_NM, C_FCT_OFF.OFF_DESC FCT_OFFERING_DESC, ");
		sb1.append("C_FCT_OFF.VAS_CD FCT_OFFERING_CD, C_FCT_OFF.GRP_CD FCT_OFFERING_GRP_CD, ");
		sb1.append("C_FCT_OFF.IS_ACTIVE_IND FCT_OFFERING_STATUS FROM C_FCT_OFF_REL ");
		sb1.append("INNER JOIN C_FCT_OFF ON C_FCT_OFF.ROWID_OBJECT = C_FCT_OFF_REL.OFFERING_ROWID ");
		sb1.append("INNER JOIN C_FCT_OFF_GRP ON C_FCT_OFF_GRP.GRP_CD = C_FCT_OFF.GRP_CD ");
		sb1.append("WHERE C_FCT_OFF_REL.HUB_STATE_IND = 1 AND TRUNC(C_FCT_OFF_REL.VALID_THRU_DT, 'DDD') >= TRUNC(SYSDATE, 'DDD') ), ");
		sb1.append("COUNTRY_DETAILS AS (SELECT C_GDA_DFND_AREA.ROWID_OBJECT COUNTRY_MDM_ID, C_GDA_DFND_AREA.NAME COUNTRY_NAME, ");
		sb1.append("C_ALT_CODE.CODE COUNTRY_GEOID, COUNTRY_CODE.CODE COUNTRY_CODE ");
		sb1.append("FROM C_GDA_DFND_AREA INNER JOIN C_ALT_CODE ON C_ALT_CODE.GDA_DFND_AREA_ROWID = C_GDA_DFND_AREA.ROWID_OBJECT ");
		sb1.append("INNER JOIN C_TYP_TYPE ON C_ALT_CODE.TYP_TYPE_ROWID = C_TYP_TYPE.ROWID_OBJECT ");
		sb1.append("INNER JOIN C_ALT_CODE COUNTRY_CODE ON COUNTRY_CODE.GDA_DFND_AREA_ROWID = C_GDA_DFND_AREA.ROWID_OBJECT ");
		sb1.append("INNER JOIN C_TYP_TYPE ISO_CTRY_TYPE ON COUNTRY_CODE.TYP_TYPE_ROWID = ISO_CTRY_TYPE.ROWID_OBJECT ");
		sb1.append("WHERE C_GDA_DFND_AREA.HUB_STATE_IND = 1 AND C_GDA_DFND_AREA.ACTIVE_FLAG = 'Y' AND C_GDA_DFND_AREA.TYP_TYPE_CD = 'GDA.COUNTRY' ");
		sb1.append("AND C_TYP_TYPE.CODE = 'ALT_CODE.GEOID' AND ISO_CTRY_TYPE.CODE = 'ALT_CODE.RKST' ");
		sb1.append("AND LOWER ( COUNTRY_CODE.CODE ) LIKE LOWER(NVL((:IN_FCT_CTRY_CD), COUNTRY_CODE.CODE)) ");
		sb1.append("AND LOWER ( C_ALT_CODE.CODE ) LIKE LOWER(NVL((:IN_CTRY_GEOID), C_ALT_CODE.CODE )) ), ");
		sb1.append("CITY_DETAILS AS (SELECT C_GDA_DFND_AREA.ROWID_OBJECT CITY_MDM_ID, C_GDA_DFND_AREA.NAME CITY_NAME, ");
		sb1.append("C_ALT_CODE.CODE CITY_GEOID FROM C_GDA_DFND_AREA INNER JOIN C_ALT_CODE ON C_ALT_CODE.GDA_DFND_AREA_ROWID = C_GDA_DFND_AREA.ROWID_OBJECT ");
		sb1.append("INNER JOIN C_TYP_TYPE ON C_ALT_CODE.TYP_TYPE_ROWID = C_TYP_TYPE.ROWID_OBJECT ");
		sb1.append("WHERE C_GDA_DFND_AREA.HUB_STATE_IND = 1 AND C_GDA_DFND_AREA.ACTIVE_FLAG = 'Y' ");
		sb1.append("AND C_GDA_DFND_AREA.TYP_TYPE_CD = 'GDA.CITY' AND C_TYP_TYPE.CODE = 'ALT_CODE.GEOID' ), ");
		sb1.append("REGION_DETAILS AS ( SELECT DISTINCT C_GDA_DFND_AREA.ROWID_OBJECT REGION_MDM_ID, C_GDA_DFND_AREA.NAME REGION_NAME, ");
		sb1.append("C_ALT_CODE.CODE REGION_GEOID, REGION_CODE.CODE REGION_CODE FROM C_GDA_DFND_AREA ");
		sb1.append("INNER JOIN C_ALT_CODE ON C_ALT_CODE.GDA_DFND_AREA_ROWID = C_GDA_DFND_AREA.ROWID_OBJECT ");
		sb1.append("INNER JOIN C_TYP_TYPE ON C_ALT_CODE.TYP_TYPE_ROWID = C_TYP_TYPE.ROWID_OBJECT ");
		sb1.append("INNER JOIN C_ALT_CODE REGION_CODE ON REGION_CODE.GDA_DFND_AREA_ROWID = C_GDA_DFND_AREA.ROWID_OBJECT ");
		sb1.append("INNER JOIN C_TYP_TYPE ISO_TRTY_TYPE ON REGION_CODE.TYP_TYPE_ROWID = ISO_TRTY_TYPE.ROWID_OBJECT ");
		sb1.append("WHERE C_GDA_DFND_AREA.HUB_STATE_IND = 1 AND C_GDA_DFND_AREA.ACTIVE_FLAG = 'Y' ");
		sb1.append("AND C_GDA_DFND_AREA.TYP_TYPE_CD = 'GDA.STATE/PROV' ");
		sb1.append("AND C_TYP_TYPE.CODE = 'ALT_CODE.GEOID' ");
		sb1.append("AND ISO_TRTY_TYPE.CODE = 'ALT_CODE.ISO_TRTY' ) ");
		sb1.append("SELECT KK.* FROM ( SELECT C_FCT_FACILITY.ROWID_OBJECT FACILITY_MDM_ID, ");
		sb1.append("C_FCT_FACILITY.HUB_STATE_IND HUB_STATE_IND, C_FCT_FACILITY.FACILITY_NAME FACILITY_NAME, ");
		sb1.append("C_FCT_FACILITY.EXT_OWNED EXT_OWNED, C_FCT_FACILITY.CLASS_CD FCT_CATEGORY, ");
		sb1.append("FCT_CATEGORY.DESCRIPTION FCT_CATEGORY_DESC, C_FCT_FACILITY.STATUS_CD FACILITY_STATUS, ");
		sb1.append("FCT_ALT_CODES_TYPE.CODE FCT_ALT_CODE_TYP_CD, FCT_ALT_CODES_TYPE.NAME FCT_ALT_CODE_TYP_NM, ");
		sb1.append("C_FCT_ALT_CODES.CODE FCT_ALT_CODE, C_FCT_FACILITY.EXT_EXPOSED EXT_EXPOSED, ");
		sb1.append("C_FCT_FACILITY.URL FACILITY_URL, COMMERCIAL_FCT_INFO.COMM_FCT_TYPE_CD COMM_FCT_TYPE_CD, ");
		sb1.append("COMMERCIAL_FCT_INFO.COMM_FCT_TYPE_NM COMM_FCT_TYPE_NM, COMMERCIAL_FCT_INFO.COMM_FCT_BRAND_CD COMM_FCT_BRAND_CD, ");
		sb1.append("COMMERCIAL_FCT_INFO. COMM_FCT_BRAND_NM COMM_FCT_BRAND_NM, COMMERCIAL_FCT_INFO.COMM_FCT_FUNC_CD COMM_FCT_FUNC_CD, ");
		sb1.append("COMMERCIAL_FCT_INFO.COMM_FCT_FUNC_NM COMM_FCT_FUNC_NM, COMMERCIAL_FCT_INFO.DIALNG_CD COMM_FCT_DIALING_CD, ");
		sb1.append("COMMERCIAL_FCT_INFO.DAILNG_CD_DESC COMM_FCT_DAILNG_CD_DESC, COMMERCIAL_FCT_INFO.TELECOM_NUM TELECOM_NUM, ");
		sb1.append("COMMERCIAL_FCT_INFO.IMP_MAIL IMP_MAIL, COMMERCIAL_FCT_INFO.EXP_MAIL EXP_MAIL, ");
		sb1.append("FACILITY_ADDRESS.PO_BOX PO_BOX, ");
		sb1.append("FACILITY_ADDRESS.STREET STREET, FACILITY_ADDRESS.HOUSE_NUM HOUSE_NUM, ");
		sb1.append("FACILITY_ADDRESS.ADDR_LN_2 BUILDING, FACILITY_ADDRESS.ADDR_LN_3 SUBURB, ");
		sb1.append("FACILITY_ADDRESS.DSTRCT DISTRICT, CITY_DETAILS.CITY_NAME CITY, CITY_DETAILS.CITY_GEOID CITY_GEOID, ");
		sb1.append("FACILITY_ADDRESS.PSTCD POST_CODE, FACILITY_ADDRESS.TRTY_ROWID TRTY_ROWID, ");
		sb1.append("REGION_DETAILS.REGION_CODE REGION_ISO_CODE, REGION_DETAILS.REGION_GEOID REGION_GEOID, ");
		sb1.append("REGION_DETAILS.REGION_NAME REGION_NAME, FACILITY_ADDRESS.CTRY_ROWID CTRY_ROWID, ");
		sb1.append("COUNTRY_DETAILS.COUNTRY_CODE COUNTRY_CODE, COUNTRY_DETAILS.COUNTRY_GEOID COUNTRY_GEOID, ");
		sb1.append("COUNTRY_DETAILS.COUNTRY_NAME COUNTRY_NAME, FACILITY_ADDRESS.LNG_GEOSPTL LNG_GEOSPATIAL, ");
		sb1.append("FACILITY_ADDRESS.LAT_GEOSPTL LAT_GEOSPATIAL, FACILITY_OFF_INFO.FCT_OFFERING_NM FCT_OFFERING_NM, ");
		sb1.append("FACILITY_OFF_INFO.FCT_OFFERING_DESC FCT_OFFERING_DESC, FACILITY_OFF_INFO.FCT_OFFERING_CD FCT_OFFERING_CD, ");
		sb1.append("FACILITY_OFF_INFO.FCT_OFFERING_GRP_CD FCT_OFFERING_GRP_CD, FACILITY_OFF_INFO.FCT_OFFERING_STATUS FCT_OFFERING_STATUS ");
		sb1.append("FROM ");
		sb1.append("C_FCT_FACILITY ");
		sb1.append("INNER JOIN C_FCT_CLASS FCT_CATEGORY ON FCT_CATEGORY.CODE = C_FCT_FACILITY.CLASS_CD ");
		sb1.append("INNER JOIN C_FCT_STATUS ON C_FCT_STATUS.CODE = C_FCT_FACILITY.STATUS_CD ");
		sb1.append("INNER JOIN C_FCT_ALT_CODES ON C_FCT_ALT_CODES.FCT_ROWID = C_FCT_FACILITY.ROWID_OBJECT ");
		sb1.append("INNER JOIN C_TYP_TYPE FCT_ALT_CODES_TYPE ON C_FCT_ALT_CODES.TYP_TYPE_ROWID = FCT_ALT_CODES_TYPE.ROWID_OBJECT ");
		sb1.append("INNER JOIN FACILITY_ADDR FACILITY_ADDRESS ON ( FACILITY_ADDRESS.FCT_ROWID = C_FCT_FACILITY.ROWID_OBJECT ");
		sb1.append("AND LOWER ( NVL(FACILITY_ADDRESS.HOUSE_NUM, 'XX') ) LIKE LOWER(NVL((:IN_HOUSE_NUM), NVL(FACILITY_ADDRESS.HOUSE_NUM, 'XX'))) ");
		sb1.append("AND LOWER ( NVL(FACILITY_ADDRESS.STREET, 'XX') ) LIKE LOWER(NVL((:IN_STREET), NVL(FACILITY_ADDRESS.STREET, 'XX'))) ");
		sb1.append("AND LOWER ( NVL(FACILITY_ADDRESS.PSTCD, 'XX') ) LIKE LOWER(NVL((:IN_POSTAL_CODE), NVL(FACILITY_ADDRESS.PSTCD, 'XX'))) ");
		sb1.append("AND LOWER ( NVL(FACILITY_ADDRESS.LNG_GEOSPTL, 'XX') ) LIKE LOWER(NVL((:IN_LONGITUDE), NVL(FACILITY_ADDRESS.LNG_GEOSPTL, 'XX'))) ");
		sb1.append("AND LOWER ( NVL(FACILITY_ADDRESS.LAT_GEOSPTL, 'XX') ) LIKE LOWER(NVL((:IN_LATITUDE), NVL(FACILITY_ADDRESS.LAT_GEOSPTL, 'XX')))) ");
		sb1.append("INNER JOIN COUNTRY_DETAILS ON ( FACILITY_ADDRESS.CTRY_ROWID = COUNTRY_DETAILS.COUNTRY_MDM_ID ");
		sb1.append("AND LOWER ( COUNTRY_DETAILS.COUNTRY_CODE ) LIKE LOWER(NVL((:IN_FCT_CTRY_CD), COUNTRY_DETAILS.COUNTRY_CODE)) ");
		sb1.append("AND LOWER ( COUNTRY_DETAILS.COUNTRY_GEOID ) LIKE LOWER(NVL((:IN_CTRY_GEOID), COUNTRY_DETAILS.COUNTRY_GEOID))) ");
		sb1.append("INNER JOIN CITY_DETAILS ON ( FACILITY_ADDRESS.CITY_ROWID = CITY_DETAILS.CITY_MDM_ID ");
		sb1.append("AND LOWER ( CITY_DETAILS.CITY_NAME ) LIKE LOWER(NVL((:IN_FCT_CITY_NM), CITY_DETAILS.CITY_NAME)) ");
		sb1.append("AND LOWER ( CITY_DETAILS.CITY_GEOID ) LIKE LOWER(NVL((:IN_CITY_GEOID), CITY_DETAILS.CITY_GEOID))) ");
		sb1.append("LEFT OUTER JOIN REGION_DETAILS ON ( FACILITY_ADDRESS.TRTY_ROWID = REGION_DETAILS.REGION_MDM_ID ");
		sb1.append("AND LOWER ( NVL(REGION_DETAILS.REGION_GEOID, 'XX') ) LIKE LOWER(NVL((:IN_TRTY_GEOID), NVL(REGION_DETAILS.REGION_GEOID, 'XX'))) ");
		sb1.append("AND LOWER ( NVL(REGION_DETAILS.REGION_CODE, 'XX') ) LIKE LOWER ( NVL((:IN_FCT_ISO_TRTY_CD), NVL(REGION_DETAILS.REGION_CODE, 'XX')) ) ) ");
		sb1.append("LEFT OUTER JOIN FACILITY_OFF_INFO ON ( FACILITY_OFF_INFO.FCT_ROWID = C_FCT_FACILITY.ROWID_OBJECT ) ");
		sb1.append("LEFT OUTER JOIN COMMERCIAL_FCT_INFO ON ( COMMERCIAL_FCT_INFO.FCT_ROWID = C_FCT_FACILITY.ROWID_OBJECT ");
		sb1.append("AND LOWER ( NVL(COMMERCIAL_FCT_INFO.COMM_FCT_TYPE_CD, 'XX') ) LIKE LOWER ( NVL ( (:IN_COMM_FCT_TYPE_CD), NVL(COMMERCIAL_FCT_INFO.COMM_FCT_TYPE_CD, 'XX') ) ) ");
		sb1.append("AND LOWER ( NVL(COMMERCIAL_FCT_INFO.COMM_FCT_BRAND_CD, 'XX') ) LIKE LOWER ( NVL ( (:IN_COMM_FCT_BRAND_CD), NVL(COMMERCIAL_FCT_INFO.COMM_FCT_BRAND_CD, 'XX') ) ) ");
		sb1.append("AND LOWER ( NVL(COMMERCIAL_FCT_INFO.COMM_FCT_FUNC_CD, 'XX') ) LIKE LOWER ( NVL ( (:IN_COMM_FCT_FUNC_CD), NVL(COMMERCIAL_FCT_INFO.COMM_FCT_FUNC_CD, 'XX') ) )) ");
		sb1.append("WHERE C_FCT_FACILITY.CLASS_CD='COMM' ) KK ");
		sb1.append("WHERE KK.HUB_STATE_IND = 1 ");
		sb1.append("AND LOWER ( KK. FACILITY_STATUS ) = LOWER(NVL(:IN_FACILITY_STATUS, KK.FACILITY_STATUS)) ");
		sb1.append("AND LOWER ( KK.FACILITY_NAME ) LIKE LOWER(NVL((:IN_FACILITY_NAME), KK.FACILITY_NAME)) ");
		sb1.append("AND LOWER(KK.FCT_CATEGORY) LIKE LOWER(NVL((:IN_FCT_CATEGORY), KK.FCT_CATEGORY )) ");

		StringBuilder sb2 = new StringBuilder();

		sb2.append("WITH FACILITY_ADDRESS AS ");
		sb2.append("(SELECT C_FCT_ADDR_REL.FCT_ROWID, ");
		sb2.append("C_CTM_PSTL_ADDR.CTRY_ROWID, ");
		sb2.append("C_CTM_PSTL_ADDR.CITY_ROWID, ");
		sb2.append("C_CTM_PSTL_ADDR.TRTY_ROWID, ");
		sb2.append("C_CTM_PSTL_ADDR.PO_BOX, ");
		sb2.append("C_CTM_PSTL_ADDR.STREET, ");
		sb2.append("C_CTM_PSTL_ADDR.HOUSE_NUM, ");
		sb2.append("C_CTM_PSTL_ADDR.ADDR_LN_2, ");
		sb2.append("C_CTM_PSTL_ADDR.ADDR_LN_3, ");
		sb2.append("C_CTM_PSTL_ADDR.DSTRCT, ");
		sb2.append("C_CTM_PSTL_ADDR.PSTCD, ");
		sb2.append("C_CTM_PSTL_ADDR.LNG_GEOSPTL, ");
		sb2.append("C_CTM_PSTL_ADDR.LAT_GEOSPTL ");
		sb2.append("FROM ");
		sb2.append("C_FCT_ADDR_REL ");
		sb2.append("INNER JOIN C_CTM_PSTL_ADDR ON C_CTM_PSTL_ADDR.ROWID_OBJECT = C_FCT_ADDR_REL.ADDR_ROWID ");
		sb2.append("INNER JOIN C_FCT_FACILITY ON C_FCT_FACILITY.ROWID_OBJECT = C_FCT_ADDR_REL.FCT_ROWID ");
		sb2.append("WHERE C_CTM_PSTL_ADDR.HUB_STATE_IND = 1 AND C_FCT_FACILITY.CLASS_CD='OPS' ");
		sb2.append("AND LOWER ( NVL(C_CTM_PSTL_ADDR.HOUSE_NUM, 'XX') ) LIKE LOWER(NVL((:IN_HOUSE_NUM), NVL(C_CTM_PSTL_ADDR.HOUSE_NUM, 'XX'))) ");
		sb2.append("AND LOWER ( NVL(C_CTM_PSTL_ADDR.STREET, 'XX') ) LIKE LOWER(NVL((:IN_STREET), NVL(C_CTM_PSTL_ADDR.STREET, 'XX'))) ");
		sb2.append("AND LOWER ( NVL(C_CTM_PSTL_ADDR.PSTCD, 'XX') ) LIKE LOWER(NVL((:IN_POSTAL_CODE), NVL(C_CTM_PSTL_ADDR.PSTCD, 'XX'))) ");
		sb2.append("AND LOWER ( NVL(C_CTM_PSTL_ADDR.LNG_GEOSPTL, 'XX') ) LIKE LOWER(NVL((:IN_LONGITUDE), NVL(C_CTM_PSTL_ADDR.LNG_GEOSPTL, 'XX'))) ");
		sb2.append("AND LOWER ( NVL(C_CTM_PSTL_ADDR.LAT_GEOSPTL, 'XX') ) LIKE LOWER(NVL((:IN_LATITUDE), NVL(C_CTM_PSTL_ADDR.LAT_GEOSPTL, 'XX'))) ), ");
		sb2.append("OPERATIONAL_FCT_INFO AS (SELECT C_FCT_OPS.ROWID_OBJECT, C_FCT_OPS.FCT_ROWID, C_FCT_OPS_TYP_REL.TYP_TYPE_ROWID FCT_OPS_TYPE_ROWID, ");
		sb2.append("C_TYP_TYPE.CODE FCT_OPS_TYPE_CD, C_TYP_TYPE.NAME FCT_OPS_TYPE_NM ");
		sb2.append("FROM C_FCT_OPS ");
		sb2.append("INNER JOIN C_FCT_OPS_TYP_REL ON C_FCT_OPS_TYP_REL.FCT_OPS_ROWID = C_FCT_OPS.ROWID_OBJECT ");
		sb2.append("INNER JOIN C_TYP_TYPE ON C_FCT_OPS_TYP_REL.TYP_TYPE_ROWID = C_TYP_TYPE.ROWID_OBJECT ");
		sb2.append("WHERE C_FCT_OPS_TYP_REL.HUB_STATE_IND = 1 AND TRUNC(C_FCT_OPS_TYP_REL.VALID_THRU_DT, 'DDD') >= TRUNC(SYSDATE, 'DDD') ), ");
		sb2.append("FACILITY_OFF_INFO AS (SELECT C_FCT_OFF_REL.FCT_ROWID, C_FCT_OFF_REL.OFFERING_ROWID, ");
		sb2.append("C_FCT_OFF.OFF_NAME FCT_OFFERING_NM, C_FCT_OFF.OFF_DESC FCT_OFFERING_DESC, ");
		sb2.append("C_FCT_OFF.VAS_CD FCT_OFFERING_CD, C_FCT_OFF.GRP_CD FCT_OFFERING_GRP_CD, ");
		sb2.append("C_FCT_OFF.IS_ACTIVE_IND FCT_OFFERING_STATUS FROM C_FCT_OFF_REL ");
		sb2.append("INNER JOIN C_FCT_OFF ON C_FCT_OFF.ROWID_OBJECT = C_FCT_OFF_REL.OFFERING_ROWID ");
		sb2.append("INNER JOIN C_FCT_OFF_GRP ON C_FCT_OFF_GRP.GRP_CD = C_FCT_OFF.GRP_CD ");
		sb2.append("WHERE C_FCT_OFF_REL.HUB_STATE_IND = 1 AND TRUNC(C_FCT_OFF_REL.VALID_THRU_DT, 'DDD') >= TRUNC(SYSDATE, 'DDD') ), ");
		sb2.append("COUNTRY_DETAILS AS (SELECT C_GDA_DFND_AREA.ROWID_OBJECT COUNTRY_MDM_ID, C_GDA_DFND_AREA.NAME COUNTRY_NAME, ");
		sb2.append("C_ALT_CODE.CODE COUNTRY_GEOID, COUNTRY_CODE.CODE COUNTRY_CODE ");
		sb2.append("FROM C_GDA_DFND_AREA INNER JOIN C_ALT_CODE ON C_ALT_CODE.GDA_DFND_AREA_ROWID = C_GDA_DFND_AREA.ROWID_OBJECT ");
		sb2.append("INNER JOIN C_TYP_TYPE ON C_ALT_CODE.TYP_TYPE_ROWID = C_TYP_TYPE.ROWID_OBJECT ");
		sb2.append("INNER JOIN C_ALT_CODE COUNTRY_CODE ON COUNTRY_CODE.GDA_DFND_AREA_ROWID = C_GDA_DFND_AREA.ROWID_OBJECT ");
		sb2.append("INNER JOIN C_TYP_TYPE ISO_CTRY_TYPE ON COUNTRY_CODE.TYP_TYPE_ROWID = ISO_CTRY_TYPE.ROWID_OBJECT ");
		sb2.append("WHERE C_GDA_DFND_AREA.HUB_STATE_IND = 1 AND C_GDA_DFND_AREA.ACTIVE_FLAG = 'Y' AND C_GDA_DFND_AREA.TYP_TYPE_CD = 'GDA.COUNTRY' ");
		sb2.append("AND C_TYP_TYPE.CODE = 'ALT_CODE.GEOID' AND ISO_CTRY_TYPE.CODE = 'ALT_CODE.RKST' ");
		sb2.append("AND LOWER ( COUNTRY_CODE.CODE ) LIKE LOWER(NVL((:IN_FCT_CTRY_CD), COUNTRY_CODE.CODE)) ");
		sb2.append("AND LOWER ( C_ALT_CODE.CODE ) LIKE LOWER(NVL((:IN_CTRY_GEOID), C_ALT_CODE.CODE ))), ");
		sb2.append("CITY_DETAILS AS (SELECT C_GDA_DFND_AREA.ROWID_OBJECT CITY_MDM_ID, C_GDA_DFND_AREA.NAME CITY_NAME, ");
		sb2.append("C_ALT_CODE.CODE CITY_GEOID FROM C_GDA_DFND_AREA INNER JOIN C_ALT_CODE ON C_ALT_CODE.GDA_DFND_AREA_ROWID = C_GDA_DFND_AREA.ROWID_OBJECT ");
		sb2.append("INNER JOIN C_TYP_TYPE ON C_ALT_CODE.TYP_TYPE_ROWID = C_TYP_TYPE.ROWID_OBJECT ");
		sb2.append("WHERE C_GDA_DFND_AREA.HUB_STATE_IND = 1 AND C_GDA_DFND_AREA.ACTIVE_FLAG = 'Y' ");
		sb2.append("AND C_GDA_DFND_AREA.TYP_TYPE_CD = 'GDA.CITY' AND C_TYP_TYPE.CODE = 'ALT_CODE.GEOID' ), ");
		sb2.append("REGION_DETAILS AS ( SELECT DISTINCT C_GDA_DFND_AREA.ROWID_OBJECT REGION_MDM_ID, C_GDA_DFND_AREA.NAME REGION_NAME, ");
		sb2.append("C_ALT_CODE.CODE REGION_GEOID, REGION_CODE.CODE REGION_CODE FROM C_GDA_DFND_AREA ");
		sb2.append("INNER JOIN C_ALT_CODE ON C_ALT_CODE.GDA_DFND_AREA_ROWID = C_GDA_DFND_AREA.ROWID_OBJECT ");
		sb2.append("INNER JOIN C_TYP_TYPE ON C_ALT_CODE.TYP_TYPE_ROWID = C_TYP_TYPE.ROWID_OBJECT ");
		sb2.append("INNER JOIN C_ALT_CODE REGION_CODE ON REGION_CODE.GDA_DFND_AREA_ROWID = C_GDA_DFND_AREA.ROWID_OBJECT ");
		sb2.append("INNER JOIN C_TYP_TYPE ISO_TRTY_TYPE ON REGION_CODE.TYP_TYPE_ROWID = ISO_TRTY_TYPE.ROWID_OBJECT ");
		sb2.append("WHERE C_GDA_DFND_AREA.HUB_STATE_IND = 1 AND C_GDA_DFND_AREA.ACTIVE_FLAG = 'Y' ");
		sb2.append("AND C_GDA_DFND_AREA.TYP_TYPE_CD = 'GDA.STATE/PROV' ");
		sb2.append("AND C_TYP_TYPE.CODE = 'ALT_CODE.GEOID' ");
		sb2.append("AND ISO_TRTY_TYPE.CODE = 'ALT_CODE.ISO_TRTY' ) ");
		sb2.append("SELECT KK.* FROM ( SELECT C_FCT_FACILITY.ROWID_OBJECT FACILITY_MDM_ID, ");
		sb2.append("C_FCT_FACILITY.HUB_STATE_IND HUB_STATE_IND, C_FCT_FACILITY.FACILITY_NAME FACILITY_NAME, ");
		sb2.append("C_FCT_FACILITY.EXT_OWNED EXT_OWNED, C_FCT_FACILITY.CLASS_CD FCT_CATEGORY, ");
		sb2.append("FCT_CATEGORY.DESCRIPTION FCT_CATEGORY_DESC, C_FCT_FACILITY.STATUS_CD FACILITY_STATUS, ");
		sb2.append("FCT_ALT_CODES_TYPE.CODE FCT_ALT_CODE_TYP_CD, FCT_ALT_CODES_TYPE.NAME FCT_ALT_CODE_TYP_NM, ");
		sb2.append("C_FCT_ALT_CODES.CODE FCT_ALT_CODE, C_FCT_FACILITY.EXT_EXPOSED EXT_EXPOSED, ");
		sb2.append("C_FCT_FACILITY.URL FACILITY_URL, ");
		sb2.append("OPERATIONAL_FCT_INFO.FCT_OPS_TYPE_CD FCT_OPS_TYPE_CD, ");
		sb2.append("OPERATIONAL_FCT_INFO.FCT_OPS_TYPE_NM FCT_OPS_TYPE_NM, ");
		sb2.append("FACILITY_ADDRESS.PO_BOX PO_BOX, ");
		sb2.append("FACILITY_ADDRESS.STREET STREET, FACILITY_ADDRESS.HOUSE_NUM HOUSE_NUM, ");
		sb2.append("FACILITY_ADDRESS.ADDR_LN_2 BUILDING, FACILITY_ADDRESS.ADDR_LN_3 SUBURB, ");
		sb2.append("FACILITY_ADDRESS.DSTRCT DISTRICT, CITY_DETAILS.CITY_NAME CITY, CITY_DETAILS.CITY_GEOID CITY_GEOID, ");
		sb2.append("FACILITY_ADDRESS.PSTCD POST_CODE, FACILITY_ADDRESS.TRTY_ROWID TRTY_ROWID, ");
		sb2.append("REGION_DETAILS.REGION_CODE REGION_ISO_CODE, REGION_DETAILS.REGION_GEOID REGION_GEOID, ");
		sb2.append("REGION_DETAILS.REGION_NAME REGION_NAME, FACILITY_ADDRESS.CTRY_ROWID CTRY_ROWID, ");
		sb2.append("COUNTRY_DETAILS.COUNTRY_CODE COUNTRY_CODE, COUNTRY_DETAILS.COUNTRY_GEOID COUNTRY_GEOID, ");
		sb2.append("COUNTRY_DETAILS.COUNTRY_NAME COUNTRY_NAME, FACILITY_ADDRESS.LNG_GEOSPTL LNG_GEOSPATIAL, ");
		sb2.append("FACILITY_ADDRESS.LAT_GEOSPTL LAT_GEOSPATIAL, FACILITY_OFF_INFO.FCT_OFFERING_NM FCT_OFFERING_NM, ");
		sb2.append("FACILITY_OFF_INFO.FCT_OFFERING_DESC FCT_OFFERING_DESC, FACILITY_OFF_INFO.FCT_OFFERING_CD FCT_OFFERING_CD, ");
		sb2.append("FACILITY_OFF_INFO.FCT_OFFERING_GRP_CD FCT_OFFERING_GRP_CD, FACILITY_OFF_INFO.FCT_OFFERING_STATUS FCT_OFFERING_STATUS ");
		sb2.append("FROM ");
		sb2.append("C_FCT_FACILITY ");
		sb2.append("INNER JOIN C_FCT_CLASS FCT_CATEGORY ON FCT_CATEGORY.CODE = C_FCT_FACILITY.CLASS_CD ");
		sb2.append("INNER JOIN C_FCT_STATUS ON C_FCT_STATUS.CODE = C_FCT_FACILITY.STATUS_CD ");
		sb2.append("INNER JOIN C_FCT_ALT_CODES ON C_FCT_ALT_CODES.FCT_ROWID = C_FCT_FACILITY.ROWID_OBJECT ");
		sb2.append("INNER JOIN C_TYP_TYPE FCT_ALT_CODES_TYPE ON C_FCT_ALT_CODES.TYP_TYPE_ROWID = FCT_ALT_CODES_TYPE.ROWID_OBJECT ");
		sb2.append("INNER JOIN FACILITY_ADDRESS FACILITY_ADDRESS ON ( FACILITY_ADDRESS.FCT_ROWID = C_FCT_FACILITY.ROWID_OBJECT ");
		sb2.append("AND LOWER ( NVL(FACILITY_ADDRESS.HOUSE_NUM, 'XX') ) LIKE LOWER(NVL((:IN_HOUSE_NUM), NVL(FACILITY_ADDRESS.HOUSE_NUM, 'XX'))) ");
		sb2.append("AND LOWER ( NVL(FACILITY_ADDRESS.STREET, 'XX') ) LIKE LOWER(NVL((:IN_STREET), NVL(FACILITY_ADDRESS.STREET, 'XX'))) ");
		sb2.append("AND LOWER ( NVL(FACILITY_ADDRESS.PSTCD, 'XX') ) LIKE LOWER(NVL((:IN_POSTAL_CODE), NVL(FACILITY_ADDRESS.PSTCD, 'XX'))) ");
		sb2.append("AND LOWER ( NVL(FACILITY_ADDRESS.LNG_GEOSPTL, 'XX') ) LIKE LOWER(NVL((:IN_LONGITUDE), NVL(FACILITY_ADDRESS.LNG_GEOSPTL, 'XX'))) ");
		sb2.append("AND LOWER ( NVL(FACILITY_ADDRESS.LAT_GEOSPTL, 'XX') ) LIKE LOWER(NVL((:IN_LATITUDE), NVL(FACILITY_ADDRESS.LAT_GEOSPTL, 'XX'))) ) ");
		sb2.append("INNER JOIN COUNTRY_DETAILS ON ( FACILITY_ADDRESS.CTRY_ROWID = COUNTRY_DETAILS.COUNTRY_MDM_ID ");
		sb2.append("AND LOWER ( COUNTRY_DETAILS.COUNTRY_CODE ) LIKE LOWER(NVL((:IN_FCT_CTRY_CD), COUNTRY_DETAILS.COUNTRY_CODE)) ");
		sb2.append("AND LOWER ( COUNTRY_DETAILS.COUNTRY_GEOID ) LIKE LOWER(NVL((:IN_CTRY_GEOID), COUNTRY_DETAILS.COUNTRY_GEOID)) ) ");
		sb2.append("INNER JOIN CITY_DETAILS ON ( FACILITY_ADDRESS.CITY_ROWID = CITY_DETAILS.CITY_MDM_ID ");
		sb2.append("AND LOWER ( CITY_DETAILS.CITY_NAME ) LIKE LOWER(NVL((:IN_FCT_CITY_NM), CITY_DETAILS.CITY_NAME)) ");
		sb2.append("AND LOWER ( CITY_DETAILS.CITY_GEOID ) LIKE LOWER(NVL((:IN_CITY_GEOID), CITY_DETAILS.CITY_GEOID))) ");
		sb2.append("LEFT OUTER JOIN REGION_DETAILS ON ( FACILITY_ADDRESS.TRTY_ROWID = REGION_DETAILS.REGION_MDM_ID ");
		sb2.append("AND LOWER ( NVL(REGION_DETAILS.REGION_GEOID, 'XX') ) LIKE LOWER(NVL((:IN_TRTY_GEOID), NVL(REGION_DETAILS.REGION_GEOID, 'XX'))) ");
		sb2.append("AND LOWER ( NVL(REGION_DETAILS.REGION_CODE, 'XX') ) LIKE LOWER ( NVL((:IN_FCT_ISO_TRTY_CD), NVL(REGION_DETAILS.REGION_CODE, 'XX')) ) ) ");
		sb2.append("LEFT OUTER JOIN OPERATIONAL_FCT_INFO ON ( OPERATIONAL_FCT_INFO.FCT_ROWID = C_FCT_FACILITY.ROWID_OBJECT ) ");
		sb2.append("LEFT OUTER JOIN FACILITY_OFF_INFO ON ( FACILITY_OFF_INFO.FCT_ROWID = C_FCT_FACILITY.ROWID_OBJECT ) ");
		sb2.append("WHERE C_FCT_FACILITY.CLASS_CD='OPS' ) KK ");
		sb2.append("WHERE KK.HUB_STATE_IND = 1 ");
		sb2.append("AND LOWER ( KK. FACILITY_STATUS ) = LOWER(NVL(:IN_FACILITY_STATUS, KK.FACILITY_STATUS)) ");
		sb2.append("AND LOWER ( KK.FACILITY_NAME ) LIKE LOWER(NVL((:IN_FACILITY_NAME), KK.FACILITY_NAME)) ");
		sb2.append("AND LOWER(KK.FCT_CATEGORY) LIKE LOWER(NVL((:IN_FCT_CATEGORY), KK.FCT_CATEGORY )) ");


		if (StringUtils.isNotBlank(parameters.getBrand())) {
			sb1.append(" AND LOWER(NVL(KK.COMM_FCT_BRAND_CD,'XX')) LIKE		 LOWER(NVL((:IN_COMM_FCT_BRAND_CD), 'XX')) ");
		}

		if (StringUtils.isNotBlank(parameters.getRegion())) {
			sb.append(" AND LOWER(NVL(KK.REGION_ISO_CODE,'XX')) LIKE		 LOWER(NVL((:IN_FCT_ISO_TRTY_CD), 'XX')) ");
			sb1.append(" AND LOWER(NVL(KK.REGION_ISO_CODE,'XX')) LIKE		 LOWER(NVL((:IN_FCT_ISO_TRTY_CD), 'XX')) ");
			sb2.append(" AND LOWER(NVL(KK.REGION_ISO_CODE,'XX')) LIKE		 LOWER(NVL((:IN_FCT_ISO_TRTY_CD), 'XX')) ");
		}

		if (StringUtils.isNotBlank(parameters.getRegionGeoID())) {
			sb.append(" AND LOWER(NVL(KK.REGION_GEOID,'XX')) LIKE		 LOWER(NVL((:IN_TRTY_GEOID), 'XX')) ");
			sb1.append(" AND LOWER(NVL(KK.REGION_GEOID,'XX')) LIKE		 LOWER(NVL((:IN_TRTY_GEOID), 'XX')) ");
			sb2.append(" AND LOWER(NVL(KK.REGION_GEOID,'XX')) LIKE		 LOWER(NVL((:IN_TRTY_GEOID), 'XX')) ");
		}

		// query.append(" AND ROWNUM <= " + prop.getProperty("record.limit"));
		String offerCodes = inputMap.get(DaoConstants.INPUTMAP_OFFER_CODES);
		if (offerCodes != null) {
			String[] offerCodesSplit = offerCodes.split(":");
			if (offerCodesSplit != null && offerCodesSplit.length > 0) {
				for (int i = 0; i < offerCodesSplit.length; i++) {
					if (i == 0) {
						sb1.append(addAnd() + "( ");
						sb2.append(addAnd() + "( ");
					} else {

						sb1.append(addOr());
						sb2.append(addOr());
					}


					sb1.append("EXISTS ");
					sb1.append("( ");
					sb1.append("   SELECT ");
					sb1.append("   1 ");
					sb1.append("   FROM FACILITY_OFF_INFO ");
					sb1.append("   WHERE KK.FACILITY_MDM_ID = FACILITY_OFF_INFO.FCT_ROWID ");
					sb1.append("   AND LOWER(FACILITY_OFF_INFO.FCT_OFFERING_NM) LIKE LOWER(:IN_" + i + ")");
					sb1.append(") ");

					sb2.append("EXISTS ");
					sb2.append("( ");
					sb2.append("   SELECT ");
					sb2.append("   1 ");
					sb2.append("   FROM FACILITY_OFF_INFO ");
					sb2.append("   WHERE KK.FACILITY_MDM_ID = FACILITY_OFF_INFO.FCT_ROWID ");
					sb2.append("   AND LOWER(FACILITY_OFF_INFO.FCT_OFFERING_NM) LIKE LOWER(:IN_" + i + ")");
					sb2.append(") ");

				}
				sb1.append(" )");
				sb2.append(" )");
			}
		}

		if (parameters.getFacilityTypes() != null && parameters.getFacilityTypes().size()>0) {
			String facilityTypes = inputMap.get(DaoConstants.INPUTMAP_FACILITY_TYPE);
			if (facilityTypes != null) {
				String[] facilityTypesSplit = facilityTypes.split(":");
				if (facilityTypesSplit != null && facilityTypesSplit.length > 0) {
					for (int i = 0; i < facilityTypesSplit.length; i++) {

						if (i == 0) {
							sb2.append(addAnd() + "( ");
						} else {
							sb2.append(addOr());
						}

						sb2.append("EXISTS ");
						sb2.append("( ");
						sb2.append("   SELECT ");
						sb2.append("   1 ");
						sb2.append("   FROM OPERATIONAL_FCT_INFO ");
						sb2.append("   WHERE KK.FACILITY_MDM_ID = OPERATIONAL_FCT_INFO.FCT_ROWID ");
						sb2.append("   AND LOWER ( OPERATIONAL_FCT_INFO.FCT_OPS_TYPE_CD ) LIKE LOWER(:IN_TYPE_" + i + ")");
						sb2.append(") ");

					}

					sb2.append(" )");
				}
			}
		}

		if (StringUtils.isNotBlank(parameters.getCommType())) {

			String commFacilityTypes = parameters.getCommType();
			if (commFacilityTypes != null) {
				String[] commFacilityTypesSplit = commFacilityTypes.split(":");
				if (commFacilityTypesSplit != null && commFacilityTypesSplit.length > 0) {
					for (int i = 0; i < commFacilityTypesSplit.length; i++) {

						if (i == 0) {
							sb1.append(addAnd() + "( ");

						} else {
							sb1.append(addOr());
						}

						sb1.append("EXISTS ");
						sb1.append("( ");
						sb1.append("   SELECT ");
						sb1.append("   1 ");
						sb1.append("   FROM COMMERCIAL_FCT_INFO ");
						sb1.append("   WHERE KK.FACILITY_MDM_ID = COMMERCIAL_FCT_INFO.FCT_ROWID ");



						sb1.append("   AND LOWER ");
						sb1.append("   ( ");
						sb1.append("      COMMERCIAL_FCT_INFO.COMM_FCT_TYPE_CD ");
						sb1.append("   ) ");
						sb1.append("   LIKE LOWER(:IN_COMM_FCT_TYPE_CD)");
						sb1.append(") ");



					}
					sb1.append(" )");
				}
			}

		}

		////////////////////// Brand exist//////////

		if (StringUtils.isNotBlank(parameters.getBrand())) {


			sb1.append(addAnd()+ " (" );


			sb1.append(" EXISTS ");
			sb1.append("( ");
			sb1.append("   SELECT ");
			sb1.append("   1 ");
			sb1.append("   FROM COMMERCIAL_FCT_INFO ");
			sb1.append("   WHERE KK.FACILITY_MDM_ID = COMMERCIAL_FCT_INFO.FCT_ROWID ");

			sb1.append("   AND LOWER ");
			sb1.append("   ( ");
			sb1.append("      NVL(KK.COMM_FCT_BRAND_CD,'XX') ");
			sb1.append("   ) ");
			sb1.append("   LIKE LOWER(NVL((:IN_COMM_FCT_BRAND_CD),'XX')) ");

			sb1.append(" ) )");
		}

		///////////////////////////////

		/////////////////////// Function exist/////////

		if (StringUtils.isNotBlank(parameters.getFunction())) {

			sb1.append(addAnd());

			sb1.append(" EXISTS (");
			sb1.append("( ");
			sb1.append("   SELECT ");
			sb1.append("   1 ");
			sb1.append("   FROM COMMERCIAL_FCT_INFO ");
			sb1.append("   WHERE KK.FACILITY_MDM_ID = COMMERCIAL_FCT_INFO.FCT_ROWID ");

			sb1.append("   AND LOWER ");
			sb1.append("   ( ");
			sb1.append("      COMMERCIAL_FCT_INFO.COMM_FCT_FUNC_CD  ");
			sb1.append("   ) ");
			sb1.append("   LIKE LOWER(:IN_COMM_FCT_FUNC_CD)");
			sb1.append(") ");

			sb1.append(" ) ");


		}

		///////////////////////////////

		if (StringUtils.isNotBlank(parameters.getFacilityCategory())) {
			facilityCategory = parameters.getFacilityCategory();
		}

		if(facilityCategory!= null) {
			sb.append(" AND ROWNUM < 300 ");
			sb1.append(" AND ROWNUM < 300 ");
			sb2.append(" AND ROWNUM < 300 ");
			LOGGER.info("Input query ::" + sb.toString());
			LOGGER.info("Input query ::" + sb1.toString());
			LOGGER.info("Input query ::" + sb2.toString());
		}
		else if(facilityCategory == null || StringUtils.isBlank(parameters.getFacilityCategory()))
		{
			sb.append(" AND ROWNUM < 100 ");
			sb1.append(" AND ROWNUM < 100 ");
			sb2.append(" AND ROWNUM < 100 ");
			LOGGER.info("Input query ::" + sb.toString());
			LOGGER.info("Input query ::" + sb1.toString());
			LOGGER.info("Input query ::" + sb2.toString());
		}




		StringBuilder commonsb = new StringBuilder();
		commonsb = null;

		if (StringUtils.isNotBlank(parameters.getFacilityCategory())) {
			if (facilityCategory.equalsIgnoreCase("CUST")) {
				commonsb = sb;
			} else if (facilityCategory.equalsIgnoreCase("COMM")) {
				commonsb = sb1;
			} else if (facilityCategory.equalsIgnoreCase("OPS")) {
				commonsb = sb2;
			}

		}


		PreparedStatement stmt = null;
		ResultSet rs = null;
		ResultSet rs1 = null;
		ResultSet rs2 = null;
		ResultSet rs3 = null;

		NamedParameterStatement p = null;
		NamedParameterStatement p1 = null;
		NamedParameterStatement p2 = null;
		NamedParameterStatement p3 = null;

		List<ResultSet> resultsets = new ArrayList<>();
		try {
			if(commonsb == null)
			{
				if(StringUtils.isNotBlank(parameters.getCommType()) ||
						StringUtils.isNotBlank(parameters.getBrand()) ||
						StringUtils.isNotBlank(parameters.getFunction()))
				{
					p2 = getExtractedQuery(c, inputMap, parameters, prop, sb1,"COMM");
					String querytext2 = p2.getStatement().toString();
					LOGGER.info("Query2::" + querytext2);
					rs2 = p2.executeQuery();
					resultsets.add(rs2);
				}


				else if(parameters.getFacilityTypes() != null && StringUtils.isNotBlank(parameters.getFacilityTypes().get(0))) {

					p3 = getExtractedQuery(c, inputMap, parameters, prop, sb2, "OPS");
					String querytext3 = p3.getStatement().toString();
					LOGGER.info("Query3::" + querytext3);
					rs3 = p3.executeQuery();
					resultsets.add(rs3);
				}




				else if(parameters.getOfferingCodes() != null && StringUtils.isNotBlank(parameters.getOfferingCodes().get(0))) {
					p2 = getExtractedQuery(c, inputMap, parameters, prop, sb1, "COMM");
					p3 = getExtractedQuery(c, inputMap, parameters, prop, sb2, "OPS");
					String querytext2 = p2.getStatement().toString();
					String querytext3 = p3.getStatement().toString();
					LOGGER.info("Query2::" + querytext2);
					LOGGER.info("Query3::" + querytext3);
					rs2 = p2.executeQuery();
					rs3 = p3.executeQuery();
					resultsets.add(rs2);
					resultsets.add(rs3);
				}

				else
				{
					p1 = getExtractedQuery(c, inputMap, parameters, prop, sb, "CUST");
					p2 = getExtractedQuery(c, inputMap, parameters, prop, sb1, "COMM");
					p3 = getExtractedQuery(c, inputMap, parameters, prop, sb2, "OPS");
					String querytext1 = p1.getStatement().toString();
					String querytext2 = p2.getStatement().toString();
					String querytext3 = p3.getStatement().toString();
					LOGGER.info("Query1::" + querytext1);
					LOGGER.info("Query2::" + querytext2);
					LOGGER.info("Query3::" + querytext3);
					rs1 = p1.executeQuery();
					rs2 = p2.executeQuery();
					rs3 = p3.executeQuery();
					resultsets.add(rs1);
					resultsets.add(rs2);
					resultsets.add(rs3);
				}


			}
			else
			{


				String finalQuery = commonsb.toString();
				String factCategory = parameters.getFacilityCategory();
				p = getExtractedQuery(c, inputMap, parameters, prop, commonsb,factCategory);
				String querytext = p.getStatement().toString();
				LOGGER.info("Query::" + querytext);
				rs = p.executeQuery();
				resultsets.add(rs);

			}

			resultList = new ArrayList<SearchFacilityVO>();


			// printResultSet(rs);

			// rs.beforeFirst();


			for(ResultSet rs4 : resultsets) {


				while (rs4.next()) {
					List<OfferingVO> offeringCodesList = null;
					List<String> facilityTypeList = new ArrayList<String>();
					SearchFacilityVO response = new SearchFacilityVO();
					Boolean found = false;
					String category  = rs4.getString(DaoConstants.RETRIEVE_FACILITY_RESULTSET_FCT_CATEGORY);
					if(category!=null ) {
						responseFacilityCategory = category.trim();
					}


					String fctRowID = rs4.getString(DaoConstants.RETRIEVE_FACILITY_RESULTSET_MDM_ID);
					// String fctRowID = getFacilityrowid(c,
					// rs.getString(DaoConstants.SEARCH_FACILITY_RESULTSET_FCT_RKST_ID));
					// String fctRowID = getFacilityrowid(c,
					// rs.getString(DaoConstants.RETRIEVE_FACILITY_RESULTSET_MDM_ID));

					// if(StringUtils.isBlank(fctRowID)){
					// fctRowID = getFacilityrowid(c,
					// rs.getString(DaoConstants.SEARCH_FACILITY_RESULTSET_FCT_RKST_ID));
					// }

					response.setFacilityID(fctRowID);

					if (resultList.contains(response)) {
						found = true;
						response = resultList.get(resultList.indexOf(response));
					}

					if (rs4.getString(DaoConstants.SEARCH_FACILITY_RESULTSET_FCT_GEO_ID).contains("GEOID")) {
						if (StringUtils.isBlank(response.getFacilityGEOId())) {
							response.setFacilityGEOId(rs4.getString(DaoConstants.SEARCH_FACILITY_RESULTSET_FCT_RKST_ID));
						}
//					String rkstCode = getRkstCode(rs4.getString(DaoConstants.SEARCH_FACILITY_RESULTSET_FCT_RKST_ID), c);

					} else if (rs4.getString(DaoConstants.SEARCH_FACILITY_RESULTSET_FCT_GEO_ID).contains("RKST")) {

						String rkstCode = rs4.getString(DaoConstants.SEARCH_FACILITY_RESULTSET_FCT_RKST_ID);
						if (rkstCode != null) {
							if (StringUtils.isBlank(response.getFacilityRKSTCode())) {
								response.setFacilityRKSTCode(rkstCode);
							}
						}


						if (StringUtils.isBlank(response.getFacilityRKSTCode())) {
							response.setFacilityRKSTCode(rs4.getString(DaoConstants.SEARCH_FACILITY_RESULTSET_FCT_RKST_ID));
						}
						String geoCode = getGeoID(rs4.getString(DaoConstants.SEARCH_FACILITY_RESULTSET_FCT_RKST_ID), c);
						if (geoCode != null) {
							if (StringUtils.isBlank(response.getFacilityGEOId())) {
								response.setFacilityGEOId(geoCode);
							}
						}
					} else if (rs4.getString(DaoConstants.SEARCH_FACILITY_RESULTSET_FCT_GEO_ID).contains("BUID")) {

						if (StringUtils.isBlank(response.getBusinessUnitId())) {
							response.setBusinessUnitId(rs4.getString(DaoConstants.SEARCH_FACILITY_RESULTSET_FCT_RKST_ID));
						}

					}
					// if (resultList.contains(response)) {
					// found = true;
					// response = resultList.get(resultList.indexOf(response));
					if (response.getFacilityOffering() != null) {
						offeringCodesList = response.getFacilityOffering();
					}
					if (response.getFacilityTypes() != null) {
						facilityTypeList = response.getFacilityTypes();
					} else {
						response.setFacilityTypes(new ArrayList<String>());
						facilityTypeList = response.getFacilityTypes();
					}
					// }

					if(responseFacilityCategory.equalsIgnoreCase("OPS") || responseFacilityCategory.equalsIgnoreCase("COMM") || responseFacilityCategory.equalsIgnoreCase("BLANK")) {
						if (rs4.getString(DaoConstants.SEARCH_FACILITY_RESULTSET_FCT_OFFERING_NM) != null) {
							if (offeringCodesList == null) {
								offeringCodesList = new ArrayList<OfferingVO>();
							}
							OfferingVO offer = new OfferingVO();
							offer.setOffName(rs4.getString(DaoConstants.SEARCH_FACILITY_RESULTSET_FCT_OFFERING_NM));
							offer.setOffCode(rs4.getString(DaoConstants.SEARCH_FACILITY_RESULTSET_COLUMN_OFFER_CODE));
							if (StringUtils.isNotBlank(offer.getOffName()) && !offeringCodesList.contains(offer)) {
								offeringCodesList.add(offer);
							}
						}
					}
					if(responseFacilityCategory.equalsIgnoreCase("OPS") || responseFacilityCategory.equalsIgnoreCase("BLANK")) {
						if (!facilityTypeList.contains(rs4.getString(DaoConstants.SEARCH_FACILITY_RESULTSET_COLUMN_TYPE))) {
							facilityTypeList.add(rs4.getString(DaoConstants.SEARCH_FACILITY_RESULTSET_COLUMN_TYPE));
						}
					}
					response.setFacilityName(rs4.getString(DaoConstants.SEARCH_FACILITY_RESULTSET_COLUMN_NAME));
					response.setFacilityCategory(rs4.getString(DaoConstants.SEARCH_FACILITY_RESULTSET_COLUMN_CATEGORY));
					response.setCountry(rs4.getString(DaoConstants.SEARCH_FACILITY_RESULTSET_COLUMN_COUNTRY));
					response.setFacilityLifecycleStatus(rs4.getString(DaoConstants.SEARCH_FACILITY_RESULTSET_FCT_STATUS));
					response.setHouseNo(rs4.getString(DaoConstants.SEARCH_FACILITY_RESULTSET_COLUMN_HOUSE_NO));
					response.setPostalCode(rs4.getString(DaoConstants.SEARCH_FACILITY_RESULTSET_COLUMN_POSTAL_CODE));
					response.setStreetName(rs4.getString(DaoConstants.SEARCH_FACILITY_RESULTSET_COLUMN_STREET_NAME));
					response.setIsoCountryCode(rs4.getString(DaoConstants.SEARCH_FACILITY_RESULTSET_COLUMN_COUNTRY_CODE));
					response.setCountryGeoID(rs4.getString(DaoConstants.SEARCH_FACILITY_RESULTSET_COLUMN_COUNTRY_GEOID));
					response.setCity(rs4.getString(DaoConstants.SEARCH_FACILITY_RESULTSET_COLUMN_CITY));
					response.setCityGeoID(rs4.getString(DaoConstants.SEARCH_FACILITY_RESULTSET_COLUMN_CITY_GEOID));
					response.setRegion(rs4.getString(DaoConstants.SEARCH_FACILITY_RESULTSET_COLUMN_REGION_ISO_CODE));
					response.setRegionGeoID(rs4.getString(DaoConstants.SEARCH_FACILITY_RESULTSET_COLUMN_REGION_GEOID));
					response.setLatitude(rs4.getString(DaoConstants.SEARCH_FACILITY_RESULTSET_COLUMN_LATITUDE));
					response.setLongitude(rs4.getString(DaoConstants.SEARCH_FACILITY_RESULTSET_COLUMN_LONGITUDE));
					if (prop.getProperty("fct.commercial.switch").equalsIgnoreCase("on")
							&& responseFacilityCategory.equalsIgnoreCase("COMM") || responseFacilityCategory.equalsIgnoreCase("BLANK")) {
						response.setBrand(rs4.getString(DaoConstants.SEARCH_FACILITY_RESULTSET_COLUMN_COMM_FCT_BRAND_CD));
						response.setFunction(rs4.getString(DaoConstants.SEARCH_FACILITY_RESULTSET_COLUMN_COMM_FCT_FUNC_CD));
						response.setCommType(rs4.getString(DaoConstants.SEARCH_FACILITY_RESULTSET_COLUMN_COMM_FCT_TYPE_CD));
					}
					response.setFacilityTypes(facilityTypeList);
					response.setFacilityOffering(offeringCodesList);
					if (!found) {
						resultList.add(response);
					}
				}
			}

		} catch (SQLException e) {
			LOGGER.fatal(e.getLocalizedMessage());
			throw e;
		} finally {
			if (stmt != null) {
				stmt.close();
			}
			if (rs != null) {
				rs.close();
			}
			if (rs1 != null) {
				rs1.close();
			}
			if (rs2 != null) {
				rs2.close();
			}
			if (rs3 != null) {
				rs3.close();
			}
			if (c != null) {
				c.close();
			}
		}
		LOGGER.info("total records found-------------->" + resultList.size());
		return resultList;
	}


	private NamedParameterStatement getExtractedQuery(Connection c, Map<String, String> inputMap, SearchFacilityVO parameters, Properties prop, StringBuilder commonsb, String categoryParam) throws SQLException {
		NamedParameterStatement p;
		p = new NamedParameterStatement(c, commonsb.toString());
		String retrieveFacilityCategory="BLANK";
		String facilityCategory = parameters.getFacilityCategory();
		if(facilityCategory!=null) {
			retrieveFacilityCategory = facilityCategory.trim();
		}
		if (parameters.getHouseNo() != null) {
			p.setString("IN_HOUSE_NUM", appendPattern(parameters.getHouseNo()));
		} else {
			p.setString("IN_HOUSE_NUM", null);
		}



		if (prop.getProperty("fct.commercial.switch").equalsIgnoreCase("on")) {

			if (categoryParam.equalsIgnoreCase("COMM") && (retrieveFacilityCategory.equalsIgnoreCase("COMM")
					|| retrieveFacilityCategory.equalsIgnoreCase("BLANK"))) {

				if (parameters.getBrand() != null) {
					p.setString("IN_COMM_FCT_BRAND_CD", appendPattern(parameters.getBrand()));
				} else {
					p.setString("IN_COMM_FCT_BRAND_CD", null);
				}


				if (parameters.getFunction() != null) {
					p.setString("IN_COMM_FCT_FUNC_CD", appendPattern(parameters.getFunction()));
				} else {
					p.setString("IN_COMM_FCT_FUNC_CD", null);
				}


				if (parameters.getCommType() != null) {
					p.setString("IN_COMM_FCT_TYPE_CD", appendPattern(parameters.getCommType()));
				} else {
					p.setString("IN_COMM_FCT_TYPE_CD", null);
				}

			}
		}
		if (parameters.getStreetName() != null) {
			p.setString("IN_STREET", appendPattern(parameters.getStreetName()));
		} else {
			p.setString("IN_STREET", null);
		}
		if (parameters.getPostalCode() != null) {
			p.setString("IN_POSTAL_CODE", appendPattern(parameters.getPostalCode()));
		} else {
			p.setString("IN_POSTAL_CODE", null);
		}
		if (parameters.getLongitude() != null) {
			p.setString("IN_LONGITUDE", parameters.getLongitude() + DaoConstants.SEARCH_FACILITY_SQL_WILDCARD_CHARACTER);
			LOGGER.info("LONGITUDE" + parameters.getLongitude());
		} else {
			p.setString("IN_LONGITUDE", null);
		}
		if (parameters.getLatitude() != null) {
			p.setString("IN_LATITUDE", parameters.getLatitude() + DaoConstants.SEARCH_FACILITY_SQL_WILDCARD_CHARACTER);
			LOGGER.info("IN_LATITUDE" + parameters.getLatitude());
		} else {
			p.setString("IN_LATITUDE", null);
		}
		if (parameters.getIsoCountryCode() != null) {
			p.setString("IN_FCT_CTRY_CD", appendPattern(parameters.getIsoCountryCode()));
		} else {
			p.setString("IN_FCT_CTRY_CD", null);
		}
		if (parameters.getCountryGeoID() != null) {
			p.setString("IN_CTRY_GEOID", parameters.getCountryGeoID());
		} else {
			p.setString("IN_CTRY_GEOID", null);
		}
		if (parameters.getCity() != null) {
			p.setString("IN_FCT_CITY_NM", appendPattern(parameters.getCity()));
		} else {
			p.setString("IN_FCT_CITY_NM", null);
		}
		if (parameters.getCityGeoID() != null) {
			p.setString("IN_CITY_GEOID", parameters.getCityGeoID());
		} else {
			p.setString("IN_CITY_GEOID", null);
		}
		if (parameters.getRegion() != null) {
			p.setString("IN_FCT_ISO_TRTY_CD", appendPattern(parameters.getRegion()));
		} else {
			p.setString("IN_FCT_ISO_TRTY_CD", null);
		}
		if (parameters.getRegionGeoID() != null) {
			p.setString("IN_TRTY_GEOID", parameters.getRegionGeoID());
		} else {
			p.setString("IN_TRTY_GEOID", null);
		}
		if (parameters.getFacilityLifecycleStatus() != null) {
			p.setString("IN_FACILITY_STATUS", parameters.getFacilityLifecycleStatus());
			LOGGER.info("Status------------------------------>" + parameters.getFacilityLifecycleStatus());
		} else {
			p.setString("IN_FACILITY_STATUS", null);
		}
		if (parameters.getFacilityName() != null) {
			p.setString("IN_FACILITY_NAME", appendPattern(parameters.getFacilityName()));
			LOGGER.info("Name------------------------------>" + parameters.getFacilityName());
		} else {
			p.setString("IN_FACILITY_NAME", null);
		}
		if (parameters.getFacilityCategory() != null) {
			p.setString("IN_FCT_CATEGORY", parameters.getFacilityCategory());
			LOGGER.info("Category------------------------------>" + parameters.getFacilityCategory());
		} else {
			p.setString("IN_FCT_CATEGORY", null);
		}
		String offerCodes1 = inputMap.get(DaoConstants.INPUTMAP_OFFER_CODES);
		LOGGER.info("offerCodes1------------------------------>"+ offerCodes1);
		if ((categoryParam.equalsIgnoreCase("COMM") && (retrieveFacilityCategory.equalsIgnoreCase("COMM")
				|| retrieveFacilityCategory.equalsIgnoreCase("BLANK"))) || (categoryParam.equalsIgnoreCase("OPS") && (retrieveFacilityCategory.equalsIgnoreCase("OPS")
				|| retrieveFacilityCategory.equalsIgnoreCase("BLANK"))) ) {

			if (offerCodes1 != null) {
				String[] offerCodesSplit1 = offerCodes1.split(":");
				if (offerCodesSplit1 != null && offerCodesSplit1.length > 0) {
					for (int i = 0; i < offerCodesSplit1.length; i++) {
						p.setString("IN_" + i, appendPattern(offerCodesSplit1[i]));

					}
				}
			}
		}

		String facilityTypes1 = inputMap.get(DaoConstants.INPUTMAP_FACILITY_TYPE);
		LOGGER.info("facilityTypes1------------------------------>"+ facilityTypes1);
		if (categoryParam.equalsIgnoreCase("OPS") && (retrieveFacilityCategory.equalsIgnoreCase("OPS")
				|| retrieveFacilityCategory.equalsIgnoreCase("BLANK"))) {
			if (facilityTypes1 != null) {
				String[] facilityTypesSplit1 = facilityTypes1.split(":");
				if (facilityTypesSplit1 != null && facilityTypesSplit1.length > 0) {
					for (int i = 0; i < facilityTypesSplit1.length; i++) {
						p.setString("IN_TYPE_" + i, appendPattern(facilityTypesSplit1[i]));
					}
				}
			}
		}
		return p;
	}


	/**
	 * Run fecth.
	 *
	 * @paramc
	 *            the c
	 * @paraminputMap
	 *            the input map
	 * @return the list
	 * @throwsSQLException
	 *             the SQL exception
	 */
	protected List<SearchFacilityVO> runSearchFacilityFecth(Connection c, Map<String, String> inputMap, SearchFacilityVO searchFacilityRequest, Properties prop) throws SQLException {

		LOGGER.info("Invoked runfetch from helper class:: " + this.getClass().getCanonicalName());
		StringBuilder sb = new QueryHelper().getSearchFacilityQuery(prop);
		StringBuilder finalQuery = new QueryHelper().getSearchFacilityQuery(prop);
		Map<Integer, String> parameterMap = new LinkedHashMap<Integer, String>();
		createQueryAndParameterMap(inputMap, sb, finalQuery, parameterMap);
		if (searchFacilityRequest.getFacilityCategory() != null) {
			sb = new StringBuilder(sb.toString().replaceAll(":FCT_CATEGORY_CD", "'" + searchFacilityRequest.getFacilityCategory() + "'"));
			finalQuery = new StringBuilder(finalQuery.toString().replaceAll(":FCT_CATEGORY_CD", "'" + searchFacilityRequest.getFacilityCategory() + "'"));
		} else {
			sb = new StringBuilder(sb.toString().replaceAll(":FCT_CATEGORY_CD", "NULL"));
			finalQuery = new StringBuilder(finalQuery.toString().replaceAll(":FCT_CATEGORY_CD", "NULL"));
		}
		LOGGER.info(finalQuery);
		List<SearchFacilityVO> resultList = new ArrayList<SearchFacilityVO>();
		try (PreparedStatement stmt = new QueryHelper().createPreparedStatementForSearchFacility(c, sb.toString(), parameterMap); ResultSet rs = stmt.executeQuery();) {
			while (rs.next()) {
				List<OfferingVO> offeringCodesList = null;
				List<String> facilityTypeList = new ArrayList<String>();
				SearchFacilityVO response = new SearchFacilityVO();
				Boolean found = false;
				String fctRowID = getFacilityrowid(c, rs.getString(DaoConstants.SEARCH_FACILITY_RESULTSET_FCT_RKST_ID));
				response.setFacilityID(fctRowID);
				if (rs.getString(DaoConstants.SEARCH_FACILITY_RESULTSET_FCT_GEO_ID).contains("GEOID")) {
					response.setFacilityGEOId(rs.getString(DaoConstants.SEARCH_FACILITY_RESULTSET_FCT_RKST_ID));
					String rkstCode = getRkstCode(rs.getString(DaoConstants.SEARCH_FACILITY_RESULTSET_FCT_RKST_ID), c);
					if (rkstCode != null) {
						response.setFacilityRKSTCode(rkstCode);
					}
				} else {
					response.setFacilityRKSTCode(rs.getString(DaoConstants.SEARCH_FACILITY_RESULTSET_FCT_RKST_ID));
					String geoCode = getGeoID(rs.getString(DaoConstants.SEARCH_FACILITY_RESULTSET_FCT_RKST_ID), c);
					if (geoCode != null) {
						response.setFacilityGEOId(geoCode);
					}
				}
				if (resultList.contains(response)) {
					found = true;
					response = resultList.get(resultList.indexOf(response));
					if (response.getFacilityOffering() != null) {
						offeringCodesList = response.getFacilityOffering();
					}
					if (response.getFacilityTypes() != null) {
						facilityTypeList = response.getFacilityTypes();
					}
				}

				if (rs.getString(DaoConstants.SEARCH_FACILITY_RESULTSET_FCT_OFFERING_NM) != null) {
					if (offeringCodesList == null) {
						offeringCodesList = new ArrayList<OfferingVO>();
					}
					OfferingVO offer = new OfferingVO();
					offer.setOffName(rs.getString(DaoConstants.SEARCH_FACILITY_RESULTSET_FCT_OFFERING_NM));
					offer.setOffCode(rs.getString(DaoConstants.SEARCH_FACILITY_RESULTSET_COLUMN_OFFER_CODE));
					if (!offeringCodesList.contains(offer)) {
						offeringCodesList.add(offer);
					}
				}
				if (!facilityTypeList.contains(rs.getString(DaoConstants.SEARCH_FACILITY_RESULTSET_COLUMN_TYPE))) {
					facilityTypeList.add(rs.getString(DaoConstants.SEARCH_FACILITY_RESULTSET_COLUMN_TYPE));
				}
				response.setFacilityName(rs.getString(DaoConstants.SEARCH_FACILITY_RESULTSET_COLUMN_NAME));
				response.setFacilityCategory(rs.getString(DaoConstants.SEARCH_FACILITY_RESULTSET_COLUMN_CATEGORY));
				response.setCountry(rs.getString(DaoConstants.SEARCH_FACILITY_RESULTSET_COLUMN_COUNTRY));
				response.setFacilityLifecycleStatus(rs.getString(DaoConstants.SEARCH_FACILITY_RESULTSET_FCT_STATUS));
				response.setHouseNo(rs.getString(DaoConstants.SEARCH_FACILITY_RESULTSET_COLUMN_HOUSE_NO));
				response.setPostalCode(rs.getString(DaoConstants.SEARCH_FACILITY_RESULTSET_COLUMN_POSTAL_CODE));
				response.setStreetName(rs.getString(DaoConstants.SEARCH_FACILITY_RESULTSET_COLUMN_STREET_NAME));
				response.setIsoCountryCode(rs.getString(DaoConstants.SEARCH_FACILITY_RESULTSET_COLUMN_COUNTRY_CODE));
				response.setCountryGeoID(rs.getString(DaoConstants.SEARCH_FACILITY_RESULTSET_COLUMN_COUNTRY_GEOID));
				response.setCity(rs.getString(DaoConstants.SEARCH_FACILITY_RESULTSET_COLUMN_CITY));
				response.setCityGeoID(rs.getString(DaoConstants.SEARCH_FACILITY_RESULTSET_COLUMN_CITY_GEOID));
				response.setRegion(rs.getString(DaoConstants.SEARCH_FACILITY_RESULTSET_COLUMN_REGION_CODE));
				response.setRegionGeoID(rs.getString(DaoConstants.SEARCH_FACILITY_RESULTSET_COLUMN_REGION_GEOID));
				response.setLatitude(rs.getString(DaoConstants.SEARCH_FACILITY_RESULTSET_COLUMN_LATITUDE));
				response.setLongitude(rs.getString(DaoConstants.SEARCH_FACILITY_RESULTSET_COLUMN_LONGITUDE));
				response.setFacilityTypes(facilityTypeList);
				response.setFacilityOffering(offeringCodesList);
				if (!found) {
					resultList.add(response);
				}
			}
		}

		return resultList;
	}

	public String appendPattern(String input) {
		if (null != input) {
			input = input.trim();
			input = input.replaceAll(DaoConstants.SEARCH_FACILITY_WILDCARD_CHARACTER, "%");
			if (!input.equalsIgnoreCase(DaoConstants.INPUTMAP_FACILITY_LIFECYCLESTATUS) || !input.equalsIgnoreCase(DaoConstants.INPUTMAP_LATITUDE)
					|| !input.equalsIgnoreCase(DaoConstants.INPUTMAP_LONGITUDE)) {
				input = DaoConstants.SEARCH_FACILITY_SQL_WILDCARD_CHARACTER + input + DaoConstants.SEARCH_FACILITY_SQL_WILDCARD_CHARACTER;
			}
			if (input.equalsIgnoreCase(DaoConstants.INPUTMAP_LATITUDE) || input.equalsIgnoreCase(DaoConstants.INPUTMAP_LONGITUDE)) {
				input = input + DaoConstants.SEARCH_FACILITY_SQL_WILDCARD_CHARACTER;
			}
		} else {
			throw new NullPointerException("Input search term can not be null");
		}
		return input;
	}

	/**
	 * Creates the query and parameter map.
	 *
	 * @param inputMap
	 *            the input map
	 * @param sb
	 *            the sb
	 * @param parameterMap
	 *            the parameter map
	 */
	private void createQueryAndParameterMap(Map<String, String> inputMap, StringBuilder sb, StringBuilder finalQuery, Map<Integer, String> parameterMap) {

		Integer index = 0;

		for (String input : inputMap.keySet()) {

			switch (input) {

				case DaoConstants.INPUTMAP_FACILITY_NAME:
					sb.append(addAnd());
					sb.append(" LOWER(" + DaoConstants.SEARCH_FACILITY_RESULTSET_COLUMN_NAME + ") LIKE LOWER(?) ");
					finalQuery.append(addAnd());
					finalQuery.append(" LOWER(" + DaoConstants.SEARCH_FACILITY_RESULTSET_COLUMN_NAME + ") LIKE LOWER('" + appendPattern(inputMap.get(input)) + "') ");
					parameterMap.put(++index, appendPattern(inputMap.get(input)));
					break;
				case DaoConstants.INPUTMAP_FACILITY_CATEGORY:
					sb.append(addAnd());
					sb.append(" LOWER(" + DaoConstants.SEARCH_FACILITY_RESULTSET_COLUMN_CATEGORY + ") LIKE LOWER(?) ");
					finalQuery.append(addAnd());
					finalQuery.append(" LOWER(" + DaoConstants.SEARCH_FACILITY_RESULTSET_COLUMN_CATEGORY + ") LIKE LOWER('" + appendPattern(inputMap.get(input)) + "') ");
					parameterMap.put(++index, appendPattern(inputMap.get(input)));
					break;
				case DaoConstants.INPUTMAP_HOUSE_NO:
					sb.append(addAnd());
					sb.append(" LOWER(" + DaoConstants.SEARCH_FACILITY_RESULTSET_COLUMN_HOUSE_NO + ") LIKE LOWER(?) ");
					finalQuery.append(addAnd());
					finalQuery.append(" LOWER(" + DaoConstants.SEARCH_FACILITY_RESULTSET_COLUMN_HOUSE_NO + ") LIKE LOWER('" + appendPattern(inputMap.get(input)) + "') ");
					parameterMap.put(++index, appendPattern(inputMap.get(input)));
					break;
				case DaoConstants.INPUTMAP_COUNTRY:
					sb.append(addAnd());
					sb.append(" LOWER(" + DaoConstants.SEARCH_FACILITY_RESULTSET_COLUMN_COUNTRY + ") LIKE LOWER(?) ");
					finalQuery.append(addAnd());
					finalQuery.append(" LOWER(" + DaoConstants.SEARCH_FACILITY_RESULTSET_COLUMN_COUNTRY + ") LIKE LOWER('" + appendPattern(inputMap.get(input)) + "') ");
					parameterMap.put(++index, appendPattern(inputMap.get(input)));
					break;
				case DaoConstants.INPUTMAP_COUNTRY_CODE:
					sb.append(addAnd());
					sb.append(" LOWER(" + DaoConstants.SEARCH_FACILITY_RESULTSET_COLUMN_COUNTRY_CODE + ") LIKE LOWER(?) ");
					finalQuery.append(addAnd());
					finalQuery.append(" LOWER(" + DaoConstants.SEARCH_FACILITY_RESULTSET_COLUMN_COUNTRY_CODE + ") LIKE LOWER('" + appendPattern(inputMap.get(input)) + "') ");
					parameterMap.put(++index, appendPattern(inputMap.get(input)));
					break;
				case DaoConstants.INPUTMAP_CITY:
					sb.append(addAnd());
					sb.append(" LOWER(" + DaoConstants.SEARCH_FACILITY_RESULTSET_COLUMN_CITY + ") LIKE LOWER(?) ");
					finalQuery.append(addAnd());
					finalQuery.append(" LOWER(" + DaoConstants.SEARCH_FACILITY_RESULTSET_COLUMN_CITY + ") LIKE LOWER('" + appendPattern(inputMap.get(input)) + "') ");
					parameterMap.put(++index, appendPattern(inputMap.get(input)));
					break;
				case DaoConstants.INPUTMAP_LIFY_CYCLE_STATUS:
					sb.append(addAnd());
					sb.append(" LOWER(" + DaoConstants.SEARCH_FACILITY_RESULTSET_FCT_STATUS + ") = LOWER(?) ");
					finalQuery.append(addAnd());
					finalQuery.append(" LOWER(" + DaoConstants.SEARCH_FACILITY_RESULTSET_FCT_STATUS + ") =  LOWER('" + inputMap.get(input) + "') ");
					parameterMap.put(++index, inputMap.get(input));
					break;
				case DaoConstants.INPUTMAP_LATITUDE:
					sb.append(addAnd());
					sb.append(" LOWER(" + DaoConstants.SEARCH_FACILITY_RESULTSET_COLUMN_LATITUDE + ") LIKE LOWER(?) ");
					finalQuery.append(addAnd());
					finalQuery.append(" LOWER(" + DaoConstants.SEARCH_FACILITY_RESULTSET_COLUMN_LATITUDE + ") LIKE LOWER('" + inputMap.get(input) + "%" + "') ");
					parameterMap.put(++index, inputMap.get(input) + "%");
					break;
				case DaoConstants.INPUTMAP_LONGITUDE:
					sb.append(addAnd());
					sb.append(" LOWER(" + DaoConstants.SEARCH_FACILITY_RESULTSET_COLUMN_LONGITUDE + ") LIKE LOWER(?) ");
					finalQuery.append(addAnd());
					finalQuery.append(" LOWER(" + DaoConstants.SEARCH_FACILITY_RESULTSET_COLUMN_LONGITUDE + ") LIKE LOWER('" + inputMap.get(input) + "%" + "') ");
					parameterMap.put(++index, inputMap.get(input) + "%");
					break;
				case DaoConstants.INPUTMAP_STREET_NAME:
					sb.append(addAnd());
					sb.append(" LOWER(" + DaoConstants.SEARCH_FACILITY_RESULTSET_COLUMN_STREET_NAME + ") LIKE LOWER(?) ");
					finalQuery.append(addAnd());
					finalQuery.append(" LOWER(" + DaoConstants.SEARCH_FACILITY_RESULTSET_COLUMN_STREET_NAME + ") LIKE LOWER('" + appendPattern(inputMap.get(input)) + "') ");
					parameterMap.put(++index, appendPattern(inputMap.get(input)));
					break;
				case DaoConstants.INPUTMAP_REGION:
					sb.append(addAnd());
					sb.append(" LOWER(" + DaoConstants.SEARCH_FACILITY_RESULTSET_COLUMN_REGION + ") LIKE LOWER(?) ");
					finalQuery.append(addAnd());
					finalQuery.append(" LOWER(" + DaoConstants.SEARCH_FACILITY_RESULTSET_COLUMN_REGION + ") LIKE LOWER('" + appendPattern(inputMap.get(input)) + "') ");
					parameterMap.put(++index, appendPattern(inputMap.get(input)));
					break;
				case DaoConstants.INPUTMAP_POSTAL_CODE:
					sb.append(addAnd());
					sb.append(" LOWER(" + DaoConstants.SEARCH_FACILITY_RESULTSET_COLUMN_POSTAL_CODE + ") LIKE LOWER(?) ");
					finalQuery.append(addAnd());
					finalQuery.append(" LOWER(" + DaoConstants.SEARCH_FACILITY_RESULTSET_COLUMN_POSTAL_CODE + ") LIKE LOWER('" + appendPattern(inputMap.get(input)) + "') ");
					parameterMap.put(++index, appendPattern(inputMap.get(input)));
					break;

				case DaoConstants.INPUTMAP_COUNTRY_GEOID:
					sb.append(addAnd());
					sb.append(" LOWER(" + DaoConstants.SEARCH_FACILITY_RESULTSET_COLUMN_COUNTRY_GEOID + ") LIKE LOWER(?) ");
					finalQuery.append(addAnd());
					finalQuery.append(" LOWER(" + DaoConstants.SEARCH_FACILITY_RESULTSET_COLUMN_COUNTRY_GEOID + ") LIKE LOWER('" + appendPattern(inputMap.get(input)) + "') ");
					parameterMap.put(++index, appendPattern(inputMap.get(input)));
					break;
				case DaoConstants.INPUTMAP_REGION_GEOID:
					sb.append(addAnd());
					sb.append(" LOWER(" + DaoConstants.SEARCH_FACILITY_RESULTSET_COLUMN_REGION_GEOID + ") LIKE LOWER(?) ");
					finalQuery.append(addAnd());
					finalQuery.append(" LOWER(" + DaoConstants.SEARCH_FACILITY_RESULTSET_COLUMN_REGION_GEOID + ") LIKE LOWER('" + appendPattern(inputMap.get(input)) + "') ");
					parameterMap.put(++index, appendPattern(inputMap.get(input)));
					break;
				case DaoConstants.INPUTMAP_CITY_GEOID:
					sb.append(addAnd());
					sb.append(" LOWER(" + DaoConstants.SEARCH_FACILITY_RESULTSET_COLUMN_CITY_GEOID + ") LIKE LOWER(?) ");
					finalQuery.append(addAnd());
					finalQuery.append(" LOWER(" + DaoConstants.SEARCH_FACILITY_RESULTSET_COLUMN_CITY_GEOID + ") LIKE LOWER('" + appendPattern(inputMap.get(input)) + "') ");
					parameterMap.put(++index, appendPattern(inputMap.get(input)));
					break;

				case DaoConstants.INPUTMAP_OFFER_CODES:
					String offerCodes = inputMap.get(input);
					if (offerCodes != null) {
						String[] offerCodesSplit = offerCodes.split(":");
						if (offerCodesSplit != null && offerCodesSplit.length > 0) {
							for (int i = 0; i < offerCodesSplit.length; i++) {

								if (i == 0) {
									sb.append(addAnd() + "( ");
									finalQuery.append(addAnd() + "( ");
								} else {
									sb.append(addOr());
									finalQuery.append(addOr());
								}
								sb.append(" LOWER(" + DaoConstants.SEARCH_FACILITY_RESULTSET_FCT_OFFERING_NM + ") LIKE LOWER(?) ");
								finalQuery.append(" LOWER(" + DaoConstants.SEARCH_FACILITY_RESULTSET_FCT_OFFERING_NM + ") LIKE LOWER('" + appendPattern(offerCodesSplit[i]) + "') ");
								parameterMap.put(++index, appendPattern(offerCodesSplit[i]));
							}
							sb.append(" )");
							finalQuery.append(" )");
						}
					}
					break;

				case DaoConstants.INPUTMAP_FACILITY_TYPE:
					String facilityTypes = inputMap.get(input);
					if (facilityTypes != null) {
						String[] facilityTypesSplit = facilityTypes.split(":");
						if (facilityTypesSplit != null && facilityTypesSplit.length > 0) {
							for (int i = 0; i < facilityTypesSplit.length; i++) {

								if (i == 0) {
									sb.append(addAnd() + "( ");
									finalQuery.append(addAnd() + "( ");
								} else {
									sb.append(addOr());
									finalQuery.append(addOr());
								}
								sb.append(" LOWER(" + DaoConstants.SEARCH_FACILITY_RESULTSET_COLUMN_TYPE + ") LIKE LOWER(?) ");
								finalQuery.append(" LOWER(" + DaoConstants.SEARCH_FACILITY_RESULTSET_COLUMN_TYPE + ") LIKE LOWER('" + appendPattern(facilityTypesSplit[i]) + "') ");
								parameterMap.put(++index, appendPattern(facilityTypesSplit[i]));
							}
							sb.append(" )");
							finalQuery.append(" )");
						}
					}
					break;
				default:
					break;
			}

		}

	}

	private String addAnd() {

		return " AND ";

	}

	private String addOr() {

		return " OR ";

	}

	private String getFacilityrowid(Connection conn, String id) throws SQLException {
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

	String getGeoID(String rkstCode, Connection conn) {
		String id = null;
		String sqlQuery = "SELECT GEOID.CODE GEOID " + "FROM  C_FCT_ALT_CODES RKST_CODE  " + "INNER JOIN C_FCT_ALT_CODES GEOID ON GEOID.FCT_ROWID = RKST_CODE.FCT_ROWID "
				+ "WHERE RKST_CODE.TYP_TYPE_ROWID = ( SELECT ROWID_OBJECT FROM C_TYP_TYPE WHERE CODE = 'ALT_CODE.RKST' ) "
				+ "AND   GEOID.TYP_TYPE_ROWID = ( SELECT ROWID_OBJECT FROM C_TYP_TYPE WHERE CODE = 'ALT_CODE.GEOID' ) " + "AND   RKST_CODE.CODE = (?) ";

		try (PreparedStatement stmt = conn.prepareStatement(sqlQuery);) {
			stmt.setString(1, rkstCode);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				id = rs.getString("GEOID");
			}
		} catch (SQLException e) {
			LOGGER.info(e);
		}
		return id;
	}

	String getRkstCode(String GeoCode, Connection conn) {
		String id = null;
		String sqlQuery = "SELECT RKST_CODE.CODE RKST_CODE" + "				FROM  C_FCT_ALT_CODES GEOID"
				+ "				INNER JOIN C_FCT_ALT_CODES RKST_CODE  ON GEOID.FCT_ROWID = RKST_CODE.FCT_ROWID"
				+ "				WHERE GEOID.TYP_TYPE_ROWID = ( SELECT ROWID_OBJECT FROM C_TYP_TYPE WHERE CODE = 'ALT_CODE.GEOID' )"
				+ "				AND   RKST_CODE.TYP_TYPE_ROWID = ( SELECT ROWID_OBJECT FROM C_TYP_TYPE WHERE CODE = 'ALT_CODE.RKST' )" + "				AND   GEOID.CODE = (?)";

		try (PreparedStatement stmt = conn.prepareStatement(sqlQuery);) {
			stmt.setString(1, GeoCode);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				id = rs.getString("RKST_CODE");
			}
		} catch (SQLException e) {
			LOGGER.info(e);
		}

		return id;
	}


	protected String getRetrieveFacilityMasterQuery() {

		StringBuffer sb = new StringBuffer();

		sb.append("  WITH FACILITY_IDENTIFIER AS  ");
		sb.append("    (SELECT C_FCT_ALT_CODES.FCT_ROWID,  ");
		sb.append("      C_FCT_ALT_CODES.CODE,  ");
		sb.append("      C_FCT_ADDR_REL.ADDR_ROWID,  ");
		sb.append("      C_CTM_PSTL_ADDR.CTRY_ROWID,  ");
		sb.append("      C_CTM_PSTL_ADDR.CITY_ROWID,  ");
		sb.append("      C_CTM_PSTL_ADDR.TRTY_ROWID  ");
		sb.append("    FROM C_FCT_ALT_CODES  ");
		sb.append("    INNER JOIN C_FCT_ADDR_REL  ");
		sb.append("    ON C_FCT_ADDR_REL.FCT_ROWID = C_FCT_ALT_CODES.FCT_ROWID  ");
		sb.append("    INNER JOIN C_CTM_PSTL_ADDR  ");
		sb.append("    ON C_FCT_ADDR_REL.ADDR_ROWID                                   = C_CTM_PSTL_ADDR.ROWID_OBJECT  ");
		sb.append("    WHERE C_FCT_ALT_CODES.CODE                                     = :CODE  ");
		sb.append("    AND TRUNC ( NVL(C_FCT_ADDR_REL.VALID_THRU_DT,SYSDATE),'DDD' ) >= TRUNC(SYSDATE,'DDD')  ");
		sb.append("    ),  ");
		sb.append("    COUNTRY_GEOID AS  ");
		sb.append("    (SELECT C_GDA_DFND_AREA.ROWID_OBJECT COUNTRY_MDM_ID,  ");
		sb.append("      C_GDA_DFND_AREA.NAME COUNTRY_NAME,  ");
		sb.append("      C_ALT_CODE.CODE COUNTRY_GEOID,  ");
		sb.append("      COUNTRY_CODE.CODE COUNTRY_CODE  ");
		sb.append("    FROM C_GDA_DFND_AREA  ");
		sb.append("    ");
		sb.append("    INNER JOIN C_ALT_CODE  ");
		sb.append("    ON C_ALT_CODE.GDA_DFND_AREA_ROWID = C_GDA_DFND_AREA.ROWID_OBJECT  ");
		sb.append("    INNER JOIN C_TYP_TYPE  ");
		sb.append("    ON C_ALT_CODE.TYP_TYPE_ROWID = C_TYP_TYPE.ROWID_OBJECT  ");
		sb.append("    INNER JOIN C_ALT_CODE COUNTRY_CODE  ");
		sb.append("    ON COUNTRY_CODE.GDA_DFND_AREA_ROWID = C_GDA_DFND_AREA.ROWID_OBJECT  ");
		sb.append("    INNER JOIN C_TYP_TYPE ISO_CTRY_TYPE  ");
		sb.append("    ON COUNTRY_CODE.TYP_TYPE_ROWID      = ISO_CTRY_TYPE.ROWID_OBJECT  ");
		sb.append("    WHERE C_GDA_DFND_AREA.HUB_STATE_IND = 1  ");
		sb.append("    AND C_GDA_DFND_AREA.ACTIVE_FLAG     = 'Y'  ");
		sb.append("    AND C_GDA_DFND_AREA.TYP_TYPE_CD     = 'GDA.COUNTRY'  ");
		sb.append("    AND C_TYP_TYPE.CODE                 = 'ALT_CODE.GEOID'  ");
		sb.append("    AND ISO_CTRY_TYPE.CODE              = 'ALT_CODE.RKST'  ");
		sb.append("    ),  ");
		sb.append("    CITY_GEOID AS  ");
		sb.append("    (SELECT C_GDA_DFND_AREA.ROWID_OBJECT CITY_MDM_ID,  ");
		sb.append("      C_GDA_DFND_AREA.NAME CITY_NAME,  ");
		sb.append("      C_ALT_CODE.CODE CITY_GEOID  ");
		sb.append("    FROM C_GDA_DFND_AREA  ");
		sb.append("     INNER JOIN C_ALT_CODE  ");
		sb.append("    ON C_ALT_CODE.GDA_DFND_AREA_ROWID = C_GDA_DFND_AREA.ROWID_OBJECT  ");
		sb.append("    INNER JOIN C_TYP_TYPE  ");
		sb.append("    ON C_ALT_CODE.TYP_TYPE_ROWID        = C_TYP_TYPE.ROWID_OBJECT  ");
		sb.append("    WHERE C_GDA_DFND_AREA.HUB_STATE_IND = 1  ");
		sb.append("    AND C_GDA_DFND_AREA.ACTIVE_FLAG     = 'Y'  ");
		sb.append("    AND C_GDA_DFND_AREA.TYP_TYPE_CD     = 'GDA.CITY'  ");
		sb.append("    AND C_TYP_TYPE.CODE                 = 'ALT_CODE.GEOID'  ");
		sb.append("    ),  ");
		sb.append("    REGION_GEOID AS  ");
		sb.append("    ( SELECT DISTINCT C_GDA_DFND_AREA.ROWID_OBJECT REGION_MDM_ID,  ");
		sb.append("      C_GDA_DFND_AREA.NAME REGION_NAME,  ");
		sb.append("      C_ALT_CODE.CODE REGION_GEOID,  ");
		sb.append("      REGION_CODE.CODE REGION_CODE  ");
		sb.append("    FROM C_GDA_DFND_AREA  ");
		sb.append("    INNER JOIN FACILITY_IDENTIFIER  ");
		sb.append("    ON ( NVL(FACILITY_IDENTIFIER.TRTY_ROWID,'XX') = NVL(C_GDA_DFND_AREA.ROWID_OBJECT,'XX') )  ");
		sb.append("    INNER JOIN C_ALT_CODE  ");
		sb.append("    ON C_ALT_CODE.GDA_DFND_AREA_ROWID = C_GDA_DFND_AREA.ROWID_OBJECT  ");
		sb.append("    INNER JOIN C_TYP_TYPE  ");
		sb.append("    ON C_ALT_CODE.TYP_TYPE_ROWID = C_TYP_TYPE.ROWID_OBJECT  ");
		sb.append("    INNER JOIN C_ALT_CODE REGION_CODE  ");
		sb.append("    ON REGION_CODE.GDA_DFND_AREA_ROWID = C_GDA_DFND_AREA.ROWID_OBJECT  ");
		sb.append("    INNER JOIN C_TYP_TYPE ISO_TRTY_TYPE  ");
		sb.append("    ON REGION_CODE.TYP_TYPE_ROWID       = ISO_TRTY_TYPE.ROWID_OBJECT  ");
		sb.append("    WHERE C_GDA_DFND_AREA.HUB_STATE_IND = 1  ");
		sb.append("    AND C_GDA_DFND_AREA.ACTIVE_FLAG     = 'Y'  ");
		sb.append("    AND C_GDA_DFND_AREA.TYP_TYPE_CD     = 'GDA.STATE/PROV'  ");
		sb.append("    AND C_TYP_TYPE.CODE                 = 'ALT_CODE.GEOID'  ");
		sb.append("    AND ISO_TRTY_TYPE.CODE              = 'ALT_CODE.ISO_TRTY'  ");
		sb.append("    ),  ");
		sb.append("    OPERATIONAL_FCT_INFO AS  ");
		sb.append("    (SELECT C_FCT_OPS.ROWID_OBJECT,  ");
		sb.append("      C_FCT_OPS.FCT_ROWID,  ");
		sb.append("      C_TYP_TYPE.CODE FCT_OPS_TYPE_CD,  ");
		sb.append("      C_TYP_TYPE.NAME FCT_OPS_TYPE_NM,  ");
		sb.append("      WEIGHT_LMT_CRANE,  ");
		sb.append("      WEIGHT_LMT_YARD,  ");
		sb.append("      VESSEL_AGENT,  ");
		sb.append("      GPS_FLAG,  ");
		sb.append("      GSM_FLAG,  ");
		sb.append("      OCE_FRGHT_PR,  ");
		sb.append("      C_FCT_OPS_TYP_REL.VALID_FROM_DT FCT_OPS_TYPE_START_DT,  ");
		sb.append("      C_FCT_OPS_TYP_REL.VALID_THRU_DT FCT_OPS_TYPE_END_DT  ");
		sb.append("    FROM C_FCT_OPS  ");
		sb.append("    ");
		sb.append("    LEFT OUTER JOIN  ");
		sb.append("      (SELECT *  ");
		sb.append("      FROM C_FCT_OPS_TYP_REL  ");
		sb.append("      WHERE HUB_STATE_IND                                            = 1  ");
		sb.append("      AND TRUNC(NVL(C_FCT_OPS_TYP_REL.VALID_THRU_DT,SYSDATE),'DDD') >= TRUNC(SYSDATE,'DDD')  ");
		sb.append("      ) C_FCT_OPS_TYP_REL  ");
		sb.append("    ON C_FCT_OPS_TYP_REL.FCT_OPS_ROWID = C_FCT_OPS.ROWID_OBJECT  ");
		sb.append("    LEFT OUTER JOIN C_TYP_TYPE  ");
		sb.append("    ON C_FCT_OPS_TYP_REL.TYP_TYPE_ROWID = C_TYP_TYPE.ROWID_OBJECT  ");
		sb.append("    WHERE C_FCT_OPS.HUB_STATE_IND       = 1  ");
		sb.append("    ),  ");
		sb.append("    ");
		sb.append("    COMMERCIAL_FCT_INFO AS  ");
		sb.append("    (SELECT C_FCT_COM.FCT_ROWID,  ");
		sb.append("      C_TYP_TYPE.CODE COMM_FCT_TYPE_CD,  ");
		sb.append("      C_TYP_TYPE.CODE COMM_FCT_TYPE_NM,  ");
		sb.append("      C_FCT_BRAND.CODE COMM_FCT_BRAND_CD,  ");
		sb.append("      C_FCT_BRAND.NAME COMM_FCT_BRAND_NM,  ");
		sb.append("      C_FCT_COMM_FUNC.CODE COMM_FCT_FUNC_CD,  ");
		sb.append("      C_FCT_COMM_FUNC.NAME COMM_FCT_FUNC_NM,  ");
		sb.append("      C_CTM_INTL_DIALNG_CD.DIALNG_CD COMM_FCT_DIALING_CD,  ");
		sb.append("      C_CTM_INTL_DIALNG_CD.DAILNG_CD_DESC COMM_FCT_DAILNG_CD_DESC,  ");
		sb.append("      C_FCT_COM.TELECOM_NUM,  ");
		sb.append("      C_FCT_COM.IMP_MAIL,  ");
		sb.append("      C_FCT_COM.EXP_MAIL  ");
		sb.append("    FROM C_FCT_COM  ");
		sb.append("     INNER JOIN C_FCT_COMM_FUNC  ");
		sb.append("    ON C_FCT_COM.COMM_FUNC_ROWID = C_FCT_COMM_FUNC.ROWID_OBJECT  ");
		sb.append("    LEFT OUTER JOIN C_FCT_BRAND  ");
		sb.append("    ON C_FCT_COM.FCT_BRAND_ROWID = C_FCT_BRAND.ROWID_OBJECT  ");
		sb.append("    INNER JOIN C_TYP_TYPE  ");
		sb.append("    ON C_FCT_COM.TYP_TYPE_ROWID = C_TYP_TYPE.ROWID_OBJECT  ");
		sb.append("    LEFT OUTER JOIN C_CTM_INTL_DIALNG_CD  ");
		sb.append("    ON C_FCT_COM.INTL_DIALING_ROWID = C_CTM_INTL_DIALNG_CD.ROWID_OBJECT  ");
		sb.append("    )  ");
		sb.append("  SELECT DISTINCT PKG_RETRIEVE_FACILITY.ROWID_OBJECT FCT_MDM_ID,  ");
		sb.append("    CLASS_CD FCT_CATEGORY,  ");
		sb.append("    FACILITY_NAME FCT_NAME,  ");
		sb.append("    PKG_RETRIEVE_FACILITY.HUB_STATE_IND FCT_HUB_STATE,  ");
		sb.append("    STATUS_CD FCT_STATUS,  ");
		sb.append("    PKG_RETRIEVE_FACILITY.CREATE_DATE FCT_CREATE_DT,  ");
		sb.append("    PKG_RETRIEVE_FACILITY.UPDATED_BY FCT_LAST_UPDATED_BY,  ");
		sb.append("    PKG_RETRIEVE_FACILITY.CREATOR FCT_CREATOR,  ");
		sb.append("    PKG_RETRIEVE_FACILITY.DELETED_BY FCT_DELETED_BY,  ");
		sb.append("    PKG_RETRIEVE_FACILITY.LAST_UPDATE_DATE FCT_LAST_UPDATE_DT,  ");
		sb.append("    PKG_RETRIEVE_FACILITY.LAST_ROWID_SYSTEM LAST_ROWID_SYSTEM,  ");
		sb.append("    PKG_RETRIEVE_FACILITY.DELETED_DATE FCT_DELETED_DT,  ");
		sb.append("    EXT_OWNED EXT_OWNED_FLAG,  ");
		sb.append("    EXT_EXPOSED EXT_EXPOSED_FLAG,  ");
		sb.append("    URL FCT_URL,  ");
		sb.append("    DODAAC DODAAC,  ");
		sb.append("    FCT_ALT_CODE_HUB_STATE FCT_ALT_CODE_HUB_STATE,  ");
		sb.append("    TYP_TYPE_ROWID FCT_ALT_CD_TYP_ROWID,  ");
		sb.append("    ALT_CODE_TYPE_CD ALT_CODE_TYPE_CD,  ");
		sb.append("    ALT_CODE_TYPE_NM ALT_CODE_TYPE_NM,  ");
		sb.append("    FCT_ALT_CODE FCT_ALT_CODE,  ");
		sb.append("    FCT_ADDR_REL_HUB_STATE FCT_ADDR_REL_HUB_STATE,  ");
		sb.append("    FACILITY_IDENTIFIER.ADDR_ROWID ADDR_MDM_ID,  ");
		sb.append("    FCT_ADDR_VALID_FROM_DT FCT_ADDR_VALID_FROM_DT,  ");
		sb.append("    FCT_ADDR_VALID_THRU_DT FCT_ADDR_VALID_THRU_DT,  ");
		sb.append("    ADDR_HUB_STATE ADDR_HUB_STATE,  ");
		sb.append("    ADDR_LAST_UPDATE_DT ADDR_LAST_UPDATE_DT,  ");
		sb.append("    ADDR_UPDATED_BY ADDR_UPDATED_BY,  ");
		sb.append("    ADDR_CREATE_DT ADDR_CREATE_DT,  ");
		sb.append("    ADDR_CREATOR ADDR_CREATOR,  ");
		sb.append("    HOUSE_NUM HOUSE_NUM,  ");
		sb.append("    DSTRCT DISTRICT,  ");
		sb.append("    PSTCD POSTAL_CODE,  ");
		sb.append("    TAX_JURN_CD TAX_JURN_CD,  ");
		sb.append("    PO_BOX PO_BOX,  ");
		sb.append("    STREET STREET,  ");
		sb.append("    ADDR_LN_2 BUILDING_NUM,  ");
		sb.append("    ADDR_LN_3 SUBURB,  ");
		sb.append("    CITY_MDM_ID CITY_MDM_ID,  ");
		sb.append("    CITY_GEOID CITY_GEOID,  ");
		sb.append("    CITY_GEOID.CITY_NAME CITY,  ");
		sb.append("    LAT_GEOSPTL LAT_GEOSPTL,  ");
		sb.append("    LNG_GEOSPTL LNG_GEOSPTL,  ");
		sb.append("    FACILITY_IDENTIFIER.TRTY_ROWID REGION_MDM_ID,  ");
		sb.append("    REGION_GEOID REGION_GEOID,  ");
		sb.append("    REGION_CODE ISO_REGION_CODE,  ");
		sb.append("    REGION_NAME REGION_NAME,  ");
		sb.append("    FACILITY_IDENTIFIER.CTRY_ROWID CTRY_MDM_ID,  ");
		sb.append("    COUNTRY_GEOID COUNTRY_GEOID,  ");
		sb.append("    COUNTRY_CODE ISO_COUNTRY_CODE,  ");
		sb.append("    COUNTRY_NAME COUNTRY_NAME,  ");
		sb.append("    C_FCT_DFND_REL.GDA_ROWID GDA_ROWID,  ");
		sb.append("    C_FCT_DFND_REL.VALID_FROM_DT FCT_DFND_REL_VALID_FROM_DT,  ");
		sb.append("    C_FCT_DFND_REL.VALID_THRU_DT FCT_DFND_REL_VALID_THRU_DT,  ");
		sb.append("    FCT_OPS_TYPE_CD FCT_OPS_TYPE_CD,  ");
		sb.append("    FCT_OPS_TYPE_NM FCT_OPS_TYPE_NM,  ");
		sb.append("    FCT_OPS_TYPE_START_DT FCT_OPS_TYPE_START_DT,  ");
		sb.append("    FCT_OPS_TYPE_END_DT FCT_OPS_TYPE_END_DT,  ");
		sb.append("    WEIGHT_LMT_CRANE WEIGHT_LMT_CRANE,  ");
		sb.append("    WEIGHT_LMT_YARD WEIGHT_LMT_YARD,  ");
		sb.append("    VESSEL_AGENT VESSEL_AGENT,  ");
		sb.append("    GPS_FLAG GPS_FLAG,  ");
		sb.append("    GSM_FLAG GSM_FLAG,  ");
		sb.append("    OCE_FRGHT_PR OCE_FRGHT_PR,  ");
		sb.append("    COMMERCIAL_FCT_INFO.COMM_FCT_TYPE_CD,  ");
		sb.append("    COMMERCIAL_FCT_INFO.COMM_FCT_TYPE_NM,  ");
		sb.append("    COMMERCIAL_FCT_INFO.COMM_FCT_BRAND_CD,  ");
		sb.append("    COMMERCIAL_FCT_INFO.COMM_FCT_BRAND_NM,  ");
		sb.append("    COMMERCIAL_FCT_INFO.COMM_FCT_FUNC_CD,  ");
		sb.append("    COMMERCIAL_FCT_INFO.COMM_FCT_FUNC_CD,  ");
		sb.append("    COMMERCIAL_FCT_INFO.COMM_FCT_FUNC_NM,  ");
		sb.append("    COMMERCIAL_FCT_INFO.COMM_FCT_DIALING_CD,  ");
		sb.append("    COMMERCIAL_FCT_INFO.COMM_FCT_DAILNG_CD_DESC,  ");
		sb.append("    COMMERCIAL_FCT_INFO.TELECOM_NUM,  ");
		sb.append("    COMMERCIAL_FCT_INFO.IMP_MAIL,  ");
		sb.append("    COMMERCIAL_FCT_INFO.EXP_MAIL  ");
		sb.append("  FROM PKG_RETRIEVE_FACILITY  ");
		sb.append("  INNER JOIN FACILITY_IDENTIFIER  ");
		sb.append("  ON PKG_RETRIEVE_FACILITY.ROWID_OBJECT = FACILITY_IDENTIFIER.FCT_ROWID  ");
		sb.append("  LEFT OUTER JOIN C_FCT_DFND_REL  ");
		sb.append("  ON C_FCT_DFND_REL.FCT_ROWID = PKG_RETRIEVE_FACILITY.ROWID_OBJECT  ");
		sb.append("  INNER JOIN COUNTRY_GEOID  ");
		sb.append("  ON COUNTRY_GEOID.COUNTRY_MDM_ID = PKG_RETRIEVE_FACILITY.CTRY_ROWID  ");
		sb.append("  INNER JOIN CITY_GEOID  ");
		sb.append("  ON CITY_GEOID.CITY_MDM_ID = PKG_RETRIEVE_FACILITY.CITY_ROWID  ");
		sb.append("  LEFT OUTER JOIN REGION_GEOID  ");
		sb.append("  ON REGION_GEOID.REGION_MDM_ID = PKG_RETRIEVE_FACILITY.TRTY_ROWID  ");
		sb.append("  LEFT OUTER JOIN OPERATIONAL_FCT_INFO  ");
		sb.append("  ON OPERATIONAL_FCT_INFO.FCT_ROWID = PKG_RETRIEVE_FACILITY.ROWID_OBJECT  ");
		sb.append("  LEFT OUTER JOIN COMMERCIAL_FCT_INFO  ");
		sb.append("  ON COMMERCIAL_FCT_INFO.FCT_ROWID          = PKG_RETRIEVE_FACILITY.ROWID_OBJECT  ");
		sb.append(" WHERE PKG_RETRIEVE_FACILITY.HUB_STATE_IND = 1 ");

		return sb.toString();
	}

	protected  String getRetrieveFacilityTransDayQuery() {
		StringBuffer sb = new StringBuffer();

		sb.append("  WITH FACILITY_IDENTIFIER AS  ");
		sb.append("    (SELECT C_FCT_ALT_CODES.FCT_ROWID,  ");
		sb.append("      C_FCT_ALT_CODES.CODE,  ");
		sb.append("      C_FCT_ADDR_REL.ADDR_ROWID,  ");
		sb.append("      C_CTM_PSTL_ADDR.CTRY_ROWID,  ");
		sb.append("      C_CTM_PSTL_ADDR.CITY_ROWID,  ");
		sb.append("      C_CTM_PSTL_ADDR.TRTY_ROWID  ");
		sb.append("    FROM C_FCT_ALT_CODES  ");
		sb.append("    INNER JOIN C_FCT_ADDR_REL  ");
		sb.append("    ON C_FCT_ADDR_REL.FCT_ROWID = C_FCT_ALT_CODES.FCT_ROWID  ");
		sb.append("    INNER JOIN C_CTM_PSTL_ADDR  ");
		sb.append("    ON C_FCT_ADDR_REL.ADDR_ROWID                                   = C_CTM_PSTL_ADDR.ROWID_OBJECT  ");
		sb.append("    WHERE C_FCT_ALT_CODES.CODE                                     = :CODE  ");
		sb.append("    AND TRUNC ( NVL(C_FCT_ADDR_REL.VALID_THRU_DT,SYSDATE),'DDD' ) >= TRUNC(SYSDATE,'DDD')  ");
		sb.append("    ),  ");
		sb.append("    FCT_TRANSPORT_INFO AS  ");
		sb.append("    (SELECT TRNSP_CD,  ");
		sb.append("      TRNSP_NAME,  ");
		sb.append("      TRNSP_DESC,  ");
		sb.append("      C_FCT_TRNSP_REL.FCT_ROWID,  ");
		sb.append("      C_FCT_TRNSP_REL.VALID_FROM_DT TRNSP_START_DT,  ");
		sb.append("      C_FCT_TRNSP_REL.VALID_THRU_DT TRNSP_END_DT  ");
		sb.append("    FROM C_FCT_TRNSP_MODE  ");
		sb.append("    INNER JOIN C_FCT_TRNSP_REL  ");
		sb.append("    ON C_FCT_TRNSP_REL.TRNSP_ROWID = C_FCT_TRNSP_MODE.ROWID_OBJECT  ");
		sb.append("    ");
		sb.append("    WHERE C_FCT_TRNSP_MODE.HUB_STATE_IND                         = 1  ");
		sb.append("    AND C_FCT_TRNSP_REL.HUB_STATE_IND                            = 1  ");
		sb.append("    AND TRUNC(NVL(C_FCT_TRNSP_REL.VALID_THRU_DT,SYSDATE),'DDD') >= TRUNC(SYSDATE,'DDD')  ");
		sb.append("    )  ");
		sb.append("  SELECT DISTINCT PKG_RETRIEVE_FACILITY.ROWID_OBJECT FCT_MDM_ID,  ");
		sb.append("    ALT_CODE_TYPE_CD ALT_CODE_TYPE_CD,  ");
		sb.append("    ALT_CODE_TYPE_NM ALT_CODE_TYPE_NM,  ");
		sb.append("    FCT_ALT_CODE FCT_ALT_CODE,  ");
		sb.append("    DECODE(OPEN_TIME_HRS  ");
		sb.append("    || ':'  ");
		sb.append("    || OPEN_TIME_MINS,':',NULL,OPEN_TIME_HRS  ");
		sb.append("    || ':'  ");
		sb.append("    || OPEN_TIME_MINS ) FCT_DAY_OPEN,  ");
		sb.append("    DECODE(CLOSE_TIME_HRS  ");
		sb.append("    || ':'  ");
		sb.append("    || CLOSE_TIME_MINS,':',NULL,CLOSE_TIME_HRS  ");
		sb.append("    || ':'  ");
		sb.append("    || CLOSE_TIME_MINS ) FCT_DAY_CLOSE,  ");
		sb.append("    DAY FCT_WORKING_DAYS,  ");
		sb.append("    TRNSP_CD FCT_TRANSPORT_CD,  ");
		sb.append("    TRNSP_NAME FCT_TRANSPORT_NAME,  ");
		sb.append("    TRNSP_DESC FCT_TRANSPORT_DESC,  ");
		sb.append("    TRNSP_START_DT TRNSP_START_DT,  ");
		sb.append("    TRNSP_END_DT FCT_TRNSP_END_DT  ");
		sb.append("  FROM PKG_RETRIEVE_FACILITY  ");
		sb.append("  INNER JOIN FACILITY_IDENTIFIER  ");
		sb.append("  ON PKG_RETRIEVE_FACILITY.ROWID_OBJECT = FACILITY_IDENTIFIER.FCT_ROWID  ");
		sb.append("  LEFT OUTER JOIN C_FCT_OPNH  ");
		sb.append("  ON C_FCT_OPNH.FCT_ROWID = PKG_RETRIEVE_FACILITY.ROWID_OBJECT  ");
		sb.append("  LEFT OUTER JOIN FCT_TRANSPORT_INFO  ");
		sb.append("  ON FCT_TRANSPORT_INFO.FCT_ROWID = PKG_RETRIEVE_FACILITY.ROWID_OBJECT  ");
		sb.append(" WHERE PKG_RETRIEVE_FACILITY.HUB_STATE_IND = 1 ");

		return sb.toString();
	}

	protected String getRetrieveFacilityOfferingQuery() {
		StringBuffer sb = new StringBuffer();

		sb.append("  WITH FACILITY_IDENTIFIER AS  ");
		sb.append("    (SELECT C_FCT_ALT_CODES.FCT_ROWID,  ");
		sb.append("      C_FCT_ALT_CODES.CODE,  ");
		sb.append("      C_FCT_ADDR_REL.ADDR_ROWID,  ");
		sb.append("      C_CTM_PSTL_ADDR.CTRY_ROWID,  ");
		sb.append("      C_CTM_PSTL_ADDR.CITY_ROWID,  ");
		sb.append("      C_CTM_PSTL_ADDR.TRTY_ROWID  ");
		sb.append("    FROM C_FCT_ALT_CODES  ");
		sb.append("    INNER JOIN C_FCT_ADDR_REL  ");
		sb.append("    ON C_FCT_ADDR_REL.FCT_ROWID = C_FCT_ALT_CODES.FCT_ROWID  ");
		sb.append("    INNER JOIN C_CTM_PSTL_ADDR  ");
		sb.append("    ON C_FCT_ADDR_REL.ADDR_ROWID                                   = C_CTM_PSTL_ADDR.ROWID_OBJECT  ");
		sb.append("    WHERE C_FCT_ALT_CODES.CODE                                     = :CODE  ");
		sb.append("    AND TRUNC ( NVL(C_FCT_ADDR_REL.VALID_THRU_DT,SYSDATE),'DDD' ) >= TRUNC(SYSDATE,'DDD')  ");
		sb.append("    ),  ");
		sb.append("    FCT_OFFERING_INFO AS  ");
		sb.append("    (SELECT C_FCT_OFF.GRP_CD,  ");
		sb.append("      C_FCT_OFF.VAS_CD,  ");
		sb.append("      C_FCT_OFF.OFF_NAME,  ");
		sb.append("      C_FCT_OFF.OFF_DESC,  ");
		sb.append("      C_FCT_OFF_REL.FCT_ROWID,  ");
		sb.append("      C_FCT_OFF_REL.VALID_FROM_DT FCT_OFFERING_START_DT,  ");
		sb.append("      C_FCT_OFF_REL.VALID_THRU_DT FCT_OFFERING_END_DT  ");
		sb.append("    FROM C_FCT_OFF  ");
		sb.append("    INNER JOIN C_FCT_OFF_REL  ");
		sb.append("    ON C_FCT_OFF_REL.OFFERING_ROWID = C_FCT_OFF.ROWID_OBJECT  ");
		sb.append("     ");
		sb.append("    WHERE C_FCT_OFF.HUB_STATE_IND                              = 1  ");
		sb.append("    AND C_FCT_OFF_REL.HUB_STATE_IND                            = 1  ");
		sb.append("    AND TRUNC(NVL(C_FCT_OFF_REL.VALID_THRU_DT,SYSDATE),'DDD') >= TRUNC(SYSDATE,'DDD')  ");
		sb.append("    )  ");
		sb.append("  SELECT DISTINCT PKG_RETRIEVE_FACILITY.ROWID_OBJECT FCT_MDM_ID,  ");
		sb.append("    ALT_CODE_TYPE_CD ALT_CODE_TYPE_CD,  ");
		sb.append("    ALT_CODE_TYPE_NM ALT_CODE_TYPE_NM,  ");
		sb.append("    FCT_ALT_CODE FCT_ALT_CODE,  ");
		sb.append("    GRP_CD GRP_CD,  ");
		sb.append("    VAS_CD FCT_OFFERING_CD,  ");
		sb.append("    OFF_NAME FCT_OFFERING_NAME,  ");
		sb.append("    OFF_DESC FCT_OFFERING_DESC,  ");
		sb.append("    FCT_OFFERING_START_DT FCT_OFFERING_START_DT,  ");
		sb.append("    FCT_OFFERING_END_DT FCT_OFFERING_END_DT  ");
		sb.append("  FROM PKG_RETRIEVE_FACILITY  ");
		sb.append("  INNER JOIN FACILITY_IDENTIFIER  ");
		sb.append("  ON PKG_RETRIEVE_FACILITY.ROWID_OBJECT = FACILITY_IDENTIFIER.FCT_ROWID  ");
		sb.append("  LEFT OUTER JOIN FCT_OFFERING_INFO  ");
		sb.append("  ON FCT_OFFERING_INFO.FCT_ROWID = PKG_RETRIEVE_FACILITY.ROWID_OBJECT  ");
		sb.append(" WHERE PKG_RETRIEVE_FACILITY.HUB_STATE_IND = 1 ");

		return sb.toString();
	}

	/*================================Below 3 Queries to get OPS/CUST/COMM data individually for performance improvement=========*/
	/**
	 * Below query returns
	 * @return
	 */
	private String getRetrieveOpsFacilityAllQuery() {
		LOGGER.info("Inside getRetrieveOpsFacilityAllQuery");
		String opsFacilityQuery="WITH PKG_VIEW_OPS_FACT AS ( \r\n" +
				"				    SELECT \r\n" +
				"				        C_FCT_FACILITY.ROWID_OBJECT AS F_ROWID_OBJECT, \r\n" +
				"				        C_FCT_FACILITY.CREATOR AS F_CREATOR, \r\n" +
				"				        C_FCT_FACILITY.CREATE_DATE AS F_CREATE_DATE, \r\n" +
				"				        C_FCT_FACILITY.UPDATED_BY AS F_UPDATED_BY, \r\n" +
				"				        C_FCT_FACILITY.LAST_UPDATE_DATE AS F_LAST_UPDATE_DATE, \r\n" +
				"				        C_FCT_FACILITY.DELETED_IND AS F_DELETED_IND, \r\n" +
				"				        C_FCT_FACILITY.DELETED_BY AS F_DELETED_BY, \r\n" +
				"				        C_FCT_FACILITY.DELETED_DATE AS F_DELETED_DATE, \r\n" +
				"				        C_FCT_FACILITY.LAST_ROWID_SYSTEM AS F_LAST_ROWID_SYSTEM, \r\n" +
				"				        C_FCT_FACILITY.HUB_STATE_IND AS F_HUB_STATE_IND, \r\n" +
				"				        C_FCT_FACILITY.FACILITY_NAME AS F_FACILITY_NAME, \r\n" +
				"				        C_FCT_FACILITY.EXT_OWNED AS F_EXT_OWNED, \r\n" +
				"				        C_FCT_FACILITY.CLASS_CD AS F_CLASS_CD, \r\n" +
				"				        C_FCT_FACILITY.STATUS_CD AS F_STATUS_CD, \r\n" +
				"				        C_FCT_FACILITY.EXT_EXPOSED AS F_EXT_EXPOSED, \r\n" +
				"				        C_FCT_FACILITY.DODAAC AS F_DODAAC, \r\n" +
				"				        C_FCT_FACILITY.URL AS F_URL, \r\n" +
				"				        C_FCT_ALT_CODES.ROWID_OBJECT AS FC_ROWID_OBJECT, \r\n" +
				"				        C_FCT_ALT_CODES.HUB_STATE_IND AS FC_HUB_STATE_IND, \r\n" +
				"				        C_FCT_ALT_CODES.TYP_TYPE_ROWID AS FC_TYP_TYPE_ROWID, \r\n" +
				"				        C_FCT_ALT_CODES.CODE AS FC_CODE, \r\n" +
				"				        C_FCT_ALT_CODES.FCT_ROWID AS FC_FCT_ROWID, \r\n" +
				"				        C_FCT_ADDR_REL.ROWID_OBJECT AS FR_ROWID_OBJECT, \r\n" +
				"				        C_FCT_ADDR_REL.HUB_STATE_IND AS FR_HUB_STATE_IND, \r\n" +
				"				        C_FCT_ADDR_REL.FCT_ROWID AS FR_FCT_ROWID, \r\n" +
				"				        C_FCT_ADDR_REL.ADDR_ROWID AS FR_ADDR_ROWID, \r\n" +
				"				        C_FCT_ADDR_REL.VALID_FROM_DT AS FR_VALID_FROM_DT, \r\n" +
				"				        C_FCT_ADDR_REL.VALID_THRU_DT AS FR_VALID_THRU_DT, \r\n" +
				"				        C_CTM_PSTL_ADDR.ROWID_OBJECT AS FA_ROWID_OBJECT, \r\n" +
				"				        C_CTM_PSTL_ADDR.CREATOR AS FA_CREATOR, \r\n" +
				"				        C_CTM_PSTL_ADDR.CREATE_DATE AS FA_CREATE_DATE, \r\n" +
				"				        C_CTM_PSTL_ADDR.UPDATED_BY AS FA_UPDATED_BY, \r\n" +
				"				        C_CTM_PSTL_ADDR.LAST_UPDATE_DATE AS FA_LAST_UPDATE_DATE, \r\n" +
				"				        C_CTM_PSTL_ADDR.HUB_STATE_IND AS FA_HUB_STATE_IND, \r\n" +
				"				        C_CTM_PSTL_ADDR.PO_BOX AS FA_PO_BOX, \r\n" +
				"				        C_CTM_PSTL_ADDR.STREET AS FA_STREET, \r\n" +
				"				        C_CTM_PSTL_ADDR.HOUSE_NUM AS FA_HOUSE_NUM, \r\n" +
				"				        C_CTM_PSTL_ADDR.ADDR_LN_2 AS FA_ADDR_LN_2, \r\n" +
				"				        C_CTM_PSTL_ADDR.ADDR_LN_3 AS FA_ADDR_LN_3, \r\n" +
				"				        C_CTM_PSTL_ADDR.DSTRCT AS FA_DSTRCT, \r\n" +
				"				        C_CTM_PSTL_ADDR.CITY_ROWID AS FA_CITY_ROWID, \r\n" +
				"				        C_CTM_PSTL_ADDR.PSTCD AS FA_PSTCD, \r\n" +
				"				        C_CTM_PSTL_ADDR.TRTY_ROWID AS FA_TRTY_ROWID, \r\n" +
				"				        C_CTM_PSTL_ADDR.CTRY_ROWID AS FA_CTRY_ROWID, \r\n" +
				"				        C_CTM_PSTL_ADDR.TAX_JURN_CD AS FA_TAX_JURN_CD, \r\n" +
				"				        C_CTM_PSTL_ADDR.CITY AS FA_CITY, \r\n" +
				"				        C_CTM_PSTL_ADDR.LAT_GEOSPTL AS FA_LAT_GEOSPTL, \r\n" +
				"				        C_CTM_PSTL_ADDR.LNG_GEOSPTL AS FA_LNG_GEOSPTL, \r\n" +
				"				        C_CTM_PSTL_ADDR.FCT_ROWID AS FA_FCT_ROWID, \r\n" +
				"				        C_CTM_PSTL_ADDR.PSTCD_ROWID AS FA_PSTCD_ROWID, \r\n" +
				"				        C_TYP_TYPE.CODE AS FT_ALT_CODE_TYPE_CD, \r\n" +
				"				        C_TYP_TYPE.NAME AS FT_ALT_CODE_TYPE_NM \r\n" +
				"				    FROM \r\n" +
				"				        C_FCT_FACILITY C_FCT_FACILITY \r\n" +
				"				        INNER JOIN C_FCT_ADDR_REL C_FCT_ADDR_REL ON C_FCT_FACILITY.ROWID_OBJECT = C_FCT_ADDR_REL.FCT_ROWID \r\n" +
				"				        RIGHT OUTER JOIN C_FCT_ALT_CODES C_FCT_ALT_CODES ON C_FCT_FACILITY.ROWID_OBJECT = C_FCT_ALT_CODES.FCT_ROWID \r\n" +
				"				        INNER JOIN C_CTM_PSTL_ADDR C_CTM_PSTL_ADDR ON C_CTM_PSTL_ADDR.ROWID_OBJECT = C_FCT_ADDR_REL.ADDR_ROWID \r\n" +
				"				        INNER JOIN C_TYP_TYPE ON C_TYP_TYPE.ROWID_OBJECT = C_FCT_ALT_CODES.TYP_TYPE_ROWID \r\n" +
				"				    WHERE \r\n" +
				"				            C_FCT_ADDR_REL.HUB_STATE_IND = 1 \r\n" +
				"				        AND \r\n" +
				"				            C_FCT_FACILITY.ROWID_OBJECT = ( \r\n" +
				"				                SELECT \r\n" +
				"				                    FCT_ROWID \r\n" +
				"				                FROM \r\n" +
				"				                    C_FCT_ALT_CODES \r\n" +
				"				                WHERE \r\n" +
				"				                    C_FCT_ALT_CODES.CODE = :CODE \r\n" +
				"				            ) \r\n" +
				"				        AND \r\n" +
				"				            C_FCT_FACILITY.CLASS_CD = 'OPS' \r\n" +
				"				),GDA AS ( \r\n" +
				"				    SELECT \r\n" +
				"				        C_GDA_DFND_AREA.ROWID_OBJECT MDM_ID, \r\n" +
				"				        C_GDA_DFND_AREA.NAME NAME, \r\n" +
				"				        C_ALT_CODE.CODE GEOID, \r\n" +
				"				        COUNTRY_CODE.CODE CODE, \r\n" +
				"				            CASE \r\n" +
				"				                WHEN C_GDA_DFND_AREA.TYP_TYPE_CD = 'GDA.COUNTRY'    THEN 'COUNTRY' \r\n" +
				"				                WHEN C_GDA_DFND_AREA.TYP_TYPE_CD = 'GDA.CITY'       THEN 'CITY' \r\n" +
				"                               WHEN c_gda_dfnd_area.typ_type_cd = 'GDA.CITY_SUBAREA'  THEN 'SUBCITY'\r\n" +
				"				                WHEN C_GDA_DFND_AREA.TYP_TYPE_CD = 'GDA.STATE/PROV' THEN 'REGION' \r\n" +
				"				            END \r\n" +
				"				        AS TYP_CD \r\n" +
				"				    FROM \r\n" +
				"				        MDM_INFM_SMDS.C_GDA_DFND_AREA C_GDA_DFND_AREA \r\n" +
				"				        INNER JOIN MDM_INFM_SMDS.C_ALT_CODE REGION ON REGION.GDA_DFND_AREA_ROWID = C_GDA_DFND_AREA.ROWID_OBJECT \r\n" +
				"				        INNER JOIN MDM_INFM_SMDS.C_TYP_TYPE ON REGION.TYP_TYPE_ROWID = C_TYP_TYPE.ROWID_OBJECT \r\n" +
				"				        INNER JOIN MDM_INFM_SMDS.C_ALT_CODE ON \r\n" +
				"				            C_ALT_CODE.GDA_DFND_AREA_ROWID = C_GDA_DFND_AREA.ROWID_OBJECT \r\n" +
				"				        AND \r\n" +
				"				            C_ALT_CODE.TYP_TYPE_ROWID = C_TYP_TYPE.ROWID_OBJECT \r\n" +
				"				        INNER JOIN MDM_INFM_SMDS.C_ALT_CODE COUNTRY_CODE ON COUNTRY_CODE.GDA_DFND_AREA_ROWID = C_GDA_DFND_AREA.ROWID_OBJECT \r\n" +
				"				        INNER JOIN MDM_INFM_SMDS.C_TYP_TYPE ISO_CTRY_TYPE ON COUNTRY_CODE.TYP_TYPE_ROWID = ISO_CTRY_TYPE.ROWID_OBJECT \r\n" +
				"				        INNER JOIN MDM_INFM_SMDS.C_ALT_CODE CITY ON \r\n" +
				"				            CITY.GDA_DFND_AREA_ROWID = C_GDA_DFND_AREA.ROWID_OBJECT \r\n" +
				"				        AND \r\n" +
				"				            CITY.TYP_TYPE_ROWID = C_TYP_TYPE.ROWID_OBJECT \r\n" +
				"				    WHERE \r\n" +
				"				            C_GDA_DFND_AREA.HUB_STATE_IND = 1 \r\n" +
				"				        AND \r\n" +
				"				            C_GDA_DFND_AREA.TYP_TYPE_CD IN ( \r\n" +
				"				                'GDA.COUNTRY','GDA.CITY','GDA.STATE/PROV','GDA.CITY_SUBAREA' \r\n" +
				"				            ) \r\n" +
				"				        AND \r\n" +
				"				            C_GDA_DFND_AREA.ACTIVE_FLAG = 'Y' \r\n" +
				"				        AND \r\n" +
				"				            C_TYP_TYPE.CODE = 'ALT_CODE.GEOID' \r\n" +
				"				        AND \r\n" +
				"				            ISO_CTRY_TYPE.CODE IN ( \r\n" +
				"				                'ALT_CODE.ISO_TRTY','ALT_CODE.RKST' \r\n" +
				"				            ) \r\n" +
				"				        AND \r\n" +
				"				            C_GDA_DFND_AREA.ROWID_OBJECT IN ( \r\n" +
				"				                SELECT \r\n" +
				"				                    FA_CITY_ROWID AS ROWIDO \r\n" +
				"				                FROM \r\n" +
				"				                    PKG_VIEW_OPS_FACT \r\n" +
				"				                UNION ALL \r\n" +
				"				                SELECT \r\n" +
				"				                    FA_CTRY_ROWID AS ROWIDO \r\n" +
				"				                FROM \r\n" +
				"				                    PKG_VIEW_OPS_FACT \r\n" +
				"				                UNION ALL \r\n" +
				"				                SELECT \r\n" +
				"				                    FA_TRTY_ROWID AS ROWIDO \r\n" +
				"				                FROM \r\n" +
				"				                    PKG_VIEW_OPS_FACT \r\n" +
				"				            ) \r\n" +
				"				),REGION_GEOID AS ( \r\n" +
				"				    SELECT \r\n" +
				"				        C_GDA_DFND_AREA.MDM_ID MDM_ID, \r\n" +
				"				        C_GDA_DFND_AREA.NAME NAME, \r\n" +
				"				        C_GDA_DFND_AREA.GEOID GEOID, \r\n" +
				"				        C_GDA_DFND_AREA.CODE CODE, \r\n" +
				"				        C_GDA_DFND_AREA.TYP_CD TYP_CD \r\n" +
				"				    FROM \r\n" +
				"				        GDA C_GDA_DFND_AREA \r\n" +
				"				        INNER JOIN PKG_VIEW_OPS_FACT ON ( \r\n" +
				"				            NVL( \r\n" +
				"				                PKG_VIEW_OPS_FACT.FA_TRTY_ROWID, \r\n" +
				"				                'XX' \r\n" +
				"				            ) = NVL( \r\n" +
				"				                C_GDA_DFND_AREA.MDM_ID, \r\n" +
				"				                'XX' \r\n" +
				"				            ) \r\n" +
				"				        ) AND \r\n" +
				"				            C_GDA_DFND_AREA.TYP_CD = 'REGION' \r\n" +
				"				),OPERATIONAL_FCT_INFO AS ( \r\n" +
				"				    SELECT \r\n" +
				"				        C_FCT_OPS.ROWID_OBJECT, \r\n" +
				"				        C_FCT_OPS.FCT_ROWID, \r\n" +
				"				        C_TYP_TYPE.CODE FCT_OPS_TYPE_CD, \r\n" +
				"				        C_TYP_TYPE.NAME FCT_OPS_TYPE_NM, \r\n" +
				"				        WEIGHT_LMT_CRANE, \r\n" +
				"				        WEIGHT_LMT_YARD, \r\n" +
				"				        VESSEL_AGENT, \r\n" +
				"				        GPS_FLAG, \r\n" +
				"				        GSM_FLAG, \r\n" +
				"				        OCE_FRGHT_PR, \r\n" +
				"				        C_FCT_OPS_TYP_REL.VALID_FROM_DT FCT_OPS_TYPE_START_DT, \r\n" +
				"				        C_FCT_OPS_TYP_REL.VALID_THRU_DT FCT_OPS_TYPE_END_DT \r\n" +
				"				    FROM \r\n" +
				"				        C_FCT_OPS \r\n" +
				"				        LEFT OUTER JOIN ( \r\n" +
				"				            SELECT \r\n" +
				"				                * \r\n" +
				"				            FROM \r\n" +
				"				                C_FCT_OPS_TYP_REL \r\n" +
				"				            WHERE \r\n" +
				"				                    HUB_STATE_IND = 1 \r\n" +
				"				                AND \r\n" +
				"				                    TRUNC( \r\n" +
				"				                        NVL( \r\n" +
				"				                            C_FCT_OPS_TYP_REL.VALID_THRU_DT, \r\n" +
				"				                            SYSDATE \r\n" +
				"				                        ), \r\n" +
				"				                        'DDD' \r\n" +
				"				                    ) >= TRUNC( \r\n" +
				"				                        SYSDATE, \r\n" +
				"				                        'DDD' \r\n" +
				"				                    ) \r\n" +
				"				        ) C_FCT_OPS_TYP_REL ON C_FCT_OPS_TYP_REL.FCT_OPS_ROWID = C_FCT_OPS.ROWID_OBJECT \r\n" +
				"				        LEFT OUTER JOIN C_TYP_TYPE ON C_FCT_OPS_TYP_REL.TYP_TYPE_ROWID = C_TYP_TYPE.ROWID_OBJECT \r\n" +
				"				    WHERE \r\n" +
				"				        C_FCT_OPS.HUB_STATE_IND = 1 \r\n" +
				"				) SELECT DISTINCT /*+ NO_CPU_COSTING */ \r\n" +
				"				    PKG_VIEW_OPS_FC.F_ROWID_OBJECT FCT_MDM_ID, \r\n" +
				"				    PKG_VIEW_OPS_FC.F_CLASS_CD FCT_CATEGORY, \r\n" +
				"				    PKG_VIEW_OPS_FC.F_FACILITY_NAME FCT_NAME, \r\n" +
				"				    PKG_VIEW_OPS_FC.F_HUB_STATE_IND FCT_HUB_STATE, \r\n" +
				"				    PKG_VIEW_OPS_FC.F_STATUS_CD FCT_STATUS, \r\n" +
				"				    PKG_VIEW_OPS_FC.F_CREATE_DATE FCT_CREATE_DT, \r\n" +
				"				    PKG_VIEW_OPS_FC.F_UPDATED_BY FCT_LAST_UPDATED_BY, \r\n" +
				"				    PKG_VIEW_OPS_FC.F_CREATOR FCT_CREATOR, \r\n" +
				"				    PKG_VIEW_OPS_FC.F_DELETED_BY FCT_DELETED_BY, \r\n" +
				"				    PKG_VIEW_OPS_FC.F_LAST_UPDATE_DATE FCT_LAST_UPDATE_DT, \r\n" +
				"				    PKG_VIEW_OPS_FC.F_LAST_ROWID_SYSTEM LAST_ROWID_SYSTEM, \r\n" +
				"				    PKG_VIEW_OPS_FC.F_DELETED_DATE FCT_DELETED_DT, \r\n" +
				"				    PKG_VIEW_OPS_FC.F_EXT_OWNED EXT_OWNED_FLAG, \r\n" +
				"				    PKG_VIEW_OPS_FC.F_EXT_EXPOSED EXT_EXPOSED_FLAG, \r\n" +
				"				    PKG_VIEW_OPS_FC.F_URL FCT_URL, \r\n" +
				"				    PKG_VIEW_OPS_FC.F_DODAAC DODAAC, \r\n" +
				"				    PKG_VIEW_OPS_FC.FC_HUB_STATE_IND FCT_ALT_CODE_HUB_STATE, \r\n" +
				"				    PKG_VIEW_OPS_FC.FC_TYP_TYPE_ROWID FCT_ALT_CD_TYP_ROWID, \r\n" +
				"				    PKG_VIEW_OPS_FC.FT_ALT_CODE_TYPE_CD ALT_CODE_TYPE_CD, \r\n" +
				"				    PKG_VIEW_OPS_FC.FT_ALT_CODE_TYPE_NM ALT_CODE_TYPE_NM, \r\n" +
				"				    PKG_VIEW_OPS_FC.FC_CODE FCT_ALT_CODE, \r\n" +
				"				    PKG_VIEW_OPS_FC.FR_HUB_STATE_IND FCT_ADDR_REL_HUB_STATE, \r\n" +
				"				    PKG_VIEW_OPS_FC.FR_ADDR_ROWID ADDR_MDM_ID, \r\n" +
				"				    PKG_VIEW_OPS_FC.FR_VALID_FROM_DT FCT_ADDR_VALID_FROM_DT, \r\n" +
				"				    PKG_VIEW_OPS_FC.FR_VALID_THRU_DT FCT_ADDR_VALID_THRU_DT, \r\n" +
				"				    PKG_VIEW_OPS_FC.FA_HUB_STATE_IND ADDR_HUB_STATE, \r\n" +
				"				    PKG_VIEW_OPS_FC.FA_LAST_UPDATE_DATE ADDR_LAST_UPDATE_DT, \r\n" +
				"				    PKG_VIEW_OPS_FC.FA_UPDATED_BY ADDR_UPDATED_BY, \r\n" +
				"				    PKG_VIEW_OPS_FC.FA_CREATE_DATE ADDR_CREATE_DT, \r\n" +
				"				    PKG_VIEW_OPS_FC.FA_CREATOR ADDR_CREATOR, \r\n" +
				"				    PKG_VIEW_OPS_FC.FA_HOUSE_NUM HOUSE_NUM, \r\n" +
				"				    PKG_VIEW_OPS_FC.FA_DSTRCT DISTRICT, \r\n" +
				"				    PKG_VIEW_OPS_FC.FA_PSTCD POSTAL_CODE, \r\n" +
				"				    PKG_VIEW_OPS_FC.FA_TAX_JURN_CD TAX_JURN_CD, \r\n" +
				"				    PKG_VIEW_OPS_FC.FA_PO_BOX PO_BOX, \r\n" +
				"				    PKG_VIEW_OPS_FC.FA_STREET STREET, \r\n" +
				"				    PKG_VIEW_OPS_FC.FA_ADDR_LN_2 BUILDING_NUM, \r\n" +
				"				    PKG_VIEW_OPS_FC.FA_ADDR_LN_3 SUBURB, \r\n" +
				"				        CASE \r\n" +
				"				            WHEN CITY_GEOID.TYP_CD IN ('SUBCITY','CITY')  THEN CITY_GEOID.MDM_ID \r\n" +
				"				        END \r\n" +
				"				    AS CITY_MDM_ID, \r\n" +
				"				        CASE \r\n" +
				"				            WHEN CITY_GEOID.TYP_CD IN ('SUBCITY','CITY')  THEN CITY_GEOID.GEOID \r\n" +
				"				        END \r\n" +
				"				    AS CITY_GEOID, \r\n" +
				"				        CASE \r\n" +
				"				            WHEN CITY_GEOID.TYP_CD IN ('SUBCITY','CITY')  THEN CITY_GEOID.NAME \r\n" +
				"				        END \r\n" +
				"				    AS CITY, \r\n" +
				"				    PKG_VIEW_OPS_FC.FA_LAT_GEOSPTL LAT_GEOSPTL, \r\n" +
				"				    PKG_VIEW_OPS_FC.FA_LNG_GEOSPTL LNG_GEOSPTL, \r\n" +
				"				        CASE \r\n" +
				"				            WHEN COUNTRY_GEOID.TYP_CD = 'COUNTRY' THEN COUNTRY_GEOID.MDM_ID \r\n" +
				"				        END \r\n" +
				"				    AS CTRY_MDM_ID, \r\n" +
				"				        CASE \r\n" +
				"				            WHEN COUNTRY_GEOID.TYP_CD = 'COUNTRY' THEN COUNTRY_GEOID.GEOID \r\n" +
				"				        END \r\n" +
				"				    AS COUNTRY_GEOID, \r\n" +
				"				        CASE \r\n" +
				"				            WHEN COUNTRY_GEOID.TYP_CD = 'COUNTRY' THEN COUNTRY_GEOID.NAME \r\n" +
				"				        END \r\n" +
				"				    AS COUNTRY_NAME, \r\n" +
				"				        CASE \r\n" +
				"				            WHEN COUNTRY_GEOID.TYP_CD = 'COUNTRY' THEN COUNTRY_GEOID.CODE \r\n" +
				"				        END \r\n" +
				"				    AS ISO_COUNTRY_CODE, \r\n" +
				"				        CASE \r\n" +
				"				            WHEN REGION_GEOID.TYP_CD = 'REGION' THEN REGION_GEOID.MDM_ID \r\n" +
				"				        END \r\n" +
				"				    AS REGION_MDM_ID, \r\n" +
				"				        CASE \r\n" +
				"				            WHEN REGION_GEOID.TYP_CD = 'REGION' THEN REGION_GEOID.GEOID \r\n" +
				"				        END \r\n" +
				"				    AS REGION_GEOID, \r\n" +
				"				        CASE \r\n" +
				"				            WHEN REGION_GEOID.TYP_CD = 'REGION' THEN REGION_GEOID.NAME \r\n" +
				"				        END \r\n" +
				"				    AS REGION_NAME, \r\n" +
				"				        CASE \r\n" +
				"				            WHEN REGION_GEOID.TYP_CD = 'REGION' THEN REGION_GEOID.CODE \r\n" +
				"				        END \r\n" +
				"				    AS ISO_REGION_CODE, \r\n" +
				"				    C_FCT_DFND_REL.GDA_ROWID GDA_ROWID, \r\n" +
				"				    C_FCT_DFND_REL.VALID_FROM_DT FCT_DFND_REL_VALID_FROM_DT, \r\n" +
				"				    C_FCT_DFND_REL.VALID_THRU_DT FCT_DFND_REL_VALID_THRU_DT, \r\n" +
				"				    OPERATIONAL_FCT_INFO.FCT_OPS_TYPE_CD FCT_OPS_TYPE_CD, \r\n" +
				"				    OPERATIONAL_FCT_INFO.FCT_OPS_TYPE_NM FCT_OPS_TYPE_NM, \r\n" +
				"				    OPERATIONAL_FCT_INFO.FCT_OPS_TYPE_START_DT FCT_OPS_TYPE_START_DT, \r\n" +
				"				    OPERATIONAL_FCT_INFO.FCT_OPS_TYPE_END_DT FCT_OPS_TYPE_END_DT, \r\n" +
				"				    OPERATIONAL_FCT_INFO.WEIGHT_LMT_CRANE WEIGHT_LMT_CRANE, \r\n" +
				"				    OPERATIONAL_FCT_INFO.WEIGHT_LMT_YARD WEIGHT_LMT_YARD, \r\n" +
				"				    OPERATIONAL_FCT_INFO.VESSEL_AGENT VESSEL_AGENT, \r\n" +
				"				    OPERATIONAL_FCT_INFO.GPS_FLAG GPS_FLAG, \r\n" +
				"				    OPERATIONAL_FCT_INFO.GSM_FLAG GSM_FLAG, \r\n" +
				"				    OPERATIONAL_FCT_INFO.OCE_FRGHT_PR OCE_FRGHT_PR \r\n" +
				"				FROM \r\n" +
				"				    PKG_VIEW_OPS_FACT PKG_VIEW_OPS_FC \r\n" +
				"				    LEFT OUTER JOIN MDM_INFM_SMDS.C_FCT_DFND_REL ON C_FCT_DFND_REL.FCT_ROWID = PKG_VIEW_OPS_FC.F_ROWID_OBJECT \r\n" +
				"				    INNER JOIN GDA CITY_GEOID ON CITY_GEOID.MDM_ID = PKG_VIEW_OPS_FC.FA_CITY_ROWID \r\n" +
				"				    INNER JOIN GDA COUNTRY_GEOID ON COUNTRY_GEOID.MDM_ID = PKG_VIEW_OPS_FC.FA_CTRY_ROWID \r\n" +
				"				    LEFT OUTER JOIN REGION_GEOID REGION_GEOID ON REGION_GEOID.MDM_ID = PKG_VIEW_OPS_FC.FA_TRTY_ROWID \r\n" +
				"				    LEFT OUTER JOIN OPERATIONAL_FCT_INFO ON OPERATIONAL_FCT_INFO.FCT_ROWID = PKG_VIEW_OPS_FC.F_ROWID_OBJECT \r\n" +
				"				WHERE \r\n" +
				"				        CITY_GEOID.TYP_CD IN  ('SUBCITY','CITY') \r\n" +
				"				    AND \r\n" +
				"				        COUNTRY_GEOID.TYP_CD = 'COUNTRY'";
		return opsFacilityQuery;
	}

	private String getRetrieveCommFacilityAllQuery() {
		LOGGER.info("Inside getRetrieveCommFacilityAllQuery");
		String commFacilityQuery="WITH PKG_VIEW_COMM_FACT AS (\r\n" +
				"    SELECT\r\n" +
				"        C_FCT_FACILITY.ROWID_OBJECT AS F_ROWID_OBJECT,\r\n" +
				"        C_FCT_FACILITY.CREATOR AS F_CREATOR,\r\n" +
				"        C_FCT_FACILITY.CREATE_DATE AS F_CREATE_DATE,\r\n" +
				"        C_FCT_FACILITY.UPDATED_BY AS F_UPDATED_BY,\r\n" +
				"        C_FCT_FACILITY.LAST_UPDATE_DATE AS F_LAST_UPDATE_DATE,\r\n" +
				"        C_FCT_FACILITY.DELETED_IND AS F_DELETED_IND,\r\n" +
				"        C_FCT_FACILITY.DELETED_BY AS F_DELETED_BY,\r\n" +
				"        C_FCT_FACILITY.DELETED_DATE AS F_DELETED_DATE,\r\n" +
				"        C_FCT_FACILITY.LAST_ROWID_SYSTEM AS F_LAST_ROWID_SYSTEM,\r\n" +
				"        C_FCT_FACILITY.HUB_STATE_IND AS F_HUB_STATE_IND,\r\n" +
				"        C_FCT_FACILITY.FACILITY_NAME AS F_FACILITY_NAME,\r\n" +
				"        C_FCT_FACILITY.EXT_OWNED AS F_EXT_OWNED,\r\n" +
				"        C_FCT_FACILITY.CLASS_CD AS F_CLASS_CD,\r\n" +
				"        C_FCT_FACILITY.STATUS_CD AS F_STATUS_CD,\r\n" +
				"        C_FCT_FACILITY.EXT_EXPOSED AS F_EXT_EXPOSED,\r\n" +
				"        C_FCT_FACILITY.DODAAC AS F_DODAAC,\r\n" +
				"        C_FCT_FACILITY.URL AS F_URL,\r\n" +
				"        C_FCT_ALT_CODES.ROWID_OBJECT AS FC_ROWID_OBJECT,\r\n" +
				"        C_FCT_ALT_CODES.HUB_STATE_IND AS FC_HUB_STATE_IND,\r\n" +
				"        C_FCT_ALT_CODES.TYP_TYPE_ROWID AS FC_TYP_TYPE_ROWID,\r\n" +
				"        C_FCT_ALT_CODES.CODE AS FC_CODE,\r\n" +
				"        C_FCT_ALT_CODES.FCT_ROWID AS FC_FCT_ROWID,\r\n" +
				"        C_FCT_ADDR_REL.ROWID_OBJECT AS FR_ROWID_OBJECT,\r\n" +
				"        C_FCT_ADDR_REL.HUB_STATE_IND AS FR_HUB_STATE_IND,\r\n" +
				"        C_FCT_ADDR_REL.FCT_ROWID AS FR_FCT_ROWID,\r\n" +
				"        C_FCT_ADDR_REL.ADDR_ROWID AS FR_ADDR_ROWID,\r\n" +
				"        C_FCT_ADDR_REL.VALID_FROM_DT AS FR_VALID_FROM_DT,\r\n" +
				"        C_FCT_ADDR_REL.VALID_THRU_DT AS FR_VALID_THRU_DT,\r\n" +
				"        C_CTM_PSTL_ADDR.ROWID_OBJECT AS FA_ROWID_OBJECT,\r\n" +
				"        C_CTM_PSTL_ADDR.CREATOR AS FA_CREATOR,\r\n" +
				"        C_CTM_PSTL_ADDR.CREATE_DATE AS FA_CREATE_DATE,\r\n" +
				"        C_CTM_PSTL_ADDR.UPDATED_BY AS FA_UPDATED_BY,\r\n" +
				"        C_CTM_PSTL_ADDR.LAST_UPDATE_DATE AS FA_LAST_UPDATE_DATE,\r\n" +
				"        C_CTM_PSTL_ADDR.HUB_STATE_IND AS FA_HUB_STATE_IND,\r\n" +
				"        C_CTM_PSTL_ADDR.PO_BOX AS FA_PO_BOX,\r\n" +
				"        C_CTM_PSTL_ADDR.STREET AS FA_STREET,\r\n" +
				"        C_CTM_PSTL_ADDR.HOUSE_NUM AS FA_HOUSE_NUM,\r\n" +
				"        C_CTM_PSTL_ADDR.ADDR_LN_2 AS FA_ADDR_LN_2,\r\n" +
				"        C_CTM_PSTL_ADDR.ADDR_LN_3 AS FA_ADDR_LN_3,\r\n" +
				"        C_CTM_PSTL_ADDR.DSTRCT AS FA_DSTRCT,\r\n" +
				"        C_CTM_PSTL_ADDR.CITY_ROWID AS FA_CITY_ROWID,\r\n" +
				"        C_CTM_PSTL_ADDR.PSTCD AS FA_PSTCD,\r\n" +
				"        C_CTM_PSTL_ADDR.TRTY_ROWID AS FA_TRTY_ROWID,\r\n" +
				"        C_CTM_PSTL_ADDR.CTRY_ROWID AS FA_CTRY_ROWID,\r\n" +
				"        C_CTM_PSTL_ADDR.TAX_JURN_CD AS FA_TAX_JURN_CD,\r\n" +
				"        C_CTM_PSTL_ADDR.CITY AS FA_CITY,\r\n" +
				"        C_CTM_PSTL_ADDR.LAT_GEOSPTL AS FA_LAT_GEOSPTL,\r\n" +
				"        C_CTM_PSTL_ADDR.LNG_GEOSPTL AS FA_LNG_GEOSPTL,\r\n" +
				"        C_CTM_PSTL_ADDR.FCT_ROWID AS FA_FCT_ROWID,\r\n" +
				"        C_CTM_PSTL_ADDR.PSTCD_ROWID AS FA_PSTCD_ROWID,\r\n" +
				"        C_TYP_TYPE.CODE AS FT_ALT_CODE_TYPE_CD,\r\n" +
				"        C_TYP_TYPE.NAME AS FT_ALT_CODE_TYPE_NM\r\n" +
				"    FROM\r\n" +
				"        C_FCT_FACILITY C_FCT_FACILITY\r\n" +
				"        INNER JOIN C_FCT_ADDR_REL C_FCT_ADDR_REL ON C_FCT_FACILITY.ROWID_OBJECT = C_FCT_ADDR_REL.FCT_ROWID\r\n" +
				"        RIGHT OUTER JOIN C_FCT_ALT_CODES C_FCT_ALT_CODES ON C_FCT_FACILITY.ROWID_OBJECT = C_FCT_ALT_CODES.FCT_ROWID\r\n" +
				"        INNER JOIN C_CTM_PSTL_ADDR C_CTM_PSTL_ADDR ON C_CTM_PSTL_ADDR.ROWID_OBJECT = C_FCT_ADDR_REL.ADDR_ROWID\r\n" +
				"        INNER JOIN C_TYP_TYPE ON C_TYP_TYPE.ROWID_OBJECT = C_FCT_ALT_CODES.TYP_TYPE_ROWID\r\n" +
				"    WHERE\r\n" +
				"            C_FCT_ADDR_REL.HUB_STATE_IND = 1\r\n" +
				"        AND\r\n" +
				"            C_FCT_FACILITY.ROWID_OBJECT = (\r\n" +
				"                SELECT\r\n" +
				"                    FCT_ROWID\r\n" +
				"                FROM\r\n" +
				"                    C_FCT_ALT_CODES\r\n" +
				"                WHERE\r\n" +
				"                    C_FCT_ALT_CODES.CODE = :CODE\r\n" +
				"            )\r\n" +
				"        AND\r\n" +
				"            C_FCT_FACILITY.CLASS_CD = 'COMM'\r\n" +
				"),GDA AS (\r\n" +
				"    SELECT\r\n" +
				"        C_GDA_DFND_AREA.ROWID_OBJECT MDM_ID,\r\n" +
				"        C_GDA_DFND_AREA.NAME NAME,\r\n" +
				"        C_ALT_CODE.CODE GEOID,\r\n" +
				"        COUNTRY_CODE.CODE CODE,\r\n" +
				"            CASE\r\n" +
				"                WHEN C_GDA_DFND_AREA.TYP_TYPE_CD = 'GDA.COUNTRY'    THEN 'COUNTRY'\r\n" +
				"                WHEN C_GDA_DFND_AREA.TYP_TYPE_CD = 'GDA.CITY'       THEN 'CITY'\r\n" +
				"                WHEN c_gda_dfnd_area.typ_type_cd = 'GDA.CITY_SUBAREA'  THEN 'SUBCITY'\r\n" +
				"                WHEN C_GDA_DFND_AREA.TYP_TYPE_CD = 'GDA.STATE/PROV' THEN 'REGION'\r\n" +
				"            END\r\n" +
				"        AS TYP_CD\r\n" +
				"    FROM\r\n" +
				"        MDM_INFM_SMDS.C_GDA_DFND_AREA C_GDA_DFND_AREA\r\n" +
				"        INNER JOIN MDM_INFM_SMDS.C_ALT_CODE REGION ON REGION.GDA_DFND_AREA_ROWID = C_GDA_DFND_AREA.ROWID_OBJECT\r\n" +
				"        INNER JOIN MDM_INFM_SMDS.C_TYP_TYPE ON REGION.TYP_TYPE_ROWID = C_TYP_TYPE.ROWID_OBJECT\r\n" +
				"        INNER JOIN MDM_INFM_SMDS.C_ALT_CODE ON\r\n" +
				"            C_ALT_CODE.GDA_DFND_AREA_ROWID = C_GDA_DFND_AREA.ROWID_OBJECT\r\n" +
				"        AND\r\n" +
				"            C_ALT_CODE.TYP_TYPE_ROWID = C_TYP_TYPE.ROWID_OBJECT\r\n" +
				"        INNER JOIN MDM_INFM_SMDS.C_ALT_CODE COUNTRY_CODE ON COUNTRY_CODE.GDA_DFND_AREA_ROWID = C_GDA_DFND_AREA.ROWID_OBJECT\r\n" +
				"        INNER JOIN MDM_INFM_SMDS.C_TYP_TYPE ISO_CTRY_TYPE ON COUNTRY_CODE.TYP_TYPE_ROWID = ISO_CTRY_TYPE.ROWID_OBJECT\r\n" +
				"        INNER JOIN MDM_INFM_SMDS.C_ALT_CODE CITY ON\r\n" +
				"            CITY.GDA_DFND_AREA_ROWID = C_GDA_DFND_AREA.ROWID_OBJECT\r\n" +
				"        AND\r\n" +
				"            CITY.TYP_TYPE_ROWID = C_TYP_TYPE.ROWID_OBJECT\r\n" +
				"    WHERE\r\n" +
				"            C_GDA_DFND_AREA.HUB_STATE_IND = 1\r\n" +
				"        AND\r\n" +
				"            C_GDA_DFND_AREA.TYP_TYPE_CD IN (\r\n" +
				"                'GDA.COUNTRY','GDA.CITY','GDA.STATE/PROV','GDA.CITY_SUBAREA'\r\n" +
				"            )\r\n" +
				"        AND\r\n" +
				"            C_GDA_DFND_AREA.ACTIVE_FLAG = 'Y'\r\n" +
				"        AND\r\n" +
				"            C_TYP_TYPE.CODE = 'ALT_CODE.GEOID'\r\n" +
				"        AND\r\n" +
				"            ISO_CTRY_TYPE.CODE IN (\r\n" +
				"                'ALT_CODE.ISO_TRTY','ALT_CODE.RKST'\r\n" +
				"            )\r\n" +
				"        AND\r\n" +
				"            C_GDA_DFND_AREA.ROWID_OBJECT IN (\r\n" +
				"                SELECT\r\n" +
				"                    FA_CITY_ROWID AS ROWIDO\r\n" +
				"                FROM\r\n" +
				"                    PKG_VIEW_COMM_FACT\r\n" +
				"                UNION ALL\r\n" +
				"                SELECT\r\n" +
				"                    FA_CTRY_ROWID AS ROWIDO\r\n" +
				"                FROM\r\n" +
				"                    PKG_VIEW_COMM_FACT\r\n" +
				"                UNION ALL\r\n" +
				"                SELECT\r\n" +
				"                    FA_TRTY_ROWID AS ROWIDO\r\n" +
				"                FROM\r\n" +
				"                    PKG_VIEW_COMM_FACT\r\n" +
				"            )\r\n" +
				"),REGION_GEOID AS (\r\n" +
				"    SELECT\r\n" +
				"        C_GDA_DFND_AREA.MDM_ID MDM_ID,\r\n" +
				"        C_GDA_DFND_AREA.NAME NAME,\r\n" +
				"        C_GDA_DFND_AREA.GEOID GEOID,\r\n" +
				"        C_GDA_DFND_AREA.CODE CODE,\r\n" +
				"        C_GDA_DFND_AREA.TYP_CD TYP_CD\r\n" +
				"    FROM\r\n" +
				"        GDA C_GDA_DFND_AREA\r\n" +
				"        INNER JOIN PKG_VIEW_COMM_FACT ON (\r\n" +
				"            NVL(\r\n" +
				"                PKG_VIEW_COMM_FACT.FA_TRTY_ROWID,\r\n" +
				"                'XX'\r\n" +
				"            ) = NVL(\r\n" +
				"                C_GDA_DFND_AREA.MDM_ID,\r\n" +
				"                'XX'\r\n" +
				"            )\r\n" +
				"        ) AND\r\n" +
				"            C_GDA_DFND_AREA.TYP_CD = 'REGION'\r\n" +
				"),COMMERCIAL_FCT_INFO AS (\r\n" +
				"    SELECT\r\n" +
				"        C_FCT_COM.FCT_ROWID,\r\n" +
				"        C_TYP_TYPE.CODE COMM_FCT_TYPE_CD,\r\n" +
				"        C_TYP_TYPE.CODE COMM_FCT_TYPE_NM,\r\n" +
				"        C_FCT_BRAND.CODE COMM_FCT_BRAND_CD,\r\n" +
				"        C_FCT_BRAND.NAME COMM_FCT_BRAND_NM,\r\n" +
				"        C_FCT_COMM_FUNC.CODE COMM_FCT_FUNC_CD,\r\n" +
				"        C_FCT_COMM_FUNC.NAME COMM_FCT_FUNC_NM,\r\n" +
				"        C_CTM_INTL_DIALNG_CD.DIALNG_CD COMM_FCT_DIALING_CD,\r\n" +
				"        C_CTM_INTL_DIALNG_CD.DAILNG_CD_DESC COMM_FCT_DAILNG_CD_DESC,\r\n" +
				"        C_FCT_COM.TELECOM_NUM,\r\n" +
				"        C_FCT_COM.IMP_MAIL,\r\n" +
				"        C_FCT_COM.EXP_MAIL\r\n" +
				"    FROM\r\n" +
				"        C_FCT_COM\r\n" +
				"        INNER JOIN C_FCT_COMM_FUNC ON C_FCT_COM.COMM_FUNC_ROWID = C_FCT_COMM_FUNC.ROWID_OBJECT\r\n" +
				"        LEFT OUTER JOIN C_FCT_BRAND ON C_FCT_COM.FCT_BRAND_ROWID = C_FCT_BRAND.ROWID_OBJECT\r\n" +
				"        INNER JOIN C_TYP_TYPE ON C_FCT_COM.TYP_TYPE_ROWID = C_TYP_TYPE.ROWID_OBJECT\r\n" +
				"        LEFT OUTER JOIN C_CTM_INTL_DIALNG_CD ON C_FCT_COM.INTL_DIALING_ROWID = C_CTM_INTL_DIALNG_CD.ROWID_OBJECT\r\n" +
				") SELECT DISTINCT /*+ NO_CPU_COSTING */\r\n" +
				"    PKG_VIEW_COMM_FC.F_ROWID_OBJECT FCT_MDM_ID,\r\n" +
				"    PKG_VIEW_COMM_FC.F_CLASS_CD FCT_CATEGORY,\r\n" +
				"    PKG_VIEW_COMM_FC.F_FACILITY_NAME FCT_NAME,\r\n" +
				"    PKG_VIEW_COMM_FC.F_HUB_STATE_IND FCT_HUB_STATE,\r\n" +
				"    PKG_VIEW_COMM_FC.F_STATUS_CD FCT_STATUS,\r\n" +
				"    PKG_VIEW_COMM_FC.F_CREATE_DATE FCT_CREATE_DT,\r\n" +
				"    PKG_VIEW_COMM_FC.F_UPDATED_BY FCT_LAST_UPDATED_BY,\r\n" +
				"    PKG_VIEW_COMM_FC.F_CREATOR FCT_CREATOR,\r\n" +
				"    PKG_VIEW_COMM_FC.F_DELETED_BY FCT_DELETED_BY,\r\n" +
				"    PKG_VIEW_COMM_FC.F_LAST_UPDATE_DATE FCT_LAST_UPDATE_DT,\r\n" +
				"    PKG_VIEW_COMM_FC.F_LAST_ROWID_SYSTEM LAST_ROWID_SYSTEM,\r\n" +
				"    PKG_VIEW_COMM_FC.F_DELETED_DATE FCT_DELETED_DT,\r\n" +
				"    PKG_VIEW_COMM_FC.F_EXT_OWNED EXT_OWNED_FLAG,\r\n" +
				"    PKG_VIEW_COMM_FC.F_EXT_EXPOSED EXT_EXPOSED_FLAG,\r\n" +
				"    PKG_VIEW_COMM_FC.F_URL FCT_URL,\r\n" +
				"    PKG_VIEW_COMM_FC.F_DODAAC DODAAC,\r\n" +
				"    PKG_VIEW_COMM_FC.FC_HUB_STATE_IND FCT_ALT_CODE_HUB_STATE,\r\n" +
				"    PKG_VIEW_COMM_FC.FC_TYP_TYPE_ROWID FCT_ALT_CD_TYP_ROWID,\r\n" +
				"    PKG_VIEW_COMM_FC.FT_ALT_CODE_TYPE_CD ALT_CODE_TYPE_CD,\r\n" +
				"    PKG_VIEW_COMM_FC.FT_ALT_CODE_TYPE_NM ALT_CODE_TYPE_NM,\r\n" +
				"    PKG_VIEW_COMM_FC.FC_CODE FCT_ALT_CODE,\r\n" +
				"    PKG_VIEW_COMM_FC.FR_HUB_STATE_IND FCT_ADDR_REL_HUB_STATE,\r\n" +
				"    PKG_VIEW_COMM_FC.FR_ADDR_ROWID ADDR_MDM_ID,\r\n" +
				"    PKG_VIEW_COMM_FC.FR_VALID_FROM_DT FCT_ADDR_VALID_FROM_DT,\r\n" +
				"    PKG_VIEW_COMM_FC.FR_VALID_THRU_DT FCT_ADDR_VALID_THRU_DT,\r\n" +
				"    PKG_VIEW_COMM_FC.FA_HUB_STATE_IND ADDR_HUB_STATE,\r\n" +
				"    PKG_VIEW_COMM_FC.FA_LAST_UPDATE_DATE ADDR_LAST_UPDATE_DT,\r\n" +
				"    PKG_VIEW_COMM_FC.FA_UPDATED_BY ADDR_UPDATED_BY,\r\n" +
				"    PKG_VIEW_COMM_FC.FA_CREATE_DATE ADDR_CREATE_DT,\r\n" +
				"    PKG_VIEW_COMM_FC.FA_CREATOR ADDR_CREATOR,\r\n" +
				"    PKG_VIEW_COMM_FC.FA_HOUSE_NUM HOUSE_NUM,\r\n" +
				"    PKG_VIEW_COMM_FC.FA_DSTRCT DISTRICT,\r\n" +
				"    PKG_VIEW_COMM_FC.FA_PSTCD POSTAL_CODE,\r\n" +
				"    PKG_VIEW_COMM_FC.FA_TAX_JURN_CD TAX_JURN_CD,\r\n" +
				"    PKG_VIEW_COMM_FC.FA_PO_BOX PO_BOX,\r\n" +
				"    PKG_VIEW_COMM_FC.FA_STREET STREET,\r\n" +
				"    PKG_VIEW_COMM_FC.FA_ADDR_LN_2 BUILDING_NUM,\r\n" +
				"    PKG_VIEW_COMM_FC.FA_ADDR_LN_3 SUBURB,\r\n" +
				"        CASE\r\n" +
				"            WHEN CITY_GEOID.TYP_CD IN ('SUBCITY','CITY')  THEN CITY_GEOID.MDM_ID\r\n" +
				"        END\r\n" +
				"    AS CITY_MDM_ID,\r\n" +
				"        CASE\r\n" +
				"            WHEN CITY_GEOID.TYP_CD IN ('SUBCITY','CITY')  THEN CITY_GEOID.GEOID\r\n" +
				"        END\r\n" +
				"    AS CITY_GEOID,\r\n" +
				"        CASE\r\n" +
				"            WHEN CITY_GEOID.TYP_CD IN ('SUBCITY','CITY')  THEN CITY_GEOID.NAME\r\n" +
				"        END\r\n" +
				"    AS CITY ,\r\n" +
				"    PKG_VIEW_COMM_FC.FA_LAT_GEOSPTL LAT_GEOSPTL,\r\n" +
				"    PKG_VIEW_COMM_FC.FA_LNG_GEOSPTL LNG_GEOSPTL,\r\n" +
				"        CASE\r\n" +
				"            WHEN COUNTRY_GEOID.TYP_CD = 'COUNTRY' THEN COUNTRY_GEOID.MDM_ID\r\n" +
				"        END\r\n" +
				"    AS CTRY_MDM_ID,\r\n" +
				"        CASE\r\n" +
				"            WHEN COUNTRY_GEOID.TYP_CD = 'COUNTRY' THEN COUNTRY_GEOID.GEOID\r\n" +
				"        END\r\n" +
				"    AS COUNTRY_GEOID,\r\n" +
				"        CASE\r\n" +
				"            WHEN COUNTRY_GEOID.TYP_CD = 'COUNTRY' THEN COUNTRY_GEOID.NAME\r\n" +
				"        END\r\n" +
				"    AS COUNTRY_NAME,\r\n" +
				"        CASE\r\n" +
				"            WHEN COUNTRY_GEOID.TYP_CD = 'COUNTRY' THEN COUNTRY_GEOID.CODE\r\n" +
				"        END\r\n" +
				"    AS ISO_COUNTRY_CODE,\r\n" +
				"        CASE\r\n" +
				"            WHEN REGION_GEOID.TYP_CD = 'REGION' THEN REGION_GEOID.MDM_ID\r\n" +
				"        END\r\n" +
				"    AS REGION_MDM_ID,\r\n" +
				"        CASE\r\n" +
				"            WHEN REGION_GEOID.TYP_CD = 'REGION' THEN REGION_GEOID.GEOID\r\n" +
				"        END\r\n" +
				"    AS REGION_GEOID,\r\n" +
				"        CASE\r\n" +
				"            WHEN REGION_GEOID.TYP_CD = 'REGION' THEN REGION_GEOID.NAME\r\n" +
				"        END\r\n" +
				"    AS REGION_NAME,\r\n" +
				"        CASE\r\n" +
				"            WHEN REGION_GEOID.TYP_CD = 'REGION' THEN REGION_GEOID.CODE\r\n" +
				"        END\r\n" +
				"    AS ISO_REGION_CODE,\r\n" +
				"    C_FCT_DFND_REL.GDA_ROWID GDA_ROWID,\r\n" +
				"    C_FCT_DFND_REL.VALID_FROM_DT FCT_DFND_REL_VALID_FROM_DT,\r\n" +
				"    C_FCT_DFND_REL.VALID_THRU_DT FCT_DFND_REL_VALID_THRU_DT,\r\n" +
				"    COMMERCIAL_FCT_INFO.COMM_FCT_TYPE_CD,\r\n" +
				"    COMMERCIAL_FCT_INFO.COMM_FCT_TYPE_NM,\r\n" +
				"    COMMERCIAL_FCT_INFO.COMM_FCT_BRAND_CD,\r\n" +
				"    COMMERCIAL_FCT_INFO.COMM_FCT_BRAND_NM,\r\n" +
				"    COMMERCIAL_FCT_INFO.COMM_FCT_FUNC_CD,\r\n" +
				"    COMMERCIAL_FCT_INFO.COMM_FCT_FUNC_CD,\r\n" +
				"    COMMERCIAL_FCT_INFO.COMM_FCT_FUNC_NM,\r\n" +
				"    COMMERCIAL_FCT_INFO.COMM_FCT_DIALING_CD,\r\n" +
				"    COMMERCIAL_FCT_INFO.COMM_FCT_DAILNG_CD_DESC,\r\n" +
				"    COMMERCIAL_FCT_INFO.TELECOM_NUM,\r\n" +
				"    COMMERCIAL_FCT_INFO.IMP_MAIL,\r\n" +
				"    COMMERCIAL_FCT_INFO.EXP_MAIL\r\n" +
				"FROM\r\n" +
				"    PKG_VIEW_COMM_FACT PKG_VIEW_COMM_FC\r\n" +
				"    LEFT OUTER JOIN MDM_INFM_SMDS.C_FCT_DFND_REL ON C_FCT_DFND_REL.FCT_ROWID = PKG_VIEW_COMM_FC.F_ROWID_OBJECT\r\n" +
				"    INNER JOIN GDA CITY_GEOID ON CITY_GEOID.MDM_ID = PKG_VIEW_COMM_FC.FA_CITY_ROWID\r\n" +
				"    INNER JOIN GDA COUNTRY_GEOID ON COUNTRY_GEOID.MDM_ID = PKG_VIEW_COMM_FC.FA_CTRY_ROWID\r\n" +
				"    LEFT OUTER JOIN REGION_GEOID REGION_GEOID ON REGION_GEOID.MDM_ID = PKG_VIEW_COMM_FC.FA_TRTY_ROWID\r\n" +
				"    LEFT OUTER JOIN COMMERCIAL_FCT_INFO ON COMMERCIAL_FCT_INFO.FCT_ROWID = PKG_VIEW_COMM_FC.F_ROWID_OBJECT\r\n" +
				"WHERE\r\n" +
				"        CITY_GEOID.TYP_CD IN ('SUBCITY','CITY')\r\n" +
				"    AND\r\n" +
				"        COUNTRY_GEOID.TYP_CD = 'COUNTRY'";
		return commFacilityQuery;
	}
	private String getRetrieveCustFacilityAllQuery() {
		LOGGER.info("Inside getRetrieveCustFacilityAllQuery");
		String custFacilityQuery="WITH PKG_VIEW_CUST_FACT AS (\r\n" +
				"    SELECT\r\n" +
				"        C_FCT_FACILITY.ROWID_OBJECT AS F_ROWID_OBJECT,\r\n" +
				"        C_FCT_FACILITY.CREATOR AS F_CREATOR,\r\n" +
				"        C_FCT_FACILITY.CREATE_DATE AS F_CREATE_DATE,\r\n" +
				"        C_FCT_FACILITY.UPDATED_BY AS F_UPDATED_BY,\r\n" +
				"        C_FCT_FACILITY.LAST_UPDATE_DATE AS F_LAST_UPDATE_DATE,\r\n" +
				"        C_FCT_FACILITY.DELETED_IND AS F_DELETED_IND,\r\n" +
				"        C_FCT_FACILITY.DELETED_BY AS F_DELETED_BY,\r\n" +
				"        C_FCT_FACILITY.DELETED_DATE AS F_DELETED_DATE,\r\n" +
				"        C_FCT_FACILITY.LAST_ROWID_SYSTEM AS F_LAST_ROWID_SYSTEM,\r\n" +
				"        C_FCT_FACILITY.HUB_STATE_IND AS F_HUB_STATE_IND,\r\n" +
				"        C_FCT_FACILITY.FACILITY_NAME AS F_FACILITY_NAME,\r\n" +
				"        C_FCT_FACILITY.EXT_OWNED AS F_EXT_OWNED,\r\n" +
				"        C_FCT_FACILITY.CLASS_CD AS F_CLASS_CD,\r\n" +
				"        C_FCT_FACILITY.STATUS_CD AS F_STATUS_CD,\r\n" +
				"        C_FCT_FACILITY.EXT_EXPOSED AS F_EXT_EXPOSED,\r\n" +
				"        C_FCT_FACILITY.DODAAC AS F_DODAAC,\r\n" +
				"        C_FCT_FACILITY.URL AS F_URL,\r\n" +
				"        C_FCT_ALT_CODES.ROWID_OBJECT AS FC_ROWID_OBJECT,\r\n" +
				"        C_FCT_ALT_CODES.HUB_STATE_IND AS FC_HUB_STATE_IND,\r\n" +
				"        C_FCT_ALT_CODES.TYP_TYPE_ROWID AS FC_TYP_TYPE_ROWID,\r\n" +
				"        C_FCT_ALT_CODES.CODE AS FC_CODE,\r\n" +
				"        C_FCT_ALT_CODES.FCT_ROWID AS FC_FCT_ROWID,\r\n" +
				"        C_FCT_ADDR_REL.ROWID_OBJECT AS FR_ROWID_OBJECT,\r\n" +
				"        C_FCT_ADDR_REL.HUB_STATE_IND AS FR_HUB_STATE_IND,\r\n" +
				"        C_FCT_ADDR_REL.FCT_ROWID AS FR_FCT_ROWID,\r\n" +
				"        C_FCT_ADDR_REL.ADDR_ROWID AS FR_ADDR_ROWID,\r\n" +
				"        C_FCT_ADDR_REL.VALID_FROM_DT AS FR_VALID_FROM_DT,\r\n" +
				"        C_FCT_ADDR_REL.VALID_THRU_DT AS FR_VALID_THRU_DT,\r\n" +
				"        C_CTM_PSTL_ADDR.ROWID_OBJECT AS FA_ROWID_OBJECT,\r\n" +
				"        C_CTM_PSTL_ADDR.CREATOR AS FA_CREATOR,\r\n" +
				"        C_CTM_PSTL_ADDR.CREATE_DATE AS FA_CREATE_DATE,\r\n" +
				"        C_CTM_PSTL_ADDR.UPDATED_BY AS FA_UPDATED_BY,\r\n" +
				"        C_CTM_PSTL_ADDR.LAST_UPDATE_DATE AS FA_LAST_UPDATE_DATE,\r\n" +
				"        C_CTM_PSTL_ADDR.HUB_STATE_IND AS FA_HUB_STATE_IND,\r\n" +
				"        C_CTM_PSTL_ADDR.PO_BOX AS FA_PO_BOX,\r\n" +
				"        C_CTM_PSTL_ADDR.STREET AS FA_STREET,\r\n" +
				"        C_CTM_PSTL_ADDR.HOUSE_NUM AS FA_HOUSE_NUM,\r\n" +
				"        C_CTM_PSTL_ADDR.ADDR_LN_2 AS FA_ADDR_LN_2,\r\n" +
				"        C_CTM_PSTL_ADDR.ADDR_LN_3 AS FA_ADDR_LN_3,\r\n" +
				"        C_CTM_PSTL_ADDR.DSTRCT AS FA_DSTRCT,\r\n" +
				"        C_CTM_PSTL_ADDR.CITY_ROWID AS FA_CITY_ROWID,\r\n" +
				"        C_CTM_PSTL_ADDR.PSTCD AS FA_PSTCD,\r\n" +
				"        C_CTM_PSTL_ADDR.TRTY_ROWID AS FA_TRTY_ROWID,\r\n" +
				"        C_CTM_PSTL_ADDR.CTRY_ROWID AS FA_CTRY_ROWID,\r\n" +
				"        C_CTM_PSTL_ADDR.TAX_JURN_CD AS FA_TAX_JURN_CD,\r\n" +
				"        C_CTM_PSTL_ADDR.CITY AS CITY,\r\n" +
				"        C_CTM_PSTL_ADDR.LAT_GEOSPTL AS FA_LAT_GEOSPTL,\r\n" +
				"        C_CTM_PSTL_ADDR.LNG_GEOSPTL AS FA_LNG_GEOSPTL,\r\n" +
				"        C_CTM_PSTL_ADDR.FCT_ROWID AS FA_FCT_ROWID,\r\n" +
				"        C_CTM_PSTL_ADDR.PSTCD_ROWID AS FA_PSTCD_ROWID,\r\n" +
				"        C_TYP_TYPE.CODE AS FT_ALT_CODE_TYPE_CD,\r\n" +
				"        C_TYP_TYPE.NAME AS FT_ALT_CODE_TYPE_NM\r\n" +
				"    FROM\r\n" +
				"        C_FCT_FACILITY C_FCT_FACILITY\r\n" +
				"        INNER JOIN C_FCT_ADDR_REL C_FCT_ADDR_REL ON C_FCT_FACILITY.ROWID_OBJECT = C_FCT_ADDR_REL.FCT_ROWID\r\n" +
				"        RIGHT OUTER JOIN C_FCT_ALT_CODES C_FCT_ALT_CODES ON C_FCT_FACILITY.ROWID_OBJECT = C_FCT_ALT_CODES.FCT_ROWID\r\n" +
				"        INNER JOIN C_CTM_PSTL_ADDR C_CTM_PSTL_ADDR ON C_CTM_PSTL_ADDR.ROWID_OBJECT = C_FCT_ADDR_REL.ADDR_ROWID\r\n" +
				"        INNER JOIN C_TYP_TYPE ON C_TYP_TYPE.ROWID_OBJECT = C_FCT_ALT_CODES.TYP_TYPE_ROWID\r\n" +
				"    WHERE\r\n" +
				"            C_FCT_ADDR_REL.HUB_STATE_IND = 1\r\n" +
				"        AND\r\n" +
				"            C_FCT_FACILITY.ROWID_OBJECT = (\r\n" +
				"                SELECT\r\n" +
				"                    FCT_ROWID\r\n" +
				"                FROM\r\n" +
				"                    C_FCT_ALT_CODES\r\n" +
				"                WHERE\r\n" +
				"                    C_FCT_ALT_CODES.CODE = :CODE\r\n" +
				"            )\r\n" +
				"        AND\r\n" +
				"            C_FCT_FACILITY.CLASS_CD = 'CUST'\r\n" +
				"),GDA AS (\r\n" +
				"    SELECT\r\n" +
				"        C_GDA_DFND_AREA.ROWID_OBJECT MDM_ID,\r\n" +
				"        C_GDA_DFND_AREA.NAME NAME,\r\n" +
				"        C_ALT_CODE.CODE GEOID,\r\n" +
				"        COUNTRY_CODE.CODE CODE,\r\n" +
				"            CASE\r\n" +
				"                WHEN C_GDA_DFND_AREA.TYP_TYPE_CD = 'GDA.COUNTRY'    THEN 'COUNTRY'\r\n" +
				"                WHEN C_GDA_DFND_AREA.TYP_TYPE_CD = 'GDA.CITY'       THEN 'CITY'\r\n" +
				"                WHEN c_gda_dfnd_area.typ_type_cd = 'GDA.CITY_SUBAREA'  THEN 'SUBCITY'\r\n" +
				"                WHEN C_GDA_DFND_AREA.TYP_TYPE_CD = 'GDA.STATE/PROV' THEN 'REGION'\r\n" +
				"            END\r\n" +
				"        AS TYP_CD\r\n" +
				"    FROM\r\n" +
				"        MDM_INFM_SMDS.C_GDA_DFND_AREA C_GDA_DFND_AREA\r\n" +
				"        INNER JOIN MDM_INFM_SMDS.C_ALT_CODE REGION ON REGION.GDA_DFND_AREA_ROWID = C_GDA_DFND_AREA.ROWID_OBJECT\r\n" +
				"        INNER JOIN MDM_INFM_SMDS.C_TYP_TYPE ON REGION.TYP_TYPE_ROWID = C_TYP_TYPE.ROWID_OBJECT\r\n" +
				"        INNER JOIN MDM_INFM_SMDS.C_ALT_CODE ON\r\n" +
				"            C_ALT_CODE.GDA_DFND_AREA_ROWID = C_GDA_DFND_AREA.ROWID_OBJECT\r\n" +
				"        AND\r\n" +
				"            C_ALT_CODE.TYP_TYPE_ROWID = C_TYP_TYPE.ROWID_OBJECT\r\n" +
				"        INNER JOIN MDM_INFM_SMDS.C_ALT_CODE COUNTRY_CODE ON COUNTRY_CODE.GDA_DFND_AREA_ROWID = C_GDA_DFND_AREA.ROWID_OBJECT\r\n" +
				"        INNER JOIN MDM_INFM_SMDS.C_TYP_TYPE ISO_CTRY_TYPE ON COUNTRY_CODE.TYP_TYPE_ROWID = ISO_CTRY_TYPE.ROWID_OBJECT\r\n" +
				"        INNER JOIN MDM_INFM_SMDS.C_ALT_CODE CITY ON\r\n" +
				"            CITY.GDA_DFND_AREA_ROWID = C_GDA_DFND_AREA.ROWID_OBJECT\r\n" +
				"        AND\r\n" +
				"            CITY.TYP_TYPE_ROWID = C_TYP_TYPE.ROWID_OBJECT\r\n" +
				"    WHERE\r\n" +
				"            C_GDA_DFND_AREA.HUB_STATE_IND = 1\r\n" +
				"        AND\r\n" +
				"            C_GDA_DFND_AREA.TYP_TYPE_CD IN (\r\n" +
				"                'GDA.COUNTRY','GDA.CITY','GDA.STATE/PROV','GDA.CITY_SUBAREA'\r\n" +
				"            )\r\n" +
				"        AND\r\n" +
				"            C_GDA_DFND_AREA.ACTIVE_FLAG = 'Y'\r\n" +
				"        AND\r\n" +
				"            C_TYP_TYPE.CODE = 'ALT_CODE.GEOID'\r\n" +
				"        AND\r\n" +
				"            ISO_CTRY_TYPE.CODE IN (\r\n" +
				"                'ALT_CODE.ISO_TRTY','ALT_CODE.RKST'\r\n" +
				"            )\r\n" +
				"        AND\r\n" +
				"            C_GDA_DFND_AREA.ROWID_OBJECT IN (\r\n" +
				"                SELECT\r\n" +
				"                    FA_CITY_ROWID AS ROWIDO\r\n" +
				"                FROM\r\n" +
				"                    PKG_VIEW_CUST_FACT\r\n" +
				"                UNION ALL\r\n" +
				"                SELECT\r\n" +
				"                    FA_CTRY_ROWID AS ROWIDO\r\n" +
				"                FROM\r\n" +
				"                    PKG_VIEW_CUST_FACT\r\n" +
				"                UNION ALL\r\n" +
				"                SELECT\r\n" +
				"                    FA_TRTY_ROWID AS ROWIDO\r\n" +
				"                FROM\r\n" +
				"                    PKG_VIEW_CUST_FACT\r\n" +
				"            )\r\n" +
				"),REGION_GEOID AS (\r\n" +
				"    SELECT\r\n" +
				"        C_GDA_DFND_AREA.MDM_ID MDM_ID,\r\n" +
				"        C_GDA_DFND_AREA.NAME NAME,\r\n" +
				"        C_GDA_DFND_AREA.GEOID GEOID,\r\n" +
				"        C_GDA_DFND_AREA.CODE CODE,\r\n" +
				"        C_GDA_DFND_AREA.TYP_CD TYP_CD\r\n" +
				"    FROM\r\n" +
				"        GDA C_GDA_DFND_AREA\r\n" +
				"        INNER JOIN PKG_VIEW_CUST_FACT ON (\r\n" +
				"            NVL(\r\n" +
				"                PKG_VIEW_CUST_FACT.FA_TRTY_ROWID,\r\n" +
				"                'XX'\r\n" +
				"            ) = NVL(\r\n" +
				"                C_GDA_DFND_AREA.MDM_ID,\r\n" +
				"                'XX'\r\n" +
				"            )\r\n" +
				"        ) AND\r\n" +
				"            C_GDA_DFND_AREA.TYP_CD = 'REGION'\r\n" +
				") SELECT DISTINCT /*+ NO_CPU_COSTING */\r\n" +
				"    PKG_VIEW_CUST_FC.F_ROWID_OBJECT FCT_MDM_ID,\r\n" +
				"    PKG_VIEW_CUST_FC.F_CLASS_CD FCT_CATEGORY,\r\n" +
				"    PKG_VIEW_CUST_FC.F_FACILITY_NAME FCT_NAME,\r\n" +
				"    PKG_VIEW_CUST_FC.F_HUB_STATE_IND FCT_HUB_STATE,\r\n" +
				"    PKG_VIEW_CUST_FC.F_STATUS_CD FCT_STATUS,\r\n" +
				"    PKG_VIEW_CUST_FC.F_CREATE_DATE FCT_CREATE_DT,\r\n" +
				"    PKG_VIEW_CUST_FC.F_UPDATED_BY FCT_LAST_UPDATED_BY,\r\n" +
				"    PKG_VIEW_CUST_FC.F_CREATOR FCT_CREATOR,\r\n" +
				"    PKG_VIEW_CUST_FC.F_DELETED_BY FCT_DELETED_BY,\r\n" +
				"    PKG_VIEW_CUST_FC.F_LAST_UPDATE_DATE FCT_LAST_UPDATE_DT,\r\n" +
				"    PKG_VIEW_CUST_FC.F_LAST_ROWID_SYSTEM LAST_ROWID_SYSTEM,\r\n" +
				"    PKG_VIEW_CUST_FC.F_DELETED_DATE FCT_DELETED_DT,\r\n" +
				"    PKG_VIEW_CUST_FC.F_EXT_OWNED EXT_OWNED_FLAG,\r\n" +
				"    PKG_VIEW_CUST_FC.F_EXT_EXPOSED EXT_EXPOSED_FLAG,\r\n" +
				"    PKG_VIEW_CUST_FC.F_URL FCT_URL,\r\n" +
				"    PKG_VIEW_CUST_FC.F_DODAAC DODAAC,\r\n" +
				"    PKG_VIEW_CUST_FC.FC_HUB_STATE_IND FCT_ALT_CODE_HUB_STATE,\r\n" +
				"    PKG_VIEW_CUST_FC.FC_TYP_TYPE_ROWID FCT_ALT_CD_TYP_ROWID,\r\n" +
				"    PKG_VIEW_CUST_FC.FT_ALT_CODE_TYPE_CD ALT_CODE_TYPE_CD,\r\n" +
				"    PKG_VIEW_CUST_FC.FT_ALT_CODE_TYPE_NM ALT_CODE_TYPE_NM,\r\n" +
				"    PKG_VIEW_CUST_FC.FC_CODE FCT_ALT_CODE,\r\n" +
				"    PKG_VIEW_CUST_FC.FR_HUB_STATE_IND FCT_ADDR_REL_HUB_STATE,\r\n" +
				"    PKG_VIEW_CUST_FC.FR_ADDR_ROWID ADDR_MDM_ID,\r\n" +
				"    PKG_VIEW_CUST_FC.FR_VALID_FROM_DT FCT_ADDR_VALID_FROM_DT,\r\n" +
				"    PKG_VIEW_CUST_FC.FR_VALID_THRU_DT FCT_ADDR_VALID_THRU_DT,\r\n" +
				"    PKG_VIEW_CUST_FC.FA_HUB_STATE_IND ADDR_HUB_STATE,\r\n" +
				"    PKG_VIEW_CUST_FC.FA_LAST_UPDATE_DATE ADDR_LAST_UPDATE_DT,\r\n" +
				"    PKG_VIEW_CUST_FC.FA_UPDATED_BY ADDR_UPDATED_BY,\r\n" +
				"    PKG_VIEW_CUST_FC.FA_CREATE_DATE ADDR_CREATE_DT,\r\n" +
				"    PKG_VIEW_CUST_FC.FA_CREATOR ADDR_CREATOR,\r\n" +
				"    PKG_VIEW_CUST_FC.FA_HOUSE_NUM HOUSE_NUM,\r\n" +
				"    PKG_VIEW_CUST_FC.FA_DSTRCT DISTRICT,\r\n" +
				"    PKG_VIEW_CUST_FC.FA_PSTCD POSTAL_CODE,\r\n" +
				"    PKG_VIEW_CUST_FC.FA_TAX_JURN_CD TAX_JURN_CD,\r\n" +
				"    PKG_VIEW_CUST_FC.FA_PO_BOX PO_BOX,\r\n" +
				"    PKG_VIEW_CUST_FC.FA_STREET STREET,\r\n" +
				"    PKG_VIEW_CUST_FC.FA_ADDR_LN_2 BUILDING_NUM,\r\n" +
				"    PKG_VIEW_CUST_FC.FA_ADDR_LN_3 SUBURB,\r\n" +
				"        CASE\r\n" +
				"            WHEN CITY_GEOID.TYP_CD IN ('SUBCITY','CITY')  THEN CITY_GEOID.MDM_ID\r\n" +
				"        END\r\n" +
				"    AS CITY_MDM_ID,\r\n" +
				"        CASE\r\n" +
				"            WHEN CITY_GEOID.TYP_CD IN ('SUBCITY','CITY') THEN CITY_GEOID.GEOID\r\n" +
				"        END\r\n" +
				"    AS CITY_GEOID,\r\n" +
				"        CASE\r\n" +
				"            WHEN CITY_GEOID.TYP_CD IN ('SUBCITY','CITY') THEN CITY_GEOID.NAME\r\n" +
				"        END\r\n" +
				"    AS CITY,\r\n" +
				"    PKG_VIEW_CUST_FC.FA_LAT_GEOSPTL LAT_GEOSPTL,\r\n" +
				"    PKG_VIEW_CUST_FC.FA_LNG_GEOSPTL LNG_GEOSPTL,\r\n" +
				"        CASE\r\n" +
				"            WHEN COUNTRY_GEOID.TYP_CD = 'COUNTRY' THEN COUNTRY_GEOID.MDM_ID\r\n" +
				"        END\r\n" +
				"    AS CTRY_MDM_ID,\r\n" +
				"        CASE\r\n" +
				"            WHEN COUNTRY_GEOID.TYP_CD = 'COUNTRY' THEN COUNTRY_GEOID.GEOID\r\n" +
				"        END\r\n" +
				"    AS COUNTRY_GEOID,\r\n" +
				"        CASE\r\n" +
				"            WHEN COUNTRY_GEOID.TYP_CD = 'COUNTRY' THEN COUNTRY_GEOID.NAME\r\n" +
				"        END\r\n" +
				"    AS COUNTRY_NAME,\r\n" +
				"        CASE\r\n" +
				"            WHEN COUNTRY_GEOID.TYP_CD = 'COUNTRY' THEN COUNTRY_GEOID.CODE\r\n" +
				"        END\r\n" +
				"    AS ISO_COUNTRY_CODE,\r\n" +
				"        CASE\r\n" +
				"            WHEN REGION_GEOID.TYP_CD = 'REGION' THEN REGION_GEOID.MDM_ID\r\n" +
				"        END\r\n" +
				"    AS REGION_MDM_ID,\r\n" +
				"        CASE\r\n" +
				"            WHEN REGION_GEOID.TYP_CD = 'REGION' THEN REGION_GEOID.GEOID\r\n" +
				"        END\r\n" +
				"    AS REGION_GEOID,\r\n" +
				"        CASE\r\n" +
				"            WHEN REGION_GEOID.TYP_CD = 'REGION' THEN REGION_GEOID.NAME\r\n" +
				"        END\r\n" +
				"    AS REGION_NAME,\r\n" +
				"        CASE\r\n" +
				"            WHEN REGION_GEOID.TYP_CD = 'REGION' THEN REGION_GEOID.CODE\r\n" +
				"        END\r\n" +
				"    AS ISO_REGION_CODE,\r\n" +
				"    C_FCT_DFND_REL.GDA_ROWID GDA_ROWID,\r\n" +
				"    C_FCT_DFND_REL.VALID_FROM_DT FCT_DFND_REL_VALID_FROM_DT,\r\n" +
				"    C_FCT_DFND_REL.VALID_THRU_DT FCT_DFND_REL_VALID_THRU_DT\r\n" +
				"FROM\r\n" +
				"    PKG_VIEW_CUST_FACT PKG_VIEW_CUST_FC\r\n" +
				"    LEFT OUTER JOIN MDM_INFM_SMDS.C_FCT_DFND_REL ON C_FCT_DFND_REL.FCT_ROWID = PKG_VIEW_CUST_FC.F_ROWID_OBJECT\r\n" +
				"    INNER JOIN GDA CITY_GEOID ON CITY_GEOID.MDM_ID = PKG_VIEW_CUST_FC.FA_CITY_ROWID\r\n" +
				"    INNER JOIN GDA COUNTRY_GEOID ON COUNTRY_GEOID.MDM_ID = PKG_VIEW_CUST_FC.FA_CTRY_ROWID\r\n" +
				"    LEFT OUTER JOIN REGION_GEOID REGION_GEOID ON REGION_GEOID.MDM_ID = PKG_VIEW_CUST_FC.FA_TRTY_ROWID\r\n" +
				"WHERE\r\n" +
				"        CITY_GEOID.TYP_CD IN ('SUBCITY','CITY')\r\n" +
				"    AND\r\n" +
				"        COUNTRY_GEOID.TYP_CD = 'COUNTRY'";
		return custFacilityQuery;
	}



	private String getCategoryClassCode() {
		String factClassCode="SELECT CLASS_CD FROM C_FCT_FACILITY WHERE "
				+ "ROWID_OBJECT IN(SELECT FCT_ROWID FROM C_FCT_ALT_CODES WHERE CODE=:CODE)";
		return factClassCode;
	}
	private String getFactClassCodeValue(Connection con, String facilityId, String classCodeQuery)  throws SQLException  {
		PreparedStatement pstmt = null ;
		ResultSet rs = null ;
		NamedParameterStatement p = null;
		List<FacilityTypeVO> facilityTypes = null;
		String classCodeValue="";
		try {
			p = new NamedParameterStatement(con, classCodeQuery);
			LOGGER.info("getFactClassCodeValue Query Parameter :--> " + classCodeQuery);
			if (facilityId != null) {
				p.setString("CODE", facilityId);
			}
			System.out.println("1st print "+p.getStatement());
			System.out.println("2nd print "+p.toString());
			rs = p.executeQuery();
			while(rs.next()) {
				classCodeValue=rs.getString("CLASS_CD");
				System.out.println("CLASS_CD IS "+classCodeValue);
				LOGGER.info("CLASS_CD IS "+classCodeValue);
				return classCodeValue;
			}
		}catch (SQLException e) {
			LOGGER.fatal(e.getLocalizedMessage());
			throw e;
		} finally {
			if (pstmt != null) {
				pstmt.close();
			}
			if (rs != null) {
				rs.close();
			}

		}
		return classCodeValue;
	}

}
