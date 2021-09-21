package net.apmm.mdm.ops.geo.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;

@Configuration
@Slf4j
public class DataBaseQueries {

    public static String cityQuery = "select GEOROWID                   as geoRowID,\r\n"
            + "       GEOTYPE                    as geoType,\r\n"
            + "       NAME                       as name,\r\n"
            + "       STATUS                     as status,\r\n"
            + "       VALID_FROM_DT              as validFrom,\r\n"
            + "       VALID_THRU_DT              as validTo,\r\n"
            + "       LATITUDE                   as latitude,\r\n"
            + "       LONGITUDE                  as longitude,\r\n"
            + "       TIMEZONE                   as timeZone,\r\n"
            + "       DAYLIGHTSAVINGTIME         as daylightSavingTime,\r\n"
            + "       UTCOFFSETMINUTES           as utcOffsetMinutes,\r\n"
            + "       DAYLIGHTSAVINGSTART        as daylightSavingStart,\r\n"
            + "       DAYLIGHTSAVINGEND          as daylightSavingEnd,\r\n"
            + "       DAYLIGHTSAVINGSHIFTMINUTES as daylightSavingShiftMinutes,\r\n"
            + "       DESCRIPTION                as description,\r\n"
            + "       WORKAROUNDREASON           as workaroundReason,\r\n"
            + "       RESTRICTED                 as restricted,\r\n"
            + "       SITETYPE                   as siteType,\r\n"
            + "       GPSFLAG                    as gpsFlag,\r\n"
            + "       GSMFLAG                    as gsmFlag,\r\n"
            + "       STREETNUMBER               as streetNumber,\r\n"
            + "       ADDRESSLINE1               as addressLine1,\r\n"
            + "       ADDRESSLINE2               as addressLine2,\r\n"
            + "       ADDRESSLINE3               as addressLine3,\r\n"
            + "       POSTALCODE                 as postalCode,\r\n"
            + "       POSTALCODEMANDATORYFLAG    as postalCodeMandatoryFlag,\r\n"
            + "       STATEPROVINCEMANDATORY     as stateProvinceMandatory,\r\n"
            + "       DIALINGCODE                as dialingCode,\r\n"
            + "       DIALINGCODEDESCRIPTION     as dialingCodeDescription,\r\n"
            + "       PORTFLAG                   as portFlag,\r\n"
            + "       OLSONTIMEZONE              as olsonTimezone,\r\n"
            + "       BDATYPE                    as bdaType\r\n"
            + "from (select A.rowid_object        as GeoRowID,\r\n"
            + "            CASE WHEN A.TYP_TYPE_CD ='GDA.CONTINENT' THEN 'Continent'\r\n"
            + "                 WHEN A.TYP_TYPE_CD ='GDA.COUNTRY' THEN 'Country'\r\n"
            + "                 WHEN A.TYP_TYPE_CD ='GDA.STATE/PROV' THEN 'State/Prov'\r\n"
            + "                 WHEN A.TYP_TYPE_CD ='GDA.CITY' THEN 'City'\r\n"
            + "                 WHEN A.TYP_TYPE_CD ='GDA.CITY_SUBAREA' THEN 'CitySubArea'\r\n"
            + "                 WHEN A.TYP_TYPE_CD ='GDA.SITE' THEN 'Site'\r\n"
            + "                 WHEN A.TYP_TYPE_CD ='GDA.POSTAL_CODE' THEN 'Postal Code'\r\n"
            + "                 WHEN A.TYP_TYPE_CD ='GDA.BDA' THEN 'Business Defined Area'\r\n"
            + "                 ELSE A.TYP_TYPE_CD end  as GeoType,\r\n"
            + "             A.NAME,\r\n"
            + "             CASE WHEN A.ACTIVE_FLAG ='Y' THEN 'Active'\r\n"
            + "                  WHEN  A.ACTIVE_FLAG='N' THEN 'InActive'\r\n"
            + "                  ELSE A.ACTIVE_FLAG end  as Status,\r\n"
            + "             A.VALID_FROM_DT,\r\n"
            + "             A.VALID_THRU_DT,\r\n"
            + "             A.LAT_GEOSPTL         as Latitude,\r\n"
            + "             A.LNG_GEOSPTL         as Longitude,\r\n"
            + "             B.CODE                as TimeZone,\r\n"
            + "             C.CODE                as DaylightSavingTime,\r\n"
            + "             B.UTC_OFFSET_MINS                 UTCOffsetMinutes,\r\n"
            + "             D.PERIOD_START_TM     as DaylightSavingStart,\r\n"
            + "             D.PERIOD_END_TM       as DaylightSavingEnd,\r\n"
            + "             D.SHIFT_MINS          as DaylightSavingShiftMinutes,\r\n"
            + "             ROW_NUMBER()             OVER(ORDER BY PERIOD_START_TM DESC,  PERIOD_END_TM DESC) as ROW_NUMBER,\r\n"
            + "             A.DESCRIPTION,\r\n"
            + "             H.NAME                as WorkaroundReason,\r\n"
            + "             I.CNTRY_RSTRCTD_FLAG  as Restricted,\r\n"
            + "             M.NAME                as SiteType,\r\n"
            + "             L.GPS_FLAG            as GPSFlag,\r\n"
            + "             L.GSM_FLAG               GSMFlag,\r\n"
            + "             N.STREET_NO           as StreetNumber,\r\n"
            + "             N.ADDR_LN_1           as AddressLine1,\r\n"
            + "             N.ADDR_LN_2           as AddressLine2,\r\n"
            + "             N.ADDR_LN_3           as AddressLine3,\r\n"
            + "             N.PSTL_CD             as PostalCode,\r\n"
            + "             I.PSTL_CD_MNDTRY_FLAG as PostalCodeMandatoryFlag,\r\n"
            + "             I.STATE_MNDTRY_FLAG   as StateProvinceMandatory,\r\n"
            + "             J.DIALNG_CD           as DialingCode,\r\n"
            + "             J.DAILNG_CD_DESC      as DialingCodeDescription,\r\n"
            + "             CASE WHEN E.PORT_FLAG='N' THEN 'No'\r\n"
            + "                  WHEN  E.PORT_FLAG='Y' THEN 'Yes'\r\n"
            + "                  ELSE E.PORT_FLAG end  as PortFlag,\r\n"
            + "             E.OLSON_TZ            as OlsonTimezone,\r\n"
            + "             K.BDA_TYPE_CD         as BDAType\r\n"
            + "      from MDM_INFM_SMDS.C_GDA_DFND_AREA A\r\n"
            + "               LEFT OUTER JOIN C_TDS_TMZ B ON B.ROWID_OBJECT = A.TDS_TMZ_ROWID AND B.HUB_STATE_IND = 1\r\n"
            + "               LEFT OUTER JOIN C_TDS_DST C ON C.ROWID_OBJECT = A.TDS_DST_ROWID AND C.HUB_STATE_IND = 1\r\n"
            + "               LEFT OUTER JOIN C_TDS_DSTD D ON D.TDS_DST_ROWID = C.ROWID_OBJECT AND D.HUB_STATE_IND = 1\r\n"
            + "               LEFT OUTER JOIN C_GDA_CITY E ON E.GDA_DFND_AREA_ROWID = A.ROWID_OBJECT AND E.HUB_STATE_IND = 1\r\n"
            + "               LEFT OUTER JOIN C_GDA_OLSON_TZ F ON F.OLSON_TZ_CD = E.OLSON_TZ AND F.HUB_STATE_IND = 1\r\n"
            + "               LEFT OUTER JOIN C_TMP_WRKRND_RSN G ON G.GDA_DFND_AREA_ROWID = A.ROWID_OBJECT AND G.HUB_STATE_IND = 1\r\n"
            + "               LEFT OUTER JOIN C_TYP_TYPE H ON H.ROWID_OBJECT = G.TYP_TYPE_ROWID AND H.HUB_STATE_IND = 1\r\n"
            + "               LEFT OUTER JOIN C_GDA_COUNTRY I ON I.GDA_DFND_AREA_ROWID = A.ROWID_OBJECT AND I.HUB_STATE_IND = 1\r\n"
            + "               LEFT OUTER JOIN C_CTM_INTL_DIALNG_CD J ON J.CTRY_ROWID = A.ROWID_OBJECT AND J.HUB_STATE_IND = 1\r\n"
            + "               LEFT OUTER JOIN C_GDA_BDA K ON K.GDA_DFND_AREA_ROWID = A.ROWID_OBJECT AND K.HUB_STATE_IND = 1\r\n"
            + "               LEFT OUTER JOIN C_GDA_SITE L ON L.GDA_DFND_AREA_ROWID = A.ROWID_OBJECT AND L.HUB_STATE_IND = 1\r\n"
            + "               LEFT OUTER JOIN C_TYP_TYPE M ON M.ROWID_OBJECT = L.TYP_TYPE_ROWID AND M.HUB_STATE_IND = 1\r\n"
            + "               LEFT OUTER JOIN C_TMP_SITE_PHYS_LCN N ON N.GDA_SITE_ROWID = A.ROWID_OBJECT AND N.HUB_STATE_IND = 1\r\n"
            + "      where A.HUB_STATE_IND = 1\r\n"
            + "        AND A.rowid_object = 'GEOROWNUMBER')\r\n"
            + "where ROW_NUMBER = '1'";


