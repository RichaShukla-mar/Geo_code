select B.GDA_DFND_AREA_PRNT_ROWID as bdaRowID,
       C.NAME as name ,
       D.NAME as type,
       E.BDA_TYPE_CD as bdatype
from C_GDA_DFND_AREA A
         INNER JOIN C_GDA_DFND_AREA_REL B ON B.GDA_DFND_AREA_CHLD_ROWID=A.ROWID_OBJECT AND B.HUB_STATE_IND=1 and B.TYP_TYPE_CD in ('GDA_REL.GDA_IN_BDA')
         INNER JOIN C_GDA_DFND_AREA C ON C.ROWID_OBJECT=B.GDA_DFND_AREA_PRNT_ROWID AND C.HUB_STATE_IND=1
         INNER JOIN C_TYP_TYPE D ON D.CODE=C.TYP_TYPE_CD AND D.HUB_STATE_IND=1
         INNER JOIN C_GDA_BDA E ON E.GDA_DFND_AREA_ROWID=B.GDA_DFND_AREA_PRNT_ROWID AND E.HUB_STATE_IND=1
where A.HUB_STATE_IND=1 AND A.ROWID_OBJECT=?