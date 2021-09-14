select C.CODE as code,
       C.NAME as name,
       D.NAME as  masterType,
       B.VALID_THRU_DT as validThroughDate
from C_FCT_OPS_TYP_REL B
         inner join MDM_INFM_SMDS.C_TYP_TYPE C on C.ROWID_OBJECT = B.TYP_TYPE_ROWID AND C.HUB_STATE_IND = 1
         inner join MDM_INFM_SMDS.C_TYP_MSTR_TYPE D on D.CODE = C.TYP_MSTR_TYPE_CD AND C.HUB_STATE_IND = 1
where B.HUB_STATE_IND ='1' AND B.FCT_OPS_ROWID=?