    public static String geoAltCodeQuery = "select C.NAME as codeType ,B.CODE as code from C_GDA_DFND_AREA A\r\n"
            + "INNER JOIN C_ALT_CODE B ON B.GDA_DFND_AREA_ROWID=A.ROWID_OBJECT AND B.HUB_STATE_IND=1\r\n"
            + "INNER JOIN C_TYP_TYPE C ON C.ROWID_OBJECT=B.TYP_TYPE_ROWID AND C.HUB_STATE_IND=1\r\n"
            + "where A.HUB_STATE_IND=1 AND A.ROWID_OBJECT='GEOROWNUMBER'";

    public static String geoAltNameQuery = "select B.NAME as name ,\r\n"
            + "       B.DESCRIPTION as description,\r\n"
            + "       CASE WHEN  B.ACTIVE_FLAG='Y' THEN 'Active'\r\n"
            + "            ELSE 'InActive' end  as status  from C_GDA_DFND_AREA A\r\n"
            + "INNER JOIN C_ALT_NAME B ON B.GDA_DFND_AREA_ROWID=A.ROWID_OBJECT AND B.HUB_STATE_IND=1\r\n"
            + "where A.HUB_STATE_IND=1 AND A.ROWID_OBJECT='GEOROWNUMBER'\r\n"
            + "";


