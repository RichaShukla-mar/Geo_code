select B.NAME as codeType,
       A.CODE as code
from c_fct_alt_codes A
         INNER JOIN C_TYP_TYPE B ON B.ROWID_OBJECT = A.TYP_TYPE_ROWID AND A.HUB_STATE_IND = 1
where A.HUB_STATE_IND ='1' and A.FCT_ROWID=?