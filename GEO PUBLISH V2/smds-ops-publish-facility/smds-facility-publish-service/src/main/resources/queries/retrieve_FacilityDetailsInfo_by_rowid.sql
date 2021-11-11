select fctOpsRowid
     , weightLimitCraneKg
     , weightLimitYardKg
     , vesselAgent
     , gpsFlag
     , gsmFlag
     , oceanFreightPricing
     , commFacilityBrand
     , commFacilityType
     , commExportEnquiriesEmail
     , commImportEnquiriesEmail
     , commFacilityFunction
     , commFacilityFunctionDesc
     , commInternationalDialCode
     , telephoneNumber
from ((select A.Rowid_Object     as fctOpsRowid,
              A.WEIGHT_LMT_CRANE as weightLimitCraneKg,
              A.WEIGHT_LMT_YARD  as weightLimitYardKg,
              A.VESSEL_AGENT     as vesselAgent,
              A.GPS_FLAG         as gpsFlag,
              A.GSM_FLAG         as gsmFlag,
              A.OCE_FRGHT_PR     as oceanFreightPricing,
              NULL               as commFacilityBrand,
              NULL               as commFacilityType,
              NULL               as commExportEnquiriesEmail,
              NULL               as commImportEnquiriesEmail,
              NULL               as commFacilityFunction,
              NULL               as commFacilityFunctionDesc,
              NULL               as commInternationalDialCode,
              NULL               as telephoneNumber
       from C_FCT_OPS A
       where A.HUB_STATE_IND = '1'
         and A.FCT_ROWID = ?)
      UNION
      (select A.Rowid_Object   as fctOpsRowid,
              NULL             as weightLimitCraneKg,
              NULL             as weightLimitYardKg,
              NULL             as vesselAgent,
              NULL             as gpsFlag,
              NULL             as gsmFlag,
              NULL             as oceanFreightPricing,
              B.NAME           as commFacilityBrand,
              C.NAME           as commFacilityType,
              A.EXP_MAIL       as commExportEnquiriesEmail,
              A.IMP_MAIL       as commImportEnquiriesEmail,
              D.NAME           as commFacilityFunction,
              D.DESCRIPTION    as commFacilityFunctionDesc,
              E.DAILNG_CD_DESC as commInternationalDialCode,
              A.TELECOM_NUM    as telephoneNumber
       from C_FCT_COM A
                LEFT Outer join C_FCT_BRAND B on B.ROWID_OBJECT = A.FCT_BRAND_ROWID AND B.HUB_STATE_IND = 1
                LEFT Outer join C_TYP_TYPE C on C.ROWID_OBJECT = A.TYP_TYPE_ROWID AND C.HUB_STATE_IND = 1 and
                                                C.TYP_MSTR_TYPE_CD = 'FCT_COM_TYPE'
                LEFT Outer join C_FCT_COMM_FUNC D on D.ROWID_OBJECT = A.COMM_FUNC_ROWID AND D.HUB_STATE_IND = 1 and
                                                     C.TYP_MSTR_TYPE_CD = 'FCT_COM_TYPE'
                LEFT Outer join C_CTM_INTL_DIALNG_CD E on E.ROWID_OBJECT = A.INTL_DIALING_ROWID AND E.HUB_STATE_IND = 1
       where A.HUB_STATE_IND = '1'
         and A.FCT_ROWID = ?))