    public static String cityParentQuery = "select PARENTROWID as parentRowId, NAME as name, TYPE as type, BDATYPE as bdatype  from\r\n"
            + "( select B.GDA_DFND_AREA_PRNT_ROWID as parentRowId,C.NAME as name ,D.NAME as type ,E.BDA_TYPE_CD as BDATYPE from C_GDA_DFND_AREA A\r\n"
            + "INNER JOIN C_GDA_DFND_AREA_REL B ON B.GDA_DFND_AREA_CHLD_ROWID=A.ROWID_OBJECT AND B.HUB_STATE_IND=1 and B.TYP_TYPE_CD not in ('GDA_REL.CSA_IN_CITY','GDA_REL.GDA_IN_BDA')\r\n"
            + "INNER JOIN C_GDA_DFND_AREA C ON C.ROWID_OBJECT=B.GDA_DFND_AREA_PRNT_ROWID AND C.HUB_STATE_IND=1\r\n"
            + "INNER JOIN C_TYP_TYPE D ON D.CODE=C.TYP_TYPE_CD AND D.HUB_STATE_IND=1\r\n"
            + "LEFT OUTER JOIN C_GDA_BDA E ON E.GDA_DFND_AREA_ROWID =B.GDA_DFND_AREA_PRNT_ROWID AND E.HUB_STATE_IND=1\r\n"
            + "where A.HUB_STATE_IND=1 AND A.ROWID_OBJECT='GEOROWNUMBER'\r\n"
            + "Union\r\n"
            + "select E.GDA_DFND_AREA_PRNT_ROWID as parentRowId,C.NAME as name ,D.NAME as type ,E.BDA_TYPE_CD as BDATYPE from C_GDA_DFND_AREA A\r\n"
            + "INNER JOIN C_GDA_DFND_AREA_REL B ON B.GDA_DFND_AREA_CHLD_ROWID=A.ROWID_OBJECT AND B.HUB_STATE_IND=1 and B.TYP_TYPE_CD in ('GDA_REL.CSA_IN_CITY')\r\n"
            + "INNER JOIN C_GDA_DFND_AREA_REL E ON E.GDA_DFND_AREA_CHLD_ROWID=B.GDA_DFND_AREA_PRNT_ROWID AND E.HUB_STATE_IND=1 and E.TYP_TYPE_CD not in ('GDA_REL.GDA_IN_BDA')\r\n"
            + "INNER JOIN C_GDA_DFND_AREA C ON C.ROWID_OBJECT=E.GDA_DFND_AREA_PRNT_ROWID AND C.HUB_STATE_IND=1\r\n"
            + "INNER JOIN C_TYP_TYPE D ON D.CODE=C.TYP_TYPE_CD AND D.HUB_STATE_IND=1\r\n"
            + "LEFT OUTER JOIN C_GDA_BDA E ON E.GDA_DFND_AREA_ROWID =B.GDA_DFND_AREA_PRNT_ROWID AND E.HUB_STATE_IND=1\r\n"
            + "where A.HUB_STATE_IND=1 AND A.ROWID_OBJECT='GEOROWNUMBER')";


