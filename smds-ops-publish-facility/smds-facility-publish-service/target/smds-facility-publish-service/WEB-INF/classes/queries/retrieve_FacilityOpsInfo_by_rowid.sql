select
       ROWID_OBJECT as fctRowID,
       FACILITY_NAME as facilityName,
       CASE WHEN CLASS_CD ='OPS' THEN 'OperationalFacility'
            WHEN  CLASS_CD='COMM' THEN 'CommercialFacility'
            WHEN  CLASS_CD='CUST' THEN 'CustomerFacility'
            ELSE CLASS_CD end  as facilityType,
       EXT_OWNED as facilityExtOwned,
       CASE WHEN STATUS_CD ='A' THEN 'Active'
            WHEN  STATUS_CD='I' THEN 'InActive'
            WHEN  STATUS_CD='P' THEN 'Pending'
            ELSE STATUS_CD end  as facilityStatus,
       EXT_EXPOSED as facilityExtExposed,
       URL as facilityURL,
       DODAAC as facilityDoDaac
from C_FCT_FACILITY
where HUB_STATE_IND = '1'
AND  rowid_object=?

