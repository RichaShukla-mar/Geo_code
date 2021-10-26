package net.apmoller.maersk.cmd.geo.util;

public class GEOConstants {

	public static final String GEO_ENTITY_POSTAL_CODE = "POSTALCODE";
	public static final String GEO_ENTITY_CITY = "CITY";
	public static final String GEO_ENTITY_CITY_BDA = "BDA";
	public static final String GEO_ENTITY_CITY_BDA_DELETE = "D";
	public static final String GEO_ENTITY_CITY_BDA_INSERT = "I";
	public static final String GEO_ENTITY_CITY_BDA_UPDATE = "U";


	public static final String GEO_FIELD_POSTALNAME = "GEO_FIELD_POSTALNAME";
	public static final String GEO_FIELD_POSTALCODE = "GEO_FIELD_POSTALCODE";
	public static final String GEO_FIELD_VALIDFROM = "GEO_FIELD_VALIDFROM";
	public static final String GEO_FIELD_VALIDTHRU = "GEO_FIELD_VALIDTHRU";
	public static final String GEO_FIELD_PARENTROWID = "GEO_FIELD_PARENTROWID";
	public static final String GEO_FIELD_GDAROWID = "GEO_FIELD_GDAROWID";
	public static final String GEO_FIELD_DESCRIPTION = "GEO_FIELD_DESCRIPTION";
	public static final String GEO_FIELD_ENTITYTYPE = "GEO_FIELD_ENTITYTYPE";
	public static final String GEO_FIELD_TYPTYPECD = "GEO_FIELD_TYPTYPECD";
	public static final String GEO_INSERT_OPERATION = "I";
	public static final String GEO_UPDATE_OPERATION = "U";

	//City MU Constants
	public static final String GEO_FIELD_CITY_NAME = "CITY_NAME";
	public static final String GEO_FIELD_CITY_DESCRIPTION = "DESCRIPTION";
	public static final String GEO_FIELD_MAERSK_CITY = "MAERSK_CITY";
	public static final String GEO_FIELD_HSUD_NAME = "HSUD_NAME";
	public static final String GEO_FIELD_CITYGDA_VALID_FROM = "GDA_VALID_FROM";
	public static final String GEO_FIELD_CITYGDA_VALID_TO = "GDA_VALID_TO";
	public static final String GEO_FIELD_CITY_LATITUDE = "LATITUDE";
	public static final String GEO_FIELD_CITY_LONGITUDE = "LONGITUDE";
	public static final String GEO_FIELD_CITY_STATUS = "STATUS";
	public static final String GEO_FIELD_CITY_TIMEZONE = "TIMEZONE";
	public static final String GEO_FIELD_CITY_DAYLIGHTSAVING = "DAYLIGHTSAVING";
	public static final String GEO_FIELD_CITY_PORT_FLAG = "PORT_FLAG";
	public static final String GEO_FIELD_CITY_WORKRND_REASON = "WORKRND_REASON";
	public static final String GEO_FIELD_CITY_WORKRND_REASON_ROWID = "WORKRND_REASON_ROWID";
	public static final String GEO_FIELD_CITY_GDA_DFND_AREA_PRNT_ROWID = "GDA_DFND_AREA_PRNT_ROWID";
	public static final String GEO_FIELD_CITY_GDA_DFND_AREA_REL_ROWID = "GDA_DFND_AREA_REL_ROWID";
	public static final String GEO_FIELD_CITY_TYP_TYPE_CD = "TYP_TYPE_CD";
	
	public static final String GEO_FIELD_CITY_ALTNAME_RKST = "ALTNAME_RKST";
	public static final String GEO_FIELD_CITY_ALTDDESC_RKST = "ALTDDESC_RKST";
	public static final String GEO_FIELD_CITY_ALTSTATUS_RKST = "ALTSTATUS_RKST";
	public static final String GEO_FIELD_CITY_ALTTYPE_RKST = "ALTTYPE_RKST";
	public static final String GEO_FIELD_CITY_ALTCODE_RKST = "ALTCODE_RKST";
	
	public static final String GEO_FIELD_CITY_ALTNAME_RKTS = "ALTNAME_RKTS";
	public static final String GEO_FIELD_CITY_ALTDDESC_RKTS = "ALTDDESC_RKTS";
	public static final String GEO_FIELD_CITY_ALTSTATUS_RKTS = "ALTSTATUS_RKTS";
	public static final String GEO_FIELD_CITY_ALTTYPE_RKTS = "ALTTYPE_RKTS";
	public static final String GEO_FIELD_CITY_ALTCODE_RKTS = "ALTCODE_RKTS";
	
	public static final String GEO_FIELD_CITY_ALTNAME_MODEL = "ALTNAME_MODEL";
	public static final String GEO_FIELD_CITY_ALTDDESC_MODEL = "ALTDDESC_MODEL";
	public static final String GEO_FIELD_CITY_ALTSTATUS_MODEL = "ALTSTATUS_MODEL";
	public static final String GEO_FIELD_CITY_ALTTYPE_MODEL = "ALTTYPE_MODEL";
	public static final String GEO_FIELD_CITY_ALTCODE_MODEL = "ALTCODE_MODEL";
	
	public static final String GEO_FIELD_CITY_OLSON_TIMEZONE = "OLSON_TIMEZONE";
	public static final String GEO_FIELD_CITYREL_VALID_FROM = "CITYREL_VALID_FROM";
	public static final String GEO_FIELD_CITYREL_VALID_TO = "CITYREL_VALID_TO";
	public static final String GEO_FIELD_CITY_ALT_CODE = "CITY_ALT_CODE";
	public static final String GEO_FIELD_BDA = "CITY_BDA";
	public static final String GEO_ENTITY_CITY_BDA_HUB_IND = "CITY_BDA_HUB_IND";
	public static final String GEO_ENTITY_CITY_REL_BDA_ROWID = "CITY_REL_BDA_ROWID";
	
	/** The sip properties file name. */
	public static String SIP_PROPERTIES_FILE_NAME = "/siperian-client";
	
	/** The file name separator. */
	public static String FILE_NAME_SEPARATOR = "_";
	
	/** The datasounce name. */
	public static final String DATASOURCE_NAME = "SMDS-WEB-SERVICES";
	
	/** The properties file extension. */
	public static String PROPERTIES_FILE_EXTENSION = ".properties";
	
	/** The jms configuration file. */
	public static String JMS_CONFIGURATION_FILE = "/SDNJMSCONFIGURATION.properties";
}