    public static String cityParentAltCodeQuery ="select C.NAME as codeType ,B.CODE as code from C_GDA_DFND_AREA A\n" +
            "INNER JOIN C_ALT_CODE B ON B.GDA_DFND_AREA_ROWID=A.ROWID_OBJECT AND B.HUB_STATE_IND=1\n" +
            "INNER JOIN C_TYP_TYPE C ON C.ROWID_OBJECT=B.TYP_TYPE_ROWID AND C.HUB_STATE_IND=1\n" +
            "where A.HUB_STATE_IND=1 AND A.ROWID_OBJECT in (\n" +
            "select PARENTROWID as parentRowId  from\n" +
            "( select B.GDA_DFND_AREA_PRNT_ROWID as parentRowId,C.NAME as name ,D.NAME as type ,E.BDA_TYPE_CD as BDATYPE from C_GDA_DFND_AREA A\n" +
            "INNER JOIN C_GDA_DFND_AREA_REL B ON B.GDA_DFND_AREA_CHLD_ROWID=A.ROWID_OBJECT AND B.HUB_STATE_IND=1 and B.TYP_TYPE_CD not in ('GDA_REL.CSA_IN_CITY','GDA_REL.GDA_IN_BDA')\n" +
            "INNER JOIN C_GDA_DFND_AREA C ON C.ROWID_OBJECT=B.GDA_DFND_AREA_PRNT_ROWID AND C.HUB_STATE_IND=1\n" +
            "INNER JOIN C_TYP_TYPE D ON D.CODE=C.TYP_TYPE_CD AND D.HUB_STATE_IND=1\n" +
            "LEFT OUTER JOIN C_GDA_BDA E ON E.GDA_DFND_AREA_ROWID =B.GDA_DFND_AREA_PRNT_ROWID AND E.HUB_STATE_IND=1\n" +
            "where A.HUB_STATE_IND=1 AND A.ROWID_OBJECT='GEOROWNUMBER'\n" +
            "Union\n" +
            "select E.GDA_DFND_AREA_PRNT_ROWID as parentRowId,C.NAME as name ,D.NAME as type ,E.BDA_TYPE_CD as BDATYPE from C_GDA_DFND_AREA A\n" +
            "INNER JOIN C_GDA_DFND_AREA_REL B ON B.GDA_DFND_AREA_CHLD_ROWID=A.ROWID_OBJECT AND B.HUB_STATE_IND=1 and B.TYP_TYPE_CD in ('GDA_REL.CSA_IN_CITY')\n" +
            "INNER JOIN C_GDA_DFND_AREA_REL E ON E.GDA_DFND_AREA_CHLD_ROWID=B.GDA_DFND_AREA_PRNT_ROWID AND E.HUB_STATE_IND=1 and E.TYP_TYPE_CD not in ('GDA_REL.GDA_IN_BDA')\n" +
            "INNER JOIN C_GDA_DFND_AREA C ON C.ROWID_OBJECT=E.GDA_DFND_AREA_PRNT_ROWID AND C.HUB_STATE_IND=1\n" +
            "INNER JOIN C_TYP_TYPE D ON D.CODE=C.TYP_TYPE_CD AND D.HUB_STATE_IND=1\n" +
            "LEFT OUTER JOIN C_GDA_BDA E ON E.GDA_DFND_AREA_ROWID =B.GDA_DFND_AREA_PRNT_ROWID AND E.HUB_STATE_IND=1\n" +
            "where A.HUB_STATE_IND=1 AND A.ROWID_OBJECT='GEOROWNUMBER'))";


