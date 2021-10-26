select GEOROWID                   as geoRowID,
       GEOTYPE                    as geoType,
       NAME                       as name,
       STATUS                     as status,
       VALID_FROM_DT              as validFrom,
       VALID_THRU_DT              as validTo,
       LATITUDE                   as latitude,
       LONGITUDE                  as longitude,
       TIMEZONE                   as timeZone,
       DAYLIGHTSAVINGTIME         as daylightSavingTime,
       UTCOFFSETMINUTES           as utcOffsetMinutes,
       DAYLIGHTSAVINGSTART        as daylightSavingStart,
       DAYLIGHTSAVINGEND          as daylightSavingEnd,
       DAYLIGHTSAVINGSHIFTMINUTES as daylightSavingShiftMinutes,
       DESCRIPTION                as description,
       WORKAROUNDREASON           as workaroundReason,
       RESTRICTED                 as restricted,
       SITETYPE                   as siteType,
       GPSFLAG                    as gpsFlag,
       GSMFLAG                    as gsmFlag,
       STREETNUMBER               as streetNumber,
       ADDRESSLINE1               as addressLine1,
       ADDRESSLINE2               as addressLine2,
       ADDRESSLINE3               as addressLine3,
       POSTALCODE                 as postalCode,
       POSTALCODEMANDATORYFLAG    as postalCodeMandatoryFlag,
       STATEPROVINCEMANDATORY     as stateProvinceMandatory,
       DIALINGCODE                as dialingCode,
       DIALINGCODEDESCRIPTION     as dialingCodeDescription,
       PORTFLAG                   as portFlag,
       OLSONTIMEZONE              as olsonTimezone,
       BDATYPE                    as bdaType,
       HSUDNAME                   as hsudName
from (select A.rowid_object        as GeoRowID,
            CASE WHEN A.TYP_TYPE_CD ='GDA.CONTINENT' THEN 'Continent'
                 WHEN A.TYP_TYPE_CD ='GDA.COUNTRY' THEN 'Country'
                 WHEN A.TYP_TYPE_CD ='GDA.STATE/PROV' THEN 'State/Prov'
                 WHEN A.TYP_TYPE_CD ='GDA.CITY' THEN 'City'
                 WHEN A.TYP_TYPE_CD ='GDA.CITY_SUBAREA' THEN 'CitySubArea'
                 WHEN A.TYP_TYPE_CD ='GDA.SITE' THEN 'Site'
                 WHEN A.TYP_TYPE_CD ='GDA.POSTAL_CODE' THEN 'Postal Code'
                 WHEN A.TYP_TYPE_CD ='GDA.BDA' THEN 'Business Defined Area'
                 ELSE A.TYP_TYPE_CD end  as GeoType,
             A.NAME,
             CASE WHEN A.ACTIVE_FLAG ='Y' THEN 'Active'
                  WHEN  A.ACTIVE_FLAG='N' THEN 'InActive'
                  ELSE A.ACTIVE_FLAG end  as Status,
             A.VALID_FROM_DT,
             A.VALID_THRU_DT,
             A.LAT_GEOSPTL         as Latitude,
             A.LNG_GEOSPTL         as Longitude,
             B.CODE                as TimeZone,
             C.CODE                as DaylightSavingTime,
             B.UTC_OFFSET_MINS                 UTCOffsetMinutes,
             D.PERIOD_START_TM     as DaylightSavingStart,
             D.PERIOD_END_TM       as DaylightSavingEnd,
             D.SHIFT_MINS          as DaylightSavingShiftMinutes,
             ROW_NUMBER()             OVER(ORDER BY PERIOD_START_TM DESC,  PERIOD_END_TM DESC) as ROW_NUMBER,
             A.DESCRIPTION,
             H.NAME                as WorkaroundReason,
             I.CNTRY_RSTRCTD_FLAG  as Restricted,
             M.NAME                as SiteType,
             L.GPS_FLAG            as GPSFlag,
             L.GSM_FLAG               GSMFlag,
             N.STREET_NO           as StreetNumber,
             N.ADDR_LN_1           as AddressLine1,
             N.ADDR_LN_2           as AddressLine2,
             N.ADDR_LN_3           as AddressLine3,
             N.PSTL_CD             as PostalCode,
             I.PSTL_CD_MNDTRY_FLAG as PostalCodeMandatoryFlag,
             I.STATE_MNDTRY_FLAG   as StateProvinceMandatory,
             J.DIALNG_CD           as DialingCode,
             J.DAILNG_CD_DESC      as DialingCodeDescription,
             CASE WHEN E.PORT_FLAG='N' THEN 'No'
                  WHEN  E.PORT_FLAG='Y' THEN 'Yes'
                  ELSE E.PORT_FLAG end  as PortFlag,
             E.OLSON_TZ            as OlsonTimezone,
             K.BDA_TYPE_CD         as BDAType,
             E.HSUD_NAME as HSUDNAME
      from MDM_INFM_SMDS.C_GDA_DFND_AREA A
               LEFT OUTER JOIN C_TDS_TMZ B ON B.ROWID_OBJECT = A.TDS_TMZ_ROWID AND B.HUB_STATE_IND = 1
               LEFT OUTER JOIN C_TDS_DST C ON C.ROWID_OBJECT = A.TDS_DST_ROWID AND C.HUB_STATE_IND = 1
               LEFT OUTER JOIN C_TDS_DSTD D ON D.TDS_DST_ROWID = C.ROWID_OBJECT AND D.HUB_STATE_IND = 1
               LEFT OUTER JOIN C_GDA_CITY E ON E.GDA_DFND_AREA_ROWID = A.ROWID_OBJECT AND E.HUB_STATE_IND = 1
               LEFT OUTER JOIN C_GDA_OLSON_TZ F ON F.OLSON_TZ_CD = E.OLSON_TZ AND F.HUB_STATE_IND = 1
               LEFT OUTER JOIN C_TMP_WRKRND_RSN G ON G.GDA_DFND_AREA_ROWID = A.ROWID_OBJECT AND G.HUB_STATE_IND = 1
               LEFT OUTER JOIN C_TYP_TYPE H ON H.ROWID_OBJECT = G.TYP_TYPE_ROWID AND H.HUB_STATE_IND = 1
               LEFT OUTER JOIN C_GDA_COUNTRY I ON I.GDA_DFND_AREA_ROWID = A.ROWID_OBJECT AND I.HUB_STATE_IND = 1
               LEFT OUTER JOIN C_CTM_INTL_DIALNG_CD J ON J.CTRY_ROWID = A.ROWID_OBJECT AND J.HUB_STATE_IND = 1
               LEFT OUTER JOIN C_GDA_BDA K ON K.GDA_DFND_AREA_ROWID = A.ROWID_OBJECT AND K.HUB_STATE_IND = 1
               LEFT OUTER JOIN C_GDA_SITE L ON L.GDA_DFND_AREA_ROWID = A.ROWID_OBJECT AND L.HUB_STATE_IND = 1
               LEFT OUTER JOIN C_TYP_TYPE M ON M.ROWID_OBJECT = L.TYP_TYPE_ROWID AND M.HUB_STATE_IND = 1
               LEFT OUTER JOIN C_TMP_SITE_PHYS_LCN N ON N.GDA_SITE_ROWID = A.ROWID_OBJECT AND N.HUB_STATE_IND = 1
      where A.HUB_STATE_IND = 1
        AND A.rowid_object = ?)
where ROW_NUMBER = '1'