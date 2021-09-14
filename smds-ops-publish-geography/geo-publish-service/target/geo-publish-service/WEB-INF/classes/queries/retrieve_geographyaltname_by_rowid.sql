select B.NAME as name ,
       B.DESCRIPTION as description,
       CASE WHEN  B.ACTIVE_FLAG='Y' THEN 'Active'
            ELSE 'InActive' end  as status  from C_GDA_DFND_AREA A
INNER JOIN C_ALT_NAME B ON B.GDA_DFND_AREA_ROWID=A.ROWID_OBJECT AND B.HUB_STATE_IND=1
where A.HUB_STATE_IND=1 AND A.ROWID_OBJECT=?