    public static String cityCountryQuery = "select COUNTRYROWID as countryRowID, NAME as name, TYPE as type from\r\n"
            + "    ((select DFND.ROWID_OBJECT as countryRowID, DFND.NAME as name, DFND.TYP_TYPE_CD as type\r\n"
            + "      from C_GDA_DFND_AREA_REL RL,\r\n"
            + "           C_GDA_DFND_AREA DFND,\r\n"
            + "           (select B.GDA_DFND_AREA_PRNT_ROWID as StateRowid\r\n"
            + "            from C_GDA_DFND_AREA A\r\n"
            + "                     INNER JOIN C_GDA_DFND_AREA_REL B\r\n"
            + "                                ON B.GDA_DFND_AREA_CHLD_ROWID = A.ROWID_OBJECT AND B.HUB_STATE_IND = 1 and\r\n"
            + "                                   B.TYP_TYPE_CD not in 'GDA_REL.GDA_IN_BDA'\r\n"
            + "                     INNER JOIN C_GDA_DFND_AREA C\r\n"
            + "                                ON C.ROWID_OBJECT = B.GDA_DFND_AREA_PRNT_ROWID AND C.HUB_STATE_IND = 1\r\n"
            + "                     INNER JOIN C_TYP_TYPE D ON D.CODE = C.TYP_TYPE_CD AND D.HUB_STATE_IND = 1\r\n"
            + "            where A.HUB_STATE_IND = 1\r\n"
            + "              and C.TYP_TYPE_CD = 'GDA.STATE/PROV'\r\n"
            + "              AND A.ROWID_OBJECT='GEOROWNUMBER'\r\n"
            + "           ) RLFK\r\n"
            + "      where RL.GDA_DFND_AREA_CHLD_ROWID = RLFK.STATEROWID\r\n"
            + "        and RL.TYP_TYPE_CD = 'GDA_REL.TRTY_IN_CTRY'\r\n"
            + "        AND DFND.ROWID_OBJECT = RL.GDA_DFND_AREA_PRNT_ROWID\r\n"
            + "        AND DFND.TYP_TYPE_CD = 'GDA.COUNTRY')\r\n"
            + "     UNION\r\n"
            + "     (select C.ROWID_OBJECT, C.NAME as CountryNm, C.TYP_TYPE_CD as Type\r\n"
            + "      from C_GDA_DFND_AREA A\r\n"
            + "               INNER JOIN C_GDA_DFND_AREA_REL B ON B.GDA_DFND_AREA_CHLD_ROWID = A.ROWID_OBJECT AND B.HUB_STATE_IND = 1\r\n"
            + "          and B.TYP_TYPE_CD not in 'GDA_REL.GDA_IN_BDA'\r\n"
            + "               INNER JOIN C_GDA_DFND_AREA C\r\n"
            + "                          ON C.ROWID_OBJECT = B.GDA_DFND_AREA_PRNT_ROWID AND C.HUB_STATE_IND = 1\r\n"
            + "               INNER JOIN C_TYP_TYPE D ON D.CODE = C.TYP_TYPE_CD AND D.HUB_STATE_IND = 1\r\n"
            + "      where A.HUB_STATE_IND = 1\r\n"
            + "        and C.TYP_TYPE_CD = 'GDA.COUNTRY'\r\n"
            + "        AND A.ROWID_OBJECT='GEOROWNUMBER')\r\n"
            + "     UNION\r\n"
            + "     (select dfnd.rowid_object, dfnd.NAME, dfnd.TYP_TYPE_CD\r\n"
            + "      from c_gda_dfnd_area city,\r\n"
            + "           c_gda_dfnd_area_rel r1,\r\n"
            + "           c_gda_dfnd_area regn,\r\n"
            + "           c_gda_dfnd_area_rel r2,\r\n"
            + "           c_gda_dfnd_area cntry,\r\n"
            + "           c_gda_dfnd_area_rel r3,\r\n"
            + "           c_gda_dfnd_area dfnd\r\n"
            + "      where r1.gda_dfnd_area_chld_rowid = city.rowid_object\r\n"
            + "        and regn.rowid_object = r1.GDA_DFND_AREA_PRNT_ROWID\r\n"
            + "        and regn.TYP_TYPE_CD = 'GDA.CITY'\r\n"
            + "        and r2.gda_dfnd_area_chld_rowid = regn.rowid_object\r\n"
            + "        and cntry.ROWID_OBJECT = r2.GDA_DFND_AREA_PRNT_ROWID\r\n"
            + "        and cntry.TYP_TYPE_CD = 'GDA.STATE/PROV'\r\n"
            + "        and r3.gda_dfnd_area_chld_rowid = cntry.rowid_object\r\n"
            + "        and dfnd.ROWID_OBJECT = r3.GDA_DFND_AREA_PRNT_ROWID\r\n"
            + "        and dfnd.TYP_TYPE_CD = 'GDA.COUNTRY'\r\n"
            + "        AND city.ROWID_OBJECT='GEOROWNUMBER')\r\n"
            + "     UNION\r\n"
            + "     (select cntry.rowid_object, cntry.NAME, cntry.TYP_TYPE_CD\r\n"
            + "      from c_gda_dfnd_area city,\r\n"
            + "           c_gda_dfnd_area_rel r1,\r\n"
            + "           c_gda_dfnd_area regn,\r\n"
            + "           c_gda_dfnd_area_rel r2,\r\n"
            + "           c_gda_dfnd_area cntry\r\n"
            + "      where r1.gda_dfnd_area_chld_rowid = city.rowid_object\r\n"
            + "        and regn.rowid_object = r1.GDA_DFND_AREA_PRNT_ROWID\r\n"
            + "        and regn.TYP_TYPE_CD = 'GDA.CITY'\r\n"
            + "        and r2.gda_dfnd_area_chld_rowid = regn.rowid_object\r\n"
            + "        and cntry.ROWID_OBJECT = r2.GDA_DFND_AREA_PRNT_ROWID\r\n"
            + "        and cntry.TYP_TYPE_CD = 'GDA.COUNTRY'\r\n"
            + "        and city.ROWID_OBJECT='GEOROWNUMBER')\r\n"
            + "    )";


