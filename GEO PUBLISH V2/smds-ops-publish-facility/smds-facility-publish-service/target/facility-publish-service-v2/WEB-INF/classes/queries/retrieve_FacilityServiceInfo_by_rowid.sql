select
    B.OFF_NAME as serviceName,
    B.GRP_CD as serviceCode,
    B.OFF_DESC as serviceDescription,
    A.VALID_THRU_DT as validThroughDate
from C_FCT_OFF_REL A
inner join C_FCT_OFF B on B.ROWID_OBJECT = A.OFFERING_ROWID AND B.HUB_STATE_IND = 1
where A.HUB_STATE_IND = '1'
  and A.FCT_ROWID=?