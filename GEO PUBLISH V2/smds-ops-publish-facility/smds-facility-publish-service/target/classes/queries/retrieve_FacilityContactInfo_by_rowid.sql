select B.FRST_NM as firstName,
       B.LST_NM as lastName,
       B.JOB_TITLE as jobTitle,
       B.DEPT as department,
       C.DAILNG_CD_DESC as internationalDialingCdPhone,
       B.EXTN as extension,
       B.PH_NUM as phoneNumber,
       D.DAILNG_CD_DESC as internationalDialingCdMobile,
       B.MOB_NUM as mobileNumber,
       E.DAILNG_CD_DESC as internationalDialingCdFax,
       B.FAX as faxNumber,
       B.EMAIL as emailAddress,
       A.VALID_THRU_DT as validThroughDate
from C_FCT_CONT_REL A
         inner join C_FCT_CONT B on B.ROWID_OBJECT = A.CONT_ROWID AND B.HUB_STATE_IND = 1
         LEFT Outer join C_CTM_INTL_DIALNG_CD C on C.ROWID_OBJECT = B.INTL_DIALNG_ROWID_PH AND C.HUB_STATE_IND = 1
         LEFT Outer join C_CTM_INTL_DIALNG_CD D on D.ROWID_OBJECT = B.INTL_DIALNG_ROWID_MOB AND D.HUB_STATE_IND = 1
         LEFT Outer join C_CTM_INTL_DIALNG_CD E on E.ROWID_OBJECT = B.INTL_DIALNG_ROWID_FAX AND E.HUB_STATE_IND = 1
where A.HUB_STATE_IND ='1' and A.FCT_ROWID=?