    public static String cityCountryAltCodeQuery = "select C.NAME as codeType ,B.CODE as code from C_GDA_DFND_AREA A\n" +
            "INNER JOIN C_ALT_CODE B ON B.GDA_DFND_AREA_ROWID=A.ROWID_OBJECT AND B.HUB_STATE_IND=1\n" +
            "INNER JOIN C_TYP_TYPE C ON C.ROWID_OBJECT=B.TYP_TYPE_ROWID AND C.HUB_STATE_IND=1\n" +
            "where A.HUB_STATE_IND=1 AND A.ROWID_OBJECT in\n" +
            "(\n" +
            "select COUNTRYROWID as countryRowID from\n" +
            "    ((select DFND.ROWID_OBJECT as countryRowID, DFND.NAME as name, DFND.TYP_TYPE_CD as type\n" +
            "      from C_GDA_DFND_AREA_REL RL,\n" +
            "           C_GDA_DFND_AREA DFND,\n" +
            "           (select B.GDA_DFND_AREA_PRNT_ROWID as StateRowid\n" +
            "            from C_GDA_DFND_AREA A\n" +
            "                     INNER JOIN C_GDA_DFND_AREA_REL B\n" +
            "                                ON B.GDA_DFND_AREA_CHLD_ROWID = A.ROWID_OBJECT AND B.HUB_STATE_IND = 1 and\n" +
            "                                   B.TYP_TYPE_CD not in 'GDA_REL.GDA_IN_BDA'\n" +
            "                     INNER JOIN C_GDA_DFND_AREA C\n" +
            "                                ON C.ROWID_OBJECT = B.GDA_DFND_AREA_PRNT_ROWID AND C.HUB_STATE_IND = 1\n" +
            "                     INNER JOIN C_TYP_TYPE D ON D.CODE = C.TYP_TYPE_CD AND D.HUB_STATE_IND = 1\n" +
            "            where A.HUB_STATE_IND = 1\n" +
            "              and C.TYP_TYPE_CD = 'GDA.STATE/PROV'\n" +
            "              AND A.ROWID_OBJECT='GEOROWNUMBER'\n" +
            "           ) RLFK\n" +
            "      where RL.GDA_DFND_AREA_CHLD_ROWID = RLFK.STATEROWID\n" +
            "        and RL.TYP_TYPE_CD = 'GDA_REL.TRTY_IN_CTRY'\n" +
            "        AND DFND.ROWID_OBJECT = RL.GDA_DFND_AREA_PRNT_ROWID\n" +
            "        AND DFND.TYP_TYPE_CD = 'GDA.COUNTRY')\n" +
            "     UNION\n" +
            "     (select C.ROWID_OBJECT, C.NAME as CountryNm, C.TYP_TYPE_CD as Type\n" +
            "      from C_GDA_DFND_AREA A\n" +
            "               INNER JOIN C_GDA_DFND_AREA_REL B ON B.GDA_DFND_AREA_CHLD_ROWID = A.ROWID_OBJECT AND B.HUB_STATE_IND = 1\n" +
            "          and B.TYP_TYPE_CD not in 'GDA_REL.GDA_IN_BDA'\n" +
            "               INNER JOIN C_GDA_DFND_AREA C\n" +
            "                          ON C.ROWID_OBJECT = B.GDA_DFND_AREA_PRNT_ROWID AND C.HUB_STATE_IND = 1\n" +
            "               INNER JOIN C_TYP_TYPE D ON D.CODE = C.TYP_TYPE_CD AND D.HUB_STATE_IND = 1\n" +
            "      where A.HUB_STATE_IND = 1\n" +
            "        and C.TYP_TYPE_CD = 'GDA.COUNTRY'\n" +
            "        AND A.ROWID_OBJECT='GEOROWNUMBER'\n" +
            "        )\n" +
            "     UNION\n" +
            "     (select dfnd.rowid_object, dfnd.NAME, dfnd.TYP_TYPE_CD\n" +
            "      from c_gda_dfnd_area city,\n" +
            "           c_gda_dfnd_area_rel r1,\n" +
            "           c_gda_dfnd_area regn,\n" +
            "           c_gda_dfnd_area_rel r2,\n" +
            "           c_gda_dfnd_area cntry,\n" +
            "           c_gda_dfnd_area_rel r3,\n" +
            "           c_gda_dfnd_area dfnd\n" +
            "      where r1.gda_dfnd_area_chld_rowid = city.rowid_object\n" +
            "        and regn.rowid_object = r1.GDA_DFND_AREA_PRNT_ROWID\n" +
            "        and regn.TYP_TYPE_CD = 'GDA.CITY'\n" +
            "        and r2.gda_dfnd_area_chld_rowid = regn.rowid_object\n" +
            "        and cntry.ROWID_OBJECT = r2.GDA_DFND_AREA_PRNT_ROWID\n" +
            "        and cntry.TYP_TYPE_CD = 'GDA.STATE/PROV'\n" +
            "        and r3.gda_dfnd_area_chld_rowid = cntry.rowid_object\n" +
            "        and dfnd.ROWID_OBJECT = r3.GDA_DFND_AREA_PRNT_ROWID\n" +
            "        and dfnd.TYP_TYPE_CD = 'GDA.COUNTRY'\n" +
            "        AND city.ROWID_OBJECT='GEOROWNUMBER')\n" +
            "     UNION\n" +
            "     (select cntry.rowid_object, cntry.NAME, cntry.TYP_TYPE_CD\n" +
            "      from c_gda_dfnd_area city,\n" +
            "           c_gda_dfnd_area_rel r1,\n" +
            "           c_gda_dfnd_area regn,\n" +
            "           c_gda_dfnd_area_rel r2,\n" +
            "           c_gda_dfnd_area cntry\n" +
            "      where r1.gda_dfnd_area_chld_rowid = city.rowid_object\n" +
            "        and regn.rowid_object = r1.GDA_DFND_AREA_PRNT_ROWID\n" +
            "        and regn.TYP_TYPE_CD = 'GDA.CITY'\n" +
            "        and r2.gda_dfnd_area_chld_rowid = regn.rowid_object\n" +
            "        and cntry.ROWID_OBJECT = r2.GDA_DFND_AREA_PRNT_ROWID\n" +
            "        and cntry.TYP_TYPE_CD = 'GDA.COUNTRY'\n" +
            "        and city.ROWID_OBJECT='GEOROWNUMBER')\n" +
            "    )\n" +
            "    )";

