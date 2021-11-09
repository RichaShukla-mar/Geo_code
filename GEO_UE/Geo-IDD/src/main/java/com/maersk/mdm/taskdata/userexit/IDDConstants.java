package com.maersk.mdm.taskdata.userexit;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public final class IDDConstants {

    final public static String IDD_AREANAME_ALT_CODE = "AlternateCode";

    final public static String IDD_AREANAME_ALT_NAME = "AlternateName";

    final public static String IDD_AREANAME_HIERARCHY = "Hierarchy";
    
    final public static String IDD_AREANAME_BDA_TYPE = "BDAType";
    
    final public static String  IDD_AREANAME_BDA_PARENT ="BDAParent";
    
    final public static String  IDD_AREANAME_GDA_BDA ="BDA";
    
    final public static String  IDD_AREANAME_BDA_CHILD ="BDAChild";

    final public static String IDD_AREANAME_SITE_PHYSICAL_LOC = "SitePhysicalLocation";

    // -------------------------------------------------------------------

    final public static String MDM_TABLE_DEFAREA_COLUMN_NAME_ROWID_OBJECT = "C_GDA_DFND_AREA|ROWID_OBJECT";

    final public static String MDM_TABLE_DEFAREA_COLUMN_NAME_TIMEZONE = "C_GDA_DFND_AREA|TDS_TMZ_ROWID";

    final public static String MDM_TABLE_DEFAREA_COLUMN_NAME_NAME = "C_GDA_DFND_AREA|NAME";

    final public static String MDM_TABLE_DEFAREA_COLUMN_NAME_TYPE_CD = "C_GDA_DFND_AREA|TYP_TYPE_CD";

    final public static String MDM_TABLE_DEFAREAREL_COLUMN_NAME_VALID_FROM = "C_GDA_DFND_AREA_REL|VALID_FROM_DT";

    final public static String MDM_TABLE_DEFAREAREL_COLUMN_NAME_VALID_TO = "C_GDA_DFND_AREA_REL|VALID_THRU_DT";

    final public static String MDM_TABLE_DEFAREA_COLUMN_NAME_VALID_FROM = "C_GDA_DFND_AREA|VALID_FROM_DT";

    final public static String MDM_TABLE_DEFAREA_COLUMN_NAME_VALID_TO = "C_GDA_DFND_AREA|VALID_THRU_DT";

    final public static String MDM_TABLE_DEFAREA_COLUMN_NAME_STATUS = "C_GDA_DFND_AREA|ACTIVE_FLAG";

    final public static String MDM_TABLE_ALTCODE_COLUMN_NAME_ROWID_OBJECT = "C_ALT_CODE|ROWID_OBJECT";

    final public static String MDM_TABLE_ALTCODE_COLUMN_NAME_TYPEID = "C_ALT_CODE|TYP_TYPE_ROWID";
    
    final public static String MDM_TABLE_ALTCODE_COLUMN_NAME_VALUE = "C_ALT_CODE|CODE";

    final public static String MDM_TABLE_ALTNAME_COLUMN_NAME_NAME = "C_ALT_NAME|NAME";

    final public static String MDM_TABLE_SITE_COLUMN_NAME_POSTAL_CODE = "C_TMP_SITE_PHYS_LCN|PSTL_CD";

    final public static String MDM_TABLE_TYPES_COLUMN_NAME_CODE = "CODE";

    final public static String MDM_COLUMN_NAME_ROWID_OBJECT = "ROWID_OBJECT";
    
    final public static String MDM_TABLE_DEFAREA_COLUMN_NAME_BDATYPE = "C_GDA_BDA|BDA_TYPE_CD";

    
    //Added Below For Geo Changes
    
    final public static String MDM_TABLE_DEFAREA_COLUMN_NAME_DESCRIPTION = "C_GDA_DFND_AREA|DESCRIPTION";
    final public static String MDM_TABLE_C_TMP_WRKRND_RSN_COLUMN_NAME_WR = "C_TMP_WRKRND_RSN|TYP_TYPE_ROWID";
    final public static String MDM_TABLE_ALTNAME_COLUMN_NAME_DESCRIPTION = "C_ALT_NAME|DESCRIPTION";
    final public static String MDM_TABLE_ALTNAME_COLUMN_NAME_STATUS = "C_ALT_NAME|ACTIVE_FLAG";
    final public static String MDM_TABLE_DEFAREA_COLUMN_NAME_DST = "C_GDA_DFND_AREA|TDS_DST_ROWID";
    final public static String MDM_TABLE_GDACOUNTRY_COLUMN_NAME_POSTAL_CD_MND = "C_GDA_COUNTRY|PSTL_CD_MNDTRY_FLAG";
    final public static String MDM_TABLE_GDACOUNTRY_COLUMN_CNTRY_RSTRCTD_FLAG = "C_GDA_COUNTRY|CNTRY_RSTRCTD_FLAG";
    final public static String MDM_TABLE_GDACOUNTRY_COLUMN_CNTRY_STATE_MND_FLAG = "C_GDA_COUNTRY|STATE_MNDTRY_FLAG";
    final public static String MDM_TABLE_GDACOUNTRY_COLUMN_CNTRY_DL_CD = "C_CTM_INTL_DIALNG_CD|DIALNG_CD";
    final public static String MDM_TABLE_GDACOUNTRY_COLUMN_CNTRY__DL_CD_DESC = "C_CTM_INTL_DIALNG_CD|DAILNG_CD_DESC";
    final public static String MDM_TABLE_DEFAREA_COLUMN_NAME_LATITUDE = "C_GDA_DFND_AREA|LAT_GEOSPTL";
    final public static String MDM_TABLE_DEFAREA_COLUMN_NAME_LONGITUDE = "C_GDA_DFND_AREA|LNG_GEOSPTL";
    final public static String MDM_TABLE_GDACITY_COLUMN_NAME_PORT_FLAG = "C_GDA_CITY|PORT_FLAG";
    final public static String MDM_TABLE_GDACITY_COLUMN_NAME_MAERSK_CITY = "C_GDA_CITY|MAERSK_CITY";
    final public static String MDM_TABLE_GDACITY_COLUMN_NAME_OLSONTZ = "C_GDA_CITY|OLSON_TZ";
    final public static String MDM_TABLE_SITE_COLUMN_NAME_GPS_FLAG = "C_GDA_SITE|GPS_FLAG";
    final public static String MDM_TABLE_SITE_COLUMN_NAME_GSM_FLAG = "C_GDA_SITE|GSM_FLAG";
    final public static String MDM_TABLE_DFND_PMTR_COLUMN_NAME_NAME = "C_GDA_DFND_PMTR|NAME";
    final public static String MDM_TABLE_DFND_PMTR_COLUMN_NAME_TYP_CD = "C_GDA_DFND_PMTR|PMTR_TYP_CD";
    final public static String MDM_TABLE_TMP_SITE_PHYS_LCN_COLUMN_NAME_STREETNO = "C_TMP_SITE_PHYS_LCN|STREET_NO";
    final public static String MDM_TABLE_SITE_COLUMN_NAME_ADDR_LN2 = "C_TMP_SITE_PHYS_LCN|ADDR_LN_2";
    final public static String MDM_TABLE_SITE_COLUMN_NAME_ADDR_LN3 = "C_TMP_SITE_PHYS_LCN|ADDR_LN_3";
    // -------------------------------------------------------------------

    final public static String MDM_PACKAGE_NAME_TYPE_TABLE = "PKG_TYPE";
    
    //-------------------------------------------------------------------

    //CONSTANTS FOR BDA USER EXIT
    
    final public static String MDM_PACKAGE_BDA_TYPE_CODE ="PKG_GEOGRAPHY_BDA_TYPE_CODE";
    
    final public static String MDM_PACKAGE_COLUMN_NAME_BDA_TYPE="MATCH_PATH_COMPONENT.C_MT_GDA_BDA|BDA_TYPE_CD";
    
    final public static String MDM_PACKAGE_COLUMN_NAME_BDA_CODE ="MATCH_PATH_COMPONENT.C_MT_ALT_CODE|CODE";

    final public static String MDM_PACKAGE_COLUMN_NAME_ROWID_OBJECT= "COLUMN.C_GDA_DFND_AREA|ROWID_OBJECT";
    // -------------------------------------------------------------------

    final public static String MDM_PACKAGE_NAME_DEFINED_AREA_PARENT = "PKG_DEFINED_AREA_PARENT";
    
    final public static String MDM_PACKAGE_NAME_BDA_AS_PARENT = "PKG_BDA_AS_PARENT";

    final public static String MDM_PACKAGE_COLUMN_NAME_TYPE = "TYPE";

    final public static String MDM_PACKAGE_COLUMN_NAME_NAME = "NAME";

    final public static String MDM_PACKAGE_COLUMN_NAME_PARENT_TYPE = "PARENT_TYPE";

    final public static String MDM_PACKAGE_COLUMN_NAME_GR_PARENT_TYPE = "GR_PARENT_TYPE";

    final public static String MDM_PACKAGE_COLUMN_NAME_PARENT_ROWID = "PARENT_ROWID";

    final public static String MDM_PACKAGE_COLUMN_NAME_VALID_TO = "VALID_TO";

    final public static String MDM_PACKAGE_COLUMN_NAME_GR_PARENT_ROWID = "GR_PARENT_ROWID";

    // -------------------------------------------------------------------

    final public static String MDM_PACKAGE_NAME_ALT_CODE = "PKG_ALT_CODE";

    final public static String MDM_PACKAGE_COLUMN_NAME_DEF_AREA_ROWID = "DEF_AREA_ROWID";

    final public static String MDM_PACKAGE_COLUMN_NAME_TYP_TYPE_ROWID = "TYP_TYPE_ROWID";

    final public static String MDM_PACKAGE_COLUMN_NAME_CODE = "CODE";
    
   /* final public static String MDM_PACKAGE_COLUMN_NAME_HUB_STATE_IND = "HUB_STATE_IND";*/

    
    // -------------------------------------------------------------------
    
    final public static String MDM_PACKAGE_NAME_GDA_ACTIVE_ALT_CODE = "PKG_ACTIVE_GDA_ALT_CODE";

    final public static String MDM_PACKAGE_COLUMN_NAME_DEFINED_AREA_ROWID = "MATCH_PATH_COMPONENT.C_MT_ALT_CODE|GDA_DFND_AREA_ROWID";

    final public static String MDM_PACKAGE_COLUMN_NAME_CODE_TYP_TYPE_ROWID = "MATCH_PATH_COMPONENT.C_MT_ALT_CODE|TYP_TYPE_ROWID";

    final public static String MDM_PACKAGE_COLUMN_NAME_CODE_VALUE = "MATCH_PATH_COMPONENT.C_MT_ALT_CODE|CODE";
    // -------------------------------------------------------------------
    
    final public static String MDM_PACKAGE_NAME_DEF_AREA_ALT_CODE = "PKG_DEFINED_AREA_ALT_CODE";

    // -------------------------------------------------------------------

    final public static String MDM_CL_FUNC_NAME_VALIDATE = "Geography Cleanse And Validation Library|ValidateAlternateNameCodeAndTimeZone";

    final public static String MDM_CL_FUNC_VALIDATE_FIELD_PARENT_ID = "DefinedAreaParentRowId";

    final public static String MDM_CL_FUNC_VALIDATE_FIELD_AREA_ID = "DefinedAreaRowId";

    final public static String MDM_CL_FUNC_VALIDATE_FIELD_AREA_NAME = "DefinedAreaName";

    final public static String MDM_CL_FUNC_VALIDATE_FIELD_AREA_TYPE = "DefinedAreaType";

    final public static String MDM_CL_FUNC_VALIDATE_FIELD_TIMEZONE = "StandardTimeZoneId";

    final public static String MDM_CL_FUNC_VALIDATE_FIELD_ALT_NAMES = "EncodedAlternateName";

    final public static String MDM_CL_FUNC_VALIDATE_FIELD_ALT_CODES = "EncodedAlternateCode";

    final public static String MDM_CL_FUNC_VALIDATE_FIELD_VALIDATION_STATUS = "validationStatus";

    // -------------------------------------------------------------------

    final public static String MDM_CL_FUNC_NAME_GEOID = "Generic Java Cleanse And Validation Library|GenerateGeoId";

    final public static String MDM_CL_FUNC_GEOID_FIELD_VALIDATION_STATUS = "validationStatus";

    final public static String MDM_CL_FUNC_GEOID_FIELD_GENERATED_ID = "generatedID";

    // -------------------------------------------------------------------

    final public static String CLEANSE_ENCODE_SEPARATOR = "~";

    // -------------------------------------------------------------------

    // Constant of GEO ID code type value. Correspond to value from C_TYP_TYPE table
    final public static String DATA_TABLE_ALTCODE_GEOID = "ALT_CODE.GEOID";

    final public static String DATA_TABLE_ALTCODE_RKST = "ALT_CODE.RKST";

    final public static String DATA_TABLE_ALTCODE_RKTS = "ALT_CODE.RKTS";

    final public static String DATA_TABLE_ALTCODE_MODEL = "ALT_CODE.MODEL";

    final public static String DATA_TABLE_ALTCODE_STATE = "ALT_CODE.STATE";

    final public static String DATA_TABLE_ALTCODE_PROVINCE = "ALT_CODE.PROVINCE";

    final public static String DATA_TABLE_ALTCODE_POSTCODE = "ALT_CODE.POSTCODE";
    final public static String DATA_TABLE_ALTCODE_CONT_CODE = "ALT_CODE.CONT_CODE";
    
    // tpr 723
    final public static String DATA_TABLE_ALTCODE_CONTINENT_CODE = "ALT_CODE.CONT_CODE";
    //CONSTANTS FOR BDA USER EXIT
    final public static String DATA_TABLE_ALTCODE_BDACODE = "ALT_CODE.BDACODE";
    
   
    // -------------------------------------------------------------------

    // Constant of "Active" value within ACTIVE_FLAG column of DEFINED AREA table 
    final public static String DATA_TABLE_DEFAREA_ACTIVE_FLAG_YES_VALUE = "Y";    
    final public static String DATA_TABLE_DEFAREA_ACTIVE_FLAG_NO_VALUE = "N";

    // Constants for defined area type enumeration. Correspond to subject area names in IDD
    final public static String DATA_DEFAREA_TYPE_CONTINENT = "Continent";

    final public static String DATA_DEFAREA_TYPE_COUNTRY = "Country";

    final public static String DATA_DEFAREA_TYPE_PROVINCE = "StateProv";

    final public static String DATA_DEFAREA_TYPE_COUNTY = "County";

    final public static String DATA_DEFAREA_TYPE_CITY = "City";

    final public static String DATA_DEFAREA_TYPE_SITE = "Site";

    final public static String DATA_DEFAREA_TYPE_POSTALCODE = "PostalCode";
    
    // CONSTANTS FOR BDA USER EXIT
    final public static String DATA_DEFAREA_TYPE_BDA = "BusinessDefinedArea";

    // Constants for defined area type enumeration. Correspond to C_TYP_TYPE table
    final public static String DATA_TABLE_TYP_TYPE_CONTINENT = "GDA.CONTINENT";

    final public static String DATA_TABLE_TYP_TYPE_COUNTRY = "GDA.COUNTRY";

    final public static String DATA_TABLE_TYP_TYPE_PROVINCE = "GDA.STATE/PROV";

    final public static String DATA_TABLE_TYP_TYPE_COUNTY = "GDA.TBD";
    
    //CONSTANTS FOR BDA USER EXIT
    final public static String DATA_TABLE_TYP_TYPE_BDA = "GDA.BDA";

    final public static String DATA_TABLE_TYP_TYPE_CITY = "GDA.CITY";

    final public static String DATA_TABLE_TYP_TYPE_SITE = "GDA.SITE";

    final public static String DATA_TABLE_TYP_TYPE_POSTALCODE = "GDA.POSTAL_CODE";

    // -------------------------------------------------------------------
    
    final public static String EXT_RESOURCE_ROWID_GEOID_TYPE = "maersk_geo_rowid_geoid_type";

    final public static String EXT_RESOURCE_ROWID_RKST_TYPE = "maersk_geo_rowid_RKST_type";

    final public static String EXT_RESOURCE_ROWID_RKTS_TYPE = "maersk_geo_rowid_RKTS_type";

    final public static String EXT_RESOURCE_ROWID_MODEL_TYPE = "maersk_geo_rowid_MODEL_type";

    final public static String EXT_RESOURCE_ROWID_STATE_TYPE = "maersk_geo_rowid_STATE_type";

    final public static String EXT_RESOURCE_ROWID_PROVINCE_TYPE = "maersk_geo_rowid_PROVINCE_type";

    final public static String EXT_RESOURCE_ROWID_POSTCODE = "maersk_geo_rowid_POSTCODE_type";
    
    /****************** City SubArea & Pool BDA City Change Starts- Anil***********************/
    
    final public static String DATA_DEFAREA_TYPE_SUBCITY = "CitySubArea";

    final public static String DATA_TABLE_TYP_TYPE_SUBCITY = "GDA.CITY_SUBAREA";
    
    final public static String EXT_RESOURCE_ROWID_IATA_CODE = "maersk_geo_rowid_IATA_type";
    
    final public static String DATA_TABLE_ALTCODE_IATACODE = "ALT_CODE.IATA";

    /****************** City SubArea & Pool BDA City Change Ends- Anil***********************/
    
    /****************** HSUD & LNS Code Change Start- Richa***********************/
    final public static String EXT_RESOURCE_ROWID_HSUD_CODE = "maersk_geo_rowid_HSUD_type";
    
    final public static String DATA_TABLE_ALTCODE_HSUDCODE = "ALT_CODE.HSUD_CODE";
    
    final public static String EXT_RESOURCE_ROWID_LNS_CODE = "maersk_geo_rowid_LNS_type";
    
    final public static String DATA_TABLE_ALTCODE_LNSCODE = "ALT_CODE.LNS_CODE";
    
    final public static String EXT_RESOURCE_ROWID_HSUDNUM_CODE  = "maersk_geo_rowid_HSUDNUM_type";
    
    final public static String DATA_TABLE_ALTCODE_HSUDNUM = "ALT_CODE.HSUD_NUM";
    
    /****************** HSUD & LNS Code Change Ends- Richa***********************/
    
    //CONSTANTS FOR BDA USER EXIT 
    final public static String EXT_RESOURCE_ROWID_BDACODE = "maersk_geo_rowid_BDACODE_type";

    // -------------------------------------------------------------------

    final public static String DATA_CITY_MODEl_CODE_RESERVED_VALUE = "DKCPH";

    final public static String DATA_SITE_MODEl_CODE_RESERVED_VALUE = "DKCPHTM";

    // -------------------------------------------------------------------

    final public static DateFormat DATE_FORMAT = new SimpleDateFormat("yyyy MM dd");

    final public static Date END_OF_DAYS = new Date(9999,12,31);

    final public static int NUMBER_OF_VIOLATED_CHILDREN_TO_RETURN = 5;
    //CONSTANTS ADDED FOR DUPLICATE VALIDATION
    final public static String MDM_PACKAGE_STATE_PROV ="PKG_STATE_PROV";
    final public static String MDM_PACKAGE_SITE ="PKG_SITE";
    final public static String MDM_PACKAGE_CITY ="PKG_CITY";
    final public static String MDM_PACKAGE_COLUMN_NAME_PARENT_NAME = "PARENT_NAME";
    //Added for geography mandatory validation #28MAY2013
    final public static String DATA_TABLE_ALTCODE_ISO_TRTRY = "ALT_CODE.ISO_TRTY";
    //CONSTANTS ADDED FOR DATA VALIDATION
    final public static String MDM_TABLE_SITE_COLUMN_NAME_ADDR_LN1 = "C_TMP_SITE_PHYS_LCN|ADDR_LN_1";
    //CONSTANT SUBJECT AREA FOR SITE TYPE VALIDATION 
    final public static String IDD_AREANAME_SITE_TYPE = "SiteType";
    final public static String MDM_TABLE_SITE_COLUMN_NAME_SITE_TYPE = "C_GDA_SITE|TYP_TYPE_ROWID";
    final public static String DATA_TABLE_TYP_SITE_TYPE = "SITE_TYPE.TBU";
    //CONSTANTS FOR POSTAL CODE MANDATORY CHECK
    final public static String MDM_PACKAGE_GDA_COUNTRY ="PKG_GDA_COUNTRY";
    final public static String MDM_PACKAGE_POSTAL_CODE ="PKG_POSTAL_CODE";
    final public static String MDM_PACKAGE_PKG_DEFINED_AREA ="PKG_DEFINED_AREA";
    final public static String MDM_PACKAGE_COLUMN_NAME_COUNTRY_ID = "COUNTRY_ID";
    final public static String MDM_PACKAGE_COLUMN_NAME_AREA_TYPE_CODE = "AREA_TYPE_CODE";
    final public static String MDM_PACKAGE_COLUMN_NAME_PSTL_CD_MNDTRY_FLG = "PSTL_CD_MNDTRY_FLAG";
}
