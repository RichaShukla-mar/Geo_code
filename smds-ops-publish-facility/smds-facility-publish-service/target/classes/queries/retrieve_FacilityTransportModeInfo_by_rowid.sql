select B.TRNSP_NAME as transportMode,
       B.TRNSP_CD as transportCode,
       B.TRNSP_DESC as transportDescription,
       A.VALID_THRU_DT as validThroughDate
       from C_FCT_TRNSP_REL A
       inner join C_FCT_TRNSP_MODE B on B.ROWID_OBJECT = A.TRNSP_ROWID AND B.HUB_STATE_IND = 1
where A.HUB_STATE_IND ='1' and A.FCT_ROWID=?