    public static String citySubCityParentQuery ="select B.GDA_DFND_AREA_PRNT_ROWID as parentRowId,C.NAME as name ,D.NAME as type from C_GDA_DFND_AREA A\r\n"
            + "INNER JOIN C_GDA_DFND_AREA_REL B ON B.GDA_DFND_AREA_CHLD_ROWID=A.ROWID_OBJECT AND B.HUB_STATE_IND=1 and B.TYP_TYPE_CD in ('GDA_REL.CSA_IN_CITY')\r\n"
            + "INNER JOIN C_GDA_DFND_AREA C ON C.ROWID_OBJECT=B.GDA_DFND_AREA_PRNT_ROWID AND C.HUB_STATE_IND=1\r\n"
            + "INNER JOIN C_TYP_TYPE D ON D.CODE=C.TYP_TYPE_CD AND D.HUB_STATE_IND=1\r\n"
            + "where A.HUB_STATE_IND=1 AND A.ROWID_OBJECT='GEOROWNUMBER'";


    public static String citySubCityParentAltCodesQuery = "select D.NAME as codeType ,C.CODE as code from C_GDA_DFND_AREA A\n" +
            "INNER JOIN C_ALT_CODE C ON C.GDA_DFND_AREA_ROWID=A.ROWID_OBJECT AND C.HUB_STATE_IND=1\n" +
            "INNER JOIN C_TYP_TYPE D ON D.ROWID_OBJECT=C.TYP_TYPE_ROWID AND D.HUB_STATE_IND=1\n" +
            "where A.HUB_STATE_IND=1 AND A.ROWID_OBJECT in \n" +
            "(select B.GDA_DFND_AREA_PRNT_ROWID as parentRowId from C_GDA_DFND_AREA A\n" +
            "INNER JOIN C_GDA_DFND_AREA_REL B ON B.GDA_DFND_AREA_CHLD_ROWID=A.ROWID_OBJECT AND B.HUB_STATE_IND=1 and B.TYP_TYPE_CD in ('GDA_REL.CSA_IN_CITY')\n" +
            "INNER JOIN C_GDA_DFND_AREA C ON C.ROWID_OBJECT=B.GDA_DFND_AREA_PRNT_ROWID AND C.HUB_STATE_IND=1\n" +
            "INNER JOIN C_TYP_TYPE D ON D.CODE=C.TYP_TYPE_CD AND D.HUB_STATE_IND=1\n" +
            "where A.HUB_STATE_IND=1 AND A.ROWID_OBJECT='GEOROWNUMBER')";


    public static String cityBDAQuery = "select B.GDA_DFND_AREA_PRNT_ROWID as bdaRowID,\n" +
            "       C.NAME as name ,\n" +
            "       D.NAME as type,\n" +
            "       E.BDA_TYPE_CD as bdatype\n" +
            "from C_GDA_DFND_AREA A\n" +
            "         INNER JOIN C_GDA_DFND_AREA_REL B ON B.GDA_DFND_AREA_CHLD_ROWID=A.ROWID_OBJECT AND B.HUB_STATE_IND=1 and B.TYP_TYPE_CD in ('GDA_REL.GDA_IN_BDA')\n" +
            "         INNER JOIN C_GDA_DFND_AREA C ON C.ROWID_OBJECT=B.GDA_DFND_AREA_PRNT_ROWID AND C.HUB_STATE_IND=1\n" +
            "         INNER JOIN C_TYP_TYPE D ON D.CODE=C.TYP_TYPE_CD AND D.HUB_STATE_IND=1\n" +
            "         INNER JOIN C_GDA_BDA E ON E.GDA_DFND_AREA_ROWID=B.GDA_DFND_AREA_PRNT_ROWID AND E.HUB_STATE_IND=1\n" +
            "where A.HUB_STATE_IND=1 AND A.ROWID_OBJECT='GEOROWNUMBER'";

    public static String bdaAltCodeQuery = "select C.NAME as codeType ,B.CODE as code from C_GDA_DFND_AREA A\n" +
            "INNER JOIN C_ALT_CODE B ON B.GDA_DFND_AREA_ROWID=A.ROWID_OBJECT AND B.HUB_STATE_IND=1\n" +
            "INNER JOIN C_TYP_TYPE C ON C.ROWID_OBJECT=B.TYP_TYPE_ROWID AND C.HUB_STATE_IND=1\n" +
            "where A.HUB_STATE_IND=1 AND A.ROWID_OBJECT='GEOROWNUMBER'";


}
